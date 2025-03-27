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
	
	private long rangoTiempoMinutosMostrarTerminalesAvion;
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
	
	/**
     * Constructor privado para aplicar el patrón Singleton.
     * Carga los datos desde un archivo si existe.
     */
	private SkyManager() {
		File fichero = new File("resources\\skyManagerDatos.dat");
		if (fichero.exists()) {
			 this.cargarDatos();
		} else {
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
	        this.costeBaseLlegada = refDisco.costeExtraMercancias;
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
	        this.usuarioActual = null;
	        
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
		if (this.vuelos.containsKey(v.getId())) {
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
     * Busca vuelos que tengan una hora de llegada específica.
     * 
     * @param hLlegada Hora de llegada de los vuelos a buscar.
     * @return Una lista de vuelos que coinciden con la hora de llegada indicada.
     */
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
	
	/**
     * Busca vuelos que tengan una hora de salida específica.
     * 
     * @param hSalida Hora de salida de los vuelos a buscar.
     * @return Una lista de vuelos que coinciden con la hora de salida indicada.
     */
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
	 
	
}
