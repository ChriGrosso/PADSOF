package facturas;

import es.uam.eps.padsof.invoices.*;
import es.uam.eps.padsof.telecard.*;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import aerolineas.Aerolinea;
import aviones.TipoAvion;
import elementos.Uso;
import sistema.SkyManager;
import vuelos.Vuelo;

/**
 * Clase que representa una factura generada para una aerolínea,
 * incluyendo servicios utilizados, precios y métodos de pago.
 * Implementa IInvoiceInfo para la integración con el sistema de facturación.
 * 
 * @author Christian Grosso
 */
public class Factura implements IInvoiceInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private double precioBase;
    private double total;
    private LocalDate fechaEmision;
    private LocalDate fechaPago = null;
    private boolean pagado = false;

    private Aerolinea aerolinea;
    private List<Uso> serviciosUsados = new ArrayList<>();
    private List<IResourceUsageInfo> rUsage = new ArrayList<>();
    private double sobrecarga = 0;
    private String logo;

    // === Costruttore ===
    public Factura(String id, double precioBase, double total, LocalDate fechaEmision, Aerolinea aerolinea, String logo) {
        this.id = id;
        this.precioBase = precioBase;
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.aerolinea = aerolinea;
        this.logo = logo;
    }

    // === Aggiunta di un servizio utilizzato ===
    public void addUso(Uso uso) {
        this.serviciosUsados.add(uso);
        this.rUsage.add(uso);
    }

    // === Generazione file PDF della fattura ===
    public void generarFactura(String path) throws NonExistentFileException, UnsupportedImageTypeException {
        File outputDirectory = new File(path).getParentFile();
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new NonExistentFileException("La carpeta de destino no existe.");
        }
        InvoiceSystem.createInvoice(this, path);
    }

    // === Calcolo del totale della fattura per il mese precedente ===
    public double calcularFactura(Aerolinea a) {
        this.aerolinea = a;
        SkyManager sm = SkyManager.getInstance();

        LocalDate inizioMese = this.fechaEmision.minusMonths(1).withDayOfMonth(1);
        LocalDate fineMese = inizioMese.withDayOfMonth(inizioMese.lengthOfMonth());

        double totalBase = 0;
        double totalSurcharge = 0;
        double totalUso = 0;
        this.rUsage.clear();

        for (Uso u : this.serviciosUsados) {
            LocalDate dataUso = u.getHoraUso().toLocalDate();
            if (!dataUso.isBefore(inizioMese) && !dataUso.isAfter(fineMese)) {
                TipoAvion tipo = u.getAeronave().getTipoAvion();
                totalSurcharge += (tipo != null && tipo.isMercancias()) 
                    ? sm.getCosteExtraMercancias() 
                    : sm.getCosteExtraPasajeros();

                totalUso += u.calcularCosteUso();
                this.rUsage.add(u);
            }
        }

        for (Vuelo v : this.aerolinea.getVuelos()) {
            LocalDate salida = v.getHoraSalida().toLocalDate();
            if (!salida.isBefore(inizioMese) && !salida.isAfter(fineMese)) {
                totalBase += v.getLlegada() ? sm.getCosteBaseLlegada() : sm.getCosteBaseSalida();
            }
        }

        this.precioBase = totalBase;
        this.sobrecarga = totalSurcharge;
        this.total = this.precioBase + this.sobrecarga + totalUso;
        return this.total;
    }

    // === Metodo per il pagamento ===
    public boolean pagar(String cardNumber) {
        try {
            TeleChargeAndPaySystem.charge(cardNumber, this.getAirline(), this.getPrice(), true);
            this.pagado = true;
            this.fechaPago = LocalDate.now();
            return true;
        } catch (OrderRejectedException e) {
            System.err.println("Error al pagar: " + e.getMessage());
            return false;
        }
    }
    
    public static Factura generarFacturaMensual(Aerolinea aerolinea) {
        LocalDate hoy = LocalDate.now();
        String idFactura = "FAC-" + aerolinea.getId() + "-" + hoy;
        String logo = "./resources/logo.png";

        // Crea la nuova factura
        Factura factura = new Factura(idFactura, 0.0, 0.0, hoy, aerolinea, logo);

        // Aggiunge tutti gli usi dell'aerolinea (la factura filtrerà internamente quelli rilevanti)
        for (Uso uso : aerolinea.getArrayUsos()) {
            factura.addUso(uso);
        }

        // Calcola totale e registra
        double total = factura.calcularFactura(aerolinea);
        factura.setTotal(total);

        // Registra in SkyManager
        SkyManager.getInstance().registrarFactura(factura);

        // Genera PDF
        try {
            String path = "./resources/" + idFactura + ".pdf";
            factura.generarFactura(path);
            System.out.println("Factura mensual generada: " + path);
        } catch (Exception e) {
            System.err.println("Error al generar la factura: " + e.getMessage());
        }
		return factura;
    }


    // === Getter base ===
    public String getId() { return id; }
    public double getTotal() { return total; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public LocalDate getFechaPago() { return fechaPago; }
    public boolean isPagado() { return pagado; }
    public Aerolinea getAerolinea() { return aerolinea; }

    // === Setter per test o correzioni ===
    public void setTotal(double total) { this.total = total; }

    // === Implementazione IInvoiceInfo ===
    @Override public String getAirline() { return aerolinea.getNombre(); }
    @Override public double getBasePrice() { return precioBase; }
    @Override public String getCompanyName() { return "SkyManager"; }
    @Override public String getInvoiceDate() { return fechaEmision.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); }
    @Override public String getInvoiceIdentifier() { return id; }

    @Override
    public double getPrice() {
        double total = precioBase + sobrecarga;
        for (Uso uso : serviciosUsados) {
            total += uso.calcularCosteUso();
        }
        return total;
    }

    @Override public double getSurcharge() { return sobrecarga; }
    @Override public String getCompanyLogo() { return logo; }
    @Override public List<IResourceUsageInfo> getResourcePrices() { return rUsage; }
}
