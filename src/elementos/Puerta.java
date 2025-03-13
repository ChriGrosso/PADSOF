/**
 * 
 */
package elementos;

import vuelos.Vuelo;

/**
 * 
 */
public class Puerta {
	private String cod;
	private Vuelo vueloAsig = null;
	
	public Puerta(String cod) {
		this.setCod(cod);
	}
	
	public boolean enUso() {
		if(this.vueloAsig == null) {
			return false;
		}
		return true;
	}

	/**
	 * @return the cod
	 */
	public String getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public void setVuelo(Vuelo v) {
		this.vueloAsig = v;
	}
	
	public void liberarPuerta() {
		this.vueloAsig = null;
	}
}
