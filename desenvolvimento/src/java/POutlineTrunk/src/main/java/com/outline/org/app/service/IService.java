package com.outline.org.app.service;

import com.outline.org.app.domain.Usuario;
import com.outline.org.app.security.AuthenticationOutline;

public interface IService {

	public void execute(String acao);
	
	public void setParametros(Object[] params);
	
	public Object[] getParametros();
	
	public void setJSon(String json);
	
	public String getJSon();
	
	public String getResposta();
	
	public void setResposta(String resposta);
	
	public void setUsuarioAutenticado(AuthenticationOutline auth);
	
	public Usuario getUsuarioAutenticado();
}
