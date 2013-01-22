package search.utils;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

public class HtmlFile {
	private String url;
	private HtmlPage hp;

	public HtmlFile(String url) throws ParserException {
		this.url = url;
		Parser parser = new Parser(url);
		hp = new HtmlPage(parser);
		parser.visitAllNodesWith(hp);
	}

	public String getTitle() {
		return hp.getTitle();
	}

	public String getHtmlContent() {
		return hp.getBody().toHtml();
	}
	
	public String getPlainTextContent() {
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

}
