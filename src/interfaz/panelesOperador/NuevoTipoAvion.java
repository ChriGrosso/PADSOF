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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;

/**
 * Clase que representa el panel de registro de un nuevo tipo de avión.
 * Permite ingresar la información del modelo y registrarlo en el sistema.
 * 
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class NuevoTipoAvion extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton registrarTipoAvion;
	private JTextField cmpMarca, cmpModelo, cmpAutonomia, cmpAltura, cmpAnchura, cmpLargo, capacidadM, capacidadP;
	private JRadioButton mercancias, pasajeros, mercPeligrosas, mercNoPeligrosas;
	ButtonGroup tipoAvion, tipoMerc;
	
	/**
     * Constructor de la clase NuevoTipoAvion.
     * Configura la interfaz gráfica con los elementos necesarios para el registro del modelo de avión.
     */
	public NuevoTipoAvion() {
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
        panelContenido.setBackground(new Color(173, 216, 230));
        panelContenido.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        JLabel etiquetaCap = new JLabel("Capacidad (Mercancía en [Tn], Pasajeros en [nº personas]):");
        etiquetaCap.setForeground(Color.BLACK);
        JLabel etiquetaMarca = new JLabel("Marca:");
        etiquetaMarca.setForeground(Color.BLACK);
        JLabel etiquetaModelo = new JLabel("Modelo:");
        etiquetaModelo.setForeground(Color.BLACK);
        JLabel etiquetaAutonomia = new JLabel("Máxima autonomía [km]:");
        etiquetaAutonomia.setForeground(Color.BLACK);
        JLabel etiquetaAnchura = new JLabel("Anchura del avión [m]:");
        etiquetaAnchura.setForeground(Color.BLACK);
        JLabel etiquetaAltura = new JLabel("Altura del avión [m]:");
        etiquetaAltura.setForeground(Color.BLACK);
        JLabel etiquetaLargo = new JLabel("Largo del avión [m]:");
        etiquetaLargo.setForeground(Color.BLACK);
        capacidadM = new JTextField(15);
        capacidadP = new JTextField(15);
        cmpMarca = new JTextField(15);
        cmpModelo = new JTextField(15);
        cmpAutonomia = new JTextField(15);
        cmpAltura = new JTextField(15);
        cmpAnchura = new JTextField(15);
        cmpLargo = new JTextField(15);
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
        registrarTipoAvion = createContentButton("Registrar Tipo de Avión");
        
        // Ubicar los elementos
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
        panelContenido.add(etiquetaAutonomia, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpAutonomia, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaAnchura, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpAnchura, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaAltura, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpAltura, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaLargo, gbc);
        
        gbc.gridx++;
        panelContenido.add(cmpLargo, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(mercancias, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(pasajeros, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaCap, gbc);
        
        gbc.gridx++;
        panelContenido.add(capacidadM, gbc);
        panelContenido.add(capacidadP, gbc);
        
        capacidadM.setVisible(false);
        capacidadP.setVisible(false);
        
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
        formatoBotones(registrarTipoAvion);
        panelInferior.add(registrarTipoAvion, gbc);
        
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
	 * Crea y configura un botón de la interfaz con formato estándar.
	 * 
	 * @param text Texto que aparecerá en el botón.
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
     * Aplica formato de estilo a los botones.
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
		registrarTipoAvion.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	}

	/**
	 * Obtiene la capacidad de carga en toneladas del avión de mercancías.
	 * 
	 * @return Capacidad de carga en toneladas. Retorna -1 si el valor no es válido.
	 */
    public Double getCapacidadM() {
    	try {
            return Double.parseDouble(capacidadM.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    /**
     * Obtiene el campo de entrada de capacidad de carga de mercancías.
     * 
     * @return Campo de texto para ingresar la capacidad de carga de mercancías.
     */
    public JTextField getCapacidadMTextField() {
    	return capacidadM;
    }
    
    /**
     * Obtiene la capacidad de pasajeros del avión.
     * 
     * @return Número de pasajeros. Retorna -1 si el valor no es válido.
     */
    public Integer getCapacidadP() {
    	try {
            return Integer.parseInt(capacidadP.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // Retorna -1 si el texto no es un número válido
        }
    }
    
    /**
     * Obtiene el campo de entrada de capacidad de pasajeros.
     * 
     * @return Campo de texto para ingresar la capacidad de pasajeros.
     */
    public JTextField getCapacidadPTextField() {
    	return capacidadP;
    }

    /**
     * Obtiene la marca del modelo de avión.
     * 
     * @return Marca del avión como una cadena de texto.
     */
    public String getMarca() {
        return cmpMarca.getText().trim();
    }

    /**
     * Obtiene el modelo del avión.
     * 
     * @return Modelo del avión como una cadena de texto.
     */
    public String getModelo() {
        return cmpModelo.getText().trim();
    }
    
    /**
     * Obtiene la autonomía máxima del avión en kilómetros.
     * 
     * @return Autonomía del avión. Retorna -1 si el valor no es válido.
     */
    public Double getAutonomia() {
    	try {
            return Double.parseDouble(cmpAutonomia.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    /**
     * Obtiene la anchura del avión en metros.
     * 
     * @return Anchura del avión. Retorna -1 si el valor no es válido.
     */
    public Double getAnchura() {
    	try {
            return Double.parseDouble(cmpAnchura.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    /**
     * Obtiene la altura del avión en metros.
     * 
     * @return Altura del avión. Retorna -1 si el valor no es válido.
     */
    public Double getAltura() {
    	try {
            return Double.parseDouble(cmpAltura.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    /**
     * Obtiene el largo del avión en metros.
     * 
     * @return Largo del avión. Retorna -1 si el valor no es válido.
     */
    public Double getLargo() {
    	try {
            return Double.parseDouble(cmpLargo.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
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
     * Verifica si el avión puede transportar mercancías peligrosas.
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
     * @return JRadioButton que permite la selección de mercancías no peligrosas.
     */
    public JRadioButton getMercNoPeligrosas() {
        return mercNoPeligrosas;
    }

    /**
     * Restablece los valores de los campos de entrada y los elementos de la interfaz.
     * Se reinician los textos, capacidades y opciones de selección.
     */
	public void update() {
		// Reiniciar campos de texto
	    capacidadM.setText("");
	    capacidadP.setText("");
	    cmpMarca.setText("");
	    cmpModelo.setText("");
	    cmpAutonomia.setText("");
	    cmpAnchura.setText("");
	    cmpAltura.setText("");
	    cmpLargo.setText("");

	    // Desmarcar todos los radio buttons
	    tipoAvion.clearSelection();
	    tipoMerc.clearSelection();

	    // Ocultar los radio buttons relacionados con mercancías peligrosas (si están visibles por algún evento)
	    // Hacer lo mismo con los campos de texto de las capacidades
	    mercPeligrosas.setVisible(false);
	    mercNoPeligrosas.setVisible(false);
	    capacidadM.setVisible(false);
        capacidadP.setVisible(false);
	}
}
