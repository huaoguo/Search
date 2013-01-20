package search.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import freemarker.ext.servlet.FreemarkerServlet;

public class Main {

	public static void main(String[] args) throws Exception{
		Server server = new Server(80);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(FreemarkerServlet.class, "*.ftl");
		context.setInitParameter("TempaltePath", "/");
		context.addServlet(IndexServlet.class, "/");
		context.addServlet(SearchServlet.class, "/search");
		context.addServlet(SnapshotServlet.class, "/snapshot");
		server.setHandler(context);
		server.start();
		server.join();
	}

}
