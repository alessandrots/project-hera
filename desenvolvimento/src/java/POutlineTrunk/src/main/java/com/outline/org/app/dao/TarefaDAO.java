package com.outline.org.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.outline.org.app.domain.Tarefa;
import com.outline.org.core.db.HibernateDAOImpl;
import com.outline.org.core.db.IDao;

@Repository("tarefaDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class TarefaDAO extends HibernateDAOImpl<Tarefa> implements IDao<Tarefa> {
	
	protected Class getClazz() {
		return Tarefa.class;
	}

//	public List<Tarefa> obterTodasTarefas() {
//		List<Tarefa> lista 	= null;
//		ResultSet rs 			= null;
//		PreparedStatement ps 	= null;
//		boolean bPrimeiraVez 	= true;
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("Select iCodigo, sNome, dataInicio, dataFim from Tb_Tarefa");
//		
//		try {
//			ps = getConexao().prepareStatement(sql.toString());
//			rs = ps.executeQuery();
//			
//			while (rs.next()) {
//				if (bPrimeiraVez){
//					lista = new ArrayList<Tarefa>();
//					bPrimeiraVez = false;||
//				}
//				
//				Tarefa tarefa = new Tarefa();
//				tarefa.setCodigo(rs.getInt("iCodigo"));
//				tarefa.setNome(rs.getString("snome"));
//				tarefa.setDataInicio(rs.getTimestamp("dataInicio"));
//				tarefa.setDataEntrega(rs.getTimestamp("dataFim"));
//				
//				lista.add(tarefa);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {				
//				if (rs != null){
//					rs.close();
//				}
//				if (ps != null){
//					ps.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return lista;
//	}

}
