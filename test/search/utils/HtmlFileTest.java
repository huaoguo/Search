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
		assertEquals("����ˮ �������ٰ�׷�� - 51CTO�������� - ���ȵ�IT��������", hf.getTitle());
	}
	
	public void testGetKeywords() throws ParserException {
		assertEquals("����ˮ �������ٰ�׷�� - 51CTO�������� - ���ȵ�IT��������", hf.getTitle());
		assertEquals("����ˮ �������ٰ�׷��", hf.getKeywords());
		assertEquals("����ˮ �������ٰ�׷�� - 51CTO�������� - ���ȵ�IT��������", hf.getTitle());
		assertEquals("����ˮ �������ٰ�׷��", hf.getKeywords());
	}
}
