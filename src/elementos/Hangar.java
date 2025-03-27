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
    /**
     * Obtiene el número de plazas disponibles.
     * 
     * @return Número de plazas.
     */
    public int getNumPlazas() {
        return numPlazas;
    }
    
    /**
     * Establece el número de plazas disponibles.
     * 
     * @param numPlazas Número de plazas a establecer.
     */
    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }
    
    /**
     * Obtiene la altura de la plaza.
     * 
     * @return Altura de la plaza en metros.
     */
    public double getAlturaPlaza() {
        return alturaPlaza;
    }
    
    /**
     * Establece la altura de la plaza.
     * 
     * @param alturaPlaza Altura de la plaza en metros.
     */
    public void setAlturaPlaza(double alturaPlaza) {
        this.alturaPlaza = alturaPlaza;
    }
    
    /**
     * Obtiene la anchura de la plaza.
     * 
     * @return Anchura de la plaza en metros.
     */
    public double getAnchuraPlaza() {
        return anchuraPlaza;
    }
    
    /**
     * Establece la anchura de la plaza.
     * 
     * @param anchuraPlaza Anchura de la plaza en metros.
     */
    public void setAnchuraPlaza(double anchuraPlaza) {
        this.anchuraPlaza = anchuraPlaza;
    }
    
    /**
     * Obtiene el largo de la plaza.
     * 
     * @return Largo de la plaza en metros.
     */
    public double getLargoPlaza() {
        return largoPlaza;
    }
    
    /**
     * Establece el largo de la plaza.
     * 
     * @param largoPlaza Largo de la plaza en metros.
     */
    public void setLargoPlaza(double largoPlaza) {
        this.largoPlaza = largoPlaza;
    }
    
    /**
     * Obtiene la lista de aviones en la plaza.
     * 
     * @return Lista de aviones.
     */
    public ArrayList<Avion> getAviones() {
    	return this.aviones;
    }
    
    /**
     * Agrega un avión a la lista de aviones en la plaza.
     * 
     * @param av Avión a agregar.
     */
    public void addAvion(Avion av) {
    	this.aviones.add(av);
    	return;
    }
    
    /**
     * Elimina un avión de la lista de aviones en la plaza.
     * 
     * @param av Avión a eliminar.
     */
    public void removeAvion(Avion av) {
    	this.aviones.remove(av);
    	return;
    }
}
