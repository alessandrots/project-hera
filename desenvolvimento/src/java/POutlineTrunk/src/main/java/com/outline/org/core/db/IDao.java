package com.outline.org.core.db;

import java.util.List;

/**
 * interface básica do padrão de projeto DAO.
 * 
 * @author alessandrots
 *
 * @param <T>
 */
public interface IDao<T> {

	public List<T> recuperarTodos();
	
	public List<T> recuperarPaginado(int offset, int max);
	
	public void persistir(T objeto);
	
	public void excluir(T objeto);
	
	public T recuperarPorChave(Long id);
}
