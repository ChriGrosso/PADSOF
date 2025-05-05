package vuelos;

/**
 * Enumeración que define el estado de la petición de compartir de un vuelo.
 *
 * Tipos de estado de la petición:
 * NO_COMPARTIDO: La petición ha sido rechzada/el vuelo no es compartido.
 * COMPARTIDO: La petición ha sido aceptada/el vuelo es compartido.
 * PETICION_ENVIADA: La petición ha sido enviada a la aerolínea correspondiente.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public enum PeticionCompartir {
	NO_COMPARTIDO,
	COMPARTIDO,
	PETICION_ENVIADA;
}
