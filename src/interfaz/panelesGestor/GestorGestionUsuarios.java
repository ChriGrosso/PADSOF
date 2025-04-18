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

import sistema.SkyManager;
import usuarios.Usuario;

public class GestorGestionUsuarios extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoUsuario;
	private JButton atras;
	private DefaultTableModel modeloDatos;
	private JTable tabla;
	
	public GestorGestionUsuarios() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		
		// Título
	    JLabel titulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
	    titulo.setForeground(Color.WHITE);
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    add(titulo, BorderLayout.NORTH);
				
		// Crear un array con el título de las columnas
		String[] titulos = {"Nombre", "DNI", "Tipo de Usuario"};
		modeloDatos = new DefaultTableModel(titulos, 0); 

		for (Usuario u : SkyManager.getInstance().getUsuarios().values()) {
		    Object[] fila = {u.getNombre(), u.getDni(), u.getClass().getSimpleName()};
		    modeloDatos.addRow(fila);
		}
		
		tabla = new JTable(modeloDatos);
		tabla.setBackground(Color.DARK_GRAY);
	    tabla.setForeground(Color.WHITE);
	    tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    tabla.setRowHeight(25);
	    tabla.setGridColor(Color.GRAY);
	    tabla.getTableHeader().setBackground(Color.GRAY);
	    tabla.getTableHeader().setForeground(Color.BLACK);
	    tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		 
		// barra de scroll para la tabla 
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    add(scroll, BorderLayout.CENTER);
	    
	    // Panel inferior para los botones
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new BorderLayout());
	    panelInferior.setBackground(Color.BLACK);
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		  
		//Añadir los botones
	    // Botón "Nuevo Usuario" centrado
		nuevoUsuario = new JButton("Nuevo Usuario");
		this.formatoBotones(nuevoUsuario, 200, 50);
		JPanel panelNuevo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelNuevo.setBackground(Color.BLACK);
	    panelNuevo.add(nuevoUsuario);
		
		// Botón "Atrás" a la izquierda
		atras = new JButton("Atrás");
		this.formatoBotones(atras, 160, 50);
		JPanel panelAtras = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    panelAtras.setBackground(Color.BLACK);
	    panelAtras.add(atras);
		
	    // Agregar subpaneles al panel inferior
	    panelInferior.add(panelAtras, BorderLayout.WEST);
	    panelInferior.add(panelNuevo, BorderLayout.CENTER);
		
	    add(panelInferior, BorderLayout.SOUTH);
	}
	
	void formatoBotones(JButton boton,  int ancho, int alto) {
		//boton.setFont(new Font("Arial", Font.BOLD, (int)boton.getWidth()/5));
		//boton.setBackground(Color.DARK_GRAY);
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(255, 140, 0)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 16));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		nuevoUsuario.setActionCommand("NUEVO_USUARIO");
		atras.setActionCommand("ATRAS");
		
		nuevoUsuario.addActionListener(c);
		atras.addActionListener(c);
	}
	
	void addFila(Object[] nuevaFila) {
		modeloDatos.addRow(nuevaFila);
	}
	
	void removeFila(int index) {
		modeloDatos.removeRow(index);
	}

}
