package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
    	String contenido = bv.getContenidoCampoBusqueda();
    	
    	String tipo = bv.getTipoBusquedaSeleccionado();
    	switch (tipo) {
        case "Código":
            vuelos.add(modelo.buscarVueloPorCodigo(contenido));
            break;
        case "Origen":
            vuelos = modelo.buscarVuelosPorOrigen(modelo.getAeropuertosExternos().get(contenido));
            break;
        case "Destino":
        	vuelos = modelo.buscarVuelosPorDestino(modelo.getAeropuertosExternos().get(contenido));
            break;    
        case "Vuelos del Día":
        	vuelos = modelo.buscarVuelosDelDia();
            break;
        case "Terminal":
        	vuelos = modelo.buscarVuelosPorTerminal(modelo.getTerminales().get(contenido));
            break;
        case "Hora de Llegada":
        	//vuelos = modelo.buscarVuelosPorHoraLlegada();
            break;
        case "Hora de Salida":
            buscar();
            break;
        default:
        	JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de búsqueda a realizar");
    }
    
    	
    	
    }
}
