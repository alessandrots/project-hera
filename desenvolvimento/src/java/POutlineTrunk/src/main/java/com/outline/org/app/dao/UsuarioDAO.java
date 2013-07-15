package com.outline.org.app.dao;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.outline.org.app.domain.Usuario;
import com.outline.org.core.db.HibernateDAOImpl;

@Repository
public class UsuarioDAO  extends HibernateDAOImpl<Usuario> {

	@Override
	protected Class<Usuario> getEntityClass() {
		return Usuario.class;
	}
	
	public Usuario getUsuario(String login, String senha) {
		Query query = getSession().createQuery("from Usuario usr where usr.login = ? and usr.hashSenha = ?");
		query.setString(0, login);
		query.setString(1, DigestUtils.sha256Hex(senha));
		return (Usuario) query.uniqueResult();				   
	}

	public Usuario getUsuario(String login) {
		Query query = getSession().createQuery("from Usuario usr where usr.login = ?");
		query.setString(0, login);
		return (Usuario) query.uniqueResult();
	}
}
