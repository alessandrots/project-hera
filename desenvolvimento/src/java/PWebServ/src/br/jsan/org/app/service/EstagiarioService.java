package br.jsan.org.app.service;

import java.lang.reflect.Method;
import java.util.List;

import br.jsan.org.app.dao.EstagiarioDao;
import br.jsan.org.app.domain.Estagiario;

public class EstagiarioService extends ServiceImpl {
	
	private EstagiarioDao estagiarioDao;
	
	private Method methodToExecute;

	public EstagiarioService() {
		super();
		estagiarioDao = new EstagiarioDao();
	}

	public List<Estagiario> recuperarTodos() {
		return estagiarioDao.obterTodosEstagiarios();
	}

	@Override
	protected Method getMetodoParaExecutar(String acao) {
		return this.methodToExecute;
	}

	@Override
	public void criarListaDeParametrosParaExecucao(String acao) {
		// TODO Auto-generated method stub
		
	}
}
