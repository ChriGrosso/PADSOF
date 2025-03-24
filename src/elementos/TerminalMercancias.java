package elementos;

import java.time.LocalDate;
import vuelos.Vuelo;
import vuelos.VueloMercancias;

/**
 * Clase que representa una terminal especializada en la gestión de vuelos de mercancías.
 * Hereda de Terminal y añade un atributo para la capacidad total en toneladas.
 */
public class TerminalMercancias extends Terminal {
    private static final long serialVersionUID = 1L;

    private double capacidadToneladas; // Capacidad máxima de carga que puede gestionar la terminal

    /**
     * Constructor de TerminalMercancias.
     * @param id identificador de la terminal
     * @param fchRegistro fecha de registro de la terminal
     * @param numeroPuertas número total de puertas en la terminal
     * @param prefijoPuerta prefijo que se usará para las puertas
     * @param capacidadToneladas capacidad máxima en toneladas que puede albergar
     */
    public TerminalMercancias(String id, LocalDate fchRegistro, int numeroPuertas,
                              String prefijoPuerta, double capacidadToneladas) {
        super(id, fchRegistro, numeroPuertas, prefijoPuerta);
        this.setCapacidadToneladas(capacidadToneladas);
    }


    /**
     * Establece la capacidad máxima de la terminal en toneladas.
     */
    public void setCapacidadToneladas(double capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }

    /**
     * Calcula la carga total actual sumando la carga de todos los vuelos de mercancías.
     * @return carga total en toneladas
     */
    public double getCargaTotal() {
        if (this.getVuelos().isEmpty()) {
            return 0;
        }

        double total = 0;
        for (Vuelo v : this.getVuelos()) {
            total += ((VueloMercancias) v).getCarga();
        }
        return total;
    }

	@Override
	public boolean isMercancias() {
		return true;
	}

	/**
     * @return capacidad total en toneladas de la terminal
     */
	@Override
	public double getCapacidad() {
		return capacidadToneladas;
	}
}
