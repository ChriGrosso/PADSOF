package sistema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.EstadoAvion;
import elementos.Finger;
import elementos.Hangar;
import elementos.Pista;
import elementos.Terminal;
import elementos.ZonaParking;
import facturas.Factura;
import usuarios.Usuario;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

public class SkyManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static transient SkyManager INSTANCE = null;

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
	private Usuario usuarioActual;
	
	// Private constructor suppresses
	private SkyManager() {
		File fichero = new File("skyManagerDatos.dat");
		if (fichero.exists()) {
			 this.cargarDatos();
			 return;
		}
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
		this.usuarioActual = null;
	}
	
    public static SkyManager getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new SkyManager();
        }
        return INSTANCE;
    }
	//El método "clone" sobreescrito que arroja una excepción, para evitar que se pueda clonar el objeto
	public Object clone() throws CloneNotSupportedException {
	    	throw new CloneNotSupportedException(); 
	}
	
	public void guardarDatos() {
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("skyManagerDatos.dat"))) {
			salida.writeObject(this);
	    } catch (IOException e) {
	        System.err.println("Error al guardar los datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	//Método para CARGAR los datos desde un archivo
	//leer de disco la clase sistema
	// actualizar los atributos de la nueva clase sistema creada a la original
	private void cargarDatos() {
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("skyManagerDatos.dat"))) {
	        SkyManager refDisco = (SkyManager) entrada.readObject();
	        this.aerolineas = refDisco.aerolineas;
	        this.aeropuertosExternos = refDisco.aeropuertosExternos;
	        this.costeBaseLlegada = refDisco.costeExtraMercancias;
	        this.costeBaseSalida = refDisco.costeBaseSalida;
	        this.costeExtraMercancias = refDisco.costeExtraMercancias;
	        this.costeExtraPasajeros = refDisco.costeExtraPasajeros;
	        this.facturas = refDisco.facturas;
	        this.fingers = refDisco.fingers;
	        this.hangares = refDisco.hangares;
	        this.informacionPropia = refDisco.informacionPropia;
	        this.pistas = refDisco.pistas;
	        this.terminales = refDisco.terminales;
	        this.usuarios = refDisco.usuarios;
	        this.vuelos = refDisco.vuelos;
	        this.zonasParking = refDisco.zonasParking;
	        
	    } catch (IOException | ClassNotFoundException e) {
	    	System.err.println("Error al cargar los datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void cargarDatosAeropuertos(String nombreFichero) {
	    try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
	            String linea;
	            ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
	            while ((linea = br.readLine()) != null) {
	                String[] datos = linea.split(";");
	                String nombre = datos[0];
	                String codigo = datos[1];
	                String ciudadMasCercana = datos[2];
	                String pais = datos[3];
	                double distanciaCiudad = Double.parseDouble(datos[4]);
	                String direccionCiudad = datos[5];
	                Direccion dir = Direccion.getDireccion(direccionCiudad);
	                int diferenciaHoraria = Integer.parseInt(datos[6]);
	                int numeroTemporadas = Integer.parseInt(datos[7]);

	                for (int i = 0; i < numeroTemporadas; i++) {
	                    String aux = datos[8 + i * 3];
	                    String[] auxs = aux.split("/");
	                    MonthDay fechaInicio = MonthDay.of(Integer.parseInt(auxs[1]), Integer.parseInt(auxs[0]));
	                    
	                    aux = datos[9 + i * 3];
	                    auxs = aux.split(":");
	                    String[] aux2 = auxs[1].split("-");
	                    LocalTime apertura = LocalTime.of(Integer.parseInt(auxs[0]), Integer.parseInt(aux2[0]));
	                    LocalTime cierre = LocalTime.of(Integer.parseInt(aux2[1]), Integer.parseInt(auxs[2]));
	                    
	                    aux = datos[10 + i * 3];
	                    auxs = aux.split("/");
	                    MonthDay fechaFin = MonthDay.of(Integer.parseInt(auxs[1]), Integer.parseInt(auxs[0]));

	                    Temporada temporada = new Temporada(fechaInicio, apertura, cierre, fechaFin);
	                    temporadas.add(temporada);
	                }
	                
	                Aeropuerto aeropuerto = new Aeropuerto(nombre, codigo, ciudadMasCercana, pais, distanciaCiudad, diferenciaHoraria, temporadas, dir);
	                this.registrarAeropuertoExterno(aeropuerto);
	            }
	            
	    } catch (IOException e) { e.printStackTrace(); }
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
	public Boolean registrarAeropuertoExterno(Aeropuerto a) {
		if (this.aeropuertosExternos.containsKey(a.getCodigo()) == true) {
			return false;
		}
		this.aeropuertosExternos.put(a.getCodigo(), a);
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
	
	
	public void logIn(String user, String password) {
		Usuario usuario = usuarios.get(user);
	    if (usuario != null && usuario.getPassword().equals(password)) {
	        this.usuarioActual = usuario;
	    } else {
	        System.out.println("Usuario o contraseña incorrectos.");
	    }
	}
	 
	 
	public Vuelo buscarVueloPorCodigo(String id) {
		this.updateVuelos();
		return this.vuelos.get(id);
	}
	 
	public ArrayList<Vuelo> buscarVuelosPorTerminal(Terminal t) {
		this.updateVuelos();
		if (this.terminales.containsKey(t.getId())) {
			return t.getVuelos();
		}
		return null;
	}
	public ArrayList<Vuelo> buscarVuelosPorHoraLlegada(LocalDateTime hLlegada) {
		this.updateVuelos();
		ArrayList<Vuelo> vuelosHLlegada = new ArrayList<Vuelo>();
		Collection<Vuelo> vuelos = this.vuelos.values();
		
		for(Vuelo v: vuelos) {
			if (v.getHoraLlegada().equals(hLlegada)) {
				vuelosHLlegada.add(v);
			}
		}
		return vuelosHLlegada;		
	}
	
	public ArrayList<Vuelo> buscarVuelosPorHoraSalida(LocalDateTime hSalida) {
		this.updateVuelos();
		ArrayList<Vuelo> vuelosHSalida = new ArrayList<Vuelo>();
		Collection<Vuelo> vuelos = this.vuelos.values();
		
		for(Vuelo v: vuelos) {
			if (v.getHoraSalida().equals(hSalida)) {
				vuelosHSalida.add(v);
			}
		}
		return vuelosHSalida;	
	}
	
	private void updateVuelos() {
		LocalDateTime horaActual = LocalDateTime.now();
		Collection<Vuelo> vuelos = this.vuelos.values();
		
		for (Vuelo v: vuelos) {
			EstadoAvion estAvion = v.getAvion().getEstadoAvion();
			if (v.getEstVuelo().equals(EstadoVuelo.EN_TIEMPO)) {
				if ((horaActual.isAfter(v.getHoraLlegada()) && v.getLlegada() && estAvion.equals(EstadoAvion.FUERA_AEROPUERTO)) || 
						(horaActual.plusMinutes(45).isAfter(v.getHoraSalida()) && !v.getLlegada() && estAvion.equals(EstadoAvion.FUERA_AEROPUERTO)) ||
						(horaActual.isAfter(v.getHoraSalida()) && !v.getLlegada() && 
								(estAvion.equals(EstadoAvion.EN_HANGAR) || estAvion.equals(EstadoAvion.EN_PARKING) || estAvion.equals(EstadoAvion.EN_FINGER))) ) {
					v.setEstVuelo(EstadoVuelo.RETRASADO);
				}
			}
			if (v.getEstVuelo().equals(EstadoVuelo.ESPERANDO_DESPEGUE) && estAvion.equals(EstadoAvion.FUERA_AEROPUERTO)) {
				v.setEstVuelo(EstadoVuelo.EN_VUELO);
			}
		}
		
	}
	
	
	public String verEstadisticasGestor() {
		String estadisticas = "";
		if (this.usuarioActual.esGestor() == false) { 
			return null; 
		}	
		
		
		
		
		return estadisticas;
	}
	
}
