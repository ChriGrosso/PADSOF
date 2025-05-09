package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

/**
 * Clase que controla la pantalla de inicio del gestor.
 * Maneja los eventos relacionados con la navegación entre diferentes módulos de gestión dentro de la aplicación.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ControlGestorInicio implements ActionListener {
	/**
     * Maneja los eventos de acción generados en la pantalla de inicio del gestor.
     * Dependiendo del comando recibido, redirige a las distintas secciones de la aplicación.
     * 
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "BUSQUEDA_VUELOS":
            	Aplicacion.getInstance().showBusquedaVuelos();
                break;
            case "GESTION_VUELOS":
            	Aplicacion.getInstance().getGestorGestionVuelos().actualizarPantalla();
            	Aplicacion.getInstance().showGestorGestionVuelos();
                break;    
            case "GESTION_AEROPUERTO":
            	Aplicacion.getInstance().showGestorGestionAeropuerto();
                break;  
            case "GESTION_FACTURAS":
                Aplicacion.getInstance().showGestorGestionFacturas();
                break;
            case "GESTION_USUARIOS":
            	Aplicacion.getInstance().showGestorGestionUsuarios();
                break;
            case "ESTADISTICAS":
            	Aplicacion.getInstance().showGestorEstadisticas();
                break;

            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
}
