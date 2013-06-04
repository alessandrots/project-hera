package com.outline.org.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="negocio.tb_usuario")
public class Usuario implements Comparable<Tarefa>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874661855786721888L;

	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;

	@NotNull
	@Column(name="snome", nullable=false, length=100)
	private String nome;	

	@NotNull
	@Column(name="susername", nullable=false, length=20)
	private String login;

	@NotNull
	@Column(name="semail", nullable=false, length=100)
	private String email;

	@NotNull
	@Column(name="spassword", nullable=false, length=128)
	private String hashSenha;
	
	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the hashSenha
	 */
	public String getHashSenha() {
		return hashSenha;
	}

	/**
	 * @param hashSenha the hashSenha to set
	 */
	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}	

	@Override
	public int compareTo(Tarefa o) {
		if (o instanceof Tarefa) {
			return getNome().compareTo(o.getNome());
		} else {
			return 0;
		}
	}
}
