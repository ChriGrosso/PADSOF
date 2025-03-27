package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import facturas.*;
import elementos.*;
import es.uam.eps.padsof.invoices.*;
import aerolineas.*;
import java.time.*;
import java.util.ArrayList;

import aeropuertos.*;
import aviones.*;

public class FacturaTest {

	@Test
	void testCrearFacturaYUso() {
	    // 1. Crea una compagnia aerea
	    Aerolinea aerolinea = new Aerolinea("AER001", "Sky Airlines");

	    // 2. Crea un aeroporto e imposta i costi
	    Aeropuerto aeropuerto = new Aeropuerto("Test Airport", "TEST", "City", "Country", 10.0, 1,
	        new ArrayList<>(), null);
	    aeropuerto.setCosteBaseSalida(100.0);          // Prezzo base per voli in partenza
	    aeropuerto.setCosteExtraPasanjeros(20.0);       // Surcharge per aerei passeggeri

	    // 3. Crea un tipo di aereo passeggeri
	    AvionPasajeros tipo = new AvionPasajeros("Boeing", "737", 5000, 12, 35, 40, 180);

	    // 4. Crea l'aereo con quel tipo
	    Avion avion = new Avion("AB123", LocalDate.now().minusYears(2), tipo);

	    // 5. Crea una risorsa strutturale (Finger)
	    Finger finger = new Finger("F001", 30.0, LocalDate.now(), 22.0); // 30€/ora

	    // 6. Crea l'oggetto Uso per 1 ora con l'aereo
	    Uso uso = new Uso(LocalDateTime.now().minusHours(1), finger, avion);
	    uso.setHoraDesuso(LocalDateTime.now());

	    // 7. Crea la fattura e aggiungi l'uso
	    Factura factura = new Factura("INV-001", 0.0, 0.0, LocalDate.now(), aerolinea, "logo.png");
	    factura.addUso(uso);

	    // 8. Calcola il totale della fattura (volo in partenza)
	    factura.calcularFactura(aerolinea, aeropuerto, true);

	    // 9. Verifica il prezzo atteso = base + surcharge + uso (1h x 30€)
	    double expected = 100.0 + 20.0 + 30.0;

	    assertEquals(expected, factura.getPrice(), 0.001, "Il prezzo totale della fattura non è corretto.");
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
