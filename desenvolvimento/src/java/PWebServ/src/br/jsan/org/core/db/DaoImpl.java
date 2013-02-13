package br.jsan.org.core.db;

import java.sql.Connection;

/**
 * TODO
 * http://tutorials.jenkov.com/java-generics/classes.html
 * @author alessandrots
 *
 */
public class DaoImpl implements Dao {
	private Connection conn;
	
	public DaoImpl() {
		super();
		//gerando a instância do banco
		EngineDB.getInstancia();
		
		//setando a conexão
		setConexao(EngineDB.getConn());
	}

	@Override
	public void setConexao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Connection getConexao() {
		return this.conn;
	}

}
