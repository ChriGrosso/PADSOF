package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

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
import facturas.Factura;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;

/**
 * Panel que permite a un operador visualizar y gestionar las facturas
 * asociadas a su aerolínea. Muestra una tabla con facturas, su estado,
 * y permite realizar pagos directamente desde la interfaz.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class OperadorFacturas extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable tablaFacturas;

	/**
	 * Constructor del panel. Inicializa la interfaz, el diseño
	 * y la tabla de facturas.
	 */
	public OperadorFacturas() {
		// Configurar el Layout
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
				        
		// Contenedor en la esquina superior derecha
		BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras.png");
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
		JLabel titulo = new JLabel("Gestión de Facturas");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(new Color(70, 130, 180));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3; // Ocupa 3 columnas
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc);
				
		// Tabla
		tablaFacturas = new JTable(); // La tabla se actualizará dinámicamente con los datos
		tablaFacturas.setBackground(Color.WHITE);
		tablaFacturas.setForeground(Color.BLACK);
		tablaFacturas.setGridColor(new Color(75, 135, 185));
		tablaFacturas.getTableHeader().setBackground(new Color(70, 130, 180));
		tablaFacturas.getTableHeader().setForeground(Color.WHITE);
		tablaFacturas.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tablaFacturas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		tablaFacturas.setRowHeight(25); // Ajustar altura de las filas
        
		JScrollPane scroll = new JScrollPane(tablaFacturas);
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
		        
		add(panelContenido, BorderLayout.CENTER);
	}
	
	/**
	 * Actualiza los datos mostrados en la tabla con las facturas
	 * asociadas a la aerolínea del operador actualmente logueado.
	 */
	public void actualizarPantalla() {
		// Colocar los nombres de las columnas de los aviones
		String [] columnas = {"ID", "Fecha Emisión", "Total", "Estado", "Fecha Emisión", "Pagar"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Factura> facturas = SkyManager.getInstance().getFacturas().values();
		Collection<Factura> factOp = new ArrayList<Factura>();
		// Recuperar facturas de la aerolinea del usuario actual
		for(Factura f: facturas) {
			if(f.getAirline().equals(a.getNombre())) {
				factOp.add(f);
			}
		}
		
		// Crear el modelo de tabla personalizado
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Solo la última columna ("Pagar") será editable
                return column == 5;
            }
        };
        
        // Rellenar el modelo con los datos de los vuelos
        for (Factura f : factOp) {
            String pagado = null, estado = null, accion = null;
            if(f.isPagado()) {
            	pagado = String.valueOf(f.getFechaPago()); 
            	estado = "PAGADA";
            	accion = "Pagada";
            } else { 
            	pagado = ""; 
            	estado = "PENDIENTE";
            	accion = "Pagar";
            }
        
	        model.addRow(new Object[]{
	        		f.getId(),
	        		String.valueOf(f.getFechaEmision()),
	                f.getTotal() + " €",
	                estado, 
	                pagado,
	                accion
	        });
        }

        tablaFacturas.setModel(model);
        
        // Editor y Renderizador para la última columna (botón)
        tablaFacturas.getColumn("Pagar").setCellEditor(new PagadoEditor());
        tablaFacturas.getColumn("Pagar").setCellRenderer(new PagadoRenderer());
	}
	
	/**
	 * Renderizador para la columna "Pagar", que muestra un botón o una etiqueta.
	 */
	private static class PagadoRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final JButton botonPagar = new JButton("Pagar");
		private final JLabel pagado = new JLabel("Pagado");

	    public PagadoRenderer() {
	        setLayout(new BorderLayout());
	        setBackground(Color.WHITE);
	        botonPagar.setBackground(Color.cyan);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                   boolean hasFocus, int row, int column) {
	    	removeAll();
	        if (value != null && "Pagar".equals(value.toString())) {
	            add(botonPagar, BorderLayout.CENTER);
	        } else {
	        	add(pagado, BorderLayout.CENTER);
	        }
	        return this;
	    }
	}
    
	/**
	 * Editor personalizado para la columna "Pagar" que gestiona la acción de pago.
	 */
    private static class PagadoEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JPanel panel;
		private final JButton botonPagar;
		private final JLabel pagado;

        public PagadoEditor() {
            this.panel = new JPanel(new BorderLayout());
            this.botonPagar = new JButton("Pagar");
            this.pagado = new JLabel("Pagado");

            botonPagar.setBackground(Color.cyan);
	        panel.setBackground(Color.WHITE);

            botonPagar.addActionListener(_ -> {
            	JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, botonPagar);
                int row = table.getEditingRow();
                String factId = (String) table.getValueAt(row, 0);
                Factura fact = SkyManager.getInstance().getFacturas().get(factId);
            	// Mostrar diálogo para seleccionar la aerolínea secundaria
                String cardNum = JOptionPane.showInputDialog(null, "Ingrese el número de tarjeta para pagar esta factura:");

                if (cardNum == null || cardNum.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se ingresó ningún número de tarjeta.");
                    return;
                }
                // Redirigir System.err temporalmente a un stream de memoria
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                PrintStream originalErr = System.err;
                System.setErr(ps);

                boolean res = fact.pagar(cardNum);

                // Restaurar System.err original
                System.err.flush();
                System.setErr(originalErr);

                if (!res) {
                    String errorMsg = baos.toString();
                    JOptionPane.showMessageDialog(Aplicacion.getInstance().getOpFacturas(), errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Actualizar la tabla
                ((OperadorFacturas) SwingUtilities.getAncestorOfClass(OperadorFacturas.class, table)).actualizarPantalla();
                fireEditingStopped();
            });
        }
			@Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				panel.removeAll();
			    if (value != null && "Pagar".equals(value.toString())) {
			        panel.add(botonPagar, BorderLayout.CENTER);
			    } else {
			    	panel.add(pagado, BorderLayout.CENTER);
			    }
			    return panel;
            }
			@Override
			public Object getCellEditorValue() {
				return null; // El valor no cambia directamente desde el editor
			}
    }

    /**
	 * Método para regresar a la pantalla anterior guardando los datos.
	 */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpFacturas().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}

}
