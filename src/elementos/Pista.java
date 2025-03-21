package elementos;
import java.time.*;
import java.util.ArrayList;

import vuelos.Vuelo;

public class Pista extends ElementoEstructural{
	
	private boolean despegue;
	private int longitud;
	private ArrayList<Vuelo> vuelosQueSirve; 
	private Vuelo usando;
	
	public Pista(String id, LocalDate fchRegistro, boolean despegue, int longitud) {
		super(id,0,fchRegistro);
		this.setDespegue(despegue);
		this.setLongitud(longitud);
		this.vuelosQueSirve = new ArrayList<Vuelo>();
	}
	
	public boolean enUso() {
		return false;
	}

	/**
	 * @return the despegue
	 */
	public boolean isDespegue() {
		return despegue;
	}

	/**
	 * @param despegue the despegue to set
	 */
	public void setDespegue(boolean despegue) {
		this.despegue = despegue;
	}

	/**
	 * @return the longitud
	 */
	public int getLongitud() {
		return longitud;
	}
	
	public Vuelo getUsando() {
		return this.usando;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	
	public ArrayList<Vuelo> getVuelos() {
		return this.vuelosQueSirve;
	}
	
	public void addVuelo(Vuelo v) {
		if(this.usando == null) {
			this.usando = v;
			this.addUso(LocalDateTime.now());
		} else {
			this.vuelosQueSirve.addLast(v);
		}
		return;
	}
	
	public void actualizarColaVuelos() {
		if(this.vuelosQueSirve.isEmpty()) {
			return;
		}
		Vuelo siguiente = this.vuelosQueSirve.getFirst();
		this.usando = siguiente;
		this.vuelosQueSirve.removeFirst();
		return;
	}
}
