package interfaz.panelesControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfaz.Aplicacion;
import sistema.SkyManager;

/**
 * Clase ControlControladorInicio - Controlador principal para la pantalla de inicio del controlador de vuelos.
 * 
 * Gestiona las acciones asociadas a los botones: cerrar sesión, ver notificaciones,
 * acceder a la gestión de vuelos y a la búsqueda de vuelos.
 * 
 * Implementa la interfaz {@link ActionListener} para manejar eventos de acción desde la interfaz gráfica.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class ControlControladorInicio implements ActionListener {
    private SkyManager modelo;

    /**
     * Constructor del controlador de la pantalla de inicio.
     * Inicializa el modelo accediendo a la instancia única de SkyManager.
     */
    public ControlControladorInicio() {
        this.modelo = SkyManager.getInstance();
    }

    /**
     * Maneja los eventos de acción generados por los botones.
     * Realiza distintas operaciones según el comando recibido.
     *
     * @param e Evento de acción generado por la interfaz.
     */
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
                gestionVuelos();
                break;

            case "buscar vuelos":
                buscarVuelos();
                break;

            default:
                System.out.println("⚠ Acción no reconocida: " + comando);
        }
    }

    /**
     * Cierra la sesión del usuario actual, guarda los datos y redirige al login.
     */
    private void cerrarSesion() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showLogin();
    }

    /**
     * Abre la pantalla de notificaciones y guarda previamente los datos.
     */
    private void verNotificaciones() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showNotificaciones();
    }

    /**
     * Abre la vista de gestión de vuelos para el controlador.
     */
    private void gestionVuelos() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showControladorGestionVuelos();
    }

    /**
     * Abre la vista de búsqueda de vuelos.
     */
    private void buscarVuelos() {
        modelo.guardarDatos();
        Aplicacion.getInstance().showBusquedaVuelos();
    }
}
