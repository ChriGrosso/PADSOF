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
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import vuelos.Vuelo;

public class BusquedaVuelos extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private JComboBox<String> tiposBusqueda;
	private JTextField campoBusqueda;
	private JButton botonBuscar;
	
	public BusquedaVuelos() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		
		// Boton atrás en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

        // Añadir el contenedor al panel principal
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		
		// Título
	    JLabel titulo = new JLabel("Búsqueda de Vuelos", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    add(titulo, BorderLayout.NORTH);
	    
	    // Panel con el contenido
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new GridBagLayout());
        panelGeneral.setBackground(new Color(173, 216, 230));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
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
	    
		add(panelGeneral, BorderLayout.CENTER);
		add(scroll, BorderLayout.AFTER_LAST_LINE);
	}
	
	public void setFilasTabla(ArrayList<Vuelo> vuelos) {
		String[] titulos = {"ID Vuelo", "Origen", "Destino", "Estado", "Terminal", "Fecha", "Aerolinea", "ID Avión"};
		DefaultTableModel modeloDatos = new DefaultTableModel(titulos, 0);
		
		for (Vuelo v : vuelos) {
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
		
		tabla.setModel(modeloDatos);
	}
	
	private void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 12));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showGestorInicio();
	}
	
	// método que devuelve el continido que buscar (contenido del campoBusqueda JTextField)
	public String getContenidoCampoBusqueda() {
	    return campoBusqueda.getText().trim();
	}
	
	// Obtener el metodo de busqueda seleccionado del combo
	public String getTipoBusquedaSeleccionado() {
	    return (String) tiposBusqueda.getSelectedItem();
	}
	
	// método que actualiza el valor de los campos
	public void limpiarCampos() {
	    campoBusqueda.setText("");
	    
	    setFilasTabla(null);
	    tiposBusqueda.setSelectedIndex(0);
	    campoBusqueda.grabFocus();
	}
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		botonBuscar.setActionCommand("BUSCAR");
		
		botonBuscar.addActionListener(c);
	}
}
