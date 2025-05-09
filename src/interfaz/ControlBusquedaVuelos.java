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

/**
 * Clase que controla la búsqueda de vuelos en la aplicación.
 * Implementa ActionListener para responder a eventos de búsqueda de vuelos según diferentes criterios.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ControlBusquedaVuelos implements ActionListener {
    private SkyManager modelo;

    /**
     * Constructor de la clase ControlBusquedaVuelos.
     * Obtiene la instancia única del modelo SkyManager para gestionar la búsqueda de vuelos.
     */
    public ControlBusquedaVuelos() {
        this.modelo = SkyManager.getInstance();
    }

    /**
     * Maneja eventos de acción generados en la interfaz.
     * 
     * @param e Evento de acción que se ha generado.
     */
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

    /**
     * Realiza la búsqueda de vuelos según el criterio seleccionado en la interfaz.
     * Filtra vuelos en base a código, origen, destino, vuelos del día, terminal o por hora de llegada/salida.
     */
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
                JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de búsqueda a realizar.");
        }

        bv.setFilasTabla(vuelos);

        if (vuelos == null || vuelos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay resultados.");
        }
    }

    /**
     * Convierte una hora ingresada por el usuario en formato HH:mm a un objeto LocalTime.
     * 
     * @param horaUsuario Cadena de texto con la hora ingresada por el usuario.
     * @return Un objeto LocalTime si el formato es válido, null en caso contrario.
     */
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
