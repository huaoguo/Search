package search.utils;

import org.htmlparser.util.ParserException;

import junit.framework.TestCase;

public class HtmlFileTest extends TestCase{

	public void testGetTitle() throws ParserException{
		HtmlFile hf = new HtmlFile("file:///E:/workspace/heritrix-3.1.0/mirror/zywqs.blog.51cto.com/index.html");
		assertEquals("����ˮ �������ٰ�׷�� - 51CTO�������� - ���ȵ�IT��������", hf.getTitle());
	}
}
