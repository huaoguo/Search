package search.db;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import search.utils.Conventions;
import search.utils.FileUtils;
import search.utils.ReflectionUtils;

public class DBUtils {
	public static DBMode mode = DBMode.DEVELOPMENT;
	private static final Logger logger = LogManager.getLogger(DBUtils.class);

	public static <T> T queryObject(Class<T> clazz, int id) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Conventions.getDBPath());
		String tableName = Conventions.getTableName(clazz);
		String sql = "select * from " + tableName + " where id = ?;";
		logger.debug(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		T result = null;
		if (rs.next()) {
			result = clazz.newInstance();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i + 1);
				Object data = rs.getObject(i + 1);
				Method setter = ReflectionUtils.getSetter(clazz, columnName, data.getClass());
				setter.invoke(result, data);
			}
		}
		rs.close();
		pstmt.close();
		conn.close();
		return result;
	}

	public static void execute(String sql, Object... params) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Conventions.getDBPath());
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		}
		pstmt.execute();
		pstmt.close();
		conn.close();
	}

	private static void runSqlFile(Connection conn,String filepath) throws IOException, SQLException {
		String sql = FileUtils.readFile(filepath);
		String[] sqls = sql.split("\r\n");
		Statement stmt = conn.createStatement();
		for (String s : sqls) {
			if(s.length() > 0){
				stmt.execute(s);
				logger.debug(s);
			}
		}
		stmt.executeBatch();
		stmt.close();
	}

	public static void createDB() throws SQLException, ClassNotFoundException, IOException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Conventions.getDBPath());
		conn.setAutoCommit(false);
		runSqlFile(conn,Conventions.getSQLFilePath("create_table"));
		runSqlFile(conn,Conventions.getSQLFilePath("init_data"));
		conn.commit();
		conn.close();
	}

	public static void storeObject(Object data) throws ClassNotFoundException, SQLException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Conventions.getDBPath());
		String tableName = Conventions.getTableName(data.getClass());
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
		List<String> columns = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tableName).append("(");
		while(rs.next()){
			String columnName = rs.getString("COLUMN_NAME");
			if(!columnName.equals("id")){
				columns.add(columnName);
				sql.append(columnName).append(",");
			}
		}
		rs.close();
		sql.replace(sql.length()-1, sql.length(), ") values(");
		for (String columnName : columns) {
			Method getter = ReflectionUtils.getGetter(data.getClass(), columnName);
			Object field = getter.invoke(data, new Object[0]);
			sql.append("'").append(field.toString()).append("',");
		}
		sql.replace(sql.length()-1, sql.length(), ");");
		logger.debug(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		pstmt.execute();
		rs = pstmt.getGeneratedKeys();
		if(rs.next()){
			int id = rs.getInt(1);
			Method setter = ReflectionUtils.getSetter(data.getClass(), "id", Integer.class);
			setter.invoke(data, id);
		}
		rs.close();
		pstmt.close();
		conn.close();
	}

}
