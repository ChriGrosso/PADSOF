package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aviones.AvionMercancias;

class AvionMercanciasTest {
	private AvionMercancias m1;
	
	@BeforeEach 
	void setUp() throws Exception { 
		m1 = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
	}
	

	@Test
	void testGetCapacidad() {
		assertEquals(280, m1.getCapacidad());
	}

	@Test
	void testGetProductosPeligrosos() {
		assertEquals(false, m1.getProductosPeligrosos());
	}

	@Test
	void testGetMarca() {
		assertEquals("Airbus", m1.getMarca());
	}

	@Test
	void testGetModelo() {
		assertEquals("A350-900", m1.getModelo());
	}

	@Test
	void testGetAutonomia() {
		assertEquals(14815.96, m1.getAutonomia());
	}

	@Test
	void testGetAltura() {
		assertEquals(17.05, m1.getAltura());
	}

	@Test
	void testGetAnchura() {
		assertEquals(64.75, m1.getAnchura());
	}

	@Test
	void testGetLargo() {
		assertEquals(66.89, m1.getLargo());
	}

}
