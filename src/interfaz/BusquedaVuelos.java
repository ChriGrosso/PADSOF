package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import aerolineas.Aerolinea;
import elementos.Terminal;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import vuelos.Vuelo;

/**
 * Clase BusquedaVuelos representa un panel de búsqueda de vuelos en la interfaz de usuario.
 * Permite al usuario realizar búsquedas por distintos criterios y visualizar los resultados en una tabla.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class BusquedaVuelos extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private JComboBox<String> tiposBusqueda;
	private JTextField campoBusqueda;
	private JButton botonBuscar;
	
	/**
     * Constructor de la clase BusquedaVuelos.
     * Configura la interfaz gráfica incluyendo opciones de búsqueda y una tabla de resultados.
     */
	public BusquedaVuelos() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
		
		JPanel panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		panelSuperiorIzquierdo.setBackground(new Color(173, 216, 230));
		
		// Boton atrás en la esquina superior derecha
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> paginaAnterior());
		
		// Título
	    JLabel titulo = new JLabel("Búsqueda de Vuelos", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    
	    // Añadir el contenedor al panel superior
        panelSuperiorIzquierdo.add(panelAtras, BorderLayout.NORTH);
	    panelSuperiorIzquierdo.add(titulo, BorderLayout.AFTER_LAST_LINE);
	    add(panelSuperiorIzquierdo, BorderLayout.NORTH);
	    
	    // Panel con el contenido
	    JPanel panelContenido = new JPanel();
	    panelContenido.setLayout(new BorderLayout());
	    panelContenido.setBackground(new Color(173, 216, 230));
	    
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new GridBagLayout());
        panelGeneral.setBackground(new Color(173, 216, 230));
        panelGeneral.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
	    
	    // JComboBox para selecionar el tipo de búsqueda
	    JLabel labelBusqueda = new JLabel("Buscar por: ");
	    labelBusqueda.setForeground(Color.BLACK);
        panelGeneral.add(labelBusqueda, gbc);
        gbc.gridx = 1;
        // Crear un array con las opciones del combo
        String[] tipos = {"Código", "Origen", "Destino", "Vuelos del Día", "Terminal", "Hora de Llegada", "Hora de Salida"};
        tiposBusqueda = new JComboBox<String>(tipos);
        panelGeneral.add(tiposBusqueda, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        campoBusqueda = new JTextField(15);
        panelGeneral.add(campoBusqueda, gbc);
        gbc.gridx++;
        botonBuscar = new JButton("Buscar");
        formatoBotones(botonBuscar, 50, 30);
        panelGeneral.add(botonBuscar, gbc);
       
		gbc.gridx = 0; gbc.gridy = 2; 
		tabla = new JTable();
		setFilasTabla(new ArrayList<Vuelo>());
		tabla.setBackground(Color.WHITE);
	    tabla.setForeground(Color.BLACK);
	    tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    tabla.setRowHeight(25);
	    tabla.setGridColor(new Color(75, 135, 185));
	    tabla.getTableHeader().setBackground(new Color(70, 130, 180));
	    tabla.getTableHeader().setForeground(Color.WHITE);
	    tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		 
		// barra de scroll para la tabla 
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
	    
		panelContenido.add(panelGeneral, BorderLayout.NORTH);
		panelContenido.add(scroll, BorderLayout.CENTER);
		add(panelContenido, BorderLayout.CENTER);
	}
	
	/**
     * Método para actualizar la tabla de resultados con los vuelos proporcionados.
     *
     * @param vuelos Lista de vuelos a mostrar en la tabla.
     */
	public void setFilasTabla(ArrayList<Vuelo> vuelos) {
		String[] titulos = {"ID Vuelo", "Origen", "Destino", "Estado", "Terminal", "Fecha", "Aerolinea", "ID Avión"};
		DefaultTableModel modeloDatos = new DefaultTableModel(titulos, 0);
		
		if (vuelos == null || vuelos.isEmpty()) {
			tabla.setModel(modeloDatos);
			return;
		}
		
		for (Vuelo v : vuelos) {
			if (v != null) {
				Terminal t = v.getTerminal();
				String terminal = "No asignada";
				if (t != null) {
					terminal = t.getId();
				}
				String fecha = v.getHoraLlegada()+" - "+v.getHoraSalida();
				if (v.getLlegada()) {
					fecha = v.getHoraSalida()+" - "+v.getHoraLlegada();
				}
				ArrayList<Aerolinea> a = v.getAerolineas();
				String aerolineas = a.get(0).getId();
				if (a.size() == 2) { aerolineas += ", "+a.get(1).getId();}
			    Object[] fila = {v.getId(), v.getOrigen().getCodigo(), v.getDestino().getCodigo(), v.getEstVuelo(), terminal, fecha, aerolineas, v.getAvion().getId()};
			    modeloDatos.addRow(fila);
			}
		}
		
		tabla.setModel(modeloDatos);
	}
	
	/**
     * Método para dar formato a los botones de la interfaz.
     *
     * @param boton Botón a formatear.
     * @param ancho Ancho deseado.
     * @param alto Alto deseado.
     */
	private void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
	
	/**
     * Método para regresar a la pantalla anterior.
     * Guarda los datos y muestra la interfaz correspondiente según el tipo de usuario.
     */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		limpiarCampos();
		if (SkyManager.getInstance().getUsuarioActual().esGestor()==true) {
			Aplicacion.getInstance().showGestorInicio();
		} else if (SkyManager.getInstance().getUsuarioActual().esControlador()==true) {
			Aplicacion.getInstance().showContInicio();
		} else {
			Aplicacion.getInstance().showOpInicio();
		}
	}
	
	/**
     * Devuelve el contenido del campo de búsqueda.
     *
     * @return Texto ingresado en el campo de búsqueda.
     */
	public String getContenidoCampoBusqueda() {
	    return campoBusqueda.getText().trim();
	}
	
	/**
     * Devuelve el tipo de búsqueda seleccionado.
     *
     * @return Tipo de búsqueda seleccionado en el combo box.
     */
	public String getTipoBusquedaSeleccionado() {
	    return (String) tiposBusqueda.getSelectedItem();
	}
	
	/**
     * Limpia los campos de búsqueda y la tabla de resultados.
     */
	public void limpiarCampos() {
	    campoBusqueda.setText("");
	    
	    setFilasTabla(new ArrayList<Vuelo>());
	    tiposBusqueda.setSelectedIndex(0);
	    campoBusqueda.grabFocus();
	}
	
	/**
     * Asigna un controlador de eventos a los botones de la interfaz.
     *
     * @param c Controlador de eventos para manejar las acciones de búsqueda.
     */
	public void setControlador(ActionListener c) {  
		botonBuscar.setActionCommand("BUSCAR");
		
		botonBuscar.addActionListener(c);
	}
}
