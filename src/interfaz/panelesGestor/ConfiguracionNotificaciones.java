package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import aviones.Avion;
import aviones.EstadoAvion;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import usuarios.Gestor;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

/**
 * Clase que representa el panel de configuración de notificaciones en el sistema.
 * Permite a los usuarios gestionar transiciones de estado de vuelos y aviones, así como seguir vuelos y aviones específicos.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ConfiguracionNotificaciones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton confirmarA;
	private JButton confirmarV;
	private JButton botonSeguirA;
	private JButton botonSeguirV;
	private JTextField campoA;
	private JTextField campoV;
	private JTable tablaEstadosA;
	private JTable tablaEstadosV;
	private JTable tablaVuelos;
	private JTable tablaAviones;
	private JComboBox<EstadoAvion> comboAvionInicio;
	private JComboBox<EstadoAvion> comboAvionFin;
	private JComboBox<EstadoVuelo> comboVueloInicio;
	private JComboBox<EstadoVuelo> comboVueloFin;
	
	/**
     * Constructor de la clase ConfiguracionNotificaciones.
     * Configura la interfaz gráfica con los elementos necesarios para gestionar las notificaciones.
     */
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
	    panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    GridBagConstraints gbc_cont = new GridBagConstraints();
	    gbc_cont.insets = new Insets(5, 10, 5, 10);
	    gbc_cont.weightx = 1.0;
	    gbc_cont.fill = GridBagConstraints.BOTH;

	    /** PANEL DE VUELOS (IZQUIERDA) **/
	    JPanel panelVueloG = new JPanel(new BorderLayout());
	    panelVueloG.setBackground(new Color(173, 216, 230));
	    
	    JPanel panelVuelo = new JPanel(new GridBagLayout());
	    panelVuelo.setBorder(BorderFactory.createTitledBorder("Transiciones de Vuelo"));
	    panelVuelo.setBackground(new Color(173, 216, 230));
	    GridBagConstraints gbcVuelo = new GridBagConstraints();
	    gbcVuelo.insets = new Insets(5, 10, 5, 10);
	    gbcVuelo.gridx = 0; gbcVuelo.gridy = 0;

	    // Sección de selección de estados
	    panelVuelo.add(new JLabel("Desde:"), gbcVuelo);
	    gbcVuelo.gridx++;
	    comboVueloInicio = new JComboBox<>(EstadoVuelo.values());
	    panelVuelo.add(comboVueloInicio, gbcVuelo);

	    gbcVuelo.gridx++;
	    panelVuelo.add(new JLabel("Hasta:"), gbcVuelo);
	    gbcVuelo.gridx++;
	    comboVueloFin = new JComboBox<>(EstadoVuelo.values());
	    panelVuelo.add(comboVueloFin, gbcVuelo);

	    gbcVuelo.gridx++;
	    confirmarV = new JButton("Seguir transición");
	    this.formatoBotones(confirmarV);
	    panelVuelo.add(confirmarV, gbcVuelo);
	    panelVueloG.add(panelVuelo, BorderLayout.NORTH);

	    // Tabla de transiciones actuales de vuelos
	    tablaEstadosV = new JTable(new DefaultTableModel(new Object[]{"Desde", "Hasta"}, 0));
	    this.formatoTabla(tablaEstadosV);
	    panelVueloG.add(new JScrollPane(tablaEstadosV), BorderLayout.CENTER);

	    // Panel de seguimiento de vuelos
	    JPanel panelSegVuelosG = new JPanel(new BorderLayout());
	    panelSegVuelosG.setBackground(new Color(173, 216, 230));
	    
	    JPanel panelSeguimientoVuelos = new JPanel(new GridBagLayout());
	    panelSeguimientoVuelos.setBorder(BorderFactory.createTitledBorder("Seguimiento de Vuelos"));
	    panelSeguimientoVuelos.setBackground(new Color(173, 216, 230));

	    GridBagConstraints gbcSeguimientoV = new GridBagConstraints();
	    gbcSeguimientoV.insets = new Insets(5, 10, 5, 10);
	    gbcSeguimientoV.gridx = 0; gbcSeguimientoV.gridy = 0;

	    panelSeguimientoVuelos.add(new JLabel("Seguir vuelo:"), gbcSeguimientoV);
	    gbcSeguimientoV.gridx++;
	    campoV = new JTextField(15);
	    panelSeguimientoVuelos.add(campoV, gbcSeguimientoV);

	    gbcSeguimientoV.gridx++;
	    botonSeguirV = new JButton("Seguir");
	    this.formatoBotones(botonSeguirV);
	    panelSeguimientoVuelos.add(botonSeguirV, gbcSeguimientoV);
	    panelSegVuelosG.add(panelSeguimientoVuelos, BorderLayout.NORTH);

	    // Tabla de vuelos seguidos
	    tablaVuelos = new JTable(new DefaultTableModel(new Object[]{"ID Vuelo", "Aerolínea", "Estado"}, 0));
	    this.formatoTabla(tablaVuelos);
	    JScrollPane scrollVuelos = new JScrollPane(tablaVuelos);
	    scrollVuelos.setPreferredSize(null);  // Permitir expansión
	    panelSegVuelosG.add(scrollVuelos, BorderLayout.CENTER);

	    // Agregar los paneles al contenedor principal (Vuelos a la izquierda)
	    gbc_cont.gridx = 0;
	    gbc_cont.gridy = 0;
	    panelContenido.add(panelVueloG, gbc_cont);

	    gbc_cont.gridy = 1;
	    panelContenido.add(panelSegVuelosG, gbc_cont);

	    /** PANEL DE AVIONES (DERECHA) **/
	    JPanel panelAvionG = new JPanel(new BorderLayout());
	    panelAvionG.setBackground(new Color(173, 216, 230));
	    
	    JPanel panelAvion = new JPanel(new GridBagLayout());
	    panelAvion.setBorder(BorderFactory.createTitledBorder("Transiciones de Avión"));
	    panelAvion.setBackground(new Color(173, 216, 230));
	    GridBagConstraints gbcAvion = new GridBagConstraints();
	    gbcAvion.insets = new Insets(5, 10, 5, 10);
	    gbcAvion.gridx = 0; gbcAvion.gridy = 0;

	    // Sección de selección de estados
	    panelAvion.add(new JLabel("Desde:"), gbcAvion);
	    gbcAvion.gridx++;
	    comboAvionInicio = new JComboBox<>(EstadoAvion.values());
	    panelAvion.add(comboAvionInicio, gbcAvion);

	    gbcAvion.gridx++;
	    panelAvion.add(new JLabel("Hasta:"), gbcAvion);
	    gbcAvion.gridx++;
	    comboAvionFin = new JComboBox<>(EstadoAvion.values());
	    panelAvion.add(comboAvionFin, gbcAvion);

	    gbcAvion.gridx++;
	    confirmarA = new JButton("Seguir transición");
	    this.formatoBotones(confirmarA);
	    panelAvion.add(confirmarA, gbcAvion);
	    panelAvionG.add(panelAvion, BorderLayout.NORTH);

	    // Tabla de transiciones actuales de aviones
	    tablaEstadosA = new JTable(new DefaultTableModel(new Object[]{"Desde", "Hasta"}, 0));
	    this.formatoTabla(tablaEstadosA);
	    panelAvionG.add(new JScrollPane(tablaEstadosA), BorderLayout.CENTER);

	    // Panel de seguimiento de aviones
	    JPanel panelSegAvionesG = new JPanel(new BorderLayout());
	    panelSegAvionesG.setBackground(new Color(173, 216, 230));
	    
	    JPanel panelSeguimientoAviones = new JPanel(new GridBagLayout());
	    panelSeguimientoAviones.setBorder(BorderFactory.createTitledBorder("Seguimiento de Aviones"));
	    panelSeguimientoAviones.setBackground(new Color(173, 216, 230));

	    GridBagConstraints gbcSeguimientoA = new GridBagConstraints();
	    gbcSeguimientoA.insets = new Insets(5, 10, 5, 10);
	    gbcSeguimientoA.gridx = 0; gbcSeguimientoA.gridy = 0;

	    panelSeguimientoAviones.add(new JLabel("Seguir avión:"), gbcSeguimientoA);
	    gbcSeguimientoA.gridx++;
	    campoA = new JTextField(15);
	    panelSeguimientoAviones.add(campoA, gbcSeguimientoA);

	    gbcSeguimientoA.gridx++;
	    botonSeguirA = new JButton("Seguir");
	    this.formatoBotones(botonSeguirA);
	    panelSeguimientoAviones.add(botonSeguirA, gbcSeguimientoA);
	    panelSegAvionesG.add(panelSeguimientoAviones, BorderLayout.NORTH);
	    
	    // Tabla de aviones seguidos
	    tablaAviones = new JTable(new DefaultTableModel(new Object[]{"Matrícula", "Tipo", "Estado"}, 0));
	    this.formatoTabla(tablaAviones);
	    JScrollPane scrollAviones = new JScrollPane(tablaAviones);
	    scrollAviones.setPreferredSize(null);  // Permitir expansión
	    gbc_cont.weighty = 0.25;
	    panelSegAvionesG.add(scrollAviones, BorderLayout.CENTER);

	    // Agregar los paneles al contenedor principal (Aviones a la derecha)
	    gbc_cont.gridx = 1;
	    gbc_cont.gridy = 0;
	    panelContenido.add(panelAvionG, gbc_cont);

	    gbc_cont.gridy = 1;
	    panelContenido.add(panelSegAvionesG, gbc_cont);
        
        add(panelContenido, BorderLayout.CENTER);  
	}
	
	/**
     * Regresa a la pantalla anterior y guarda los datos del sistema.
     */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showNotificaciones();
	}
	
	 /**
     * Aplica formato de estilo a los botones.
     * 
     * @param boton Botón a formatear.
     */
	private void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	}
	
	/**
     * Aplica formato de estilo a las tablas de la interfaz.
     * 
     * @param tabla Tabla a formatear.
     */
	private void formatoTabla(JTable tabla) {
		tabla.setBackground(Color.WHITE);
	    tabla.setForeground(Color.BLACK);
	    tabla.setFont(new Font("SansSerif", Font.PLAIN, 10));
	    tabla.setRowHeight(25);
	    tabla.setGridColor(new Color(75, 135, 185));
	    tabla.getTableHeader().setBackground(new Color(70, 130, 180));
	    tabla.getTableHeader().setForeground(Color.WHITE);
	    tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
	}
	
	/**
     * Asigna un controlador de eventos a los botones de la interfaz.
     * 
     * @param c Controlador de eventos para manejar las acciones del usuario.
     */
	public void setControlador(ActionListener c) {  
		confirmarA.setActionCommand("CONFIRMAR_A");
		confirmarV.setActionCommand("CONFIRMAR_V");
		botonSeguirA.setActionCommand("SEGUIR_A");
		botonSeguirV.setActionCommand("SEGUIR_V");
		
		confirmarA.addActionListener(c);
		confirmarV.addActionListener(c);
		botonSeguirA.addActionListener(c);
		botonSeguirV.addActionListener(c);
	}
	
	/**
     * Obtiene el contenido ingresado en el campo de seguimiento de vuelos.
     * 
     * @return Identificador del vuelo que el usuario desea seguir.
     */
	public String getContenidoCampoVuelos() {
	    return campoV.getText().trim();
	}
	
	/**
     * Obtiene el contenido ingresado en el campo de seguimiento de aviones.
     * 
     * @return Matrícula del avión que el usuario desea seguir.
     */
	public String getContenidoCampoAviones() {
	    return campoA.getText().trim();
	}
	
	/**
     * Limpia los campos de búsqueda de vuelos y aviones seguidos.
     */
	public void limpiarCampos() {
	    campoA.setText("");
	    campoV.setText("");
	}
	
	/**
     * Obtiene el estado inicial de la transición de aviones seleccionado en el combo box.
     * 
     * @return Estado de inicio del avión.
     */
	public EstadoAvion getEstadoAvionInicio() {
	    return (EstadoAvion) comboAvionInicio.getSelectedItem();
	}
	
	/**
     * Obtiene el estado final de la transición de aviones seleccionado en el combo box.
     * 
     * @return Estado final del avión.
     */
	public EstadoAvion getEstadoAvionFin() {
	    return (EstadoAvion) comboAvionFin.getSelectedItem();
	}
	
	/**
     * Obtiene el estado inicial de la transición de vuelos seleccionado en el combo box.
     * 
     * @return Estado de inicio del vuelo.
     */
	public EstadoVuelo getEstadoVueloInicio() {
	    return (EstadoVuelo) comboVueloInicio.getSelectedItem();
	}
	
	/**
     * Obtiene el estado final de la transición de vuelos seleccionado en el combo box.
     * 
     * @return Estado final del vuelo.
     */
	public EstadoVuelo getEstadoVueloFin() {
	    return (EstadoVuelo) comboVueloFin.getSelectedItem();
	}
	
	/**
     * Actualiza las filas de las tablas con el contenido almacenado en el sistema.
     */
	public void actualizarTablas() {
		SkyManager sk = SkyManager.getInstance();
		DefaultTableModel modeloTablaEstadosA = new DefaultTableModel(new Object[]{"Desde", "Hasta"}, 0);
		DefaultTableModel modeloTablaEstadosV = new DefaultTableModel(new Object[]{"Desde", "Hasta"}, 0);
		DefaultTableModel modeloTablaVuelos= new DefaultTableModel(new Object[]{"ID Vuelo", "Aerolínea", "Estado"}, 0);
		DefaultTableModel modeloTablaAviones = new DefaultTableModel(new Object[]{"Matrícula", "Tipo", "Estado"}, 0);
		Gestor gestor = (Gestor)sk.getUsuarioActual();
		if (gestor == null) {
			return;
		}
		
		//Tabla estados vuelo
		for (Map.Entry<EstadoVuelo, EstadoVuelo> e: gestor.getEstadosSeguidosVuelo()) {
			modeloTablaEstadosV.addRow(new Object[]{e.getKey(), e.getValue()});
		}
		for (Map.Entry<EstadoAvion, EstadoAvion> e: gestor.getEstadosSeguidosAvion()) {
			modeloTablaEstadosA.addRow(new Object[]{e.getKey(), e.getValue()});
		}
		for (Vuelo v: sk.vuelosSeguidos(gestor)) {
			modeloTablaVuelos.addRow(new Object[]{v.getId(), v.getAerolinea().getId(), v.getEstVuelo()});
		}
		for (Avion v: sk.avionesSeguidos(gestor)) {
			modeloTablaAviones.addRow(new Object[]{v.getMatricula(), v.getTipoAvion().getMarca()+" "+v.getTipoAvion().getModelo(), v.getEstadoAvion()});
		}
		
		this.tablaAviones.setModel(modeloTablaAviones);
		this.tablaEstadosA.setModel(modeloTablaEstadosA);
		this.tablaEstadosV.setModel(modeloTablaEstadosV);
		this.tablaVuelos.setModel(modeloTablaVuelos);
	}
}
