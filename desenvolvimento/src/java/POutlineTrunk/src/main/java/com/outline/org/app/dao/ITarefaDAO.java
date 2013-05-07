package com.outline.org.app.dao;

import java.util.List;

import com.outline.org.app.domain.Tarefa;

public interface ITarefaDAO {

	public List<Tarefa> recuperarPorCodigo(Long codigoTarefa);
}
