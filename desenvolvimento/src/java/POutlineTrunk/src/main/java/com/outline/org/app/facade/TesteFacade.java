package com.outline.org.app.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.app.presenter.TesteModel;

@Component("testeFacade")
public class TesteFacade {

	//TODO - mudar para o domain Tarefa
	private List<TarefaPresenter> lista;	
	
	public void add() {
		System.out.println("**********************");
	}
	
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
	
	
	public List<TesteModel> recuperarListaTeste2() {
		List<TesteModel> listaModel = new ArrayList<TesteModel>();
		
		int cont = 0;
		
		for (int i = 0; i < 3; i++) {
			TesteModel model = new TesteModel();			
			cont = i + 1;
			model.setTitle("Alessandro_" + cont);
			model.setText("AnaClaudia_" + cont);
			listaModel.add(model);
		}
		
		return listaModel;
	}
	
	
	public List<TesteModel> recuperarListaTeste2(TarefaPresenter pPresenter) {
		List<TesteModel> listaModel = new ArrayList<TesteModel>();
		System.out.println("executando o método com parâmetro");
		
		int cont = 0;
		
		for (int i = 0; i < 3; i++) {
			TesteModel model = new TesteModel();			
			cont = i + 1;			
			model.setTitle("Alessandro_" + cont);
			model.setText("AnaClaudia_" + cont);
			
			if (pPresenter!= null && pPresenter.getNome().equals("Alessandro_" + cont)) {
				listaModel.add(model);
			}
			
		}
		
		return listaModel;
	}
	
	
	public List<TarefaPresenter> recuperarTodasTarefas() {
		List<TarefaPresenter> listaRetorno = null;
		
		listaRetorno = this.lista;
		
		return listaRetorno;
	}
}
