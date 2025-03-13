package aerolineas;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import sistema.Aeropuerto;
import vuelos.Vuelo;

public class EstadisticasVuelos {
	private Aerolinea aerolinea;
	
	public EstadisticasVuelos(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	
	public List<Vuelo> vuelosEnTiempo() {
		return null;
	}
	public int numVuelosEnTiempo() {
		return this.vuelosEnTiempo().size();
	}
	
	public List<Vuelo> vuelosRetrasados() {
		return null;
	}
	public int numVuelosRetrasados() {
		return this.vuelosRetrasados().size();
	}
	
	public Duration retrasoMedioPorMes(Month month) {
		return null;
	}
	
	public Duration retrasoMedioPorVuelo(Aeropuerto origen, Aeropuerto destino) {
		return null;
	}
	
	public Duration retrasoMedioPorFranjaHoraria(LocalTime inicio, LocalTime fin) {
		return null;
	}
}
