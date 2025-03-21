package vuelos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.EstadoAvion;
import elementos.Finger;
import elementos.LocalizacionAterrizaje;
import elementos.Pista;
import elementos.Puerta;
import elementos.ZonaParking;
import aeropuertos.Aeropuerto;

public abstract class Vuelo {
	private String id;
	private Aeropuerto origen;
	private Aeropuerto destino;
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
	private EstadoVuelo estVuelo;
	private LocalizacionAterrizaje locAterrizaje;
	private Pista pista;
	private Puerta puerta;
	
	public Vuelo(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolineas, boolean llegada, Periodicidad periodicidad, Avion avion) {
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.aerolinea = new ArrayList<Aerolinea>();
		if(aerolineas.size() == 1) {
			this.compartido = false;
			this.aerolinea.addAll(aerolineas);
		} else if(aerolineas.size() == 2) {
			this.compartido = true;
			this.aerolinea.addAll(aerolineas);
		} else {
			throw new IllegalArgumentException("Un vuelo solo puede ser compartido por 2 aerolineas\n");
		}
		this.avion = avion;
		this.llegada = llegada;
		this.periodicidad = periodicidad;
		this.estVuelo = EstadoVuelo.EN_TIEMPO;
	}

	public Vuelo(String id, Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, Aerolinea aerolinea,
			boolean llegada, Periodicidad periodicidad, Avion avion) {
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.aerolinea = new ArrayList<Aerolinea>();
		this.aerolinea.add(aerolinea);
		this.compartido = false;
		this.avion = avion;
		this.llegada = llegada;
		this.periodicidad = periodicidad;
		this.estVuelo = EstadoVuelo.EN_TIEMPO;
	}
	
	public boolean isVueloMercancias() {
		if(this instanceof VueloMercancias) {
			return true;
		}
		return false;
	}

	public String getId() {
		return this.id;
	}
	
	public Aeropuerto getOrigen() {
		return this.origen;
	}
	
