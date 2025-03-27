package usuarios;

import java.util.ArrayList;

import vuelos.Vuelo;
import elementos.Terminal;

/**
 * Representa a un Controlador dentro del sistema, responsable de una terminal
 * y de gestionar los vuelos asignados.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class Controlador extends Usuario{
	private static final long serialVersionUID = 1L;
	private Terminal terminal;
	private ArrayList<Vuelo> vuelosASuCargo; 
	
	/**
     * Constructor de la clase Controlador.
     *
     * @param dni      Identificación del controlador.
     * @param nombre   Nombre del controlador.
     * @param password Contraseña del controlador.
     * @param terminal Terminal asignada al controlador.
     */
	public Controlador(String dni, String nombre, String password, Terminal terminal) {
		super(dni, nombre, password);
		this.terminal = terminal;
		this.vuelosASuCargo = new ArrayList<Vuelo>();

		//sigue los vuelos de su terminal
		ArrayList<Vuelo> vuelosTerminal = terminal.getVuelos();
		for (Vuelo v: vuelosTerminal) {
			v.addObserver(this);
		}
	}
	
	/**
     * Obtiene la terminal asignada al controlador.
     *
     * @return Terminal asignada.
     */
	public Terminal getTerminal() {
		return this.terminal;
	}
	
	/**
     * Obtiene la lista de vuelos a cargo del controlador.
     *
     * @return Lista de vuelos asignados.
     */
	public ArrayList<Vuelo> getVuelosASuCargo() {
		return this.vuelosASuCargo;
	}
	
	/**
     * Asigna una nueva terminal al controlador.
     * 
     * El controlador deja de seguir los vuelos de su antigua terminal
     * y comienza a seguir los de la nueva.
     *
     * @param terminal Nueva terminal asignada.
     */
	public void setTerminal(Terminal terminal) {
		ArrayList<Vuelo> vuelos = this.terminal.getVuelos();
		for (Vuelo v: vuelos) {
			v.deleteObserver(this);
		}
		this.terminal = terminal;
		vuelos = terminal.getVuelos();
		for (Vuelo v: vuelos) {
			v.addObserver(this);
		}
		return;
	}
	
	/**
     * Asigna un vuelo al controlador.
     *
     * @param v Vuelo a asignar.
     * @return true si el vuelo fue asignado correctamente.
     */
	public Boolean asignarVuelo(Vuelo v) {
		if (this.vuelosASuCargo.contains(v)) {
			return false;
		}
		this.vuelosASuCargo.add(v);
		v.addObserver(this);
		return true;
	}
	
	/**
     * Indica si el usuario es un gestor.
     *
     * @return false, ya que es un controlador.
     */
	public Boolean esGestor() {
		return false;
	}
	
	/**
     * Indica si el usuario es un controlador.
     *
     * @return true, ya que esta clase representa un controlador.
     */
	public Boolean esControlador() {
		return true;
	}
	
	/**
     * Indica si el usuario es un operador.
     *
     * @return false, ya que es un controlador.
     */
	public Boolean esOperador() {
		return false;
	}
}
