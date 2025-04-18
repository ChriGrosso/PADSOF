package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;

public class NuevoTipoAvion extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton registrarTipoAvion;
	private JTextField cmpMarca, cmpModelo, cmpAutonomia, cmpAltura, cmpAnchura, cmpLargo, capacidadM, capacidadP;
	private JRadioButton mercancias, pasajeros, mercPeligrosas, mercNoPeligrosas;
	ButtonGroup tipoAvion, tipoMerc;
	
	public NuevoTipoAvion() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

        // Contenedor en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
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
        
        // Crear Componentes
        JLabel etiquetaCap = new JLabel("Capacidad (Mercancía en [Tn], Pasajeros en [nº personas]):");
        JLabel etiquetaMarca = new JLabel("Marca:");
        JLabel etiquetaModelo = new JLabel("Modelo:");
        JLabel etiquetaAutonomia = new JLabel("Máxima autonomía [km]:");
        JLabel etiquetaAnchura = new JLabel("Anchura del avión [m]:");
        JLabel etiquetaAltura = new JLabel("Altura del avión [m]:");
        JLabel etiquetaLargo = new JLabel("Largo del avión [m]:");
        capacidadM = new JTextField(15);
        capacidadP = new JTextField(15);
        cmpMarca = new JTextField(15);
        cmpModelo = new JTextField(15);
        cmpAutonomia = new JTextField(15);
        cmpAltura = new JTextField(15);
        cmpAnchura = new JTextField(15);
        cmpLargo = new JTextField(15);
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
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(registrarTipoAvion, gbc);
        
        add(panelContenido, BorderLayout.CENTER);
	}
	
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getNuevoAvion().setVisible(false);
		Aplicacion.getInstance().showOpAviones();
	}
	

	private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 48));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
	
	
	public void setControlador(ActionListener c) {
		registrarTipoAvion.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	}

	// Obtener el dato (int o double) del campo capcidad
    public Double getCapacidadM() {
    	try {
            return Double.parseDouble(capacidadM.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    public JTextField getCapacidadMTextField() {
    	return capacidadM;
    }
    public Integer getCapacidadP() {
    	try {
            return Integer.parseInt(capacidadP.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // Retorna -1 si el texto no es un número válido
        }
    }
    public JTextField getCapacidadPTextField() {
    	return capacidadP;
    }

    // Obtener el texto del campo marca
    public String getMarca() {
        return cmpMarca.getText().trim();
    }

    // Obtener el texto del campo modelo
    public String getModelo() {
        return cmpModelo.getText().trim();
    }
    
    // Obtener el dato del campo autonomia
    public Double getAutonomia() {
    	try {
            return Double.parseDouble(cmpAutonomia.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    // Obtener el dato del campo anchura
    public Double getAnchura() {
    	try {
            return Double.parseDouble(cmpAnchura.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    // Obtener el dato del campo altura
    public Double getAltura() {
    	try {
            return Double.parseDouble(cmpAltura.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    
    // Obtener el dato del campo largo
    public Double getLargo() {
    	try {
            return Double.parseDouble(cmpLargo.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
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
