package com.outline.org.core.db;

//package com.outline.org.app.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;


/**
 * Representação básica do Hibernate.
 * 
 * http://tutorials.jenkov.com/java-generics/classes.html
 * 
 * >>>> http://stackoverflow.com/questions/10298483/spring-and-hibernate-no-session-found-for-current-thread (este aqui resolveu o problema):
 * A explicação tá aqui:
 * - But if i start Transaction in DAO layer, i am getting two sessions opened exception or no session bound... becoz in service layer if i call two methods
 * those two methods are using different sessions right.. then how to solve this.. – Ramesh K
 *  
 * - No ! If you have transaction at service level, then you have more control over multiple DAO calls. Consider a scenario where you want transaction support 
 * over multiple DAOs which depend on each other, in that case the service is the right place to make that decision. 
 * If you have only one transaction mapped to DAO then you can very well do it easily in service layer. Its just gives more flexibility to play around and 
 * over that I don't believe DAOs to be linked with each other making them tightly coupled. –
 * 
 * http://stackoverflow.com/questions/9053095/why-this-no-session-found-for-current-thread-exceptions-in-spring-3-1-and-hibern
 * 
 * http://stackoverflow.com/questions/8993318/what-is-the-right-way-to-use-spring-mvc-with-hibernate-in-dao-sevice-layer-arch
 * 
 * http://stackoverflow.com/questions/10807754/no-session-found-for-current-thread-error-in-dao-when-marking-service-class-as
 * 
 * @author alessandrots
 *
 */
@Service
public abstract class HibernateDAOImpl<T> implements IDao<T> {

	@Resource(name = "sessionFactory")
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
