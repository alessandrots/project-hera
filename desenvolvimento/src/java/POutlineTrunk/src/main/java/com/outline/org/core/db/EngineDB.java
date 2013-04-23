package com.outline.org.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.outline.org.app.domain.Teste;

public class EngineDB {
	
	private static Connection conn;
	private static EngineDB engineDB;
	
	//Parâmetros de Conexão
	private String url;
	private String user;
	private String pwd;
	private String driver;
	
	public static EngineDB getInstancia(){
		if (engineDB == null){
			engineDB = new EngineDB();
		}
		
		return engineDB;
	}

	/**
	 *
	 * driver  = "org.postgresql.Driver";
	 * url 	= "jdbc:postgresql://localhost:5432/outline";
	 * user 	= "postgres";
	 * pwd 	= "jv121206";
	 * 
	 * driver  = "com.mysql.jdbc.Driver";
	 * url = "jdbc:mysql://128.1.30.58/dbsroii";
	 * user 	= "root
	 * pwd 	= "";//ou jv121206
	 * 
	 */
	private EngineDB() {
		this("jdbc:postgresql://localhost:5432/outline", "outline", "jv121206", "org.postgresql.Driver");
	}

	private EngineDB(String url, String user, String pwd, String driver) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.driver = driver;		
		gerarConexao();
	}

	protected void gerarConexao() {
		try {
			Class.forName(this.driver);
			conn = DriverManager.getConnection(this.url, this.user, this.pwd);
			conn.setAutoCommit(false);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}
	
	/**
	 * Funcionando OK.
	 * 
	 * @return List<Teste>
	 */
	public static List<Teste> recuperarTodosTesteTmp() {
		Connection conexao 		= getConn();
		List<Teste> lista 		= null;
		boolean bPrimeiraVez 	= true;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select icodigo, snome from TB_Teste ");
			PreparedStatement ps = conexao.prepareStatement(sql.toString());			
			//setando filtros
			
			ResultSet rs = ps.executeQuery();
			
			//Montando a lista de objetos
			while (rs.next()){
				if (bPrimeiraVez){
					lista = new ArrayList<Teste>();
					bPrimeiraVez = false;
				}
				
				Teste t = new Teste();
				t.setCodigo(rs.getInt("icodigo"));
				t.setNome(rs.getString("snome"));				
				lista.add(t);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	public static List<Teste> recuperarTodasTarefas() {
		Connection conexao 		= getConn();
		List<Teste> lista 		= null;
		boolean bPrimeiraVez 	= true;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select icodigo, snome from negocio.tb_tarefa ");
			PreparedStatement ps = conexao.prepareStatement(sql.toString());			
			//setando filtros
			
			ResultSet rs = ps.executeQuery();
			
			//Montando a lista de objetos
			while (rs.next()){
				if (bPrimeiraVez){
					lista = new ArrayList<Teste>();
					bPrimeiraVez = false;
				}
				
				Teste t = new Teste();
				t.setCodigo(rs.getInt("icodigo"));
				t.setNome(rs.getString("snome"));				
				lista.add(t);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public static void main(String[] args) {
		List<Teste> lista = null;
		
		EngineDB.getInstancia();
		
//		EngineDB.recuperarTodosTesteTmp();//OK
		
		lista = EngineDB.recuperarTodasTarefas();
		
		if (lista != null && lista.size() > 0){
			for (Teste teste : lista) {
				System.out.println(" codigo = " + teste.getCodigo() + " => nome = " + teste.getNome());
			}
		}
	}
	
}
