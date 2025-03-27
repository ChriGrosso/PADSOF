package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aviones.Avion;
import aviones.AvionPasajeros;
import elementos.Terminal;

public class VueloPasajeros extends Vuelo{
	private static final long serialVersionUID = 1L;
	private int numPasajeros;

	public VueloPasajeros(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Periodicidad periodicidad,
			Avion avion) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if((avion.getTipoAvion() instanceof AvionPasajeros) == false) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros");
		}
		this.numPasajeros = numPasajeros;
	}
	
	public VueloPasajeros(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Avion avion, String diasAlternos) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, avion, diasAlternos);
		if((avion.getTipoAvion() instanceof AvionPasajeros) == false) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros");
		}
		this.numPasajeros = numPasajeros;
	}
	

	public int getNumPasajeros() {
		return this.numPasajeros;
	}
	

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
