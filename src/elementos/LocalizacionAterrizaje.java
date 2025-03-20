/**
 * 
 */
package elementos;

import java.time.LocalDate;
import java.util.ArrayList;

import vuelos.Vuelo;

/**
 * 
 */
public abstract class LocalizacionAterrizaje extends ElementoEstructural{
	private ArrayList<Vuelo> vuelosQueSirve;
	
	public LocalizacionAterrizaje(String id, double costeph,LocalDate fchRegistro) {
		super(id,costeph,fchRegistro);
		
	}
	
}
