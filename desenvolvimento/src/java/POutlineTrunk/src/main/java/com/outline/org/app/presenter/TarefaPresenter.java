package com.outline.org.app.presenter;


/**
 *  source = window_1001
 *  target = window_1002
 *  TipoRelacionamentoLogico = 0
 *  
 * @author alessandrots
 *
 */
public class TarefaPresenter extends PresenterImpl {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 3050449973665933675L;
	
	private String codigo;
	private String nome;
	private String duracao;
	private String dataInicio;
	private String dataEntrega;
	private String dataTermino;
	private String idWinTarefa;	
	
	//Campos usados para testar a sincronização no servidor
	private String source;
	private String target;
	private String tipoRelacionamentoLogico;
	
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
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
	public String getDuracao() {
		return duracao;
	}
	/**
	 * @param duracao the duracao to set
	 */
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	/**
	 * @return the dataInicio
	 */
	public String getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	/**
	 * @return the dataEntrega
	 */
	public String getDataEntrega() {
		return dataEntrega;
	}
	/**
	 * @param dataEntrega the dataEntrega to set
	 */
	public void setDataEntrega(String dataEntrega) {		
		this.dataEntrega = dataEntrega;
	}
	/**
	 * @return the dataTermino
	 */
	public String getDataTermino() {
		return dataTermino;
	}
	/**
	 * @param dataTermino the dataTermino to set
	 */
	public void setDataTermino(String dataTermino) {
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
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the tipoRelacionamentoLogico
	 */
	public String getTipoRelacionamentoLogico() {
		return tipoRelacionamentoLogico;
	}
	/**
	 * @param tipoRelacionamentoLogico the tipoRelacionamentoLogico to set
	 */
	public void setTipoRelacionamentoLogico(String tipoRelacionamentoLogico) {
		this.tipoRelacionamentoLogico = tipoRelacionamentoLogico;
	}
	
	
	
	 
}
