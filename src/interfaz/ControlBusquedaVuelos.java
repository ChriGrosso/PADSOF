package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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

        switch (comando) {
            case "BUSCAR":
                buscar();
                break;
                
            default:
	            break;
        }
    }
    
    private void buscar() {
    	ArrayList<Vuelo> vuelos = new ArrayList<>();
    	BusquedaVuelos bv = Aplicacion.getInstance().getBusquedaVuelos();
    	String contenido = bv.getContenidoCampoBusqueda();
    	
    	LocalTime hora;
    	
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
        	hora = parsearHoraUsuario(contenido);
        	if (hora != null) vuelos = modelo.buscarVuelosPorHoraLlegada(hora);
            break;
        case "Hora de Salida":
        	hora = parsearHoraUsuario(contenido);
        	if (hora != null) vuelos = modelo.buscarVuelosPorHoraSalida(hora);
            break;
        default:
        	JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de búsqueda a realizar");
        }
    	bv.setFilasTabla(vuelos);
    	if (vuelos == null || vuelos.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "No hay resultados");
    	}
    	
    }
    
    private LocalTime parsearHoraUsuario(String horaUsuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(horaUsuario, formatter);
        } catch (DateTimeParseException e) {
        	JOptionPane.showMessageDialog(null, "Formato de hora inválido. Usa el formato HH:mm (por ejemplo, 14:45).");
            return null;
        }
    }
}
