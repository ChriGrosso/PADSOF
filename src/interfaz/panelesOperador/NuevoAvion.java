package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
	private JButton registrarAvion;
	private JTextField cmpMatricula;
	private JTextField cmpMarca;
	private JTextField cmpModelo;
	SpinnerDateModel model = new SpinnerDateModel();
	JSpinner compra = new JSpinner(model);
	JSpinner ultimaRev = new JSpinner(model);
	private JRadioButton mercancias;
	private JRadioButton pasajeros;
	private JRadioButton mercPeligrosas;
	private JRadioButton mercNoPeligrosas;
	ButtonGroup tipoAvion, tipoMerc;
	private JButton botonVolver;
	
	public NuevoAvion() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		// Crear el botón para ir al panel anterior
        botonVolver = new JButton("Volver");
        botonVolver.setPreferredSize(new Dimension(100, 40)); // Tamaño inicial del botón
        botonVolver.setFocusPainted(false);

        // Contenedor en la esquina superior derecha
        JPanel panelSuperiorDerecho = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperiorDerecho.setOpaque(false); // Fondo transparente
        panelSuperiorDerecho.add(botonVolver);

        // Añadir el contenedor al panel principal
        add(panelSuperiorDerecho, BorderLayout.NORTH);
		
		JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        JLabel etiquetaMatricula = new JLabel("Matrícula:");
        JLabel etiquetaMarca = new JLabel("Marca del Avión:");
        JLabel etiquetaModelo = new JLabel("Modelo del Avión:");
        JLabel etiquetaCompra = new JLabel("Fecha de compra:");
        JLabel etiquetaRev = new JLabel("Fecha de última revisión:");
        cmpMatricula = new JTextField(15);
        cmpMarca = new JTextField(15);
        cmpModelo = new JTextField(15);
        // Mostrar solo la fecha, no la hora
        JSpinner.DateEditor editor = new JSpinner.DateEditor(compra, "dd/MM/yyyy");
        compra.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(ultimaRev, "dd/MM/yyyy");
        ultimaRev.setEditor(editor2);
        mercancias = new JRadioButton("Mercancías");
        pasajeros = new JRadioButton("Pasajeros");
        mercPeligrosas = new JRadioButton("Puede llevar mercancías peligrosas");
        mercNoPeligrosas = new JRadioButton("No puede llevar mercancías peligrosas");
        // Fusionar ambas opciones de tipo de avión y de tipo de mercancias
        tipoAvion = new ButtonGroup();
        tipoAvion.add(mercancias);
        tipoAvion.add(pasajeros);
        tipoMerc = new ButtonGroup();
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
        panelContenido.add(etiquetaCompra, gbc);
        
        gbc.gridx++;
        panelContenido.add(compra, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaRev, gbc);
        
        gbc.gridx++;
        panelContenido.add(ultimaRev, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(mercancias, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(pasajeros, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(mercPeligrosas, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(mercNoPeligrosas, gbc);
        
        mercPeligrosas.setVisible(false);
        mercNoPeligrosas.setVisible(false);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(registrarAvion, gbc);
        
        add(panelContenido, BorderLayout.CENTER);
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
	 	registrarAvion.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	 	botonVolver.addActionListener(c);
	 }
	 	
	// Obtener el texto del campo matrícula
    public String getMatricula() {
        return cmpMatricula.getText().trim();
    }

    // Obtener el texto del campo marca
    public String getMarca() {
        return cmpMarca.getText().trim();
    }

    // Obtener el texto del campo modelo
    public String getModelo() {
        return cmpModelo.getText().trim();
    }

    // Obtener la fecha seleccionada en el spinner "compra"
    public LocalDate getFechaCompra() {
    	Date date = (Date) compra.getValue(); // Obtener el valor como Date
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir a LocalDate
    }

    // Obtener la fecha seleccionada en el spinner "última revisión"
    public LocalDate getFechaUltimaRevision() {
    	Date date = (Date) ultimaRev.getValue(); // Obtener el valor como Date
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir a LocalDate
    }

    // Verificar si el avión es de tipo "Mercancías"
    public boolean esMercancias() {
        return mercancias.isSelected();
    }

    // Verificar si el avión es de tipo "Pasajeros"
    public boolean esPasajeros() {
        return pasajeros.isSelected();
    }

    // Verificar si el avión puede transportar mercancías peligrosas
    public boolean esMercPeligrosas() {
        return mercPeligrosas.isSelected();
    }
    
    public JRadioButton getMercPeligrosas() {
    	return mercPeligrosas;
    }

    // Verificar si el avión no puede transportar mercancías peligrosas
    public boolean esMercNoPeligrosas() {
        return mercNoPeligrosas.isSelected();
    }
    
    public JRadioButton getMercNoPeligrosas() {
    	return mercNoPeligrosas;
    }


	public void update() {
		// Reiniciar campos de texto
	    cmpMatricula.setText("");
	    cmpMarca.setText("");
	    cmpModelo.setText("");

	    // Reiniciar spinners a las fechas actuales o iniciales
	    compra.setValue(new Date()); // Fecha actual
	    ultimaRev.setValue(new Date()); // Fecha actual

	    // Desmarcar todos los radio buttons
	    tipoAvion.clearSelection();
	    tipoMerc.clearSelection();

	    // Ocultar los radio buttons relacionados con mercancías peligrosas (si están visibles por algún evento)
	    mercPeligrosas.setVisible(false);
	    mercNoPeligrosas.setVisible(false);
	}
}
