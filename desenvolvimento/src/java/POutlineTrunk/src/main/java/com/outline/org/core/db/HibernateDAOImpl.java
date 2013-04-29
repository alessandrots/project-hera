package com.outline.org.core.db;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Representação básica do Hibernate.
 * 
 * http://tutorials.jenkov.com/java-generics/classes.html
 * 
 * @author alessandrots
 *
 */

public abstract class HibernateDAOImpl<T> implements IDao<T> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {return sessionFactory;}
	public void setSessionFactory(SessionFactory sf) {sessionFactory = sf;}
	
	protected Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	protected abstract Class getClazz();
	
	
	public void persistir(T objeto) {
		getSession().saveOrUpdate(objeto);
	}
	
	public void excluir(T objeto) {
		getSession().delete(objeto);
	}
	
	public T recuperarPorChave(Long id) {
		return (T) getSession().get(getClazz(), id);
	}
	
	public List<T> recuperarPaginado(int offset, int max) {
		return (List<T>) getSession().createCriteria(getClazz())
						   .setMaxResults(max)
						   .setFirstResult(offset).list();
	}
	
	public List<T> recuperarTodos() {
		return (List<T>) getSession().createCriteria(getClazz()).list();
	}


}
