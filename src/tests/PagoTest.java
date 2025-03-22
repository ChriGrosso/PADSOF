package tests;

import facturas.*;
import aerolineas.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PagoTest {

    private Factura creaFacturaConAerolinea(String nombreAerolinea) {
        Aerolinea aerolinea = new Aerolinea("AER001", nombreAerolinea);
        return new Factura("INV-100", 100.0, 120.0, LocalDate.now(), aerolinea, "logo.png");
    }

    @Test
    void testPagoSuccess() {
        Factura factura = creaFacturaConAerolinea("Sky Airlines");
        String cardNumber = "1234567812345678"; // numero valido

        boolean result = factura.pagar(cardNumber);
        assertTrue(result, "Il pagamento dovrebbe avere successo.");
        assertTrue(factura.isPagado(), "La fattura dovrebbe risultare pagata.");
        assertNotNull(factura.getFechaPago(), "La data di pagamento non dovrebbe essere nulla.");
    }

    @Test
    void testPagoCartaInvalida() {
        Factura factura = creaFacturaConAerolinea("Sky Airlines");
        String cardNumber = "123"; // formato non valido

        boolean result = factura.pagar(cardNumber);
        assertFalse(result, "Il pagamento con carta invalida dovrebbe fallire.");
        assertFalse(factura.isPagado(), "La fattura non dovrebbe risultare pagata.");
    }

    @Test
    void testPagoConnessioneFallita() {
        Factura factura = creaFacturaConAerolinea("Web Airlines"); // 'W' iniziale = connessione fallita
        String cardNumber = "1234567812345678";

        boolean result = factura.pagar(cardNumber);
        assertFalse(result, "Il pagamento con connessione fallita dovrebbe fallire.");
        assertFalse(factura.isPagado(), "La fattura non dovrebbe risultare pagata.");
    }

    @Test
    void testPagoOrdineRifiutato() {
        Factura factura = creaFacturaConAerolinea("Ryanair"); // 'R' iniziale = ordine rifiutato
        String cardNumber = "1234567812345678";

        boolean result = factura.pagar(cardNumber);
        assertFalse(result, "Il pagamento con ordine rifiutato dovrebbe fallire.");
        assertFalse(factura.isPagado(), "La fattura non dovrebbe risultare pagata.");
    }
}
