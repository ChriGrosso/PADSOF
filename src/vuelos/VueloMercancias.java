package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import elementos.Terminal;

public class VueloMercancias extends Vuelo{
	private double carga;
	private boolean mercanciasPeligrosas;

	public VueloMercancias(String id, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, double carga, boolean mercanciasPeligrosas, 
			Periodicidad periodicidad, Avion avion) {
		super(id, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if(avion.getTipoAvion() instanceof AvionMercancias) {
			throw new IllegalArgumentException("Un vuelo de mercancías debe tener un avión para mercancías\n");
		}
		this.carga = carga;
		this.mercanciasPeligrosas = mercanciasPeligrosas;
	}

	public double getCarga() {
		return this.carga;
	}
	
	public boolean getMercanciasPeligrosas() {
		return this.mercanciasPeligrosas;
	}

	@Override
	public boolean asignarTerminal(Terminal terminal) {
		// TODO Auto-generated method stub
		return false;
	}
}
