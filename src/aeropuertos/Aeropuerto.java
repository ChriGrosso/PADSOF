package aeropuertos;

import java.util.ArrayList;

/**
 * La clase Aeropuerto representa un aeropuerto con sus características principales, 
 * como su nombre, código, ubicación, diferencia horaria y temporadas de operación.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class Aeropuerto {
    private String nombre;
    private String codigo;
    private String ciudadMasCercana;
    private String pais;
    private double distanciaCiudadMasCercana;
    private int diferenciaHoraria;
    private int numeroTemporadas;
    private ArrayList<Temporada> temporadas;
    private Direccion direccion;

    /**
     * Constructor que inicializa un aeropuerto con toda su información relevante.
     *
     * @param nombre            Nombre del aeropuerto.
     * @param codigo            Código identificador del aeropuerto.
     * @param ciudad            Ciudad más cercana al aeropuerto.
     * @param pais              País donde se encuentra el aeropuerto.
     * @param distancia         Distancia en kilómetros hasta la ciudad más cercana.
     * @param difHoraria        Diferencia horaria con respecto al UTC.
     * @param numTemporadas     Número de temporadas activas del aeropuerto.
     * @param t                 Lista de temporadas del aeropuerto.
     * @param d                 Dirección física del aeropuerto.
     */
    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distancia, int difHoraria, int numTemporadas, ArrayList<Temporada> t, Direccion d) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudadMasCercana = ciudad;
        this.pais = pais;
        this.distanciaCiudadMasCercana = distancia;
        this.diferenciaHoraria = difHoraria;
        this.numeroTemporadas = numTemporadas;
        this.temporadas = t;
        this.direccion = d;
    }

    /**
     * Obtiene el nombre del aeropuerto.
     *
     * @return Nombre del aeropuerto.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene el código del aeropuerto.
     *
     * @return Código del aeropuerto.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Obtiene la ciudad más cercana al aeropuerto.
     *
     * @return Ciudad más cercana.
     */
    public String getCiudadMasCercana() {
        return this.ciudadMasCercana;
    }

    /**
     * Obtiene el país donde se encuentra el aeropuerto.
     *
     * @return País del aeropuerto.
     */
    public String getPais() {
        return this.pais;
    }

    /**
     * Obtiene la distancia en kilómetros hasta la ciudad más cercana.
     *
     * @return Distancia a la ciudad más cercana.
     */
    public double getDistanciaCiudadMasCercana() {
        return this.distanciaCiudadMasCercana;
    }

    /**
     * Obtiene la diferencia horaria del aeropuerto con respecto al UTC.
     *
     * @return Diferencia horaria en horas.
     */
    public int getDiferenciaHoraria() {
        return this.diferenciaHoraria;
    }

    /**
     * Obtiene el número total de temporadas activas del aeropuerto.
     *
     * @return Número de temporadas activas.
     */
    public int getNumeroTemporadas() {
        return this.numeroTemporadas;
    }

    /**
     * Obtiene la lista de temporadas del aeropuerto.
     *
     * @return Lista de temporadas.
     */
    public ArrayList<Temporada> getTemporadas() {
        return this.temporadas;
    }

    /**
     * Obtiene la dirección física del aeropuerto.
     *
     * @return Dirección del aeropuerto.
     */
    public Direccion getDireccion() {
        return this.direccion;
    }

    /**
     * Configura el código del aeropuerto.
     *
     * @param codigo Nuevo código del aeropuerto.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Configura la ciudad más cercana al aeropuerto.
     *
     * @param ciudad Nueva ciudad más cercana.
     */
    public void setCiudadMasCercana(String ciudad) {
        this.ciudadMasCercana = ciudad;
    }

    /**
     * Configura el país donde se encuentra el aeropuerto.
     *
     * @param pais Nuevo país del aeropuerto.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Configura la distancia en kilómetros hasta la ciudad más cercana.
     *
     * @param distancia Nueva distancia a la ciudad más cercana.
     */
    public void setDistanciaCiudadMasCercana(double distancia) {
        this.distanciaCiudadMasCercana = distancia;
    }

    /**
     * Configura la diferencia horaria con respecto al UTC.
     *
     * @param diferenciaHoraria Nueva diferencia horaria en horas.
     */
    public void setDiferenciaHoraria(int diferenciaHoraria) {
        this.diferenciaHoraria = diferenciaHoraria;
    }

    /**
     * Añade una nueva temporada a la lista de temporadas del aeropuerto.
     *
     * @param t Nueva temporada a añadir.
     */
    public void addTemporada(Temporada t) {
        this.temporadas.add(t);
        this.numeroTemporadas++;
    }

    /**
     * Configura la dirección física del aeropuerto.
     *
     * @param direccion Nueva dirección del aeropuerto.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}

