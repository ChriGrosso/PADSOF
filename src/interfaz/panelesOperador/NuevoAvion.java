package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class NuevoAvion extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton registrarAvion;
	private JTextField cmpMatricula;
	private JTextField cmpMarca;
	private JTextField cmpModelo;
	SpinnerDateModel model = new SpinnerDateModel();
	JSpinner compra = new JSpinner(model);
	JSpinner ultimaRev = new JSpinner(model);
	private JRadioButton mercancias;
	private JRadioButton pasajeros;
	private JTextField capacidad;
	private JRadioButton mercPeligrosas;
	private JRadioButton mercNoPeligrosas;
	
	public NuevoAvion() {
		setLayout(new BorderLayout());

        // === MENU LATERAL ===
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(45, 45, 45));

        notificaciones = createMenuButton("🔔 Notificaciones");
        cerrarSesion = createMenuButton("🔓 Cerrar Sesión");

        JButton iconoAvion = new JButton(new ImageIcon("resources/plane_icon.png")); // Asegúrate de tener este recurso
        iconoAvion.setEnabled(false);
        iconoAvion.setBorderPainted(false);
        iconoAvion.setContentAreaFilled(false);

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(iconoAvion);
        panelMenu.add(Box.createVerticalStrut(30));
        panelMenu.add(notificaciones);
        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(cerrarSesion);
        
        // === PANEL CENTRAL (FORMULARIO) ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        JLabel etiquetaMatricula = new JLabel("Matrícula:");
        JLabel etiquetaMarca = new JLabel("Marca del Avión:");
        JLabel etiquetaModelo = new JLabel("Modelo del Avión:");
        JLabel etiquetaCap = new JLabel("Capacidad:");
        cmpMatricula = new JTextField(15);
        cmpMarca = new JTextField(15);
        cmpModelo = new JTextField(15);
        // Mostrar solo la fecha, no la hora
        JSpinner.DateEditor editor = new JSpinner.DateEditor(compra, "dd/MM/yyyy");
        compra.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(ultimaRev, "dd/MM/yyyy");
        ultimaRev.setEditor(editor2);
        capacidad = new JTextField(15);
        mercancias = new JRadioButton("Mercancías");
        pasajeros = new JRadioButton("Pasajeros");
        capacidad = new JTextField(15);
        mercPeligrosas = new JRadioButton("Puede llevar mercancías peligrosas");
        mercNoPeligrosas = new JRadioButton("No puede llevar mercancías peligrosas");
        // Fusionar ambas opciones de tipo de avión y de tipo de mercancias
        ButtonGroup tipoAvion = new ButtonGroup();
        tipoAvion.add(mercancias);
        tipoAvion.add(pasajeros);
        ButtonGroup tipoMerc = new ButtonGroup();
        tipoMerc.add(mercPeligrosas);
        tipoMerc.add(mercNoPeligrosas);
        registrarAvion = createContentButton("Registrar Avión");
        
        // Ubicar los elementos
        panelContenido.add(etiquetaMatricula, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpMatricula, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaMarca, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpMarca, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaModelo, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpModelo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(mercancias, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(pasajeros, gbc);
	}
	
	private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(66, 66, 66));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }
	
	private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 80));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
	 	notificaciones.addActionListener(c);
	 	cerrarSesion.addActionListener(c);
	 	registrarAvion.addActionListener(c);
	 	cmpMatricula.addActionListener(c);
	 	cmpMarca.addActionListener(c);
	 	cmpModelo.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	 }
	 	
}
