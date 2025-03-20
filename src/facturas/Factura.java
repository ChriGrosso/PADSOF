package facturas;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import es.uam.eps.padsof.invoices.*;


public class Factura {
    private String id;
    private double total;
    private Date fechaEmision;
    private boolean pagado;
    private static final String RUTA_FACTURAS = "./facturas/";  // Directorio de salida

    public Factura(String id, double total, Date fechaEmision) {
        this.id = id;
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.pagado = false;
    }

    // Método para generar la factura en PDF usando `InvoiceSystem.createInvoice()`
    public boolean generarFactura(String aerolinea, double precioBase, double recargo, List<IResourceUsageInfo> recursos) {
        try {
            // Asegurar que la carpeta de facturas existe
            File carpetaFacturas = new File(RUTA_FACTURAS);
            if (!carpetaFacturas.exists()) {
                boolean creada = carpetaFacturas.mkdirs();
                if (!creada) {
                    System.out.println("❌ No se pudo crear la carpeta en: " + RUTA_FACTURAS);
                    return false;
                }
            }
            
            // Obtener la ruta absoluta sin `.` innecesario
            String rutaAbsolutaFacturas = carpetaFacturas.getCanonicalPath();
            System.out.println("📂 Ruta corregida de facturas: " + rutaAbsolutaFacturas);
            
            DatosFactura facturaInfo = new DatosFactura(id, aerolinea, fechaEmision, precioBase, recargo, recursos);
            InvoiceSystem.createInvoice(facturaInfo, rutaAbsolutaFacturas);
            
            System.out.println("✅ Factura generada correctamente en " + rutaAbsolutaFacturas);
            return true;

        } catch (NonExistentFileException e) {
            System.out.println("❌ Error: La carpeta de salida no existe.");
        } catch (UnsupportedImageTypeException e) {
            System.out.println("❌ Error: Tipo de imagen no soportado en el logo.");
        } catch (IOException e) {
            System.out.println("❌ Error al obtener la ruta de la carpeta: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public String getId() { return id; }
    public double getTotal() { return total; }
    public Date getFechaEmision() { return fechaEmision; }
    public boolean isPagado() { return pagado; }
}
