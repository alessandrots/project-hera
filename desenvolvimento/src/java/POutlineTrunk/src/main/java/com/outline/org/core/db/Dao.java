package com.outline.org.core.db;

import java.sql.Connection;

public interface Dao {

	public void setConexao(Connection conn);
	public Connection getConexao();
}
