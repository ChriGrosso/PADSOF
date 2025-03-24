package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import aviones.EstadoAvion;
import usuarios.Gestor;
import usuarios.Operador;
import usuarios.Usuario;
import vuelos.EstadoVuelo;

class UsuarioTest {
	
	private Usuario user;

	@BeforeEach
	void setUp() throws Exception {
		Aerolinea a = new Aerolinea("IBE", "Iberia");
		user = new Operador("01020304M", "jperez", "password123", a);
	}

	@Test
	void testGetDni() {
		assertEquals("01020304M", user.getDni());
	}

	@Test
	void testGetNombre() {
		assertEquals("jperez", user.getNombre());
	}

	@Test
	void testGetPassword() {
		assertEquals("password123", user.getPassword());
	}
	
	@Test
	void testGetNotificaciones() {
		assertTrue(user.getNotificaciones().size()==0);
	}

	@Test
	void testSetPassword() {
		user.setPassword("12345678");
		assertEquals("12345678", user.getPassword());
	}

	@Test
	void testUpdateStringEstadoVueloEstadoVuelo() {
		user.update("0102", EstadoVuelo.ATERRIZADO, EstadoVuelo.DESEMBARQUE_INI);
		assertTrue(user.getNotificaciones().size()>0);
		
	}

	@Test
	void testUpdateStringEstadoAvionEstadoAvion() {
		user.update("0102", EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR);
		assertTrue(user.getNotificaciones().size()>0);
	}

	@Test
	void testSigueCambioEstadoVuelo() {
		assertTrue(user.sigueCambioEstadoVuelo(EstadoVuelo.ATERRIZADO, EstadoVuelo.DESEMBARQUE_INI));
	}

	@Test
	void testSigueCambioEstadoAvion() {
		assertTrue(user.sigueCambioEstadoAvion(EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR));
	}

	@Test
	void testEnviarNotificacion() {
		String s = "Hola";
		Usuario gestor = new Gestor("01020304S", "raul", "pass123word");
		user.enviarNotificacion(s, gestor);
		assertTrue(gestor.getNotificaciones().size()>0);
	}

}
