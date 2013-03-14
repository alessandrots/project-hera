package br.jsan.org.app.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.jsan.org.app.dao.TarefaDao;
import br.jsan.org.app.domain.Tarefa;
import br.jsan.org.app.model.TesteModel;
import br.jsan.org.app.presenter.TarefaPresenter;
import br.jsan.org.core.EngineJson;

import com.google.gson.reflect.TypeToken;

public class TarefaService extends ServiceImpl<TarefaPresenter> {
	//TODO - via spring 
	private TarefaDao tarefaDao;

	public TarefaService() {
		tarefaDao = new TarefaDao();
	}

	@Override
	public void execute(String acao) {
		try {
			//set a classe presenter
			setClasse(TarefaPresenter.class);
			
			//ssetarListaParametroseta a lista de todos os métodos desta classe
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
//					System.out.println(" resposta = " + getResposta());
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
	}
	
	@ClasseNegocial(negocial=true)
	public void add(TarefaPresenter presenter) {
		if (presenter != null){
			System.out.println(" >>>>> add ... " + presenter.getCodigo());
		}
	}
	
	@ClasseNegocial(negocial=true)
	public List<Tarefa> recuperarTodos() {
		return tarefaDao.obterTodasTarefas();
	}
	
	@ClasseNegocial(negocial=true)
	public List<Tarefa> recuperarPorChave(Integer chave) {
		return tarefaDao.obterTodasTarefas();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste() {
		List<TesteModel> listaModel = new ArrayList<TesteModel>();
		
		int cont = 0;
		
		for (int i = 0; i < 10; i++) {
			TesteModel model = new TesteModel();			
			cont = i + 1;
//			model.setId(cont);
			model.setTitle("titulo_" + cont);
			model.setText("texto_" + cont);
			listaModel.add(model);
		}
		
		return listaModel;
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
