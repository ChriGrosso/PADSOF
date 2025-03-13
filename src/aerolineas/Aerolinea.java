package aerolineas;

import java.util.ArrayList;
import java.util.HashMap;
import aviones.Avion;
import aviones.TipoAvion;
import vuelos.Vuelo;

public class Aerolinea {
	private String id;
	private String nombre;
	private ArrayList<Vuelo> vuelos;
	private HashMap<String, Avion> aviones;
	private ArrayList<TipoAvion> tiposAviones;
	
	public Aerolinea(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vuelos = new ArrayList<Vuelo>();
		this.aviones = new HashMap<String, Avion>();
		this.tiposAviones = new ArrayList<TipoAvion>();
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
}
