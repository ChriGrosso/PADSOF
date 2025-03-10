/**
 * 
 */
package elementos;

/**
 * 
 */
public class Puerta {
	private String cod;
	
	public Puerta(String cod) {
		this.setCod(cod);
	}
	
	public boolean enUso() {
		return false;
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
}
