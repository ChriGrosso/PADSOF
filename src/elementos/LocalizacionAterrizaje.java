package elementos;

import java.time.LocalDate;

/**
 * Clase abstracta que representa una localización donde pueden aterrizar o estacionarse los aviones,
 * como pistas, puertas o fingers. Hereda de ElementoEstructural.
 */
public abstract class LocalizacionAterrizaje extends ElementoEstructural {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de LocalizacionAterrizaje.
     * @param id identificador único del recurso
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de alta del recurso
     */
    public LocalizacionAterrizaje(String id, double costeph, LocalDate fchRegistro) {
        super(id, costeph, fchRegistro);
    }
}
