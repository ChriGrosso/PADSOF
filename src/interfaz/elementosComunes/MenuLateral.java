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
    private Boolean gestor = false;

    public MenuLateral(String logoPath) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 130, 180));

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

        // === BOTTONI FISSI: Notifiche e Logout ===
        JButton notifiche = FabricaBotones.crearBotonMenu("Notificaciones", "resources/notification_icon.png");
        notifiche.setBackground(new Color(112, 128, 150));
        notifiche.setForeground(Color.WHITE);
        notifiche.setActionCommand("NOTIFICHE");

        JButton logout = FabricaBotones.crearBotonMenu("Logout", "resources/logout_icon.png");
        logout.setBackground(new Color(112, 128, 150));
        logout.setForeground(Color.WHITE);
        logout.setActionCommand("LOGOUT");

        // Aggiunta listener interno
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
    
    public MenuLateral(String logoPath, Boolean gestor) {
    	super();
    	this.gestor = gestor;
    }

    private void cerrarSesion() {
        SkyManager.getInstance().guardarDatos();
        Aplicacion.getInstance().showLogin();
    }

    private void verNotifiche() {
    	Aplicacion.getInstance().getNotificaciones().actualizarPantalla();
    	if (gestor == false) {
    		Aplicacion.getInstance().showNotificaciones();
    	} else {
    		Aplicacion.getInstance().showGestorNotificaciones();
    	}
    }

    public JButton getBottone(String nome) {
        return bottoni.get(nome);
    }
}
