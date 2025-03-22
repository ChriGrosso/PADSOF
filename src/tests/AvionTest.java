package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aviones.Avion;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import aviones.EstadoAvion;


class AvionTest {
	private Avion a1;
	private Avion a2;
	
	@BeforeEach 
	void setUp() throws Exception { 
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		AvionMercancias av1 = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		AvionPasajeros av2 = new AvionPasajeros("Airbus", "A320-200", 3717.83, 11.8, 34.12, 37.6, 173);
		a1 = new Avion("0001", date, av1, date2); 
		a2 = new Avion("0002", date2, av2);
	}
	
	
	@Test
	void testGetMatricula() {
		assertEquals("0001", a1.getMatricula());
	}

	@Test
	void testGetFechaCompra() {
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		assertEquals(date2, a2.getFechaCompra());
	}

	@Test
	void testGetTipoAvion() {
		AvionMercancias av1 = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		// Comparar todos los atributos de TipoAvion
		assertEquals(av1.getMarca(), a1.getTipoAvion().getMarca());
		assertEquals(av1.getModelo(), a1.getTipoAvion().getModelo());
		assertEquals(av1.getAutonomia(), a1.getTipoAvion().getAutonomia());
		assertEquals(av1.getAltura(), a1.getTipoAvion().getAltura());
		assertEquals(av1.getAnchura(), a1.getTipoAvion().getAnchura());
		assertEquals(av1.getLargo(), a1.getTipoAvion().getLargo());
	}

	@Test
	void testGetFechaUltimaRevision() {
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		assertEquals(date2, a1.getFechaUltimaRevision());
	}
	
	@Test
	void testGetFechaUltimaRevisionNotInit() {
		assertNull(a2.getFechaUltimaRevision());
	}

	@Test
	void testGetEstadoAvionNotInit() {
		assertEquals(EstadoAvion.FUERA_AEROPUERTO, a2.getEstadoAvion());
	}

	@Test
	void testSetFechaUltimaRevision() {
		LocalDate date3 = LocalDate.of(2024, 7, 24);
		a2.setFechaUltimaRevision(date3);
		assertEquals(date3, a2.getFechaUltimaRevision());
	}

	@Test
	void testSetEstadoAvion() {
		a1.setEstadoAvion(EstadoAvion.EN_FINGER);
		assertEquals(EstadoAvion.EN_FINGER, a1.getEstadoAvion());
	}
}





