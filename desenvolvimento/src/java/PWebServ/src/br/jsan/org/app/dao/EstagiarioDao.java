package br.jsan.org.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.jsan.org.app.domain.Estagiario;
import br.jsan.org.core.db.DaoImpl;

public class EstagiarioDao extends DaoImpl {	
	
	public EstagiarioDao() {
		super();
	}

	public List<Estagiario> obterTodosEstagiarios(){
		List<Estagiario> lista 	= null;
		ResultSet rs 			= null;
		PreparedStatement ps 	= null;
		boolean bPrimeiraVez 	= true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("Select iCodigo, sNome, cAtivo from Tb_Estagiario");
		
		try {
			ps = getConexao().prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				if (bPrimeiraVez){
					lista = new ArrayList<Estagiario>();
					bPrimeiraVez = false;
				}
				
				Estagiario estagiario = new Estagiario();
				estagiario.setCodigo(rs.getInt("iCodigo"));
				estagiario.setNome(rs.getString("snome"));
				estagiario.setAtivo(rs.getString("cAtivo").charAt(0));
				
				lista.add(estagiario);
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
