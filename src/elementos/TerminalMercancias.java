package elementos;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    /**
     * Calcula la carga total dentro de un rango de tiempo, sumando la carga de todos los vuelos de mercancías.
     * @param r1 Extremo inferior del rango de tiempo
     * @param r2 Extremo superior del rango de tiempo
     * @return carga total ocupada en toneladas
     */
    public double getCargaTotal(LocalDateTime r1, LocalDateTime r2) {
        if (this.getVuelos().isEmpty()) {
            return 0;
        }

        double total = 0;
        for (Vuelo v : this.getVuelos()) {
        	if (v.getLlegada() && v.getHoraLlegada().isBefore(r2) && v.getHoraLlegada().isAfter(r1)) {
        		total += ((VueloMercancias) v).getCarga();
        	}
        	if (!v.getLlegada() && v.getHoraSalida().isBefore(r2) && v.getHoraSalida().isAfter(r1)) {
        		total += ((VueloMercancias) v).getCarga();
        	}
        }
        return total;
    }
    
    /**
     * Determina si una terminal es de mercancias
     * 
     * @return true, si es de mercancias
     */
	@Override
	public boolean isMercancias() {
		return true;
	}

	/**
	 * Método para obtener la capacidad de la terminal
	 * 
     * @return capacidad total en toneladas de la terminal
     */
	@Override
	public double getCapacidad() {
		return capacidadToneladas;
	}

	/**
	 * Método para obtener la capacidad disponible de la terminal en un rango especifico de tiempo
	 * 
	 * @param r1 Extremo inferior del rango de tiempo
     * @param r2 Extremo superior del rango de tiempo
     * @return capacidad disponible en toneladas de la terminal
     */
	@Override
	public double getCapDisponible(LocalDateTime r1, LocalDateTime r2) {
		
		return this.capacidadToneladas-this.getCargaTotal(r1, r2);
	}


	@Override
	public double getCapacidadOcup() {
		return this.getCargaTotal();
	}
}
