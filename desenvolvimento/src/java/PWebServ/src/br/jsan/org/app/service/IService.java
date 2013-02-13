package br.jsan.org.app.service;

public interface IService {

	public void execute(String acao);
	
	public void setParametros(Object[] params);
	
	public Object[] getParametros();
	
	public void setJSon(String json);
	
	public String getJSon();
}
