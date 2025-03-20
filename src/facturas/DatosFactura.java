package facturas;

import java.util.Date;
import java.util.List;
import es.uam.eps.padsof.invoices.*;

public class DatosFactura implements IInvoiceInfo {
    private String id;
    private String aerolinea;
    private Date fecha;
    private double precioBase;
    private double recargo;
    private List<IResourceUsageInfo> recursos;
    private double precioTotal;

    public DatosFactura(String id, String aerolinea, Date fecha, double precioBase, double recargo, List<IResourceUsageInfo> recursos) {
        this.id = id;
        this.aerolinea = aerolinea;
        this.fecha = fecha;
        this.precioBase = precioBase;
        this.recargo = recargo;
        this.recursos = recursos;
        this.precioTotal = precioBase + recargo + recursos.stream().mapToDouble(IResourceUsageInfo::getPrice).sum();
    }

    public String getCompanyName() { return "AeroServices Corp."; }
    public String getCompanyLogo() { return "./resources/logo.jpg"; }
    public String getAirline() { return aerolinea; }
    public String getInvoiceIdentifier() { return id; }
    public String getInvoiceDate() { return fecha.toString(); }
    public double getBasePrice() { return precioBase; }
    public double getSurcharge() { return recargo; }
    public List<IResourceUsageInfo> getResourcePrices() { return recursos; }
    public double getPrice() { return precioTotal; }
}