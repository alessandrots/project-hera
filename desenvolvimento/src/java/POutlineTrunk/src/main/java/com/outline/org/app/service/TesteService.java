package com.outline.org.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.outline.org.app.facade.TesteFacade;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.app.presenter.TesteModel;

/**
 * 
 * @author alessandrots
 *
 */

@Component("cadTeste")
public class TesteService {

	@Autowired	 
	private TesteFacade testeFacade;
	
	@ClasseNegocial(negocial=true)
	public void add() {
		System.out.println(" >>>>> add ... ");
		this.testeFacade.add();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste() {
		return this.testeFacade.recuperarListaTeste();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste2() {
		return this.testeFacade.recuperarListaTeste2();
	}
	
	@ClasseNegocial(negocial=true)
	public List<TesteModel> recuperarListaTeste2(TarefaPresenter pPresenter) {
		return this.testeFacade.recuperarListaTeste2(pPresenter);
	}
	
	@ClasseNegocial(negocial=true)
	public List<TarefaPresenter> recuperarTodasTarefas() {
		return this.testeFacade.recuperarTodasTarefas();
	}
}
