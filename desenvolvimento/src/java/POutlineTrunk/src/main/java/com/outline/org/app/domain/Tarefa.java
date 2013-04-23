package com.outline.org.app.domain;

import java.sql.Timestamp;

public class Tarefa implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5652715197735231887L;
	
	private Integer codigo;
	private String nome;
	private Integer duracao;
	private Timestamp dataInicio;
	private Timestamp dataEntrega;
	private Timestamp dataTermino;
	
	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
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
	 * @return the dataInicio
	 */
	public Timestamp getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	
	
	
	 */
	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}
	/**
	 * @return the dataFim
	 */
	public Timestamp getDataEntrega() {
		return dataEntrega;
	}
	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataEntrega(Timestamp dataFim) {
		this.dataEntrega = dataFim;
	}
	/**
	 * @return the dataTermino
	 */
	public Timestamp getDataTermino() {
		return dataTermino;
	}
	/**
	 * @param dataTermino the dataTermino to set
	 */
	public void setDataTermino(Timestamp dataTermino) {
		this.dataTermino = dataTermino;
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
	
}
