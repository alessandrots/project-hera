package com.outline.org.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="negocio.tb_recurso")
public class Recurso implements Comparable<Recurso>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7889480535750536179L;
	
	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;
	
	@ManyToOne @JoinColumn(name="iusuario", nullable=false)
	private Usuario usuario;
	
	@ManyToOne @JoinColumn(name="ipacotetrabalho", nullable=false)
	private PacoteTrabalho pacoteTrabalho;
	
	@ManyToOne @JoinColumn(name="itiporecurso", nullable=false)
	private TipoRecurso tipoRecurso;	
	
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the pacoteTrabalho
	 */
	public PacoteTrabalho getPacoteTrabalho() {
		return pacoteTrabalho;
	}

	/**
	 * @param pacoteTrabalho the pacoteTrabalho to set
	 */
	public void setPacoteTrabalho(PacoteTrabalho pacoteTrabalho) {
		this.pacoteTrabalho = pacoteTrabalho;
	}

	/**
	 * @return the tipoRecurso
	 */
	public TipoRecurso getTipoRecurso() {
		return tipoRecurso;
	}

	/**
	 * @param tipoRecurso the tipoRecurso to set
	 */
	public void setTipoRecurso(TipoRecurso tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	@Override
	public int compareTo(Recurso o) {
		//TODO
		if (o instanceof Recurso) {
			return (getPacoteTrabalho().getCodigo().compareTo(o.getPacoteTrabalho().getCodigo()));
		} else {
			return 0;
		}
	}

}
