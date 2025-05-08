package interfaz.panelesGestor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import facturas.Factura;
import sistema.SkyManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controlador para el panel de gestión de facturas del gestor.
 *
 * Esta clase se encarga de conectar la vista {@link GestorGestionFacturas} con el modelo {@link SkyManager}
 * para permitir al usuario gestor visualizar y generar facturas mensuales asociadas a aerolíneas.
 * 
 * Funcionalidades principales:
 * <ul>
 *   <li>Mostrar la lista de facturas existentes en una tabla</li>
 *   <li>Permitir la generación de nuevas facturas mediante un formulario de selección de aerolínea</li>
 *   <li>Actualizar automáticamente la interfaz y guardar los datos tras cada operación</li>
 * </ul>
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class ControlGestorGestionFacturas {

    /**
     * Vista gráfica asociada a la gestión de facturas.
     */
    private GestorGestionFacturas vista;

    /**
     * Referencia al sistema principal que contiene la lógica de negocio y los datos.
     */
    private SkyManager sistema;

    /**
     * Constructor que inicializa el controlador, carga los eventos y la tabla inicial de facturas.
     * 
     * @param vista Vista del panel de gestión de facturas.
     */
    public ControlGestorGestionFacturas(GestorGestionFacturas vista) {
        this.vista = vista;
        this.sistema = SkyManager.getInstance();

        inicializarEventos();
        cargarFacturasTabla();
    }

    /**
     * Registra los eventos de la interfaz gráfica.
     * Actualmente gestiona el evento para el botón "Generar Factura".
     */
    private void inicializarEventos() {
        vista.getBotonGenerarFactura().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioGeneracion();
            }
        });
    }

    /**
     * Carga las facturas registradas en el sistema dentro de la tabla de la vista.
     * Muestra información básica: ID, nombre de la aerolínea, total, fecha y estado de pago.
     */
    private void cargarFacturasTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);
        ArrayList<Factura> facturas = new ArrayList<>(sistema.getFacturas().values());

        for (Factura f : facturas) {
            modelo.addRow(new Object[] {
                f.getId(),
                f.getAerolinea().getNombre(),
                String.format("%.2f", f.getTotal()),
                f.getFechaEmision().toString(),
                f.isPagado() ? "Sí" : "No"
            });
        }
    }

    /**
     * Muestra un cuadro de diálogo para seleccionar una aerolínea y genera una nueva factura mensual
     * utilizando el método estático de la clase {@link Factura}.
     * La nueva factura se guarda en el sistema y se actualiza la tabla.
     */
    private void mostrarFormularioGeneracion() {
        JComboBox<String> comboAerolineas = new JComboBox<>();
        for (String id : sistema.getAerolineas().keySet()) {
            comboAerolineas.addItem(id);
        }

        int opcion = JOptionPane.showConfirmDialog(vista, comboAerolineas, "Seleccione una Aerolínea", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String idAerolinea = (String) comboAerolineas.getSelectedItem();
            Factura factura = Factura.generarFacturaMensual(sistema.getAerolineas().get(idAerolinea));
            sistema.registrarFactura(factura);
            cargarFacturasTabla();
            sistema.guardarDatos();
            JOptionPane.showMessageDialog(vista, "Factura generada correctamente para " + factura.getAerolinea().getNombre());
        }
    }
}
