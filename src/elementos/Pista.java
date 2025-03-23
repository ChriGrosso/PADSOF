package elementos;
import java.time.*;
import java.util.ArrayList;

import vuelos.Vuelo;

public class Pista extends ElementoEstructural{
	private static final long serialVersionUID = 1L;
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
		return this.despegue;
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
		} else {
			this.vuelosQueSirve.addLast(v);
		}
		return;
	}
	
	public void actualizarColaVuelos() {
		if(this.vuelosQueSirve.isEmpty()) {
			this.usando = null;
			return;
		}
		Vuelo siguiente = this.vuelosQueSirve.getFirst();
		this.usando = siguiente;
		this.vuelosQueSirve.removeFirst();
		return;
	}
}
