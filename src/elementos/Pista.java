package elementos;

import java.time.*;
import java.util.ArrayList;

import vuelos.EstadoVuelo;
import vuelos.Vuelo;

/**
 * Clase que representa una pista del aeropuerto, que puede ser usada para
 * despegues o aterrizajes según su configuración. 
 * Gestiona una cola de vuelos y un vuelo activo.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class Pista extends ElementoEstructural {
    private static final long serialVersionUID = 1L;

    private boolean despegue;              // Indica si la pista es para despegue (true) o aterrizaje (false)
    private int longitud;                  // Longitud de la pista en metros
    private ArrayList<Vuelo> vuelosQueSirve; // Cola de vuelos esperando para usar la pista
    private Vuelo usando;                  // Vuelo que está utilizando la pista actualmente

    /**
     * Constructor de la pista.
     * @param id identificador de la pista
     * @param fchRegistro fecha de alta en el sistema
     * @param despegue true si es pista de despegue, false si es de aterrizaje
     * @param longitud longitud física de la pista
     */
    public Pista(String id, LocalDate fchRegistro, boolean despegue, int longitud) {
        super(id, 0, fchRegistro);
        this.setDespegue(despegue);
        this.setLongitud(longitud);
        this.vuelosQueSirve = new ArrayList<>();
    }

    /**
     * Indica si la pista está actualmente en uso por un vuelo.
     * @return false por defecto (no implementado)
     */
    public boolean enUso() {
        return false;
    }

    public boolean isDespegue() {
        return this.despegue;
    }

    public void setDespegue(boolean despegue) {
        this.despegue = despegue;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    /**
     * Obtiene el vuelo que está actualmente utilizando la pista.
     */
    public Vuelo getUsando() {
        return this.usando;
    }

    /**
     * Devuelve la lista de vuelos en cola para utilizar la pista.
     */
    public ArrayList<Vuelo> getVuelos() {
        return this.vuelosQueSirve;
    }

    /**
     * Añade un vuelo a la pista: si está libre, lo asigna; si no, lo encola.
     * @param v vuelo a añadir
     */
    public void addVuelo(Vuelo v) {
        if (this.usando == null) {
            this.usando = v;
        } else {
            this.vuelosQueSirve.add(v);
        }
    }

    /**
     * Actualiza el vuelo que está usando la pista, asignando el siguiente en la cola.
     */
    public void actualizarColaVuelos() {
        if (this.vuelosQueSirve.isEmpty()) {
        	if(this.despegue) {
        		this.usando.setEstVuelo(EstadoVuelo.EN_VUELO);
        	}
            this.usando = null;
            return;
        }
        Vuelo siguiente = this.vuelosQueSirve.get(0);
        if(this.despegue) {
    		this.usando.setEstVuelo(EstadoVuelo.EN_VUELO);
    	}
        this.usando = siguiente;
        this.vuelosQueSirve.remove(0);
    }
}
