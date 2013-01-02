package search.utils;

import search.utils.StringUtils;
import junit.framework.TestCase;

public class StringUtilsTest extends TestCase{
	
	public void testCapitalize(){
		assertEquals("Value", StringUtils.capitalize("value"));
		assertEquals("TermFrequence", StringUtils.capitalize("term_frequence"));
	}
	
	public void testTableStyle(){
		assertEquals("dict", StringUtils.tableStyle("Dict"));
		assertEquals("term_frequence", StringUtils.tableStyle("TermFrequence"));
	}
}
