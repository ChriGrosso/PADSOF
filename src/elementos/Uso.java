package elementos;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;

import es.uam.eps.padsof.invoices.IResourceUsageInfo;

/**
 * Clase que representa el uso de un recurso (ElementoEstructural) por parte de un vuelo.
 * Contiene información sobre el momento de inicio y final del uso, y calcula duración y coste.
 * Implementa la interfaz IResourceUsageInfo para compatibilidad con el sistema de facturación.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class Uso implements IResourceUsageInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime horaUso;       // Momento en que comienza el uso del recurso
    private LocalDateTime horaDesuso;    // Momento en que finaliza el uso
    private ElementoEstructural elem;    // Elemento estructural utilizado (pista, hangar, puerta, etc.)

    /**
     * Constructor de Uso.
     * @param horaUso fecha y hora de inicio del uso
     * @param elem elemento estructural que se está utilizando
     */
    public Uso(LocalDateTime horaUso, ElementoEstructural elem) {
        this.horaUso = horaUso;
        this.elem = elem;
    }

    /**
     * Calcula la duración total del uso en horas.
     * @return duración del uso (puede incluir decimales si se adaptara)
     */
    public double calcularDuracion() {
        return ChronoUnit.HOURS.between(horaUso, horaDesuso);
    }

    /**
     * Calcula el coste total del uso del recurso.
     * @return coste total = duración * coste por hora del recurso
     */
    public double calcularCosteUso() {
        return this.calcularDuracion() * this.elem.getCostePorHora();
    }

    // Getters y setters

    public LocalDateTime getHoraUso() {
        return horaUso;
    }

    public LocalDateTime getHoraDesuso() {
        return horaDesuso;
    }

    public void setHoraDesuso(LocalDateTime horaDesuso) {
        this.horaDesuso = horaDesuso;
    }

    // Métodos requeridos por IResourceUsageInfo

    @Override
    public double getHourlyPrice() {
        return elem.getCostePorHora();
    }

    @Override
    public double getPrice() {
        return calcularCosteUso();
    }

    @Override
    public String getResourceDescription() {
        return elem.getId();
    }

    @Override
    public String getUsageTime() {
        return Double.toString(calcularDuracion());
    }
}
