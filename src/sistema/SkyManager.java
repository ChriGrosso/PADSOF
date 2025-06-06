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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.Avion;
import aviones.EstadoAvion;
import aviones.TipoAvion;
import elementos.Finger;
import elementos.Hangar;
import elementos.Pista;
import elementos.Terminal;
import elementos.ZonaParking;
import facturas.Factura;
import usuarios.Gestor;
import usuarios.Usuario;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;
import elementos.Puerta;

/**
 * 
 * SkyManager es una clase Singleton que gestiona la información del sistema aeroportuario,
 * incluyendo aeropuertos, usuarios, vuelos, aerolíneas, terminales, pistas, y más.
 * También permite la persistencia de datos a través de la serialización.
 *
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class SkyManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static transient SkyManager INSTANCE = null;
	
	private int diasAntelacionProgVuelo; //dias minimos necesarios de antelacion para registrar un vuelo
	private long rangoTiempoMinutosMostrarTerminalesAvion; //"rango" de tiempo en el que se buscaran las terminales al intentar asignarlas a un avion 
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
	private long ultimoGenIdVuelo;  
	// --- Costos Base y Costos por Hora ---
	private double costoBaseFactura = 0;
	private double costoHoraPista = 0;
	private double costoHoraTerminal = 0;
	private double costoHoraFinger = 0;
	private double costoHoraHangar = 0;
	private double costoHoraAutobus = 0;


	
	/**
     * Constructor privado para aplicar el patrón Singleton.
     * Carga los datos desde un archivo si existe.
     */
	private SkyManager() {
		File fichero = new File("resources\\skyManagerDatos.dat");
		if (fichero.exists()) {
			 this.cargarDatos();
		} else {
			this.diasAntelacionProgVuelo = 1;
			this.rangoTiempoMinutosMostrarTerminalesAvion = 30;
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
			Usuario gestor = new Gestor("01020304A", "Pepe", "password123");
			this.usuarios.put("01020304A", gestor);
		}
	}
	
	/**
     * Retorna la única instancia de SkyManager.
     * 
     * @return Instancia única de SkyManager.
     */
    public static SkyManager getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new SkyManager();
        }
        return INSTANCE;
    }
    
    /**
     * Evita la clonación del objeto Singleton.
     */
	public Object clone() throws CloneNotSupportedException {
	    	throw new CloneNotSupportedException(); 
	}
	
	/**
     * Guarda los datos de SkyManager en un archivo.
     */
	public void guardarDatos() {
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("resources\\skyManagerDatos.dat"))) {
			this.ultimoGenIdVuelo = Vuelo.getGenId();  // Guarda el valor actual
			salida.writeObject(this);
	    } catch (IOException e) {
	        System.err.println("Error al guardar los datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
     * Carga los datos desde un archivo y actualiza la instancia actual.
     */
	private void cargarDatos() {
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("resources\\skyManagerDatos.dat"))) {
	        SkyManager refDisco = (SkyManager) entrada.readObject();
	        this.aerolineas = (refDisco.aerolineas!= null) ? refDisco.aerolineas : new HashMap<String, Aerolinea>();
	        this.aeropuertosExternos = (refDisco.aeropuertosExternos!= null) ? refDisco.aeropuertosExternos : new HashMap<String, Aeropuerto>();
	        this.costeBaseSalida = refDisco.costeBaseSalida;
	        this.costeExtraMercancias = refDisco.costeExtraMercancias;
	        this.costeExtraPasajeros = refDisco.costeExtraPasajeros;
	        this.facturas = (refDisco.facturas!= null) ? refDisco.facturas : new HashMap<String, Factura>();
	        this.fingers = (refDisco.fingers!= null) ? refDisco.fingers : new HashMap<String, Finger>();
	        this.hangares = (refDisco.hangares!= null) ? refDisco.hangares : new HashMap<String, Hangar>();
	        this.informacionPropia = refDisco.informacionPropia;
	        this.pistas = (refDisco.pistas!= null) ? refDisco.pistas : new HashMap<String, Pista>();
	        this.terminales = (refDisco.terminales!= null) ? refDisco.terminales : new HashMap<String, Terminal>();
	        this.usuarios = (refDisco.usuarios!= null) ? refDisco.usuarios : new HashMap<String, Usuario>();
	        this.vuelos = (refDisco.vuelos!= null) ? refDisco.vuelos : new HashMap<String, Vuelo>();
	        this.zonasParking = (refDisco.zonasParking!= null) ? refDisco.zonasParking : new HashMap<String, ZonaParking>();
	        this.diasAntelacionProgVuelo = refDisco.diasAntelacionProgVuelo;
	        this.rangoTiempoMinutosMostrarTerminalesAvion = refDisco.rangoTiempoMinutosMostrarTerminalesAvion;
	        this.usuarioActual = null;
	        this.ultimoGenIdVuelo = refDisco.ultimoGenIdVuelo;  // Restaurar
	        Vuelo.setGenId(this.ultimoGenIdVuelo);              // Aplicarlo a Vuelo
	        actualizarContadorPistas();
	        actualizarContadorTerminales();
	        actualizarContadorFingers();
	        actualizarContadorHangars();
	        actualizarContadorZonaParking();
	        this.costeBaseLlegada = refDisco.costeBaseLlegada; // Correzione
	        this.costeBaseSalida = refDisco.costeBaseSalida;
	        this.costeExtraMercancias = refDisco.costeExtraMercancias;
	        this.costeExtraPasajeros = refDisco.costeExtraPasajeros;

	        this.costoBaseFactura = refDisco.costoBaseFactura;
	        this.costoHoraPista = refDisco.costoHoraPista;
	        this.costoHoraTerminal = refDisco.costoHoraTerminal;
	        this.costoHoraFinger = refDisco.costoHoraFinger;
	        this.costoHoraHangar = refDisco.costoHoraHangar;
	        this.costoHoraAutobus = refDisco.costoHoraAutobus;
	        
	        
	    } catch (IOException | ClassNotFoundException e) {
	    	System.err.println("Error al cargar los datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/**
     * Carga la información de aeropuertos externos desde un archivo de texto.
     * 
     * @param nombreFichero nombre (path) del archivo que contiene los datos de los aeropuertos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
	public void cargarDatosAeropuertos(String nombreFichero) {
	    try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
	            String linea;
	            ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
	            while ((linea = br.readLine()) != null) {
	                String[] datos = linea.split(";");
	                if (datos.length > 7) { // Asegura que hay al menos 8 elementos
		                String nombre = datos[0];
		                String codigo = datos[1];
		                String ciudadMasCercana = datos[2];
		                String pais = datos[3];
		                double distanciaCiudad = Double.parseDouble(datos[4]);
		                String direccionCiudad = datos[5];
		                Direccion dir = Direccion.getDireccion(direccionCiudad);
		                int diferenciaHoraria = Integer.parseInt(datos[6]);
		                int numeroTemporadas = Integer.parseInt(datos[7]);
		                
		                if (datos.length >= 8 + numeroTemporadas * 3) {
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
		                }
		                Aeropuerto aeropuerto = new Aeropuerto(nombre, codigo, ciudadMasCercana, pais, distanciaCiudad, diferenciaHoraria, temporadas, dir);
		                this.registrarAeropuertoExterno(aeropuerto);
	                }
	            }
	            
	    } catch (IOException e) { e.printStackTrace(); }
	}

	 /**
     * Obtiene el costo base de llegada.
     * 
     * @return Costo base de llegada.
     */
	public double getCosteBaseLlegada() {
		return this.costeBaseLlegada;
	}
	
	/**
     * Obtiene el costo base de salida.
     * 
     * @return Costo base de salida.
     */
	public double getCosteBaseSalida() {
		return this.costeBaseSalida;
	}
	
	/**
     * Obtiene el costo extra asociado al transporte de mercancías.
     * 
     * @return Costo extra por mercancías.
     */
	public double getCosteExtraMercancias() {
		return this.costeExtraMercancias;
	}
	
	/**
     * Obtiene el costo extra asociado al transporte de pasajeros.
     * 
     * @return Costo extra por pasajero.
     */
	public double getCosteExtraPasajeros() {
		return this.costeExtraPasajeros;
	}
	
	/**
     * Obtiene la lista de aeropuertos externos registrados.
     * 
     * @return Mapa con aeropuertos externos (clave: código, valor: objeto Aeropuerto).
     */
	public HashMap<String, Aeropuerto> getAeropuertosExternos(){
		return this.aeropuertosExternos;
	}
	
	/**
     * Obtiene la información del aeropuerto propio.
     * 
     * @return Objeto Aeropuerto con la información propia.
     */
	public Aeropuerto getInformacionPropia(){
		return this.informacionPropia;
	}
	
	/**
     * Obtiene la lista de usuarios registrados.
     * 
     * @return Mapa con usuarios (clave: DNI, valor: objeto Usuario).
     */
	public HashMap<String, Usuario> getUsuarios(){
		return this.usuarios;
	}
	
	/**
     * Obtiene la lista de vuelos registrados.
     * 
     * @return Mapa con vuelos (clave: identificador del vuelo, valor: objeto Vuelo).
     */
	public HashMap<String, Vuelo> getVuelos(){
		return this.vuelos;
	}
	
	/**
     * Obtiene la lista de aerolíneas registradas.
     * 
     * @return Mapa con aerolíneas registradas.
     */
	public HashMap<String, Aerolinea> getAerolineas(){
		return this.aerolineas;
	}
	
	/**
     * Obtiene la lista de facturas registradas.
     * 
     * @return Mapa con facturas registradas.
     */
	public HashMap<String, Factura> getFacturas(){
		return this.facturas;
	}
	
	/**
     * Obtiene la lista de terminales registradas.
     * 
     * @return Mapa con terminales registradas.
     */
	public HashMap<String, Terminal> getTerminales(){
		return this.terminales;
	}
	
	/**
     * Obtiene la lista de pistas registradas.
     * 
     * @return Mapa con pistas registradas.
     */
	public HashMap<String, Pista> getPistas(){
		return this.pistas;
	}
	
	/**
     * Obtiene la lista de fingers registrados.
     * 
     * @return Mapa con fingers registrados.
     */
	public HashMap<String, Finger> getFingers(){
		return this.fingers;
	}
	
	/**
     * Obtiene la lista de zonas de parking registradas.
     * 
     * @return Mapa con zonas de parking registradas.
     */
	public HashMap<String, ZonaParking> getZonasParking(){
		return this.zonasParking;
	}
	
	/**
     * Obtiene la lista de hangares registrados.
     * 
     * @return Mapa con hangares registrados.
     */
	public HashMap<String, Hangar> getHangares(){
		return this.hangares;
	}
	
	/**
     * Obtiene el usuario actual que ha iniciado sesion.
     * 
     * @return usuario actual.
     */
	public Usuario getUsuarioActual(){
		return this.usuarioActual;
	}
	
	/**
     * Obtiene el rango de tiempo (en minutos) que se considera a la hora de mostrar las 
     * terminales disponibles al configurar un vuelo.
     * 
     * @return rango de tiempo en el que se buscaran las terminales al intentar asignarlas a un avion.
     */
	public long geRangoTiempoMinutosMostrarTerminalesAvion() {
		return this.rangoTiempoMinutosMostrarTerminalesAvion;
	}
	
	/**
     * Obtiene la cantidad de dias necesarios de antelacion para registrar un vuelo
     * 
     * @return dias de antelacion necesarios para programar un vuelo.
     */
	public int getDiasAntelacionProgVuelo() {
		return this.diasAntelacionProgVuelo;
	}
	
	/**
     * Modifica el rango de tiempo (en minutos) que se considera a la hora de mostrar las 
     * terminales disponibles al configurar un vuelo.
     * 
     * @param coste Nuevo valor del costo base de llegada.
     */
	public void setRangoTiempoMinutosMostrarTerminalesAvion(long mins) {
		this.rangoTiempoMinutosMostrarTerminalesAvion = mins;
		return;
	}
	
	/**
     * Modifica la cantidad de dias necesarios de antelacion para registrar un vuelo
     * 
     * @param coste Nuevo valor del costo base de llegada.
     */
	public void setDiasAntelacionProgVuelo(int dias) {
		this.diasAntelacionProgVuelo = dias;
		return;
	}
	
	/**
     * Modifica el costo base de llegada.
     * 
     * @param coste Nuevo valor del costo base de llegada.
     */
	public void setCosteBaseLlegada(double coste) {
		this.costeBaseLlegada = coste;
		return;
	}
	
	 /**
     * Modifica el costo base de salida.
     * 
     * @param coste Nuevo valor del costo base de salida.
     */
	public void setCosteBaseSalida(double coste) {
		this.costeBaseSalida = coste;
		return;
	}
	
	/**
     * Modifica el costo extra asociado al transporte de mercancías.
     * 
     * @param coste Nuevo valor del costo extra por mercancías.
     */
	public void setCosteExtraMercancias(double coste) {
		this.costeExtraMercancias = coste;
		return;
	}
	
	/**
     * Modifica el costo extra asociado al transporte de pasajeros.
     * 
     * @param coste Nuevo valor del costo extra por pasajeros.
     */
	public void setCosteExtraPasajeros(double coste) {
		this.costeExtraPasajeros = coste;
		return;
	}
	
	/**
     * Modifica la información del aeropuerto propio.
     * 
     * @param informacion Nuevo objeto Aeropuerto con la información actualizada.
     */
	public void setInformacionPropia(Aeropuerto informacion){
		this.informacionPropia = informacion;
		return;
	}
	
	/**
     * Registra un usuario en el sistema.
     * @param u Usuario a registrar.
     * @return true si el usuario fue registrado correctamente, false si ya existía.
     */
	public Boolean registrarUsuario(Usuario u){
		if (this.usuarios.containsKey(u.getDni()) == true) {
			return false;
		}
		this.usuarios.put(u.getDni(), u);
		return true;
	}
	
	/**
     * Registra un nuevo vuelo en el sistema.
     * 
     * @param v vuelo a registrar.
     * @return true si el vuelo se registró correctamente, false si ya existía.
     */
	public Boolean registrarVuelo(Vuelo v) {
		if (this.vuelos.containsKey(v.getId()) || v.getHoraSalida().isBefore(LocalDateTime.now().plusDays(this.diasAntelacionProgVuelo))) {
			return false;
		}
	
		this.vuelos.put(v.getId(), v);
		return true;
	}
	
	/**
     * Registra una nueva aerolínea en el sistema.
     * 
     * @param a Aerolínea a registrar.
     * @return true si la aerolínea se registró correctamente, false si ya existía.
     */
	public Boolean registrarAerolinea(Aerolinea a) {
		if (this.aerolineas.containsKey(a.getId()) == true) {
			return false;
		}
		
		this.aerolineas.put(a.getId(), a);
		return true;
	}
	
	/**
     * Registra un aeropuerto externo en el sistema.
     * 
     * @param a Aeropuerto a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarAeropuertoExterno(Aeropuerto a) {
		if (this.aeropuertosExternos.containsKey(a.getCodigo()) == true) {
			return false;
		}
		this.aeropuertosExternos.put(a.getCodigo(), a);
		return true;
	}
	
	/**
     * Registra una factura en el sistema.
     * 
     * @param f Factura a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarFactura(Factura f) {
		if (this.facturas.containsKey(f.getId()) == true) {
			return false;
		}
		
		this.facturas.put(f.getId(), f);
		return true;
	}
	
	/**
     * Registra una terminal en el sistema.
     * 
     * @param t Terminal a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarTerminal(Terminal t) {
		if (this.terminales.containsKey(t.getId()) == true) {
			return false;
		}
	
		this.terminales.put(t.getId(), t);
		return true;
	}
	
	/**
     * Registra una pista en el sistema.
     * 
     * @param p Pista a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarPista(Pista p) {
		if (this.pistas.containsKey(p.getId()) == true) {
			return false;
		}
		this.pistas.put(p.getId(), p);
		return true;
	}
	
	/**
     * Registra un finger en el sistema.
     * 
     * @param fi Finger a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarFinger(Finger fi) {
		if (this.fingers.containsKey(fi.getId()) == true) {
			return false;
		}
		this.fingers.put(fi.getId(), fi);
		return true;
	}
	
	/**
     * Registra una zona de parking en el sistema.
     * 
     * @param zp Zona de parking a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarZonaParking(ZonaParking zp) {
		if (this.zonasParking.containsKey(zp.getId()) == true) {
			return false;
		}
		this.zonasParking.put(zp.getId(), zp);
		return true;
	}
	
	 /**
     * Registra un hangar en el sistema.
     * 
     * @param h Hangar a registrar.
     * @return true si se registró correctamente, false si ya existía.
     */
	public Boolean registrarHangar(Hangar h) {
		if (this.hangares.containsKey(h.getId()) == true) {
			return false;
		}
		this.hangares.put(h.getId(), h);
		return true;
	}
	
	/**
     * Inicia sesión con un usuario y una contraseña.
     * 
     * @param user DNI del usuario.
     * @param password Contraseña del usuario.
     * @throws IllegalArgumentException Si el usuario no existe o la contraseña es incorrecta.
     */
	public void logIn(String user, String password) {
		Usuario usuario = usuarios.get(user);
	    if (this.usuarios.containsKey(user) && usuario.getPassword().equals(password)) {
	        this.usuarioActual = usuario;
	    } else {
	    	throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
	    }
	}
	 
	/**
     * Busca un vuelo por su código.
     * @param id Código del vuelo.
     * @return Vuelo encontrado o null si no existe.
     */
	public Vuelo buscarVueloPorCodigo(String id) {
		this.updateVuelos();
		return this.vuelos.get(id);
	}
	
	/**
     * Busca los vuelos del mismo día.
     * @return Vuelo/s encontrado/s o null si no hay.
     */
	public ArrayList<Vuelo> buscarVuelosDelDia() {
		this.updateVuelos();
		LocalDate hoy = LocalDate.now();
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		for(Vuelo v: this.getVuelos().values()) {
			if(v.getHoraSalida().getDayOfYear() == hoy.getDayOfYear() && v.getHoraSalida().getYear() == hoy.getYear()) {
				vuelos.add(v);
			}
		}
		return vuelos;
	}
	
	/**
     * Busca un vuelo por sus aeropuertos de origen.
     * @param origen Aeropuerto de origen del vuelo.
     * @return Vuelo/s encontrado/s o null si no hay.
     */
	public ArrayList<Vuelo> buscarVuelosPorOrigen(Aeropuerto origen) {
		this.updateVuelos();
		ArrayList<Vuelo> vuelosOrigen = new ArrayList<Vuelo>();
		for(Vuelo v: this.getVuelos().values()) {
			if(v.getOrigen().equals(origen)) {
				vuelosOrigen.add(v);
			}
		}
		return vuelosOrigen;
	}
	
	/**
     * Busca un vuelo por sus aeropuertos de destino.
     * @param destino Aeropuerto de destino del vuelo.
     * @return Vuelo/s encontrado/s o null si no hay.
     */
	public ArrayList<Vuelo> buscarVuelosPorDestino(Aeropuerto destino) {
		this.updateVuelos();
		ArrayList<Vuelo> vuelosDest = new ArrayList<Vuelo>();
		for(Vuelo v: this.getVuelos().values()) {
			if(v.getDestino().equals(destino)) {
				vuelosDest.add(v);
			}
		}
		return vuelosDest;
	}
	
	/**
     * Busca un vuelo por sus aeropuertos de origen y destino.
     * @param origen Aeropuerto de origen del vuelo.
     * @param destino Aeropuerto de destino del vuelo.
     * @return Vuelo/s encontrado/s o null si no hay.
     */
	public ArrayList<Vuelo> buscarVuelosPorOrigen(Aeropuerto origen, Aeropuerto destino) {
		this.updateVuelos();
		ArrayList<Vuelo> vuelosOD = new ArrayList<Vuelo>();
		for(Vuelo v: this.getVuelos().values()) {
			if(v.getOrigen().equals(origen) && v.getDestino().equals(destino)) {
				vuelosOD.add(v);
			}
		}
		return vuelosOD;
	}
	
	/**
     * Busca los vuelos asociados a una terminal específica.
     * 
     * @param t Terminal en la que se quieren buscar vuelos.
     * @return Una lista de vuelos de la terminal si existe, de lo contrario, lanza una excepción.
     * @throws IllegalArgumentException Si la terminal no está registrada en el sistema.
     */
	public ArrayList<Vuelo> buscarVuelosPorTerminal(Terminal t) {
		this.updateVuelos();
		if (this.terminales.containsKey(t.getId())) {
			return t.getVuelos();
		}
		throw new IllegalArgumentException("La terminal no está registrada en el sistema.");
	}
	
	/**
	 * Busca vuelos que tengan una hora de llegada específica (solo hora y minuto, sin segundos).
	 * 
	 * @param hLlegada Hora de llegada de los vuelos a buscar.
	 * @return Una lista de vuelos que coinciden con la hora de llegada indicada.
	 */
	public ArrayList<Vuelo> buscarVuelosPorHoraLlegada(LocalTime hLlegada) {
	    this.updateVuelos();
	    ArrayList<Vuelo> vuelosHLlegada = new ArrayList<>();
	    Collection<Vuelo> vuelos = this.vuelos.values();
	    
	    for (Vuelo v : vuelos) {
	        LocalTime horaLlegadaVuelo = v.getHoraLlegada().toLocalTime().withSecond(0).withNano(0);
	        LocalTime horaBuscada = hLlegada.withSecond(0).withNano(0);
	        if (horaLlegadaVuelo.getHour() == horaBuscada.getHour() && horaLlegadaVuelo.getMinute() == horaBuscada.getMinute()) {
	            vuelosHLlegada.add(v);
	        }
	    }
	    return vuelosHLlegada;
	}

	/**
	 * Busca vuelos que tengan una hora de salida específica (solo hora y minuto, sin segundos).
	 * 
	 * @param hSalida Hora de salida de los vuelos a buscar.
	 * @return Una lista de vuelos que coinciden con la hora de salida indicada.
	 */
	public ArrayList<Vuelo> buscarVuelosPorHoraSalida(LocalTime hSalida) {
	    this.updateVuelos();
	    ArrayList<Vuelo> vuelosHSalida = new ArrayList<>();
	    Collection<Vuelo> vuelos = this.vuelos.values();
	    
	    for (Vuelo v : vuelos) {
	        LocalTime horaSalidaVuelo = v.getHoraSalida().toLocalTime().withSecond(0).withNano(0);
	        LocalTime horaBuscada = hSalida.withSecond(0).withNano(0);
	        if (horaSalidaVuelo.getHour() == horaBuscada.getHour() && horaSalidaVuelo.getMinute() == horaBuscada.getMinute()) {
	            vuelosHSalida.add(v);
	        }
	    }
	    return vuelosHSalida;
	}
	
	/**
     * Actualiza el estado de los vuelos basado en la hora actual.
     * De forma que se puedan actualizar los estados automaticos.
     */
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
	
	/**
     * Método que muestra las esdisticas de uso medio diario de distintos elementos del aeropuerto.
     * Solo se puden ver si el usuario tiene el rol de Gestor.
     *  
     * @return Un string con todos los datos de las estadisticas.
     */
	public String verEstadisticasGestor() {
		if (this.usuarioActual.esGestor() == false) { 
			return null; 
		}
		String estadFingers = "Uso Medio Diario Fingers: \n";
		String estadParkings = "Uso Medio Diario Zonas Parking: \n";
		String estadPuertas = "Uso Medio Diario Puertas: \n";
		String estadHangares = "Uso Medio Diario Hangares: \n";
		Collection<Finger> fingers = this.fingers.values();
		Collection<ZonaParking> parkings = this.zonasParking.values();
		Collection<Hangar> hangares = this.hangares.values();
		Collection<Terminal> terminales = this.terminales.values();

		for(Finger f: fingers) {
			estadFingers += "Finger ("+ f.getId() +"): "+ f.mediaHorasUsoDiario() +" horas\n";
		}
		for(ZonaParking k: parkings) {
			estadParkings += "Zona Parking ("+ k.getId() +"): "+ k.mediaHorasUsoDiario() +" horas\n";
		}
		for(Hangar h: hangares) {
			estadHangares += "Hangar ("+ h.getId() +"): "+ h.mediaHorasUsoDiario() +" horas\n";
		}		
		for(Terminal t: terminales) {
			Collection<Puerta> puertas = t.getPuertas().values();
			estadPuertas += "Terminal "+ t.getId() +":\n";
			for(Puerta p: puertas) {
				estadPuertas += "Puerta ("+ p.getCod() +"): "+ p.mediaHorasUsoDiario() +" horas\n";
			}
		}
		
		return estadFingers+estadParkings+estadPuertas+estadHangares;
	}
	

	/**
     * Devuelve las facturas que esten pagadas o las no lo esten dependiendo del parametro introducido.
     * 
     * @param pagado Boolean sobre si se quieren facturas por pagar o que ya esten pagadas.
     * @return Una lista de facturas.
     */
	 public ArrayList<Factura> verFacturasPorEstatusDePago(Boolean pagado) {
		 ArrayList<Factura> facturas = new ArrayList<Factura>();
		 Collection <Factura> factSistema = this.facturas.values();
		 
		 for (Factura f: factSistema) {
			 if (f.isPagado() == pagado) {
				 facturas.add(f);
			 }
		 }
		 return facturas;
	 }
	 
	 /**
	 * Devuelve las terminales disponibles para asignar a un vuelo.
	 * 
	 * @param vuelo Vuelo al que se le quiere asignar terminal.
	 * @return Una lista de terminables disponibles.
	 */
	 public ArrayList<Terminal> getTerminalesDisponibles(Vuelo vuelo) {
		 ArrayList<Terminal> terminales = new ArrayList<Terminal>();
		 Collection<Terminal> termSistema = this.terminales.values();
		 TipoAvion avion = vuelo.getAvion().getTipoAvion();
		 
		 LocalDateTime horaVuelo, r1, r2;
		 if (vuelo.getLlegada()) {
			 horaVuelo = vuelo.getHoraLlegada();
		 } else {
			 horaVuelo = vuelo.getHoraSalida();
		 }
		 r1 = horaVuelo.minusMinutes(rangoTiempoMinutosMostrarTerminalesAvion);
		 r2 = horaVuelo.plusMinutes(rangoTiempoMinutosMostrarTerminalesAvion);
		 
		 for (Terminal t: termSistema) {
			 if (t.isMercancias() == vuelo.isVueloMercancias() && avion.getCapacidad() < t.getCapDisponible(r1, r2) 
					 && t.numPuertasOcupadasTerm(r1, r2)<t.getNumeroPuertas()) {
				 terminales.add(t);
			 }
		 }
		 
		 return terminales;
	 }
	 
	 /**
	 * Devuelve las pistas disponibles para asignar a un vuelo.
	 * 
	 * @param vuelo Vuelo al que se le quiere asignar la pista.
	 * @return Una lista de pistas disponibles.
	 */
	 public ArrayList<Pista> getPistasDisponibles(Vuelo v) {
		 ArrayList<Pista> disponibles = new ArrayList<Pista>();
		 Collection<Pista> pistas = this.pistas.values();
		 
		 for (Pista p: pistas) {
			 if (v.getLlegada()!=p.isDespegue() && !p.enUso()) {
				 disponibles.add(p);
			 }
		 }
		 return disponibles;
	 }
	 
	 /**
	 * Devuelve los fingers disponibles para asignar a un vuelo.
	 * 
	 * @param vuelo Vuelo al que se le quiere asignar la pista.
	 * @return Una lista con los fingers disponibles.
	 */
	 public ArrayList<Finger> getFingersDisponibles(Vuelo v) {
		 ArrayList<Finger> disponibles = new ArrayList<Finger>();
		 Collection<Finger> fingers = this.fingers.values();
		 
		 for (Finger f: fingers) {
			 if (f.comprobarCompatibilidad(v.getAvion()) && !f.enUso()) {
				 disponibles.add(f);
			 }
		 }
		 return disponibles;
	 }
	 
	 /**
	 * Devuelve los hangares disponibles para almacenar un avión.
	 * 
	 * @param a Avion al que se le quiere asignar el hangar.
	 * @return Una lista con los hangares disponibles.
	 */
	 public ArrayList<Hangar> getHangaresDisponibles(Avion a) {
		 ArrayList<Hangar> disponibles = new ArrayList<Hangar>();
		 Collection<Hangar> hangares = this.hangares.values();
		 
		 for (Hangar h: hangares) {
			 if (h.comprobarCompatibilidad(a) && h.numPlazasOcupadasHangar()<h.getNumPlazas()) {
				 disponibles.add(h);
			 }
		 }
		 return disponibles;
	 }

	 
	 /**
	  * Devuelve la representacion en String del sistema.
	  * 
	  * @return Un string con todo el contenido del sistema.
	  */
	 public String toString() {
		 String s = "Sistema SkyManager costes: BaseLlegada ("+this.costeBaseLlegada+"), BaseSalida: ("+this.costeBaseSalida+
				 "), ExtraMercancias: ("+this.costeExtraMercancias+"), ExtraPasajeros: ("+this.costeExtraPasajeros+")\n";
		 
		 s += "Informacion Propia Apuerto: "+this.informacionPropia+"\n";
		 s += "Aeropuertos Externos: "+this.aeropuertosExternos.values()+"\n";
		 s += "Usuarios: "+ this.usuarios.values()+"\n";
		 s += "Vuelos: "+ this.vuelos.values()+"\n";
		 s += "Aerolineas: "+ this.aerolineas.values()+"\n";
		 s += "Terminales: "+this.terminales.values()+"\n";
		 s += "Pistas: "+this.pistas.values()+"\n";
		 s += "Fingers: "+this.fingers.values()+"\n";
		 s += "Zonas Parking: "+this.zonasParking.values()+"\n";
		 s += "Hangares: "+this.hangares.values()+"\n";
		 s += "Facturas: "+this.facturas.values()+"\n";
		 s += "Usuario Actual: "+this.usuarioActual+"\n";
		 
		 return s;
	 }
	 
	 /**
	  * Actualiza el contador de pistas para asegurar que los identificadores sean únicos y consecutivos.
	  * Busca el identificador más alto en la colección y establece el siguiente número disponible.
	  */
	 private void actualizarContadorPistas() {
		    int max = 0;
		    for (String id : pistas.keySet()) {
		        if (id.startsWith("PS")) {
		            try {
		                int num = Integer.parseInt(id.substring(2));
		                if (num >= max) {
		                    max = num + 1; // Prossimo disponibile
		                }
		            } catch (NumberFormatException ignored) {}
		        }
		    }
		    Pista.setContador(max);
		}
	 
	 /**
	  * Actualiza el contador de terminales asegurando que los identificadores sean únicos y consecutivos.
	  */
	 public void actualizarContadorTerminales() {
		    int max = 0;
		    for (Terminal t : terminales.values()) {
		        try {
		            String idNumStr = t.getId().substring(1); // Rimuove la "T"
		            int num = Integer.parseInt(idNumStr);
		            if (num > max) {
		                max = num;
		            }
		        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
		            // In caso di errore sul parsing, ignoriamo quel terminale
		        }
		    }
		    Terminal.setContador(max + 1);
		}
	 
	 /**
	  * Actualiza el contador de fingers garantizando identificadores únicos y consecutivos.
	  */
	 public void actualizarContadorFingers() {
		    int max = 0;
		    for (Finger f : fingers.values()) {
		        try {
		            String idNumStr = f.getId().substring(1); // Rimuove la "F"
		            int num = Integer.parseInt(idNumStr);
		            if (num > max) {
		                max = num;
		            }
		        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
		            // Ignora ID malformati
		        }
		    }
		    Finger.setContador(max + 1);
		}
	 
	 /**
	  * Actualiza el contador de hangares asegurando que los identificadores sean únicos y consecutivos.
	  */
	 public void actualizarContadorHangars() {
		    int max = 0;
		    for (Hangar h : hangares.values()) {
		        try {
		            String idNumStr = h.getId().substring(1); // Rimuove "H"
		            int num = Integer.parseInt(idNumStr);
		            if (num > max) {
		                max = num;
		            }
		        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
		            // Ignora errori
		        }
		    }
		    Hangar.setContador(max + 1);
		}
	 
	 /**
	  * Actualiza el contador de zonas de estacionamiento asegurando que los identificadores sean únicos y consecutivos.
	  */
	 public void actualizarContadorZonaParking() {
		    int max = 0;
		    for (ZonaParking zp : zonasParking.values()) {
		        try {
		            String idNumStr = zp.getId().substring(2); // Rimuove "ZP"
		            int num = Integer.parseInt(idNumStr);
		            if (num > max) {
		                max = num;
		            }
		        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
		            // Ignora errori
		        }
		    }
		    ZonaParking.setContador(max + 1);
		}
	 
	// --- Costos Base y Costos por Hora ---
	 /**
	  * Obtiene el costo base de una factura.
	  * 
	  * @return Costo base de la factura.
	  */
	 public double getCostoBaseFactura() {
	     return costoBaseFactura;
	 }
	 
	 /**
	  * Establece el costo base de una factura.
	  * 
	  * @param costoBaseFactura Nuevo costo base de la factura.
	  */
	 public void setCostoBaseFactura(double costoBaseFactura) {
	     this.costoBaseFactura = costoBaseFactura;
	 }

	 /**
	  * Obtiene el costo por hora de una pista.
	  * 
	  * @return Costo por hora de una pista.
	  */
	 public double getCostoHoraPista() {
	     return costoHoraPista;
	 }

	 /**
	  * Establece el costo por hora de una pista.
	  * 
	  * @param costoHoraPista Nuevo costo por hora de la pista.
	  */
	 public void setCostoHoraPista(double costoHoraPista) {
	     this.costoHoraPista = costoHoraPista;
	 }

	 /**
	  * Obtiene el costo por hora de una terminal.
	  * 
	  * @return Costo por hora de una terminal.
	  */
	 public double getCostoHoraTerminal() {
	     return costoHoraTerminal;
	 }

	 /**
	  * Establece el costo por hora de una terminal.
	  * 
	  * @param costoHoraTerminal Nuevo costo por hora de la terminal.
	  */
	 public void setCostoHoraTerminal(double costoHoraTerminal) {
	     this.costoHoraTerminal = costoHoraTerminal;
	 }

	 /**
	  * Obtiene el costo por hora de un finger.
	  * 
	  * @return Costo por hora de un finger.
	  */
	 public double getCostoHoraFinger() {
	     return costoHoraFinger;
	 }

	 /**
	  * Establece el costo por hora de un finger.
	  * 
	  * @param costoHoraFinger Nuevo costo por hora del finger.
	  */
	 public void setCostoHoraFinger(double costoHoraFinger) {
	     this.costoHoraFinger = costoHoraFinger;
	 }

	 /**
	  * Obtiene el costo por hora de un hangar.
	  * 
	  * @return Costo por hora de un hangar.
	  */
	 public double getCostoHoraHangar() {
	     return costoHoraHangar;
	 }

	 /**
	  * Establece el costo por hora de un hangar.
	  * 
	  * @param costoHoraHangar Nuevo costo por hora del hangar.
	  */
	 public void setCostoHoraHangar(double costoHoraHangar) {
	     this.costoHoraHangar = costoHoraHangar;
	 }

	 /**
	  * Obtiene el costo por hora de un autobús.
	  * 
	  * @return Costo por hora de un autobús.
	  */
	 public double getCostoHoraAutobus() {
	     return costoHoraAutobus;
	 }

	 /**
	  * Establece el costo por hora de un autobús.
	  * 
	  * @param costoHoraAutobus Nuevo costo por hora del autobús.
	  */
	 public void setCostoHoraAutobus(double costoHoraAutobus) {
	     this.costoHoraAutobus = costoHoraAutobus;
	 }

	 // --- Métodos para actualizar el coste por hora de los elementos ---

	 /**
	  * Actualiza el costo por hora de todas las pistas en el sistema.
	  * 
	  * @param nuevoCoste Nuevo costo por hora de las pistas.
	  */
	 public void actualizarCosteHoraPistas(double nuevoCoste) {
	     for (Pista pista : pistas.values()) {
	         pista.setCostePorHora(nuevoCoste);
	     }
	 }

	 /**
	  * Actualiza el costo por hora de todas las terminales en el sistema.
	  * 
	  * @param nuevoCoste Nuevo costo por hora de las terminales.
	  */
	 public void actualizarCosteHoraTerminales(double nuevoCoste) {
	     for (Terminal terminal : terminales.values()) {
	         terminal.setCostePorHora(nuevoCoste);
	     }
	 }

	 /**
	  * Actualiza el costo por hora de todos los fingers en el sistema.
	  * 
	  * @param nuevoCoste Nuevo costo por hora de los fingers.
	  */
	 public void actualizarCosteHoraFingers(double nuevoCoste) {
	     for (Finger finger : fingers.values()) {
	         finger.setCostePorHora(nuevoCoste);
	     }
	 }

	 /**
	  * Actualiza el costo por hora de todos los hangares en el sistema.
	  * 
	  * @param nuevoCoste Nuevo costo por hora de los hangares.
	  */
	 public void actualizarCosteHoraHangares(double nuevoCoste) {
	     for (Hangar hangar : hangares.values()) {
	         hangar.setCostePorHora(nuevoCoste);
	     }
	 }

	 /**
	  * Obtiene el aeropuerto propio del sistema.
	  * 
	  * @return Aeropuerto propio del sistema.
	  */
	 public Aeropuerto getAeropuertoPropio() {
	     return informacionPropia;
	 }

	 /**
	  * Establece el aeropuerto propio del sistema.
	  * 
	  * @param aeropuertoPropio Nuevo aeropuerto propio.
	  */
	 public void setAeropuertoPropio(Aeropuerto aeropuertoPropio) {
	     this.informacionPropia = aeropuertoPropio;
	 }

	
	/**
	 * Devuelve una lista de posibles horas alternativas para la salida o llegada de un vuelo.
	 * La función ajusta la hora del vuelo y verifica disponibilidad de terminales para ofrecer opciones al usuario.
	 *
	 * @param v Vuelo al que se le buscan horas alternativas.
	 * @return Lista de posibles horarios disponibles para la operación del vuelo.
	 */
	public List<LocalDateTime> horasAlternativas(Vuelo v) {
		ArrayList<LocalDateTime> horas = new ArrayList<>();
		int rangoNuevasHorasVuelo = 48;
		LocalDateTime salida = v.getHoraSalida();
		LocalDateTime llegada = v.getHoraLlegada();
		int i = 0;
		
		LocalDateTime alternativaSalida = salida.minusHours(rangoNuevasHorasVuelo);
		LocalDateTime alternativaLlegada = llegada.minusHours(rangoNuevasHorasVuelo);

		while (((rangoNuevasHorasVuelo-i) > 0) && (horas.size()<4)) {
			v.setHoraLlegada(alternativaLlegada.plusHours(i));
			v.setHoraSalida(alternativaSalida.plusHours(i));
			if (this.getTerminalesDisponibles(v).isEmpty() == false) {
				if (v.getLlegada()) {
					horas.add(alternativaLlegada.plusHours(i));
				} else horas.add(alternativaSalida.plusHours(i));
			}
			i += 1;
		}
		
		alternativaSalida = salida.plusHours(rangoNuevasHorasVuelo);
		alternativaLlegada = llegada.plusHours(rangoNuevasHorasVuelo);
		i=0;
		
		while (((rangoNuevasHorasVuelo-i) > 0) && (horas.size()<8)) {
			v.setHoraLlegada(alternativaLlegada.minusHours(i));
			v.setHoraSalida(alternativaSalida.minusHours(i));
			if (this.getTerminalesDisponibles(v).isEmpty() == false) {
				if (v.getLlegada()) {
					horas.add(alternativaLlegada.minusHours(i));
				} else horas.add(alternativaSalida.minusHours(i));
			}
			i += 1;
		}
		
		v.setHoraLlegada(llegada);
		v.setHoraSalida(salida);
		
		return horas;
	}
	
	/**
	 * Devuelve una lista de vuelos seguidos por un usuario.
	 * 
	 * @param u Usuario que sigue los vuelos.
	 * @return Lista de vuelos seguidos por el usuario.
	 */
	public ArrayList<Vuelo> vuelosSeguidos(Usuario u) {
		ArrayList<Vuelo> vuelos = new ArrayList<>();
		
		for (Vuelo v: this.vuelos.values()) {
			for (Usuario o: v.getObservers()) {
				if (u.getDni().equals(o.getDni())) {
					vuelos.add(v);
				}
			}
		}
		
		return vuelos;
	}
	
	/**
	 * Devuelve una lista de aviones seguidos por un usuario.
	 * 
	 * @param u Usuario que sigue los aviones.
	 * @return Lista de aviones seguidos por el usuario.
	 */
	public ArrayList<Avion> avionesSeguidos(Usuario u) {
		ArrayList<Avion> aviones = new ArrayList<>();
		
		for (Aerolinea a: this.aerolineas.values()) {
			for (Avion v: a.getAviones().values()) {
				for (Usuario o: v.getObservers()) {
					if (u.getDni().equals(o.getDni())) {
						aviones.add(v);
					}
				}
			}
		}
		
		return aviones;
	}
	
	/**
	 * Cancela un vuelo, eliminandolo del sistema.
	 * 
	 * @param v Vuelo que se quiere cancelar.
	 */
	public void denegarSolicitudVuelo(Vuelo v) {
		if (vuelos.containsKey(v.getId())) {
			vuelos.remove(v.getId(), v);
		}
	}
	
}
