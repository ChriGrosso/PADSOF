package interfaz;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aeropuertos.Aeropuerto;
import interfaz.panelesControlador.ControlControladorGestionVuelos;
import interfaz.panelesControlador.ControlControladorInicio;
import interfaz.panelesControlador.ControladorGestionVuelos;
import interfaz.panelesControlador.ControladorInicio;
import interfaz.panelesGestor.ControlGestorEstadisticas;
import interfaz.panelesGestor.ControlGestorGestionAeropuerto;
import interfaz.panelesGestor.ControlGestorGestionUsuarios;
import interfaz.panelesGestor.ControlGestorGestionVuelos;
import interfaz.panelesGestor.ControlGestorInicio;
import interfaz.panelesGestor.ControlNuevoUsuario;
import interfaz.panelesGestor.GestorEstadisticas;
import interfaz.panelesGestor.GestorGestionAeropuerto;
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
	
	final static String OP_GESTION_AVIONES   = "Operador Aviones";
	final static String OP_NUEVO_AVION   = "Nuevo Avion";
	final static String OP_NUEVO_TIPO_AVION   = "Nuevo Tipo Avion";
	final static String OP_GESTION_VUELOS   = "Operador Vuelos";
	final static String OP_NUEVO_VUELO   = "Nuevo Vuelo";
	final static String OP_FACTURAS   = "Operador Facturas";
	final static String OP_ESTADISTICAS   = "Operador Estadisticas";
	
	final static String CONT_GESTION_VUELOS   = "Controlador Vuelos";
	final static String CONTROLADOR_GESTION_VUELOS = "Controlador Gestion Vuelos";
	
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
		ControlGestorGestionVuelos controlGestorGestionVuelos = new ControlGestorGestionVuelos();
		ControlGestorEstadisticas controlGestorEstadisticas = new ControlGestorEstadisticas();
		ControlControladorGestionVuelos controlControladorGestionVuelos = new ControlControladorGestionVuelos(panelControladorGestionVuelos);
		ControlGestorGestionAeropuerto controlGestorGestAeropuerto = new ControlGestorGestionAeropuerto(panelGestorGestionAeropuerto);

		
		// configurar las vistas con los controladores
		panelLogin.setControlador(controlLogin);
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
		panelGestorGestionVuelos.setControlador(controlGestorGestionVuelos);
		panelGestorEstadisticas.setControlador(controlGestorEstadisticas);
		panelControladorGestionVuelos.setControlador(controlControladorGestionVuelos);
		panelGestorGestionAeropuerto.setControlador(controlGestorGestAeropuerto);
		
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
	
	public static Aplicacion getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new Aplicacion();
        }
        return INSTANCE;
    }
	
	public JPanel getCartas() {
		return this.cartas;
	}
	
	public Login getLogin() {
		return this.panelLogin;
	}
	
	public void showLogin() {
		((CardLayout)cartas.getLayout()).show(cartas, LOGIN);
	}
	
	public Notificaciones getNotificaciones() {
		return this.panelNotificaciones;
	}
	
	public OperadorInicio getOpInicio() {
		return this.panelOpInicio;
	}
	
	public OperadorGestionAviones getOpAviones() {
		return this.panelOpAviones;
	}
	
	public NuevoAvion getNuevoAvion() {
		return this.panelNuevoAvion;
	}
	
	public NuevoTipoAvion getNuevoTipoAvion() {
		return this.panelNuevoTipoAvion;
	}
	
	public OperadorGestionVuelos getOpVuelos() {
		return this.panelOpVuelos;
	}
	
	public NuevoVuelo getNuevoVuelo() {
		return this.panelNuevoVuelo;
	}
	
	public OperadorFacturas getOpFacturas() {
		return this.panelOpFacturas;
	}
	
	public OperadorEstadisticas getOpEstadisticas() {
		return this.panelOpEstadisticas;
	}
	
	public ControladorInicio getContInicio() {
		return this.panelContInicio;
	}
	
	public GestorInicio getGestorInicio() {
		return this.panelGestorInicio;
	}
	
	public NuevoUsuario getNuevoUsuario() {
		return this.panelNuevoUsuario;
	}
	
	public GestorGestionUsuarios getGestorGestionUsuarios() {
		return this.panelGestorGestUsers;
	}
	
	public BusquedaVuelos getBusquedaVuelos() {
		return this.panelBusquedaVuelos;
	}
	
	public GestorGestionVuelos getGestorGestionVuelos() {
		return this.panelGestorGestionVuelos;
	}
	
	public GestorEstadisticas getGestorEstadisticas() {
		return this.panelGestorEstadisticas;
	}
	
	public void showOpInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_INICIO);
	}
	
	public void showOpAviones() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_GESTION_AVIONES);
	}
	
	public void showNuevoAvion() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_AVION);
	}
	
	public void showNuevoTipoAvion() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_TIPO_AVION);
	}
	
	public void showOpVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_GESTION_VUELOS);
	}
	
	public void showNuevoVuelo() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_NUEVO_VUELO);
	}
	
	public void showOpFacturas() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_FACTURAS);
	}
	
	public void showOpEstadisticas() {
		((CardLayout)cartas.getLayout()).show(cartas, OP_ESTADISTICAS);
	}
	
	public void showContInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, CONT_INICIO);
	}
	
	public void showGestorInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_INICIO);
	}
	
	public void showNotificaciones() {
		((CardLayout)cartas.getLayout()).show(cartas, VER_NOTIFICACIONES);
	}
	
	public void showGestorGestionUsuarios() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_GESTION_USUARIOS);
	}
	
	public void showNuevoUsuario() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_NUEVO_USUARIO);
	}
	
	public void showBusquedaVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, BUSQUEDA_VUELOS);
	}
	
	public void showGestorGestionVuelos() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_GESTION_VUELOS);
	}
	
	public void showGestorEstadisticas() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_ESTADISTICAS);
	}
	
	public void showControladorGestionVuelos() {
	    ((CardLayout) cartas.getLayout()).show(cartas, CONTROLADOR_GESTION_VUELOS);
	}
	
	public void showGestorGestionAeropuerto() {
		((CardLayout) cartas.getLayout()).show(cartas, GESTOR_GESTION_AEROPUERTO);
	}
}


