package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import aerolineas.Aerolinea;
import elementos.Terminal;
import sistema.SkyManager;

/**
 * Clase que representa la pantalla de creación de un nuevo usuario en el sistema.
 * Permite ingresar los datos necesarios para registrar un operador o un controlador.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class NuevoUsuario extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextField campoNombre;
	private JTextField campoDni;
	private JTextField campoPassword;
	private JLabel labelAerolinea;
	private JLabel labelTerminal;
	private JButton aceptar;
	private JButton cancelar;
	private JRadioButton operador;
	private JRadioButton controlador;
	private JComboBox<String> aerolineas;
	private JComboBox<String> terminales;
	
	/**
     * Constructor de la clase `NuevoUsuario`.
     * Configura la interfaz gráfica con los campos de entrada necesarios para el registro de usuarios.
     */
	public NuevoUsuario() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		
		//panel central con los datos a completar
		JPanel formulario = new JPanel(new GridBagLayout());
		formulario.setBackground(new Color(173, 216, 230));
		formulario.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

        // Crear Componentes
		// nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel labelNombre = new JLabel("Nombre");
        labelNombre.setForeground(Color.BLACK);
        formulario.add(labelNombre, gbc);
        gbc.gridx = 1;
        campoNombre = new JTextField(15);
        formulario.add(campoNombre, gbc);
        //dni
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelDni = new JLabel("DNI");
        labelDni.setForeground(Color.BLACK);
        formulario.add(labelDni, gbc);
        gbc.gridx = 1;
        campoDni = new JTextField(15);
        formulario.add(campoDni, gbc);
        //contraseña
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelPassword = new JLabel("Contraseña");
        labelPassword.setForeground(Color.BLACK);
        formulario.add(labelPassword, gbc);
        gbc.gridx = 1;
        campoPassword = new JTextField(15);
        formulario.add(campoPassword, gbc);
        
        // Tipo
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelTipo = new JLabel("Tipo");
        labelTipo.setForeground(Color.BLACK);
        formulario.add(labelTipo, gbc);
        gbc.gridx = 1;
        operador = new JRadioButton("Operador");
        controlador = new JRadioButton("Controlador");
        operador.setOpaque(false);
        controlador.setOpaque(false);
        operador.setForeground(Color.BLACK);
        controlador.setForeground(Color.BLACK);
        // Crear un grupo para las opciones, para garantizar que solo se seleccione una
        ButtonGroup grupo = new ButtonGroup();
        // Añadir las opciones al grupo
        grupo.add(operador);
        grupo.add(controlador);
        
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.setBackground(new Color(173, 216, 230));
        tipoPanel.add(operador);
        tipoPanel.add(controlador);
        formulario.add(tipoPanel, gbc);
        
        // Aerolinea
        gbc.gridx = 0; gbc.gridy++;
        labelAerolinea = new JLabel("Aerolínea");
        labelAerolinea.setForeground(Color.BLACK);
        formulario.add(labelAerolinea, gbc);
        gbc.gridx = 1;
        aerolineas = new JComboBox<String>();
        // Añadir las opciones del combo aerolineas
        for (Aerolinea a: SkyManager.getInstance().getAerolineas().values()) {
        	aerolineas.addItem(a.getId() + " - " + a.getNombre());
        }
        formulario.add(aerolineas, gbc);
        
        // Terminal
        gbc.gridx = 0; gbc.gridy++;
        labelTerminal = new JLabel("Terminal");
        labelTerminal.setForeground(Color.BLACK);
        formulario.add(labelTerminal, gbc);
        gbc.gridx = 1;
        terminales = new JComboBox<String>();
        // Añadir las opciones del combo terminales
        for (Terminal t: SkyManager.getInstance().getTerminales().values()) {
        	terminales.addItem(t.getId());
        }
        formulario.add(terminales, gbc);
        
        operador.setSelected(true);
        this.mostrarAerolineas();
	    
	    // Panel inferior para los botones
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new GridLayout(1, 2, 20, 0));
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 150, 0, 150));
		  
		//Añadir los botones
	    aceptar = new JButton("OK");
		this.formatoBotones(aceptar);
		panelInferior.add(aceptar);
		
		// Botón "Atrás" a la izquierda
		cancelar = new JButton("Cancelar");
		this.formatoBotones(cancelar);
		panelInferior.add(cancelar);
		

		// Añadir contenedor al centro del layout principal
		add(formulario, BorderLayout.CENTER);
	    add(panelInferior, BorderLayout.SOUTH);
	}
	
	/**
     * Aplica formato de estilo a los botones de la interfaz.
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
     * @param c Controlador de eventos que manejará la acción de los botones.
     */
	public void setControlador(ActionListener c) {
		aceptar.setActionCommand("ACEPTAR");
		cancelar.setActionCommand("CANCELAR");
		operador.setActionCommand("OPERADOR");
		controlador.setActionCommand("CONTROLADOR");
		
		aceptar.addActionListener(c);
		cancelar.addActionListener(c);
		operador.addActionListener(c);
		controlador.addActionListener(c);
	}
	
	/**
     * Obtiene el DNI ingresado en el campo de texto.
     * 
     * @return DNI como cadena de texto.
     */
	public String getDniUsuario() {
	    return campoDni.getText().trim();
	}

	/**
     * Obtiene la contraseña ingresada en el campo de texto.
     * 
     * @return Contraseña como cadena de texto.
     */
	public String getPasswordUsuario() {
	    return campoPassword.getText().trim();
	}
	
	/**
     * Obtiene el nombre ingresado en el campo de texto.
     * 
     * @return Nombre del usuario.
     */
	public String getNombreUsuario() {
	    return campoNombre.getText().trim();
	}
	
	/**
     * Muestra el selector de aerolíneas y oculta el selector de terminales.
     */
	public void mostrarAerolineas() {
		aerolineas.setVisible(true);
        labelAerolinea.setVisible(true);
        terminales.setVisible(false);
        labelTerminal.setVisible(false);
	}
	
	/**
     * Muestra el selector de terminales y oculta el selector de aerolíneas.
     */
	public void mostrarTerminales() {
		aerolineas.setVisible(false);
        labelAerolinea.setVisible(false);
        terminales.setVisible(true);
        labelTerminal.setVisible(true);
	}
	
	/**
	 * Verifica si esta seleccionado operador.
	 * 
	 * @return true si la casilla marcada es operador, false en caso contrario.
	 */
	public boolean esOperadorSeleccionado() {
	    return operador.isSelected();
	}
	
	/**
	 * Verifica si esta seleccionado controlador.
	 * 
	 * @return true si la casilla marcada es controlador, false en caso contrario.
	 */
	public boolean esControladorSeleccionado() {
	    return controlador.isSelected();
	}
	
	/**
	 * Obtiene la aerolínea seleccionada en el combo box.
	 * 
	 * @return Aerolínea seleccionada como una cadena de texto.
	 */
	public String getAerolineaSeleccionada() {
	    return (String) aerolineas.getSelectedItem();
	}

	/**
	 * Obtiene la terminal seleccionada en el combo box.
	 * 
	 * @return Terminal seleccionada como una cadena de texto.
	 */
	public String getTerminalSeleccionada() {
	    return (String) terminales.getSelectedItem();
	}
	
	/**
	 * Restablece los valores de los campos de entrada y los elementos de la interfaz.
	 * Se reinician los textos y las opciones seleccionadas a su estado inicial.
	 */
	public void limpiarCampos() {
	    campoNombre.setText("");
	    campoDni.setText("");
	    campoPassword.setText("");
	    
	    aerolineas.setSelectedIndex(0);
	    terminales.setSelectedIndex(0);
	    
	    campoNombre.grabFocus();
	}
}
