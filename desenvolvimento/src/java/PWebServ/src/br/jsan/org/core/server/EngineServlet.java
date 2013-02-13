package br.jsan.org.core.server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.jsan.org.app.service.EstagiarioService;
import br.jsan.org.core.EngineJson;

public class EngineServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5696878751194134922L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("cons".equals(req.getParameter("acao"))){
			EstagiarioService service = new EstagiarioService();
			String listaJson = EngineJson.getInstancia().serializarLista(service.recuperarTodos());
			req.setAttribute("listaJson", listaJson);
		}
		
		RequestDispatcher reqd = req.getRequestDispatcher("/pages/resultado.jsp");
		reqd.forward(req, resp);
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
