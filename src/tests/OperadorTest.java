package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import usuarios.Operador;

class OperadorTest {
	
	private Operador operador;
    private Aerolinea a;

	@BeforeEach
	void setUp() throws Exception {
		a = new Aerolinea("IBE", "Iberia");
		operador = new Operador("01020304M", "jperez", "password123", a);
		
	}

	@Test
	void testEsGestor() {
		assertFalse(operador.esGestor());
	}

	@Test
	void testEsControlador() {
		assertFalse(operador.esControlador());
	}

	@Test
	void testEsOperador() {
		assertTrue(operador.esOperador());
	}

	@Test
	void testGetAerolinea() {
		assertEquals(a, operador.getAerolinea());
	}

}
