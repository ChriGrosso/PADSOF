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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import aerolineas.Aerolinea;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import interfaz.elementosComunes.MultiLineCellRenderer;
import sistema.SkyManager;
import usuarios.Operador;
import vuelos.PeticionCompartir;
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
        tablaVuelos.setRowHeight(50); // Ajustar altura de las filas
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
		String [] columnas = {"ID", "Origen", "Destino", "Avión", "Estado", "Compartido", "Compartir"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Vuelo> vuelos = a.getVuelos();
		
		// Crear el modelo de tabla personalizado
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Solo la última columna ("Compartir") será editable
                return column == 6;
            }
        };
        
        // Rellenar el modelo con los datos de los vuelos
        for (Vuelo vuelo : vuelos) {
            String compartido = null, compartirAccion = null;
            if (vuelo.getCompartido()) {
            	// Encontrar la aerolinea con la que comparte
            	Aerolinea aux = null;
            	for(Aerolinea ae: vuelo.getAerolineas()) {
            		if(!a.equals(ae)) {
            			aux = ae;
            		}
            	}
            	compartido = "Compartido con " + aux.getNombre();
            	compartirAccion = "No disponible";
            } else {
            	if(vuelo.getPetComp() == PeticionCompartir.PETICION_ENVIADA) { 
            		compartido = "Petición para\n compartir enviada";
            		compartirAccion = "No disponible";
            	}
            	else { 
            		compartido = "No compartido"; 
            		compartirAccion = "Compartir";
            	}
            }
        
	        model.addRow(new Object[]{
	        		vuelo.getId(),
	                vuelo.getOrigen().getCiudadMasCercana() + " (" + vuelo.getOrigen().getDiferenciaHoraria() + ") " + vuelo.getHoraSalida() + "\n" + vuelo.getOrigen().getNombre(),
	                vuelo.getDestino().getCiudadMasCercana() + " (" + vuelo.getDestino().getDiferenciaHoraria() + ") " + vuelo.getHoraLlegada() + "\n" + vuelo.getDestino().getNombre(),
	                vuelo.getAvion().getMatricula(),
	                vuelo.getEstVuelo(),
	                compartido,
	                compartirAccion
	        });
        }
        tablaVuelos.setModel(model);
        tablaVuelos.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tablaVuelos.getColumnModel().getColumn(1).setPreferredWidth(150); // Origen
        tablaVuelos.getColumnModel().getColumn(2).setPreferredWidth(150); // Destino
        tablaVuelos.getColumnModel().getColumn(5).setPreferredWidth(150); // Compartido
        tablaVuelos.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer()); // Origen
        tablaVuelos.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer()); // Destino

        // Editor y Renderizador para la última columna (botón)
        tablaVuelos.getColumn("Compartir").setCellEditor(new CompartidoEditor());
        tablaVuelos.getColumn("Compartir").setCellRenderer(new CompartidoRenderer());
	}
	
	private static class CompartidoRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton botonCompartir = new JButton("Compartir");
	    private final JLabel mensaje = new JLabel();

	    public CompartidoRenderer() {
	        setLayout(new BorderLayout());
	        botonCompartir.setBackground(Color.cyan);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                   boolean hasFocus, int row, int column) {
	    	removeAll();
	        if (value != null && "Compartir".equals(value.toString())) {
	            add(botonCompartir, BorderLayout.CENTER);
	        } else {
	            mensaje.setText(value != null ? value.toString() : "");
	            add(mensaje, BorderLayout.CENTER);
	        }
	        return this;
	    }
	}
    
    // Editor personalizado para la columna "Compartido"
    private static class CompartidoEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel;
        private final JButton botonCompartir;
        private final JLabel mensaje;
        private Aerolinea aeSec; // Aerolínea secundaria seleccionada

        public CompartidoEditor() {
            this.panel = new JPanel(new BorderLayout());
            this.botonCompartir = new JButton("Compartir");
            this.mensaje = new JLabel();

            botonCompartir.setBackground(Color.cyan);

            botonCompartir.addActionListener(_ -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonCompartir);
                int row = table.getEditingRow();
                String vueloId = (String) table.getValueAt(row, 0);
                compartirVuelo(vueloId);
                fireEditingStopped();
            });
        }
            private void compartirVuelo(String vueloId) {
            	Vuelo v = SkyManager.getInstance().getVuelos().get(vueloId);

                // Mostrar diálogo para seleccionar la aerolínea secundaria
                String nombreAeSec = JOptionPane.showInputDialog(null, "Ingrese el nombre de la aerolínea con la que desea compartir el vuelo:");

                if (nombreAeSec == null || nombreAeSec.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se ingresó ninguna aerolínea.");
                    return;
                }

                // Buscar la aerolínea por nombre
                aeSec = SkyManager.getInstance().getAerolineas().get(nombreAeSec.trim()); 

                if (aeSec == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró la aerolínea: " + nombreAeSec);
                    return;
                }

                if (v.getCompartido() || aeSec.equals(v.getAerolinea())) {
                    JOptionPane.showMessageDialog(null, "Este vuelo ya está compartido o pertenece a la misma aerolínea.");
                    return;
                }

                // Crear mensaje de petición
                String msg = "La aerolínea " + v.getAerolinea().getNombre()
                        + " ha solicitado compartir su vuelo " + v.getId()
                        + " con su aerolínea (" + aeSec.getNombre() + ")";

                // Enviar la notificación a todos los operadores de esa aerolínea
                for (Operador op : aeSec.getOperadores()) {
                    SkyManager.getInstance().getUsuarioActual().enviarNotificacion(msg, op);
                }
                // Actualizar estado "Compartido" del vuelo a la espera de veredicto
                v.setPetComp(PeticionCompartir.PETICION_ENVIADA);

                JOptionPane.showMessageDialog(null, "Solicitud de compartir vuelo enviada a " + aeSec.getNombre());
                // Actualizar pantalla para ver los cambios inmediatamente
                Aplicacion.getInstance().getOpVuelos().actualizarPantalla();
		    }
			@Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				panel.removeAll();
			    if (value != null && "Compartir".equals(value.toString())) {
			        panel.add(botonCompartir, BorderLayout.CENTER);
			    } else {
			        mensaje.setText(value != null ? value.toString() : "");
			        panel.add(mensaje, BorderLayout.CENTER);
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
