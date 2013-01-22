package search.utils;

import org.htmlparser.util.ParserException;

import junit.framework.TestCase;

public class HtmlFileTest extends TestCase{

	public void testGetTitle() throws ParserException{
		HtmlFile hf = new HtmlFile("file:///E:/workspace/heritrix-3.1.0/mirror/zywqs.blog.51cto.com/index.html");
		assertEquals("忘情水 曾经年少爱追梦 - 51CTO技术博客 - 领先的IT技术博客", hf.getTitle());
	}
}
