package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

/**
 * Controlador principal del panel de inicio del operador.
 * Gestiona las acciones realizadas sobre los botones de navegación,
 * permitiendo el acceso a las distintas secciones como vuelos, aviones,
 * facturas, estadísticas y búsqueda de vuelos.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlOperadorInicio implements ActionListener {

    /**
     * Constructor vacío del controlador.
     */
    public ControlOperadorInicio() {
    }

    /**
     * Maneja las acciones realizadas por el usuario en el panel de inicio.
     * Dependiendo del texto del botón presionado, redirige a la sección correspondiente.
     *
     * @param e Evento de acción generado por la interfaz.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Gestionar Aviones")) {
            this.gestionarAviones();
        } else if (e.getActionCommand().equals("Gestionar Vuelos")) {
            this.gestionarVuelos();
        } else if (e.getActionCommand().equals("Gestionar Facturas")) {
            this.gestionarFacturas();
        } else if (e.getActionCommand().equals("Buscar Vuelos")) {
            this.buscarVuelos();
        } else if (e.getActionCommand().equals("Estadísticas")) {
            this.mostrarEstadisticas();
        }
    }

    /**
     * Abre el panel de estadísticas del operador.
     */
    private void mostrarEstadisticas() {
        Aplicacion.getInstance().getOpEstadisticas().actualizarPantalla();
        Aplicacion.getInstance().getOpInicio().setVisible(false);
        Aplicacion.getInstance().showOpEstadisticas();
    }

    /**
     * Abre el panel de búsqueda de vuelos.
     */
    private void buscarVuelos() {
        Aplicacion.getInstance().getOpInicio().setVisible(false);
        Aplicacion.getInstance().showBusquedaVuelos();
    }

    /**
     * Abre el panel de gestión de aviones del operador.
     */
    private void gestionarAviones() {
        Aplicacion.getInstance().getOpAviones().actualizarPantalla();
        Aplicacion.getInstance().getOpInicio().setVisible(false);
        Aplicacion.getInstance().showOpAviones();
    }

    /**
     * Abre el panel de gestión de vuelos del operador.
     */
    private void gestionarVuelos() {
        Aplicacion.getInstance().getOpVuelos().actualizarPantalla();
        Aplicacion.getInstance().getOpInicio().setVisible(false);
        Aplicacion.getInstance().showOpVuelos();
    }

    /**
     * Abre el panel de gestión de facturas del operador.
     */
    private void gestionarFacturas() {
        Aplicacion.getInstance().getOpFacturas().actualizarPantalla();
        Aplicacion.getInstance().getOpInicio().setVisible(false);
        Aplicacion.getInstance().showOpFacturas();
    }
}
