package elementos;

import java.time.LocalDate;

/**
 * Clase que representa un hangar destinado al almacenamiento de aviones de mercancías.
 * Extiende la clase Hangar e incluye información adicional sobre si puede albergar
 * materiales peligrosos.
 */
public class HangarMercancias extends Hangar {
    private static final long serialVersionUID = 1L;

    private boolean materialesPeligrosos; // Indica si el hangar puede almacenar mercancías peligrosas

    /**
     * Constructor del hangar de mercancías.
     * @param id identificador del hangar
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de alta del hangar
     * @param numPlazas número de plazas disponibles
     * @param alturaPlaza altura máxima por plaza
     * @param anchuraPlaza anchura máxima por plaza
     * @param largoPlaza largo máximo por plaza
     */
    public HangarMercancias(String id, double costeph, LocalDate fchRegistro,
                            int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza) {
        super(id, costeph, fchRegistro, numPlazas, alturaPlaza, anchuraPlaza, largoPlaza);
        this.setMaterialesPeligrosos(false); // Por defecto, no se permite almacenar materiales peligrosos
    }

    /**
     * Indica si el hangar acepta materiales peligrosos.
     * @return true si permite materiales peligrosos, false en caso contrario
     */
    public boolean isMaterialesPeligrosos() {
        return materialesPeligrosos;
    }

    /**
     * Establece si el hangar puede almacenar materiales peligrosos.
     * @param materialesPeligrosos valor booleano para permitir o no
     */
    public void setMaterialesPeligrosos(boolean materialesPeligrosos) {
        this.materialesPeligrosos = materialesPeligrosos;
    }
    
    public HangarMercancias(LocalDate fechaRegistro, int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza, boolean materialesPeligrosos) {
        super(generarNuevoId(), 0.0, fechaRegistro, numPlazas, alturaPlaza, anchuraPlaza, largoPlaza);
        this.materialesPeligrosos = materialesPeligrosos;
    }

}
