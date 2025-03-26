package elementos;

import java.time.LocalDate;
import java.util.ArrayList;

import aviones.Avion;

/**
 * Clase abstracta que representa un hangar en el aeropuerto.
 * Un hangar puede albergar varios aviones, siempre que sus dimensiones sean compatibles.
 * Hereda de ElementoEstructural.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public abstract class Hangar extends ElementoEstructural {
    private static final long serialVersionUID = 1L;

    private int numPlazas;         // Número total de plazas disponibles en el hangar
    private double alturaPlaza;    // Altura máxima permitida por plaza
    private double anchuraPlaza;   // Anchura máxima permitida por plaza
    private double largoPlaza;     // Largo máximo permitido por plaza
    private ArrayList<Avion> aviones;

    /**
     * Constructor de Hangar.
     * @param id identificador del hangar
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de alta del hangar
     * @param numPlazas número de plazas disponibles
     * @param alturaPlaza altura máxima permitida por plaza
     * @param anchuraPlaza anchura máxima permitida por plaza
     * @param largoPlaza largo máximo permitido por plaza
     */
    public Hangar(String id, double costeph, LocalDate fchRegistro, int numPlazas,
                  double alturaPlaza, double anchuraPlaza, double largoPlaza) {
        super(id, costeph, fchRegistro);
        this.setAlturaPlaza(alturaPlaza);
        this.setNumPlazas(numPlazas);
        this.setAnchuraPlaza(anchuraPlaza);
        this.setLargoPlaza(largoPlaza);
        this.aviones = new ArrayList<Avion>();
    }

    /**
     * Devuelve el número de plazas actualmente ocupadas en el hangar.
     * (Método no implementado todavía)
     * @return 0 por defecto
     */
    public int numPlazasOcupadasHangar() {
        return this.aviones.size();
    }

    /**
     * Verifica si un avión puede entrar al hangar según sus dimensiones.
     * @param avion avión a comprobar
     * @return true si cabe en la plaza, false si no
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
    
    public ArrayList<Avion> getAviones() {
    	return this.aviones;
    }
    
    public void addAvion(Avion av) {
    	this.aviones.add(av);
    	return;
    }
    
    public void removeAvion(Avion av) {
    	this.aviones.remove(av);
    	return;
    }
}
