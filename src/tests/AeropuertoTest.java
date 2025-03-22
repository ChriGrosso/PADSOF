package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;

class AeropuertoTest {

	private Aeropuerto aeropuerto;
    
    @BeforeEach
    void setUp() throws Exception {
        ArrayList<Temporada> temps = new ArrayList<Temporada>(Arrays.asList(new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31)), 
                                      new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31))));
        aeropuerto = new Aeropuerto("Aeropuerto Internacional", "ABC123", "Madrid", "España", 15.5, 1, temps, Direccion.NORTE);
    }

    @Test
    void testGetNombre() {
        assertEquals("Aeropuerto Internacional", aeropuerto.getNombre());
    }

    @Test
    void testGetCodigo() {
        assertEquals("ABC123", aeropuerto.getCodigo());
    }

    @Test
    void testGetCiudadMasCercana() {
        assertEquals("Madrid", aeropuerto.getCiudadMasCercana());
    }

    @Test
    void testGetPais() {
        assertEquals("España", aeropuerto.getPais());
    }

    @Test
    void testGetDistanciaCiudadMasCercana() {
        assertEquals(15.5, aeropuerto.getDistanciaCiudadMasCercana());
    }

    @Test
    void testGetDiferenciaHoraria() {
        assertEquals(1, aeropuerto.getDiferenciaHoraria());
    }

	@Test
	void testGetTemporadas() {
		assertEquals(2, aeropuerto.getTemporadas().size());
	}

	@Test
    void testGetDireccion() {
        assertEquals(Direccion.NORTE, aeropuerto.getDireccion());
    }

    @Test
    void testSetCodigo() {
        aeropuerto.setCodigo("XYZ789");
        assertEquals("XYZ789", aeropuerto.getCodigo());
    }

    @Test
    void testSetCiudadMasCercana() {
        aeropuerto.setCiudadMasCercana("Barcelona");
        assertEquals("Barcelona", aeropuerto.getCiudadMasCercana());
    }

    @Test
    void testSetPais() {
        aeropuerto.setPais("Francia");
        assertEquals("Francia", aeropuerto.getPais());
    }

    @Test
    void testSetDistanciaCiudadMasCercana() {
        aeropuerto.setDistanciaCiudadMasCercana(20.0);
        assertEquals(20.0, aeropuerto.getDistanciaCiudadMasCercana());
    }

    @Test
    void testSetDiferenciaHoraria() {
        aeropuerto.setDiferenciaHoraria(2);
        assertEquals(2, aeropuerto.getDiferenciaHoraria());
    }

    @Test
    void testAddTemporada() {
        Temporada verano = new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31));
        aeropuerto.addTemporada(verano);
        assertTrue(aeropuerto.getTemporadas().contains(verano));
    }

    @Test
    void testSetDireccion() {
        aeropuerto.setDireccion(Direccion.SUR);
        assertEquals(Direccion.SUR, aeropuerto.getDireccion());
    }
}
