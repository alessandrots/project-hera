package com.outline.org.core.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import com.outline.org.app.security.AuthenticationOutline;
import com.outline.org.app.service.IService;

/**
 * TODO - botar o projeto todo no padrão maven e colocar as dependências.
 * 
 * @author alessandrots
 *
 */
public class ProjectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3954779776778587702L;
	
	private Map<String, IService> mapaServicos;
	
	@Override
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);		
		mapaServicos = new HashMap<String, IService>();		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("PathInfo 		= " + req.getPathInfo());
		System.out.println("ServletPath 	= " + req.getServletPath());
		System.out.println("QueryString 	= " + req.getQueryString());
		System.out.println("getRequestURI 	= " + req.getRequestURI());
		System.out.println("getRequestURL 	= " + req.getRequestURL().toString());
		
		try {
//			exibirParametros(req);
			
			if (req.getPathInfo() != null) {
				//retirando a primeira barra
				String pathURL 		= req.getPathInfo().substring(1);				
				String servFunc[] 	= pathURL.split("/");
				
				//TODO tratamentos
				if (servFunc.length < 2 ){
					throw new RuntimeException("Impossível carregar e executar o serviço e/ou a funcionalidade.");
				}
				
				//classe de serviço
				String defServico 		 = servFunc[0];
				
				//método negocial
				String defFuncionalidade = servFunc[1];
				
				//TODO - spring
				IService servico = carregarServico(defServico);
				
				//Criado o domain de camada de apresentação
//				IPresenter objPresenter = gerarPresenterViaJSon(req, req.getPathInfo());
				String jsonDados = gerarJsonViaRequest(req);
				
				//passando a autenticação
				Principal seguranca = req.getUserPrincipal();
				
				if (seguranca != null) {			
					servico.setUsuarioAutenticado((AuthenticationOutline)seguranca);
				}
				
				//TODO - o serviço vai executar sempre o método execute e dentro dele vai executar
				//a funcionalidade passada como segundo parâmetro
				executarFuncionalidade(servico, defFuncionalidade, jsonDados, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//TODO - definir a página de retorno, tratamento de exceção
		}		
		//RequestDispatcher reqd = req.getRequestDispatcher("/pages/resultado.jsp");
		//reqd.forward(req, resp);
	}
	
	/**
	 * 
	 * TODO - spring
	 *  - carregar o service específico, de acordo com a primeira parte do path
	 *  	cadTarefas(servico)/add(funcionalidade)
	 *  	cadTarefas/load	// TODO Auto-generated method stub
	 *  
	 *  - neste caso vai gerar o serviço associado ao cadTarefas
	 *   
	 * @param string
	 * @return IService
	 */
	private IService carregarServico(String path) {
		IService servico = null;
		
		SpringManager.getInstancia().setServletContext(getServletContext());
		
		if (path != null && !path.equals("")){
			String[] arrayPath = path.split("/");
			
			String defServico = arrayPath[0];
			
			if (mapaServicos.containsKey(path)){
				servico = mapaServicos.get(defServico);
			} else if (path != null && path.contains(defServico)){
//			servico = new TarefaService();
				servico = SpringManager.getInstancia().getBean(defServico);
				
				if (servico != null){
					mapaServicos.put(defServico, servico);
				}
			}
		}
		
		
		return servico;
	}

	/**
	 * Aqui o serviço vai executar um método padrão(tipo execute) e com base na funcionalidade vai carregar o método específico.
	 * 
	 * No caso do path: cadTarefas/add, a segunda parte (add) vai ser o método a ser acionado na classe que herda de IService.
	 * 
	 * TODO o ideal é que tenha um método que traga o retorno da pesquisạ, ou o método execute vai dar um return,
	 * ou vai ter um classe pai com um getRetorno ou já envio de volta um JSon. Talvez a última seja a melhor soluçãọ.
	 * 
	 * TODO tem que ver o tratamento de exceção
	 * 
	 * @param servico
	 * @param resp TODO
	 * @param objPresenter
	 * @param funcionalidade
	 */
	private void executarFuncionalidade(IService servico, String defFuncionalidade, String dados, HttpServletResponse resp) {		
		//Setando os parâmetros de filtro ou para persistência
		servico.setJSon(dados);
		
		try {
			//Criando o método execute e definindo o tipo do parâmetro de entrada
			//TODO colocar a string execute numa classe constante
			Method method = servico.getClass().getMethod("execute", String.class);
			
			//Chamando o método execute, passando o parâmetro acao(que é o método a ser chamado)
			method.invoke(servico, new Object[]{defFuncionalidade});
			
			enviarResposta(resp, servico.getResposta());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gera uma String no formato JSon a partir dos dados que vem da requisição:
	 * 	http://stackoverflow.com/questions/2496494/library-to-encode-decode-from-json-to-java-util-map
	 * 	http://stackoverflow.com/questions/9598707/gson-throwing-expected-begin-object-but-was-begin-array
	 * 
	 * Pq recebo um model:
	 * http://net.tutsplus.com/tutorials/javascript-ajax/build-a-contacts-manager-using-backbone-js-part-5/
	 * 
	 * @param req
	 * @return String
	 */
	private String gerarJsonViaRequest(HttpServletRequest req) {		
		//Obtendo um map dos parâmetros
		Map requestParams = req.getParameterMap();
		
		String jSonGerado = null;
		
		//o map que vai ser usado para gerar uma estrutura json
		Map<String, String> mpAtributos = new HashMap<String, String>();
		
		if (requestParams != null && requestParams.size() > 0){
			Iterator<String> ite = requestParams.keySet().iterator();
			
			while (ite.hasNext()) {
				String type = ite.next();
				
				if (!type.equalsIgnoreCase("model")){
					//TODO - tratar qdo vier vários valores por um parâmetro com %2C
					mpAtributos.put(type, req.getParameter(type));
				} else{
					//vem do atributo de request -> model
					jSonGerado = req.getParameter(type);
				}
			}
			
			if (jSonGerado == null){
				jSonGerado = JSONValue.toJSONString(mpAtributos);			
			}
		}
		
		return jSonGerado;
	}	
	
	private void exibirParametros(HttpServletRequest req) {
		Map requestParams = req.getParameterMap();
		
		if (requestParams != null && requestParams.size() > 0){
			System.out.println(requestParams.keySet() + " = " + requestParams);
			Iterator<String> ite = requestParams.keySet().iterator();
			
			while (ite.hasNext()) {
				String type = ite.next();
				System.out.println(" map - param = " + type + " value = " + req.getParameter(type));	
			}
		}
	}
	
	private void enviarResposta(HttpServletResponse resp, String jsonObject) {
		PrintWriter out = null;
		
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(jsonObject);
		
		out.print(jsonObject);
		out.flush();
	}

}
