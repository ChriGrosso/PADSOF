package interfaz.panelesControlador;

import interfaz.Aplicacion;
import sistema.SkyManager;
import vuelos.Vuelo;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ControlControladorBusquedaVuelos implements ActionListener {
    private ControladorBusquedaVuelos vista;
    private SkyManager modelo;

    public ControlControladorBusquedaVuelos(ControladorBusquedaVuelos vista) {
        this.vista = vista;
        this.modelo = SkyManager.getInstance();
        this.vista.setControlador(this);
        inizializzaEventi();
    }

    private void inizializzaEventi() {
        vista.getBotonBuscar().addActionListener(this);
        vista.getBotonReset().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand().toLowerCase();
        if (comando.contains("reset")) {
            resettaCampi();
        } else {
            filtraTabella();
        }
    }

    public void filtraTabella() {
        String origen = vista.getCampoOrigen().getText().trim().toLowerCase();
        String destino = vista.getCampoDestino().getText().trim().toLowerCase();
        String idAvion = vista.getCampoIdAvion().getText().trim().toLowerCase();
        String idVuelo = vista.getCampoIdVuelo().getText().trim().toLowerCase();
        String estado = ((String) vista.getComboEstado().getSelectedItem()).toLowerCase();

        Collection<Vuelo> vuelos = modelo.getVuelos().values();
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);

        for (Vuelo v : vuelos) {
            String rIdVuelo = v.getId().toLowerCase();
            String rOrigen = v.getOrigen().getNombre().toLowerCase();
            String rDestino = v.getDestino().getNombre().toLowerCase();
            String rIdAvion = v.getAvion().getId().toLowerCase();
            String rEstado = v.getEstVuelo().toString().toLowerCase();
            String rTerminal = v.getTerminal() != null ? v.getTerminal().getId() : "-";
            String rFecha = v.getHoraLlegada().toString();

            if ((origen.isEmpty() || rOrigen.contains(origen)) &&
                (destino.isEmpty() || rDestino.contains(destino)) &&
                (idAvion.isEmpty() || rIdAvion.contains(idAvion)) &&
                (idVuelo.isEmpty() || rIdVuelo.contains(idVuelo)) &&
                (estado.equals("todos") || rEstado.equals(estado))) {

                model.addRow(new Object[]{
                    v.getId(), v.getOrigen().getNombre(), v.getDestino().getNombre(),
                    v.getEstVuelo().toString(), rTerminal, rFecha, v.getAvion().getId()
                });
            }
        }
    }

    public void resettaCampi() {
        vista.getCampoOrigen().setText("");
        vista.getCampoDestino().setText("");
        vista.getCampoIdAvion().setText("");
        vista.getCampoIdVuelo().setText("");
        vista.getComboEstado().setSelectedIndex(0);
        filtraTabella();
    }

    public void tornaIndietro() {
        Aplicacion.getInstance().showContInicio();
    }
}
