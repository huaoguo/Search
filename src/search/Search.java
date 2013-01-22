package search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import search.db.DBUtils;
import search.model.Dict;
import search.model.DictDoc;
import search.model.Doc;
import search.utils.HtmlFile;

public class Search {
	private static final Logger logger = LogManager.getLogger(Search.class);
	public static final String path = "E:\\workspace\\heritrix-3.1.0\\mirror";
	private static Map<String, Dict> dicts = new HashMap<>();

	public static void main(String[] args) throws Exception {
		DBUtils.createDB();
		File file = new File(path);
		File[] dirs = file.listFiles();
		for (File d : dirs) {
			process(d);
		}
	}

	private static void process(File dir) throws IOException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SQLException, ParserException {
		File[] dirs = dir.listFiles();
		for (File file : dirs) {
			if (file.isFile()
					&& (file.getName().endsWith(".html") || file.getName().endsWith(".htm") || file.getName()
							.endsWith(".shtml"))) {
				processHTML(file);
			} else if (file.isDirectory()) {
				process(file);
			}
		}
	}

	private static void processHTML(File file) throws IOException, ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException, ParserException {
		logger.info("process: " + file.getAbsolutePath());
		long t1 = System.currentTimeMillis();
		HtmlFile hf = new HtmlFile("file:///" + file.getAbsolutePath());
		Doc doc = new Doc();
		doc.setTitle(hf.getTitle());
		doc.setText(hf.getPlainTextContent());
		doc.setUrl(getHttpUrl(file));
		DBUtils.storeObject(doc);
		IKSegmenter seg = new IKSegmenter(new StringReader(hf.getPlainTextContent()), false);
		Lexeme lex = null;
		Map<Dict, Integer> tfMap = new HashMap<Dict, Integer>();
		List<Dict> newDicts = new ArrayList<>();
		while ((lex = seg.next()) != null) {
			String lexemeText = lex.getLexemeText();
			logger.debug(lexemeText);
			Dict dict = findDict(lexemeText);
			if (dict == null) {
				dict = new Dict();
				dict.setValue(lexemeText);
				dicts.put(lexemeText, dict);
				newDicts.add(dict);
			}
			Integer tf = tfMap.get(dict);
			if (tf == null) {
				tfMap.put(dict, 1);
			} else {
				tfMap.put(dict, tf + 1);
			}
		}
		DBUtils.storeObjects(newDicts);
		List<DictDoc> dictDocs = new ArrayList<>();
		for (Entry<Dict, Integer> entry : tfMap.entrySet()) {
			DictDoc dd = new DictDoc();
			dd.setDictId(entry.getKey().getId());
			dd.setDocId(doc.getId());
			dd.setTf(entry.getValue());
			dictDocs.add(dd);
		}
		DBUtils.storeObjects(dictDocs);
		long cost = System.currentTimeMillis() - t1;
		logger.info("词典目前共有: " + dicts.size() + " 个词，用时:" + cost + "ms");
	}

	private static Dict findDict(String text) {
		return dicts.get(text);
	}

	private static String getHttpUrl(File file) {
		String url = file.getAbsolutePath().replace(Search.path + "\\", "");
		String[] tokens = url.split("\\\\");
		StringBuilder sb = new StringBuilder("http://");
		for (String t : tokens) {
			sb.append(t).append("/");
		}
		return sb.toString();
	}

}
