package br.jsan.org.core.db;

import java.sql.Connection;
import java.sql.DriverManager;

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

	private EngineDB() {
		this("jdbc:postgresql://localhost:5432/dbTeste", "postgres", "123456", "org.postgresql.Driver");
		
//		driver  = "org.postgresql.Driver";
//		url 	= "jdbc:postgresql://localhost:5432/dbTeste";
//		user 	= "postgres";
//		pwd 	= "123456";
		
//		driver  = "com.mysql.jdbc.Driver";
//		url = "jdbc:mysql://128.1.30.58/dbsroii";
//		user 	= "root";
//		pwd 	= "";//ou jv121206
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
//		gerarConexao();
		return conn;
	}
	
}
