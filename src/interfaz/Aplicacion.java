package interfaz;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Aplicacion extends JFrame{
	// Inicializar todos los paneles personalizados
	private Login panelLogin;
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
	final static String OP_GESTION_VUELOS   = "Operador Vuelos";
	final static String OP_FACTURAS   = "Operador Facturas";
	final static String OP_ESTADISTICAS   = "Operador Estadisticas";
	
	final static String CONT_GESTION_VUELOS   = "Controlador Vuelos";
	
	
	public Aplicacion() {
		super("SkyManager");
		
		//vistas
		panelLogin = new Login();
		
		//controladores
		ControlLogin controlLogin = new ControlLogin(this);
		
		// configurar las vistas con los controladores
		panelLogin.setControlador(controlLogin);
		
		
		// Creamos e inizializamos cada una de las cartas 
		Login carta1 = new Login();
		
		// Creamos el panel que contiene las cartas 
		cartas = new JPanel(new CardLayout()); 
		cartas.add(carta1,LOGIN);
		
		// Mostramos una carta 
		((CardLayout)cartas.getLayout()).show(cartas, LOGIN);
		
		// añadir vistas a la ventana principal
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new CardLayout());
		contenedor.add(cartas);
	}
}


