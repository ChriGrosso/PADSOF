/**
 * 
 */
package elementos;

import java.time.LocalDate;

/**
 * 
 */
public abstract class LocalizacionAterrizaje extends ElementoEstructural{
	private static final long serialVersionUID = 1L;
	
	public LocalizacionAterrizaje(String id, double costeph,LocalDate fchRegistro) {
		super(id,costeph,fchRegistro);
		
	}
	
}
