
package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import facturas.*;
import elementos.*;
import es.uam.eps.padsof.invoices.*;
import aerolineas.*;
import java.time.*;
import aviones.*;
import sistema.SkyManager;

public class FacturaTest {

	@Test
    void testCrearFacturaYUso() {
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");

        SkyManager manager = SkyManager.getInstance();
        manager.setCosteBaseSalida(100.0);
        manager.setCosteExtraPasajeros(20.0);

        AvionPasajeros tipo = new AvionPasajeros("Boeing", "737", 5000, 12, 35, 40, 180);
        Avion avion = new Avion("AB123", LocalDate.now().minusYears(2), tipo);

        Finger finger = new Finger("F001", 30.0, LocalDate.now(), 22.0);

        // Uso spostato al mese precedente
        LocalDateTime usoInicio = LocalDateTime.now().minusMonths(1).withDayOfMonth(10);
        LocalDateTime usoFine = usoInicio.plusHours(1);
        Uso uso = new Uso(usoInicio, finger, avion);
        uso.setHoraDesuso(usoFine);

        Factura factura = new Factura("INV-001", 0.0, 0.0, LocalDate.now(), aerolinea, ".\\resources\\logo.png");
        factura.addUso(uso);
        factura.calcularFactura(aerolinea);

        double expected = 50.0;
        assertEquals(expected, factura.getPrice(), 0.001, "Il prezzo totale della fattura non è corretto.");
    }

    @Test
    void testCreateInvoiceSuccess() {
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-001", 100.0, 120.0, LocalDate.now(), aerolinea, ".\\resources\\logo.png");
        assertDoesNotThrow(() -> {
            factura.generarFactura("./resources/invoice_test.pdf");
        });
    }

    @Test
    void testCreateInvoiceFileNotFound() {
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-002", 100.0, 120.0, LocalDate.now(), aerolinea, ".\\resources\\logo.png");

        String invalidPath = "/invalid/directory/invoice.pdf";

        assertThrows(NonExistentFileException.class, () -> {
            factura.generarFactura(invalidPath);
        }, "La carpeta de destino no existe.");
    }

    @Test
    void testCreateInvoiceSimulatedUnsupportedImage() {
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-003", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.unsupported");

        assertThrows(UnsupportedImageTypeException.class, () -> {
            factura.generarFactura("./resources/invoice_test.pdf");
        }, "El tipo de imagen no es compatible.");
    }

    @Test
    void testGetInvoiceData() {
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-003", 200.0, 250.0, LocalDate.now(), aerolinea, ".\\resources\\logo.png");

        assertEquals("Sky Airlines", factura.getAirline());
        assertEquals("SkyManager", factura.getCompanyName());
        assertEquals(200.0, factura.getBasePrice());
        assertEquals("INV-003", factura.getInvoiceIdentifier());
        assertTrue(factura.getInvoiceDate().matches("\\d{2}/\\d{2}/\\d{4}"));
    }
}


