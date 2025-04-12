package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sistema.SkyManager;


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

        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");
        estadisticas = createContentButton("Estadísticas");
        setScaledIcon(estadisticas, "resources/statistics_icon.png");
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
    
    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                // Limitar el tamaño del icono (por ejemplo, máximo 64x64)
                int iconWidth = Math.min(64, btnWidth / 5);
                int iconHeight = Math.min(64, btnHeight / 4);

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
