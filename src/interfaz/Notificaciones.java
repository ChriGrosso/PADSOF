package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import aerolineas.Aerolinea;
import interfaz.elementosComunes.BotonVolver;
import notificaciones.Notificacion;
import sistema.SkyManager;
import usuarios.Operador;
import usuarios.Usuario;

public class Notificaciones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable tablaNotificaciones;
	
	public Notificaciones() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("Notificaciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelContenido.add(titulo, gbc);

        tablaNotificaciones = new JTable();
        tablaNotificaciones.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(tablaNotificaciones);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelContenido.add(scrollPane, gbc);

        add(panelContenido, BorderLayout.CENTER);
    }
	
	public void actualizarPantalla() {
        String[] columnas = {"Notificación", "Estado"};

        Usuario user = SkyManager.getInstance().getUsuarioActual();
        List<Notificacion> notificaciones = user.getNotificaciones();


        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Solo el botón de acción
            }
        };

        for (Notificacion notif : notificaciones) {
            String texto = "<html><b>Mensaje:</b> " + notif.getMensaje();
            if(notif.getEmisor() == null) {
            	texto += "<br><b>Remitente:</b> automático";
            } else {
            	texto += "<br><b>Remitente:</b> " + notif.getEmisor().getNombre();
            }
            texto += "<br><b>Estado:</b> " + (notif.getLeida() ? "Leído" : "No leído") + "</html>";

            model.addRow(new Object[]{
                texto,
                notif.getLeida() ? "Leído" : "Leer"
            });
        }

        tablaNotificaciones.setModel(model);
        tablaNotificaciones.getColumnModel().getColumn(1).setPreferredWidth(100); // Leer
        tablaNotificaciones.getColumnModel().getColumn(1).setMinWidth(100);
        tablaNotificaciones.getColumnModel().getColumn(1).setMaxWidth(100);

        tablaNotificaciones.getColumn("Notificación").setCellRenderer(new MultiLineRenderer());
        tablaNotificaciones.getColumn("Estado").setCellRenderer(new NotificacionRenderer());
        tablaNotificaciones.getColumn("Estado").setCellEditor(new NotificacionEditor(notificaciones));
    }
	
	private static class MultiLineRenderer extends JLabel implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        
        public MultiLineRenderer() {
            setOpaque(true);
            setVerticalAlignment(SwingConstants.TOP);
            setFont(new Font("Arial", Font.PLAIN, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value != null ? value.toString() : "");
            String estado = table.getValueAt(row, 0).toString();
            if (estado.contains("Leído")) {
                setBackground(new Color(220, 255, 220)); // verde clarito
            } else {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }
	
	private static class NotificacionRenderer extends JPanel implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final JButton botonMarcarLeido = new JButton("Leer");
        private final JLabel etiquetaLeido = new JLabel("Leído");

        public NotificacionRenderer() {
            setLayout(new BorderLayout());
            botonMarcarLeido.setBackground(Color.cyan);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            removeAll();
            if (value != null && "Leer".equals(value.toString())) {
                add(botonMarcarLeido, BorderLayout.CENTER);
            } else {
                add(etiquetaLeido, BorderLayout.CENTER);
            }
            return this;
        }
    }

    private static class NotificacionEditor extends AbstractCellEditor implements TableCellEditor {
        private static final long serialVersionUID = 1L;
        private final JPanel panel;
        private final JButton botonMarcarLeido;
        private final JLabel etiquetaLeido;
        private final List<Notificacion> notificaciones;

        public NotificacionEditor(Collection<Notificacion> notifs) {
            this.panel = new JPanel(new BorderLayout());
            this.botonMarcarLeido = new JButton("Leer");
            this.etiquetaLeido = new JLabel("Leído");
            this.notificaciones = new ArrayList<>(notifs);

            botonMarcarLeido.setBackground(Color.cyan);

            botonMarcarLeido.addActionListener(_ -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonMarcarLeido);
                int row = table.getEditingRow();
                Notificacion notif = notificaciones.get(row);

                if (notif.getMensaje().contains("ha solicitado compartir su vuelo")) {
                    int opcion = JOptionPane.showOptionDialog(null,
                            notif.getMensaje(),
                            "Solicitud de Compartir Vuelo",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Aceptar", "Rechazar"},
                            "Aceptar");
                    String vueloId = null;
                    Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
            		Aerolinea a = op.getAerolinea();

                    if (opcion == JOptionPane.YES_OPTION) {
                        vueloId = UtilidadesNotificaciones.extraerIdVuelo(notif.getMensaje());
                        SkyManager.getInstance().getVuelos().get(vueloId).compartirVuelo(a);
                        JOptionPane.showMessageDialog(null, "Has aceptado la solicitud.");
                    } else if (opcion == JOptionPane.NO_OPTION) {
                    	vueloId = UtilidadesNotificaciones.extraerIdVuelo(notif.getMensaje());
                    	SkyManager.getInstance().getVuelos().get(vueloId).rechazarCompartirVuelo();
                        JOptionPane.showMessageDialog(null, "Has rechazado la solicitud.");
                    }
                }

                notif.setLeida(true);
                Aplicacion.getInstance().getNotificaciones().actualizarPantalla();
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                      boolean isSelected, int row, int column) {
            panel.removeAll();
            if (value != null && "Leer".equals(value.toString())) {
                panel.add(botonMarcarLeido, BorderLayout.CENTER);
            } else {
                panel.add(etiquetaLeido, BorderLayout.CENTER);
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getNotificaciones().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}
}
