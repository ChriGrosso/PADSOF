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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private JButton confirmarA;
	private JButton confirmarV;
	private JButton botonSeguirA;
	private JButton botonSeguirV;
	private JTextField campoA;
	private JTextField campoV;
	
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
		
		
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));

        JPanel avionesCheckbox = new JPanel(new GridBagLayout());
        avionesCheckbox.setBorder(BorderFactory.createEmptyBorder(10, 20,20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 columnas
		gbc.anchor = GridBagConstraints.WEST;
        avionesCheckbox.add(new JLabel("Estados de Aviones que se desean seguir:"), gbc);
		gbc.anchor = GridBagConstraints.CENTER;
        // Crear las opciones
        for (EstadoAvion v: Arrays.asList(EstadoAvion.EN_FINGER, EstadoAvion.EN_PARKING, EstadoAvion.ESPERANDO_PISTA, EstadoAvion.EN_PISTA, EstadoAvion.EN_HANGAR, EstadoAvion.FUERA_AEROPUERTO)) {
        	JCheckBox casilla = new JCheckBox(v.toString());
        	casillasAvion.add(casilla);
        	gbc.gridy+=1;
        	gbc.anchor = GridBagConstraints.WEST;
        	gbc.gridwidth = 1;
        	avionesCheckbox.add(casilla, gbc);
        	if (gbc.gridy == 3) { gbc.gridx+=1; gbc.gridy = 0;}
        }
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        confirmarA = new JButton("Confirmar");
        this.formatoBotones(confirmarA);
        avionesCheckbox.add(confirmarA, gbc);
        
        JPanel vuelosCheckbox = new JPanel(new GridBagLayout());
        vuelosCheckbox.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Ocupa 4 columnas
        gbc.anchor = GridBagConstraints.WEST;
        vuelosCheckbox.add(new JLabel("Estados de Vuelos que se desean seguir:"), gbc);
		gbc.anchor = GridBagConstraints.CENTER;
        // Crear las opciones
        for (EstadoVuelo v: Arrays.asList(EstadoVuelo.EN_VUELO, EstadoVuelo.ESPERANDO_PISTA_A, EstadoVuelo.ESPERANDO_PISTA_D, EstadoVuelo.ESPERANDO_ATERRIZAJE, EstadoVuelo.ESPERANDO_DESPEGUE, 
        		EstadoVuelo.ATERRIZADO, EstadoVuelo.EMBARQUE, EstadoVuelo.DESEMBARQUE_INI, EstadoVuelo.DESEMBARQUE_FIN, EstadoVuelo.CARGA, EstadoVuelo.DESCARGA_INI, 
        		EstadoVuelo.DESCARGA_FIN, EstadoVuelo.EN_TIEMPO, EstadoVuelo.RETRASADO, EstadoVuelo.OPERATIVO, EstadoVuelo.EN_HANGAR)) {
        	JCheckBox casilla = new JCheckBox(v.toString());
        	casillasVuelos.add(casilla);
        	gbc.gridy+= 1;
        	gbc.anchor = GridBagConstraints.WEST;
        	gbc.gridwidth = 1;
        	vuelosCheckbox.add(casilla, gbc);
        	if (gbc.gridy%5 == 0) { gbc.gridx+=1; gbc.gridy = 0;}
        }
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        confirmarV = new JButton("Confirmar");
        this.formatoBotones(confirmarV);
        vuelosCheckbox.add(confirmarV, gbc);
        
        JPanel panel_seguimiento = new JPanel(new GridBagLayout());
        panel_seguimiento.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.CENTER;
		JLabel labelVuelos = new JLabel("Seguir vuelo:");
		panel_seguimiento.add(labelVuelos, gbc);
		gbc.gridx +=1;
		campoV = new JTextField(15);
		panel_seguimiento.add(campoV, gbc);
		gbc.gridx +=1;
		botonSeguirV = new JButton("Seguir");
		this.formatoBotones(botonSeguirV);
		panel_seguimiento.add(botonSeguirV, gbc);
		
        gbc.gridx = 0;
        gbc.gridy = 1;
		JLabel labelAviones = new JLabel("Seguir avión:");
		panel_seguimiento.add(labelAviones, gbc);
		gbc.gridx +=1;
		campoA = new JTextField(15);
		panel_seguimiento.add(campoA, gbc);
		gbc.gridx +=1;
		botonSeguirA = new JButton("Seguir");
		this.formatoBotones(botonSeguirA);
		panel_seguimiento.add(botonSeguirA, gbc);
		
		GridBagConstraints gbc_cont = new GridBagConstraints();
		gbc_cont.insets = new Insets(5, 10, 5, 10);
		gbc_cont.gridx = 0;
		gbc_cont.gridy = 0;
		gbc_cont.weightx = 1.0; // Permitir expansión en el eje X
		gbc_cont.fill = GridBagConstraints.BOTH; // Expandirse en ambas direcciones

		// Panel de aviones
		panelContenido.add(avionesCheckbox, gbc_cont);

		// Panel de vuelos
		gbc_cont.gridx = 1; // Ubicarlo en la segunda columna
		panelContenido.add(vuelosCheckbox, gbc_cont);

		// Panel inferior (ocupando el ancho total)
		gbc_cont.gridx = 0;
		gbc_cont.gridy = 1;
		gbc_cont.gridwidth = 2; // Ocupa el ancho total de los dos paneles superiores
		panelContenido.add(panel_seguimiento, gbc_cont);
        add(panelContenido, BorderLayout.CENTER);  
	}
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showNotificaciones();
	}
	
	private void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	}
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		confirmarA.setActionCommand("CONFIRMAR_A");
		confirmarV.setActionCommand("CONFIRMAR_V");
		botonSeguirA.setActionCommand("SEGUIR_V");
		botonSeguirV.setActionCommand("SEGUIR_V");
		
		confirmarA.addActionListener(c);
		confirmarV.addActionListener(c);
		botonSeguirA.addActionListener(c);
		botonSeguirV.addActionListener(c);
	}
	
	// método que devuelve el contenido del campoV que dice que vuelo se quiere seguir.
	public String getContenidoCampoVuelos() {
	    return campoV.getText().trim();
	}
	
	// método que devuelve el contenido del campoA que dice que avión se quiere seguir.
	public String getContenidoCampoAviones() {
	    return campoA.getText().trim();
	}
	
	public void actualizarPantalla() {
		
		
		campoA.setText("");
	    campoV.setText("");
	}
	
	// método que actualiza el valor de los campos
	public void limpiarCampos() {
	    campoA.setText("");
	    campoV.setText("");
	}

}
