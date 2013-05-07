package com.outline.org.app.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.outline.org.app.domain.Tarefa;
import com.outline.org.core.db.HibernateDAOImpl;

@Repository
public class TarefaDAO extends HibernateDAOImpl<Tarefa> {
	
	protected Class getClazz() {
		return Tarefa.class;
	}

	/**
	 * Métodos customizados.
	 * https://community.jboss.org/wiki/HibernateCoreMigrationGuide40?_sscc=t
	 */		
	public List<Tarefa> recuperarPorCodigo(Long codigoTarefa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT trf.iCodigo as codigo,  trf.sNome as nome ");
		sql.append(" FROM negocio.tb_tarefa trf ");
		sql.append(" WHERE trf.iCodigo = :codigoTarefa ");
		sql.append(" ORDER BY trf.sNome ");

		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("codigoTarefa", codigoTarefa);
		sqlQuery.addScalar("codigo", IntegerType.INSTANCE);
		sqlQuery.addScalar("nome", StringType.INSTANCE);
//		sqlQuery.setResultTransformer(Transformers.aliasToBean(OrgaoTO.class));

		List<Tarefa> listaTarefa = sqlQuery.list();
		
		return listaTarefa;
	}

}
