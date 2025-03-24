package facturas;

import es.uam.eps.padsof.invoices.*;
import es.uam.eps.padsof.telecard.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import aerolineas.*;
import elementos.*;

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

    /**
     * Calcula el coste total basado en los servicios usados.
     * @param a Aerolínea a la que pertenece la factura
     * @return coste total de los servicios
     */
    public double calcularFactura(Aerolinea a) {
        double suma = 0;
        for (Uso u : this.serviciosUsados) {
            suma += u.calcularCosteUso();
        }
        return suma;
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
        LocalDate date = LocalDate.now(); // Se usa la fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
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
        double rTotPrice = 0;
        if (rUsage != null) {
            for (IResourceUsageInfo ru : rUsage) {
                rTotPrice += ru.getPrice() * Double.parseDouble(ru.getUsageTime());
            }
        }
        rTotPrice += this.getBasePrice();
        rTotPrice += this.getSurcharge();
        return rTotPrice;
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
