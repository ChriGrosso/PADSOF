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
        String comando = e.getActionCommand();

        switch (comando) {
            case "CERRAR_SESION":
                cerrarSesion();
                break;

            case "NOTIFICACIONES":
                verNotificaciones();
                break;

            case "BUSQUEDA_VUELOS":
                System.out.println("Navegar a búsqueda vuelos...");
                break;
            case "GESTION_VUELOS":
                System.out.println("Navegar a gestión vuelos...");
                break;    
            case "GESTION_AEROPUERTO":
                System.out.println("Navegar a gestión aeropuerto...");
                break;  
            case "GESTION_FACTURAS":
                System.out.println("Navegar a gestión facturas...");
                break;
            case "GESTION_USUARIOS":
                verUsuarios();
                break;
            case "ESTADISTICAS":
                System.out.println("Navegar a estadísticas...");
                break;

            default:
                System.out.println("Comando desconocido:  " + comando);
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
    
    private void verUsuarios() {
        Aplicacion.getInstance().showGestorGestionUsuarios();
    }
}
