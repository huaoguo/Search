package search.utils;

import search.db.DBUtils;

public class Conventions {
	public static String getDBPath() {
		return "db/" + DBUtils.mode.toString().toLowerCase() + ".db";
	}
	
	public static String getSQLFilePath(String filename){
		return "db/" + DBUtils.mode.toString().toLowerCase() + "/" + filename + ".sql";
	}
}
