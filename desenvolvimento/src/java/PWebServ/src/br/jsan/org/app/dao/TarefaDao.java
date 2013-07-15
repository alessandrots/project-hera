package br.jsan.org.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.jsan.org.app.domain.Tarefa;
import br.jsan.org.core.db.DaoImpl;

//TODO colocar generics
public class TarefaDao extends DaoImpl {

	public List<Tarefa> obterTodasTarefas() {
		List<Tarefa> lista 	= null;
		ResultSet rs 			= null;
		PreparedStatement ps 	= null;
		boolean bPrimeiraVez 	= true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("Select iCodigo, sNome, dataInicio, dataFim from Tb_Tarefa");
		
		try {
			ps = getConexao().prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				if (bPrimeiraVez){
					lista = new ArrayList<Tarefa>();
					bPrimeiraVez = false;
				}
				
				Tarefa tarefa = new Tarefa();
				tarefa.setCodigo(rs.getInt("iCodigo"));
				tarefa.setNome(rs.getString("snome"));
				tarefa.setDataInicio(rs.getTimestamp("dataInicio"));
				tarefa.setDataEntrega(rs.getTimestamp("dataFim"));
				
				lista.add(tarefa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (rs != null){
					rs.close();
				}
				if (ps != null){
					ps.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lista;
	}

}
