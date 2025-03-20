package facturas;

import es.uam.eps.padsof.invoices.*;
import es.uam.eps.padsof.telecard.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import aerolineas.*;
import aviones.*;
import elementos.*;


public class Factura implements IInvoiceInfo {
    private String id;
    private double precioBase;
    private double total;
    private LocalDate fechaEmision;
    private LocalDate fechaPago = null;
    private boolean pagado = false;
    private Aerolinea aerolinea;
    private List <IResourceUsageInfo> rUsage;
    private List <Uso> serviciosUsados;
    private double sobrecarga = 0;
    private String logo;

    public Factura(String id, double precioBase, double total, LocalDate fechaEmision, Aerolinea aerolinea, String logo) {
        this.id = id;
        this.precioBase = precioBase;
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.aerolinea = aerolinea;
        this.serviciosUsados = null;
        this.logo=logo;
    }
    
    public double calcularFactura(Aerolinea a) {
    	double somma = 0;
    	for(Uso u: this.serviciosUsados) {
			somma += u.calcularCosteUso();
		}
    	return somma;
    }
    
    public boolean pagar(String cardNumber) {
    	try {
			TeleChargeAndPaySystem.charge(cardNumber, this.getAirline(), this.getPrice(), true);
		} catch (InvalidCardNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	fechaPago = LocalDate.now();
    	return true;
    }

    
    public String getId() { return id; }
    public double getTotal() { return total; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public boolean isPagado() { return pagado; }
	public LocalDate getFechaPago() { return fechaPago; }
	//public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

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
		return "SkyManager";
	}

	@Override
	public String getInvoiceDate() {
		LocalDate date = LocalDate.now(); // Data corrente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato personalizzato
        String formattedDate = date.format(formatter);
        return formattedDate;
	}

	@Override
	public String getInvoiceIdentifier() {
		return this.getId();
	}

	@Override
	public double getPrice() {
		double rTotPrice = 0;
		for (IResourceUsageInfo ru : rUsage) {
			rTotPrice += ru.getPrice() * Double.parseDouble(ru.getUsageTime());		
		}
		rTotPrice += this.getBasePrice();
		rTotPrice += this.getSurcharge();
		return rTotPrice;
	}

	@Override
	public double getSurcharge() { return sobrecarga; }
	
	@Override
	public String getCompanyLogo() { 
		return logo;
	}
	
	@Override
	public List<IResourceUsageInfo> getResourcePrices(){ return rUsage; }
}
