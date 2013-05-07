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
	
	public void insert(final T entity);
	
	public void update(final T entity);	
	
	public void delete(final T entity);
	
	public void flush();
	
	public T recuperarPorChave(Long id);
}
