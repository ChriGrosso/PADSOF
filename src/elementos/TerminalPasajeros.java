package elementos;

import java.time.LocalDate;
import vuelos.Vuelo;
import vuelos.VueloPasajeros;

/**
 * Clase que representa una terminal para vuelos de pasajeros.
 * Hereda de Terminal y añade información sobre la capacidad total de personas
 * que puede gestionar.
 */
public class TerminalPasajeros extends Terminal {
    private static final long serialVersionUID = 1L;

    private int capacidadPersonas; // Capacidad máxima en número de personas

    /**
     * Constructor de TerminalPasajeros.
     * @param id identificador único de la terminal
     * @param fchRegistro fecha de alta en el sistema
     * @param numeroPuertas número total de puertas
     * @param prefijoPuerta prefijo utilizado para nombrar las puertas
     * @param capacidadPersonas capacidad total de personas admitidas en la terminal
     */
    public TerminalPasajeros(String id, LocalDate fchRegistro, int numeroPuertas,
                             String prefijoPuerta, int capacidadPersonas) {
        super(id, fchRegistro, numeroPuertas, prefijoPuerta);
        this.setCapacidadPersonas(capacidadPersonas);
    }

    /**
     * @return capacidad máxima de personas que admite la terminal
     */
    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    /**
     * Establece la capacidad máxima de personas de la terminal.
     */
    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    /**
     * Calcula el número total de pasajeros presentes en todos los vuelos asignados a la terminal.
     * @return número total de pasajeros
     */
    public int getPasajerosTotal() {
        if (this.getVuelos().isEmpty()) {
            return 0;
        }

        int total = 0;
        for (Vuelo v : this.getVuelos()) {
            total += ((VueloPasajeros) v).getNumPasajeros();
        }
        return total;
    }
}
