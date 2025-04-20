package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import aerolineas.Aerolinea;
import aviones.Avion;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;
import vuelos.Vuelo;

public class OperadorGestionVuelos extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoVuelo;
	private JTable tablaVuelos;
	
	public OperadorGestionVuelos() {
		// Configurar el Layout
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		        
		// Contenedor en la esquina superior derecha
		BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
		panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

		// Añadir el contenedor al panel principal
		add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		        
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridBagLayout());
		panelContenido.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;

	    // Título
		JLabel titulo = new JLabel("Gestión de Vuelos");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3; // Ocupa 3 columnas
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc);
		
		// Tabla
        tablaVuelos = new JTable(); // La tabla se actualizará dinámicamente con los datos
        tablaVuelos.setRowHeight(30); // Ajustar altura de las filas
        JScrollPane scrollPane = new JScrollPane(tablaVuelos);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todo el ancho
        gbc.fill = GridBagConstraints.BOTH; // Expandir horizontal y verticalmente
        gbc.weightx = 1.0; // Permitir expansión horizontal
        gbc.weighty = 1.0; // Permitir expansión vertical
        panelContenido.add(scrollPane, gbc);

        // Botones debajo de la tabla
        gbc.gridwidth = 1; // Cada botón ocupa una columna
        gbc.fill = GridBagConstraints.NONE; // Tamaño por defecto
        gbc.weightx = 0; // No expandir botones horizontalmente
        gbc.weighty = 0; // No expandir botones verticalmente

        nuevoVuelo = new JButton("Nuevo Vuelo");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelContenido.add(nuevoVuelo, gbc);
        
        add(panelContenido, BorderLayout.CENTER);
	}
	

	public void actualizarPantalla() {
		// Colocar los nombres de las columnas de los aviones
		String [] columnas = {"ID", "Origen", "Destino", "Avión", "Estado", "Compartido"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Vuelo> vuelos = a.getVuelos();
		
		// Crear el modelo de tabla personalizado
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la última columna ("Compartido") será editable
                return column == 5;
            }
        };
        
        // Rellenar el modelo con los datos de los vuelos
        for (Vuelo vuelo : vuelos) {
            String compartido = null;
            if (vuelo.getCompartido()) {
            	Aerolinea aux = null;
            	for(Aerolinea ae: vuelo.getAerolineas()) {
            		if(!a.equals(ae)) {
            			aux = ae;
            		}
            	}
            	compartido = "Compartido con " + aux.getNombre();
            } else {
            	compartido = "No compartido";
            }
        
	        model.addRow(new Object[]{
	        		vuelo.getId(),
	                vuelo.getOrigen().getCiudadMasCercana() + " (" + vuelo.getOrigen().getDiferenciaHoraria() + ")",
	                vuelo.getDestino().getCiudadMasCercana() + " (" + vuelo.getDestino().getDiferenciaHoraria() + ")",
	                vuelo.getAvion().getMatricula(),
	                vuelo.getEstVuelo(),
	                compartido
	        });
        }
        tablaVuelos.setModel(model);

        // Renderizador y editor para la última columna (botón o mensaje)
        tablaVuelos.getColumn("Compartido").setCellRenderer(new CompartidoRenderer());
        tablaVuelos.getColumn("Compartido").setCellEditor(new CompartidoEditor(a));
	}
	
	 // Renderizador personalizado para la columna "Compartido"
    private static class CompartidoRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value; // Renderizar el botón directamente
            }
            // Renderizar el texto (mensaje)
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    
 // Editor personalizado para la columna "Compartido"
    private static class CompartidoEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final Aerolinea aerolinea; // Aerolínea actual
        private final JPanel panel;
        private final JButton botonCompartir;
        private final JLabel mensaje;

        public CompartidoEditor(Aerolinea aerolinea) {
            this.aerolinea = aerolinea;
            this.panel = new JPanel(new BorderLayout());
            this.botonCompartir = new JButton("Compartir");
            this.mensaje = new JLabel();

            botonCompartir.setBackground(Color.BLUE);
            botonCompartir.setForeground(Color.WHITE);
            botonCompartir.addActionListener(e -> {
            // Acción para compartir el vuelo
            int row = ((JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonCompartir)).getSelectedRow();
            String vueloId = (String) ((JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonCompartir)).getValueAt(row, 0);
            compartirVuelo(vueloId); // Lógica de compartir
            fireEditingStopped(); // Notificar que terminó de editar
        });}
            private void compartirVuelo(String vueloId) {
            	// Implementa la lógica para compartir el vuelo
                System.out.println("Compartiendo vuelo con ID: " + vueloId);
                JOptionPane.showMessageDialog(null, "El vuelo con ID " + vueloId + " ahora es compartido.");
		    }
			@Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                panel.removeAll(); // Limpiar contenido previo
                if (value.toString().startsWith("Compartido")) {
                    // Si está compartido, mostrar el mensaje
                    mensaje.setText(value.toString());
                    panel.add(mensaje, BorderLayout.CENTER);
                } else {
                    // Si no está compartido, mostrar el botón
                    panel.add(botonCompartir, BorderLayout.CENTER);
                }
                return panel;
            }
			@Override
			public Object getCellEditorValue() {
				return null; // El valor no cambia directamente desde el editor
			}
    }
	
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpVuelos().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}
	
	
	public void setControlador(ActionListener c) {
		nuevoVuelo.addActionListener(c);
	}

}
