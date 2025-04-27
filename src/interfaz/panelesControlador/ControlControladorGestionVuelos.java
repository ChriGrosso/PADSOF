package interfaz.panelesControlador;

import interfaz.Aplicacion;
import sistema.SkyManager;
import vuelos.Vuelo;
import vuelos.EstadoVuelo;
import aviones.AvionPasajeros;
import aviones.AvionMercancias;
import elementos.Pista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ControlControladorGestionVuelos implements ActionListener, MouseListener {
    private ControladorGestionVuelos vista;
    private SkyManager modelo;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ControlControladorGestionVuelos(ControladorGestionVuelos vista) {
        this.vista = vista;
        this.modelo = SkyManager.getInstance();
        inizializza();
    }

    private void inizializza() {
        vista.getBotonVolver().setControladorVolver(e -> Aplicacion.getInstance().showContInicio());
        vista.getTabla().addMouseListener(this);
        actualizarPantalla();
    }

    public void actualizarPantalla() {
        String[] columnas = {"ID", "Origen", "Destino", "Fecha", "Tipo Avión", "Estado", "Pista Asignada", "Gestionar Pista"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 7;
            }
        };

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Vuelo v : modelo.getVuelos().values()) {
            String tipoAvion = (v.getAvion().getTipoAvion() instanceof AvionPasajeros) ? "Pasajeros" :
                               (v.getAvion().getTipoAvion() instanceof AvionMercancias) ? "Mercancias" : "Otro";

            String pistaAsignada = (v.getPista() != null) ? v.getPista().getId() : "No Asignada";

            String fechaFormateada = v.getHoraSalida().format(dateFormatter) + " - " + v.getHoraLlegada().format(dateFormatter);

            Object[] fila = {
                v.getId(),
                v.getOrigen().getCodigo(),
                v.getDestino().getCodigo(),
                fechaFormateada,
                tipoAvion,
                v.getEstVuelo().toString(),
                pistaAsignada,
                "Asignar/Modificar"
            };
            model.addRow(fila);
        }

        vista.getTabla().setModel(model);

        vista.getTabla().getColumn("Pista Asignada").setCellRenderer(new PistaRenderer());
        vista.getTabla().getColumn("Gestionar Pista").setCellRenderer(new GestionarPistaRenderer());
        vista.getTabla().getColumn("Gestionar Pista").setCellEditor(new GestionarPistaEditor());

        vista.getTabla().getColumn("Estado").setCellEditor(new DefaultCellEditor(new JComboBox<>(EstadoVuelo.values())));
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vista.getTabla().getSelectedRow();
        int column = vista.getTabla().getSelectedColumn();
        if (row != -1 && column == 5) { // Cambio di stato
            String idVuelo = (String) vista.getTabla().getValueAt(row, 0);
            Vuelo vuelo = modelo.getVuelos().get(idVuelo);
            if (vuelo != null) {
                String nuevoEstado = (String) vista.getTabla().getValueAt(row, 5);
                vuelo.setEstVuelo(EstadoVuelo.valueOf(nuevoEstado));
                JOptionPane.showMessageDialog(null, "Estado actualizado a: " + nuevoEstado);
                actualizarPantalla(); // Ricarica tabella
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Renderer per "Pista Asignada"
    private static class PistaRenderer extends JLabel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public PistaRenderer() {
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                        boolean hasFocus, int row, int column) {
            String text = (String) value;
            setText(text);
            if ("No Asignada".equals(text)) {
                setFont(getFont().deriveFont(Font.BOLD));
            } else {
                setFont(getFont().deriveFont(Font.PLAIN));
            }
            setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            return this;
        }
    }

    // Renderer per il bottone "Gestionar Pista"
    private static class GestionarPistaRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton boton = new JButton("Asignar/Modificar");

        public GestionarPistaRenderer() {
            setLayout(new BorderLayout());
            boton.setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                        boolean hasFocus, int row, int column) {
            removeAll();
            add(boton, BorderLayout.CENTER);
            return this;
        }
    }

    // Editor per il bottone "Gestionar Pista"
    private static class GestionarPistaEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel = new JPanel(new BorderLayout());
        private final JButton boton = new JButton("Asignar/Modificar");

        public GestionarPistaEditor() {
            boton.setBackground(Color.LIGHT_GRAY);
            boton.addActionListener(_ -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, boton);
                int row = table.getEditingRow();
                String idVuelo = (String) table.getValueAt(row, 0);
                Vuelo vuelo = SkyManager.getInstance().getVuelos().get(idVuelo);

                if (vuelo == null) {
                    JOptionPane.showMessageDialog(null, "Error: vuelo no encontrado.");
                    fireEditingCanceled();
                    return;
                }

                List<Pista> disponibles;
                disponibles = SkyManager.getInstance().getPistasDisponibles(vuelo);

                disponibles = disponibles.stream()
                        .filter(p -> p != null)
                        .sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                        .collect(Collectors.toList());

                if (disponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay pistas disponibles.");
                    fireEditingCanceled();
                    return;
                }

                Pista seleccion = (Pista) JOptionPane.showInputDialog(
                        null, "Seleccione una pista:", "Asignar Pista",
                        JOptionPane.QUESTION_MESSAGE, null, disponibles.toArray(), disponibles.get(0));

                if (seleccion != null) {
                    vuelo.asignarPista(seleccion);
                    JOptionPane.showMessageDialog(null, "Pista asignada: " + seleccion.getId());
                    fireEditingStopped();
                    // Aggiorna visualizzazione
                    SwingUtilities.invokeLater(() -> Aplicacion.getInstance().showControladorGestionVuelos());
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            panel.removeAll();
            panel.add(boton, BorderLayout.CENTER);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}