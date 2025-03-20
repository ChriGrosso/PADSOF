package aerolineas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import aviones.Avion;
import aviones.TipoAvion;
import elementos.ElementoEstructural;
import elementos.Uso;
import vuelos.Vuelo;

public class Aerolinea {
	private String id;
	private String nombre;
	private ArrayList<Vuelo> vuelos;
	private HashMap<String, Avion> aviones;
	private ArrayList<TipoAvion> tiposAviones;
	private HashMap<String, Uso> historialUsos;
	
	public Aerolinea(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vuelos = new ArrayList<Vuelo>();
		this.aviones = new HashMap<String, Avion>();
		this.tiposAviones = new ArrayList<TipoAvion>();
		this.historialUsos = new HashMap<String, Uso>();
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
	
	public boolean addUso(LocalDateTime horaUso, ElementoEstructural elem) {
		Uso u = new Uso(horaUso, elem);
		if(elem.addUso(horaUso) == false) {
			return false;
		}
		this.historialUsos.put(elem.getId(), u);
		return true;
	}
	
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
		return;
	}
}
