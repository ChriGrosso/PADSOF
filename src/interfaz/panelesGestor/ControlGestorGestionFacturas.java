package interfaz.panelesGestor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import facturas.Factura;
import sistema.SkyManager;
import usuarios.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControlGestorGestionFacturas {

    private GestorGestionFacturas vista;
    private SkyManager sistema;

    public ControlGestorGestionFacturas(GestorGestionFacturas vista) {
        this.vista = vista;
        this.sistema = SkyManager.getInstance();

        inicializarEventos();
        cargarFacturasTabla();
    }

    private void inicializarEventos() {
        vista.getBotonGenerarFactura().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioGeneracion();
            }
        });
    }

    private void cargarFacturasTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);
        ArrayList<Factura> facturas = new ArrayList<>(sistema.getFacturas().values());

        for (Factura f : facturas) {
            modelo.addRow(new Object[]{
                f.getId(),
                f.getAerolinea().getNombre(),
                String.format("%.2f", f.getTotal()),
                f.getFechaEmision().toString(),
                f.isPagado() ? "Sí" : "No"
            });
        }
    }

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
