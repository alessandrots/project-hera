package com.outline.org.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.outline.org.app.dao.UsuarioDAO;
import com.outline.org.app.domain.Usuario;

public class AuthenticationProviderImpl implements AuthenticationProvider  {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Transactional
	public Authentication authenticate(Authentication auth)	throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
		String username = token.getName();
		String senha    = token.getCredentials() != null ? token.getCredentials().toString() : null;
		
		Usuario usuario = getUsuarioDAO().getUsuario(username, senha);
		
		if (usuario == null) {
			return null;
		}
		
		/* TODO
		List<PermissaoUsuario> permissoes = getDaoPermissao().getPermissoesUsuario(usuario);
		SFAuthentication resultado = new SFAuthentication(usuario, permissoes);
		resultado.setAuthenticated(usuario != null);
		*/
		
		AuthenticationOutline resultado = new AuthenticationOutline(usuario);
		return resultado;
	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	/**
	 * @return the usuarioDAO
	 */
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	/**
	 * @param usuarioDAO the usuarioDAO to set
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
}
