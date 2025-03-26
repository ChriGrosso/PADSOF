package elementos;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    /**
     * Calcula el número total de pasajeros presentes en todos los vuelos asignados a la terminal.
     * @param r1 Extremo inferior del rango de tiempo
     * @param r2 Extremo superior del rango de tiempo
     * @return número total de pasajeros
     */
    public int getPasajerosTotal(LocalDateTime r1, LocalDateTime r2) {
        if (this.getVuelos().isEmpty()) {
            return 0;
        }

        int total = 0;
        for (Vuelo v : this.getVuelos()) {
        	if (v.getLlegada() && v.getHoraLlegada().isBefore(r2) && v.getHoraLlegada().isAfter(r1)) {
        		 total += ((VueloPasajeros) v).getNumPasajeros();
        	}
        	if (!v.getLlegada() && v.getHoraSalida().isBefore(r2) && v.getHoraSalida().isAfter(r1)) {
        		 total += ((VueloPasajeros) v).getNumPasajeros();
        	}
        }
        return total;
    }
    
    /**
     * Determina si una terminal es de mercancias
     * 
     * @return false, no es de mercancias
     */
	@Override
	public boolean isMercancias() {
		return false;
	}

	/**
     * @return capacidad máxima de personas que admite la terminal
     */
	@Override
	public double getCapacidad() {
		return capacidadPersonas;
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
		return this.capacidadPersonas-this.getPasajerosTotal(r1, r2);
	}


	@Override
	public double getCapacidadOcup() {
		return this.getPasajerosTotal();
	}
}
