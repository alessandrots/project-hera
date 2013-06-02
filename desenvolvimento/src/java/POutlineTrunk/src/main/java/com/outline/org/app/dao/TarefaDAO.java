package com.outline.org.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.outline.org.app.domain.Tarefa;
import com.outline.org.core.db.HibernateDAOImpl;

/**
 * 
 * @author alessandrots
 *
 */

@Repository
public class TarefaDAO extends HibernateDAOImpl<Tarefa> {
	
	@Override
	protected Class<Tarefa> getEntityClass() {
		return Tarefa.class;
	}
	
	/**
	 * Métodos customizados.
	 * 
	 * https://community.jboss.org/wiki/HibernateCoreMigrationGuide40?_sscc=t
	 */		
	public List<Tarefa> recuperarPorQualquerParteDoNome(String nomeTarefa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT trf.iCodigo as codigo,  trf.sNome as nome ");
		sql.append(" FROM negocio.tb_tarefa trf ");
		sql.append(" WHERE trf.sNome like '%" + nomeTarefa +"%'");
		sql.append(" ORDER BY trf.sNome ");

		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
//		sqlQuery.setParameter("nomeTarefa", nomeTarefa);
		sqlQuery.addScalar("codigo", LongType.INSTANCE);
		sqlQuery.addScalar("nome", StringType.INSTANCE);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Tarefa.class));

		List<Tarefa> listaTarefa = sqlQuery.list();
		
		return listaTarefa;
	}
	
	/**
	 * Recupera uma tarefa específica pelo campo idWinTarefa.
	 * 
	 * @param idWinTarefa
	 * @return Tarefa
	 */
	public Tarefa recuperarTarefaPorWinTarefa(String idWinTarefa) {
		StringBuilder hql = new StringBuilder();
		hql.append("select d from ");
		hql.append(getEntityName());
		hql.append(" d where d.idWinTarefa = :idWinTarefa ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("idWinTarefa", idWinTarefa);
		
		return (Tarefa) query.uniqueResult();
	}
	
	public List<Tarefa> recuperarTarefaPorNome(String nomeTarefa) {
		StringBuilder hql = new StringBuilder();
		hql.append("select d from ");
		hql.append(getEntityName());
		hql.append(" d where d.idWinTarefa like '%" + nomeTarefa + "%'");
		
		Query query = getSession().createQuery(hql.toString());
		
		List<Tarefa> listaTarefa = query.list();
		
		return listaTarefa;
	}

	

}
