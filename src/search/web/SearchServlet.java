package search.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import search.Server;
import search.Server.Result;
import search.db.DBUtils;
import search.model.Doc;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(SearchServlet.class);
	
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
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < 30 && i < results.size(); i++) {
			if(i > 0){
				sql.append(" union all ");
			}
			sql.append("select * from doc where id = ").append(results.get(i).docId);
		}
		long t = System.currentTimeMillis();
		List<Doc> docs = DBUtils.executeQuery(Doc.class, sql.toString());
		logger.debug("≤È—Ødocs£¨”√ ±£∫" + (System.currentTimeMillis() - t) + "ms");
		return docs;
	}
}
