package search.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

public class FileUtils {

	public static String readHtmlFile(String filepath) throws ParserException {
		return new Parser(filepath).parse(null).toHtml();
	}

	public static String readFile(String filepath) throws IOException {
		return readFile(filepath, "GBK");
	}

	public static String readFile(String filepath, String encoding) throws IOException {
		File file = new File(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				Charset.forName(encoding)));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line + "\r\n");
		}
		br.close();
		return sb.toString();
	}
}
