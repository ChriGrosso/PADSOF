package elementos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import usuarios.Controlador;
import vuelos.Vuelo;

/**
 * Clase abstracta que representa una terminal de aeropuerto.
 * Contiene múltiples puertas y puede gestionar varios vuelos.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public abstract class Terminal extends ElementoEstructural {
    private static final long serialVersionUID = 1L;

    private int numeroPuertas;                          // Número total de puertas
    private String prefijoPuerta;                       // Prefijo que se usa para nombrar las puertas (ej. "A", "T", etc.)
    private HashMap<String, Puerta> puertas;            // Mapa de código -> puerta
    private ArrayList<Vuelo> vuelos;                    // Lista de vuelos asignados a esta terminal
    private ArrayList<Controlador> controladores;       // Lista de controladores

    /**
     * Constructor de Terminal.
     * @param id identificador de la terminal
     * @param fchRegistro fecha de registro
     * @param numeroPuertas número de puertas inicial
     * @param prefijoPuerta prefijo usado para crear nombres de puertas
     */
    public Terminal(String id, LocalDate fchRegistro, int numeroPuertas, String prefijoPuerta) {
        super(id, 0, fchRegistro);
        this.numeroPuertas = numeroPuertas;
        this.prefijoPuerta = prefijoPuerta;
        this.puertas = new HashMap<>();
        this.vuelos = new ArrayList<>();
        this.controladores = new ArrayList<>();

        // Inicializa las puertas automáticamente
        for (int i = 1; i <= numeroPuertas; i++) {
            String nomP = prefijoPuerta + i;
            Puerta p = new Puerta(nomP, fchRegistro);
            this.puertas.put(nomP, p);
        }
    }
    
    /**
     * Método para saber si una terminal es de mercancias o no
     * 
     * @return true, si es de mercancias, false en caso contrario
     */
    public abstract boolean isMercancias();
    
    /**
     * Obtiene la capacidad de la terminal.
     * 
     * método que se sobreescribe en las clases hija.
     * 
     * @return capicidad en personas en el caso de terminal de pasajeros o en toneladas en al caso de la de mercancias.
     */
    public abstract double getCapacidad();
    
    /**
     * Obtiene la capacidad disponible de la terminal, en un intervalo de tiempo dado.
     * 
     * @param r1 Extremo inferior del rango de tiempo
     * @param r2 Extremo superior del rango de tiempo
     * 
     * @return capacidad disponible
     */
    public abstract double getCapDisponible(LocalDateTime r1, LocalDateTime r2);
    
    /**
     * Obtiene la capacidad ocupada de la terminal.
     * 
     * @return capacidad ocupada
     */
    public abstract double getCapacidadOcup();

    /**
     * Cuenta cuántas puertas están actualmente en uso.
     * @return número de puertas ocupadas
     */
    public int numPuertasOcupadasTerm() {
        int p_ocupadas = 0;
        for (Puerta p : puertas.values()) {
            if (p.enUso()) {
                p_ocupadas += 1;
            }
        }
        return p_ocupadas;
    }
    
    /**
     * Cuenta cuántas puertas estaran ocupadas dentro del rango de tiempo especificado.
     * 
     * @param r1 Extremo inferior del rango de tiempo
     * @param r2 Extremo superior del rango de tiempo
     * @return número de puertas ocupadas
     */
    public int numPuertasOcupadasTerm(LocalDateTime r1, LocalDateTime r2) {
        int p_ocupadas = 0;

        for(Vuelo v: this.vuelos) {
        	if (v.getLlegada() && v.getHoraLlegada().isBefore(r2) && v.getHoraLlegada().isAfter(r1)) {
        		p_ocupadas +=1;
        	}
        	if (!v.getLlegada() && v.getHoraSalida().isBefore(r2) && v.getHoraSalida().isAfter(r1)) {
        		p_ocupadas +=1;
        	}
        	
        }
        return p_ocupadas;
    }
    
    /**
     * Obtiene las puertas de la terminal.
     * 
     * @return número de puertas
     */
    public int getNumeroPuertas() {
        return this.numeroPuertas;
    }

    /**
     * Permite modificar el número de puertas de la terminal.
     * Si se reduce, elimina puertas al final.
     * Si se aumenta, añade nuevas puertas con el prefijo definido.
     */
    public void setNumeroPuertas(int numeroPuertas) {
        if (numeroPuertas < this.numeroPuertas) {
            for (int i = this.numeroPuertas; i > numeroPuertas; i--) {
                String nomP = this.prefijoPuerta + i;
                this.puertas.remove(nomP);
            }
        } else if (numeroPuertas > this.numeroPuertas) {
            for (int i = this.numeroPuertas + 1; i <= numeroPuertas; i++) {
                String nomP = this.prefijoPuerta + i;
                Puerta p = new Puerta(nomP, this.getFchRegistro());
                this.puertas.put(nomP, p);
            }
        }
        this.numeroPuertas = numeroPuertas;
    }

    /**
     * Obtiene el prefijo de puerta de la terminal.
     * 
     * @return prefijo de la puerta
     */
    public String getPrefijoPuerta() {
        return this.prefijoPuerta;
    }
    
    /**
     * Cambia el prefijo de puerta de la terminal.
     * 
     * @param prefijoPuerta Nuevo prefijo de las puertas de la terminal
     */
    public void setPrefijoPuerta(String prefijoPuerta) {
        this.prefijoPuerta = prefijoPuerta;
    }
    
    /**
     * Obtiene las puertas de la terminal.
     * 
     * @return puertas de la terminal
     */
    public HashMap<String, Puerta> getPuertas() {
        return this.puertas;
    }
    
    /**
     * Obtiene los vuelos de la terminal.
     * 
     * @return Lista con los vuelos de la terminal
     */
    public ArrayList<Vuelo> getVuelos() {
        return this.vuelos;
    }
    
    /**
     * Añade un vuelo a la lista de vuelos de la terminal
     * 
     * @param v Vuelo que se quiere añadir
     */
    public void addVuelo(Vuelo v) {
        this.vuelos.add(v);
    }
    
    /**
     * Obtiene los controladores asignados de la terminal.
     * 
     * @return lista con los controladores
     */
    public ArrayList<Controlador> getControladores() {
        return this.controladores;
    }
    
    /**
     * Añade un controlador a la lista de vuelos de la terminal
     * 
     * @param v Controlador que se quiere añadir
     */
    public void addControlador(Controlador c) {
        this.controladores.add(c);
        for (Vuelo v: this.vuelos) {
        	v.addObserver(c);
        }
    }
}
