package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aviones.Avion;
import aviones.AvionMercancias;
import aviones.EstadoAvion;
import usuarios.Gestor;
import vuelos.EstadoVuelo;

class GestorTest {
	
	private Gestor gestor;
	private Avion av;

	@BeforeEach
	void setUp() throws Exception {
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		av = new Avion("0001", LocalDate.of(2023, 3, 14), m, LocalDate.of(2024, 6, 20)); 
		gestor = new Gestor("01020304M", "jperez", "password123");
	}

	@Test
	void testSigueCambioEstadoVuelo() {
		assertFalse(gestor.sigueCambioEstadoVuelo(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN));
	}

	@Test
	void testSigueCambioEstadoAvion() {
		gestor.seguirCamEstado(EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR);
		assertTrue(gestor.sigueCambioEstadoAvion(EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR));
	}

	@Test
	void testEsGestor() {
		assertTrue(gestor.esGestor());
	}

	@Test
	void testEsControlador() {
		assertFalse(gestor.esControlador());
	}

	@Test
	void testEsOperador() {
		assertFalse(gestor.esOperador());
	}

	@Test
	void testSeguirCamEstadoEstadoVueloEstadoVuelo() {
		gestor.seguirCamEstado(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN);
		assertTrue(gestor.sigueCambioEstadoVuelo(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN));
	}

	@Test
	void testSeguirCamEstadoEstadoAvionEstadoAvion() {
		gestor.seguirCamEstado(EstadoAvion.EN_PARKING, EstadoAvion.EN_HANGAR);
		assertTrue(gestor.sigueCambioEstadoAvion(EstadoAvion.EN_PARKING, EstadoAvion.EN_HANGAR));
	}

	@Test
	void testDejarDeSeguirCamEstadoEstadoVueloEstadoVuelo() {
		gestor.seguirCamEstado(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN);
		gestor.dejarDeSeguirCamEstado(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN);
		assertFalse(gestor.sigueCambioEstadoVuelo(EstadoVuelo.DESCARGA_FIN, EstadoVuelo.DESCARGA_FIN));
	}

	@Test
	void testDejarDeSeguirCamEstadoEstadoAvionEstadoAvion() {
		gestor.seguirCamEstado(EstadoAvion.EN_PARKING, EstadoAvion.EN_HANGAR);
		gestor.dejarDeSeguirCamEstado(EstadoAvion.EN_PARKING, EstadoAvion.EN_HANGAR);
		assertFalse(gestor.sigueCambioEstadoAvion(EstadoAvion.EN_PARKING, EstadoAvion.EN_HANGAR));
	}

}
