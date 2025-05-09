package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import aerolineas.Aerolinea;
import aviones.Avion;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;

/**
 * Panel que permite a un operador gestionar los aviones asociados
 * a su aerolínea. Muestra una tabla con la información de cada avión
 * y ofrece opciones para añadir un nuevo avión o un nuevo tipo de avión.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class OperadorGestionAviones extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton nuevoAvion, nuevoTipoAv;
	private JTable tablaAviones;

	/**
	 * Constructor del panel de gestión de aviones.
	 * Inicializa la interfaz gráfica y sus componentes.
	 */
	public OperadorGestionAviones() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        
        // Contenedor en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());
		add(panelSuperiorIzquierdo, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridBagLayout());
		panelContenido.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Título
		JLabel titulo = new JLabel("Gestión de Aviones");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(new Color(70, 130, 180));
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc);

		// Tabla de aviones
		tablaAviones = new JTable();
		tablaAviones.setBackground(Color.WHITE);
		tablaAviones.setForeground(Color.BLACK);
		tablaAviones.setGridColor(new Color(75, 135, 185));
		tablaAviones.getTableHeader().setBackground(new Color(70, 130, 180));
		tablaAviones.getTableHeader().setForeground(Color.WHITE);
		tablaAviones.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tablaAviones.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		tablaAviones.setRowHeight(25);

		JScrollPane scroll = new JScrollPane(tablaAviones);
		scroll.setPreferredSize(new Dimension(500, 150));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelContenido.add(scroll, gbc);

		// Botones de acción
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;

		nuevoAvion = new JButton("Nuevo Avión");
		gbc.gridx = 0;
		gbc.gridy = 2;
		formatoBotones(nuevoAvion, 200, 50);
		panelContenido.add(nuevoAvion, gbc);

		nuevoTipoAv = new JButton("Nuevo Tipo Avión");
		gbc.gridx = 1;
		gbc.gridy = 2;
		formatoBotones(nuevoTipoAv, 200, 50);
		panelContenido.add(nuevoTipoAv, gbc);

		add(panelContenido, BorderLayout.CENTER);
	}

	/**
	 * Actualiza el contenido de la tabla con la información
	 * de los aviones pertenecientes a la aerolínea del operador actual.
	 */
	public void actualizarPantalla() {
		String[] columnas = {"Matrícula", "Fecha de Compra", "Última Revisión", "Modelo", "Tipo", "Estado"};
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Avion> aviones = a.getAviones().values();

		// Crear el modelo de tabla personalizado
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda es editable
                return false;
            }
        };
        
        // Rellenar el modelo con los datos de los aviones
        for (Avion avion: aviones) {
            String tipo = null;
            if (avion.getTipoAvion().isMercancias()) {
            	tipo = "Mercancías";
            } 
            else { tipo = "Pasajeros"; }
        
	        model.addRow(new Object[]{
	        		avion.getMatricula(),
	                avion.getFechaCompra(),
	                avion.getFechaUltimaRevision(),
	                avion.getTipoAvion().getModelo(),
	                tipo,
	                avion.getEstadoAvion()
	        });
        }

		tablaAviones.setModel(model);
	}

	/**
	 * Vuelve a la pantalla anterior guardando los datos.
	 */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}

	/**
	 * Aplica formato estético a los botones de acción.
	 * 
	 * @param boton Botón al que aplicar formato.
	 * @param ancho Ancho deseado.
	 * @param alto Alto deseado.
	 */
	private void formatoBotones(JButton boton, int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(5, 10, 20)); 
		boton.setFocusPainted(false);
		boton.setFont(new Font("SansSerif", Font.BOLD, 16));
		boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}

	/**
	 * Asigna un ActionListener común a los botones del panel.
	 * 
	 * @param c Controlador de eventos.
	 */
	public void setControlador(ActionListener c) {
		nuevoAvion.addActionListener(c);
		nuevoTipoAv.addActionListener(c);
	}
}
