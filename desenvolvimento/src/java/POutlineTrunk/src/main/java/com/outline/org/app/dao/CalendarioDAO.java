package com.outline.org.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.outline.org.app.domain.Calendario;
import com.outline.org.core.db.HibernateDAOImpl;

@Repository
public class CalendarioDAO extends HibernateDAOImpl<Calendario> {
	
	@Override
	protected Class<Calendario> getEntityClass() {
		return Calendario.class;
	}
	
	/**
	 * http://docs.jboss.org/hibernate/orm/3.3/reference/pt-BR/html/queryhql.html
	 * 
	 * TODO tem que passar o pacote de trabalho.
	 * 
	 * O pacote de trabalho vai ter um cadastro prévio, bem como os recursos ligados a este pacote de trabalho.
	 * 
	 * @param login
	 * 
	 * @return List<Calendario>
	 */
	public List<Calendario> recuperarCalendarioPorLogin(String login) {
		StringBuilder hql = new StringBuilder();
		
		hql.append(" SELECT cal ");		
		hql.append(" FROM Calendario as cal ");
		hql.append("  INNER JOIN cal.recurso as rec ");
		hql.append("  INNER JOIN rec.usuario as usu ");
		hql.append(" WHERE usu.login = :login");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("login", login);
		
		List<Calendario> listaCalendario = query.list();
		
		return listaCalendario;
	}
	
	/**
	 select
        calendario0_.icodigo as icodigo9_,
        calendario0_.dia_domingo as dia2_9_,
        calendario0_.dia_quarta as dia3_9_,
        calendario0_.dia_quinta as dia4_9_,
        calendario0_.irecurso as irecurso9_,
        calendario0_.dia_sabado as dia5_9_,
        calendario0_.dia_segunda as dia6_9_,
        calendario0_.dia_sexta as dia7_9_,
        calendario0_.dia_terca as dia8_9_ 
    from
        negocio.tb_calendario calendario0_ 
    inner join
        negocio.tb_recurso recurso1_ 
            on calendario0_.irecurso=recurso1_.icodigo 
    inner join
        negocio.tb_usuario usuario2_ 
            on recurso1_.iusuario=usuario2_.icodigo 
    where
        usuario2_.susername=?
        
        
	Hibernate: 
    select
        recurso0_.icodigo as icodigo8_7_,
        recurso0_.ipacotetrabalho as ipacotet2_8_7_,
        recurso0_.itiporecurso as itiporec3_8_7_,
        recurso0_.iusuario as iusuario8_7_,
        pacotetrab1_.icodigo as icodigo4_0_,
        pacotetrab1_.sdescricao as sdescricao4_0_,
        pacotetrab1_.snome as snome4_0_,
        pacotetrab1_.iproduto as iproduto4_0_,
        produto2_.icodigo as icodigo3_1_,
        produto2_.sdescricao as sdescricao3_1_,
        produto2_.ifase as ifase3_1_,
        produto2_.snome as snome3_1_,
        fase3_.icodigo as icodigo2_2_,
        fase3_.sdescricao as sdescricao2_2_,
        fase3_.ieap as ieap2_2_,
        fase3_.snome as snome2_2_,
        eap4_.icodigo as icodigo1_3_,
        eap4_.sdescricao as sdescricao1_3_,
        eap4_.snome as snome1_3_,
        eap4_.iprojeto as iprojeto1_3_,
        projeto5_.icodigo as icodigo0_4_,
        projeto5_.sdescricao as sdescricao0_4_,
        projeto5_.snome as snome0_4_,
        tiporecurs6_.icodigo as icodigo7_5_,
        tiporecurs6_.cativo as cativo7_5_,
        tiporecurs6_.sdescricao as sdescricao7_5_,
        usuario7_.icodigo as icodigo6_6_,
        usuario7_.semail as semail6_6_,
        usuario7_.spassword as spassword6_6_,
        usuario7_.susername as susername6_6_,
        usuario7_.snome as snome6_6_ 
    from
        negocio.tb_recurso recurso0_ 
    inner join
        negocio.tb_pacote_trabalho pacotetrab1_ 
            on recurso0_.ipacotetrabalho=pacotetrab1_.icodigo 
    left outer join
        negocio.tb_produto produto2_ 
            on pacotetrab1_.iproduto=produto2_.icodigo 
    left outer join
        negocio.tb_fase fase3_ 
            on produto2_.ifase=fase3_.icodigo 
    left outer join
        negocio.tb_eap eap4_ 
            on fase3_.ieap=eap4_.icodigo 
    left outer join
        negocio.tb_projeto projeto5_ 
            on eap4_.iprojeto=projeto5_.icodigo 
    inner join
        negocio.tb_tipo_recurso tiporecurs6_ 
            on recurso0_.itiporecurso=tiporecurs6_.icodigo 
    inner join
        negocio.tb_usuario usuario7_ 
            on recurso0_.iusuario=usuario7_.icodigo 
    where
        recurso0_.icodigo=?
 source = window_1001
 
	Hibernate: 
    select
        tarefa0_.icodigo as icodigo5_,
        tarefa0_.data_entrega as data2_5_,
        tarefa0_.data_inicio as data3_5_,
        tarefa0_.data_termino as data4_5_,
        tarefa0_.duracao as duracao5_,
        tarefa0_.idWinTarefa as idWinTar6_5_,
        tarefa0_.snome as snome5_,
        tarefa0_.ipacotetrabalho as ipacotet8_5_ 
    from
        negocio.tb_tarefa tarefa0_ 
    where
        tarefa0_.idWinTarefa=?
 target = window_1002
 
	Hibernate: 
    select
        tarefa0_.icodigo as icodigo5_,
        tarefa0_.data_entrega as data2_5_,
        tarefa0_.data_inicio as data3_5_,
        tarefa0_.data_termino as data4_5_,
        tarefa0_.duracao as duracao5_,
        tarefa0_.idWinTarefa as idWinTar6_5_,
        tarefa0_.snome as snome5_,
        tarefa0_.ipacotetrabalho as ipacotet8_5_ 
    from
        negocio.tb_tarefa tarefa0_ 
    where
        tarefa0_.idWinTarefa=?
	 * 
	 */

}
