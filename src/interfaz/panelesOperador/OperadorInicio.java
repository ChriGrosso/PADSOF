package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaz.elementosComunes.MenuLateral;
import sistema.SkyManager;


public class OperadorInicio extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton busquedaVuelos;
    private JButton estadisticas;
    private JButton facturas;
    private JButton gestionVuelos;
    private JButton gestionAviones;
    private JLabel bienvenida;

    public OperadorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERAL ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

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

        // Componentes
        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");
        estadisticas = createContentButton("Estadísticas");
        setScaledIcon(estadisticas, "resources/statistics_icon.png");
        facturas = createContentButton("Ver Facturas");
        setScaledIcon(facturas, "resources/invoice_icon.png");
        gestionVuelos = createContentButton("Gestionar Vuelos");
        setScaledIcon(gestionVuelos, "resources/flight_icon.png");
        gestionAviones = createContentButton("Gestionar Aviones");
        setScaledIcon(gestionAviones, "resources/plane_icon.png");

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
        add(panelContenido, BorderLayout.CENTER);
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
 		busquedaVuelos.addActionListener(c);
 		estadisticas.addActionListener(c);
 		facturas.addActionListener(c);
 		gestionVuelos.addActionListener(c);
 		gestionAviones.addActionListener(c);
 	}
 	
}
