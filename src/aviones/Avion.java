package aviones;

import java.time.LocalDate; // import the LocalDate class

public class Avion {
	private String matricula;
	private LocalDate fechaCompra;
	private LocalDate fechaUltimaRevision;
	private TipoAvion tipoAvion;
	private EstadoAvion estadoAvion;
	
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion, LocalDate fechaUltimaRevision, EstadoAvion estadoAvion) {     
		this.matricula = matricula;
		this.fechaCompra = fechaCompra;
		this.tipoAvion = tipoAvion;
		this.fechaUltimaRevision = fechaUltimaRevision;
		this.estadoAvion = estadoAvion;
	}
	
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion, EstadoAvion estadoAvion) {
		this(matricula, fechaCompra, tipoAvion, fechaCompra, estadoAvion);
	}
	
	public String getMatricula() {
		return this.matricula;
	}
	public LocalDate getFechaCompra() {
		return this.fechaCompra;
	}
	public TipoAvion getTipoAvion() {
		return this.tipoAvion;
	}
	public LocalDate getFechaUltimaRevision() {
		return this.fechaUltimaRevision;
	}
	public EstadoAvion getEstadoAvion() {
		return this.estadoAvion;
	}
	
	public void setFechaUltimaRevision(LocalDate fechaUltimaRevision) {
		this.fechaUltimaRevision = fechaUltimaRevision;
	}
	public void setEstadoAvion(EstadoAvion estadoAvion) {
		this.estadoAvion = estadoAvion;
	}
}
