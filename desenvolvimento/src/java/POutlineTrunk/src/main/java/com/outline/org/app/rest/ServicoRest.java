package com.outline.org.app.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.outline.org.app.facade.TarefaFacade;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.core.server.SpringManager;

@Path("/teste")
/**
 * http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
 * http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/
 * http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
 */
@Component("serviceTest")
public class ServicoRest {
	
	@Autowired	 
	private TarefaFacade tarefaFacade;
	
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/listarResumidosSobResponsabilidadeMembro")
	@Produces("text/json;charset=UTF-8")
	public TarefaPresenter listarResumidosSobResponsabilidadeMembro(@QueryParam("haMaisDeTantosDiasInt") Integer haMaisDeTantosDiasInt,
			   														@QueryParam("haMaisDeTantosDiasExt") Integer haMaisDeTantosDiasExt) {
		Integer matricula = 4566;

		if (matricula == null) {// testar
			throw new RestException("Parametro matricula informado nulo.");
		}

		TarefaPresenter json = new TarefaPresenter();
		json.setCodigo("1");
		json.setNome("teste");
		System.out.println(haMaisDeTantosDiasInt + " - " + haMaisDeTantosDiasExt);

		return json;
	}
	
	@POST
	@Path("/listarResumidosSobResponsabilidade2")
	@Consumes("application/json")
	@Produces("text/json;charset=UTF-8")
//	public Response createProductInJSON(TarefaPresenter product) {
	public TarefaPresenter listarResumidosSobResponsabilidade(TarefaPresenter presenter) {
		init();
		
		this.tarefaFacade = getFacade();
 
//		String result = "TarefaPresenter created : " + presenter;
		System.out.println("original = " + ToStringBuilder.reflectionToString(presenter, ToStringStyle.MULTI_LINE_STYLE));		
		presenter.setNome("Alterado");		
		presenter.setDataInicio("15/06/2013");
		presenter.setDataTermino("17/06/2013");
		presenter.setDataEntrega("19/06/2013");
		presenter.setCodigo("6");
		
//		return Response.status(201).entity(result).build();
		System.out.println("modificado = " + ToStringBuilder.reflectionToString(presenter, ToStringStyle.MULTI_LINE_STYLE));
		
		this.tarefaFacade.salvar(presenter);
		
		
		return presenter;
 
	}

	public void init() {
		SpringManager.getInstancia().setServletContext(request.getSession().getServletContext());
	}

	public TarefaFacade getFacade() {
		return SpringManager.getInstancia().getBean("tarefaFacade");
	}

}
