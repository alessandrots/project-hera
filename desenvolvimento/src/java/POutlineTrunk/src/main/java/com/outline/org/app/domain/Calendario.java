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
@Table(name="negocio.tb_calendario")
public class Calendario implements Comparable<Calendario>, Serializable ,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3122260198022615777L;
	
	@Id @Generated(GenerationTime.INSERT) 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icodigo", nullable=false) 
	private Long codigo;	
	
	@Column(name="dia_domingo", nullable=false) 
	private float domingo;
	
	@Column(name="dia_segunda", nullable=false) 
	private float segunda;
	
	@Column(name="dia_terca", nullable=false) 
	private float terca;
	
	@Column(name="dia_quarta", nullable=false) 
	private float quarta;
	
	@Column(name="dia_quinta", nullable=false) 
	private float quinta;
	
	@Column(name="dia_sexta", nullable=false) 
	private float sexta;
	
	@Column(name="dia_sabado", nullable=false) 
	private float sabado;
	
	@ManyToOne @JoinColumn(name="irecurso", nullable=false)
	private Recurso recurso;

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
	 * @return the domingo
	 */
	public float getDomingo() {
		return domingo;
	}

	/**
	 * @param domingo the domingo to set
	 */
	public void setDomingo(float domingo) {
		this.domingo = domingo;
	}

	/**
	 * @return the segunda
	 */
	public float getSegunda() {
		return segunda;
	}

	/**
	 * @param segunda the segunda to set
	 */
	public void setSegunda(float segunda) {
		this.segunda = segunda;
	}

	/**
	 * @return the terca
	 */
	public float getTerca() {
		return terca;
	}

	/**
	 * @param terca the terca to set
	 */
	public void setTerca(float terca) {
		this.terca = terca;
	}

	/**
	 * @return the quarta
	 */
	public float getQuarta() {
		return quarta;
	}

	/**
	 * @param quarta the quarta to set
	 */
	public void setQuarta(float quarta) {
		this.quarta = quarta;
	}

	/**
	 * @return the quinta
	 */
	public float getQuinta() {
		return quinta;
	}

	/**
	 * @param quinta the quinta to set
	 */
	public void setQuinta(float quinta) {
		this.quinta = quinta;
	}

	/**
	 * @return the sexta
	 */
	public float getSexta() {
		return sexta;
	}

	/**
	 * @param sexta the sexta to set
	 */
	public void setSexta(float sexta) {
		this.sexta = sexta;
	}

	/**
	 * @return the sabado
	 */
	public float getSabado() {
		return sabado;
	}

	/**
	 * @param sabado the sabado to set
	 */
	public void setSabado(float sabado) {
		this.sabado = sabado;
	}

	/**
	 * @return the recurso
	 */
	public Recurso getRecurso() {
		return recurso;
	}

	/**
	 * @param recurso the recurso to set
	 */
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	@Override
	public int compareTo(Calendario o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
