package br.jsan.org.core.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EngineServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5696878751194134922L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("PathInfo 		= " + req.getPathInfo());
		System.out.println("ServletPath 	= " + req.getServletPath());
		System.out.println("QueryString 	= " + req.getQueryString());
		System.out.println("getRequestURI 	= " + req.getRequestURI());
		System.out.println("getRequestURL 	= " + req.getRequestURL().toString());
		
//		if ("cons".equals(req.getParameter("acao"))){
//			EstagiarioService service = new EstagiarioService();
//			String listaJson = EngineJson.getInstancia().serializarLista(service.recuperarTodos());
//			req.setAttribute("listaJson", listaJson);
//		}
//		
//		RequestDispatcher reqd = req.getRequestDispatcher("/pages/resultado.jsp");
//		reqd.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
