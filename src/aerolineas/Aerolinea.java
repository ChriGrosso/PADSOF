
package aerolineas;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import aviones.Avion;
import aviones.TipoAvion;
import elementos.ElementoEstructural;
import elementos.Uso;
import usuarios.Operador;
import usuarios.Usuario;
import vuelos.Periodicidad;
import vuelos.Vuelo;
import vuelos.VueloMercancias;
import vuelos.VueloPasajeros;

/**
 * Clase Aerolinea que representa a una aerolinea con sus características principales, 
 * como su nombre, código, los aviones que tiene o los vuelos que maneja.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es 
 */
public class Aerolinea implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private ArrayList<Vuelo> vuelos;
	private HashMap<String, Avion> aviones;
	private ArrayList<TipoAvion> tiposAviones;
	private HashMap<ClaveVueloElemento, Uso> historialUsos;
	private EstadisticasVuelos estadisticasVuelos;
	private ArrayList<Operador> operadores;
	
	/**
     * Constructor de la clase Aerolinea.
     *
     * @param id       Código de identificación de la aerolínea.
     * @param nombre   Nombre de la aerolínea.
     */
	public Aerolinea(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vuelos = new ArrayList<Vuelo>();
		this.aviones = new HashMap<String, Avion>();
		this.tiposAviones = new ArrayList<TipoAvion>();
		this.historialUsos = new HashMap<ClaveVueloElemento, Uso>();
		this.estadisticasVuelos = new EstadisticasVuelos(this);
		this.operadores = new ArrayList<Operador>();
	}
	
	
	/**
     * Obtiene el código de la aerolínea.
     *
     * @return Código de la aerolínea.
     */
	public String getId()  {
		return this.id;
	}
	
	/**
     * Obtiene el nombre de la aerolínea.
     *
     * @return Nombre de la aerolínea.
     */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
     * Obtiene las estadísticas de la aerolínea.
     *
     * @return Estadísticas de la aerolínea.
     */
	public EstadisticasVuelos getEstadisticas() {
		return this.estadisticasVuelos;
	}
	
	/**
     * Obtiene los vuelos de la aerolínea.
     *
     * @return Lista de vuelos de la aerolínea.
     */
	public ArrayList<Vuelo> getVuelos() {
		return this.vuelos;
	}
	
	/**
     * Obtiene los aviones de la aerolínea.
     *
     * @return Lista (en forma de HashMap) de aviones de la aerolínea.
     */
	public HashMap<String, Avion> getAviones() {
		return this.aviones;
	}
	
	/**
     * Obtiene los tipos de aviones de la aerolínea.
     *
     * @return Lista de tipos de aviones de la aerolínea.
     */
	public ArrayList<TipoAvion> getTiposAvion() {
		return this.tiposAviones;
	}
	
	/**
     * Obtiene los operadores de la aerolínea.
     *
     * @return Lista de operadores de la aerolínea.
     */
	public ArrayList<Operador> getOperadores() {
		return this.operadores;
	}
	
	/**
     * Obtiene el historial de usos de la infraestructura del aeropuerto de la aerolínea.
     *
     * @return Lista (en forma de HashMap) de todos los usos de infraestructura de la aerolínea.
     */
	public HashMap<ClaveVueloElemento, Uso> getHistorialUsos() {
		return this.historialUsos;
	}
	
	/**
     * Obtiene el historial de usos de la infraestructura del aeropuerto de la aerolínea.
     *
     * @return Lista (no en HashMap) de todos los usos de infraestructura de la aerolínea.
     */
	public ArrayList<Uso> getArrayUsos() {
		return (ArrayList<Uso>) this.historialUsos.values();
	}
	
	
	/**
     * Añade un vuelo a la lista de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addVuelo(Vuelo v) {
		if(this.vuelos.contains(v) || !this.aviones.containsValue(v.getAvion())) {
			return false;
		}
		this.vuelos.add(v);
		for (Usuario u: this.operadores) {
			v.addObserver(u);
		}
		return true;
	}
	
	/**
     * Añade un avión a la lista de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addAvion(Avion a) {
		if(this.aviones.containsKey(a.getMatricula()) || !this.tiposAviones.contains(a.getTipoAvion())) {
			return false;
		}
		this.aviones.put(a.getMatricula(), a);
		return true;
	}
	
	/**
     * Añade un vuelo a la lista de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addTipoAvion(TipoAvion ta) {
		if(this.tiposAviones.contains(ta)) {
			return false;
		}
		this.tiposAviones.add(ta);
		return true;
	}
	
	/**
     * Añade un operador a la lista de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addOperador(Operador op) {
		if(this.operadores.contains(op)) {
			return false;
		}
		this.operadores.add(op);
		return true;
	}
	
	/**
     * Añade un uso de la infraestructura por un vuelo de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addUso(LocalDateTime horaUso, Vuelo vuelo, ElementoEstructural elem) {
		if(!this.vuelos.contains(vuelo)) {
			return false;
		}
		Uso u = new Uso(horaUso, elem);
		ClaveVueloElemento clave = new ClaveVueloElemento(vuelo, elem);
		this.historialUsos.put(clave, u);
		vuelo.getMapaElemClave().put(elem, clave);
		elem.addUso(vuelo, horaUso);
		return true;
	}
	
	/**
     * Termina un uso de la infraestructura por un vuelo de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean setEndUso(LocalDateTime horaDesuso, Vuelo vuelo, ElementoEstructural elem) {
		if(!this.vuelos.contains(vuelo)) {
			return false;
		}
		ClaveVueloElemento clave = vuelo.getMapaElemClave().get(elem);
		this.historialUsos.get(clave).setHoraDesuso(horaDesuso);
		elem.getHistorailUsos().get(vuelo).setHoraDesuso(horaDesuso);
		return true;
	}
	
	/**
     * Limpia el historial de usos de la aerolínea.
     *
     */
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
		return;
	}
	
	
	/**
     * Añade un vuelo a la lista de la aerolínea.
     *
     * @return True si se ha podido hacer la operación correctamente, False sino.
     */
	public boolean addVueloPeriodico(Vuelo v) {
		if(!this.vuelos.contains(v) || !this.aviones.containsValue(v.getAvion())) {
			return false;
		}
		Vuelo nuevoV;
		if(v.isVueloMercancias()) {
			if(v.getPeriodicidad() == Periodicidad.DIARIO) {
				nuevoV = new VueloMercancias(v.getId(), v.getOrigen(), v.getDestino(),
				v.getHoraSalida().plusDays(1), v.getHoraLlegada().plusDays(1), v.getAerolineas(), v.getLlegada(), ((VueloMercancias) v).getCarga(),
				((VueloMercancias) v).getMercanciasPeligrosas(), v.getPeriodicidad(), v.getAvion());
			} else {
				DayOfWeek day;
				if(v.getLlegada()) {
					day = v.getHoraLlegada().getDayOfWeek();
				} else {
					day = v.getHoraSalida().getDayOfWeek();
				}
				int diasASumar = siguienteDia(day, v.getDiasAlternos());
				nuevoV = new VueloMercancias(v.getId(), v.getOrigen(), v.getDestino(),
				v.getHoraSalida().plusDays(diasASumar), v.getHoraLlegada().plusDays(diasASumar), v.getAerolineas(), v.getLlegada(), ((VueloMercancias) v).getCarga(),
				((VueloMercancias) v).getMercanciasPeligrosas(), v.getPeriodicidad(), v.getAvion());
			}
		} else {
			if(v.getPeriodicidad() == Periodicidad.DIARIO) {
				nuevoV = new VueloPasajeros(v.getId(), v.getOrigen(), v.getDestino(),
				v.getHoraSalida().plusDays(1), v.getHoraLlegada().plusDays(1), v.getAerolineas(), v.getLlegada(), 
				((VueloPasajeros) v).getNumPasajeros(), v.getPeriodicidad(), v.getAvion());
			} else {
				DayOfWeek day;
				if(v.getLlegada()) {
					day = v.getHoraLlegada().getDayOfWeek();
				} else {
					day = v.getHoraSalida().getDayOfWeek();
				}
				int diasASumar = siguienteDia(day, v.getDiasAlternos());
				nuevoV = new VueloPasajeros(v.getId(), v.getOrigen(), v.getDestino(),
				v.getHoraSalida().plusDays(diasASumar), v.getHoraLlegada().plusDays(diasASumar), v.getAerolineas(), v.getLlegada(), 
				((VueloPasajeros) v).getNumPasajeros(), v.getPeriodicidad(), v.getAvion());
			}
		}
		this.vuelos.add(nuevoV);
		for (Usuario u: this.operadores) {
			v.addObserver(u);
		}
		return true;
	}
	// Calcular el número de días hasta el siguiente vuelo
	private static int siguienteDia(DayOfWeek dia, ArrayList<DayOfWeek> diasAlternos) {
		int minimaDistancia = Integer.MAX_VALUE;
		for(DayOfWeek d: diasAlternos) {
			if (!d.equals(dia)) { // Nos aseguramos de que no sea el mismo día
                int distancia = calcularDistancia(d, dia);
                if (distancia < minimaDistancia) {
                    minimaDistancia = distancia;
                }
            }
		}
		return minimaDistancia;
	}
	// Calcula la distancia circular entre dos días de la semana
    private static int calcularDistancia(DayOfWeek dia1, DayOfWeek dia2) {
        int distancia = Math.abs(dia1.getValue() - dia2.getValue());
        return Math.min(distancia, 7 - distancia); // Distancia circular
    }
}
