package vuelos;

/**
 * Enumeración que representa los distintos estados en los que puede estar un vuelo.
 * 
 * Estados posibles:
 * EN_VUELO: El vuelo está en pleno trayecto.
 * ESPERANDO_PISTA_A: Esperando una pista de aterrizaje.
 * ESPERANDO_PISTA_D: Esperando una pista para despegar.
 * ESPERANDO_ATERRIZAJE: En espera para aterrizar.
 * ESPERANDO_DESPEGUE: En espera para despegar.
 * ATERRIZADO: Ha aterrizado con éxito.
 * EMBARQUE: En proceso de embarque de pasajeros.
 * DESEMBARQUE_INI: Inicio del desembarque de pasajeros.
 * DESEMBARQUE_FIN: Fin del desembarque de pasajeros.
 * CARGA: En proceso de carga de mercancías o equipaje.
 * DESCARGA_INI: Inicio de la descarga de mercancías o equipaje.
 * DESCARGA_FIN: Fin de la descarga de mercancías o equipaje.
 * EN_TIEMPO: El vuelo está en horario y sin retrasos.
 * RETRASADO: El vuelo ha sufrido un retraso.
 * OPERATIVO: El vuelo está operativo y listo para usarse.
 * EN_HANGAR: El vuelo está en el hangar, probablemente en mantenimiento.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public enum EstadoVuelo {
	EN_VUELO, 
	ESPERANDO_PISTA_A, 
	ESPERANDO_PISTA_D, 
	ESPERANDO_ATERRIZAJE, 
	ESPERANDO_DESPEGUE, 
	ATERRIZADO, 
	EMBARQUE, 
	DESEMBARQUE_INI, 
	DESEMBARQUE_FIN,
	CARGA, 
	DESCARGA_INI, 
	DESCARGA_FIN, 
	EN_TIEMPO, 
	RETRASADO, 
	OPERATIVO, 
	EN_HANGAR;
}
