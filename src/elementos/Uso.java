package elementos;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Uso {
	
	private LocalDateTime horaUso;
	private LocalDateTime horaDesuso;
	private ElementoEstructural elemento;
	
	public Uso(LocalDateTime horaUso, ElementoEstructural elemento) {
		this.horaUso = horaUso;
		this.elemento = elemento;
	}
	/**
	 * @return the horaUso
	 */
	public LocalDateTime getHoraUso() {
		return horaUso;
	}
	/**
	 * @param horaUso the horaUso to set
	 */
	public void setHoraUso(LocalDateTime horaUso) {
		this.horaUso = horaUso;
	}
	/**
	 * @return the horaDesuso
	 */
	public LocalDateTime getHoraDesuso() {
		return horaDesuso;
	}
	/**
	 * @param horaDesuso the horaDesuso to set
	 */
	public void setHoraDesuso(LocalDateTime horaDesuso) {
		this.horaDesuso = horaDesuso;
	}
	
	public double calcularDuracion() {
		return ChronoUnit.HOURS.between(horaUso, horaDesuso);
	}
	
	public double calcularCosteUso() {
		return calcularDuracion()*this.elemento.getCostePorHora();
	}
}
