package search.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getSetter(Class clazz, String columnName,
			Class... parameterTypes) throws NoSuchMethodException,
			SecurityException {
		String methodName = "set" + StringUtils.capitalize(columnName);
		return clazz.getMethod(methodName, parameterTypes);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getGetter(Class clazz, String columnName) throws NoSuchMethodException, SecurityException {
		String methodName = "get" + StringUtils.capitalize(columnName);
		return clazz.getMethod(methodName, new Class[0]);
	}
}
