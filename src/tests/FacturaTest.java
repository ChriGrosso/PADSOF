package tests;

import es.uam.eps.padsof.invoices.InvoiceSystem;
import es.uam.eps.padsof.invoices.NonExistentFileException;
import es.uam.eps.padsof.invoices.UnsupportedImageTypeException;
import aerolineas.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import facturas.*;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTest {

    @TempDir
    Path tempDir; // Directory temporanea per testare la generazione della fattura

    @Test
    void testCreateInvoiceSuccess() {
        Aerolinea aerolinea = new Aerolinea("AER001","Sky Airlines");
        Factura factura = new Factura("INV-001", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.png");

        String filePath = tempDir.resolve("invoice_test.pdf").toString();

        assertDoesNotThrow(() -> InvoiceSystem.createInvoice(factura, filePath));

        File generatedFile = new File(filePath);
        assertTrue(generatedFile.exists(), "Il file della fattura non è stato creato.");
    }

    @Test
    void testCreateInvoiceFileNotFound() {
        Aerolinea aerolinea = new Aerolinea("AER001","Sky Airlines");
        Factura factura = new Factura("INV-002", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.png");

        String invalidPath = "/invalid/directory/invoice.pdf"; // Percorso non valido

        assertThrows(NonExistentFileException.class, () -> InvoiceSystem.createInvoice(factura, invalidPath));
    }

    @Test
    void testCreateInvoiceUnsupportedImageType() {
        Aerolinea aerolinea = new Aerolinea("AER001","Sky Airlines");
        Factura factura = new Factura("INV-003", 100.0, 120.0, LocalDate.now(), aerolinea, "C:/path/to/nonexistent/logo.jpg");           
        

        String filePath = tempDir.resolve("invoice_invalid.pdf").toString();

        assertThrows(UnsupportedImageTypeException.class, () -> InvoiceSystem.createInvoice(factura, filePath));
    }

    @Test
    void testGetInvoiceData() {
        Aerolinea aerolinea = new Aerolinea("AER001","Sky Airlines");
        Factura factura = new Factura("INV-004", 200.0, 250.0, LocalDate.now(), aerolinea, "logo.png");

        assertEquals("Sky Airlines", factura.getAirline());
        assertEquals("SkyManager", factura.getCompanyName());
        assertEquals(200.0, factura.getBasePrice());
        assertEquals("INV-004", factura.getInvoiceIdentifier());
        assertTrue(factura.getInvoiceDate().matches("\\d{2}/\\d{2}/\\d{4}")); // Verifica formato DD/MM/YYYY
    }
}
