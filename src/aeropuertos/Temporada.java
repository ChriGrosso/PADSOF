package aeropuertos;

import java.time.LocalTime;
import java.time.MonthDay;

/**
 * La clase Temporada representa un período dentro de un año en el que un aeropuerto
 * opera con un horario específico de apertura y cierre.
 * 
 * Incluye información sobre la fecha de inicio y fin de la temporada, así como
 * las horas de apertura y cierre del aeropuerto durante dicho período.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class Temporada {
    private MonthDay fechaInicioTemporada;
    private LocalTime apertura;
    private LocalTime cierre;
    private MonthDay fechaFinTemporada;

    /**
     * Constructor que inicializa una nueva temporada con su rango de fechas y horarios.
     *
     * @param inicioTemporada   Fecha de inicio de la temporada (día y mes).
     * @param apertura          Hora de apertura del aeropuerto en esta temporada.
     * @param cierre            Hora de cierre del aeropuerto en esta temporada.
     * @param finTemporada      Fecha de finalización de la temporada (día y mes).
     */
    public Temporada(MonthDay inicioTemporada, LocalTime apertura, LocalTime cierre, MonthDay finTemporada) {
        this.fechaInicioTemporada = inicioTemporada;
        this.apertura = apertura;
        this.cierre = cierre;
        this.fechaFinTemporada = finTemporada;
    }

    /**
     * Obtiene la fecha de inicio de la temporada.
     *
     * @return La fecha de inicio de la temporada (día y mes).
     */
    public MonthDay getFechaInicioTemporada() {
        return this.fechaInicioTemporada;
    }

    /**
     * Obtiene la hora de apertura del aeropuerto en esta temporada.
     *
     * @return La hora de apertura.
     */
    public LocalTime getApertura() {
        return this.apertura;
    }

    /**
     * Obtiene la hora de cierre del aeropuerto en esta temporada.
     *
     * @return La hora de cierre.
     */
    public LocalTime getCierre() {
        return this.cierre;
    }

    /**
     * Obtiene la fecha de finalización de la temporada.
     *
     * @return La fecha de fin de la temporada (día y mes).
     */
    public MonthDay getFechaFinTemporada() {
        return this.fechaFinTemporada;
    }

    /**
     * Configura la fecha de inicio de la temporada.
     *
     * @param fecha La nueva fecha de inicio de la temporada (día y mes).
     */
    public void setFechaInicioTemporada(MonthDay fecha) {
        this.fechaInicioTemporada = fecha;
    }

    /**
     * Configura la hora de apertura del aeropuerto en esta temporada.
     *
     * @param apertura La nueva hora de apertura.
     */
    public void setApertura(LocalTime apertura) {
        this.apertura = apertura;
    }

    /**
     * Configura la hora de cierre del aeropuerto en esta temporada.
     *
     * @param cierre La nueva hora de cierre.
     */
    public void setCierre(LocalTime cierre) {
        this.cierre = cierre;
    }

    /**
     * Configura la fecha de finalización de la temporada.
     *
     * @param fecha La nueva fecha de fin de la temporada (día y mes).
     */
    public void setFechaFinTemporada(MonthDay fecha) {
        this.fechaFinTemporada = fecha;
    }
}

