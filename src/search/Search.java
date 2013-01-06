package search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.htmlparser.beans.StringBean;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import search.db.DBUtils;
import search.model.Dict;
import search.model.DictDoc;
import search.model.Doc;

public class Search {
	private static final Logger logger = LogManager.getLogger(Search.class);
	public static final String path = "E:\\workspace\\heritrix-3.1.0\\mirror";
	private static Set<Dict> dicts = new HashSet<>();

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
			SQLException {
		File[] dirs = dir.listFiles();
		for (File file : dirs) {
			logger.debug("process: " + file.getAbsolutePath());
			if (file.isFile()) {
				processHTML(file);
			} else if (file.isDirectory()) {
				process(file);
			}
		}
	}

	private static void processHTML(File file) throws IOException, ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException {
		long t1 = System.currentTimeMillis();
		String text = cleanHtml("file:///" + file.getAbsolutePath());
		Doc doc = new Doc();
		doc.setText(text);
		doc.setUrl(getHttpUrl(file));
		DBUtils.storeObject(doc);
		IKSegmenter seg = new IKSegmenter(new StringReader(text), false);
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
				dicts.add(dict);
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
		for (Dict dict : dicts) {
			if (dict.getValue().equals(text)) {
				return dict;
			}
		}
		return null;
	}

	private static String cleanHtml(String url) {
		StringBean sb = new StringBean();
		sb.setLinks(false);
		// 设置将不间断空格由正规空格所替代
		sb.setReplaceNonBreakingSpaces(true);
		// 设置将一序列空格由一个单一空格所代替
		sb.setCollapse(true);
		// 传入要解析的URL
		sb.setURL(url);
		return sb.getStrings();
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
