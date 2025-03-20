package elementos;

import java.time.*;
import java.time.temporal.ChronoUnit;

import es.uam.eps.padsof.invoices.IResourceUsageInfo;

public class Uso implements IResourceUsageInfo {
	
	private LocalDateTime horaUso;
	private LocalDateTime horaDesuso;
	private ElementoEstructural elem;
	
	
	public Uso(LocalDateTime horaUso, ElementoEstructural elem) {
		this.horaUso = horaUso;
		this.elem = elem;
	}
	
	
	public double calcularDuracion() {
		return ChronoUnit.HOURS.between(horaUso, horaDesuso);
	}
	
	public double calcularCosteUso() {
		return (double)this.calcularDuracion() * this.elem.getCostePorHora();	
	}
	
	public LocalDateTime getHoraUso() {	return horaUso;	}
	public LocalDateTime getHoraDesuso() {	return horaDesuso; }
	public void setHoraDesuso(LocalDateTime horaDesuso) { this.horaDesuso = horaDesuso; }


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
