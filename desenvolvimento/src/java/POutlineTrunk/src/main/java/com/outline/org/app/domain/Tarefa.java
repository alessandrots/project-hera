package com.outline.org.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="negocio.tb_tarefa")
public class Tarefa implements Comparable<Tarefa>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5652715197735231887L;
	
	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;
	
	@Column(name="snome", nullable=false, length=80)
	private String nome;	
	
	@NotNull
	@Column(name="duracao", nullable=false)
	private Integer duracao;	
	
	@NotNull
	@Column(name="data_inicio", nullable=false) @Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="data_entrega", nullable=true) @Temporal(TemporalType.TIMESTAMP)
	private Date dataEntrega;
	
	@NotNull
	@Column(name="data_termino", nullable=false) @Temporal(TemporalType.TIMESTAMP)
	private Date dataTermino;
	
	@Column(name="idWinTarefa", nullable=false, length=80)
	private String idWinTarefa;	
	
//	@Column(name="source", nullable=false, length=80)
//	private String source;
//	
//	@Column(name="target", nullable=false, length=80)
//	private String target;
//	
//	@Column(name="itipo_relacionamento", nullable=false)
//	private Integer tipoRelacionamentoLogico;

	
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
	 * @return the duracao
	 */
	public Integer getDuracao() {
		return duracao;
	}


	/**
	 * @param duracao the duracao to set
	 */
	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}


	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}


	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	/**
	 * @return the dataEntrega
	 */
	public Date getDataEntrega() {
		return dataEntrega;
	}


	/**
	 * @param dataEntrega the dataEntrega to set
	 */
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}


	/**
	 * @return the dataTermino
	 */
	public Date getDataTermino() {
		return dataTermino;
	}


	/**
	 * @param dataTermino the dataTermino to set
	 */
	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}


	/**
	 * @return the idWinTarefa
	 */
	public String getIdWinTarefa() {
		return idWinTarefa;
	}


	/**
	 * @param idWinTarefa the idWinTarefa to set
	 */
	public void setIdWinTarefa(String idWinTarefa) {
		this.idWinTarefa = idWinTarefa;
	}	


	public int compareTo(Tarefa o) {
		if (o instanceof Tarefa) {
			return getNome().compareTo(o.getNome());
		} else {
			return 0;
		}
	}
	
}
