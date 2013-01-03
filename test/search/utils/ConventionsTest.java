package search.utils;

import search.db.DBMode;
import search.db.DBUtils;
import junit.framework.TestCase;

public class ConventionsTest extends TestCase {
	
	public void testGetDBPath(){
		DBUtils.mode = DBMode.TEST;
		assertEquals("db/test.db", Conventions.getDBPath());
		DBUtils.mode = DBMode.DEVELOPMENT;
		assertEquals("db/development.db", Conventions.getDBPath());
		DBUtils.mode = DBMode.PRODUCTION;
		assertEquals("db/production.db", Conventions.getDBPath());
	}
	
	public void testGetSQLFilePath(){
		DBUtils.mode = DBMode.TEST;
		assertEquals("db/test/create_table.sql", Conventions.getSQLFilePath("create_table"));
		DBUtils.mode = DBMode.DEVELOPMENT;
		assertEquals("db/development/init_data.sql", Conventions.getSQLFilePath("init_data"));
		DBUtils.mode = DBMode.PRODUCTION;
		assertEquals("db/production/init_data.sql", Conventions.getSQLFilePath("init_data"));
	}
}
