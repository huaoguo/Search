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
import java.util.Scanner;

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
	private static boolean stopped = false;

	public static void main(String[] args) throws Exception {
		new Thread() {
			@Override
			public void run() {
				try {
					DBUtils.createDB();
					File file = new File(path);
					File[] dirs = file.listFiles();
					for (File d : dirs) {
						if (stopped) {
							logger.info("正在停止程序...");
							break;
						}
						try {
							process(d);
						} catch (Exception e) {
							logger.info(e.getMessage(), e);
						}
					}
					storeCachedObjects();
				} catch (ClassNotFoundException | SQLException | IOException | NoSuchMethodException
						| SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}.start();
		Scanner scn = new Scanner(System.in);
		while (scn.hasNext()) {
			if (scn.nextLine().equals("exit")) {
				stopped = true;
				break;
			}
		}
		scn.close();
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

	private static List<Dict> cachedDicts = new ArrayList<>();
	private static List<Doc> cachedDocs = new ArrayList<>();
	private static List<DictDoc> cachedDictDocs = new ArrayList<>();
	private static int count = 0;

	private static void processHTML(File file) throws IOException, ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException, ParserException {
		logger.info("process: " + file.getAbsolutePath());
		long t1 = System.currentTimeMillis();
		HtmlFile hf = new HtmlFile("file:///" + file.getAbsolutePath());
		Doc doc = new Doc();
		doc.setId(DBUtils.getGeneratedId(Doc.class));
		doc.setTitle(hf.getTitle());
		doc.setText(hf.getPlainTextContent());
		doc.setUrl(getHttpUrl(file));
		cachedDocs.add(doc);
		IKSegmenter seg = new IKSegmenter(new StringReader(hf.getPlainTextContent()), false);
		Lexeme lex = null;
		Map<Dict, Integer> tfMap = new HashMap<Dict, Integer>();
		while ((lex = seg.next()) != null) {
			String lexemeText = lex.getLexemeText();
			logger.debug(lexemeText);
			Dict dict = dicts.get(lexemeText);
			if (dict == null) {
				dict = new Dict();
				dict.setId(DBUtils.getGeneratedId(Dict.class));
				dict.setValue(lexemeText);
				dicts.put(lexemeText, dict);
				cachedDicts.add(dict);
			}
			Integer tf = tfMap.get(dict);
			if (tf == null) {
				tfMap.put(dict, 1);
			} else {
				tfMap.put(dict, tf + 1);
			}
		}
		for (Entry<Dict, Integer> entry : tfMap.entrySet()) {
			DictDoc dd = new DictDoc();
			dd.setId(DBUtils.getGeneratedId(DictDoc.class));
			dd.setDictId(entry.getKey().getId());
			dd.setDocId(doc.getId());
			dd.setTf(entry.getValue());
			cachedDictDocs.add(dd);
		}
		long cost = System.currentTimeMillis() - t1;
		logger.info("词典目前共有: " + dicts.size() + " 个词，用时:" + cost + "ms");
		count++;
		if (count % 1000 == 0) {
			storeCachedObjects();
		}
	}

	private static void storeCachedObjects() throws ClassNotFoundException, SQLException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		long t2 = System.currentTimeMillis();
		DBUtils.storeObjects(cachedDicts);
		DBUtils.storeObjects(cachedDocs);
		DBUtils.storeObjects(cachedDictDocs);
		long cost2 = System.currentTimeMillis() - t2;
		logger.info(String.format("存储了 %s 个词，%s 个文档，%s 个词-文档，用时 %s ms", cachedDicts.size(),
				cachedDocs.size(), cachedDictDocs.size(), cost2));
		cachedDicts.clear();
		cachedDocs.clear();
		cachedDictDocs.clear();
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
