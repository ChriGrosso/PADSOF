package notificaciones;

import java.io.Serializable;
import java.time.LocalDateTime;

import usuarios.Usuario;

/**
 * Clase que representa una notificación enviada a un usuario.
 * Contiene información sobre el mensaje, la fecha de emisión,
 * el emisor y si ha sido leída o no.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class Notificacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private LocalDateTime fechaEmision;
	private Usuario emisor;
	private Boolean leida;
	
	/**
     * Constructor de la clase Notificacion.
     *
     * @param mensaje Contenido del mensaje de la notificación.
     * @param emisor  Usuario que envió la notificación (puede ser null si es del sistema).
     */
	public Notificacion(String mensaje, Usuario emisor) {
		this.mensaje = mensaje;
		this.emisor = emisor;
		this.fechaEmision = LocalDateTime.now();
		this.leida = false;
	}
	
	/**
     * Obtiene el mensaje de la notificación.
     *
     * @return Contenido del mensaje.
     */
	public String getMensaje() {
		return this.mensaje;
	}
	
	/**
     * Obtiene la fecha en la que se emitió la notificación.
     *
     * @return Fecha de emisión de la notificación.
     */
	public LocalDateTime getFechaEmision() {
		return this.fechaEmision;
	}
	
	/**
     * Obtiene el usuario que envió la notificación.
     *
     * @return Emisor de la notificación.
     */
	public Usuario getEmisor() {
		return this.emisor;
	}
	
	/**
     * Verifica si la notificación ha sido marcada como leída.
     *
     * @return true si la notificación ha sido leída, false en caso contrario.
     */
	public Boolean getLeida() {
		return this.leida;
	}
	
	 /**
     * Establece el estado de lectura de la notificación.
     *
     * @param leida true si la notificación ha sido leída, false en caso contrario.
     * @throws IllegalArgumentException Si el valor es nulo.
     */
	public void setLeida(Boolean leida) {
		if (leida == null) {
            throw new IllegalArgumentException("El estado de lectura no puede ser nulo.");
        }
		this.leida = leida;
	}
}
