/**
 * 
 */
package elementos;

import java.time.LocalDate;
import java.util.ArrayList;

import vuelos.VueloPasajeros;

/**
 * 
 */
public class TerminalPasajeros extends Terminal{
	private int capacidadPersonas;
	private ArrayList<VueloPasajeros> vuelos; 
	
	public TerminalPasajeros(String id, double costeph,LocalDate fchRegistro,int numeroPuertas,  String prefijoPuerta, int capacidadPersonas){
		super(id, costeph, fchRegistro, numeroPuertas, prefijoPuerta);
		this.setCapacidadPersonas(capacidadPersonas);
		this.vuelos = new ArrayList<VueloPasajeros>();
	}

	/**
	 * @return the capacidadPersonas
	 */
	public int getCapacidadPersonas() {
		return capacidadPersonas;
	}

	/**
	 * @param capacidadPersonas the capacidadPersonas to set
	 */
	public void setCapacidadPersonas(int capacidadPersonas) {
		this.capacidadPersonas = capacidadPersonas;
	}
	
	public int getPasajerosTotal() {
		if(this.vuelos.isEmpty()) {
			return 0;
		}
		int total = 0;
		for(VueloPasajeros v: this.vuelos) {
			total = v.getNumPasajeros();
		}
		return total;
	}
}
