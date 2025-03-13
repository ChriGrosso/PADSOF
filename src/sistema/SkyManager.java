package sistema;

import java.util.ArrayList;
import java.util.HashMap;

import aerolineas.Aerolinea;
import elementos.ElementoEstructural;
import elementos.Finger;
import elementos.Hangar;
import elementos.Pista;
import elementos.Terminal;
import elementos.ZonaParking;
import facturas.Factura;
import usuarios.Usuario;
import vuelos.Vuelo;

public class SkyManager {
	private double costeBaseSalida;
	private double costeBaseLlegada;
	private double costeExtraMercancias;
	private double costeExtraPasajeros;
	private HashMap<String, Aeropuerto> aeropuertosExternos;
	private Aeropuerto informacionPropia;
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Vuelo> vuelos;
	private HashMap<String, Aerolinea> aerolineas;
	private HashMap<String, Terminal> terminales;
	private HashMap<String, Pista> pistas;
	private HashMap<String, Finger> fingers;
	private HashMap<String, ZonaParking> zonasParking;
	private HashMap<String, Hangar> hangares;
	private HashMap<String, Factura> facturas;
	
	
	public SkyManager() {
		this.costeBaseLlegada = 10;
		this.costeBaseSalida = 10;
		this.costeExtraMercancias = 10;
		this.costeExtraPasajeros = 10;
		this.informacionPropia = null;
		this.aeropuertosExternos = new HashMap<String, Aeropuerto>();
		this.usuarios = new HashMap<String, Usuario>();
		this.vuelos = new HashMap<String, Vuelo>();
		this.aerolineas = new HashMap<String, Aerolinea>();
		this.terminales = new HashMap<String, Terminal>();
		this.pistas = new HashMap<String, Pista>();
		this.fingers = new HashMap<String, Finger>();
		this.zonasParking = new HashMap<String, ZonaParking>();
		this.hangares = new HashMap<String, Hangar>();
		this.facturas = new HashMap<String, Factura>();
	}
	
	public double getCosteBaseLlegada() {
		return this.costeBaseLlegada;
	}
	public double getCosteBaseSalida() {
		return this.costeBaseSalida;
	}
	public double getCosteExtraMercancias() {
		return this.costeExtraMercancias;
	}
	public double getCosteExtraPasajeros() {
		return this.costeExtraPasajeros;
	}
	public HashMap<String, Aeropuerto> getAeropuertosExternos(){
		return this.aeropuertosExternos;
	}
	public Aeropuerto getInformacionPropia(){
		return this.informacionPropia;
	}
	public HashMap<String, Usuario> getUsuarios(){
		return this.usuarios;
	}
	public HashMap<String, Vuelo> getVuelos(){
		return this.vuelos;
	}
	public HashMap<String, Aerolinea> getAerolineas(){
		return this.aerolineas;
	}
	public HashMap<String, Factura> getFacturas(){
		return this.facturas;
	}
	
	
	public void setCosteBaseLlegada(double coste) {
		this.costeBaseLlegada = coste;
		return;
	}
	public void setCosteBaseSalida(double coste) {
		this.costeBaseLlegada = coste;
		return;
	}
	public void setCosteExtraMercancias(double coste) {
		this.costeBaseLlegada = coste;
		return;
	}
	public void setCosteExtraPasajeros(double coste) {
		this.costeBaseLlegada = coste;
		return;
	}
	public void setInformacionPropia(Aeropuerto informacion){
		this.informacionPropia = informacion;
		return;
	}
	
	
	
	public Boolean registrarUsuario(Usuario u){
		if (this.usuarios.containsKey(u.getDni()) == true) {
			return false;
		}
		this.usuarios.put(u.getDni(), u);
		return true;
	}
	public Boolean registrarAerolinea(Aerolinea a) {
		if (this.aerolineas.containsKey(a.getId()) == true) {
			return false;
		}
		this.aerolineas.put(a.getId(), a);
		return true;
	}
	public Boolean registrarFactura(Factura f) {
		if (this.facturas.containsKey(f.getId()) == true) {
			return false;
		}
		this.facturas.put(f.getId(), f);
		return true;
	}
	public Boolean registrarTerminal(Terminal t) {
		if (this.terminales.containsKey(t.getId()) == true) {
			return false;
		}
		this.terminales.put(t.getId(), t);
		return true;
	}
	public Boolean registrarPista(Pista p) {
		if (this.pistas.containsKey(p.getId()) == true) {
			return false;
		}
		this.pistas.put(p.getId(), p);
		return true;
	}
	public Boolean registrarFinger(Finger fi) {
		if (this.fingers.containsKey(fi.getId()) == true) {
			return false;
		}
		this.fingers.put(fi.getId(), fi);
		return true;
	}
	public Boolean registrarZonaParking(ZonaParking zp) {
		if (this.zonasParking.containsKey(zp.getId()) == true) {
			return false;
		}
		this.zonasParking.put(zp.getId(), zp);
		return true;
	}
	public Boolean registrarHangar(Hangar h) {
		if (this.hangares.containsKey(h.getId()) == true) {
			return false;
		}
		this.hangares.put(h.getId(), h);
		return true;
	}
	
	
	
	
	
	
	public void cargarDatos() {
		//leer de disco la clase sistema
		// actualizar los atributos de la nueva clase sistema creada a la original
		
	}
}
