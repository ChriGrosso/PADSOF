package elementos;

import java.time.LocalDate;
import aviones.Avion;
import vuelos.Vuelo;

/**
 * Clase que representa un Finger (pasarela telescópica) en un aeropuerto.
 * Hereda de LocalizacionAterrizaje y puede ser utilizado para conectar un avión
 * directamente con la terminal.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class Finger extends LocalizacionAterrizaje {
    private static final long serialVersionUID = 1L;

    private static int contador = 0;
    private double alturaMax; // Altura máxima permitida para que un avión use este finger
    private Vuelo vuelo;

    /**
     * Constructor del finger.
     * @param id identificador único del finger
     * @param costeph coste por hora de uso
     * @param fchRegistro fecha de alta en el sistema
     * @param alturaMax altura máxima permitida del avión
     */
    public Finger(String id, double costeph, LocalDate fchRegistro, double alturaMax) {
        super(id, costeph, fchRegistro);
        this.setAlturaMax(alturaMax);
    }

    private static String generarNuevoId() {
        String id = String.format("F%04d", contador);
        contador++;
        return id;
    }

    public static void setContador(int nuevoContador) {
        contador = nuevoContador;
    }
    
    public Finger(double alturaMax) {
        super(generarNuevoId(), 0.0, LocalDate.now()); // costo/h = 0.0 (lo puoi cambiare se vuoi), data = oggi
        this.alturaMax = alturaMax;
    }
    
    /**
     * Indica si el finger está en uso actualmente.
     *  
     * @return false por defecto
     */
    public boolean enUso() {
    	if(this.vuelo == null) { return false; }
        return true;
    }

    /**
     * Verifica si un avión es compatible con el finger según su altura.
     * 
     * @param a avión a comprobar
     * @return true si son compatibles, false si no lo son
     */
    public boolean comprobarCompatibilidad(Avion a) {
    	if (a.getTipoAvion().getAltura()<=this.alturaMax) {
    		return true;
    	}
        return false;
    }

    /**
     * @return la altura máxima permitida
     */
    public double getAlturaMax() {
        return alturaMax;
    }

    /**
     * Establece la altura máxima permitida para el finger.
     * 
     * @param alturaMax La nueva altura maxima
     */
    public void setAlturaMax(double alturaMax) {
        this.alturaMax = alturaMax;
    }
    
    /**
     * Obtiene el vuelo del finger
     * 
     * @return vuelo
     */
    public Vuelo getVuelo() {
    	return this.vuelo;
    }
    
    /**
     * Establece un nuevo vuelo al finger
     * @param v Nuevo vuelo
     */
    public void setVuelo(Vuelo v) {
    	this.vuelo = v;
    	return;
    }
}
