package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionPasajeros;
import elementos.Terminal;

public class VueloPasajeros extends Vuelo{
	private int numPasajeros;

	public VueloPasajeros(String id, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, int numPasajeros, Periodicidad periodicidad,
			Avion avion) {
		super(id, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if(avion.getTipoAvion() instanceof AvionPasajeros) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe tener un avión para pasajeros\n");
		}
		this.numPasajeros = numPasajeros;
	}

	public int getNumPasajeros() {
		return this.numPasajeros;
	}

	@Override
	public boolean asignarTerminal(Terminal terminal) {
		// TODO Auto-generated method stub
		return false;
	}
}
