/**
 * 
 */
package elementos;

import java.time.LocalDateTime;

/**
 * 
 */
public class TerminalPasajeros extends Terminal{
	private int capacidadPersonas;
	
	public TerminalPasajeros(String id, double costeph,LocalDateTime fchRegistro,int numeroPuertas,  String prefijoPuerta, int capacidadPersonas){
		super(id, costeph, fchRegistro, numeroPuertas, prefijoPuerta);
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
	
	

}
