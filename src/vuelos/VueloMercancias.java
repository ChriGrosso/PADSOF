package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import elementos.Terminal;
import aeropuertos.Aeropuerto;

/**
 * Representa un vuelo de mercancías, extendiendo la clase Vuelo.
 * Este tipo de vuelo es utilizado para transportar mercancías, incluyendo
 * información sobre la carga y si la carga es peligrosa.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class VueloMercancias extends Vuelo {
	private static final long serialVersionUID = 1L;
	private double carga;
	private boolean mercanciasPeligrosas;
	
	/**
     * Constructor que crea un vuelo de mercancías con la información necesaria.
     * 
     * @param id El identificador único del vuelo.
     * @param origen El aeropuerto de origen.
     * @param destino El aeropuerto de destino.
     * @param horaSalida La hora de salida del vuelo.
     * @param horaLlegada La hora de llegada del vuelo.
     * @param aerolinea La lista de aerolíneas asociadas al vuelo.
     * @param llegada Indica si es un vuelo de llegada.
     * @param carga La cantidad de carga transportada en el vuelo.
     * @param mercanciasPeligrosas Indica si el vuelo transporta mercancías peligrosas.
     * @param periodicidad La periodicidad del vuelo.
     * @param avion El avión utilizado para el vuelo.
     * @throws IllegalArgumentException Si el avión no es del tipo AvionMercancias.
     */
	public VueloMercancias(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, double carga, boolean mercanciasPeligrosas, 
			Periodicidad periodicidad, Avion avion) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if((avion.getTipoAvion() instanceof AvionMercancias) == false) {
			throw new IllegalArgumentException("Un vuelo de mercancías debe tener un avión para mercancías");
		}
		this.carga = carga;
		this.mercanciasPeligrosas = mercanciasPeligrosas;
	}
	
	public VueloMercancias(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, double carga, boolean mercanciasPeligrosas, 
			Avion avion, String diasAlternos) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, avion, diasAlternos);
		if((avion.getTipoAvion() instanceof AvionMercancias) == false) {
			throw new IllegalArgumentException("Un vuelo de mercancías debe tener un avión para mercancías");
		}
		this.carga = carga;
		this.mercanciasPeligrosas = mercanciasPeligrosas;
	}
	
	
	/**
     * Obtiene la cantidad de carga transportada por el vuelo.
     * 
     * @return La cantidad de carga transportada.
     */
	public double getCarga() {
		return this.carga;
	}
	
	/**
     * Indica si el vuelo transporta mercancías peligrosas.
     * 
     * @return true si el vuelo transporta mercancías peligrosas, false en caso contrario.
     */
	public boolean getMercanciasPeligrosas() {
		return this.mercanciasPeligrosas;
	}
	
	/**
     * Asigna una terminal al vuelo de mercancías, validando si la terminal tiene la capacidad 
     * suficiente para manejar la carga y si está habilitada para mercancías.
     * 
     * @param terminal La terminal a asignar al vuelo.
     * @return true si la terminal fue asignada correctamente, false si no fue posible.
     */
	public boolean asignarTerminal(Terminal terminal) {
		if(terminal.numPuertasOcupadasTerm() == terminal.getNumeroPuertas() || terminal.getCapacidadOcup()+this.carga > terminal.getCapacidad()
			|| !terminal.isMercancias()) {
			return false;
		}
		this.setTerminal(terminal);
		return true;
	}
}
