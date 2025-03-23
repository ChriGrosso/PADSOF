
package aerolineas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

import aeropuertos.Aeropuerto;
import aviones.Avion;
import aviones.TipoAvion;
import elementos.ElementoEstructural;
import elementos.Uso;
import usuarios.Usuario;
import vuelos.Vuelo;

public class Aerolinea implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private ArrayList<Vuelo> vuelos;
	private HashMap<String, Avion> aviones;
	private ArrayList<TipoAvion> tiposAviones;
	private HashMap<ClaveVueloElemento, Uso> historialUsos;
	private EstadisticasVuelos estadisticasVuelos;
	
	public Aerolinea(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vuelos = new ArrayList<Vuelo>();
		this.aviones = new HashMap<String, Avion>();
		this.tiposAviones = new ArrayList<TipoAvion>();
		this.historialUsos = new HashMap<ClaveVueloElemento, Uso>();
		this.estadisticasVuelos = new EstadisticasVuelos(this);
	}
	
	
	public String getId()  {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public ArrayList<Vuelo> getVuelos() {
		return this.vuelos;
	}
	
	public HashMap<String, Avion> getAviones() {
		return this.aviones;
	}
	
	public ArrayList<TipoAvion> getTiposAvion() {
		return this.tiposAviones;
	}
	
	
	public boolean addVuelo(Vuelo v) {
		if(this.vuelos.contains(v)) {
			return false;
		}
		this.vuelos.add(v);
		return true;
	}
	
	public boolean addAvion(Avion a) {
		if(this.aviones.containsKey(a.getMatricula())) {
			return false;
		}
		this.aviones.put(a.getMatricula(), a);
		return true;
	}
	
	public boolean addTipoAvion(TipoAvion ta) {
		if(this.tiposAviones.contains(ta)) {
			return false;
		}
		this.tiposAviones.add(ta);
		return true;
	}
	
	public boolean addUso(LocalDateTime horaUso, Vuelo vuelo, ElementoEstructural elem) {
		Uso u = new Uso(horaUso, elem);
		ClaveVueloElemento clave = new ClaveVueloElemento(vuelo, elem);
		if(elem.addUso(horaUso) == false) {
			return false;
		}
		this.historialUsos.put(clave, u);
		return true;
	}
	
	public boolean setEndUso(LocalDateTime horaDesuso, Vuelo vuelo, ElementoEstructural elem) {
		ClaveVueloElemento clave = new ClaveVueloElemento(vuelo, elem);
		if(this.historialUsos.containsKey(clave) == false) {
			return false;
		}
		this.historialUsos.get(clave).setHoraDesuso(horaDesuso);
		return true;
	}
	
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
		return;
	}
	
	public String verEstadisticasOperadorEnTiempo(Usuario user) {
		if(user.esOperador() == false) { return null; }
		return this.estadisticasVuelos.vuelosEnTiempoToString();
	}
	
	public String verEstadisticasOperadorRetrasados(Usuario user) {
		if(user.esOperador() == false) { return null; }
		return this.estadisticasVuelos.vuelosRetrasadosToString();
	}
	
	public String verEstadisticasOperadorRetrasoMes(Usuario user, Month month) {
		if(user.esOperador() == false) { return null; }
		return this.estadisticasVuelos.retrasoMedioMesToString(month);
	}
	
	public String verEstadisticasOperadorRetrasoVuelo(Usuario user, Aeropuerto origen, Aeropuerto destino) {
		if(user.esOperador() == false) { return null; }
		return this.estadisticasVuelos.retrasoMedioVueloToString(origen, destino);
	}
	
	public String verEstadisticasOperadorRetrasoFranjaHoraria(Usuario user, LocalTime inicio, LocalTime fin) {
		if(user.esOperador() == false) { return null; }
		return this.estadisticasVuelos.retrasoMedioFranjaHToString(inicio, fin);
	}
}
