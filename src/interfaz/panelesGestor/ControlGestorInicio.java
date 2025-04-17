package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlGestorInicio implements ActionListener {
	private SkyManager modelo;

    public ControlGestorInicio() {
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


            case "buscar vuelos":
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
