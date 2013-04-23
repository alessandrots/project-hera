package com.outline.org.app.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;


import com.google.gson.reflect.TypeToken;
import com.outline.org.app.domain.Tarefa;
import com.outline.org.app.facade.TarefaFacade;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.app.presenter.TesteModel;
import com.outline.org.util.EngineJson;

/**
 * TODO
 * Pensar em transferir toda a regra negocial aqui para uma classe BO, ou um facade. O serviço vai ser só porta de entrada.
 * TODO
 * @author alessandrots
 *
 */
public class TarefaService extends ServiceImpl<TarefaPresenter> {
	//TODO - via spring 
	private TarefaFacade tarefaFacade;
	

	public TarefaService() {
		tarefaFacade = new TarefaFacade();
	}

	@Override
	public void execute(String acao) {
		try {			
			//set a classe presenter
			setClasse(TarefaPresenter.class);
			
			//setarListaParametroseta a lista de todos os métodos desta classe
			setListaMetodosInstanciaFilha(this.getClass().getMethods());
			
			//faz chamada na classe pai e cria o objeto Presenter relacionado com a classe Servico e de acordo com o JSon passado.
			super.execute(acao);
			
			//recupera o método negocial 
			Method metodoAserExecutado = getMetodoParaExecutar(acao);
			
			//invoca a sua execução com a passagem de parâmetros
			Object objeto = metodoAserExecutado.invoke(this, getParametros());
			
			if (objeto != null){
				//se for uma coleção então transforma a resposta em JSon
				if (Collection.class.isAssignableFrom(objeto.getClass())) {
					String jsonRetorno = EngineJson.getInstancia().serializarColecao((List)objeto);
					setResposta(jsonRetorno);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@ClasseNegocial(negocial=true)
	public void add() {
		System.out.println(" >>>>> add ... ");
		this.tarefaFacade.add();
	}
	
	@ClasseNegocial(negocial=true)
	public void add(TarefaPresenter presenter) {
		this.tarefaFacade.add(presenter);
		
	}
	
	@ClasseNegocial(negocial=true)
	public List<Tarefa> recuperarTodos() {
		return this.tarefaFacade.recuperarTodos();
	}
	
	@ClasseNegocial(negocial=true)
	public List<Tarefa> recuperarPorChave(Integer chave) {
		return this.tarefaFacade.recuperarPorChave(chave);
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste() {
		return this.tarefaFacade.recuperarListaTeste();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste2() {
		return this.tarefaFacade.recuperarListaTeste2();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste2(TarefaPresenter pPresenter) {
		return this.tarefaFacade.recuperarListaTeste2(pPresenter);
	}
	
	@ClasseNegocial(negocial=true)
	public List<TarefaPresenter> recuperarTodasTarefas() {
		return this.tarefaFacade.recuperarTodasTarefas();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TarefaPresenter> recuperarTarefaPorNome(TarefaPresenter pPresenter) {
		return this.tarefaFacade.recuperarTarefaPorNome(pPresenter);
	}
	
	
	@ClasseNegocial(negocial=true)
	public List<TarefaPresenter> recuperarTarefaPorWinTarefa(TarefaPresenter pPresenter) {
		return this.tarefaFacade.recuperarTarefaPorWinTarefa(pPresenter);
	}
	
	@ClasseNegocial(negocial=true)
	public void sincronizarTarefas(TarefaPresenter pPresenter) {
		this.tarefaFacade.sincronizarTarefas(pPresenter);
	}

	@Override
	public void criarListaDeParametrosParaExecucao(String acao) {
		if (getPresenter() != null) {
			Object[] parametrosMetodo = new Object[1];
			
			parametrosMetodo[0] = getPresenter();
			
			//TODO fazer tratamento para cada ação.			
			setParametros(parametrosMetodo);
		}
	}

	@Override
	public Type getTypeToken() {
		return new TypeToken<TarefaPresenter>() {}.getType();
	}
	
}
