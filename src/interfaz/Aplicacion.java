package interfaz;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import interfaz.panelesControlador.ControlControladorInicio;
import interfaz.panelesControlador.ControladorInicio;
import interfaz.panelesGestor.ControlGestorInicio;
import interfaz.panelesGestor.GestorInicio;
import interfaz.panelesOperador.ControlNuevoAvion;
import interfaz.panelesOperador.ControlNuevoTipoAvion;
import interfaz.panelesOperador.ControlOperadorGestionAviones;
import interfaz.panelesOperador.ControlOperadorInicio;
import interfaz.panelesOperador.NuevoAvion;
import interfaz.panelesOperador.NuevoTipoAvion;
import interfaz.panelesOperador.OperadorGestionAviones;
import interfaz.panelesOperador.OperadorInicio;

public class Aplicacion extends JFrame{
	private static final long serialVersionUID = 1L;

	private static transient Aplicacion INSTANCE = null;
	
	// Inicializar todos los paneles personalizados
	private Login panelLogin;
	private OperadorInicio panelOpInicio;
	private ControladorInicio panelContInicio;
	private GestorInicio panelGestorInicio;
	private OperadorGestionAviones panelOpAviones;
	private NuevoAvion panelNuevoAvion;
	private NuevoTipoAvion panelNuevoTipoAvion;
	
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
	
	final static String OP_GESTION_AVIONES   = "Operador Aviones";
	final static String OP_NUEVO_AVION   = "Nuevo Avion";
	final static String OP_NUEVO_TIPO_AVION   = "Nuevo Tipo Avion";
	final static String OP_GESTION_VUELOS   = "Operador Vuelos";
	final static String OP_FACTURAS   = "Operador Facturas";
	final static String OP_ESTADISTICAS   = "Operador Estadisticas";
	
	final static String CONT_GESTION_VUELOS   = "Controlador Vuelos";
	
	
	private Aplicacion() {
		super("SkyManager");
		
		//vistas
		panelLogin = new Login();
		panelOpInicio = new OperadorInicio();
		panelContInicio = new ControladorInicio(null);
		panelGestorInicio = new GestorInicio();
		panelOpAviones = new OperadorGestionAviones();
		panelNuevoAvion = new NuevoAvion();
		panelNuevoTipoAvion = new NuevoTipoAvion();
		
		//controladores
		ControlLogin controlLogin = new ControlLogin();
		ControlOperadorInicio controlOpInicio = new ControlOperadorInicio();
		ControlControladorInicio controlContInicio = new ControlControladorInicio();
		ControlGestorInicio controlGestorInicio = new ControlGestorInicio();
		ControlOperadorGestionAviones controlOpAviones = new ControlOperadorGestionAviones();
		ControlNuevoAvion controlNuevoAvion = new ControlNuevoAvion();
		ControlNuevoTipoAvion controlNuevoTipoAvion = new ControlNuevoTipoAvion();
		
		// configurar las vistas con los controladores
		panelLogin.setControlador(controlLogin);
		panelOpInicio.setControlador(controlOpInicio);
		panelContInicio.setControlador(controlContInicio);
		panelGestorInicio.setControlador(controlGestorInicio);
		panelOpAviones.setControlador(controlOpAviones);
		panelNuevoAvion.setControlador(controlNuevoAvion);
		panelNuevoTipoAvion.setControlador(controlNuevoTipoAvion);
	
		// Creamos el panel que contiene las cartas 
		cartas = new JPanel(new CardLayout()); 
		cartas.add(panelLogin,LOGIN);
		cartas.add(panelOpInicio,OP_INICIO);
		cartas.add(panelContInicio,CONT_INICIO);
		cartas.add(panelGestorInicio,GESTOR_INICIO);
		cartas.add(panelOpAviones, OP_GESTION_AVIONES);
		cartas.add(panelNuevoAvion, OP_NUEVO_AVION);
		cartas.add(panelNuevoTipoAvion, OP_NUEVO_TIPO_AVION);
		
		// Mostramos una carta (por defecto LOGIN)
		((CardLayout)cartas.getLayout()).show(cartas, LOGIN);
		
		// añadir vistas a la ventana principal
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new CardLayout());
		contenedor.add(cartas);
		
		// configurar tamaño de la ventana principal
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);	
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
	
	public ControladorInicio getContInicio() {
		return this.panelContInicio;
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
	
	public void showContInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, CONT_INICIO);
	}
	
	public void showGestorInicio() {
		((CardLayout)cartas.getLayout()).show(cartas, GESTOR_INICIO);
	}
	
	public void showNotificaciones() {
		((CardLayout)cartas.getLayout()).show(cartas, VER_NOTIFICACIONES);
	}
}


