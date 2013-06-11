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
@Table(name="negocio.tb_produto")
public class Produto implements Comparable<Produto>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3130512886433688882L;

	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;
	
	@Column(name="snome", nullable=false, length=80)
	private String nome;
	
	@Column(name="sdescricao")
	private String descricao;
	
	@ManyToOne @JoinColumn(name="ifase", nullable=false)
	private Fase fase;
	
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
	 * @return the nomeprojeto
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
	 * @return the fase
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	@Override
	public int compareTo(Produto o) {
		if (o instanceof Produto) {
			return getNome().compareTo(o.getNome());
		} else {
			return 0;
		}
	}
}
