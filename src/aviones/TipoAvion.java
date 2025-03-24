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
     * @param marca      Marca del avión.
     * @param modelo     Modelo del avión.
     * @param autonomia  Autonomía del avión en kilómetros.
     * @param altura     Altura del avión en metros.
     * @param anchura    Anchura del avión en metros.
     * @param largo      Largo del avión en metros.
     */
	public TipoAvion(String marca, String modelo, double autonomia, double altura, double anchura, double largo) {
		this.marca = marca;
		this.modelo = modelo;
		this.autonomia = autonomia;
		this.altura = altura;
		this.anchura = anchura;
		this.largo = largo;
	}
	
	/**
     * Método abstracto que determina si el avión está diseñado para transportar mercancías o, 
     * en caso contrario, pasajeros.
     *
     * @return True si el avión transporta mercancías, False en caso contrario.
     */
	public abstract boolean isMercancias();
	
	/**
     * Método abstracto que devuelve la capacidad máxima del avión.
     *
     * @return La capacidad máxima del avión en toneladas o número de pasajeros, 
     * según el tipo de avión.
     */
	public abstract double getCapacidad();
	
	/**
     * Obtiene la marca del avión.
     *
     * @return Marca del avión.
     */
	public String getMarca() {
		return this.marca;
	}
	
	/**
     * Obtiene el modelo del avión.
     *
     * @return Modelo del avión.
     */
	public String getModelo() {
		return this.modelo;
	}
	
	/**
     * Obtiene la autonomía del avión.
     *
     * @return Autonomía del avión en kilómetros.
     */
	public double getAutonomia() {
		return this.autonomia;
	}
	
	/**
     * Obtiene la altura del avión.
     *
     * @return Altura del avión en metros.
     */
	public double getAltura() {
		return this.altura;
	}
	
	/**
     * Obtiene la anchura del avión.
     *
     * @return Anchura del avión en metros.
     */
	public double getAnchura() {
		return this.anchura;
	}
	
	/**
     * Obtiene el largo del avión.
     *
     * @return Largo del avión en metros.
     */
	public double getLargo() {
		return this.largo;
	}
}
