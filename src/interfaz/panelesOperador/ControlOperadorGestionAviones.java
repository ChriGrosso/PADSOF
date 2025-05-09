package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

/**
 * Controlador del panel de gestión de aviones del operador.
 * Gestiona las acciones asociadas a los botones "Nuevo Avión"
 * y "Nuevo Tipo Avión", redirigiendo a los formularios correspondientes.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlOperadorGestionAviones implements ActionListener {

    /**
     * Constructor vacío del controlador.
     */
    public ControlOperadorGestionAviones() {
    }

    /**
     * Maneja los eventos de acción generados por los botones del panel.
     * Redirige según el comando del botón pulsado.
     * 
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Nuevo Avión")) {
            this.nuevoAvion();
        } else if (e.getActionCommand().equals("Nuevo Tipo Avión")) {
            this.nuevoTipoAvion();
        }
    }

    /**
     * Muestra el formulario para registrar un nuevo avión.
     * Oculta el panel de gestión actual.
     */
    private void nuevoAvion() {
        Aplicacion.getInstance().getOpAviones().setVisible(false);
        Aplicacion.getInstance().showNuevoAvion();
    }

    /**
     * Muestra el formulario para registrar un nuevo tipo de avión.
     * Oculta el panel de gestión actual.
     */
    private void nuevoTipoAvion() {
        Aplicacion.getInstance().getOpAviones().setVisible(false);
        Aplicacion.getInstance().showNuevoTipoAvion();
    }
}
