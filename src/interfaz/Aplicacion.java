package interfaz;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import interfaz.panelesControlador.ControlControladorInicio;
import interfaz.panelesControlador.ControladorGestionVuelos;
import interfaz.panelesControlador.ControladorInicio;
import interfaz.panelesGestor.ConfiguracionNotificaciones;
import interfaz.panelesGestor.ControlConfiguracionNotificaciones;
import interfaz.panelesGestor.ControlGestorGestionAeropuerto;
import interfaz.panelesGestor.ControlGestorGestionFacturas;
import interfaz.panelesGestor.ControlGestorGestionUsuarios;
import interfaz.panelesGestor.ControlGestorInicio;
import interfaz.panelesGestor.ControlNuevoUsuario;
import interfaz.panelesGestor.GestorEstadisticas;
import interfaz.panelesGestor.GestorGestionAeropuerto;
import interfaz.panelesGestor.GestorGestionFacturas;
import interfaz.panelesGestor.GestorGestionUsuarios;
import interfaz.panelesGestor.GestorGestionVuelos;
import interfaz.panelesGestor.GestorInicio;
import interfaz.panelesGestor.NuevoUsuario;
import interfaz.panelesOperador.ControlNuevoAvion;
import interfaz.panelesOperador.ControlNuevoTipoAvion;
import interfaz.panelesOperador.ControlNuevoVuelo;
import interfaz.panelesOperador.ControlOperadorEstadisticas;
import interfaz.panelesOperador.ControlOperadorGestionAviones;
import interfaz.panelesOperador.ControlOperadorGestionVuelos;
import interfaz.panelesOperador.ControlOperadorInicio;
import interfaz.panelesOperador.NuevoAvion;
import interfaz.panelesOperador.NuevoTipoAvion;
import interfaz.panelesOperador.NuevoVuelo;
import interfaz.panelesOperador.OperadorEstadisticas;
import interfaz.panelesOperador.OperadorFacturas;
import interfaz.panelesOperador.OperadorGestionAviones;
import interfaz.panelesOperador.OperadorGestionVuelos;
import interfaz.panelesOperador.OperadorInicio;

