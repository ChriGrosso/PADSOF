package interfaz.panelesControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlControladorInicio implements ActionListener {
    private SkyManager modelo;

    public ControlControladorInicio() {
        this.modelo = SkyManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand().toLowerCase();

        switch (comando) {
            case "cerrar sesión":
            case "logout":
                cerrarSesion();
                break;

            case "notificaciones":
            case "ver notificaciones":
                verNotificaciones();
                break;

            case "gestión vuelos":
                // aggiungi eventuale navigazione
                System.out.println("➡ Navegar a gestión vuelos...");
                break;

            case "buscar vuelos":
                // aggiungi eventuale navigazione
                System.out.println("🔍 Navegar a búsqueda vuelos...");
                break;

            default:
                System.out.println("⚠ Acción no reconocida: " + comando);
        }
    }

    private void cerrarSesion() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showLogin();
    }

    private void verNotificaciones() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showNotificaciones();
    }
}
