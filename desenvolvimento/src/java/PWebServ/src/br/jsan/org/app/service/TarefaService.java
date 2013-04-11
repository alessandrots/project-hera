package br.jsan.org.app.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.jsan.org.app.dao.TarefaDao;
import br.jsan.org.app.domain.Tarefa;
import br.jsan.org.app.model.TesteModel;
import br.jsan.org.app.presenter.TarefaPresenter;
import br.jsan.org.core.EngineJson;
import br.jsan.org.core.utils.Utils;

import com.google.gson.reflect.TypeToken;

/**
 * TODO
 * Pensar em transferir toda a regra negocial aqui para uma classe BO, ou um facade. O serviço vai ser só porta de entrada.
 * TODO
 * @author alessandrots
 *
 */
public class TarefaService extends ServiceImpl<TarefaPresenter> {
	//TODO - via spring 
	private TarefaDao tarefaDao;
	
	private List<TarefaPresenter> lista;

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
	
	@ClasseNegocial(negocial=true)
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
	
	@ClasseNegocial(negocial=true)
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
	
	@ClasseNegocial(negocial=true)
	public List<TarefaPresenter> recuperarTodasTarefas() {
		List<TarefaPresenter> listaRetorno = null;
		
		listaRetorno = this.lista;
		
		return listaRetorno;
	}
	
	@ClasseNegocial(negocial=true)
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
	
	
	@ClasseNegocial(negocial=true)
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
	
	@ClasseNegocial(negocial=true)
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
