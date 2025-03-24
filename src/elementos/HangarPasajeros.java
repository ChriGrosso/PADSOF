package elementos;

import java.time.LocalDate;

/**
 * Clase que representa un hangar para aviones de pasajeros.
 * Extiende la clase general Hangar sin añadir atributos adicionales,
 * ya que solo se especializa en el tipo de avión que puede alojar.
 */
public class HangarPasajeros extends Hangar {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor del hangar para pasajeros.
     * @param id identificador único del hangar
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de alta en el sistema
     * @param numPlazas número de plazas disponibles en el hangar
     * @param alturaPlaza altura máxima permitida por plaza
     * @param anchuraPlaza anchura máxima permitida por plaza
     * @param largoPlaza largo máximo permitido por plaza
     */
    public HangarPasajeros(String id, double costeph, LocalDate fchRegistro,
                           int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza) {
        super(id, costeph, fchRegistro, numPlazas, alturaPlaza, anchuraPlaza, largoPlaza);
    }
}
