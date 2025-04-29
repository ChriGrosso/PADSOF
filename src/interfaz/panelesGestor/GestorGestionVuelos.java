package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
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
import elementos.Terminal;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import usuarios.Controlador;
import vuelos.Vuelo;

public class GestorGestionVuelos extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable tablaVuelos;
	
	public GestorGestionVuelos() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
		
		JPanel panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		panelSuperiorIzquierdo.setBackground(new Color(173, 216, 230));
		
		// Contenedor en la esquina superior derecha
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> paginaAnterior());

        // Título
	    JLabel titulo = new JLabel("Gestión de Vuelos", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    
	    // Añadir el contenedor al panel superior
        panelSuperiorIzquierdo.add(panelAtras, BorderLayout.NORTH);
	    panelSuperiorIzquierdo.add(titulo, BorderLayout.AFTER_LAST_LINE);
	    add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		
		
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

	    
		// tabla
		tablaVuelos = new JTable();
		tablaVuelos.setBackground(Color.WHITE);
		tablaVuelos.setForeground(Color.BLACK);
		tablaVuelos.setRowHeight(50);
		tablaVuelos.setGridColor(new Color(75, 135, 185));
		tablaVuelos.getTableHeader().setBackground(new Color(70, 130, 180));
		tablaVuelos.getTableHeader().setForeground(Color.WHITE); 
	 
		JScrollPane scroll = new JScrollPane(tablaVuelos);
		scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
		gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridwidth = 3; // Ocupa todo el ancho
	    gbc.fill = GridBagConstraints.BOTH; // Expandir horizontal y verticalmente
	    gbc.weightx = 1.0; // Permitir expansión horizontal
	    gbc.weighty = 1.0; // Permitir expansión vertical
	    panelContenido.add(scroll, gbc);
		    
	    add(panelContenido, BorderLayout.CENTER);
	}
	
	public void actualizarPantalla() {
		String[] titulos = {"ID Vuelo", "Origen", "Destino", "Aerolinea", "Estado", "Terminal", "Operador"};

		DefaultTableModel model = new DefaultTableModel(titulos, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= getColumnCount() - 2; // Solo Terminal y Operador editables
			}
		};

		for (Vuelo v : SkyManager.getInstance().getVuelos().values()) {
			ArrayList<Aerolinea> a = v.getAerolineas();
			String aerolineas = a.get(0).getId();
			if (a.size() == 2) aerolineas += ", " + a.get(1).getId();

			Object terminal = v.getTerminal() == null ? "Asignar" : v.getTerminal().getId();
			Object operador = (v.getControladorAsignado() == null && v.getTerminal() != null) ? "Asignar" : v.getControladorAsignado().getDni();

			Object[] fila = {
				v.getId(), 
				v.getOrigen().getCodigo()+", "+ v.getHoraSalida(), 
				v.getDestino().getCodigo()+", "+ v.getHoraLlegada(), 
				aerolineas, 
				v.getEstVuelo(), 
				terminal, 
				operador
			};
			model.addRow(fila);
		}

		tablaVuelos.setModel(model);
		tablaVuelos.getColumn("Terminal").setCellRenderer(new AsignarRenderer());
		tablaVuelos.getColumn("Terminal").setCellEditor(new AsignarEditor("terminal"));
		tablaVuelos.getColumn("Operador").setCellRenderer(new AsignarRenderer());
		tablaVuelos.getColumn("Operador").setCellEditor(new AsignarEditor("operador"));
	}
	
	private static class AsignarRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton boton = new JButton("Asignar");
		private final JLabel label = new JLabel();

		public AsignarRenderer() {
			setLayout(new BorderLayout());
			boton.setBackground(Color.LIGHT_GRAY);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		                                               boolean hasFocus, int row, int column) {
			removeAll();
			if ("Asignar".equals(value)) {
				add(boton, BorderLayout.CENTER);
			} else {
				label.setText(value != null ? value.toString() : "");
				add(label, BorderLayout.CENTER);
			}
			return this;
		}
	}

	private static class AsignarEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel = new JPanel(new BorderLayout());
		private final JButton boton = new JButton("Asignar");
		private final JLabel label = new JLabel();
		private final String tipo; // "terminal" o "operador"

		public AsignarEditor(String t) {
			tipo = t;
			boton.setBackground(Color.LIGHT_GRAY);

			boton.addActionListener(_ -> {
			    JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, boton);
			    int row = table.getEditingRow();
			    String vueloId = (String) table.getValueAt(row, 0);
			    Vuelo vuelo = SkyManager.getInstance().getVuelos().get(vueloId);

			    if ("terminal".equals(tipo)) {
			        // Mostrar terminales disponibles
			        List<Terminal> disponibles = SkyManager.getInstance().getTerminalesDisponibles(vuelo);
			        List<String> terminales = new ArrayList<>();
			        for (Terminal l: disponibles) {
			            terminales.add(l.getId());
			        }

			        if (terminales.isEmpty()) {
			            JOptionPane.showMessageDialog(null, "No hay terminales disponibles.");
			            fireEditingCanceled();
			            return;
			        }

			        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una terminal:", "Asignar Terminal", 
			                JOptionPane.QUESTION_MESSAGE, null, terminales.toArray(), terminales.get(0));

			        if (seleccion != null) {
			            vuelo.asignarTerminal(SkyManager.getInstance().getTerminales().get(seleccion));
			            JOptionPane.showMessageDialog(null, "Terminal asignada: " + seleccion);

			            // ACTUALIZAR CELDA
			            table.setValueAt(seleccion, row, table.getColumn("Terminal").getModelIndex());
			            table.repaint();
			            fireEditingStopped();
			        } else {
			            fireEditingCanceled(); // Usuario canceló
			        }
			    } 
			    else if ("operador".equals(tipo)) {
			        if (vuelo.getTerminal() == null) {
			            JOptionPane.showMessageDialog(null, "Debe asignar una terminal antes de asignar un operador.");
			            fireEditingCanceled();
			            return;
			        }

			        List<Controlador> disponibles = vuelo.getTerminal().getControladores();
			        List<String> controladores = new ArrayList<>();
			        for (Controlador c: disponibles) {
			            controladores.add(c.getDni());
			        }

			        if (disponibles == null || disponibles.isEmpty()) {
			            JOptionPane.showMessageDialog(null, "No hay controladores disponibles en la terminal asignada.");
			            fireEditingCanceled();
			            return;
			        }

			        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un controlador:", "Asignar Operador",
			            JOptionPane.QUESTION_MESSAGE, null, controladores.toArray(), controladores.get(0));

			        if (seleccion != null) {
			            vuelo.setControladorAsignado((Controlador)SkyManager.getInstance().getUsuarios().get(seleccion));
			            JOptionPane.showMessageDialog(null, "Controlador asignado: " + seleccion);

			            // ACTUALIZAR CELDA
			            table.setValueAt(seleccion, row, table.getColumn("Operador").getModelIndex());
			            table.repaint();
			            fireEditingStopped();
			        } else {
			            fireEditingCanceled();
			        }
			    }
			});

		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			panel.removeAll();
			if ("Asignar".equals(value)) {
				panel.add(boton, BorderLayout.CENTER);
			} else {
				label.setText(value != null ? value.toString() : "");
				panel.add(label, BorderLayout.CENTER);
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
		Aplicacion.getInstance().showGestorInicio();
	}

}
