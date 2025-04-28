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
    private static int contador = 0;

    private static String generarNuevoId() {
        String id = String.format("ZP%04d", contador);
        contador++;
        return id;
    }

    public static void setContador(int nuevoContador) {
        contador = nuevoContador;
    }


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
    
    public ZonaParking(int numPlazas, double alturaPlaza, double anchuraPlaza, double largoPlaza) {
        super(generarNuevoId(), 0.0, LocalDate.now());
        this.numPlazas = numPlazas;
        this.alturaPlaza = alturaPlaza;
        this.anchuraPlaza = anchuraPlaza;
        this.largoPlaza = largoPlaza;
        this.vuelos = new ArrayList<>();
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
     * Obtiene la lista de vuelos asociados.
     * 
     * @return Lista de vuelos.
     */
    public ArrayList<Vuelo> getVuelos() {
    	return this.vuelos;
    }
    
    /**
     * Agrega un vuelo a la lista de vuelos.
     * 
     * @param v Vuelo a agregar.
     */
    public void addVuelo(Vuelo v) {
    	this.vuelos.add(v);
    	return;
    }
    
    /**
     * Elimina un vuelo de la lista de vuelos.
     * 
     * @param v Vuelo a eliminar.
     */
    public void removeVuelo(Vuelo v) {
    	this.vuelos.remove(v);
    	return;
    }
}
