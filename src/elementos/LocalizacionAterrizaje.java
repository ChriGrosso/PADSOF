/**
 * 
 */
package elementos;

import java.time.LocalDateTime;

import vuelos.Vuelo;

/**
 * 
 */
public abstract class LocalizacionAterrizaje extends ElementoEstructural{
	
	private Vuelo vueloQueSirve;
	
	public LocalizacionAterrizaje(String id, double costeph,LocalDateTime fchRegistro) {
		super(id,costeph,fchRegistro);
		
	}
	
}
