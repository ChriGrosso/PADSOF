package interfaz2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sistema.SkyManager;


public class OperadorInicio extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton busquedaVuelos;
	private JButton estadisticas;
	private JButton facturas;
	private JButton gestionVuelos;
	private JButton gestionAviones;
	
	public OperadorInicio() {
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
        estadisticas = new JButton("Estadisticas sobre la Aerolinea");
        facturas = new JButton("Ver Facturas");
        gestionVuelos = new JButton("Gestionar Vuelos");
        gestionAviones = new JButton("Gestionar Aviones");
        
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

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(estadisticas, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(facturas, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(gestionVuelos, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        this.add(gestionAviones, gbc);
	}

	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		notificaciones.addActionListener(c);
		cerrarSesion.addActionListener(c);
		busquedaVuelos.addActionListener(c);
		estadisticas.addActionListener(c);
		facturas.addActionListener(c);
		gestionVuelos.addActionListener(c);
		gestionAviones.addActionListener(c);
	}

}
