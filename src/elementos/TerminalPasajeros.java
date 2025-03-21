/**
 * 
 */
package elementos;

import java.time.LocalDate;
import vuelos.Vuelo;
import vuelos.VueloPasajeros;

/**
 * 
 */
public class TerminalPasajeros extends Terminal{
	private int capacidadPersonas;
	
	public TerminalPasajeros(String id, LocalDate fchRegistro,int numeroPuertas,  String prefijoPuerta, int capacidadPersonas){
		super(id, fchRegistro, numeroPuertas, prefijoPuerta);
		this.setCapacidadPersonas(capacidadPersonas);
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
		if(this.getVuelos().isEmpty()) {
			return 0;
		}
		int total = 0;
		for(Vuelo v: this.getVuelos()) {
			total = ((VueloPasajeros) v).getNumPasajeros();
		}
		return total;
	}
}
