package vuelos;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.DayOfWeek; 

import aerolineas.Aerolinea;
import aerolineas.ClaveVueloElemento;
import aviones.Avion;
import aviones.EstadoAvion;
import elementos.ElementoEstructural;
import elementos.Finger;
import elementos.LocalizacionAterrizaje;
import elementos.Pista;
import elementos.Puerta;
import elementos.Terminal;
import elementos.ZonaParking;
import notificaciones.Observable;
import usuarios.Controlador;
import aeropuertos.Aeropuerto;

/**
 * Clase abstracta que representa un vuelo en un sistema de gestión de aeropuertos.
 * Un vuelo puede ser de llegada o salida y puede tener diferentes estados y periodicidad.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public abstract class Vuelo extends Observable implements Serializable{
	private static final long serialVersionUID = 1L;
	private static long genId = 0;
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
	private ArrayList<DayOfWeek> diasAlternos;
	private EstadoVuelo estVuelo;
	private LocalizacionAterrizaje locAterrizaje;
	private Pista pista;
	private Terminal terminal;
	private Puerta puerta;
	private HashMap<ElementoEstructural, ClaveVueloElemento> mapaElemClave;
	private PeticionCompartir petComp;
	private Controlador controladorAsignado = null;
	
	/**
	 * Constructor de Vuelo con múltiples aerolíneas.
	 *
	 * @param id         Identificador del vuelo.
	 * @param origen     Aeropuerto de origen.
	 * @param destino    Aeropuerto de destino.
	 * @param horaSalida Hora de salida programada.
	 * @param horaLlegada Hora de llegada programada.
	 * @param aerolineas Lista de aerolíneas operando el vuelo.
	 * @param llegada    Indica si el vuelo es de llegada.
	 * @param periodicidad Periodicidad del vuelo.
	 * @param avion      Avión asignado al vuelo.
	 * @throws IllegalArgumentException Si hay más de 2 aerolíneas (un vuelo puede ser compartido como máximo por 2).
	 */
	public Vuelo(Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolineas, boolean llegada, Periodicidad periodicidad, Avion avion) {
		this.origen = origen;
		this.destino = destino;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.aerolinea = new ArrayList<Aerolinea>();
		if(aerolineas.size() == 1) {
			this.compartido = false;
			this.aerolinea.addAll(aerolineas);
			// Generar el ID con el código de la única aerolínea que hay
			this.id = aerolineas.get(0).getId() + String.format("%04d", genId);
			genId++;
			if(genId == 10000) {
				resetGenId();
			}
			this.petComp = PeticionCompartir.NO_COMPARTIDO;
		} else if(aerolineas.size() == 2) {
			this.compartido = true;
			this.aerolinea.addAll(aerolineas);
			// Generar el ID con el código de la aerolínea que POSEE EL AVIÓN DEL VUELO
			for(Aerolinea a: aerolineas) {
				if(a.getAviones().values().contains(avion)) {
					this.id = a.getId() + String.format("%04d", genId);
					genId++;
					if(genId == 10000) {
						resetGenId();
					}
				}
			}
			this.petComp = PeticionCompartir.COMPARTIDO;
		} else {
			throw new IllegalArgumentException("Un vuelo solo puede ser compartido por 2 aerolineas\n");
		}
		this.avion = avion;
		this.llegada = llegada;
		this.periodicidad = periodicidad;
		this.estVuelo = EstadoVuelo.EN_TIEMPO;
		this.mapaElemClave = new HashMap<ElementoEstructural, ClaveVueloElemento>();
	}
	
	
	/**
	 * Constructor para vuelos con días alternos y múltiples aerolíneas.
	 *
	 * @param id           Identificador del vuelo.
	 * @param origen       Aeropuerto de origen.
	 * @param destino      Aeropuerto de destino.
	 * @param horaSalida   Hora de salida programada.
	 * @param horaLlegada  Hora de llegada programada.
	 * @param aerolineas   Lista de aerolíneas operando el vuelo.
	 * @param llegada      Indica si el vuelo es de llegada.
	 * @param periodicidad Periodicidad del vuelo (debe ser DIAS_ALTERNOS).
	 * @param avion        Avión asignado al vuelo.
	 * @param diasAlternos Días en los que opera el vuelo.
	 * @throws IllegalArgumentException Si la periodicidad no es DIAS_ALTERNOS o si hay más de 2 aerolíneas.
	 */
	public Vuelo(Aeropuerto origen, Aeropuerto destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, 
			ArrayList<Aerolinea> aerolineas, boolean llegada, Avion avion, String diasAlternos) {
		this.diasAlternos = new ArrayList<DayOfWeek>();
		for(String c: diasAlternos.split(" ")) {
			if(c.equals("L")) { this.diasAlternos.add(DayOfWeek.MONDAY); }
			if(c.equals("M")) { this.diasAlternos.add(DayOfWeek.TUESDAY); }
			if(c.equals("X")) { this.diasAlternos.add(DayOfWeek.WEDNESDAY); }
			if(c.equals("J")) { this.diasAlternos.add(DayOfWeek.THURSDAY); }
			if(c.equals("V")) { this.diasAlternos.add(DayOfWeek.FRIDAY); }
			if(c.equals("S")) { this.diasAlternos.add(DayOfWeek.SATURDAY); }
			if(c.equals("D")) { this.diasAlternos.add(DayOfWeek.SUNDAY); }
		}
		this.origen = origen;
		this.destino = destino;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.aerolinea = new ArrayList<Aerolinea>();
		if(aerolineas.size() == 1) {
			this.compartido = false;
			this.aerolinea.addAll(aerolineas);
			// Generar el ID con el código de la única aerolínea que hay
			this.id = aerolineas.get(0).getId() + String.format("%04d", genId);
			genId++;
			if(genId == 10000) {
				resetGenId();
			}
			this.petComp = PeticionCompartir.NO_COMPARTIDO;
		} else if(aerolineas.size() == 2) {
			this.compartido = true;
			this.aerolinea.addAll(aerolineas);
			// Generar el ID con el código de la aerolínea que POSEE EL AVIÓN DEL VUELO
			for(Aerolinea a: aerolineas) {
				if(a.getAviones().values().contains(avion)) {
					this.id = a.getId() + String.format("%04d", genId);
					genId++;
					if(genId == 10000) {
						resetGenId();
					}
				}
			}
			this.petComp = PeticionCompartir.COMPARTIDO;
		} else {
			throw new IllegalArgumentException("Un vuelo solo puede ser compartido por 2 aerolineas\n");
		}
		this.avion = avion;
		this.llegada = llegada;
		this.periodicidad = Periodicidad.DIAS_ALTERNOS;
		this.estVuelo = EstadoVuelo.EN_TIEMPO;
		this.mapaElemClave = new HashMap<ElementoEstructural, ClaveVueloElemento>();
	}
	
	
	/**
     * Resetea a 0 el contador único de los IDs de todos los vuelos.
     * 
     * Este método es útil, por ejemplo, en escenarios de pruebas o reinicios del sistema
     * donde se desea reiniciar la numeración de los vuelos.
     */
	public void resetGenId() {
		genId = 0;
	}
	
	/**
     * Establece un valor específico para el generador de IDs de vuelos.
     * 
     * @param id El nuevo valor que se desea asignar al generador de IDs.
     */
	public static void setGenId(long id) {
		genId = id;
	}
	
	/**
     * Obtiene el valor actual del generador de IDs de vuelos.
     * 
     * @return El valor actual del generador de IDs.
     */
	public static long getGenId() {
		return genId;
	}
	
	/**
     * Establece el estado de la petición para compartir el vuelo.
     * 
     * @param comp El nuevo estado de la petición de compartición.
     */
	public void setPetComp(PeticionCompartir comp) {
		this.petComp = comp;
	}
	
	/**
     * Obtiene el estado actual de la petición para compartir el vuelo.
     * 
     * @return El estado actual de la petición de compartición.
     */
	public PeticionCompartir getPetComp() {
		return this.petComp;
	}
	
	
	/**
	 * Retorna la representación en String del vuelo.
	 *
	 * @return String con la información del vuelo.
	 */
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String vuelo = "Flight " + this.id + " (";
		if(!this.compartido) {
			vuelo += this.aerolinea.get(0).getNombre();
		} else {
			vuelo += this.aerolinea.get(0).getNombre() + ", " + this.aerolinea.get(this.aerolinea.size()-1).getNombre();
		}
		vuelo += ")\n" + "From: " + this.origen.getCiudadMasCercana() + " to " + this.destino.getCiudadMasCercana() 
		         + "\n" + "Departure: " + this.horaSalida.format(formatter) + "\n" +
		         "Arrival: " + this.horaLlegada.format(formatter) + "\n" +
		         "Status: " + this.estVuelo.toString();

	    return vuelo;
	}
	
	/**
	 * Verifica si el vuelo es de mercancías.
	 *
	 * @return true si el vuelo es de mercancías, false en caso contrario.
	 */
	public boolean isVueloMercancias() {
		if(this instanceof VueloMercancias) {
			return true;
		}
		return false;
	}
	
	/**
	 * Obtiene el identificador del vuelo.
	 *
	 * @return ID del vuelo.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Obtiene el aeropuerto de origen del vuelo.
	 *
	 * @return Aeropuerto de origen.
	 */
	public Aeropuerto getOrigen() {
		return this.origen;
	}
	
	/**
	 * Obtiene el aeropuerto de destino del vuelo.
	 *
	 * @return Aeropuerto de destino.
	 */
	public Aeropuerto getDestino() {
		return this.destino;
	}
	
	/**
	 * Obtiene la hora de llegada programada del vuelo.
	 *
	 * @return Hora de llegada programada.
	 */
	public LocalDateTime getHoraLlegada() {
		return this.horaLlegada;
	}
	
	/**
	 * Obtiene la hora de salida programada del vuelo.
	 *
	 * @return Hora de salida programada.
	 */
	public LocalDateTime getHoraSalida() {
		return this.horaSalida;
	}
	
	/**
	 * Obtiene la hora de llegada efectiva del vuelo.
	 *
	 * @return Hora de llegada efectiva.
	 */
	public LocalDateTime getHoraLlegadaEfectiva() {
		return this.horaLlegadaEfectiva;
	}
	
	/**
	 * Obtiene la hora de salida efectiva del vuelo.
	 *
	 * @return Hora de salida efectiva.
	 */
	public LocalDateTime getHoraSalidaEfectiva() {
		return this.horaSalidaEfectiva;
	}
	
	/**
	 * Indica si el vuelo es compartido entre aerolíneas.
	 *
	 * @return true si el vuelo es compartido, false en caso contrario.
	 */
	public boolean getCompartido() {
		return this.compartido;
	}
	
	/**
	 * Indica si el vuelo usa finger para el embarque o desembarque.
	 *
	 * @return true si usa finger, false} en caso contrario.
	 */
	public boolean getFinger() {
		return this.finger;
	}
	
	/**
	 * Indica si el vuelo es de llegada.
	 *
	 * @return true si es un vuelo de llegada, false si es de salida.
	 */
	public boolean getLlegada() {
		return this.llegada;
	}
	
	/**
	 * Obtiene la lista de aerolíneas que operan el vuelo.
	 *
	 * @return Lista de aerolíneas del vuelo.
	 */
	public ArrayList<Aerolinea> getAerolineas() {
		return this.aerolinea;
	}
	
	/**
	 * Obtiene la aerolínea principal del vuelo.
	 *
	 * @return Aerolínea principal de un vuelo compartido. La única si el vuelo no es compartido
	 */
	public Aerolinea getAerolinea() {
		return this.aerolinea.get(0);
	}
	
	
	/**
	 * Obtiene la periodicidad del vuelo.
	 *
	 * @return Periodicidad del vuelo.
	 */
	public Periodicidad getPeriodicidad() {
		return this.periodicidad;
	}
	
	/**
	 * Obtiene el estado actual del vuelo.
	 *
	 * @return Estado del vuelo.
	 */
	public EstadoVuelo getEstVuelo() {
		return this.estVuelo;
	}
	
	/**
	 * Obtiene el avión asignado al vuelo.
	 *
	 * @return Avión del vuelo.
	 */
	public Avion getAvion() {
		return this.avion;
	}
	
	/**
	 * Obtiene la localización de aterrizaje del vuelo.
	 *
	 * @return Localización de aterrizaje.
	 */
	public LocalizacionAterrizaje getLocAterrizaje() {
		return this.locAterrizaje;
	}
	
	/**
	 * Obtiene la pista asignada para el vuelo.
	 *
	 * @return Pista asignada.
	 */
	public Pista getPista() {
		return this.pista;
	}
	
	/**
	 * Obtiene la puerta de embarque o desembarque asignada al vuelo.
	 *
	 * @return Puerta del vuelo.
	 */
	public Puerta getPuerta() {
		return this.puerta;
	}
	
	/**
	 * Obtiene el mapa de elementos estructurales asociados al vuelo.
	 *
	 * @return Mapa de elementos estructurales y sus claves asociadas.
	 */
	public HashMap<ElementoEstructural, ClaveVueloElemento> getMapaElemClave() {
		return this.mapaElemClave;
	}
	
	/**
	 * Obtiene la terminal asignada al vuelo.
	 *
	 * @return Terminal asignada.
	 */
	public Terminal getTerminal() {
		return this.terminal;
	}
	
	/**
	 * Obtiene los días por los que opera un vuelo.
	 *
	 * @return Los días de la semana por los que opera un vuelo. Null si no es periódico o es diario.
	 */
	public ArrayList<DayOfWeek> getDiasAlternos() {
		return this.diasAlternos;
	}
	
	/**
	 * Obtiene el controlador asignado al vuelo.
	 *
	 * @return Controlador asignado.
	*/
	public Controlador getControladorAsignado() {
		return this.controladorAsignado;
	}
	
	/**
	 * Asigna un controlador al vuelo.
	 *
	 * @return c Controlador a asignar.
	*/
	public void setControladorAsignado(Controlador c) {
		this.controladorAsignado = c;
	}
	
	/**
	 * Asigna una terminal al vuelo.
	 *
	 * @param puerta Puerta a asignar.
	 */
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
	/**
	 * Asigna una puerta de embarque o desembarque al vuelo.
	 *
	 * @param puerta Puerta a asignar.
	 */
	public void setPuerta(Puerta puerta) {
		this.puerta = puerta;
	}
	
	/**
	 * Establece si el vuelo usará finger para el embarque o desembarque.
	 *
	 * @param finger true si se usará finger, false en caso contrario.
	 */
	public void setFinger(boolean finger) {
		this.finger = finger;
	}
	
	/**
	 * Asigna una localización de aterrizaje al vuelo.
	 *
	 * @param locAterrizaje Localización de aterrizaje a asignar.
	 */
	public void setLocAterrizaje(LocalizacionAterrizaje locAterrizaje) {
		this.locAterrizaje = locAterrizaje;
	}
	
	/**
	 * Establece la hora de llegada efectiva del vuelo.
	 *
	 * @param llegada Hora de llegada efectiva.
	 */
	public void setHoraLlegadaEfectiva(LocalDateTime llegada) {
		this.horaLlegadaEfectiva = llegada;
		return;
	}
	
	/**
	 * Establece la hora de salida efectiva del vuelo.
	 *
	 * @param salida Hora de salida efectiva.
	 */
	public void setHoraSalidaEfectiva(LocalDateTime salida) {
		this.horaSalidaEfectiva = salida;
		return;
	}
	
	/**
	 * Establece la hora de llegada del vuelo.
	 *
	 * @param llegada Hora de llegada.
	 */
	public void setHoraLlegada(LocalDateTime llegada) {
		this.horaLlegada = llegada;
		return;
	}
	
	/**
	 * Establece la hora de salida del vuelo.
	 *
	 * @param salida Hora de salida.
	 */
	public void setHoraSalida(LocalDateTime salida) {
		this.horaSalida = salida;
		return;
	}
	
	/**
     * Cambia el estado del vuelo según la transición indicada por el nuevo estado de vuelo.
     * 
     * Este método realiza verificaciones sobre el estado actual del vuelo y la situación del avión 
     * para determinar si la transición de estado es válida. Si es válida, actualiza el estado del vuelo
     * y realiza acciones correspondientes como liberar puertas, actualizar la pista o registrar el uso 
     * de recursos como la puerta o el hangar.
     *
     * Las transiciones consideradas incluyen los estados de llegada y salida, como la espera en la pista,
     * el aterrizaje, el embarque, el despegue, entre otros.
     *
     * @param estV El nuevo estado del vuelo.
     * @return true si la transición de estado fue exitosa, false en caso contrario.
     */
	public boolean setEstVuelo(EstadoVuelo estV) {
		if (estV == EstadoVuelo.RETRASADO && this.estVuelo.equals(EstadoVuelo.EN_TIEMPO)) {
			this.notifyObservers(this.estVuelo, estV);
			this.estVuelo = estV;
			return true;
		}
		// Si es un vuelo de llegada
		if(this.llegada) {
			// ESPERANDO_PISTA: no hay todavía pista o localización de aterrizaje
			if(estV == EstadoVuelo.ESPERANDO_PISTA_A && this.pista == null && this.locAterrizaje == null) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				return true;
			}
			// ESPERANDO_AT: espera en su pista, no la usa
			if(estV == EstadoVuelo.ESPERANDO_ATERRIZAJE && this.pista != null && this.pista.getUsando().equals(this) == false) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				this.puerta.liberarPuerta();
				this.avion.setEstadoAvion(EstadoAvion.ESPERANDO_PISTA);
				return true;
			}
			if(estV == EstadoVuelo.ATERRIZADO) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				this.avion.setEstadoAvion(EstadoAvion.EN_PISTA);
				this.pista.actualizarColaVuelos();
				this.horaLlegadaEfectiva = LocalDateTime.now();
				return true;
			}
			if((estV == EstadoVuelo.DESEMBARQUE_INI || estV == EstadoVuelo.DESEMBARQUE_FIN ||
				estV == EstadoVuelo.DESCARGA_INI || estV == EstadoVuelo.DESCARGA_FIN) && this.puerta != null 
				&& this.locAterrizaje != null) {
				if(this.finger) {
					this.avion.setEstadoAvion(EstadoAvion.EN_FINGER);
				} else {
					this.avion.setEstadoAvion(EstadoAvion.EN_PARKING);
				}
				if(estV == EstadoVuelo.DESEMBARQUE_INI || estV == EstadoVuelo.DESCARGA_INI) {
					for(Aerolinea a: this.aerolinea) {
						a.addUso(LocalDateTime.now(), this, this.locAterrizaje);
						a.addUso(LocalDateTime.now(), this, this.puerta);
					}
				}
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				return true;
			}
			if((estV == EstadoVuelo.OPERATIVO && siguienteVueloConAvion() != null && this.locAterrizaje != null) || 
				(estV == EstadoVuelo.EN_HANGAR && siguienteVueloConAvion() == null && this.avion.getHangar() != null)) {
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
					a.setEndUso(LocalDateTime.now(), this, this.puerta);
				}
				this.puerta.liberarPuerta();
				if(this.finger) {
					this.desasignarFinger((Finger) this.locAterrizaje);
				} else {
					this.desasignarParking((ZonaParking) this.locAterrizaje);
				}
				// Añadir nuevo vuelo si es necesario
				if(this.periodicidad != Periodicidad.NO_PERIODICO) {
					for(Aerolinea a: this.aerolinea) {
						a.addVueloPeriodico(this);
					}
				}
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				return true;
			}
			return false;
		}
		// Si es un vuelo de salida
		else {
			// ESPERANDO_PISTA: no hay todavía pista, ya ha cargado
			if(estV == EstadoVuelo.ESPERANDO_PISTA_D && this.pista == null) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				return true;
			}
			// ESPERANDO_DESP: espera en su pista, no la usa
			if(estV == EstadoVuelo.ESPERANDO_DESPEGUE && this.pista != null && this.pista.getUsando().equals(this) == false) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				this.avion.setEstadoAvion(EstadoAvion.ESPERANDO_PISTA);
				for(Aerolinea a: this.aerolinea) {
					a.setEndUso(LocalDateTime.now(), this, this.locAterrizaje);
					a.setEndUso(LocalDateTime.now(), this, this.puerta);
				}
				this.puerta.liberarPuerta();
				if(this.finger) {
					this.desasignarFinger((Finger) this.locAterrizaje);
				} else {
					this.desasignarParking((ZonaParking) this.locAterrizaje);
				}
				return true;
			}
			if(estV == EstadoVuelo.EN_VUELO) {
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				this.avion.setEstadoAvion(EstadoAvion.FUERA_AEROPUERTO);
				this.pista.actualizarColaVuelos();
				this.horaSalidaEfectiva = LocalDateTime.now();
				if(this.locAterrizaje != null && this.puerta != null) {
					for(Aerolinea a: this.aerolinea) {
						a.setEndUso(LocalDateTime.now(), this, this.locAterrizaje);
						a.setEndUso(LocalDateTime.now(), this, this.puerta);
					}
					this.puerta.liberarPuerta();
					if(this.finger) {
						this.desasignarFinger((Finger) this.locAterrizaje);
					} else {
						this.desasignarParking((ZonaParking) this.locAterrizaje);
					}
				}
				// Añadir nuevo vuelo si es necesario
				if(this.periodicidad != Periodicidad.NO_PERIODICO) {
					for(Aerolinea a: this.aerolinea) {
						a.addVueloPeriodico(this);
					}
				}
				return true;
			}
			if((estV == EstadoVuelo.EMBARQUE || estV == EstadoVuelo.CARGA) && this.puerta != null
					&& this.locAterrizaje != null) {
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
				for(Aerolinea a: this.aerolinea) {
					a.addUso(LocalDateTime.now(), this, this.locAterrizaje);
					a.addUso(LocalDateTime.now(), this, this.puerta);
				}
				this.notifyObservers(this.estVuelo, estV);
				this.estVuelo = estV;
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Busca el siguiente vuelo programado con el mismo avión dentro de la misma aerolínea.
	 *
	 * @return Siguiente vuelo asignado al mismo avión, o null si no hay.
	 */
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
		} else { aerolinea = this.getAerolinea(); }
		
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
	
	/**
     * Calcula el retraso total del vuelo sumando el retraso en la salida y en la llegada.
     * 
     * Si la hora de salida o llegada efectiva es null, se considera la hora actual.
     * Si el vuelo salió o llegó antes de lo previsto, el retraso se considera como 0.
     *
     * @return Retraso total en minutos. Si no hubo retraso, retorna 0.
     */
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
	
	/**
	 * Asigna una localización de aterrizaje al vuelo (Finger o Zona de Parking).
	 *
	 * @param locAt Localización de aterrizaje a asignar.
	 * @return true si la asignación fue exitosa, false si no se pudo asignar.
	 */
	public boolean asignarLocAterrizaje(LocalizacionAterrizaje locAt) {
		// Comprobar qué tipo de LocAterrizaje es
		// Si es Finger
		if(locAt instanceof Finger) {
			// Comprobar si se puede asignar el elemento
			if(asignarFinger((Finger) locAt) == false) {
				return false;
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
			this.locAterrizaje = locAt;
			this.finger = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica si un Finger es adecuado para el vuelo y está disponible.
	 *
	 * @param locAt Finger a evaluar.
	 * @return true si se puede asignar, false en caso contrario.
	 */
	private boolean asignarFinger(Finger locAt) {
		// Comprobar que el finger no está siendo usado
		if(locAt.enUso() == true || locAt.getAlturaMax() < this.avion.getTipoAvion().getAltura()) {
			return false;
		}
		locAt.setVuelo(this);
		return true;
	}
	
	private boolean desasignarFinger(Finger locAt) {
		locAt.setVuelo(null);
		return true;
	}
	
	/**
	 * Verifica si una zona de parking es adecuada para el vuelo y tiene espacio disponible.
	 *
	 * @param locAt Zona de parking a evaluar.
	 * @return true si se puede asignar, false en caso contrario.
	 */
	private boolean asignarParking(ZonaParking locAt) {
		// Comprobar que el parking no está lleno o no sea compatible con el avión del vuelo
		if(locAt.comprobarCompatibilidad(this.avion) == false || locAt.numPlazasOcupadasPark() == locAt.getNumPlazas()) {
			return false;
		}
		locAt.addVuelo(this);
		return true;
	}
	
	private boolean desasignarParking(ZonaParking locAt) {
		locAt.removeVuelo(this);
		return true;
	}
	
	/**
	 * Asigna una pista al vuelo si es compatible con su tipo de operación (despegue o aterrizaje).
	 *
	 * @param pista Pista a asignar.
	 * @return true si la pista fue asignada correctamente, false en caso contrario.
	 */
	public boolean asignarPista(Pista pista) {
		// Comprobar que el tipo de pista sea adecuado para el vuelo
		if((pista.isDespegue() && this.llegada) || (!pista.isDespegue() && !this.llegada)) {
			return false;
		}
		pista.addVuelo(this);
		this.pista = pista;
		return true;
	}
	
	/**
	 * Asigna una terminal al vuelo.
	 *
	 * @param terminal Terminal a asignar.
	 * @return true si la terminal fue asignada correctamente, false en caso contrario.
	 */
	public abstract boolean asignarTerminal(Terminal terminal);
	
	/**
	 * Desasigna el vuelo a su terminal.
	 *
	 */
	public void deasignarTerminal() {
		this.terminal.getVuelos().remove(this);
		this.terminal = null;
	}
	
	/**
     * Asigna una puerta al vuelo, validando que la puerta esté disponible y 
     * que el vuelo tenga una terminal asignada previamente.
     * 
     * @param puerta La puerta a asignar al vuelo.
     * @return true si la puerta fue asignada correctamente, false si no fue posible.
     * @throws IllegalArgumentException Si el vuelo no tiene una terminal asignada.
     */
	public boolean asignarPuerta(Puerta puerta) {
		if(this.terminal == null) {
			throw new IllegalArgumentException("No se puede asignar puerta de a un vuelo sin terminal");
		}
		if(this.terminal.getPuertas().containsKey(puerta.getCod()) == false || puerta.enUso() == true) {
			return false;
		}
		this.setPuerta(puerta);
		puerta.setVuelo(this);
		return true;
	}
	
	/**
     * Intenta compartir el vuelo con una aerolínea secundaria, validando que el vuelo 
     * no esté ya compartido, que no tenga ya dos aerolíneas asociadas y que haya una 
     * petición de compartir pendiente.
     *
     * @param aeSec La aerolínea secundaria con la que se desea compartir el vuelo.
     * @return true si el vuelo fue compartido correctamente, false si no fue posible.
     */
	public boolean compartirVuelo(Aerolinea aeSec) {
		if(this.compartido || this.aerolinea.size() == 2 || this.petComp != PeticionCompartir.PETICION_ENVIADA) { 
			this.petComp = PeticionCompartir.NO_COMPARTIDO;
			return false; 
		}
		this.aerolinea.add(aeSec);
		aeSec.addVuelo(this);
		this.compartido = true;
		this.petComp = PeticionCompartir.COMPARTIDO;
		return true;
	}
	
	/**
     * Rechaza la petición de compartir el vuelo, cambiando el estado de la petición.
     *
     * @return true siempre, indicando que la operación se realizó correctamente.
     */
	public boolean rechazarCompartirVuelo() { 
		this.petComp = PeticionCompartir.NO_COMPARTIDO;
		return true;
	}
}
