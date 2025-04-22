package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaz.elementosComunes.MenuLateral;

public class GestorInicio extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton busquedaVuelos;
	private JButton gestionVuelos;
	private JButton gestionAeropuerto;
	private JButton gestionFacturas;
	private JButton gestionUsuarios;
	private JButton estadisticas;
	
	public GestorInicio() {
		setLayout(new BorderLayout());
		
		// === MENU LATERAL ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(2, 3, 30, 30));
        panelCentral.setBackground(new Color(173, 216, 230));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // Botones principales
        gestionUsuarios = new JButton("Gestión Usuarios");
        formatoBotones(gestionUsuarios);
        setScaledIcon(gestionUsuarios, "resources/iconoGestionUsuarios.png");
        panelCentral.add(gestionUsuarios);
        
        gestionFacturas = new JButton("Gestión Facturas");
        formatoBotones(gestionFacturas);
        setScaledIcon(gestionFacturas, "resources/iconoFacturas.png");
        panelCentral.add(gestionFacturas);
        
        gestionAeropuerto = new JButton("Gestión Aeropuerto");
        formatoBotones(gestionAeropuerto);
        setScaledIcon(gestionAeropuerto, "resources/iconoGestionAeropuerto.png");
        panelCentral.add(gestionAeropuerto);
        
        busquedaVuelos = new JButton("Búsqueda Vuelos");
        formatoBotones(busquedaVuelos);
        setScaledIcon(busquedaVuelos, "resources/iconoBusquedaVuelos.png");
        panelCentral.add(busquedaVuelos);

        estadisticas = new JButton("Estadísticas");
        formatoBotones(estadisticas);
        setScaledIcon(estadisticas, "resources/iconoEstadisticas.png");
        panelCentral.add(estadisticas);
        
        gestionVuelos = new JButton("Gestión Vuelos");
        formatoBotones(gestionVuelos);
        setScaledIcon(gestionVuelos, "resources/iconoGestionVuelos.png");
        panelCentral.add(gestionVuelos);

        // Agregar los paneles
        add(menu, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
   }
		
		
	void formatoBotones(JButton boton) {
		boton.setFont(new Font("Arial", Font.BOLD, 18));
		boton.setBackground(new Color(112, 128, 144));
		boton.setForeground(Color.WHITE);
	}

	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {
		busquedaVuelos.setActionCommand("BUSQUEDA_VUELOS");
		gestionVuelos.setActionCommand("GESTION_VUELOS");
		gestionAeropuerto.setActionCommand("GESTION_AEROPUERTO");
		gestionFacturas.setActionCommand("GESTION_FACTURAS");
		gestionUsuarios.setActionCommand("GESTION_USUARIOS");
		estadisticas.setActionCommand("ESTADISTICAS");
		
		busquedaVuelos.addActionListener(c);
		gestionVuelos.addActionListener(c);
		gestionAeropuerto.addActionListener(c);
		gestionFacturas.addActionListener(c);
		gestionUsuarios.addActionListener(c);
		estadisticas.addActionListener(c);
	}
	
	public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                // Limitar el tamaño del icono (por ejemplo, máximo 64x64)
                int iconWidth = Math.min(192, btnWidth / 2);
                int iconHeight = Math.min(192, btnHeight / 2);
                if (imagePath.equals("resources/notification_icon.png")) {
                	iconWidth = Math.min(btnWidth, btnWidth / 3);
                    iconHeight = Math.min(iconHeight, btnHeight / 3);
                }
                if ((iconWidth != iconHeight) && !imagePath.equals("resources/iconoSkyManagerInicio.png")) {
                	iconHeight = Math.min(iconHeight, btnWidth);
                	iconWidth = Math.min(iconHeight, btnWidth);
                }
                if (imagePath.equals("resources/iconoSkyManagerInicio.png")) {
                	iconWidth = btnWidth;
                    iconHeight = btnHeight;
                }
                

                // Escalar manteniendo proporciones
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        iconWidth, iconHeight, Image.SCALE_SMOOTH
                );

                button.setIcon(new ImageIcon(scaledImage));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        });
    }

}
