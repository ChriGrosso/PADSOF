/**
 * 
 */
package elementos;

/**
 * 
 */
public class TerminalPasajeros extends Terminal{
	private int capacidadPersonas;
	
	public TerminalPasajeros(int numeroPuertas,  String prefijoPuerta, int capacidadPersonas){
		super(numeroPuertas, prefijoPuerta);
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
