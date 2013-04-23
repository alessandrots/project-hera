package com.outline.org.app.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.outline.org.app.dao.TarefaDao;
import com.outline.org.app.domain.Tarefa;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.app.presenter.TesteModel;
import com.outline.org.util.Utils;

@Component
public class TarefaFacade {

	//TODO - mudar para o domain Tarefa
	private List<TarefaPresenter> lista;
	
	private TarefaDao tarefaDao;
	
	public TarefaFacade() {
		super();
		tarefaDao = new TarefaDao();
	}


	public void add() {
		System.out.println(" >>>>> add ... ");
	}
	
	
	public void add(TarefaPresenter presenter) {
		if (presenter != null && presenter.getIdWinTarefa() != null){
			System.out.println(" >>>>> add ... codigo 		= " + presenter.getCodigo());
			System.out.println(" >>>>> add ... nome   		= " + presenter.getNome());
			System.out.println(" >>>>> add ... idWinTarefa 	= " + presenter.getIdWinTarefa());
			
			//listagem para guardar em memória. TODO			
			if (this.lista == null){
				this.lista = new ArrayList<TarefaPresenter>();
			}
			this.lista.add(presenter);
		}
		
	}
	
	
	public List<Tarefa> recuperarTodos() {
		return tarefaDao.obterTodasTarefas();
	}
	
	
	public List<Tarefa> recuperarPorChave(Integer chave) {
		return tarefaDao.obterTodasTarefas();
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
	
	
	public List<TarefaPresenter> recuperarTarefaPorNome(TarefaPresenter pPresenter) {
		List<TarefaPresenter> listaRetorno = null;
		
		if (pPresenter != null){
			if (this.lista != null && this.lista.size() > 0) {
				listaRetorno = new ArrayList<TarefaPresenter>();
				
				for (TarefaPresenter tarefaPresenter : this.lista) {
					if (pPresenter.getNome().equalsIgnoreCase(tarefaPresenter.getNome())) {
						listaRetorno.add(tarefaPresenter);
					}
				}
			}
		} else {
			listaRetorno = this.lista;
		}
		
		return listaRetorno;
	}
	
	
	
	public List<TarefaPresenter> recuperarTarefaPorWinTarefa(TarefaPresenter pPresenter) {
		List<TarefaPresenter> listaRetorno = null;
		
		if (pPresenter != null){
			if (this.lista != null && this.lista.size() > 0) {
				listaRetorno = new ArrayList<TarefaPresenter>();
				
				for (TarefaPresenter tarefaPresenter : this.lista) {
					if (pPresenter.getIdWinTarefa().equalsIgnoreCase(tarefaPresenter.getIdWinTarefa())) {
						listaRetorno.add(tarefaPresenter);
					}
				}
			}
		} else {
			listaRetorno = this.lista;
		}
		
		return listaRetorno;
	}
	
	
	public void sincronizarTarefas(TarefaPresenter pPresenter) {
		//
		System.out.println(" source = " + pPresenter.getSource());
		TarefaPresenter presenterSource = recuperarTarefaPorWinTarefa(pPresenter.getSource());
		
		System.out.println(" target = " + pPresenter.getTarget());
		TarefaPresenter presenterTarget = recuperarTarefaPorWinTarefa(pPresenter.getTarget());
		
		System.out.println(" TipoRelacionamentoLogico = " + pPresenter.getTipoRelacionamentoLogico());
		
		Integer tipoRelacionamento = Integer.parseInt(pPresenter.getTipoRelacionamentoLogico());
		
		switch (tipoRelacionamento) {
			case 0://Constante.TIPO_RELACIONAMENTO_LOGICO_TI.intValue()
				atualizarRelacionamentoTerminoInicio(presenterSource, presenterTarget);
			break;
			
			case 1://Constante.TIPO_RELACIONAMENTO_LOGICO_II.intValue()
				atualizarRelacionamentoInicioInicio(presenterSource, presenterTarget);
			break;
			
			case 2://Constante.TIPO_RELACIONAMENTO_LOGICO_IT.intValue()
				atualizarRelacionamentoInicioTermino(presenterSource, presenterTarget);
			break;
			
			case 3://Constante.TIPO_RELACIONAMENTO_LOGICO_TT.intValue()
				atualizarRelacionamentoTerminoTermino(presenterSource, presenterTarget);
			break;

			default: //lançar exceção TODO
			break;
		}
		
	}
	
	
	private void atualizarRelacionamentoTerminoTermino(TarefaPresenter presenterSource, TarefaPresenter presenterTarget) {
		// TODO Auto-generated method stub		
	}

	
	private void atualizarRelacionamentoInicioTermino(TarefaPresenter presenterSource, TarefaPresenter presenterTarget) {
		// TODO Auto-generated method stub		
	}

	
	private void atualizarRelacionamentoInicioInicio(TarefaPresenter presenterSource, TarefaPresenter presenterTarget) {
		// TODO Auto-generated method stub		
	}

	/**
	 * Calculando as datas após a ligação entre uma Tarefa de Origem e de Destino.
	 * 
	 * @param presenterTarefaOrigem
	 * @param presenterTarefaDestino
	 */
	private void atualizarRelacionamentoTerminoInicio(TarefaPresenter presenterTarefaOrigem, TarefaPresenter presenterTarefaDestino) {
		//CALCULANDO AS DATAS DA TAREFA DE ORIGEM
		Calendar calDataInicioTarefaOrigem = GregorianCalendar.getInstance();
		String[] dataInicioPartes = Utils.getInstance().quebrarDataEmPartes(presenterTarefaOrigem.getDataInicio());
		
		//Recuperando a data de início e duração na Tarefa de Origem
		int duracaoTarefaOrigemDataInicio = Integer.parseInt(presenterTarefaOrigem.getDuracao());
		calDataInicioTarefaOrigem.set(Integer.parseInt(dataInicioPartes[2]), 
									  Integer.parseInt(dataInicioPartes[1]), 
									  Integer.parseInt(dataInicioPartes[0]), 
									  0, 0, 0);
		Date dataInicioOrigem = calDataInicioTarefaOrigem.getTime();
		
		//Criando a data fim baseado na data início
		Date dataTerminoOrigem = new Date(dataInicioOrigem.getTime()); 
		Calendar calDataTerminoTarefaOrigem = GregorianCalendar.getInstance();
		calDataTerminoTarefaOrigem.setTime(dataTerminoOrigem);
		
		//Recalculando a data fim da origem (a data fim = dataInicio + duração -1)		
		calDataTerminoTarefaOrigem.add(Calendar.DATE, duracaoTarefaOrigemDataInicio-1);		
		
		//Atualizando as datas na Tarefa de Origem
		presenterTarefaOrigem.setDataTermino(Utils.getInstance().transformarDateToString(calDataTerminoTarefaOrigem.getTime()));
		
		
		//CALCULANDO AS DATAS DA TAREFA DE DESTINO
		
		//A data de inicio da tarefa de destino é (dataInicioOrigem + duracaoTarefaOrigem)
		Calendar calDataInicioTarefaDestino = GregorianCalendar.getInstance();
		calDataInicioTarefaDestino.setTime(dataInicioOrigem);		
		calDataInicioTarefaDestino.add(Calendar.DATE, duracaoTarefaOrigemDataInicio);
		
		int duracaoTarefaDestinoDataInicio = Integer.parseInt(presenterTarefaDestino.getDuracao());
		Date dataInicioDestino = calDataInicioTarefaDestino.getTime();
		
		//Criando a data fim baseado na data início
		Calendar calDataTerminoTarefaDestino = GregorianCalendar.getInstance();
		Date dataTerminoDestino = new Date(dataInicioDestino.getTime()); 
		calDataTerminoTarefaDestino.setTime(dataTerminoDestino);
		
		//Recalculando a data fim da origem (a data fim = dataInicio + duração -1)		
		calDataTerminoTarefaDestino.add(Calendar.DATE, duracaoTarefaDestinoDataInicio-1);		
		
		//Atualizando as datas (inicio e fim) na Tarefa de Destino
		presenterTarefaDestino.setDataInicio(Utils.getInstance().transformarDateToString(calDataInicioTarefaDestino.getTime()));
		presenterTarefaDestino.setDataTermino(Utils.getInstance().transformarDateToString(calDataTerminoTarefaDestino.getTime()));		
	}
	
	private TarefaPresenter recuperarTarefaPorWinTarefa(String idWinTarefa) {
		TarefaPresenter presenter = null;
		
		if (this.lista != null && this.lista.size() > 0){
			loop:for (TarefaPresenter presenterAtual : this.lista) {
				if (presenterAtual.getIdWinTarefa().equalsIgnoreCase(idWinTarefa)) {
					presenter = presenterAtual;
					break loop;
				}
			}
		}
		
		return presenter;
	}
}
