package aviones;

import java.io.Serializable;

/**
 * Clase abstracta que representa a un modelo de avión dentro del sistema.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es 
 */
public abstract class TipoAvion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String marca;
	private String modelo;
	private double autonomia;
	private double altura;
	private double anchura;
	private double largo;
	
	/**
     * Constructor de la clase TipoAvion.
     *
     * @param marca    Código de identificación de la aerolínea.
     * @param nombre   Nombre de la aerolínea.
     */
	public TipoAvion(String marca, String modelo, double autonomia, double altura, double anchura, double largo) {
		this.marca = marca;
		this.modelo = modelo;
		this.autonomia = autonomia;
		this.altura = altura;
		this.anchura = anchura;
		this.largo = largo;
	}
	
	public String getMarca() {
		return this.marca;
	}
	public String getModelo() {
		return this.modelo;
	}
	public double getAutonomia() {
		return this.autonomia;
	}
	public double getAltura() {
		return this.altura;
	}
	public double getAnchura() {
		return this.anchura;
	}
	public double getLargo() {
		return this.largo;
	}
}
