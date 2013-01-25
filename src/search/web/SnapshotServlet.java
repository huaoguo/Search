package search.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search.Search;
import search.db.DBUtils;
import search.model.Doc;
import search.utils.FileUtils;

@SuppressWarnings("serial")
public class SnapshotServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		String id = req.getParameter("id");
		try {
			String url = DBUtils.queryObject(Doc.class, Integer.parseInt(id)).getUrl();
			url = url.replace("http://", "").replace("/", "\\");
			String filepath = Search.path + "\\already\\" + url;
			String content = FileUtils.readHtmlFile(filepath);
			resp.setContentType("text/html;charset=GBK");
			PrintWriter pw = resp.getWriter();
			pw.write(content);
			pw.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
