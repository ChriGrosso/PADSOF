package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import elementos.Puerta;
import elementos.TerminalMercancias;

public class VueloMercancias extends Vuelo{
	private TerminalMercancias terminal;
	private Puerta puerta;
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
	
	public TerminalMercancias getTerminal() {
		return this.terminal;
	}
	
	public Puerta getPuerta() {
		return this.puerta;
	}
	

	public boolean asignarTerminal(TerminalMercancias terminal) {
		if(terminal.numPuertasOcupadasTerm() == terminal.getNumeroPuertas() || terminal.getCargaTotal()+this.carga > terminal.getCapacidadToneladas()) {
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
