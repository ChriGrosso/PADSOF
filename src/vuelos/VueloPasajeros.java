package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aviones.Avion;
import aviones.AvionPasajeros;
import elementos.Terminal;

/**
 * Representa un vuelo de pasajeros, extendiendo la clase Vuelo.
 * Este tipo de vuelo está destinado al transporte de pasajeros y contiene información 
 * sobre la cantidad de pasajeros a bordo.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class VueloPasajeros extends Vuelo{
	private static final long serialVersionUID = 1L;
	private int numPasajeros;

	/**
     * Constructor que crea un vuelo de pasajeros con periodicidad normal.
     * 
     * @param id El identificador único del vuelo.
     * @param origen El aeropuerto de origen.
     * @param destino El aeropuerto de destino.
     * @param horaSalida La hora de salida del vuelo.
     * @param horaLlegada La hora de llegada del vuelo.
     * @param aerolinea La lista de 1 o 2 aerolíneas asociadas al vuelo.
     * @param llegada Indica si es un vuelo de llegada.
     * @param numPasajeros El número de pasajeros en el vuelo.
     * @param periodicidad La periodicidad del vuelo.
     * @param avion El avión utilizado para el vuelo.
     * @throws IllegalArgumentException Si el avión no es del tipo AvionPasajeros.
     */
	public VueloPasajeros(Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Periodicidad periodicidad,
			Avion avion) {
		super(origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if((avion.getTipoAvion() instanceof AvionPasajeros) == false) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros");
		}
		this.numPasajeros = numPasajeros;
	}
	
	/**
     * Constructor que crea un vuelo de pasajeros con periodicidad de días alternos.
     * 
     * @param id El identificador único del vuelo.
     * @param origen El aeropuerto de origen.
     * @param destino El aeropuerto de destino.
     * @param horaSalida La hora de salida del vuelo.
     * @param horaLlegada La hora de llegada del vuelo.
     * @param aerolineas La lista de 1 o 2 aerolíneas asociadas al vuelo.
     * @param llegada Indica si es un vuelo de llegada.
     * @param numPasajeros El número de pasajeros en el vuelo.
     * @param avion El avión utilizado para el vuelo.
     * @param diasAlternos La periodicidad de días alternos del vuelo.
     * @throws IllegalArgumentException Si el avión no es del tipo AvionPasajeros.
     */
	public VueloPasajeros(Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Avion avion, String diasAlternos) {
		super(origen, destino, horaSalida, horaLlegada, aerolinea, llegada, avion, diasAlternos);
		if((avion.getTipoAvion() instanceof AvionPasajeros) == false) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros");
		}
		this.numPasajeros = numPasajeros;
	}
	

	/**
     * Obtiene el número de pasajeros en el vuelo.
     * 
     * @return El número de pasajeros.
     */
	public int getNumPasajeros() {
		return this.numPasajeros;
	}
	

	/**
     * Asigna una terminal al vuelo de pasajeros, validando si la terminal tiene la capacidad 
     * suficiente para manejar el número de pasajeros y si no está habilitada exclusivamente para mercancías.
     * 
     * @param terminal La terminal a asignar al vuelo.
     * @return true si la terminal fue asignada correctamente, false si no fue posible.
     */
	public boolean asignarTerminal(Terminal terminal) {
		if(terminal.numPuertasOcupadasTerm() == terminal.getNumeroPuertas() || terminal.getCapacidadOcup()+this.numPasajeros > terminal.getCapacidad()
			|| terminal.isMercancias()) {
			return false;
		}
		this.setTerminal(terminal);
		terminal.addVuelo(this);
		return true;
	}
}
