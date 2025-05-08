package interfaz.panelesGestor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import interfaz.util.NonEditableTableModel;
import sistema.SkyManager;

import java.awt.*;

/**
 * Panel de gestión de facturas del sistema.
 * Permite al gestor visualizar las facturas existentes y generar nuevas
 * facturas mensuales para aerolíneas registradas.
 * <p>
 * Incluye una tabla de visualización y un botón para la generación de facturas.
 * </p>
 * 
 * @author Christian Grosso
 * @email christian.grosso@estudiante.uam.es
 */
public class GestorGestionFacturas extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel panelPrincipal;
    private JTable tablaFacturas;
    private JScrollPane scrollTabla;
    private BotonVolver botonVolver;
    private JButton botonGenerarFactura;
    private JLabel labelTitulo;
    private NonEditableTableModel modeloTabla;

    /**
     * Constructor principal. Inicializa el panel con el layout, los componentes
     * visuales y la tabla de facturas.
     */
    public GestorGestionFacturas() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 230, 255));

        // Panel superior con botón "Volver" e etichetta
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(200, 230, 255));
        panelSuperior.setBorder(new EmptyBorder(20, 20, 20, 20));

        botonVolver = new BotonVolver("resources/atras_icon.png");
        panelSuperior.add(botonVolver, BorderLayout.WEST);
        botonVolver.setControladorVolver(_ -> {
            SkyManager.getInstance().guardarDatos();
            Aplicacion.getInstance().showGestorInicio();
        });

        labelTitulo = new JLabel("Gestión de Facturas");
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        panelSuperior.add(labelTitulo, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con tabla
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 40, 20, 40));
        panelPrincipal.setBackground(new Color(200, 230, 255));

        String[] columnas = {"ID", "Aerolinea", "Importe (€)", "Fecha", "Pagado"};
        modeloTabla = new NonEditableTableModel(columnas, 0);
        tablaFacturas = new JTable(modeloTabla);
        personalizarTabla(tablaFacturas);
        scrollTabla = new JScrollPane(tablaFacturas);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Botón Generar Factura
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(200, 230, 255));
        botonGenerarFactura = new JButton("Generar Factura");
        personalizarBoton(botonGenerarFactura);
        panelBoton.add(botonGenerarFactura);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    public JTable getTablaFacturas() {
        return tablaFacturas;
    }

    public BotonVolver getBotonVolver() {
        return botonVolver;
    }

    public JButton getBotonGenerarFactura() {
        return botonGenerarFactura;
    }

    public NonEditableTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    private void personalizarBoton(JButton boton) {
        boton.setBackground(new java.awt.Color(135, 206, 250)); // Azzurrino chiaro (stile GestionUsuarios)
        boton.setForeground(java.awt.Color.BLACK);               // Testo nero
        boton.setFocusPainted(false);                             // Nessun bordo blu di focus
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Font coerente con il resto
        boton.setPreferredSize(new java.awt.Dimension(160, 50));           // Dimensioni uniformi
    }

	public void setControlador(ControlGestorGestionFacturas controlGestorGestionFacturas) {
		// TODO Auto-generated method stub
		
	}
	
	private void personalizarTabla(JTable tabla) {
	    tabla.setBackground(Color.WHITE);
	    tabla.setForeground(Color.BLACK);
	    tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    tabla.setRowHeight(25);
	    tabla.setGridColor(new Color(75, 135, 185));
	    tabla.getTableHeader().setBackground(new Color(70, 130, 180));
	    tabla.getTableHeader().setForeground(Color.WHITE);
	    tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
	}
}
