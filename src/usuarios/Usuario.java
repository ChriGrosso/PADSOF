package usuarios;

import java.io.Serializable;
import java.util.ArrayList;

import aviones.EstadoAvion;
import notificaciones.Notificacion;
import notificaciones.Observer;
import vuelos.EstadoVuelo;

/**
 * Clase abstracta que representa a un usuario dentro del sistema.
 * Implementa la interfaz Observer para recibir notificaciones sobre cambios en vuelos y aviones.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public abstract class Usuario implements Observer, Serializable{
	private static final long serialVersionUID = 1L;
	private String dni;
	private String nombre;
	private String password;
	private ArrayList<Notificacion> notificaciones;
	
	/**
     * Constructor de la clase Usuario.
     * 
     * @param dni      Identificación del usuario.
     * @param nombre   Nombre del usuario.
     * @param password Contraseña del usuario.
     */
	public Usuario(String dni, String nombre, String password) {
		this.dni = dni;
		this.nombre = nombre;
		this.password = password;
		notificaciones = new ArrayList<Notificacion>();		
	}
	
	/**
     * Obtiene el DNI del usuario.
     * 
     * @return DNI del usuario.
     */
	public String getDni() {
		return this.dni;
	}
	
	/**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
     * Obtiene la contraseña del usuario.
     * 
     * @return Contraseña del usuario.
     */
	public String getPassword() {
		return this.password;
	}
	
	/**
     * Obtiene las notificaciones del usuario.
     * 
     * @return Lista de notificaciones del usuario.
     */
	public ArrayList<Notificacion> getNotificaciones() {
		return this.notificaciones;
	}
	
	/**
     * Establece una nueva contraseña para el usuario.
     * 
     * @param p Nueva contraseña a establecer.
     */
	public void setPassword(String p) {
		this.password = p;
	}
	
	 /**
     * Representación en cadena del usuario, indicando su rol.
     * 
     * @return Una cadena con la información del usuario y su tipo (Gestor, Controlador u Operador).
     */
	public String toString() {
		String s = ""+ this.nombre + " dni: "+this.dni+" , contraseña: "+ this.password;
		if (this.esGestor()) {
			s += " (Gestor)\n";
		} else if (this.esControlador()) {
			s += " (Controlador)\n";
		} else {
			s += " (Operador)\n";
		}
		return s;
	}
	
	 /**
     * Método llamado cuando se actualiza el estado de un vuelo.
     * 
     * @param id     Identificador del vuelo.
     * @param anterior Estado actual del vuelo.
     * @param nuevo Nuevo estado del vuelo.
     */
    @Override
	public void update(String id, EstadoVuelo anterior, EstadoVuelo nuevo) {
		if (this.sigueCambioEstadoVuelo(anterior, nuevo)) {
			String s = "El vuelo "+ id+ " ha pasado de "+ anterior+" a "+ nuevo+"\n";
			this.notificaciones.add(new Notificacion(s, null));
		}		
	}
    
    /**
     * Método llamado cuando se actualiza el estado de un avión.
     * 
     * @param id     Identificador del avión.
     * @param anterior Estado actual del avión.
     * @param nuevo Nuevo estado del avión.
     */
	@Override
	public void update(String id, EstadoAvion anterior, EstadoAvion nuevo) {
		if (this.sigueCambioEstadoAvion(anterior, nuevo)) {
			String s = "El avión "+ id+ " ha pasado de "+ anterior+" a "+ nuevo+"\n";
			this.notificaciones.add(new Notificacion(s, null));
		}
	}
	
	/**
     * Método para comprobar si este usuario sigue un determinado cambio
     * de estado de vuelo.
     * 
     * @param anterior Estado actual del vuelo.
     * @param nuevo Estado al que se va a cambiar el estado del vuelo.
     * @return true si lo sigue, y false si no lo hace.
     */
	@Override
	public Boolean sigueCambioEstadoVuelo(EstadoVuelo anterior, EstadoVuelo nuevo) {
		return true;
	}
	
	/**
     * Método para comprobar si este usuario sigue un determinado cambio
     * de estado de avión.
     * 
     * @param anterior Estado actual del avión.
     * @param nuevo Estado al que se va a cambiar el estado del avión.
     * @return true si lo sigue, y false si no lo hace.
     */
	@Override
	public Boolean sigueCambioEstadoAvion(EstadoAvion anterior, EstadoAvion nuevo) {
		return true;
	}
	
	/**
     * Envía una notificación a otro usuario.
     * 
     * @param mensaje Mensaje de la notificación.
     * @param u       Usuario destinatario de la notificación.
     * @throws IllegalArgumentException Si el usuario destinatario es nulo.
     */
	public void enviarNotificacion(String mensaje, Usuario u) {
		if (u == null) {
            throw new IllegalArgumentException("El usuario destinatario no puede ser nulo.");
        }
		u.notificaciones.add(new Notificacion(mensaje, this));
	}
	
	/**
     * Determina si el usuario es un gestor.
     * 
     * @return true si el usuario es un gestor, false en caso contrario.
     */
	public abstract Boolean esGestor();
	
	/**
     * Determina si el usuario es un controlador.
     * 
     * @return true si el usuario es un controlador, false en caso contrario.
     */
	public abstract Boolean esControlador();
	
	/**
     * Determina si el usuario es un operador.
     * 
     * @return true si el usuario es un operador, false en caso contrario.
     */
	public abstract Boolean esOperador();
}
