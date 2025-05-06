package interfaz.panelesControlador;

import sistema.SkyManager;
import vuelos.Vuelo;
import vuelos.EstadoVuelo;
import elementos.Pista;
import interfaz.Aplicacion;

import javax.swing.*;
import java.awt.event.*;

public class ControlControladorGestionVuelos implements ActionListener {
    private ControladorGestionVuelos vista;
    private SkyManager modelo;

    private Vuelo voloSelezionato;

    public ControlControladorGestionVuelos(ControladorGestionVuelos vista) {
        this.vista = vista;
        this.modelo = SkyManager.getInstance();
        inizializzaControlli();
        aggiornaTabella();
    }

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

            // Stato selezionabili dinamici
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
                    // Rimuovi dalla vecchia pista
                    Pista attuale = voloSelezionato.getPista();
                    if (attuale != null) {
                        attuale.getVuelos().remove(voloSelezionato);
                        if (attuale.getUsando() == voloSelezionato) attuale.actualizarColaVuelos();
                    }

                    // Assegna nuova pista
                    voloSelezionato.asignarPista(nuovaPista);
                    nuovaPista.addVuelo(voloSelezionato);

                    // Aggiorna stato automaticamente
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void tornaIndietro() {
		Aplicacion.getInstance().showContInicio();
	}
}