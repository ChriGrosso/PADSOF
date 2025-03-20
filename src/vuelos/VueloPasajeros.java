package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aviones.Avion;
import aviones.AvionPasajeros;
import elementos.Puerta;
import elementos.TerminalPasajeros;

public class VueloPasajeros extends Vuelo{
	private TerminalPasajeros terminal;
	private Puerta puerta;
	private int numPasajeros;

	public VueloPasajeros(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Periodicidad periodicidad,
			Avion avion) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if(avion.getTipoAvion() instanceof AvionPasajeros) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros\n");
		}
		this.numPasajeros = numPasajeros;
	}

	public int getNumPasajeros() {
		return this.numPasajeros;
	}
	
	public TerminalPasajeros getTerminal() {
		return this.terminal;
	}
	
	public Puerta getPuerta() {
		return this.puerta;
	}

	public boolean asignarTerminal(TerminalPasajeros terminal) {
		if(terminal.numPuertasOcupadasTerm() == terminal.getNumeroPuertas() || terminal.getPasajerosTotal()+this.numPasajeros > terminal.getCapacidadPersonas()) {
			return false;
		}
		this.terminal = terminal;
		return true;
	}
	
	public boolean asignarPuerta(Puerta puerta) {
		if(this.terminal.getPuertas().containsKey(puerta.getCod()) == false || puerta.enUso() == true) {
			return false;
		}
		return true;
	}
}
