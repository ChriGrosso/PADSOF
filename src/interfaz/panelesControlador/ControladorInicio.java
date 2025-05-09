package interfaz.panelesControlador;

import sistema.SkyManager;
import usuarios.Usuario;

import javax.swing.*;
import interfaz.util.MenuLateral;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Clase ControladorInicio - Representa la pantalla principal para el controlador de vuelo.
 * 
 * Muestra un mensaje de bienvenida y dos botones centrales para acceder a las funciones
 * de búsqueda y gestión de vuelos. Incluye además un menú lateral con opciones fijas.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;
    private JLabel bienvenida;

    /**
     * Constructor que inicializa la interfaz del panel de inicio del controlador.
     * Configura el diseño del panel, el menú lateral, el mensaje de bienvenida
     * y los botones de navegación principales.
     */
    public ControladorInicio() {
        setLayout(new BorderLayout());

        // === MENÚ LATERAL ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        // === PANEL CENTRAL ===
        JPanel panelGeneral = new JPanel(new GridBagLayout());
        panelGeneral.setBackground(new Color(173, 216, 230));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // === MENSAJE DE BIENVENIDA ===
        bienvenida = new JLabel();
        bienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        bienvenida.setForeground(new Color(112, 128, 144));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelGeneral.add(bienvenida, gbc);

        // === BOTONES PRINCIPALES ===
        gbc.gridwidth = 1;
        gbc.gridy++;

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 30, 30));
        panelBotones.setBackground(new Color(173, 216, 230));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        busquedaVuelos = new JButton("Buscar Vuelos");
        formatoBotones(busquedaVuelos);
        setScaledIcon(busquedaVuelos, "resources/iconoBusquedaVuelos.png");
        panelBotones.add(busquedaVuelos);

        gestionVuelos = new JButton("Gestión Vuelos");
        formatoBotones(gestionVuelos);
        setScaledIcon(gestionVuelos, "resources/iconoGestionVuelos.png");
        panelBotones.add(gestionVuelos);

        gbc.gridwidth = 2;
        panelGeneral.add(panelBotones, gbc);

        add(panelGeneral, BorderLayout.CENTER);
    }

    /**
     * Escala dinámicamente el icono del botón en función del tamaño del botón.
     *
     * @param button Botón al que se asignará el icono.
     * @param imagePath Ruta del archivo de imagen a utilizar como icono.
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
                if ((iconWidth != iconHeight)) {
                    iconHeight = Math.min(iconHeight, btnWidth);
                    iconWidth = Math.min(iconHeight, btnWidth);
                }

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
     * Aplica estilo gráfico a un botón (fuente, color de fondo y texto).
     *
     * @param boton Botón al que se le aplicará el formato.
     */
    private void formatoBotones(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setBackground(new Color(112, 128, 144));
        boton.setForeground(Color.WHITE);
    }

    /**
     * Asocia un ActionListener a los botones principales del panel.
     *
     * @param c Controlador de eventos que se asignará a los botones.
     */
    public void setControlador(ActionListener c) {
        busquedaVuelos.addActionListener(c);
        gestionVuelos.addActionListener(c);
    }

    /**
     * Actualiza el mensaje de bienvenida con el nombre del usuario actual.
     * Se invoca cuando el usuario inicia sesión o vuelve a esta pantalla.
     */
    public void actualizarPantalla() {
        Usuario usuarioActual = SkyManager.getInstance().getUsuarioActual();
        if (usuarioActual != null) {
            bienvenida.setText("Bienvenid@ " + usuarioActual.getNombre());
        }
    }
}
