/**
 * 
 */
package elementos;
import java.time.LocalDate;
import aviones.Avion;

/**
 * 
 */
public class Finger extends LocalizacionAterrizaje{
	private double alturaMax;
	
	public Finger(String id, double costeph,LocalDate fchRegistro, double alturaMax) {
		super(id,costeph,fchRegistro);
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
