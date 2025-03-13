package vuelos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import elementos.Finger;
import elementos.LocalizacionAterrizaje;
import elementos.Pista;
import elementos.ZonaParking;

public abstract class Vuelo {
	private String id;
	private LocalDateTime horaSalida;
	private LocalDateTime horaLlegada;
	private LocalDateTime horaSalidaEfectiva;
	private LocalDateTime horaLlegadaEfectiva;
	private boolean compartido;
	private boolean finger;
	private boolean llegada;
	private Avion avion;
	private ArrayList<Aerolinea> aerolinea;
	private Periodicidad periodicidad;
	private EstadoFisico estFisico;
	private EstadoTemporal estTemporal;
	private LocalizacionAterrizaje locAterrizaje;
	private Pista pista;
	
	public Vuelo(String id, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolineas, boolean llegada, Periodicidad periodicidad, 
			Avion avion) {
		this.id = id;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		if(aerolineas.size() == 1) {
			this.compartido = false;
			this.aerolinea = aerolineas;
		} else if(aerolineas.size() == 2) {
			this.compartido = true;
			this.aerolinea.addAll(aerolineas);
		} else {
			throw new IllegalArgumentException("Un vuelo solo puede ser compartido por 2 aerolineas\n");
		}
		this.llegada = llegada;
		this.periodicidad = periodicidad;
		this.avion = avion;
	}

	public String getId() {
		return this.id;
	}
	
	public LocalDateTime getHoraLlegada() {
		return this.horaLlegada;
	}
	
	public LocalDateTime getHoraSalida() {
		return this.horaSalida;
	}
	
	public LocalDateTime getHoraLlegadaEfectiva() {
		return this.horaLlegadaEfectiva;
	}
	
	public LocalDateTime getHoraSalidaEfectiva() {
		return this.horaSalidaEfectiva;
	}
	
	public boolean getCompartido() {
		return this.compartido;
	}
	
	public boolean getFinger() {
		return this.finger;
	}
	
	public boolean getLlegada() {
		return this.llegada;
	}
	
	public ArrayList<Aerolinea> getAerolineas() {
		return this.aerolinea;
	}
	
	public Periodicidad getPeriodicidad() {
		return this.periodicidad;
	}
	
	public EstadoFisico getEstFisico() {
		return this.estFisico;
	}
	
	public EstadoTemporal getEstTemporal() {
		return this.estTemporal;
	}
	
	public LocalizacionAterrizaje getLocAterrizaje() {
		return this.locAterrizaje;
	}
	
	public Pista getPista() {
		return this.pista;
	}
	
	
	public void setHoraLlegadaEfectiva(LocalDateTime llegada) {
		this.horaLlegadaEfectiva = llegada;
		return;
	}
	
	public void setHoraSalidaEfectiva(LocalDateTime salida) {
		this.horaSalidaEfectiva = salida;
		return;
	}
	
	public void setEstFisico(EstadoFisico estFis) {
		this.estFisico = estFis;
		return;
	}
	
	public void setEstTemporal(EstadoTemporal estTemp) {
		this.estTemporal = estTemp;
		return;
	}
	
	
	public long calcularRetraso() {
        LocalDateTime ahora = LocalDateTime.now();
        
        // Si la hora de salida efectiva es null, usamos la hora actual
        LocalDateTime salidaReal = (horaSalidaEfectiva != null) ? horaSalidaEfectiva : ahora;
        
        // Si la hora de llegada efectiva es null, usamos la hora actual
        LocalDateTime llegadaReal = (horaLlegadaEfectiva != null) ? horaLlegadaEfectiva : ahora;

        // Calcular el retraso de la salida en minutos
        long retrasoSalida = Duration.between(horaSalida, salidaReal).toMinutes();
        
        // Calcular el retraso de la llegada en minutos
        long retrasoLlegada = Duration.between(horaLlegada, llegadaReal).toMinutes();
        
        // Si ambos retrasos son negativos (lo que indica que fue antes de lo previsto), los consideramos como 0
        retrasoSalida = Math.max(retrasoSalida, 0);
        retrasoLlegada = Math.max(retrasoLlegada, 0);
        
        // Sumamos ambos retrasos (si hay retraso tanto en la salida como en la llegada)
        return retrasoSalida + retrasoLlegada;
    }
	
	
	public boolean asignarLocAterrizaje(LocalizacionAterrizaje locAt) {
		// Comprobar qué tipo de LocAterrizaje es
		if(locAt instanceof Finger) {
			if(asignarFinger((Finger) locAt) == false) {
				return false;
			}
			this.locAterrizaje = locAt;
			this.finger = true;
			return true;
		} else if(locAt instanceof ZonaParking) {
			if(asignarParking((ZonaParking) locAt) == false) {
				return false;
			}
			this.locAterrizaje = locAt;
			this.finger = false;
			return true;
		}
		return false;
	}
	private boolean asignarFinger(Finger locAt) {
		// Comprobar que el finger no está siendo usado
		if(locAt.enUso() == true || locAt.getAlturaMax() < this.avion.getTipoAvion().getAltura()) {
			return false;
		}
		return true;
	}
	private boolean asignarParking(ZonaParking locAt) {
		// Comprobar que el parking no está lleno o no sea compatible con el avión del vuelo
		if(locAt.comprobarCompatibilidad(this.avion) == false || locAt.numPlazasOcupadasPark() == locAt.getNumPlazas()) {
			return false;
		}
		return true;
	}
	
	
	public boolean asignarPista(Pista pista) {
		pista.addVuelo(this);
		return true;
	}
}
