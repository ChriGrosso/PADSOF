package aerolineas;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import aeropuertos.Aeropuerto;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

/**
 * Clase que permite generar y manejar estadísticas relacionadas con los vuelos de una aerolínea.
 * Proporciona métodos para calcular información como la cantidad de vuelos en tiempo, vuelos retrasados
 * y el retraso medio en diferentes contextos (por mes, por aeropuerto, por franja horaria).
 * 
 * Esta clase actúa sobre un objeto {@link Aerolinea} y realiza cálculos utilizando los datos de los vuelos 
 * de esa aerolínea.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class EstadisticasVuelos implements Serializable{
	private static final long serialVersionUID = 1L;
	private Aerolinea aerolinea;
	
	/**
	 * Constructor de la clase EstadisticasVuelos.
	 *
	 * @param aerolinea La aerolínea sobre la cual se calcularán las estadísticas.
	 */
	public EstadisticasVuelos(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	
	/**
	 * Devuelve una lista de vuelos que llegaron o salieron en tiempo, sin retraso.
	 *
	 * @return Una lista de vuelos en tiempo.
	 */
	public ArrayList<Vuelo> vuelosEnTiempo() {
		ArrayList<Vuelo> vuelosEnTiempo = new ArrayList<>();
		for(Vuelo v: this.aerolinea.getVuelos()) {
			if(v.calcularRetraso() == 0) {
				vuelosEnTiempo.add(v);
			}
		}
		return vuelosEnTiempo;
	}
	
	/**
	 * Calcula el número total de vuelos que llegaron o salieron en tiempo.
	 *
	 * @return El número de vuelos en tiempo.
	 */
	public int numVuelosEnTiempo() {
		return this.vuelosEnTiempo().size();
	}
	
	/**
	 * Devuelve una lista de vuelos que tuvieron algún tipo de retraso.
	 *
	 * @return Una lista de vuelos retrasados.
	 */
	public ArrayList<Vuelo> vuelosRetrasados() {
		ArrayList<Vuelo> vuelosRetrasados = new ArrayList<>();
		for(Vuelo v: this.aerolinea.getVuelos()) {
			if(v.calcularRetraso() > 0 || v.getEstVuelo() == EstadoVuelo.RETRASADO) {
				vuelosRetrasados.add(v);
			}
		}
		return vuelosRetrasados;
	}
	
	/**
	 * Calcula el número total de vuelos que llegaron o salieron con retraso.
	 *
	 * @return El número de vuelos retrasados.
	 */
	public int numVuelosRetrasados() {
		return this.vuelosRetrasados().size();
	}
	
	/**
	 * Calcula el retraso medio en minutos para los vuelos en un mes específico.
	 *
	 * @param month El mes en el que se quieren obtener las estadísticas.
	 * 
	 * @return El retraso medio en minutos durante el mes.
	 */
	public long retrasoMedioPorMesMinutos(Month month) {
		long retrasoMedioMinutos = 0;
		ArrayList<Vuelo> vuelosRetrasados = vuelosRetrasados();
		long i = 0;
		for(Vuelo v: vuelosRetrasados) {
			if(v.getHoraSalida().getMonth() == month || v.getHoraLlegada().getMonth() == month) {
				retrasoMedioMinutos += v.calcularRetraso();
				i++;
			}
		}
		if(i == 0) {
			return 0;
		}
		return retrasoMedioMinutos/i;
	}
	
	/**
	 * Calcula el retraso medio en minutos para los vuelos entre un aeropuerto de origen y destino específicos.
	 *
	 * @param origen   El aeropuerto de origen.
	 * @param destino  El aeropuerto de destino.
	 * @return El retraso medio en minutos para los vuelos entre los aeropuertos dados.
	 */
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
		if(i == 0) {
			return 0;
		}
		return retrasoMedioMinutos/i;
	}
	
	/**
	 * Calcula el retraso medio en minutos para los vuelos en una franja horaria específica.
	 *
	 * @param inicio La hora de inicio de la franja horaria.
	 * @param fin    La hora de fin de la franja horaria.
	 * @return El retraso medio en minutos durante la franja horaria dada.
	 */
	public long retrasoMedioPorFranjaHorariaMinutos(LocalTime inicio, LocalTime fin) {
		long retrasoMedioMinutos = 0;
		ArrayList<Vuelo> vuelosRetrasados = vuelosRetrasados();
		int i = 0;
		for(Vuelo v: vuelosRetrasados) {
			if((v.getHoraSalida().toLocalTime().isAfter(inicio) || v.getHoraSalida().toLocalTime().equals(inicio)) 
				&& (v.getHoraLlegada().toLocalTime().isBefore(fin) || v.getHoraLlegada().toLocalTime().equals(fin))) {
				retrasoMedioMinutos += v.calcularRetraso();
				i++;
			}
		}
		if(i == 0) {
			return 0;
		}
		return retrasoMedioMinutos/i;
	}
	
}
