package search.utils;

public class StringUtils {
	public static String capitalize(String word){
		String[] tokens = word.split("_");
		StringBuilder sb = new StringBuilder();
		for (String str : tokens) {
			sb.append(str.substring(0,1).toUpperCase() + str.substring(1));
		}
		return sb.toString();
	}

	public static String tableStyle(String string) {
		char[] ch = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		for (char c : ch) {
			if(Character.isUpperCase(c)){
				if(flag){
					sb.append("_");
				}
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
				flag = true;
			}
		}
		return sb.toString();
	}
}
