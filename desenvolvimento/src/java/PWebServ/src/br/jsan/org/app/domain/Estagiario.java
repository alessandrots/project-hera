package br.jsan.org.app.domain;

public class Estagiario implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764080717966085767L;
	
	private int codigo;
	private String nome;
	private char ativo;
	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
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
	public char getAtivo() {
		return ativo;
	}
	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(char ativo) {
		this.ativo = ativo;
	}
	
	
	
}
