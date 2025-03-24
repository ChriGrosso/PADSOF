package elementos;

import java.time.LocalDate;
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
    
    
    public abstract boolean isMercancias();
    public abstract double getCapacidad();

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

    public String getPrefijoPuerta() {
        return this.prefijoPuerta;
    }

    public void setPrefijoPuerta(String prefijoPuerta) {
        this.prefijoPuerta = prefijoPuerta;
    }

    public HashMap<String, Puerta> getPuertas() {
        return this.puertas;
    }

    public ArrayList<Vuelo> getVuelos() {
        return this.vuelos;
    }

    public void addVuelo(Vuelo v) {
        this.vuelos.add(v);
    }
    
    public ArrayList<Controlador> getControladores() {
        return this.controladores;
    }

    public void addControlador(Controlador c) {
        this.controladores.add(c);
    }
}
