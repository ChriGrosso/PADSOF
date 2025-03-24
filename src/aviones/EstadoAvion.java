package aviones;

/**
 * Enumeración que representa los posibles estados en los que puede encontrarse un avión dentro 
 * del sistema.
 * Los estados posibles son:
 * 
 * - EN_FINGER: El avión está en la puerta de embarque conectada al finger.
 * - EN_PARKING: El avión está estacionado en una posición de parking en el aeropuerto.
 * - ESPERANDO_PISTA: El avión está esperando autorización para acceder a la pista de despegue o aterrizaje.
 * - EN_PISTA: El avión está en la pista, ya sea en proceso de despegue o aterrizaje.
 * - EN_HANGAR: El avión está asignado a un hangar.
 * - FUERA_AEROPUERTO: El avión está fuera del aeropuerto, por ejemplo, en vuelo o en otro destino.
 * 
 * Esta enumeración se utiliza para gestionar el estado de un avión en el sistema y para realizar operaciones 
 * en función de su estado actual.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public enum EstadoAvion {
	EN_FINGER, EN_PARKING, ESPERANDO_PISTA, EN_PISTA, EN_HANGAR, FUERA_AEROPUERTO;
}
