package search.utils;

import search.model.Dict;
import junit.framework.TestCase;

public class ReflectionUtilsTest extends TestCase {

	public void testGetMethod() throws NoSuchMethodException, SecurityException{
		assertNotNull(ReflectionUtils.getSetter(Dict.class, "id", Integer.class));
		assertNotNull(ReflectionUtils.getSetter(Dict.class, "value", String.class));
	}
}
