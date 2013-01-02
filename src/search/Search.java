package search;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.htmlparser.beans.StringBean;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


public class Search {
	private static final Logger logger = LogManager.getLogger(Search.class);
	public static final String path = "E:\\workspace\\heritrix-3.1.0\\mirror";

	public static void main(String[] args) throws Exception {
		File file = new File(path);
		File[] dirs = file.listFiles();
		for (File d : dirs) {
			processSite(d);
		}
	}
	
	private static void processSite(File dir) throws IOException{
		File[] dirs = dir.listFiles();
		for (File file : dirs) {
			if(file.isFile()){
				processHTML(file);
				break;
			}else if(file.isDirectory()){
				processDirectory(file);
			}
		}
	}
	
	private static void processHTML(File file) throws IOException{
		logger.debug("process: " + file.getAbsolutePath());
		String html = cleanHtml("file:///" + file.getAbsolutePath());
		IKSegmenter seg = new IKSegmenter(new StringReader(html), false);
		Lexeme lex = null;
		Set<String> word = new HashSet<>();
		while((lex = seg.next()) != null){
			logger.debug(lex.getLexemeText());
			word.add(lex.getLexemeText());
		}
		logger.info("共有: " + word.size() + " 个词。");
	}
	
	private static String cleanHtml(String url){
		StringBean sb = new StringBean();
		sb.setLinks(false);
		// 设置将不间断空格由正规空格所替代
		sb.setReplaceNonBreakingSpaces(true);
		// 设置将一序列空格由一个单一空格所代替
		sb.setCollapse(true);
		// 传入要解析的URL
		sb.setURL("file:///E:\\workspace\\heritrix-3.1.0\\mirror\\dota.uuu9.com\\aw.shtml");
		return sb.getStrings();
	}
	
	private static void processDirectory(File file){
		
	}

}
