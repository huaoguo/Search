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
		// ���ý�����Ͽո�������ո������
		sb.setReplaceNonBreakingSpaces(true);
		// ���ý�һ���пո���һ����һ�ո�������
		sb.setCollapse(true);
		// ����Ҫ������URL
		sb.setURL(url);
		return sb.getStrings();
	}

}
