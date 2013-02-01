package search.utils;

import org.htmlparser.util.ParserException;

import search.Search;

import junit.framework.TestCase;

public class HtmlFileTest extends TestCase{
	private HtmlFile hf;
	
	public void setUp() throws ParserException{
		hf = new HtmlFile(Search.path + "/already/zywqs.blog.51cto.com/index.html");
	}

	public void testGetTitle() {
		assertEquals("忘情水 曾经年少爱追梦 - 51CTO技术博客 - 领先的IT技术博客", hf.getTitle());
	}
	
	public void testGetKeywords() throws ParserException {
		assertEquals("忘情水 曾经年少爱追梦 - 51CTO技术博客 - 领先的IT技术博客", hf.getTitle());
		assertEquals("忘情水 曾经年少爱追梦", hf.getKeywords());
		assertEquals("忘情水 曾经年少爱追梦 - 51CTO技术博客 - 领先的IT技术博客", hf.getTitle());
		assertEquals("忘情水 曾经年少爱追梦", hf.getKeywords());
	}
}
