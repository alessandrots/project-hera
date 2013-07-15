package com.outline.org.app.security;

import org.springframework.security.core.GrantedAuthority;


public class PermissaoUsuario implements GrantedAuthority, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1927916878367325054L;
	
	protected long id;
	private String role;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getAuthority() {
		return role;
	}
	
	
}
