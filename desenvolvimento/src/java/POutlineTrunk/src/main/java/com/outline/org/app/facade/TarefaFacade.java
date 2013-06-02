package com.outline.org.app.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.outline.org.app.dao.TarefaDAO;
import com.outline.org.app.domain.Tarefa;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.util.Utils;

/**
 * 
 * @author alessandrots
 *
 */
@Component("tarefaFacade")
public class TarefaFacade {

	@Autowired
	private TarefaDAO tarefaDAO;
	
	public TarefaFacade() {
		super();
	}	
	
	@Transactional
	public void salvar(TarefaPresenter presenter) {
		if (presenter != null) {			
			Tarefa tarefa = new Tarefa();
			transformToDomain(presenter, tarefa);
			tarefa.setPacote(1);
			
			//Insert or Update
			if (tarefa.getCodigo() != null){				
				tarefaDAO.insert(tarefa);
			} else {
				tarefaDAO.update(tarefa);
			}
		}
	}	
	
	@Transactional(readOnly=true)
	public List<Tarefa> recuperarTodos() {
		return tarefaDAO.recuperarTodos();
	}
	
	@Transactional(readOnly=true)
	public Tarefa recuperarPorCodigo(Long codigoTarefa) {
		return tarefaDAO.recuperarPorChave(codigoTarefa);
	}
		
	@Transactional(readOnly=true)
	public Tarefa recuperarPorChave(Long chave) {
		return tarefaDAO.recuperarPorChave(chave);
	}
		
	@Transactional(readOnly=true)
	public List<TarefaPresenter> recuperarTarefaPorNome(TarefaPresenter pPresenter) {
		List<TarefaPresenter> listaRetorno = null;
		
		if (pPresenter != null && (pPresenter.getNome() != null && !pPresenter.getNome().equals(""))){
			List<Tarefa> lista = tarefaDAO.recuperarTarefaPorNome(pPresenter.getNome());
			
			if (lista != null && lista.size() > 0) {
				listaRetorno = new ArrayList<TarefaPresenter>();
				
				for (Tarefa t: lista){
					TarefaPresenter presenter = new TarefaPresenter();
					transformToPresenter(t, presenter);
					listaRetorno.add(presenter);
				}
			}
		}
		
		return listaRetorno;
	}
	
	@Transactional(readOnly=true)
	public List<TarefaPresenter> recuperarPorQualquerParteDoNome(TarefaPresenter pPresenter) {
		List<TarefaPresenter> listaRetorno = null;
		
		if (pPresenter != null && (pPresenter.getNome() != null && !pPresenter.getNome().equals(""))){
			List<Tarefa> lista = tarefaDAO.recuperarPorQualquerParteDoNome(pPresenter.getNome());
			
			if (lista != null && lista.size() > 0) {
				listaRetorno = new ArrayList<TarefaPresenter>();
				
				for (Tarefa t: lista){
					TarefaPresenter presenter = new TarefaPresenter();
					transformToPresenter(t, presenter);
					listaRetorno.add(presenter);
				}
			}
		}
		
		return listaRetorno;
	}
	
	@Transactional(readOnly=true)
	public TarefaPresenter recuperarTarefaPorWinTarefa(String idWinTarefa) {
		TarefaPresenter presenter = null;
		
		if (idWinTarefa != null && !idWinTarefa.equals("")){
			Tarefa t  = tarefaDAO.recuperarTarefaPorWinTarefa(idWinTarefa);
			presenter = new TarefaPresenter();
			transformToPresenter(t, presenter);
		}
		
		return presenter;
	}
	
	/**
	 * Método originalmente invocado pela camada de apresentação, por esta razão é público e recebe um Presenter 
	 * como parâmetro.
	 *  
	 * @param pPresenter
	 */
	@Transactional
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
	
	
	/**
	 * gera o domain específico a partir de um presenter.
	 * 
	 * @param presenter
	 * @param tarefa
	 */
	private void transformToDomain(TarefaPresenter presenter, Tarefa tarefa) {
		if (presenter.getDataEntrega() != null && !presenter.getDataEntrega().equals("")){
			tarefa.setDataEntrega(Utils.getInstance().converterDataToDate(presenter.getDataEntrega()));
		}
		
		if (presenter.getDataInicio() != null && !presenter.getDataInicio().equals("")){
			tarefa.setDataInicio(Utils.getInstance().converterDataToDate(presenter.getDataInicio()));
		}
		
		if (presenter.getDataTermino() != null && !presenter.getDataTermino().equals("")){
			tarefa.setDataTermino(Utils.getInstance().converterDataToDate(presenter.getDataTermino()));
		}
		
		if (presenter.getDuracao() != null && !presenter.getDuracao().equals("")){
			tarefa.setDuracao(Integer.parseInt(presenter.getDuracao()));
		}
		
		if (presenter.getIdWinTarefa() != null && !presenter.getIdWinTarefa().equals("")){
			tarefa.setIdWinTarefa(presenter.getIdWinTarefa());
		}
		
		if (presenter.getNome() != null && !presenter.getNome().equals("")){
			tarefa.setNome(presenter.getNome());
		}
		
		if (presenter.getCodigo() != null && !presenter.getCodigo().equals("")){
			tarefa.setCodigo(Long.parseLong(presenter.getCodigo()));
		}
	}
	
	/**
	 * gera o presenter específico a partir de um domain.
	 * 
	 * @param tarefa
	 * @param presenter
	 */
	private void transformToPresenter(Tarefa tarefa, TarefaPresenter presenter) {
		if (tarefa.getDataEntrega() != null && !tarefa.getDataEntrega().equals("")){
			presenter.setDataEntrega(Utils.getInstance().transformarDateToString(tarefa.getDataEntrega()));
		}
		
		if (tarefa.getDataInicio() != null && !tarefa.getDataInicio().equals("")){
			presenter.setDataInicio(Utils.getInstance().transformarDateToString(tarefa.getDataInicio()));
		}
		
		if (tarefa.getDataTermino() != null && !tarefa.getDataTermino().equals("")){
			presenter.setDataTermino(Utils.getInstance().transformarDateToString(tarefa.getDataTermino()));
		}
		
		if (tarefa.getDuracao() != null && !tarefa.getDuracao().equals("")){
			presenter.setDuracao(Integer.toString(tarefa.getDuracao()));
		}
		
		if (tarefa.getIdWinTarefa() != null && !tarefa.getIdWinTarefa().equals("")){
			presenter.setIdWinTarefa(tarefa.getIdWinTarefa());
		}
		
		if (tarefa.getNome() != null && !tarefa.getNome().equals("")){
			presenter.setNome(tarefa.getNome());
		}
		
		if (tarefa.getCodigo() != null && !tarefa.getCodigo().equals("")){
			presenter.setCodigo(Long.toString(tarefa.getCodigo()));
		}
	}
	
	public void setTarefaDAO(TarefaDAO tarefaDao) {
		this.tarefaDAO = tarefaDao;
	}
	
}
