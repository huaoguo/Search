package search.utils;

import search.model.Dict;
import junit.framework.TestCase;

public class ReflectionUtilsTest extends TestCase {

	public void testGetSetter() throws NoSuchMethodException, SecurityException{
		assertNotNull(ReflectionUtils.getSetter(Dict.class, "id", Integer.class));
		assertNotNull(ReflectionUtils.getSetter(Dict.class, "value", String.class));
	}
	
	public void testGetGetter() throws NoSuchMethodException, SecurityException{
		assertNotNull(ReflectionUtils.getGetter(Dict.class, "id"));
		assertNotNull(ReflectionUtils.getGetter(Dict.class, "value"));
	}
}
