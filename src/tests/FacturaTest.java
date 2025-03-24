package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import facturas.*;
import elementos.*;
import es.uam.eps.padsof.invoices.*;
import aerolineas.*;
import java.time.*;

public class FacturaTest {

	@Test
	void testCrearFacturaYUso() {
	    // Creazione della aerolinea
	    Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");

	    // Creazione della fattura
	    Factura factura = new Factura("INV-001", 500.0, 750.0, LocalDate.now(), aerolinea, "logo.png");

	    // Aggiunta di un uso e sincronizzazione con rUsage
	    Pista recurso = new Pista("1", LocalDate.now(), true, 22.00, 10.35); // Usa la classe Pista (figlia concreta)
	    Uso uso = new Uso(LocalDateTime.now().minusHours(1), recurso);
	    uso.setHoraDesuso(LocalDateTime.now());

	    // Aggiungiamo l'uso alla fattura
	    factura.addUso(uso);

	    // Verifica che l'uso sia stato aggiunto correttamente a rUsage
	    assertEquals(1, factura.getResourcePrices().size(), "El uso no se ha agregado a la lista de recursos.");
	    assertEquals(uso, factura.getResourcePrices().get(0), "El uso agregado no es el esperado.");

	    // Verifica che il costo totale sia corretto
	    double expectedPrice = 500.0 + 10.35; // Base price + price from usage
	    assertEquals(expectedPrice, factura.getPrice(), "El precio total de la factura no es el esperado.");
	}


    @Test
    void testCreateInvoiceSuccess() {
        // Test di creazione fattura (successo)
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-001", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.png");
        assertDoesNotThrow(() -> {
            factura.generarFactura("./tmp/invoice_test.pdf"); // Percorso di test valido
        });
        
    }

    @Test
    void testCreateInvoiceFileNotFound() {
        // Test per percorso non valido
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-002", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.png");

        // Percorso di directory non valido
        String invalidPath = "/invalid/directory/invoice.pdf";

        // Verifica che venga lanciata l'eccezione NonExistentFileException
        assertThrows(NonExistentFileException.class, () -> {
            factura.generarFactura(invalidPath);
        }, "La carpeta de destino no existe.");
    }
    
    @Test
    void testCreateInvoiceSimulatedUnsupportedImage() {
        // Creazione di una aerolinea e una fattura con un logo non supportato
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-003", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.unsupported");

        // Simulazione di errore per immagine non supportata
        assertThrows(UnsupportedImageTypeException.class, () -> {
            factura.generarFactura("./tmp/invoice_test.pdf");
        }, "El tipo de imagen no es compatible.");
    }

    @Test
    void testGetInvoiceData() {
        // Verifica dei dati della fattura
        Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");
        Factura factura = new Factura("INV-003", 200.0, 250.0, LocalDate.now(), aerolinea, "logo.png");

        assertEquals("Sky Airlines", factura.getAirline());
        assertEquals("SkyManager", factura.getCompanyName());
        assertEquals(200.0, factura.getBasePrice());
        assertEquals("INV-003", factura.getInvoiceIdentifier());
        assertTrue(factura.getInvoiceDate().matches("\\d{2}/\\d{2}/\\d{4}")); // Verifica formato DD/MM/YYYY
    }
}

class ElementoEstructural {
    private String id;
    private double costePorHora;
    public ElementoEstructural(String id, double coste) {
        this.id = id;
        this.costePorHora = coste;
    }
    public String getId() { return id; }
    public double getCostePorHora() { return costePorHora; }
}
