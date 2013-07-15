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
@Table(name="negocio.tb_fase")
public class Fase implements Comparable<Fase>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7944356856190906031L;

	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;
	
	@Column(name="snome", nullable=false, length=80)
	private String nome;
	
	@Column(name="sdescricao")
	private String descricao;
	
	@ManyToOne @JoinColumn(name="ieap", nullable=false)
	private Eap eap;
	
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the eap
	 */
	public Eap getEap() {
		return eap;
	}

	/**
	 * @param eap the eap to set
	 */
	public void setEap(Eap eap) {
		this.eap = eap;
	}

	@Override
	public int compareTo(Fase o) {
		if (o instanceof Fase) {
			return getNome().compareTo(o.getNome());
		} else {
			return 0;
		}
	}
}
