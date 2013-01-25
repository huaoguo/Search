package search.utils;

import org.htmlparser.util.ParserException;

import search.Search;

import junit.framework.TestCase;

public class HtmlFileTest extends TestCase{

	public void testGetTitle() throws ParserException{
		HtmlFile hf = new HtmlFile(Search.path + "/already/zywqs.blog.51cto.com/index.html");
		assertEquals("忘情水 曾经年少爱追梦 - 51CTO技术博客 - 领先的IT技术博客", hf.getTitle());
	}
}
