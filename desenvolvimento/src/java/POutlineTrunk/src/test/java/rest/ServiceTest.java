package rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.outline.org.app.facade.TarefaFacade;
import com.outline.org.app.presenter.TarefaPresenter;
import com.outline.org.app.rest.ServicoRest;

/**
 * http://docs.mockito.googlecode.com/hg/org/mockito/Mockito.html
 * 
 * @author alessandro.santos
 *
 */
public class ServiceTest {
	
	private TarefaFacade tarefaFacade;
	
	private ServicoRest servicoTestRest;

	@Before
	public void setup() {
		tarefaFacade = Mockito.mock(TarefaFacade.class);
		
		servicoTestRest = new ServicoRest(){
			@Override
			public void init() {
				//
			}
			
			@Override
			public TarefaFacade getFacade() {
				return tarefaFacade;
			}
		};
	}
	
	@Test
	public void listarResumidosSobResponsabilidade() {
		TarefaPresenter p = new TarefaPresenter();
		p.setCodigo("6");
		p.setNome("Teste");
		
		TarefaPresenter p2 = new TarefaPresenter();
		p.setCodigo("6");
		p.setNome("Alterado");
		
//		Mockito.when(servicoTestRest.listarResumidosSobResponsabilidade(p)).thenReturn(p2);
		
		TarefaPresenter retorno = servicoTestRest.listarResumidosSobResponsabilidade(p);
		
		Mockito.verify(tarefaFacade, Mockito.atLeastOnce()).salvar(p);
	}
}
