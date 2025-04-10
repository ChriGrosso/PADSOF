/*package interfaz.panelesControlador;

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
}*/

package interfaz.panelesControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton notificaciones;
    private JButton cerrarSesion;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;

    public ControladorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERALE ===
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(45, 45, 45));

        notificaciones = createMenuButton("🔔 Notificaciones");
        cerrarSesion = createMenuButton("🔓 Cerrar Sesión");
        JButton iconoAvion = new JButton(new ImageIcon("resources/plane_icon.png"));
        iconoAvion.setEnabled(false);
        iconoAvion.setBorderPainted(false);
        iconoAvion.setContentAreaFilled(false);

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(iconoAvion);
        panelMenu.add(Box.createVerticalStrut(30));
        panelMenu.add(notificaciones);
        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(cerrarSesion);

        // === CONTENUTO CENTRALE ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        busquedaVuelos = createContentButton("🔍 Búsqueda Vuelos");
        gestionVuelos = createContentButton("🛬 Gestión Vuelos");

        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx++;
        panelContenido.add(gestionVuelos, gbc);

        // === ASSEMBLA ===
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
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

    public void setControlador(ActionListener c) {
        notificaciones.addActionListener(c);
        cerrarSesion.addActionListener(c);
        busquedaVuelos.addActionListener(c);
        gestionVuelos.addActionListener(c);
    }
}

