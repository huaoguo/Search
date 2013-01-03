package search.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

	public static String readFile(String filepath) throws IOException{
		File file = new File(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null){
			sb.append(line + "\r\n");
		}
		br.close();
		return sb.toString();
	}
}
