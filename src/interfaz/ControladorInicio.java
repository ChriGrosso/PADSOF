package interfaz;

import javax.swing.*;
import java.awt.*;

public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;

    public ControladorInicio() {
        // Layout per il pannello
        this.setLayout(new BorderLayout());

        // Creiamo la barra laterale
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        // Aggiungiamo i pulsanti
        JButton vuelosButton = new JButton("Gestión de Vuelos");
        JButton busquedaButton = new JButton("Búsqueda de Vuelos");
        JButton notificacionesButton = new JButton("Ver Notificaciones");
        JButton logoutButton = new JButton("Cerrar Sesión");

        // Aggiungiamo i listener agli eventi
        vuelosButton.addActionListener(new ControladorInicio(frame));
        busquedaButton.addActionListener(new ControladorInicio(frame));
        notificacionesButton.addActionListener(new ControladorInicio(frame));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Torna alla schermata di login
                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), Aplicacion.LOGIN);
            }
        });

        // Aggiungiamo i bottoni alla sidebar
        sidebarPanel.add(vuelosButton);
        sidebarPanel.add(busquedaButton);
        sidebarPanel.add(notificacionesButton);
        sidebarPanel.add(logoutButton);

        // Aggiungiamo la sidebar e il contenuto principale
        this.add(sidebarPanel, BorderLayout.WEST);
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Benvenuto nel Pannello del Controllore"));
        this.add(mainPanel, BorderLayout.CENTER);
    }
}
