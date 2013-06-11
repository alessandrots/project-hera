package com.outline.org.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="negocio.tb_tipo_recurso")
public class TipoRecurso implements Comparable<TipoRecurso>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3875022863154916775L;

	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;
	
	@Column(name="snome", nullable=false, length=80)
	private String nome;
	
	@Column(name="cativo", nullable=false)
	private Boolean ativo;
	
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
	 * @return the ativo
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int compareTo(TipoRecurso o) {
		if (o instanceof TipoRecurso) {
			return getNome().compareTo(o.getNome());
		} else {
			return 0;
		}
	}
}
