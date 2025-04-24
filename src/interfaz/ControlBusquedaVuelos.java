package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import interfaz.panelesGestor.NuevoUsuario;
import sistema.SkyManager;
import vuelos.Vuelo;

public class ControlBusquedaVuelos implements ActionListener {
	private SkyManager modelo;

    public ControlBusquedaVuelos() {
        this.modelo = SkyManager.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        NuevoUsuario nu = Aplicacion.getInstance().getNuevoUsuario();

        switch (comando) {
            case "BUSCAR":
                buscar();
                break;
                
            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
    
    private void buscar() {
    	ArrayList<Vuelo> vuelos = new ArrayList<>();
    	BusquedaVuelos bv = Aplicacion.getInstance().getBusquedaVuelos();
    	
    	
    	
    }
}
