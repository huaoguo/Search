package search.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search.Server;
import search.Server.Result;
import search.db.DBUtils;
import search.model.Doc;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		req.setAttribute("org.eclipse.jetty.server.Request.queryEncoding","GBK");
		List<Doc> docs = null;
		try {
			docs = search(req.getParameter("q"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("docs", docs);
		req.getRequestDispatcher("search/web/search.ftl").forward(req, resp);
	}
	
	private List<Doc> search(String query) throws Exception{
		List<Result> results = Server.search(query);
		StringBuilder where = new StringBuilder("id in (");
		for (int i = 0; i < 10; i++) {
			where.append(results.get(i).docId).append(",");
		}
		where.replace(where.length() - 1, where.length(), ")");
		List<Doc> docs = DBUtils.queryObjects(Doc.class, where.toString());
		return docs;
	}
}
