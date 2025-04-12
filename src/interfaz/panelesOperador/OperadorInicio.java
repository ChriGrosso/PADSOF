package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sistema.SkyManager;


/*public class OperadorInicio extends JPanel{
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

}*/




public class OperadorInicio extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton notificaciones;
    private JButton cerrarSesion;
    private JButton busquedaVuelos;
    private JButton estadisticas;
    private JButton facturas;
    private JButton gestionVuelos;
    private JButton gestionAviones;
    private JLabel bienvenida;

    public OperadorInicio() {
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

        // === PANEL CENTRAL ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Bienvenida (si hay usuario)
        bienvenida = new JLabel();
        bienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(bienvenida, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        busquedaVuelos = createContentButton("🔍 Buscar Vuelos");
        estadisticas = createContentButton("📊 Estadísticas");
        facturas = createContentButton("📁 Ver Facturas");
        gestionVuelos = createContentButton("🛬 Gestionar Vuelos");
        gestionAviones = createContentButton("✈️ Gestionar Aviones");

        gbc.gridx = 0;
        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx = 1;
        panelContenido.add(estadisticas, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(facturas, gbc);

        gbc.gridx = 1;
        panelContenido.add(gestionVuelos, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelContenido.add(gestionAviones, gbc);

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
    
    public void actualizarBienvenida() {
        var usuario = SkyManager.getInstance().getUsuarioActual();
        if (usuario != null) {
            bienvenida.setText("Bienvenid@ " + usuario.getNombre());
        } else {
            bienvenida.setText("Bienvenid@");
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        actualizarBienvenida();
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
