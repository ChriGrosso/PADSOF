package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

public class ControlGestorGestionUsuarios implements ActionListener {

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
