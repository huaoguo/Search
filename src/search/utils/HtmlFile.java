package search.utils;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

public class HtmlFile {
	private String url;
	private HtmlPage hp;
	private Parser parser;

	public HtmlFile(String url) throws ParserException {
		this.url = url;
		parser = new Parser(url);
		hp = new HtmlPage(parser);
		parser.visitAllNodesWith(hp);
	}
	
	public String getKeywords() throws ParserException {
		parser.reset();
		NodeFilter keywordsFilter = new AndFilter(new TagNameFilter("meta"), new HasAttributeFilter("name", "keywords"));
		NodeList nl = parser.parse(keywordsFilter);
		return ((MetaTag)nl.elementAt(0)).getMetaContent();
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
