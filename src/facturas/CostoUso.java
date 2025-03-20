package facturas;

import es.uam.eps.padsof.invoices.*;

public class CostoUso implements IResourceUsageInfo {
    private String descripcion;
    private double precioHora;
    private String tiempoUso;
    private double precioFinal;

    public CostoUso(String descripcion, double precioHora, String tiempoUso, double precioFinal) {
        this.descripcion = descripcion;
        this.precioHora = precioHora;
        this.tiempoUso = tiempoUso;
        this.precioFinal = precioFinal;
    }

    public String getResourceDescription() { return descripcion; }
    public double getHourlyPrice() { return precioHora; }
    public String getUsageTime() { return tiempoUso; }
    public double getPrice() { return precioFinal; }
}