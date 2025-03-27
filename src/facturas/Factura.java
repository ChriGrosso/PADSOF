package facturas;

import es.uam.eps.padsof.invoices.*;
import es.uam.eps.padsof.telecard.*;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import aerolineas.*;
import elementos.*;
import aviones.*;
import sistema.*;
import vuelos.Vuelo;

/**
 * Clase que representa una factura generada para una aerolínea,
 * incluyendo los servicios utilizados, precios y métodos de pago.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es  
 */
public class Factura implements IInvoiceInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // Identificador único de la factura
    private double precioBase; // Precio base sin servicios ni sobrecargas
    private double total; // Precio total final (puede incluir sobrecargas)
    private LocalDate fechaEmision; // Fecha en la que se genera la factura
    private LocalDate fechaPago = null; // Fecha en la que se realiza el pago
    private boolean pagado = false; // Estado del pago
    private Aerolinea aerolinea; // Aerolínea asociada a la factura
    private List<IResourceUsageInfo> rUsage = new ArrayList<>(); // Recursos utilizados (para cálculo automático)
    private List<Uso> serviciosUsados = new ArrayList<>(); // Servicios utilizados por la aerolínea
    private double sobrecarga = 0; // Coste extra adicional
    private String logo; // Ruta del logo de la compañía
    /**
     * Constructor de Factura con los campos esenciales.
     */
    public Factura(String id, double precioBase, double total, LocalDate fechaEmision, Aerolinea aerolinea, String logo) {
        this.id = id;
        this.precioBase = precioBase;
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.aerolinea = aerolinea;
        this.logo = logo;
    }
    
    public void addUso(Uso uso) {
        this.serviciosUsados.add(uso);
        this.rUsage.add(uso);
    }

    public void generarFactura(String path) throws NonExistentFileException, UnsupportedImageTypeException {
        File outputDirectory = new File(path).getParentFile();
        
        // Verifica se la cartella di destinazione esiste
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new NonExistentFileException("La carpeta de destino no existe.");
        }

        // Procedi con la creazione della fattura (se il percorso è valido)
        InvoiceSystem.createInvoice(this, path);
    }


    /**
     * Calcula el coste total basado en los servicios usados.
     * @param a Aerolínea a la que pertenece la factura
     * @return coste total de los servicios
     */
    public double calcularFactura(Aerolinea a) {
        this.aerolinea = a;
        SkyManager sm = SkyManager.getInstance();

        // Calcolo intervallo del mese precedente
        LocalDate inizioMese = this.fechaEmision.minusMonths(1).withDayOfMonth(1);
        LocalDate fineMese = inizioMese.withDayOfMonth(inizioMese.lengthOfMonth());

        double totalBase = 0;
        double totalSurcharge = 0;
        double totalUso = 0;
        this.rUsage.clear();

        for (Uso u : this.serviciosUsados) {
            LocalDate dataUso = u.getHoraUso().toLocalDate();
            if (!dataUso.isBefore(inizioMese) && !dataUso.isAfter(fineMese)) {
                // Calcolo surcharge in base al tipo di aereo
                TipoAvion tipo = u.getAeronave().getTipoAvion();
                if (tipo != null && tipo.isMercancias()) {
                    totalSurcharge += sm.getCosteExtraMercancias();
                } else {
                    totalSurcharge += sm.getCosteExtraPasajeros();
                }

                // Calcolo ore d’uso del servizio
                totalUso += u.calcularCosteUso();

                // Aggiunge a lista per PDF
                this.rUsage.add(u);
            }
        }
        for(Vuelo v: this.aerolinea.getVuelos()) {
        	if (!v.getHoraSalida().toLocalDate().isBefore(inizioMese) && !v.getHoraSalida().toLocalDate().isAfter(fineMese)) {
        		// Calcolo costi base
                if (!v.getLlegada()) {
                    totalBase += sm.getCosteBaseSalida();
                } else {
                    totalBase += sm.getCosteBaseLlegada();
                }
        	}
        }

        this.precioBase = totalBase;
        this.sobrecarga = totalSurcharge;
        this.total = this.precioBase + this.sobrecarga + totalUso;
        return this.total;
    }



    /**
     * Intenta realizar el pago usando un número de tarjeta.
     * Lanza errores si el número no es válido, si hay problemas de conexión o si el banco rechaza el pago.
     * @param cardNumber número de tarjeta de crédito
     * @return true si el pago se realiza con éxito, false en caso contrario
     */
    public boolean pagar(String cardNumber) {
        try {
            TeleChargeAndPaySystem.charge(cardNumber, this.getAirline(), this.getPrice(), true);
            this.pagado = true;
            this.fechaPago = LocalDate.now();
            return true;
        } catch (OrderRejectedException e) {
            if (e instanceof InvalidCardNumberException) {
                System.err.println("Número de tarjeta no válido: " + e.getMessage());
            } else if (e instanceof FailedInternetConnectionException) {
                System.err.println("Error de conexión: " + e.getMessage());
            } else {
                System.err.println("Orden rechazada: " + e.getMessage());
            }
            return false;
        }
    }
    
    
    // Getters básicos

    public String getId() { return id; }
    public double getTotal() { return total; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public boolean isPagado() { return pagado; }
    public LocalDate getFechaPago() { return fechaPago; }

    // Métodos del contrato IInvoiceInfo

    @Override
    public String getAirline() {
        return this.aerolinea.getNombre();
    }

    @Override
    public double getBasePrice() {
        return this.precioBase;
    }

    @Override
    public String getCompanyName() {
        return "SkyManager"; // Nombre de la compañía facturadora
    }

    /**
     * Devuelve la fecha de la factura en formato "dd/MM/yyyy".
     */
    @Override
    public String getInvoiceDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.fechaEmision.format(formatter);
    }
    @Override
    public String getInvoiceIdentifier() {
        return this.getId();
    }

    /**
     * Calcula el precio total incluyendo los recursos utilizados y la sobrecarga.
     */
    @Override
    public double getPrice() {
        double total = this.precioBase + this.sobrecarga;

        for (Uso uso : this.serviciosUsados) {
            total += uso.calcularCosteUso();
        }

        return total;
    }



    @Override
    public double getSurcharge() {
        return sobrecarga;
    }

    @Override
    public String getCompanyLogo() {
        return logo;
    }

    @Override
    public List<IResourceUsageInfo> getResourcePrices() {
        return rUsage;
    }
}
