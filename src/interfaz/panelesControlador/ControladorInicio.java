package interfaz.panelesControlador;

import javax.swing.*;

import sistema.SkyManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class ControladorInicio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton busquedaVuelos;
	private JButton gestionVuelos;
	
	public ControladorInicio() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel mensajeBienvenida = null;
        // Crear Componentes
        if(this.isShowing()) {
        	mensajeBienvenida = new JLabel("Bienvenid@ " + SkyManager.getInstance().getUsuarioActual().getNombre());
        }
        notificaciones = new JButton("Notificaciones");
        cerrarSesion = new JButton("Cerrar Sesión");
        busquedaVuelos = new JButton("Buscar Vuelos");
        gestionVuelos = new JButton("Gestionar Vuelos");
        
     // === Columna izquierda: Notificaciones y Cerrar Sesión ===
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(notificaciones, gbc);

        gbc.gridy++;
        this.add(cerrarSesion, gbc);

        // === Centro: Mensaje de bienvenida ===
        if(this.isShowing()) {
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridwidth = 2;
	        gbc.anchor = GridBagConstraints.CENTER;
			this.add(mensajeBienvenida, gbc);
        }

        // === Botones distribuidos centralmente en el espacio derecho ===
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(busquedaVuelos, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(gestionVuelos, gbc);
	}

	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		notificaciones.addActionListener(c);
		cerrarSesion.addActionListener(c);
		busquedaVuelos.addActionListener(c);
		gestionVuelos.addActionListener(c);
	}
}
