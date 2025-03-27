package elementos;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;
import aviones.*;

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
    private Avion avion;

    /**
     * Constructor de Uso.
     * @param horaUso fecha y hora de inicio del uso
     * @param elem elemento estructural que se está utilizando
     */
    public Uso(LocalDateTime horaUso, ElementoEstructural elem, Avion avion) {
        this.horaUso = horaUso;
        this.elem = elem;
        this.avion = avion;
    }

    /**
     * Calcula la duración total del uso en horas.
     * @return duración del uso (puede incluir decimales si se adaptara)
     */
    public double calcularDuracion() {
        long minutes = ChronoUnit.MINUTES.between(horaUso, horaDesuso);
        return minutes / 60.0;
    }

    /**
     * Calcula el coste total del uso del recurso.
     * @return coste total = duración * coste por hora del recurso
     */
    public double calcularCosteUso() {
        return this.calcularDuracion() * this.elem.getCostePorHora();
    }
    
    /**
     * Cambia el avion del recurso.
     * @param avion Nuevo avión 
     */
    public void setAeronave(Avion avion) {
        this.avion = avion;
    }
    
    /**
     * Obtine el avión de este uso.
     * @return avión del recurso
     */
    public Avion getAeronave() {
        return this.avion;
    }

    /**
     * Obtine la hora de uso de este uso.
     * @return hora de uso del recurso
     */
    public LocalDateTime getHoraUso() {
        return horaUso;
    }
    
    /**
     * Obtine la hora de desuso de este uso.
     * @return hora de desuso del recurso
     */
    public LocalDateTime getHoraDesuso() {
        return horaDesuso;
    }
    
    /**
     * Cambia la hora de desuso de este uso.
     * @param horaDesuso Nueva hora de desuso del recurso
     */
    public void setHoraDesuso(LocalDateTime horaDesuso) {
        this.horaDesuso = horaDesuso;
    }

    // Métodos requeridos por IResourceUsageInfo
    /**
     * Obtine el coste por hora.
     * @return coste por hora del recurso
     */
    @Override
    public double getHourlyPrice() {
        return elem.getCostePorHora();
    }
    
    /**
     * Obtine el precio.
     * @return precio del uso total
     */
    @Override
    public double getPrice() {
        return calcularCosteUso();
    }
    
    /**
     * Obtine el identificador del uso.
     * @return identificador
     */
    @Override
    public String getResourceDescription() {
        return elem.getId();
    }
    
    /**
     * Obtine el tiempo de uso del recurso.
     * @return tiempo de uso total
     */
    @Override
    public String getUsageTime() {
        return Double.toString(calcularDuracion());
    }
}
