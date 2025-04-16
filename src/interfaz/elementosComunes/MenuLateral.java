package interfaz.elementosComunes;

import interfaz.Aplicacion;
import sistema.SkyManager;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuLateral extends JPanel {
    private static final long serialVersionUID = 1L;
    private Map<String, JButton> bottoni = new LinkedHashMap<>();

    public MenuLateral(String logoPath) {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

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
        topPanel.setBackground(new Color(45, 45, 45));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(logoButton);
        topPanel.add(Box.createVerticalStrut(30));

        // === BOTTONI FISSI: Notifiche e Logout ===
        JButton notifiche = FabricaBotones.crearBotonMenu("Notifiche", "resources/notification_icon.png");
        notifiche.setActionCommand("NOTIFICHE");

        JButton logout = FabricaBotones.crearBotonMenu("Logout", "resources/logout_icon.png");
        logout.setActionCommand("LOGOUT");

        // Aggiunta listener interno
        notifiche.addActionListener(_ -> verNotifiche());
        logout.addActionListener(_ -> cerrarSesion());

        bottoni.put("Notifiche", notifiche);
        bottoni.put("Logout", logout);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(45, 45, 45));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalGlue());
        bottomPanel.add(notifiche);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(logout);
        bottomPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void cerrarSesion() {
        SkyManager.getInstance().guardarDatos();
        Aplicacion.getInstance().showLogin();
    }

    private void verNotifiche() {
        Aplicacion.getInstance().showNotificaciones();
    }

    public JButton getBottone(String nome) {
        return bottoni.get(nome);
    }
}
