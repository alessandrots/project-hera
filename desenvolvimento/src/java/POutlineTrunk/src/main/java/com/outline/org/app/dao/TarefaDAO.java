package com.outline.org.app.dao;

import org.springframework.stereotype.Repository;

import com.outline.org.app.domain.Tarefa;
import com.outline.org.core.db.HibernateDAOImpl;
import com.outline.org.core.db.IDao;

@Repository("tarefaDAO")
public class TarefaDAO extends HibernateDAOImpl<Tarefa> implements IDao<Tarefa> {
	
	protected Class getClazz() {
		return Tarefa.class;
	}

	/**
	 * Métodos customizados.
	 */

}
