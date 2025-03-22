package notificaciones;

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
public abstract class Observable {
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
     * Notifica a un usuario específico sobre un cambio de estado en un vuelo.
     * 
     * @param u      Usuario a notificar.
     * @param estado Nuevo estado del vuelo.
     * @throws IllegalArgumentException Si el usuario es nulo.
     */
	public void notifyObserver(Usuario u, EstadoVuelo estado) {
		if (u == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
		if (this.observers.containsKey(u.getDni())) {
			u.update(this.getId(), estado);
		}
	}
	
	 /**
     * Notifica a un usuario específico sobre un cambio de estado en un avión.
     * 
     * @param u      Usuario a notificar.
     * @param estado Nuevo estado del avión.
     * @throws IllegalArgumentException Si el usuario es nulo.
     */
	public void notifyObserver(Usuario u, EstadoAvion estado) {
		if (u == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
		if (this.observers.containsKey(u.getDni())) {
			u.update(this.getId(), estado);
		}
		return;
	}
	
	/**
     * Notifica a todos los observadores sobre un cambio de estado en un vuelo.
     * 
     * @param estado Nuevo estado del vuelo.
     */
	public void notifyObservers(EstadoVuelo estado) {
		Collection<Usuario> usuarios = observers.values();
		
		for (Usuario u: usuarios) {
			u.update(this.getId(), estado);
		}
		return;
	}
	
	/**
     * Notifica a todos los observadores sobre un cambio de estado en un avión.
     * 
     * @param estado Nuevo estado del avión.
     */
	public void notifyObservers(EstadoAvion estado) {
		Collection<Usuario> usuarios = observers.values();
		
		for (Usuario u: usuarios) {
			u.update(this.getId(), estado);
		}
		return;
	}
	
}
