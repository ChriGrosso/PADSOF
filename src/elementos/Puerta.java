/**
 * 
 */
package elementos;

import java.time.LocalDate;

import vuelos.Vuelo;

/**
 * 
 */
public class Puerta extends ElementoEstructural{
	private Vuelo vueloAsig = null;
	
	public Puerta(String cod, LocalDate fchRegistro) {
		super(cod, 0, fchRegistro);
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
		return this.getId();
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(String cod) {
		this.setId(cod);
	}
	
	public void setVuelo(Vuelo v) {
		this.vueloAsig = v;
	}
	
	public void liberarPuerta() {
		this.vueloAsig = null;
	}
}
