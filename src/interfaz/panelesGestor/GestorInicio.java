package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class GestorInicio extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton busquedaVuelos;
	private JButton gestionVuelos;
	private JButton gestionAeropuerto;
	private JButton gestionFacturas;
	private JButton gestionUsuarios;
	private JButton estadisticas;
	
	public GestorInicio() {
		// Usar GridBagLayout para centrar componentes
		setLayout(new BorderLayout());
		
		
		// Panel lateral izquierdo
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new GridLayout(3, 1, 80, 80));
        panelLateral.setBackground(new Color(30, 30, 30));
        panelLateral.setPreferredSize(new Dimension(150, getHeight()));
        panelLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY)); // línea divisoria
        panelLateral.setBorder(BorderFactory.createEmptyBorder(80, 15, 40, 15));

        // Botones laterales
        notificaciones = new JButton();
        setScaledIcon(notificaciones, "resources/notification_icon.png");
        notificaciones.setBackground(Color.DARK_GRAY);
        notificaciones.setForeground(Color.WHITE);
        notificaciones.setPreferredSize(new Dimension(100, 130));
        cerrarSesion = new JButton("<html><center>Cerrar<br>Sesión</center></html>");
        formatoBotones(cerrarSesion);
        cerrarSesion.setPreferredSize(new Dimension(40, 130));
        JButton btnAvion = new JButton("Sky Manager");
        setScaledIcon(btnAvion, "resources/plane_icon.png");
        btnAvion.setBackground(Color.RED);
        btnAvion.setForeground(Color.BLACK);
        btnAvion.setPreferredSize(new Dimension(100, 130));

        panelLateral.add(notificaciones);
        panelLateral.add(cerrarSesion);
        panelLateral.add(btnAvion);

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(2, 3, 30, 30));
        panelCentral.setBackground(Color.BLACK);
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
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
   }
		
		
	void formatoBotones(JButton boton) {
		boton.setFont(new Font("Arial", Font.BOLD, 18));
		boton.setBackground(Color.DARK_GRAY);
		boton.setForeground(Color.WHITE);
	}

	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		notificaciones.addActionListener(c);
		cerrarSesion.addActionListener(c);
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
                	iconWidth = Math.min(btnWidth, btnWidth / 4);
                    iconHeight = Math.min(iconHeight, btnHeight / 4);
                }
                if (iconWidth != iconHeight) {
                	iconHeight = Math.min(iconHeight, btnWidth);
                	iconWidth = Math.min(iconHeight, btnWidth);
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
