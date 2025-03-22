package usuarios;

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
public abstract class Usuario implements Observer{
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
     * @param estado Nuevo estado del vuelo.
     */
    @Override
	public void update(String id, EstadoVuelo estado) {
		if (this.sigueCambioEstadoVuelo(estado)) {
			String s = "El vuelo "+ id+ " está "+ estado+"\n";
			this.notificaciones.add(new Notificacion(s, null));
		}		
	}
    
    /**
     * Método llamado cuando se actualiza el estado de un avión.
     * 
     * @param id     Identificador del avión.
     * @param estado Nuevo estado del avión.
     */
	@Override
	public void update(String id, EstadoAvion estado) {
		if (this.sigueCambioEstadoAvion(estado)) {
			String s = "El avión "+ id+ " está "+ estado+"\n";
			this.notificaciones.add(new Notificacion(s, null));
		}
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
		u.notificaciones.add(new Notificacion(mensaje, u));
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
