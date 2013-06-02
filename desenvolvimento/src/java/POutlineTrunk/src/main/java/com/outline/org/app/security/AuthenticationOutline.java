package com.outline.org.app.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.outline.org.app.domain.Usuario;

public class AuthenticationOutline implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 701987664901045323L;
	
	private final Usuario usuario;
	private boolean autenticado;
	
	public AuthenticationOutline(Usuario usuario) {
		this.usuario = usuario;
//		this.permissoes = permissoes;
	}

	@Override
	public String getName() {
		return usuario != null ? usuario.getHashSenha() : null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		return usuario != null ? usuario.getLogin() : null;
	}

	@Override
	public Object getDetails() {
		return usuario;
	}

	@Override
	public Object getPrincipal() {
		return usuario != null ? usuario.getLogin() : null;
	}

	@Override
	public boolean isAuthenticated() {
		return this.autenticado;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		this.autenticado = isAuthenticated();
	}

}
