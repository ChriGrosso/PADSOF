package interfaz.util;

import interfaz.Aplicacion;
import sistema.SkyManager;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase MenuLateral - Representa un panel lateral con un logo superior y botones fijos
 * como "Notificaciones" y "Logout". Este menú se utiliza como componente común
 * en las diferentes pantallas de la aplicación.
 * 
 * Los botones están estilizados y gestionados mediante un mapa para facilitar el acceso.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class MenuLateral extends JPanel {
    private static final long serialVersionUID = 1L;
    
    /** Mapa que asocia nombres de botones con sus respectivas instancias. */
    private Map<String, JButton> bottoni = new LinkedHashMap<>();

    /**
     * Constructor del menú lateral.
     * Crea el panel con el logo en la parte superior y los botones de notificaciones y cierre de sesión en la parte inferior.
     *
     * @param logoPath Ruta del archivo de imagen para el logo mostrado en el menú lateral.
     */
    public MenuLateral(String logoPath) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 130, 180)); // Color de fondo azul

        // === LOGO ===
        ImageIcon icon = new ImageIcon(logoPath);
        int maxSize = 100;
        Image scaledImage = icon.getImage().getScaledInstance(maxSize, maxSize, Image.SCALE_SMOOTH);
        JButton logoButton = new JButton(new ImageIcon(scaledImage));
        logoButton.setEnabled(false);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(70, 130, 180));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(logoButton);
        topPanel.add(Box.createVerticalStrut(30));

        // === BOTONES FIJOS: Notificaciones y Logout ===
        JButton notifiche = FabricaBotones.crearBotonMenu("Notificaciones", "resources/notification_icon.png");
        notifiche.setBackground(new Color(112, 128, 150));
        notifiche.setForeground(Color.WHITE);
        notifiche.setActionCommand("NOTIFICHE");

        JButton logout = FabricaBotones.crearBotonMenu("Logout", "resources/logout_icon.png");
        logout.setBackground(new Color(112, 128, 150));
        logout.setForeground(Color.WHITE);
        logout.setActionCommand("LOGOUT");

        // Listeners internos
        notifiche.addActionListener(_ -> verNotifiche());
        logout.addActionListener(_ -> cerrarSesion());

        bottoni.put("Notifiche", notifiche);
        bottoni.put("Logout", logout);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(70, 130, 180));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalGlue());
        bottomPanel.add(notifiche);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(logout);
        bottomPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Lógica para cerrar la sesión del usuario actual.
     * Guarda los datos y redirige al panel de inicio de sesión.
     */
    private void cerrarSesion() {
        SkyManager.getInstance().guardarDatos();
        Aplicacion.getInstance().showLogin();
    }

    /**
     * Muestra la pantalla de notificaciones.
     * Actualiza previamente la información mostrada.
     */
    private void verNotifiche() {
        Aplicacion.getInstance().getNotificaciones().actualizarPantalla();
        Aplicacion.getInstance().showNotificaciones();
    }

    /**
     * Devuelve un botón del menú lateral a partir de su nombre.
     *
     * @param nome Nombre asociado al botón (ej. "Notifiche" o "Logout").
     * @return Instancia del JButton correspondiente, o null si no existe.
     */
    public JButton getBottone(String nome) {
        return bottoni.get(nome);
    }
}
