package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

/**
 * Clase que gestiona los eventos de la interfaz de administración de usuarios para el gestor.
 * Implementa `ActionListener` para manejar las acciones relacionadas con la gestión de usuarios.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ControlGestorGestionUsuarios implements ActionListener {
	/**
     * Maneja los eventos de acción generados en la interfaz de gestión de usuarios.
     * Dependiendo del comando recibido, redirige a distintas pantallas de la aplicación.
     * 
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        switch (comando) {
		    case "ATRAS":
		    	Aplicacion.getInstance().showGestorInicio();
		        break;
		    case "NUEVO_USUARIO":
		    	Aplicacion.getInstance().showNuevoUsuario();
		        break;
		
		    default:
		        System.out.println("Comando desconocido:  " + comando);	
	    }
    }
}
