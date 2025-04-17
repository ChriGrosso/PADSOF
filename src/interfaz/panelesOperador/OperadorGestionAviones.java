package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import interfaz.elementosComunes.MenuLateral;

public class OperadorGestionAviones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoAvion;
	private JButton nuevoTipoAv;
	
	public OperadorGestionAviones() {
		 setLayout(new BorderLayout());

	        // === MENU LATERAL ===
		 MenuLateral menu = new MenuLateral("resources/logo_icon.png");
	     add(menu, BorderLayout.WEST);
        
        nuevoAvion = new JButton("Nuevo Avión");
        nuevoTipoAv = new JButton("Nuevo Tipo Avión");
        
	}
	
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		nuevoAvion.addActionListener(c);
		nuevoTipoAv.addActionListener(c);;
	}
}
