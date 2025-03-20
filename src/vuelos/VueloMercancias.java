package vuelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import elementos.Puerta;
import elementos.TerminalMercancias;
import sistema.Aeropuerto;

public class VueloMercancias extends Vuelo{
	private TerminalMercancias terminal = null;
	private Puerta puertaCarga = null;
	private double carga;
	private boolean mercanciasPeligrosas;

	public VueloMercancias(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolinea, boolean llegada, double carga, boolean mercanciasPeligrosas, 
			Periodicidad periodicidad, Avion avion) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if((avion.getTipoAvion() instanceof AvionMercancias) == false) {
			throw new IllegalArgumentException("Un vuelo de mercancías debe tener un avión para mercancías\n");
		}
		this.carga = carga;
		this.mercanciasPeligrosas = mercanciasPeligrosas;
	}

	public VueloMercancias(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			Aerolinea aerolinea, boolean llegada, double carga, boolean mercanciasPeligrosas, 
			Periodicidad periodicidad, Avion avion) {
		super(id, origen, destino, horaSalida, horaLlegada, aerolinea, llegada, periodicidad, avion);
		if((avion.getTipoAvion() instanceof AvionMercancias) == false) {
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
		return this.puertaCarga;
	}
	

	public boolean asignarTerminal(TerminalMercancias terminal) {
		if(terminal.numPuertasOcupadasTerm() == terminal.getNumeroPuertas() || terminal.getCargaTotal()+this.carga > terminal.getCapacidadToneladas()) {
			return false;
		}
		this.terminal = terminal;
		return true;
	}
	
	public boolean asignarPuerta(String puertaCod) {
		Puerta puerta = this.terminal.getPuertas().get(puertaCod);
		if(this.terminal == null) {
			throw new IllegalArgumentException("No se puede asignar puerta de carga a un vuelo sin terminal");
		}
		if(this.terminal.getPuertas().containsKey(puertaCod) == false || puerta.enUso() == true) {
			return false;
		}
		this.puertaCarga = puerta;
		return true;
	}
}
