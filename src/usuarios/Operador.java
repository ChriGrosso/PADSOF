package usuarios;

import java.util.ArrayList;

import aerolineas.Aerolinea;
import vuelos.Vuelo;

/**
 * Representa a un Operador dentro del sistema, encargado de gestionar
 * una aerolínea específica.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class Operador extends Usuario{
	private static final long serialVersionUID = 1L;
	private Aerolinea aerolinea;
	
	/**
     * Constructor de la clase Operador.
     *
     * @param dni       Identificación del operador.
     * @param nombre    Nombre del operador.
     * @param password  Contraseña del operador.
     * @param aerolinea Aerolínea a la que pertenece el operador.
     */
	public Operador(String dni, String nombre, String password, Aerolinea aerolinea) {
		super(dni, nombre, password);
		this.aerolinea = aerolinea;
		aerolinea.addOperador(this);
		ArrayList<Vuelo> vuelos = aerolinea.getVuelos();
		//sigue todos los vuelos de su aerolinea.
		for (Vuelo v: vuelos) {
			v.addObserver(this);
		}
	}
	
	/**
     * Obtiene la aerolínea asignada al operador.
     *
     * @return Aerolínea asignada.
     */
	public Aerolinea getAerolinea() {
		return this.aerolinea;
	}
	
	/**
     * Indica si el usuario es un gestor.
     *
     * @return false, ya que es un operador.
     */
	public Boolean esGestor() {
		return false;
	}
	
	/**
     * Indica si el usuario es un controlador.
     *
     * @return false, ya que es un operador.
     */
	public Boolean esControlador() {
		return false;
	}
	
	/**
     * Indica si el usuario es un operador.
     *
     * @return true, ya que esta clase representa un operador.
     */
	public Boolean esOperador() {
		return true;
	}
}
