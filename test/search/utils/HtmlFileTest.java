package search.utils;

import org.htmlparser.util.ParserException;

import search.Search;

import junit.framework.TestCase;

public class HtmlFileTest extends TestCase{

	public void testGetTitle() throws ParserException{
		HtmlFile hf = new HtmlFile(Search.path + "/already/zywqs.blog.51cto.com/index.html");
		assertEquals("����ˮ �������ٰ�׷�� - 51CTO�������� - ���ȵ�IT��������", hf.getTitle());
	}
}
