package elementos;

import java.time.LocalDate;
import vuelos.Vuelo;

/**
 * Clase que representa una puerta de embarque o desembarque en una terminal.
 * Puede estar asignada a un único vuelo a la vez y hereda de ElementoEstructural.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class Puerta extends ElementoEstructural {
    private static final long serialVersionUID = 1L;

    private Vuelo vueloAsig = null; // Vuelo actualmente asignado a la puerta

    /**
     * Constructor de la puerta.
     * @param cod código identificador de la puerta
     * @param fchRegistro fecha de alta de la puerta
     */
    public Puerta(String cod, LocalDate fchRegistro) {
        super(cod, 0, fchRegistro);
    }

    /**
     * Indica si la puerta está actualmente en uso (es decir, asignada a un vuelo).
     * @return true si está ocupada, false si está libre
     */
    public boolean enUso() {
        return this.vueloAsig != null;
    }

    /**
     * Devuelve el código de la puerta (alias del ID).
     */
    public String getCod() {
        return this.getId();
    }

    /**
     * Establece el código de la puerta (alias del ID).
     */
    public void setCod(String cod) {
        this.setId(cod);
    }

    /**
     * Asigna un vuelo a esta puerta.
     * @param v vuelo que va a utilizar la puerta
     */
    public void setVuelo(Vuelo v) {
        this.vueloAsig = v;
    }

    /**
     * Libera la puerta, eliminando el vuelo asignado.
     */
    public void liberarPuerta() {
        this.vueloAsig = null;
    }
}
