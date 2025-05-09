package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
import interfaz.util.BotonVolver;
import notificaciones.Notificacion;
import sistema.SkyManager;
import usuarios.Operador;
import usuarios.Usuario;
import vuelos.Vuelo;

/**
 * Clase que gestiona la pantalla de notificaciones de la aplicación.
 * Permite a los usuarios visualizar y gestionar las notificaciones recibidas.
 * 
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class Notificaciones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable tablaNotificaciones;
	private JPanel panelInferior; 
	private JButton botonConfiguracion;
	
	/**
     * Constructor de la clase Notificaciones.
     * Configura la interfaz gráfica y los componentes necesarios para mostrar notificaciones.
     */
	public Notificaciones() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("Notificaciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(70, 130, 180));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelContenido.add(titulo, gbc);

        tablaNotificaciones = new JTable();
        tablaNotificaciones.setRowHeight(50);
        tablaNotificaciones.getTableHeader().setBackground(new Color(70, 130, 180));
	    tablaNotificaciones.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tablaNotificaciones);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelContenido.add(scrollPane, gbc);

        add(panelContenido, BorderLayout.CENTER);
        
        // Panel inferior para boton de configurar
	    panelInferior = new JPanel();
	    panelInferior.setLayout(new BorderLayout());
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
		  
		//Añadir los botones
	    // Botón "Nuevo Usuario" centrado
		botonConfiguracion = new JButton("Configurar Notificaciones");
		this.formatoBotones(botonConfiguracion);
		botonConfiguracion.addActionListener(_ -> {
			Aplicacion.getInstance().showConfiguracionNotificaciones();
			Aplicacion.getInstance().getConfiguracionNotificaciones().actualizarTablas();
		}) ;
		
	    panelInferior.add(botonConfiguracion, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setVisible(false);
    }
	
	/**
     * Actualiza la pantalla mostrando las notificaciones recibidas por el usuario actual.
     * Aplica estilos y configuración de renderizado para la tabla de notificaciones.
     */
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
        
        if (SkyManager.getInstance().getUsuarioActual().esGestor()==true) {
			panelInferior.setVisible(true);
		} else {
			panelInferior.setVisible(false);
		}
    }
	
	/**
     * Renderiza celdas de la tabla con múltiples líneas de texto.
     */
	private static class MultiLineRenderer extends JLabel implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructor del renderizador de las celdas de la tabla.
         */
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
	
	/**
	 * Clase que representa el renderizador de celdas para la columna de estado de las notificaciones.
	 * Muestra un botón "Leer" si la notificación no ha sido leída, y la etiqueta "Leído" en caso contrario.
	 * 
	 */
	private static class NotificacionRenderer extends JPanel implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final JButton botonMarcarLeido = new JButton("Leer");
        private final JLabel etiquetaLeido = new JLabel("Leído");
        
        /**
         * Constructor de la clase NotificacionRenderer.
         * Configura el diseño y los estilos de los elementos dentro de la celda.
         */
        public NotificacionRenderer() {
            setLayout(new BorderLayout());
            botonMarcarLeido.setBackground(Color.cyan);
        }
        
        /**
         * Método para renderizar la celda correspondiente a la notificación.
         * 
         * @param table Tabla en la que se muestra la celda.
         * @param value Valor que se mostrará en la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus Indica si la celda tiene el foco.
         * @param row Fila de la celda.
         * @param column Columna de la celda.
         * @return Componente renderizado que se mostrará en la celda.
         */
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
	
	/**
	 * Clase que representa el editor de celdas para la columna de estado de las notificaciones.
	 * Permite cambiar el estado de una notificación a "Leído" cuando el usuario interactúa con el botón.
	 * 
	 */
    private static class NotificacionEditor extends AbstractCellEditor implements TableCellEditor {
        private static final long serialVersionUID = 1L;
        private final JPanel panel;
        private final JButton botonMarcarLeido;
        private final JLabel etiquetaLeido;
        private final List<Notificacion> notificaciones;
        
        /**
         * Constructor de la clase NotificacionEditor.
         * 
         * @param notifs Lista de notificaciones que se pueden marcar como leídas.
         */
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
                
                String mensaje = notif.getMensaje();

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
                } else if (mensaje.contains("No hay terminales disponibles en el horario solicitado.")) {
                    String vueloId = UtilidadesNotificaciones.extraerIdVueloEnHorasAlternativas(mensaje);
                    List<LocalDateTime> fechasAlternativas = UtilidadesNotificaciones.extraerFechas(mensaje);

                    if (fechasAlternativas == null || fechasAlternativas.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron fechas alternativas.");
                    } else {
                        LocalDateTime seleccion = (LocalDateTime) JOptionPane.showInputDialog(
                            null,
                            "Selecciona una nueva fecha y hora para el vuelo:",
                            "Fechas alternativas",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            fechasAlternativas.toArray(),
                            fechasAlternativas.get(0)
                        );

                        if (seleccion != null) {
                            Vuelo vuelo = SkyManager.getInstance().getVuelos().get(vueloId);
                            boolean esLlegada = mensaje.contains("llegada");

                            LocalDateTime nuevaFecha = seleccion;

                            if (esLlegada) {
                                // Calcular diferencia entre nueva y actual llegada
                                LocalDateTime llegadaActual = vuelo.getHoraLlegada();
                                LocalDateTime salidaActual = vuelo.getHoraSalida();
                                Duration diferencia = Duration.between(llegadaActual, nuevaFecha);

                                // Aplicar nueva llegada y ajustar salida
                                vuelo.setHoraLlegada(nuevaFecha);
                                vuelo.setHoraSalida(salidaActual.plus(diferencia));
                            } else {
                                // Calcular diferencia entre nueva y actual salida
                                LocalDateTime salidaActual = vuelo.getHoraSalida();
                                LocalDateTime llegadaActual = vuelo.getHoraLlegada();
                                Duration diferencia = Duration.between(salidaActual, nuevaFecha);

                                // Aplicar nueva salida y ajustar llegada
                                vuelo.setHoraSalida(nuevaFecha);
                                vuelo.setHoraLlegada(llegadaActual.plus(diferencia));
                            }
                             vuelo.setTerminal(null);
                            JOptionPane.showMessageDialog(null, "La fecha y hora del vuelo se han actualizado con éxito.");
                        }
                    }
                }

                notif.setLeida(true);
                Aplicacion.getInstance().getNotificaciones().actualizarPantalla();
                fireEditingStopped();
            });
        }
        
        /**
         * Método que devuelve el componente editor de la celda de la tabla.
         * 
         * @param table Tabla en la que se muestra la celda.
         * @param value Valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row Fila de la celda.
         * @param column Columna de la celda.
         * @return Componente que se usará como editor de la celda.
         */
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
        
        /**
         * Método que devuelve el valor de la celda después de la edición.
         * 
         * @return Valor de la celda (en este caso, null porque el estado se gestiona directamente en la tabla).
         */
        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
    
    /**
     * Retroce a la pagina establecida como la anterior para este panel.
     */
	public void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getNotificaciones().setVisible(false);
		if (SkyManager.getInstance().getUsuarioActual().esGestor()==true) {
			Aplicacion.getInstance().showGestorInicio();
		} else if (SkyManager.getInstance().getUsuarioActual().esControlador()==true) {
			Aplicacion.getInstance().showContInicio();
		} else {
			Aplicacion.getInstance().showOpInicio();
		}
	}
	
	/**
     * Aplica formato visual a los botones de la interfaz.
     */
	private void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
}
