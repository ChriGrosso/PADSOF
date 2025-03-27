package aviones;

import java.io.Serializable;
import java.time.LocalDate; // import the LocalDate class

import elementos.Hangar;
import elementos.HangarMercancias;
import elementos.HangarPasajeros;
import notificaciones.Observable;

/**
 * Clase que representa a un avión dentro del sistema.
 * Cada avión tiene una matrícula única, una fecha de compra, un tipo de avión 
 * (pasajeros o mercancías), un estado actual y puede estar asignado a un hangar.
 * Extiende la clase Observable para notificar cambios en su estado.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class Avion extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;
	private String matricula;
	private LocalDate fechaCompra;
	private LocalDate fechaUltimaRevision;
	private TipoAvion tipoAvion;
	private EstadoAvion estadoAvion;
	private Hangar hangar;
	
	/**
     * Constructor de la clase Avion.
     * 
     * @param matricula           Matrícula del avión.
     * @param fechaCompra         Fecha de compra del avión.
     * @param tipoAvion           Tipo de avión (puede ser pasajeros o mercancías).
     * @param fechaUltimaRevision Fecha de la última revisión técnica del avión.
     */
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion, LocalDate fechaUltimaRevision) { 
		this.matricula = matricula;
		this.fechaCompra = fechaCompra;
		this.tipoAvion = tipoAvion;
		this.fechaUltimaRevision = fechaUltimaRevision;
		this.estadoAvion = EstadoAvion.FUERA_AEROPUERTO;
	}
	
	/**
     * Constructor alternativo de la clase Avion, que asume que la fecha de última revisión es igual a la fecha de compra.
     * 
     * @param matricula   Matrícula del avión.
     * @param fechaCompra Fecha de compra del avión.
     * @param tipoAvion   Tipo de avión (puede ser pasajeros o mercancías).
     */
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion) {
		this(matricula, fechaCompra, tipoAvion, fechaCompra);
		this.fechaUltimaRevision = null;
	}
	
	/**
     * Obtiene la matrícula del avión.
     * 
     * @return Matrícula del avión.
     */
	public String getMatricula() {
		return this.matricula;
	}
	
	/**
     * Obtiene la fecha de compra del avión.
     * 
     * @return Fecha de compra del avión.
     */
	public LocalDate getFechaCompra() {
		return this.fechaCompra;
	}
	
	/**
     * Obtiene el tipo de avión (pasajeros o mercancías).
     * 
     * @return Tipo de avión.
     */
	public TipoAvion getTipoAvion() {
		return this.tipoAvion;
	}
	
	/**
     * Obtiene la fecha de la última revisión técnica del avión.
     * 
     * @return Fecha de la última revisión del avión.
     */
	public LocalDate getFechaUltimaRevision() {
		return this.fechaUltimaRevision;
	}
	
	/**
     * Obtiene el estado actual del avión.
     * 
     * @return Estado del avión.
     */
	public EstadoAvion getEstadoAvion() {
		return this.estadoAvion;
	}
	
	/**
     * Obtiene el hangar al que está asignado el avión.
     * 
     * @return Hangar donde está estacionado el avión.
     */
	public Hangar getHangar() {
		return this.hangar;
	}
	
	/**
     * Establece la fecha de la última revisión técnica del avión.
     * 
     * @param fechaUltimaRevision Fecha de la última revisión.
     */
	public void setFechaUltimaRevision(LocalDate fechaUltimaRevision) {
		this.fechaUltimaRevision = fechaUltimaRevision;
		return;
	}
	
	/**
     * Cambia el estado del avión y notifica a los observadores del cambio.
     * 
     * @param estadoAvion Nuevo estado del avión.
     */
	public void setEstadoAvion(EstadoAvion estadoAvion) {
		this.notifyObservers(this.estadoAvion, estadoAvion);
		this.estadoAvion = estadoAvion;
		return;
	}
	
	/**
     * Asigna un hangar al avión. Verifica que el hangar sea compatible con el tipo de avión.
     * 
     * @param hangar Hangar donde se quiere estacionar el avión.
     * @return True si el avión se ha asignado correctamente al hangar, False si no es compatible.
     * @throws IllegalArgumentException Si el tipo de avión no coincide con el tipo de hangar.
     */
	public boolean asignarHangar(Hangar hangar) {
		if(hangar instanceof HangarMercancias && this.tipoAvion instanceof AvionPasajeros) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe estar en un hangar para pasajeros");
		}
		if(hangar instanceof HangarPasajeros && this.tipoAvion instanceof AvionMercancias) {
			throw new IllegalArgumentException("Un vuelo de mercancias debe estar en un hangar para mercancias");
		}
		
		if(hangar.comprobarCompatibilidad(this) == false) {
			return false;
		}
		
		this.hangar = hangar;
		hangar.addAvion(this);
		return true;
	}
	
	/**
     * Desasigna el hangar al que está asignado el avión.
     */
	public void desasignarHangar() {
		if(this.hangar == null) {
			return;
		}
		
		hangar.removeAvion(this);
		this.hangar = null;
		return;
	}

	/**
     * Obtiene la matrícula del avión como identificación única para la clase Observable.
     * 
     * @return Matrícula del avión.
     */
	@Override
	public String getId() {
		return this.matricula;
	}
}
