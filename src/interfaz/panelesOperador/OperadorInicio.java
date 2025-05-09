package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaz.util.MenuLateral;
import sistema.SkyManager;
import usuarios.Usuario;

/**
 * Panel principal de inicio para el operador del sistema SkyManager.
 * Contiene un menú lateral y varios botones para acceder a funcionalidades
 * como búsqueda de vuelos, estadísticas, facturación, gestión de vuelos y aviones.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class OperadorInicio extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton busquedaVuelos;
    private JButton estadisticas;
    private JButton facturas;
    private JButton gestionVuelos;
    private JButton gestionAviones;
    private JLabel bienvenida;

    /**
     * Constructor del panel OperadorInicio. Inicializa la interfaz gráfica
     * con los componentes necesarios y su disposición.
     */
    public OperadorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERAL ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        // === PANEL CENTRAL ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Subpanel central para los botones
        JPanel subPanelCentral = new JPanel();
        subPanelCentral.setLayout(new GridLayout(2, 3, 30, 30));
        subPanelCentral.setBackground(new Color(173, 216, 230));
        subPanelCentral.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // Etiqueta de bienvenida al usuario
        bienvenida = new JLabel();
        bienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        bienvenida.setForeground(new Color(112, 128, 144)); 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(bienvenida, gbc);

        // Creación de botones con sus respectivos iconos
        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/iconoBusquedaVuelos.png");
        estadisticas = createContentButton("Estadísticas");
        setScaledIcon(estadisticas, "resources/iconoEstadisticas.png");
        facturas = createContentButton("Gestionar Facturas");
        setScaledIcon(facturas, "resources/iconoFacturas.png");
        gestionVuelos = createContentButton("Gestionar Vuelos");
        setScaledIcon(gestionVuelos, "resources/iconoGestionVuelos.png");
        gestionAviones = createContentButton("Gestionar Aviones");
        setScaledIcon(gestionAviones, "resources/iconoGestionAviones.png");

        subPanelCentral.add(busquedaVuelos);
        subPanelCentral.add(estadisticas);
        subPanelCentral.add(facturas, gbc);
        subPanelCentral.add(gestionVuelos, gbc);
        subPanelCentral.add(gestionAviones, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        panelContenido.add(subPanelCentral, gbc);

        // Añadir el panel central al layout principal
        add(panelContenido, BorderLayout.CENTER);
    }

    /**
     * Crea un botón estilizado con texto dado.
     * 
     * @param text Texto del botón.
     * @return JButton personalizado.
     */
    private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(112, 128, 144));
		btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        return btn;
    }

    /**
     * Asigna y escala un icono a un botón, adaptando su tamaño dinámicamente.
     * 
     * @param button Botón al que se asignará el icono.
     * @param imagePath Ruta del archivo de imagen.
     */
    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                // Limitar el tamaño del icono
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

    /**
     * Actualiza el mensaje de bienvenida con el nombre del usuario actual.
     */
    public void actualizarPantalla() {
        Usuario usuarioActual = SkyManager.getInstance().getUsuarioActual();
        if(usuarioActual != null) {
            bienvenida.setText("Bienvenid@ " + usuarioActual.getNombre());
        }
    }

    /**
     * Asigna un ActionListener común a todos los botones del panel.
     * 
     * @param c Controlador de eventos de acción.
     */
    public void setControlador(ActionListener c) {  
        busquedaVuelos.addActionListener(c);
        estadisticas.addActionListener(c);
        facturas.addActionListener(c);
        gestionVuelos.addActionListener(c);
        gestionAviones.addActionListener(c);
    }
}
