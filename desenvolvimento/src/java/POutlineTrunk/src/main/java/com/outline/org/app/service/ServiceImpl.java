package com.outline.org.app.service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.Gson;
import com.outline.org.app.domain.Usuario;
import com.outline.org.app.presenter.IPresenter;
import com.outline.org.app.security.AuthenticationOutline;

/**
 * 
 * @author alessandrots
 *
 */

public abstract class ServiceImpl<T> implements IService {
	
	private Object[] parametrosExecucao;
	private String jSonDados;
	private Class<T> classe;
	private Method methodToExecute;
	private Method[] listaMetodosInstancia;
	private List<Method> listaMetodosNegociaisInstancia;
	private IPresenter presenter;
	private String resposta;
	private Usuario usuarioAutenticado;
	
	@Autowired
	@Qualifier("outlineProperties")
	private Properties outlineProperties;
	
	public abstract void criarListaDeParametrosParaExecucao(String acao);
	
	public abstract Type getTypeToken();
	
	@Override
	public void execute(String acao) {
		//Limpar o último parâmetro de pesquisa/inserção/atualização
		this.parametrosExecucao = null;
		
		//Cria o objeto Presenter relacionado com a classe Servico e de acordo com o JSon passado.
		//Este json é preenchido na servlet -> servico.setJSon(dados);
		this.presenter = criarPresenterDeAcordoComUrl(getJSon());
		
//		Object[] parametrosMetodo = new Object[1];
		criarListaDeParametrosParaExecucao(acao);
		
		//gera a lista dos métodos que são realmente de negócio
		gerarListaMetodosNegociaisInstanciaFilha();		
		
		//limpando a resposta existente em memória
		setResposta(null);
	}	
	
	/**
	 * Cria o objeto do IPresenter baseado nos dados json.
	 * 
	 * @param dadosRequisicao
	 * @return IPresenter
	 */
	private IPresenter criarPresenterDeAcordoComUrl(String dadosRequisicao) {
		//Criar o gson uma vez só - TODO
		Gson gson 	  = new Gson();
		IPresenter t  = null;
		Type typeObj  = null;

		//Método abstrato - Carregando o respectivo Presenter via TypeToken na  classe filha
		typeObj = getTypeToken();
		
		if (dadosRequisicao != null && !dadosRequisicao.equals("")) {
			t = gson.fromJson(dadosRequisicao, typeObj);
		}
		
		return t;
	}

	public abstract String gerarDadosRequisicaoDefault();

	/**
	 * Retorna o método específico, de acordo com a ação passada da camada de apresentação, que deve ser executado na classe de serviço.
	 * 
	 * @param acao
	 * @return Method
	 */
	protected Method getMetodoParaExecutar(String acao) {
		try {
			loop:for (Method method : this.listaMetodosNegociaisInstancia) {
				if (method.getName().equalsIgnoreCase(acao)){
					//verificando se tem parâmetros
					if (method.getParameterTypes() != null && method.getParameterTypes().length > 0) {
						if (getParametros().length == method.getParameterTypes().length) {
							this.methodToExecute = method;
							break loop;
						}
					} else {
						if (getParametros() == null) {							
							this.methodToExecute = method;
							break loop;
						}
					}
				}
			}
		
			if (this.methodToExecute == null){
				throw new RuntimeException("Não há implementação da funcionalidade chamada via url.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.methodToExecute;
	}	
	
	/**
	 * Cria um ArrayList de métodos negociais a partir da lista de todos os métodos, guardando somentes os que tem o annotation ClasseNegocial
	 */
	protected void gerarListaMetodosNegociaisInstanciaFilha() {
		this.listaMetodosNegociaisInstancia = new ArrayList<Method>();
		
		for (Method method : this.listaMetodosInstancia) {
			ClasseNegocial clNeg = method.getAnnotation(ClasseNegocial.class);
			
			if (clNeg != null && clNeg.negocial()) {
				this.listaMetodosNegociaisInstancia.add(method);
			}
		}
	}
	
//	private Object[] gerarArrayDeParametros(Object... x) { 
//		Object[] arrParametros = null;
//		
//		if (x != null && x.length > 0){
//			arrParametros = x;
//		}
//		
//		return arrParametros;
//	}
	
	public void setClasse(Class<T> classe) {
		this.classe = classe;
	}
	

	@Override
	public void setParametros(Object[] params) {
		this.parametrosExecucao = params;
	}

	@Override
	public Object[] getParametros() {
		return this.parametrosExecucao;
	}
	
	/** 
	 * @see br.jsan.org.app.service.IService#setJSon(java.lang.String)
	 */
	@Override
	public void setJSon(String json) {
		this.jSonDados = json;
	}

	/**
	 * @see br.jsan.org.app.service.IService#getJSon()
	 */
	@Override
	public String getJSon() {
		return this.jSonDados;
	}
	
	/**
	 * Retorna o método negocial a ser executado de acordo com a requisição do usuário.
	 * 
	 * @return Method
	 */
	protected Method getMetodoASerExecutado() {
		return this.methodToExecute;
	}
	
	/**
	 * Seta a lista de métodos da instância filha que serão filtrados posteriormente no método gerarListaMetodosNegociaisInstanciaFilha.
	 * 
	 * @param listaMetodos
	 */
	protected void setListaMetodosInstanciaFilha(Method[] listaMetodos) {
		this.listaMetodosInstancia = listaMetodos;
	}

	/**
	 * @return the listaMetodosNegociaisInstancia
	 */
	public List<Method> getListaMetodosNegociaisInstancia() {
		return listaMetodosNegociaisInstancia;
	}

	/**
	 * @return the presenter
	 */
	public IPresenter getPresenter() {
		return presenter;
	}
	
	@Override
	public String getResposta() {
		return this.resposta;
	}

	@Override
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	public void setOutlineProperties(Properties outlineProperties) {
		this.outlineProperties = outlineProperties;
	}
	
	@Override
	public void setUsuarioAutenticado(AuthenticationOutline auth) {
		if (auth.isAuthenticated()){
			this.usuarioAutenticado = (Usuario)auth.getDetails();
		} else {
			//criar um Usuário Anônimo ou lançar uma exceção por não ter autenticação TODO
		}
	}

	@Override
	public Usuario getUsuarioAutenticado() {
		return this.usuarioAutenticado;
	}
	
}
