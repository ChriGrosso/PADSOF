/**
 * 
 */
package elementos;
import aerolineas.Avion;

/**
 * 
 */
public class Finger {
	private double alturaMax;
	
	public Finger(double alturaMax) {
		this.setAlturaMax(alturaMax);
	}
	
	public boolean enUso() {
		return false;
	}
	
	public boolean comprobarCompatibilidad(Avion a) {
		return false;
	}

	/**
	 * @return the alturaMax
	 */
	public double getAlturaMax() {
		return alturaMax;
	}

	/**
	 * @param alturaMax the alturaMax to set
	 */
	public void setAlturaMax(double alturaMax) {
		this.alturaMax = alturaMax;
	}

}
