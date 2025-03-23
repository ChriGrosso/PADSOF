package aerolineas;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import aeropuertos.Aeropuerto;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

public class EstadisticasVuelos implements Serializable{
	private static final long serialVersionUID = 1L;
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
	
	public String vuelosEnTiempoToString() {
		String estadisticas = "De los " + this.aerolinea.getVuelos().size() + " vuelos de la aerolinea "
				+ this.aerolinea.getNombre() + " tenemos:\n";
		ArrayList<Vuelo> vuelos = null;
		vuelos = this.vuelosEnTiempo();
		estadisticas += "Ha habido " + vuelos.size() + " vuelos que han llegado a tiempo.\n";
		for (Vuelo v: vuelos) {
			estadisticas += v.toString() + "\n";
		}
		return estadisticas;
	}
	
	public String vuelosRetrasadosToString() {
		String estadisticas = "De los " + this.aerolinea.getVuelos().size() + " vuelos de la aerolinea "
				+ this.aerolinea.getNombre() + " tenemos:\n";
		ArrayList<Vuelo> vuelos = null;
		vuelos = this.vuelosRetrasados();
		estadisticas += "Ha habido " + vuelos.size() + " vuelos retrasados.\n";
		for (Vuelo v: vuelos) {
			estadisticas += v.toString() + "\n";
		}
		return estadisticas;
	}
	
	public String retrasoMedioMesToString(Month month) {
		String estadisticas = "De los " + this.aerolinea.getVuelos().size() + " vuelos de la aerolinea "
				+ this.aerolinea.getNombre() + " tenemos:\n";
		long retraso = this.retrasoMedioPorMesMinutos(month);
		estadisticas += "Ha habido un retraso medio de " + retraso + " minutos en el mes de "
				+ month.toString() + "\n";
		return estadisticas;
	}
	
	public String retrasoMedioVueloToString(Aeropuerto origen, Aeropuerto destino) {
		String estadisticas = "De los " + this.aerolinea.getVuelos().size() + " vuelos de la aerolinea "
				+ this.aerolinea.getNombre() + " tenemos:\n";
		long retraso = this.retrasoMedioPorVueloMinutos(origen, destino);
		estadisticas += "Ha habido un retraso medio de " + retraso + " minutos en los vuelos desde " 
						+ origen.getNombre() + " a " + destino.getNombre() + "\n";
		return estadisticas;
	}
	
	public String retrasoMedioFranjaHToString(LocalTime inicio, LocalTime fin) {
		String estadisticas = "De los " + this.aerolinea.getVuelos().size() + " vuelos de la aerolinea "
				+ this.aerolinea.getNombre() + " tenemos:\n";
		long retraso = this.retrasoMedioPorFranjaHorariaMinutos(inicio, fin);
		estadisticas += "Ha habido un retraso medio de " + retraso + " minutos en los vuelos entre las " 
						+ inicio.toString() + " y las " + fin.toString() + "\n";
		return estadisticas;
	}
}
