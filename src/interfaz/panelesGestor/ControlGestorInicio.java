package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

public class ControlGestorInicio implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "BUSQUEDA_VUELOS":
            	Aplicacion.getInstance().showBusquedaVuelos();
                break;
            case "GESTION_VUELOS":
            	Aplicacion.getInstance().showGestorGestionVuelos();
                break;    
            case "GESTION_AEROPUERTO":
            	Aplicacion.getInstance().showGestorGestionAeropuerto();
                break;  
            case "GESTION_FACTURAS":
                System.out.println("Navegar a gestión facturas...");
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
