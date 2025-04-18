package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlGestorGestionUsuarios implements ActionListener {
	private SkyManager modelo;

    public ControlGestorGestionUsuarios() {
        this.modelo = SkyManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        switch (comando) {
		    case "ATRAS":
		    	irAtras();
		        break;
		    case "NUEVO_USUARIO":
		        System.out.println("Navegar a ficha Nuevo Usuario()...");
		        break;
		
		    default:
		        System.out.println("Comando desconocido:  " + comando);	
	    }
    }
    
    private void irAtras() {
        Aplicacion.getInstance().showGestorInicio();
    }
    /*
    private void fichaNuevoUsuario() {
        Aplicacion.getInstance().showNuevoUsuario();
    }*/
}
