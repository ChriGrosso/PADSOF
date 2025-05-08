package interfaz.panelesControlador;

import sistema.SkyManager;
import vuelos.Vuelo;
import vuelos.EstadoVuelo;
import elementos.Pista;
import interfaz.Aplicacion;

import javax.swing.*;
import java.awt.event.*;

/**
 * Clase ControlControladorGestionVuelos - Controlador que gestiona la lógica de interacción
 * entre la vista de gestión de vuelos del controlador y el modelo de la aplicación (SkyManager).
 * 
 * Permite modificar el estado y la pista asignada de un vuelo, actualiza la tabla de vuelos
 * y gestiona las acciones de los botones de la interfaz.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class ControlControladorGestionVuelos implements ActionListener {
    
    private ControladorGestionVuelos vista;
    private SkyManager modelo;
    private Vuelo voloSelezionato;

    /**
     * Constructor del controlador de la vista de gestión de vuelos.
     *
     * @param vista Instancia de la vista asociada a este controlador.
     */
    public ControlControladorGestionVuelos(ControladorGestionVuelos vista) {
        this.vista = vista;
        this.modelo = SkyManager.getInstance();
        inizializzaControlli();
        aggiornaTabella();
    }

    /**
     * Inicializa todos los listeners y acciones de los botones en la vista.
     */
    private void inizializzaControlli() {
        vista.getBotonVolver().setControladorVolver(e -> vista.mostraVistaPrincipale());

        vista.getBotonModificar().addActionListener(e -> {
            int row = vista.getTabla().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Seleziona un volo da modificare.");
                return;
            }
            String idVolo = (String) vista.getTabla().getValueAt(row, 0);
            voloSelezionato = modelo.getVuelos().get(idVolo);
            if (voloSelezionato == null) return;

            aggiornaComboStati();

            vista.getComboPistas().removeAllItems();
            for (Pista pista : modelo.getPistasDisponibles(voloSelezionato)) {
                vista.getComboPistas().addItem(pista.getId());
            }

            if (voloSelezionato.getPista() != null) {
                String idPista = voloSelezionato.getPista().getId();
                if (((DefaultComboBoxModel<String>) vista.getComboPistas().getModel()).getIndexOf(idPista) == -1) {
                    vista.getComboPistas().addItem(idPista);
                }
                vista.getComboPistas().setSelectedItem(idPista);
            }

            vista.mostraFormModifica();
        });

        vista.getBotonGuardar().addActionListener(e -> {
            if (voloSelezionato == null) return;

            String nuovoStato = (String) vista.getComboEstado().getSelectedItem();
            voloSelezionato.setEstVuelo(EstadoVuelo.valueOf(nuovoStato));

            String selezionePista = (String) vista.getComboPistas().getSelectedItem();
            if (selezionePista != null) {
                Pista nuovaPista = modelo.getPistas().get(selezionePista);
                if (nuovaPista != null) {
                    // Elimina de la pista actual
                    Pista attuale = voloSelezionato.getPista();
                    if (attuale != null) {
                        attuale.getVuelos().remove(voloSelezionato);
                        if (attuale.getUsando() == voloSelezionato) attuale.actualizarColaVuelos();
                    }

                    // Asigna la nueva pista
                    voloSelezionato.asignarPista(nuovaPista);
                    nuovaPista.addVuelo(voloSelezionato);

                    // Actualiza estado si corresponde
                    if (voloSelezionato.getEstVuelo() == EstadoVuelo.ESPERANDO_PISTA_D) {
                        voloSelezionato.setEstVuelo(EstadoVuelo.ESPERANDO_DESPEGUE);
                    } else if (voloSelezionato.getEstVuelo() == EstadoVuelo.ESPERANDO_PISTA_A) {
                        voloSelezionato.setEstVuelo(EstadoVuelo.ESPERANDO_ATERRIZAJE);
                    }
                }
            }

            SkyManager.getInstance().guardarDatos();
            voloSelezionato = null;
            aggiornaTabella();
            vista.mostraVistaPrincipale();
        });

        vista.getBotonCancelar().addActionListener(e -> {
            voloSelezionato = null;
            vista.mostraVistaPrincipale();
        });
    }

    /**
     * Actualiza dinámicamente los estados disponibles en el combo de estado
     * según el origen del vuelo seleccionado.
     */
    private void aggiornaComboStati() {
        vista.getComboEstado().removeAllItems();
        vista.getComboEstado().addItem(EstadoVuelo.EN_VUELO.name());
        vista.getComboEstado().addItem(EstadoVuelo.EN_HANGAR.name());

        if (voloSelezionato.getOrigen().equals(modelo.getAeropuertoPropio())) {
            vista.getComboEstado().addItem(EstadoVuelo.ESPERANDO_PISTA_D.name());
        } else {
            vista.getComboEstado().addItem(EstadoVuelo.ESPERANDO_PISTA_A.name());
        }

        vista.getComboEstado().setSelectedItem(voloSelezionato.getEstVuelo().name());
    }

    /**
     * Actualiza la tabla de vuelos mostrada en la vista con los datos actuales del modelo.
     * Muestra columnas como ID, origen, destino, fecha, tipo de avión, estado y pista.
     */
    public void aggiornaTabella() {
        String[] colonne = {"ID", "Origen", "Destino", "Fecha", "Tipo Avión", "Estado", "Pista Asignata"};
        vista.getModelo().setDataVector(new Object[0][0], colonne);

        for (Vuelo v : modelo.getVuelos().values()) {
            String tipo = v.getAvion().getTipoAvion().getClass().getSimpleName().contains("Pasajeros") ? "Pasajeros" : "Mercancias";
            String pista = (v.getPista() != null) ? v.getPista().getId() : "No Asignata";
            Object[] row = {
                v.getId(),
                v.getOrigen().getCodigo(),
                v.getDestino().getCodigo(),
                v.getHoraSalida().toString(),
                tipo,
                v.getEstVuelo().toString(),
                pista
            };
            vista.getModelo().addRow(row);
        }
    }

    /**
     * Método requerido por la interfaz ActionListener (no se utiliza directamente).
     *
     * @param e Evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Método no implementado
    }

    /**
     * Muestra la pantalla de inicio del controlador.
     */
    public void tornaIndietro() {
        Aplicacion.getInstance().showContInicio();
    }
}
