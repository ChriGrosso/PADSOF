package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlControladorInicio implements ActionListener {
    private JPanel vista;
    private Aplicacion frame;

    public ControladorInicio(Aplicacion frame) {
        this.frame = frame;
        this.vista = frame.getControlControladorInicio();  // Recupera il pannello per il contollore
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ver Notificaciones")) {
            // Gestisci evento Ver Notificaciones
            System.out.println("Ver Notificaciones");
        } else if (e.getActionCommand().equals("Gestión de Vuelos")) {
            // Gestisci evento Gestione Vuelos
            System.out.println("Gestión de Vuelos");
        } else if (e.getActionCommand().equals("Búsqueda de Vuelos")) {
            // Gestisci evento Ricerca Vuelos
            System.out.println("Búsqueda de Vuelos");
        }
    }
}
