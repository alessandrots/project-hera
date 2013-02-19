package br.jsan.org.app.service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.jsan.org.app.presenter.IPresenter;

import com.google.gson.Gson;

public abstract class ServiceImpl<T> implements IService {
	
	private Object[] parametrosExecucao;
	private String jSonDados;
	private Class<T> classe;
	private Method methodToExecute;
	private Method[] listaMetodosInstancia;
	private List<Method> listaMetodosNegociaisInstancia;
	private IPresenter presenter;
	
	public abstract void criarListaDeParametrosParaExecucao(String acao);
	
	public abstract Type getTypeToken();
	
	@Override
	public void execute(String acao) {
		//Cria o objeto Presenter relacionado com a classe Servico e de acordo com o JSon passado.
		this.presenter = criarPresenterDeAcordoComUrl(getJSon());
		
//		Object[] parametrosMetodo = new Object[1];
		criarListaDeParametrosParaExecucao(acao);
		
		//gera a lista dos métodos que são realmente de negócio
		gerarListaMetodosNegociaisInstanciaFilha();
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

		//carregando o Token na classe filha
		typeObj = getTypeToken();
		
		t = gson.fromJson(dadosRequisicao, typeObj);
		
		return t;
	}

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
	
	private Object[] gerarArrayDeParametros(Object... x) { 
		Object[] arrParametros = null;
		
		if (x != null && x.length > 0){
			arrParametros = x;
		}
		
		return arrParametros;
	}
	
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
	
	
}
