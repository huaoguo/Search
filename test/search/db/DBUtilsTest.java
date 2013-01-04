package search.db;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;
import search.model.Dict;

public class DBUtilsTest extends TestCase{
	
	public void setUp() throws ClassNotFoundException, SQLException, IOException, InterruptedException {
		DBUtils.mode = DBMode.TEST;
		DBUtils.createDB();
	}
	
	public void testCreateDB() throws Exception {
		File file = new File("db/test.db");
		assertTrue(file.exists());
	}
	
	public void testQueryObject() throws Exception{
		Dict dict = DBUtils.queryObject(Dict.class, 1);
		assertNotNull(dict);
		assertEquals(1, dict.getId().intValue());
		assertEquals("test", dict.getValue());
	}
	
}