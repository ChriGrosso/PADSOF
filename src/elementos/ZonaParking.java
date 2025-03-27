package elementos;

import java.time.LocalDate;
import java.util.ArrayList;

import aviones.*;
import vuelos.Vuelo;

/**
 * Clase que representa una zona de parking para aviones.
 * Hereda de LocalizacionAterrizaje y define el espacio disponible por plaza.
 */
public class ZonaParking extends LocalizacionAterrizaje {
    private static final long serialVersionUID = 1L;

    private int numPlazas;         // Número total de plazas disponibles en la zona
    private double alturaPlaza;    // Altura máxima permitida por plaza
    private double anchuraPlaza;   // Anchura máxima permitida por plaza
    private double largoPlaza;     // Largo máximo permitido por plaza
    private ArrayList<Vuelo> vuelos;

    /**
     * Constructor de la zona de parking.
     * @param id identificador de la zona
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de registro
     * @param numPlazas número total de plazas
     * @param alturaPlaza altura máxima permitida por plaza
     * @param anchuraPlaza anchura máxima permitida por plaza
     * @param largoPlaza largo máximo permitido por plaza
     */
    public ZonaParking(String id, double costeph, LocalDate fchRegistro,
                       int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza) {
        super(id, costeph, fchRegistro);
        this.alturaPlaza = alturaPlaza;
        this.anchuraPlaza = anchuraPlaza;
        this.numPlazas = numPlazas;
        this.largoPlaza = largoPlaza;
        this.vuelos = new ArrayList<Vuelo>();
    }

    /**
     * Devuelve el número de plazas ocupadas (no implementado aún).
     * @return 0 por defecto
     */
    public int numPlazasOcupadasPark() {
        return this.vuelos.size();
    }

    /**
     * Comprueba si un avión es compatible con las dimensiones de una plaza.
     * @param avion avión a comprobar
     * @return true si cabe, false si no
     */
    public boolean comprobarCompatibilidad(Avion avion) {
        return avion.getTipoAvion().getAltura() <= this.alturaPlaza &&
               avion.getTipoAvion().getAnchura() <= this.anchuraPlaza &&
               avion.getTipoAvion().getLargo() <= this.largoPlaza;
    }

    // Getters y setters

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public double getAlturaPlaza() {
        return alturaPlaza;
    }

    public void setAlturaPlaza(double alturaPlaza) {
        this.alturaPlaza = alturaPlaza;
    }

    public double getAnchuraPlaza() {
        return anchuraPlaza;
    }

    public void setAnchuraPlaza(double anchuraPlaza) {
        this.anchuraPlaza = anchuraPlaza;
    }

    public double getLargoPlaza() {
        return largoPlaza;
    }

    public void setLargoPlaza(double largoPlaza) {
        this.largoPlaza = largoPlaza;
    }
    
    public ArrayList<Vuelo> getVuelos() {
    	return this.vuelos;
    }
    
    public void addVuelo(Vuelo v) {
    	this.vuelos.add(v);
    	return;
    }
    
    public void removeVuelo(Vuelo v) {
    	this.vuelos.remove(v);
    	return;
    }
}
