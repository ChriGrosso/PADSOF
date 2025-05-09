package notificaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import aviones.EstadoAvion;
import usuarios.Usuario;
import vuelos.EstadoVuelo;

/**
 * Clase abstracta que representa un objeto observable en el patrón Observer.
 * Mantiene una lista de observadores (usuarios) que pueden recibir notificaciones 
 * sobre cambios en el estado de vuelos o aviones.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public abstract class Observable implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap<String, Usuario> observers;
	
	/**
     * Constructor de la clase Observable.
     * Inicializa la lista de observadores.
     */
	public Observable() {
		observers = new HashMap<String, Usuario>();
	}
	
	/**
     * Obtiene el identificador único del objeto observable.
     * 
     * @return Identificador del objeto.
     */
	public abstract String getId();
	
	/**
     * Obtiene los usuarios que siguen el objeto.
     * 
     * @return Lista de observadores del objeto.
     */
	public ArrayList<Usuario> getObservers() {
		return new ArrayList<Usuario>(this.observers.values());
	}
	
	/**
     * Agrega un usuario a la lista de observadores.
     * 
     * @param user Usuario a agregar.
     * @throws IllegalArgumentException Si el usuario es nulo.
     */
	public void addObserver(Usuario user) {
		if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
		if (observers.containsKey(user.getDni()) == false) {
			observers.put(user.getDni(), user);
		}
	}
	
	/**
     * Comprueba si un usuario ya pertenece a la lista de observadores.
     * 
     * @param user Usuario a comprobar.
     * @return true si lo contiene, false en caso contrario.
     */
	public boolean comprobarObserver(Usuario user) {
		if (user != null) {
            return observers.containsKey(user.getDni());
        }
		return false;
	}
	
	
	
	/**
     * Elimina un usuario de la lista de observadores.
     * 
     * @param user Usuario a eliminar.
     * @throws IllegalArgumentException Si el usuario es nulo o no está registrado.
     */
	public void deleteObserver(Usuario user) {
		if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
		if (observers.containsKey(user.getDni())) {
			observers.remove(user.getDni());
		}
	}
	
	/**
     * Notifica a todos los observadores sobre un cambio de estado en un vuelo.
     * 
     * @param anterior Estado actual del vuelo.
     * @param nuevo Estado al que se va a cambiar el estado del vuelo.
     */
	public void notifyObservers(EstadoVuelo anterior, EstadoVuelo nuevo) {
		Collection<Usuario> usuarios = observers.values();
		
		for (Usuario u: usuarios) {
			u.update(this.getId(), anterior, nuevo);
		}
		return;
	}
	
	/**
     * Notifica a todos los observadores sobre un cambio de estado en un avión.
     * 
     * @param anterior Estado actual del avión.
     * @param nuevo Estado al que se va a cambiar el estado del avión.
     */
	public void notifyObservers(EstadoAvion anterior, EstadoAvion nuevo) {
		Collection<Usuario> usuarios = observers.values();
		
		for (Usuario u: usuarios) {
			u.update(this.getId(), anterior, nuevo);
		}
		return;
	}
	
}
