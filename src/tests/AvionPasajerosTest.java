package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aviones.AvionPasajeros;

class AvionPasajerosTest {
	private AvionPasajeros p1;
	
	@BeforeEach 
	void setUp() throws Exception { 
		p1 = new AvionPasajeros("Airbus", "A320-200", 3717.83, 11.8, 34.12, 37.6, 173);
	}
	

	@Test
	void testGetNumPlazas() {
		assertEquals(173, p1.getNumPlazas());
	}

	@Test
	void testGetMarca() {
		assertEquals("Airbus", p1.getMarca());
	}

	@Test
	void testGetModelo() {
		assertEquals("A320-200", p1.getModelo());
	}

	@Test
	void testGetAutonomia() {
		assertEquals(3717.83, p1.getAutonomia());
	}

	@Test
	void testGetAltura() {
		assertEquals(11.80, p1.getAltura());
	}

	@Test
	void testGetAnchura() {
		assertEquals(34.12, p1.getAnchura());
	}

	@Test
	void testGetLargo() {
		assertEquals(37.60, p1.getLargo());
	}

}