	public Aeropuerto getDestino() {
		return this.destino;
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
	
	public Aerolinea getAerolinea() {
		return this.aerolinea.getFirst();
	}
	
	public Periodicidad getPeriodicidad() {
		return this.periodicidad;
	}
	
	public EstadoVuelo getEstVuelo() {
		return this.estVuelo;
	}
	
	public Avion getAvion() {
		return this.avion;
	}
	
	
	public LocalizacionAterrizaje getLocAterrizaje() {
		return this.locAterrizaje;
	}
	
	public Pista getPista() {
		return this.pista;
	}
	
	public Puerta getPuerta() {
		return this.puerta;
	}
	
	
	public void setPuerta(Puerta puerta) {
		this.puerta = puerta;
	}
	
	public void setFinger(boolean finger) {
		this.finger = finger;
	}

	public void setLocAterrizaje(LocalizacionAterrizaje locAterrizaje) {
		this.locAterrizaje = locAterrizaje;
	}

	public void setHoraLlegadaEfectiva(LocalDateTime llegada) {
		this.horaLlegadaEfectiva = llegada;
		return;
	}
	
	public void setHoraSalidaEfectiva(LocalDateTime salida) {
		this.horaSalidaEfectiva = salida;
		return;
	}
	
	public boolean setEstVuelo(EstadoVuelo estV) {
		if (estV == EstadoVuelo.RETRASADO && this.estVuelo.equals(EstadoVuelo.EN_TIEMPO)) {
			this.estVuelo = estV;
			return true;
		}
		// Si es un vuelo de llegada
		if(this.llegada) {
			// ESPERANDO_PISTA: no hay todavía pista o localización de aterrizaje
			if(estV == EstadoVuelo.ESPERANDO_PISTA_A && this.pista == null && this.locAterrizaje == null) {
				this.estVuelo = estV;
				return true;
			}
			// ESPERANDO_AT: espera en su pista, no la usa
			if(estV == EstadoVuelo.ESPERANDO_ATERRIZAJE && this.pista != null && this.pista.getUsando().equals(this) == false) {
				this.estVuelo = estV;
				this.puerta.liberarPuerta();
				this.avion.setEstadoAvion(EstadoAvion.ESPERANDO_PISTA);
				return true;
			}
			if(estV == EstadoVuelo.ATERRIZADO) {
				this.estVuelo = estV;
				this.avion.setEstadoAvion(EstadoAvion.EN_PISTA);
				this.pista.actualizarColaVuelos();
				this.horaLlegadaEfectiva = LocalDateTime.now();
				return true;
			}
			if(estV == EstadoVuelo.DESEMBARQUE_INI || estV == EstadoVuelo.DESEMBARQUE_FIN ||
				estV == EstadoVuelo.DESCARGA_INI || estV == EstadoVuelo.DESCARGA_FIN) {
				if(this.finger) {
					this.avion.setEstadoAvion(EstadoAvion.EN_FINGER);
				} else {
					this.avion.setEstadoAvion(EstadoAvion.EN_PARKING);
				}
				this.estVuelo = estV;
				return true;
			}
			if((estV == EstadoVuelo.OPERATIVO && siguienteVueloConAvion() != null) || 
				(estV == EstadoVuelo.EN_HANGAR && siguienteVueloConAvion() == null)) {
				this.estVuelo = estV;
				if(estV == EstadoVuelo.EN_HANGAR) {
					Aerolinea conAvion = null;
					this.avion.setEstadoAvion(EstadoAvion.EN_HANGAR);
					for(Aerolinea a: this.aerolinea) {
						if(a.getAviones().containsValue(this.avion)) {
							conAvion = a;
						}
					}
					conAvion.addUso(LocalDateTime.now(), null, this.avion.getHangar());
				}
				for(Aerolinea a: this.aerolinea) {
					a.setEndUso(LocalDateTime.now(), this, this.locAterrizaje);
				}
				return true;
			}
			return false;
		}
		else {
			// ESPERANDO_PISTA: no hay todavía pista, ya ha cargado
			if(estV == EstadoVuelo.ESPERANDO_PISTA_D && this.pista == null) {
				this.estVuelo = estV;
				return true;
			}
			// ESPERANDO_DESP: espera en su pista, no la usa
			if(estV == EstadoVuelo.ESPERANDO_DESPEGUE && this.pista != null && this.pista.getUsando().equals(this) == false) {
				this.estVuelo = estV;
				this.puerta.liberarPuerta();
				this.avion.setEstadoAvion(EstadoAvion.ESPERANDO_PISTA);
				for(Aerolinea a: this.aerolinea) {
					a.setEndUso(LocalDateTime.now(), this, this.locAterrizaje);
				}
				return true;
			}
			if(estV == EstadoVuelo.EN_VUELO) {
				this.estVuelo = estV;
				this.avion.setEstadoAvion(EstadoAvion.FUERA_AEROPUERTO);
				this.pista.actualizarColaVuelos();
				this.horaSalidaEfectiva = LocalDateTime.now();
				return true;
			}
			if(estV == EstadoVuelo.EMBARQUE || estV == EstadoVuelo.CARGA) {
				// El avion está en hangar?
				if(this.avion.getEstadoAvion() == EstadoAvion.EN_HANGAR) {
					Aerolinea conAvion = null;
					for(Aerolinea a: this.aerolinea) {
						if(a.getAviones().containsValue(this.avion)) {
							conAvion = a;
						}
					}
					conAvion.setEndUso(LocalDateTime.now(), null, this.avion.getHangar());
				}
				if(this.finger) {
					this.avion.setEstadoAvion(EstadoAvion.EN_FINGER);
				} else {
					this.avion.setEstadoAvion(EstadoAvion.EN_PARKING);
				}
				this.estVuelo = estV;
				return true;
			}
			return false;
		}
	}
	private Vuelo siguienteVueloConAvion() {
		Aerolinea aerolinea = null;
		Vuelo siguienteVuelo = null;
		// Si el vuelo de ahora ha sido compartido, averiguar de qué aerolínea es el avión
		if(this.compartido) {
			for(Aerolinea a: this.aerolinea) {
				if(a.getAviones().containsValue(this.avion)) {
					aerolinea = a;
					break;
				}
			}
		} else { aerolinea = this.aerolinea.getFirst(); }
		
		// Buscar en esa aerolinea el siguiente vuelo (salida) con el mismo avion que salga en menos de 1 hora
		//      (si existe)
		for(Vuelo v: aerolinea.getVuelos()) {
			if(v.llegada == false && v.getAvion().equals(this.avion) && 
				v.horaSalida.isAfter(this.horaLlegadaEfectiva) && 
				Duration.between(this.horaLlegadaEfectiva, v.horaSalida).toHours() < 1) {
				siguienteVuelo = v;
			}
		}
		return siguienteVuelo;
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
		// Si es Finger
		if(locAt instanceof Finger) {
			// Comprobar si se puede asignar el elemento
			if(asignarFinger((Finger) locAt) == false) {
				return false;
			}
			for(Aerolinea a: this.aerolinea) {
				a.addUso(LocalDateTime.now(), this, locAt);
			}
			this.locAterrizaje = locAt;
			this.finger = true;
			return true;
		} 
		// Si es Parking
		else if(locAt instanceof ZonaParking) {
			// Comprobar si se puede asignar el elemento
			if(asignarParking((ZonaParking) locAt) == false) {
				return false;
			}
			for(Aerolinea a: this.aerolinea) {
				a.addUso(LocalDateTime.now(), this, locAt);
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
		// Comprobar que el tipo de pista sea adecuado para el vuelo
		if((pista.isDespegue() && this.llegada) || (!pista.isDespegue() && !this.llegada)) {
			return false;
		}
		pista.addVuelo(this);
		this.pista = pista;
		return true;
	}
}
