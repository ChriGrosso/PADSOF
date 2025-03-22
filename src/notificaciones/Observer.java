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
     * @param estado Nuevo estado del vuelo.
     */
	void update(String id, EstadoVuelo estado);
	
	/**
     * Notifica al observador sobre un cambio de estado en un avión.
     *
     * @param id     Identificador del avión.
     * @param estado Nuevo estado del avión.
     */
	void update(String id, EstadoAvion estado);
	
	/**
     * Determina si el observador debe ser notificado cuando un vuelo cambia a un estado específico.
     *
     * @param estado Estado del vuelo a evaluar.
     * @return true si el observador sigue este tipo de cambio, false en caso contrario.
     */
	Boolean sigueCambioEstadoVuelo(EstadoVuelo estado);
	
	/**
     * Determina si el observador debe ser notificado cuando un avión cambia a un estado específico.
     *
     * @param estado Estado del avión a evaluar.
     * @return true si el observador sigue este tipo de cambio, false en caso contrario.
     */
	Boolean sigueCambioEstadoAvion(EstadoAvion estado);
}
