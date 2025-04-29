package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import aerolineas.Aerolinea;
import aviones.Avion;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;

public class OperadorGestionAviones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoAvion, nuevoTipoAv;
	private JTable tablaAviones;
	
	public OperadorGestionAviones() {
		// Configurar el Layout
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        
        // Contenedor en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

        // Añadir el contenedor al panel principal
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);
        
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Título
        JLabel titulo = new JLabel("Gestión de Aviones");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Ocupa 3 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(titulo, gbc);

        // Tabla
        tablaAviones = new JTable(); // La tabla se actualizará dinámicamente con los datos
        tablaAviones.setBackground(Color.WHITE);
        tablaAviones.setForeground(Color.BLACK);
        tablaAviones.setGridColor(new Color(75, 135, 185));
        tablaAviones.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaAviones.getTableHeader().setForeground(Color.WHITE);
        tablaAviones.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaAviones.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablaAviones.setRowHeight(25); // Ajustar altura de las filas
        
        JScrollPane scroll = new JScrollPane(tablaAviones);
        scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
	    add(scroll, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todo el ancho
        gbc.fill = GridBagConstraints.BOTH; // Expandir horizontal y verticalmente
        gbc.weightx = 1.0; // Permitir expansión horizontal
        gbc.weighty = 1.0; // Permitir expansión vertical
        panelContenido.add(scroll, gbc);

        // Botones debajo de la tabla
        gbc.gridwidth = 1; // Cada botón ocupa una columna
        gbc.fill = GridBagConstraints.NONE; // Tamaño por defecto
        gbc.weightx = 0; // No expandir botones horizontalmente
        gbc.weighty = 0; // No expandir botones verticalmente

        nuevoAvion = new JButton("Nuevo Avión");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formatoBotones(nuevoAvion, 200, 50);
        panelContenido.add(nuevoAvion, gbc);

        nuevoTipoAv = new JButton("Nuevo Tipo Avión");
        gbc.gridx = 1;
        gbc.gridy = 2;
        formatoBotones(nuevoTipoAv, 200, 50);
        panelContenido.add(nuevoTipoAv, gbc);
         
        add(panelContenido, BorderLayout.CENTER);
	}
	
	
	public void actualizarPantalla() {
		// Colocar los nombres de las columnas de los aviones
		String [] columnas = {"Matrícula", "Fecha de Compra", "Última Revisión", "Modelo", "Estado"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Avion> aviones = a.getAviones().values();
		String [][] datos = new String[aviones.size()][5];
		int i = 0;
		// Rellenar la matriz con los datos de los aviones
		for (Avion avion: aviones) {
		    datos[i][0] = avion.getMatricula();         
		    datos[i][1] = String.valueOf(avion.getFechaCompra()); 
		    if(avion.getFechaUltimaRevision() == null) {
		    	datos[i][2] = "No registrado";
		    }
		    else {
		    	datos[i][2] = String.valueOf(avion.getFechaUltimaRevision()); 
		    } 
		    datos[i][3] = avion.getTipoAvion().getMarca() + " " + avion.getTipoAvion().getModelo();
		    datos[i][4] = String.valueOf(avion.getEstadoAvion());     
		    i++;
		}
		// Actualizar contenido de la tabla con nuevos datos
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        tablaAviones.setModel(model);
        model.fireTableDataChanged();
	}
	
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}
	
	private void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(5, 10, 20)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 16));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		nuevoAvion.addActionListener(c);
		nuevoTipoAv.addActionListener(c);
	}
}
