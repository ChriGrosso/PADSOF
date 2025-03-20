package elementos;

import java.time.*;
import java.time.temporal.ChronoUnit;

import aerolineas.Aerolinea;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;

public class Uso implements IResourceUsageInfo {
	
	private LocalDateTime horaUso;
	private LocalDateTime horaDesuso;
	private ElementoEstructural elem;
	private Aerolinea aerolinea;
	
	
	public Uso(LocalDateTime horaUso, LocalDateTime horaDesuso) {
		setHoraUso(horaUso);
		setHoraDesuso(horaDesuso);
	}
	
	
	public double calcularDuraccion() {
		return ChronoUnit.HOURS.between(horaUso, horaDesuso);
	}
	
	public double calcularCosteUso() {
		return (double)this.calcularDuraccion() * this.elem.getCostePorHora();	
	}
	
	public LocalDateTime getHoraUso() {	return horaUso;	}
	public void setHoraUso(LocalDateTime horaUso) {	this.horaUso = horaUso;	}
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
		return Double.toString(calcularDuraccion());
	}	

}
