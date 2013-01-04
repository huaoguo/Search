package search.db;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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

}
