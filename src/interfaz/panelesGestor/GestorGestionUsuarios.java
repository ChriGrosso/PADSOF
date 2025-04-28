package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import usuarios.Usuario;
import usuarios.Controlador;
import usuarios.Operador;

public class GestorGestionUsuarios extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoUsuario;
	private DefaultTableModel modeloDatos;
	private JTable tabla;
	
	public GestorGestionUsuarios() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		
		JPanel panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		panelSuperiorIzquierdo.setBackground(new Color(173, 216, 230));
		
		// Contenedor en la esquina superior derecha
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> paginaAnterior());

        // Título
	    JLabel titulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    
	    // Añadir el contenedor al panel superior
        panelSuperiorIzquierdo.add(panelAtras, BorderLayout.NORTH);
	    panelSuperiorIzquierdo.add(titulo, BorderLayout.AFTER_LAST_LINE);
	    add(panelSuperiorIzquierdo, BorderLayout.NORTH);
				
		// Crear un array con el título de las columnas
		String[] titulos = {"Nombre", "DNI", "Tipo de Usuario", "Aerolínea/Terminal"};
		modeloDatos = new DefaultTableModel(titulos, 0); 

		for (Usuario u : SkyManager.getInstance().getUsuarios().values()) {
			addFila(u);
		}
		
		tabla = new JTable(modeloDatos);
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
	    add(scroll, BorderLayout.CENTER);
	    
	    // Panel inferior para los botones
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new BorderLayout());
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		  
		//Añadir los botones
	    // Botón "Nuevo Usuario" centrado
		nuevoUsuario = new JButton("Nuevo Usuario");
		this.formatoBotones(nuevoUsuario, 200, 50);
		JPanel panelNuevo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelNuevo.setBackground(new Color(173, 216, 230));
	    panelNuevo.add(nuevoUsuario);
		
	    panelInferior.add(panelNuevo, BorderLayout.CENTER);
		
	    add(panelInferior, BorderLayout.SOUTH);
	}
	
	void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 16));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		nuevoUsuario.setActionCommand("NUEVO_USUARIO");
		
		nuevoUsuario.addActionListener(c);
	}
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showGestorInicio();
	}
	
	public void addFila(Usuario u) {
		String s;
		if (u.esOperador() == true) { 
			s = ((Operador)u).getAerolinea().getId(); 
		} else if(u.esControlador() == true) {
			s = ((Controlador)u).getTerminal().getId(); 
		} else s = "";
		Object[] fila = {u.getNombre(), u.getDni(), u.getClass().getSimpleName(), s};
	    modeloDatos.addRow(fila);
	    if (tabla != null) {
	    	tabla.repaint();
	    }
	}
}
