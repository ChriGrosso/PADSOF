package notificaciones;

import aviones.EstadoAvion;
import vuelos.EstadoVuelo;

/**
 * Interfaz Observer que define el comportamiento para recibir actualizaciones
 * sobre cambios en el estado de vuelos y aviones.
 * 
 * Forma parte del patrón de diseño Observer, permitiendo que los objetos que
 * la implementen sean notificados de cambios en los objetos observables.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public interface Observer {
	/**
     * Notifica al observador sobre un cambio de estado en un vuelo.
     *
     * @param id     Identificador del vuelo.
     * @param anterior Estado actual del vuelo.
     * @param nuevo Nuevo estado del vuelo.
     */
	void update(String id, EstadoVuelo anterior, EstadoVuelo nuevo);
	
	/**
     * Notifica al observador sobre un cambio de estado en un avión.
     *
     * @param id     Identificador del avión.
     * @param anterior Estado actual del avion.
     * @param nuevo Nuevo estado del avión.
     */
	void update(String id, EstadoAvion anterior, EstadoAvion nuevo);
	
	/**
     * Determina si el observador debe ser notificado cuando un vuelo cambia a un estado específico.
     *
     * @param anterior Estado inicial del vuelo a evaluar.
     * @param nuevo Estado tras el cambio del vuelo a evaluar.
     * @return true si el observador sigue este tipo de cambio, false en caso contrario.
     */
	Boolean sigueCambioEstadoVuelo(EstadoVuelo anterior, EstadoVuelo nuevo);
	
	/**
     * Determina si el observador debe ser notificado cuando un avión cambia a un estado específico.
     *
     * @param anterior Estado inicial del avion a evaluar.
     * @param nuevo Estado tras el cambio del avion a evaluar.
     * @return true si el observador sigue este tipo de cambio, false en caso contrario.
     */
	Boolean sigueCambioEstadoAvion(EstadoAvion anterior, EstadoAvion nuevo);
}
