package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;

/**
 * Clase que representa el panel de registro de un nuevo avión en la aerolínea.
 * Permite ingresar la información del avión y registrarlo en el sistema.
 *
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class NuevoAvion extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton registrarAvion;
	private JTextField cmpMatricula, cmpMarca, cmpModelo;
	SpinnerDateModel model = new SpinnerDateModel();
	SpinnerDateModel model2 = new SpinnerDateModel();
	JSpinner compra = new JSpinner(model);
	JSpinner ultimaRev = new JSpinner(model2);
	private JRadioButton mercancias, pasajeros, mercPeligrosas, mercNoPeligrosas;
	ButtonGroup tipoAvion, tipoMerc;
	
	/**
     * Constructor de la clase NuevoAvion.
     * Configura la interfaz gráfica con los elementos necesarios para el registro del avión.
     */
	public NuevoAvion() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        // Contenedor en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

        // Añadir el contenedor al panel principal
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		
		JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));
        panelContenido.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        JLabel etiquetaMatricula = new JLabel("Matrícula:");
        etiquetaMatricula.setForeground(Color.BLACK);
        JLabel etiquetaMarca = new JLabel("Marca del Avión:");
        etiquetaMarca.setForeground(Color.BLACK);
        JLabel etiquetaModelo = new JLabel("Modelo del Avión:");
        etiquetaModelo.setForeground(Color.BLACK);
        JLabel etiquetaCompra = new JLabel("Fecha de compra:");
        etiquetaCompra.setForeground(Color.BLACK);
        JLabel etiquetaRev = new JLabel("Fecha de última revisión:");
        etiquetaRev.setForeground(Color.BLACK);
        cmpMatricula = new JTextField(15);
        cmpMarca = new JTextField(15);
        cmpModelo = new JTextField(15);
        // Mostrar solo la fecha, no la hora
        JSpinner.DateEditor editor = new JSpinner.DateEditor(compra, "dd/MM/yyyy");
        compra.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(ultimaRev, "dd/MM/yyyy");
        ultimaRev.setEditor(editor2);
        mercancias = new JRadioButton("Mercancías");
        mercancias.setBackground(new Color(173, 216, 230));
        pasajeros = new JRadioButton("Pasajeros");
        pasajeros.setBackground(new Color(173, 216, 230));
        mercPeligrosas = new JRadioButton("Lleva mercancías peligrosas");
        mercPeligrosas.setBackground(new Color(173, 216, 230));
        mercNoPeligrosas = new JRadioButton("No lleva mercancías peligrosas");
        mercNoPeligrosas.setBackground(new Color(173, 216, 230));
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
        
        add(panelContenido, BorderLayout.CENTER);
        
        // Panel inferior para los botones
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new GridLayout(1, 2, 20, 0));
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 150, 0, 150));
		  
		//Añadir el botón de registro
        formatoBotones(registrarAvion);
        panelInferior.add(registrarAvion, gbc);
        
        add(panelInferior, BorderLayout.SOUTH);
	}
	
	/**
     * Regresa a la pantalla anterior y guarda los datos del sistema.
     */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getNuevoAvion().setVisible(false);
		Aplicacion.getInstance().showOpAviones();
	}

	/**
     * Crea y configura un botón de contenido.
     * 
     * @param text Texto del botón.
     * @return Botón configurado.
     */
	private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 48));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
	
	/**
     * Aplica el formato de estilo a los botones.
     * 
     * @param boton Botón a formatear.
     */
	void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
	
	/**
	 * Asigna un controlador de eventos a los botones de la interfaz.
	 *
	 * @param c Controlador de eventos que manejará las acciones del usuario.
	 */
	public void setControlador(ActionListener c) {  
	 	registrarAvion.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	 }
	 	
	/**
	 * Obtiene el texto ingresado en el campo de matrícula.
	 *
	 * @return Matrícula ingresada como una cadena de texto.
	 */
    public String getMatricula() {
        return cmpMatricula.getText().trim();
    }

    /**
     * Obtiene el texto ingresado en el campo de marca del avión.
     *
     * @return Marca ingresada como una cadena de texto.
     */
    public String getMarca() {
        return cmpMarca.getText().trim();
    }

    /**
     * Obtiene el texto ingresado en el campo de modelo del avión.
     *
     * @return Modelo ingresado como una cadena de texto.
     */
    public String getModelo() {
        return cmpModelo.getText().trim();
    }

    /**
     * Obtiene la fecha de compra del avión seleccionada en el spinner correspondiente.
     *
     * @return Fecha de compra como un objeto LocalDate.
     */
    public LocalDate getFechaCompra() {
    	Date date = (Date) compra.getValue(); // Obtener el valor como Date
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir a LocalDate
    }

    /**
     * Obtiene la fecha de última revisión del avión seleccionada en el spinner correspondiente.
     *
     * @return Fecha de última revisión como un objeto LocalDate.
     */
    public LocalDate getFechaUltimaRevision() {
    	Date date = (Date) ultimaRev.getValue(); // Obtener el valor como Date
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir a LocalDate
    }

    /**
     * Verifica si el avión es de tipo mercancías.
     *
     * @return true si el avión es de mercancías, false en caso contrario.
     */
    public boolean esMercancias() {
        return mercancias.isSelected();
    }

    /**
     * Verifica si el avión es de tipo pasajeros.
     *
     * @return true si el avión es de pasajeros, false en caso contrario.
     */
    public boolean esPasajeros() {
        return pasajeros.isSelected();
    }

    /**
     * Verifica si el avión está configurado para transportar mercancías peligrosas.
     *
     * @return true si el avión transporta mercancías peligrosas, false en caso contrario.
     */
    public boolean esMercPeligrosas() {
        return mercPeligrosas.isSelected();
    }
    
    /**
     * Obtiene el botón de selección de mercancías peligrosas.
     *
     * @return JRadioButton que permite la selección de mercancías peligrosas.
     */
    public JRadioButton getMercPeligrosas() {
    	return mercPeligrosas;
    }

    /**
     * Verifica si el avión no transporta mercancías peligrosas.
     *
     * @return true si el avión no transporta mercancías peligrosas, false en caso contrario.
     */
    public boolean esMercNoPeligrosas() {
        return mercNoPeligrosas.isSelected();
    }
    
    /**
     * Obtiene el botón de selección de mercancías no peligrosas.
     *
     * @return `JRadioButton` que permite la selección de mercancías no peligrosas.
     */
    public JRadioButton getMercNoPeligrosas() {
    	return mercNoPeligrosas;
    }

    /**
     * Restablece los valores de los campos de entrada y los elementos de la interfaz.
     * Se reinician los textos, fechas y opciones de selección.
     */
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
