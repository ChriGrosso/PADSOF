package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import interfaz.util.BotonVolver;
import interfaz.util.MultiLineCellRenderer;
import sistema.SkyManager;
import usuarios.Operador;
import vuelos.EstadoVuelo;
import vuelos.PeticionCompartir;
import vuelos.Vuelo;

/**
 * Panel de interfaz gráfica que permite al operador gestionar los vuelos
 * de su aerolínea. Muestra una tabla con los vuelos actuales, su estado,
 * y permite compartir vuelos o modificar su estado a través de botones interactivos.
 * 
 * Incluye funcionalidad para actualizar automáticamente los datos y mostrar
 * diálogos de confirmación e interacción con el usuario.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class OperadorGestionVuelos extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoVuelo;
	private JTable tablaVuelos;
	
	/**
	 * Constructor del panel de gestión de vuelos.
	 * Inicializa y organiza los elementos gráficos y la tabla.
	 */
	public OperadorGestionVuelos() {
		// Configurar el Layout
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		        
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
	    titulo.setForeground(new Color(70, 130, 180));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3; // Ocupa 3 columnas
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc);
		
		// Tabla
        tablaVuelos = new JTable(); // La tabla se actualizará dinámicamente con los datos
        tablaVuelos.setBackground(Color.WHITE);
		tablaVuelos.setForeground(Color.BLACK);
		tablaVuelos.setGridColor(new Color(75, 135, 185));
		tablaVuelos.getTableHeader().setBackground(new Color(70, 130, 180));
		tablaVuelos.getTableHeader().setForeground(Color.WHITE);
		tablaVuelos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaVuelos.setRowHeight(50); // Ajustar altura de las filas
        
        JScrollPane scroll = new JScrollPane(tablaVuelos);
        scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
	    add(scroll, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todo el ancho
        gbc.fill = GridBagConstraints.BOTH; // Expandir horizontal y verticalmente
        gbc.weightx = 1.0; // Permitir expansión horizontal
        gbc.weighty = 1.0; // Permitir expansión vertical
        panelContenido.add(scroll, gbc);

        // Botones debajo de la tabla
        gbc.gridwidth = 1; // Cada botón ocupa una columna
        gbc.fill = GridBagConstraints.NONE; // Tamaño por defecto
        gbc.weightx = 0; // No expandir botones horizontalmente
        gbc.weighty = 0; // No expandir botones verticalmente

        nuevoVuelo = new JButton("Nuevo Vuelo");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formatoBotones(nuevoVuelo, 200, 50);
        panelContenido.add(nuevoVuelo, gbc);
        
        add(panelContenido, BorderLayout.CENTER);
	}
	
	/**
	 * Actualiza la tabla con los vuelos actuales de la aerolínea del operador.
	 * Muestra información sobre origen, destino, avión, estado y si el vuelo es compartido.
	 * También permite compartir vuelos o cambiar su estado si corresponde.
	 */
	public void actualizarPantalla() {
		// Colocar los nombres de las columnas de los aviones
		String [] columnas = {"ID", "Origen", "Destino", "Avión", "Estado", "Compartido", "Compartir", "Cambiar Estado"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Vuelo> vuelos = a.getVuelos();
		
		// Crear el modelo de tabla personalizado
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Solo las últimas columnas ("Compartir" y "Cambiar Estado") será editable
                return column == 6 || column == 7;
            }
        };
        
        // Rellenar el modelo con los datos de los vuelos
        for (Vuelo vuelo : vuelos) {
            String compartido = null, compartirAccion = null, cambiarEst = null;
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
            } 
            else {
            	if(vuelo.getPetComp() == PeticionCompartir.PETICION_ENVIADA) { 
            		compartido = "Petición para\n compartir enviada";
            		compartirAccion = "No disponible";
            	}
            	else { 
            		compartido = "No compartido"; 
            		compartirAccion = "Compartir";
            	}
	            if(vuelo.getHoraLlegadaEfectiva() == null) { cambiarEst = "Cambiar Estado"; }
	            else { cambiarEst = "Vuelo Finalizado"; }
            }
       
            model.addRow(new Object[]{
	        		vuelo.getId(),
	                vuelo.getOrigen().getCiudadMasCercana() + " (" + vuelo.getOrigen().getDiferenciaHoraria() + ") " + vuelo.getHoraSalida() + "\n" + vuelo.getOrigen().getNombre(),
	                vuelo.getDestino().getCiudadMasCercana() + " (" + vuelo.getDestino().getDiferenciaHoraria() + ") " + vuelo.getHoraLlegada() + "\n" + vuelo.getDestino().getNombre(),
	                vuelo.getAvion().getMatricula(),
	                vuelo.getEstVuelo(),
	                compartido,
	                compartirAccion,
	                cambiarEst
	        });
        }
        tablaVuelos.setModel(model);
        tablaVuelos.getColumnModel().getColumn(0).setMinWidth(70);  // ID
        tablaVuelos.getColumnModel().getColumn(0).setMaxWidth(70);  
        tablaVuelos.getColumnModel().getColumn(3).setMinWidth(70);  // Avión
        tablaVuelos.getColumnModel().getColumn(3).setMaxWidth(70);
        tablaVuelos.getColumnModel().getColumn(4).setMinWidth(150);  // Estado
        tablaVuelos.getColumnModel().getColumn(4).setMaxWidth(150);
        tablaVuelos.getColumnModel().getColumn(5).setMinWidth(240); // Compartido
        tablaVuelos.getColumnModel().getColumn(5).setMaxWidth(240); 
        tablaVuelos.getColumnModel().getColumn(6).setMinWidth(95);  // Botón Compartir
        tablaVuelos.getColumnModel().getColumn(6).setMaxWidth(95); 
        tablaVuelos.getColumnModel().getColumn(7).setMinWidth(95);  // Botón Modificar Est.
        tablaVuelos.getColumnModel().getColumn(7).setMaxWidth(95);
        tablaVuelos.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer()); // Origen
        tablaVuelos.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer()); // Destino
        tablaVuelos.getColumnModel().getColumn(7).setCellRenderer(new MultiLineCellRenderer()); // Botón Modificar Est.

        // Editor y Renderizador para las últimas columnas (botones)
        tablaVuelos.getColumn("Compartir").setCellEditor(new CompartidoEditor());
        tablaVuelos.getColumn("Compartir").setCellRenderer(new CompartidoRenderer());
        tablaVuelos.getColumn("Cambiar Estado").setCellEditor(new ModificarEstEditor());
        tablaVuelos.getColumn("Cambiar Estado").setCellRenderer(new ModificarEstRenderer());
	}
	
	/**
	 * Renderizador personalizado para mostrar un botón de compartir o un mensaje de estado
	 * en la columna "Compartir" de la tabla de vuelos.
	 */
	private static class CompartidoRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton botonCompartir = new JButton("Compartir");
	    private final JLabel mensaje = new JLabel();

	    public CompartidoRenderer() {
	    	setBackground(Color.WHITE);
	        setLayout(new BorderLayout());
	        setAlignmentX(Component.CENTER_ALIGNMENT);
	        botonCompartir.setBackground(Color.cyan);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                   boolean hasFocus, int row, int column) {
	    	removeAll();
	        if (value != null && "Compartir".equals(value.toString())) {
	            add(botonCompartir, BorderLayout.CENTER);
	        } else {
	            mensaje.setText("No disponible");
	            add(mensaje, BorderLayout.CENTER);
	        }
	        return this;
	    }
	}
    
	/**
	 * Editor personalizado que permite al operador solicitar compartir un vuelo
	 * con otra aerolínea a través de una entrada de texto.
	 */
    private static class CompartidoEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel;
        private final JButton botonCompartir;
        private final JLabel mensaje;
        private Aerolinea aeSec; // Aerolínea secundaria seleccionada

        public CompartidoEditor() {
            this.panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
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
			        mensaje.setText("No disponible");
			        panel.add(mensaje, BorderLayout.CENTER);
			    }
			    return panel;
            }
			@Override
			public Object getCellEditorValue() {
				return null; // El valor no cambia directamente desde el editor
			}
    }
    
    /**
     * Renderizador para la columna "Cambiar Estado", mostrando un botón o mensaje según el estado del vuelo.
     */
    private static class ModificarEstRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton botonModificar = new JButton("<html>Modificar<br>Estado</html>");
	    private final JLabel mensaje = new JLabel();

	    public ModificarEstRenderer() {
	    	setBackground(Color.WHITE);
	        setLayout(new BorderLayout());
	        botonModificar.setBackground(Color.cyan);
	        mensaje.setBackground(Color.WHITE);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                   boolean hasFocus, int row, int column) {
	    	removeAll();
	        if (value != null && "Cambiar Estado".equals(value.toString())) {
	            add(botonModificar, BorderLayout.CENTER);
	        } else {
	            mensaje.setText("<html>Vuelo<br>Finalizado</html>");
	            add(mensaje, BorderLayout.CENTER);
	        }
	        return this;
	    }
	}
    
    /**
     * Editor personalizado que permite al operador cambiar el estado de un vuelo
     * seleccionando una nueva opción desde un JComboBox.
     */
    private static class ModificarEstEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel;
        private final JButton botonModificar;
        private final JLabel mensaje;

        public ModificarEstEditor() {
            this.panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
            this.botonModificar = new JButton("<html>Modificar<br>Estado</html>");
            this.mensaje = new JLabel();

            botonModificar.setBackground(Color.cyan);

            botonModificar.addActionListener(_ -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonModificar);
                int row = table.getEditingRow();
                String vueloId = (String) table.getValueAt(row, 0);
                mostrarSelectorEstado(vueloId);
                fireEditingStopped();
            });
        }
        private void mostrarSelectorEstado(String vueloId) {
            Vuelo v = SkyManager.getInstance().getVuelos().get(vueloId);

            if (v.getHoraLlegadaEfectiva() != null) {
                JOptionPane.showMessageDialog(null, "El vuelo ya ha finalizado.");
                return;
            }

            // Crear el JComboBox con todos los estados posibles
            EstadoVuelo[] estados = EstadoVuelo.values();
            JComboBox<EstadoVuelo> combo = new JComboBox<>(estados);
            combo.setSelectedItem(v.getEstVuelo()); // Poner el estado actual seleccionado

            int option = JOptionPane.showConfirmDialog(
                    null,
                    combo,
                    "Selecciona el nuevo estado del vuelo",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                EstadoVuelo nuevoEstado = (EstadoVuelo) combo.getSelectedItem();
                boolean res = v.setEstVuelo(nuevoEstado);
                if (res) { JOptionPane.showMessageDialog(null, "Estado cambiado a: " + nuevoEstado); }
                else { JOptionPane.showMessageDialog(Aplicacion.getInstance().getOpVuelos(), "No se puede hacer el cambio de estados de vuelo: " + v.getEstVuelo() + " -> " + nuevoEstado, "Error", JOptionPane.ERROR_MESSAGE); }
                Aplicacion.getInstance().getOpVuelos().actualizarPantalla();
            }
        }
	        @Override
	        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				panel.removeAll();
			    if (value != null && "Cambiar Estado".equals(value.toString())) {
			        panel.add(botonModificar, BorderLayout.CENTER);
			    } else {
			        mensaje.setText("<html>Vuelo<br>Finalizado</html>");
			        panel.add(mensaje, BorderLayout.CENTER);
			    }
			    return panel;
	        }
			@Override
			public Object getCellEditorValue() {
				return null; // El valor no cambia directamente desde el editor
			}
    }
	
    /**
     * Vuelve a la pantalla de inicio del operador, guardando los datos actuales.
     */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpVuelos().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}
	
	/**
	 * Aplica formato estético a los botones del panel.
	 * 
	 * @param boton Botón al que se aplicará el formato.
	 * @param ancho Ancho deseado del botón.
	 * @param alto Alto deseado del botón.
	 */
	private void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(5, 10, 20)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 16));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	/**
	 * Asigna un ActionListener al botón de creación de nuevo vuelo.
	 * 
	 * @param c Controlador a asignar.
	 */
	public void setControlador(ActionListener c) {
		nuevoVuelo.addActionListener(c);
	}

}
