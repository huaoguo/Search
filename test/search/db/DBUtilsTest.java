package search.db;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import search.model.Dict;
import search.model.DictDoc;
import search.model.Doc;

public class DBUtilsTest extends TestCase {

	public void setUp() throws ClassNotFoundException, SQLException, IOException, InterruptedException {
		DBUtils.mode = DBMode.TEST;
		DBUtils.createDB();
	}

	public void testCreateDB() throws Exception {
		File file = new File("db/test.db");
		assertTrue(file.exists());
	}

	public void testQueryObject() throws Exception {
		Dict dict = DBUtils.queryObject(Dict.class, 1);
		assertNotNull(dict);
		assertEquals(new Integer(1), dict.getId());
		assertEquals("test", dict.getValue());

		Doc doc = DBUtils.queryObject(Doc.class, 1);
		assertNotNull(doc);
		assertEquals(new Integer(1), doc.getId());
		assertEquals("test", doc.getTitle());
		assertEquals("我们要做一些test", doc.getText());
		assertEquals("http://www.baidu.com", doc.getUrl());

		DictDoc dictDoc = DBUtils.queryObject(DictDoc.class, 1);
		assertNotNull(dictDoc);
		assertEquals(new Integer(1), dictDoc.getId());
		assertEquals(new Integer(1), dictDoc.getDictId());
		assertEquals(new Integer(1), dictDoc.getDocId());
		assertEquals(new Integer(1), dictDoc.getTf());

		Dict dict2 = DBUtils.queryObject(Dict.class, "value='test'");
		assertNotNull(dict2);
		assertEquals(new Integer(1), dict2.getId());
	}

	public void testQueryObjects() throws Exception {
		List<DictDoc> dictDocs = DBUtils.queryObjects(DictDoc.class, "dict_id=1");
		assertNotNull(dictDocs);
		assertEquals(2, dictDocs.size());
	}

	public void testStoreObject() throws Exception {
		Dict dict = new Dict();
		dict.setValue("java");
		DBUtils.storeObject(dict);
		assertEquals(new Integer(2), dict.getId());
		Dict dict2 = DBUtils.queryObject(Dict.class, 2);
		assertNotNull(dict2);
		assertEquals(dict.getValue(), dict2.getValue());

		Doc doc = new Doc();
		doc.setTitle("百度知道");
		doc.setText("有一些草莓的叶子枯了");
		doc.setUrl("http://www.baidu.com");
		DBUtils.storeObject(doc);
		Doc doc2 = DBUtils.queryObject(Doc.class, doc.getId());
		assertNotNull(doc2);
		assertEquals(doc.getId(), doc2.getId());
		assertEquals(doc.getText(), doc2.getText());
		assertEquals(doc.getUrl(), doc2.getUrl());
	}

	public void testStoreObjects() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		List<Dict> dicts = new ArrayList<>();
		Dict dict = new Dict();
		dict.setValue("java");
		dicts.add(dict);
		Dict dict2 = new Dict();
		dict2.setValue("eclipse");
		dicts.add(dict2);
		Dict dict3 = new Dict();
		dict3.setValue("敏捷");
		dicts.add(dict3);
		DBUtils.storeObjects(dicts);
		for (Dict d : dicts) {
			assertNotNull(d.getId());
		}
	}

}