/**
 * Clase principal de la aplicación `SkyManager`.
 * Gestiona la navegación entre distintas vistas y configura los controladores de cada panel.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class Aplicacion extends JFrame{
	private static final long serialVersionUID = 1L;

	private static transient Aplicacion INSTANCE = null;
	
	// Inicializar todos los paneles personalizados
	private Login panelLogin;
	private Notificaciones panelNotificaciones;
	private OperadorInicio panelOpInicio;
	private ControladorInicio panelContInicio;
	private GestorInicio panelGestorInicio;
	private OperadorGestionAviones panelOpAviones;
	private NuevoAvion panelNuevoAvion;
	private NuevoTipoAvion panelNuevoTipoAvion;
	private GestorGestionUsuarios panelGestorGestUsers;
	private OperadorGestionVuelos panelOpVuelos;
	private NuevoVuelo panelNuevoVuelo;
	private OperadorFacturas panelOpFacturas;
	private OperadorEstadisticas panelOpEstadisticas;
	private NuevoUsuario panelNuevoUsuario;
	private BusquedaVuelos panelBusquedaVuelos;
	private GestorGestionVuelos panelGestorGestionVuelos;
	private GestorEstadisticas panelGestorEstadisticas;
	private ControladorGestionVuelos panelControladorGestionVuelos;
	private GestorGestionAeropuerto panelGestorGestionAeropuerto;
	private ConfiguracionNotificaciones panelConfiguracionNotificaciones;
	private GestorGestionFacturas panelGestorGestionFacturas;
	
	// Declaramos el panel con las cartas JPanel cartas; 
	private JPanel cartas;

	final static String LOGIN = "Login"; 
	final static String GESTOR_INICIO   = "Gestor Inicio";
	final static String CONT_INICIO   = "Controlador Inicio";
	final static String OP_INICIO   = "Operador Inicio";
	final static String BUSQUEDA_VUELOS   = "Busqueda Vuelos";
	final static String VER_NOTIFICACIONES   = "Ver Notificaciones";
	
	final static String GESTOR_GESTION_USUARIOS   = "Gestor Usuarios";
	final static String GESTOR_GESTION_FACTURAS   = "Gestor Facturas";
	final static String GESTOR_GESTION_AEROPUERTO   = "Gestor Aeropuerto";
	final static String GESTOR_ESTADISTICAS   = "Gestor Estadisticas";
	final static String GESTOR_GESTION_VUELOS   = "Gestor Vuelos";
	final static String GESTOR_NUEVO_USUARIO   = "Nuevo Usuario";
	final static String CONFIGURACION_NOTIFICACIONES   = "Configuracion Notificaciones";
	
	final static String OP_GESTION_AVIONES   = "Operador Aviones";
	final static String OP_NUEVO_AVION   = "Nuevo Avion";
	final static String OP_NUEVO_TIPO_AVION   = "Nuevo Tipo Avion";
	final static String OP_GESTION_VUELOS   = "Operador Vuelos";
	final static String OP_NUEVO_VUELO   = "Nuevo Vuelo";
	final static String OP_FACTURAS   = "Operador Facturas";
	final static String OP_ESTADISTICAS   = "Operador Estadisticas";
	
	final static String CONT_GESTION_VUELOS   = "Controlador Vuelos";
	final static String CONTROLADOR_GESTION_VUELOS = "Controlador Gestion Vuelos";
	
	/**
     * Constructor privado de `Aplicacion`.
     * Inicializa los paneles y controladores de la aplicación.
     */
	private Aplicacion() {
		super("SkyManager");
		
		//vistas
		panelLogin = new Login();
		panelNotificaciones = new Notificaciones();
		panelOpInicio = new OperadorInicio();
		panelContInicio = new ControladorInicio();
		panelGestorInicio = new GestorInicio();
		panelOpAviones = new OperadorGestionAviones();
		panelNuevoAvion = new NuevoAvion();
		panelNuevoTipoAvion = new NuevoTipoAvion();
		panelGestorGestUsers = new GestorGestionUsuarios();
		panelNuevoUsuario = new NuevoUsuario();
		panelOpVuelos = new OperadorGestionVuelos();
		panelNuevoVuelo = new NuevoVuelo();
		panelOpFacturas = new OperadorFacturas();
		panelOpEstadisticas = new OperadorEstadisticas();
		panelBusquedaVuelos = new BusquedaVuelos();
		panelGestorGestionVuelos = new GestorGestionVuelos();
		panelGestorEstadisticas = new GestorEstadisticas();
		panelControladorGestionVuelos = new ControladorGestionVuelos();
		panelGestorGestionAeropuerto = new GestorGestionAeropuerto();
		panelConfiguracionNotificaciones = new ConfiguracionNotificaciones();
		panelGestorGestionFacturas = new GestorGestionFacturas();
		
		//controladores
		ControlLogin controlLogin = new ControlLogin();
		ControlOperadorInicio controlOpInicio = new ControlOperadorInicio();
		ControlControladorInicio controlContInicio = new ControlControladorInicio();
		ControlGestorInicio controlGestorInicio = new ControlGestorInicio();
		ControlOperadorGestionAviones controlOpAviones = new ControlOperadorGestionAviones();
		ControlNuevoAvion controlNuevoAvion = new ControlNuevoAvion();
		ControlNuevoTipoAvion controlNuevoTipoAvion = new ControlNuevoTipoAvion();
		ControlGestorGestionUsuarios controlGestorGestUsers = new ControlGestorGestionUsuarios();
		ControlNuevoUsuario controlNuevoUsuario = new ControlNuevoUsuario();
		ControlOperadorGestionVuelos controlOpVuelos = new ControlOperadorGestionVuelos();
		ControlNuevoVuelo controlNuevoVuelo = new ControlNuevoVuelo();
		ControlOperadorEstadisticas controlOpEst = new ControlOperadorEstadisticas();
		ControlBusquedaVuelos controlBusquedaVuelos = new ControlBusquedaVuelos();
		ControlGestorGestionAeropuerto controlGestorGestAeropuerto = new ControlGestorGestionAeropuerto(panelGestorGestionAeropuerto);
		ControlConfiguracionNotificaciones controlConfiguracionNotificaciones = new ControlConfiguracionNotificaciones();
		ControlGestorGestionFacturas controlGestorGestionFacturas = new ControlGestorGestionFacturas(panelGestorGestionFacturas);	
		
		// configurar las vistas con los controladores
		panelLogin.setControlador(controlLogin);;
		panelOpInicio.setControlador(controlOpInicio);
		panelContInicio.setControlador(controlContInicio);
		panelGestorInicio.setControlador(controlGestorInicio);
		panelOpAviones.setControlador(controlOpAviones);
		panelNuevoAvion.setControlador(controlNuevoAvion);
		panelNuevoTipoAvion.setControlador(controlNuevoTipoAvion);
		panelGestorGestUsers.setControlador(controlGestorGestUsers);
		panelNuevoUsuario.setControlador(controlNuevoUsuario);
		panelOpVuelos.setControlador(controlOpVuelos);
		panelNuevoVuelo.setControlador(controlNuevoVuelo);
		panelOpEstadisticas.setControlador(controlOpEst);
		panelBusquedaVuelos.setControlador(controlBusquedaVuelos);
		panelGestorGestionAeropuerto.setControlador(controlGestorGestAeropuerto);
		panelConfiguracionNotificaciones.setControlador(controlConfiguracionNotificaciones);
		panelGestorGestionFacturas.setControlador(controlGestorGestionFacturas);
		
		// Creamos el panel que contiene las cartas 
		cartas = new JPanel(new CardLayout()); 
		cartas.add(panelLogin,LOGIN);
		cartas.add(panelNotificaciones,VER_NOTIFICACIONES);
		cartas.add(panelOpInicio,OP_INICIO);
		cartas.add(panelContInicio,CONT_INICIO);
		cartas.add(panelGestorInicio,GESTOR_INICIO);
		cartas.add(panelOpAviones, OP_GESTION_AVIONES);
		cartas.add(panelNuevoAvion, OP_NUEVO_AVION);
		cartas.add(panelNuevoTipoAvion, OP_NUEVO_TIPO_AVION);
		cartas.add(panelGestorGestUsers, GESTOR_GESTION_USUARIOS);
		cartas.add(panelNuevoUsuario, GESTOR_NUEVO_USUARIO);
		cartas.add(panelOpVuelos, OP_GESTION_VUELOS);
		cartas.add(panelNuevoVuelo, OP_NUEVO_VUELO);
		cartas.add(panelOpFacturas, OP_FACTURAS);
		cartas.add(panelOpEstadisticas, OP_ESTADISTICAS);
		cartas.add(panelBusquedaVuelos, BUSQUEDA_VUELOS);
		cartas.add(panelGestorGestionVuelos, GESTOR_GESTION_VUELOS);
		cartas.add(panelGestorEstadisticas, GESTOR_ESTADISTICAS);
		cartas.add(panelControladorGestionVuelos, CONTROLADOR_GESTION_VUELOS);
		cartas.add(panelGestorGestionAeropuerto, GESTOR_GESTION_AEROPUERTO);
		cartas.add(panelConfiguracionNotificaciones, CONFIGURACION_NOTIFICACIONES);
		cartas.add(panelGestorGestionFacturas, GESTOR_GESTION_FACTURAS);
				
		// Mostramos una carta (por defecto LOGIN)
		((CardLayout)cartas.getLayout()).show(cartas, LOGIN);
		
		// añadir vistas a la ventana principal
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new CardLayout());
		contenedor.add(cartas);
		
		// configurar tamaño de la ventana principal
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 480);	
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Obtiene la instancia única de la aplicación (`Singleton`).
	 * Si la instancia aún no ha sido creada, la inicializa antes de devolverla.
	 *
	 * @return Instancia única de `Aplicacion`.
	 */
	public static Aplicacion getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new Aplicacion();
        }
        return INSTANCE;
    }
	
	/**
	 * Obtiene el contenedor de los distintos paneles de la aplicación.
	 *
	 * @return JPanel que contiene los paneles de la aplicación.
	 */
	public JPanel getCartas() {
		return this.cartas;
	}
	
	/**
	 * Obtiene el panel de inicio de sesión.
	 *
	 * @return Panel Login de la aplicación.
	 */
	public Login getLogin() {
		return this.panelLogin;
	}
	
	/**
	 * Muestra la pantalla de inicio de sesión.
	 */
	public void showLogin() {
		((CardLayout)cartas.getLayout()).show(cartas, LOGIN);
	}
	
	/**
	 * Obtiene el panel de notificaciones.
	 *
	 * @return Panel Notificaciones de la aplicación.
	 */
	public Notificaciones getNotificaciones() {
		return this.panelNotificaciones;
	}
	
	/**
	 * Obtiene el panel de inicio del operador.
	 *
	 * @return Panel OperadorInicio de la aplicación.
	 */
	public OperadorInicio getOpInicio() {
		return this.panelOpInicio;
	}
	
	/**
	 * Obtiene el panel de gestión de aviones del operador.
	 *
	 * @return Panel `OperadorGestionAviones` de la aplicación.
	 */
	public OperadorGestionAviones getOpAviones() {
		return this.panelOpAviones;
	}
	
	/**
	 * Obtiene el panel para registrar un nuevo avión.
	 *
	 * @return Panel NuevoAvion de la aplicación.
	 */
	public NuevoAvion getNuevoAvion() {
		return this.panelNuevoAvion;
	}
	
	/**
	 * Obtiene el panel para registrar un nuevo tipo de avión.
	 *
	 * @return Panel NuevoTipoAvion de la aplicación.
	 */
	public NuevoTipoAvion getNuevoTipoAvion() {
		return this.panelNuevoTipoAvion;
	}
	
	/**
	 * Obtiene el panel de gestión de vuelos del operador.
	 *
	 * @return Panel OperadorGestionVuelos de la aplicación.
	 */
	public OperadorGestionVuelos getOpVuelos() {
		return this.panelOpVuelos;
	}
	
	/**
	 * Obtiene el panel para registrar un nuevo vuelo.
	 *
	 * @return Panel NuevoVuelo de la aplicación.
	 */
	public NuevoVuelo getNuevoVuelo() {
		return this.panelNuevoVuelo;
	}
	
	/**
	 * Obtiene el panel de gestión de facturas del operador.
	 *
	 * @return Panel OperadorFacturas de la aplicación.
	 */
	public OperadorFacturas getOpFacturas() {
		return this.panelOpFacturas;
	}
	
	/**
	 * Obtiene el panel de estadísticas del operador.
	 *
	 * @return Panel OperadorEstadisticas de la aplicación.
	 */
	public OperadorEstadisticas getOpEstadisticas() {
		return this.panelOpEstadisticas;
	}
	
	/**
	 * Obtiene el panel de inicio del controlador.
	 *
	 * @return Panel ControladorInicio de la aplicación.
	 */
	public ControladorInicio getContInicio() {
		return this.panelContInicio;
	}
	
	/**
	 * Obtiene el panel de inicio del gestor.
	 *
	 * @return Panel GestorInicio de la aplicación.
	 */
	public GestorInicio getGestorInicio() {
		return this.panelGestorInicio;
	}
	
	/**
	 * Obtiene el panel para registrar un nuevo usuario.
	 *
	 * @return Panel NuevoUsuario de la aplicación.
	 */
	public NuevoUsuario getNuevoUsuario() {
		return this.panelNuevoUsuario;
	}
	
	/**
	 * Obtiene el panel de gestión de usuarios del gestor.
	 *
	 * @return Panel GestorGestionUsuarios de la aplicación.
	 */
	public GestorGestionUsuarios getGestorGestionUsuarios() {
		return this.panelGestorGestUsers;
	}
	
	/**
	 * Obtiene el panel de búsqueda de vuelos.
	 *
	 * @return Panel BusquedaVuelos de la aplicación.
	 */
	public BusquedaVuelos getBusquedaVuelos() {
		return this.panelBusquedaVuelos;
	}
	
	/**
	 * Obtiene el panel de gestión de vuelos del gestor.
	 *
	 * @return Panel GestorGestionVuelos de la aplicación.
	 */
	public GestorGestionVuelos getGestorGestionVuelos() {
		return this.panelGestorGestionVuelos;
	}
	
	/**
	 * Obtiene el panel de estadísticas del gestor.
	 *
	 * @return Panel GestorEstadisticas de la aplicación.
	 */
	public GestorEstadisticas getGestorEstadisticas() {
		return this.panelGestorEstadisticas;
	}
	
	/**
	 * Obtiene el panel de gestión de facturas del gestor.
	 *
	 * @return Panel GestorGestionFacturas de la aplicación.
	 */
	public GestorGestionFacturas getGestorGestionFacturas() {
		return this.panelGestorGestionFacturas;
	}	
	
	/**
	 * Obtiene el panel de configuración de notificaciones.
	 *
	 * @return Panel ConfiguracionNotificaciones de la aplicación.
	 */
	public ConfiguracionNotificaciones getConfiguracionNotificaciones() {
		return this.panelConfiguracionNotificaciones;
	}
	
	/**
	 * Muestra la pantalla de inicio del operador.
	 */
	public void showOpInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_INICIO);
	}
	
	/**
	 * Muestra la pantalla de gestión de aviones del operador.
	 */
	public void showOpAviones() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_GESTION_AVIONES);
	}
	
	/**
	 * Muestra la pantalla para registrar un nuevo avión.
	 */
	public void showNuevoAvion() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_AVION);
	}
	
	/**
	 * Muestra la pantalla para registrar un nuevo tipo de avión.
	 */
	public void showNuevoTipoAvion() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_TIPO_AVION);
	}
	
	/**
	 * Muestra la pantalla de gestión de vuelos del operador.
	 */
	public void showOpVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_GESTION_VUELOS);
	}
	
	/**
	 * Muestra la pantalla para registrar un nuevo vuelo.
	 */
	public void showNuevoVuelo() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_VUELO);
	}
	
	/**
	 * Muestra la pantalla de facturas del operador.
	 */
	public void showOpFacturas() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_FACTURAS);
	}
	
	/**
	 * Muestra la pantalla de estadísticas del operador.
	 */
	public void showOpEstadisticas() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_ESTADISTICAS);
	}
	
	/**
	 * Muestra la pantalla de inicio del controlador.
	 */
	public void showContInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, CONT_INICIO);
	}
	
	/**
	 * Muestra la pantalla de inicio del gestor.
	 */
	public void showGestorInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_INICIO);
	}
	
	/**
	 * Muestra la pantalla de notificaciones genérica.
	 */
	public void showNotificaciones() {
		((CardLayout)cartas.getLayout()).show(cartas, VER_NOTIFICACIONES);
	}
	
	/**
	 * Muestra la pantalla de gestión de usuarios del gestor.
	 */
	public void showGestorGestionUsuarios() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_GESTION_USUARIOS);
	}
	
	/**
	 * Muestra la pantalla para registrar un nuevo usuario del gestor.
	 */
	public void showNuevoUsuario() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_NUEVO_USUARIO);
	}
	
	/**
	 * Muestra la pantalla de busqueda de vuelos genérica.
	 */
	public void showBusquedaVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, BUSQUEDA_VUELOS);
	}
	
	/**
	 * Muestra la pantalla de gestión de vuelos del gestor.
	 */
	public void showGestorGestionVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_GESTION_VUELOS);
	}
	
	/**
	 * Muestra la pantalla de estadisticas del gestor.
	 */
	public void showGestorEstadisticas() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_ESTADISTICAS);
	}
	
	/**
	 * Muestra la pantalla de gestión de uelos del controlador.
	 */
	public void showControladorGestionVuelos() {
	    ((CardLayout) cartas.getLayout()).show(cartas, CONTROLADOR_GESTION_VUELOS);
	}
	
	/**
	 * Muestra la pantalla de gestión del aeropuerto del gestor.
	 */
	public void showGestorGestionAeropuerto() {
		((CardLayout) cartas.getLayout()).show(cartas, GESTOR_GESTION_AEROPUERTO);
	}
	
	/**
	 * Muestra la pantalla de gestión de facturas del gestor.
	 */
	public void showGestorGestionFacturas() {
		((CardLayout) cartas.getLayout()).show(cartas, GESTOR_GESTION_FACTURAS);
	}
	
	/**
	 * Muestra la pantalla de configuración de notificaciones del gestor.
	 */
	public void showConfiguracionNotificaciones() {
		((CardLayout) cartas.getLayout()).show(cartas, CONFIGURACION_NOTIFICACIONES);
	}
}


