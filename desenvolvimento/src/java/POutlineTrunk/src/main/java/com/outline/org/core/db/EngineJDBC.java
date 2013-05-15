package com.outline.org.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EngineJDBC {
	
	private static Connection conn;
	private static EngineJDBC engineDB;
	
	//Parâmetros de Conexão
	private String url;
	private String user;
	private String pwd;
	private String driver;
	
	public static EngineJDBC getInstancia(){
		if (engineDB == null){
			engineDB = new EngineJDBC();
		}
		
		return engineDB;
	}

	/**
	 *
	 * driver  = "org.postgresql.Driver";
	 * url 	= "jdbc:postgresql://localhost:5432/outline";
	 * user 	= "postgres";
	 * pwd 	= "123456";
	 * 
	 * driver  = "org.postgresql.Driver";
	 * url 	= "jdbc:postgresql://localhost:5432/outline";
	 * user 	= "outline";
	 * pwd 	= "outline";
	 * 
	 * driver  = "com.mysql.jdbc.Driver";
	 * url = "jdbc:mysql://128.1.30.58/dbsroii";
	 * user 	= "root
	 * pwd 	= "";//ou jv121206
	 * 
	 */
	private EngineJDBC() {
		this("jdbc:postgresql://127.0.0.1:5432/outline", "outline", "outline", "org.postgresql.Driver");
	}

	private EngineJDBC(String url, String user, String pwd, String driver) {
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
	
	public static void recuperarTodasTarefas() {
		Connection conexao 		= getConn();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select icodigo, snome from negocio.tb_tarefa ");
			PreparedStatement ps = conexao.prepareStatement(sql.toString());			
			//setando filtros
			
			ResultSet rs = ps.executeQuery();
			
			//Montando a lista de objetos
			while (rs.next()){
				System.out.println(" CÓDIGO = " + rs.getInt("icodigo") + " - NOME = " + rs.getString("snome"));
//				t.setCodigo(rs.getInt("icodigo"));
//				t.setNome(rs.getString("snome"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		EngineJDBC.getInstancia();
		
//		EngineDB.recuperarTodosTesteTmp();//OK
		
		EngineJDBC.recuperarTodasTarefas();
	}
}
