package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import aviones.EstadoAvion;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import vuelos.EstadoVuelo;

public class ConfiguracionNotificaciones extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<JCheckBox> casillasAvion = new ArrayList<>();
	private ArrayList<JCheckBox> casillasVuelos = new ArrayList<>();
	
	public ConfiguracionNotificaciones() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
		
		JPanel panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		panelSuperiorIzquierdo.setBackground(new Color(173, 216, 230));
		
		// Contenedor en la esquina superior derecha
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> paginaAnterior());

        // Título
	    JLabel titulo = new JLabel("Configuración Notificaciones", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    
	    // Añadir el contenedor al panel superior
        panelSuperiorIzquierdo.add(panelAtras, BorderLayout.NORTH);
	    panelSuperiorIzquierdo.add(titulo, BorderLayout.AFTER_LAST_LINE);
	    add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		
		
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.setBackground(new Color(173, 216, 230));

        JPanel avionesCheckbox = new JPanel(new GridBagLayout());
        avionesCheckbox.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 columnas
		gbc.anchor = GridBagConstraints.CENTER;
        avionesCheckbox.add(new JLabel("Estados de Aviones que se desean seguir:"));
        // Crear las opciones
        for (EstadoAvion v: Arrays.asList(EstadoAvion.EN_FINGER, EstadoAvion.EN_PARKING, EstadoAvion.ESPERANDO_PISTA, EstadoAvion.EN_PISTA, EstadoAvion.EN_HANGAR, EstadoAvion.FUERA_AEROPUERTO)) {
        	JCheckBox casilla = new JCheckBox(v.toString());
        	casillasAvion.add(casilla);
        	gbc.gridy++;
        	gbc.anchor = GridBagConstraints.WEST;
        	gbc.gridwidth = 1;
        	avionesCheckbox.add(casilla);
        	if (gbc.gridy == 3) { gbc.gridx++; gbc.gridy = 0;}
        }
        
        JPanel vuelosCheckbox = new JPanel(new GridBagLayout());
        vuelosCheckbox.setBackground(new Color(173, 216, 230));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Ocupa 4 columnas
		gbc.anchor = GridBagConstraints.CENTER;
        vuelosCheckbox.add(new JLabel("Estados de Vuelos que se desean seguir:"));
        // Crear las opciones
        for (EstadoVuelo v: Arrays.asList(EstadoVuelo.EN_VUELO, EstadoVuelo.ESPERANDO_PISTA_A, EstadoVuelo.ESPERANDO_PISTA_D, EstadoVuelo.ESPERANDO_ATERRIZAJE, EstadoVuelo.ESPERANDO_DESPEGUE, 
        		EstadoVuelo.ATERRIZADO, EstadoVuelo.EMBARQUE, EstadoVuelo.DESEMBARQUE_INI, EstadoVuelo.DESEMBARQUE_FIN, EstadoVuelo.CARGA, EstadoVuelo.DESCARGA_INI, 
        		EstadoVuelo.DESCARGA_FIN, EstadoVuelo.EN_TIEMPO, EstadoVuelo.RETRASADO, EstadoVuelo.OPERATIVO, EstadoVuelo.EN_HANGAR)) {
        	JCheckBox casilla = new JCheckBox(v.toString());
        	casillasVuelos.add(casilla);
        	gbc.gridy++;
        	gbc.anchor = GridBagConstraints.WEST;
        	gbc.gridwidth = 1;
        	vuelosCheckbox.add(casilla);
        	if (gbc.gridy%4 == 0) { gbc.gridx++; gbc.gridy = 0;}
        }
       
        panelContenido.add(vuelosCheckbox, BorderLayout.WEST);
        panelContenido.add(avionesCheckbox, BorderLayout.EAST);
        add(panelContenido, BorderLayout.CENTER);
        
        
        
        
	}
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showNotificaciones();
	}
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		//botonConfiguracion.setActionCommand("CONFIGURAR");
		
		//botonConfiguracion.addActionListener(c);
	}

}
