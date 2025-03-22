package aviones;

import java.time.LocalDate; // import the LocalDate class

import elementos.Hangar;
import elementos.HangarMercancias;
import elementos.HangarPasajeros;
import notificaciones.Observable;

public class Avion extends Observable {
	private String matricula;
	private LocalDate fechaCompra;
	private LocalDate fechaUltimaRevision;
	private TipoAvion tipoAvion;
	private EstadoAvion estadoAvion;
	private Hangar hangar;
	
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion, LocalDate fechaUltimaRevision) { 
		this.matricula = matricula;
		this.fechaCompra = fechaCompra;
		this.tipoAvion = tipoAvion;
		this.fechaUltimaRevision = fechaUltimaRevision;
		this.estadoAvion = EstadoAvion.FUERA_AEROPUERTO;
	}
	
	public Avion(String matricula, LocalDate fechaCompra, TipoAvion tipoAvion) {
		this(matricula, fechaCompra, tipoAvion, fechaCompra);
		this.fechaUltimaRevision = null;
		this.hangar = null;
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
	public Hangar getHangar() {
		return this.hangar;
	}
	
	public void setFechaUltimaRevision(LocalDate fechaUltimaRevision) {
		this.fechaUltimaRevision = fechaUltimaRevision;
		return;
	}
	public void setEstadoAvion(EstadoAvion estadoAvion) {
		this.estadoAvion = estadoAvion;
		return;
	}
	public boolean asignarHangar(Hangar hangar) {
		if(hangar instanceof HangarMercancias && this.tipoAvion instanceof AvionPasajeros) {
			throw new IllegalArgumentException("Un vuelo de pasajeros debe estar en un hangar para pasajeros");
		}
		if(hangar instanceof HangarPasajeros && this.tipoAvion instanceof AvionMercancias) {
			throw new IllegalArgumentException("Un vuelo de mercancias debe estar en un hangar para mercancias");
		}
		
		if(hangar.comprobarCompatibilidad(this) == false) {
			return false;
		}
		
		this.hangar = hangar;
		hangar.setNumPlazas(hangar.getNumPlazas()+1);
		return true;
	}
	
	public void desasignarHangar() {
		if(this.hangar == null) {
			return;
		}
		
		this.hangar = null;
		hangar.setNumPlazas(hangar.getNumPlazas()-1);
		return;
	}
}
