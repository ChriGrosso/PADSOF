package aerolineas;

import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import aeropuertos.Aeropuerto;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

public class EstadisticasVuelos {
	private Aerolinea aerolinea;
	
	public EstadisticasVuelos(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	
	public ArrayList<Vuelo> vuelosEnTiempo() {
		ArrayList<Vuelo> vuelosEnTiempo = new ArrayList<>();
		for(Vuelo v: this.aerolinea.getVuelos()) {
			if(v.calcularRetraso() == 0) {
				vuelosEnTiempo.add(v);
			}
		}
		return vuelosEnTiempo;
	}
	
	public int numVuelosEnTiempo() {
		return this.vuelosEnTiempo().size();
	}
	
	public ArrayList<Vuelo> vuelosRetrasados() {
		ArrayList<Vuelo> vuelosRetrasados = new ArrayList<>();
		for(Vuelo v: this.aerolinea.getVuelos()) {
			if(v.calcularRetraso() > 0 || v.getEstVuelo() == EstadoVuelo.RETRASADO) {
				vuelosRetrasados.add(v);
			}
		}
		return vuelosRetrasados;
	}
	
	public int numVuelosRetrasados() {
		return this.vuelosRetrasados().size();
	}
	
	public long retrasoMedioPorMesMinutos(Month month) {
		long retrasoMedioMinutos = 0;
		ArrayList<Vuelo> vuelosRetrasados = vuelosRetrasados();
		int i = 0;
		for(Vuelo v: vuelosRetrasados) {
			if(v.getHoraSalida().getMonth() == month) {
				retrasoMedioMinutos += v.calcularRetraso();
				i++;
			}
		}
		return retrasoMedioMinutos/i;
	}
	
	public long retrasoMedioPorVueloMinutos(Aeropuerto origen, Aeropuerto destino) {
		long retrasoMedioMinutos = 0;
		ArrayList<Vuelo> vuelosRetrasados = vuelosRetrasados();
		int i = 0;
		for(Vuelo v: vuelosRetrasados) {
			if(v.getOrigen().getCodigo() == origen.getCodigo() && v.getDestino().getCodigo() == destino.getCodigo()) {
				retrasoMedioMinutos += v.calcularRetraso();
				i++;
			}
		}
		return retrasoMedioMinutos/i;
	}
	
	public long retrasoMedioPorFranjaHorariaMinutos(LocalTime inicio, LocalTime fin) {
		long retrasoMedioMinutos = 0;
		ArrayList<Vuelo> vuelosRetrasados = vuelosRetrasados();
		int i = 0;
		for(Vuelo v: vuelosRetrasados) {
			if((v.getLlegada() == false && v.getHoraSalida().toLocalTime().isAfter(inicio)) || 
				(v.getLlegada() == true && v.getHoraLlegada().toLocalTime().isBefore(fin))) {
				retrasoMedioMinutos += v.calcularRetraso();
				i++;
			}
		}
		return retrasoMedioMinutos/i;
	}
}
