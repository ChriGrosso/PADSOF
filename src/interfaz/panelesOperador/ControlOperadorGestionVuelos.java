package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

/**
 * Controlador del panel de gestión de vuelos del operador.
 * Maneja las acciones relacionadas con la creación de nuevos vuelos.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlOperadorGestionVuelos implements ActionListener {

    /**
     * Constructor vacío del controlador.
     */
    public ControlOperadorGestionVuelos() {
    }

    /**
     * Maneja el evento de acción generado por los botones del panel.
     * En este caso, responde al botón "Nuevo Vuelo".
     * 
     * @param e Evento de acción que contiene la fuente del evento.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Nuevo Vuelo")) {
            this.nuevoVuelo();
        }
    }

    /**
     * Redirige a la pantalla de creación de un nuevo vuelo,
     * actualizando primero su contenido y ocultando el panel actual.
     */
    private void nuevoVuelo() {
        Aplicacion.getInstance().getOpVuelos().setVisible(false);
        Aplicacion.getInstance().getNuevoVuelo().actualizarPantalla();
        Aplicacion.getInstance().showNuevoVuelo();
    }
}
