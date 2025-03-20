package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.EstadoAvion;
import elementos.Finger;
import elementos.Pista;
import elementos.TerminalMercancias;
import elementos.ZonaParking;
import sistema.Aeropuerto;
import sistema.Direccion;
import vuelos.EstadoVuelo;
import vuelos.Periodicidad;
import vuelos.VueloMercancias;

class VueloMercanciasTest {
	private VueloMercancias vm1;
	private LocalDateTime timeLlegada;
	private LocalDateTime timeSalida;
	private Aerolinea a;
	
	@BeforeEach
	void setUp() throws Exception {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		timeSalida = LocalDateTime.of(2025, 2, 11, 14, 0);
		timeLlegada = LocalDateTime.of(2025, 2, 11, 17, 0);
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		Avion av = new Avion("0001", date, m, date2, EstadoAvion.EN_HANGAR); 
		a = new Aerolinea("IBE", "Iberia");
		Aeropuerto ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, "De 7:30 a 21:30", Direccion.NORTE);
		Aeropuerto ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, "De 0:00 a 23:59", Direccion.OESTE);
		vm1 = new VueloMercancias("H1893", ap1, ap2, timeSalida, timeLlegada, a, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
	}

	@Test
	void testGetCarga() {
		assertEquals(155.64, vm1.getCarga());
	}

	@Test
	void testGetMercanciasPeligrosas() {
		assertEquals(false, vm1.getMercanciasPeligrosas());
	}

	@Test
	void testGetTerminalNotInit() {
		assertNull(vm1.getTerminal());
	}

	@Test
	void testGetPuertaNotInit() {
		assertNull(vm1.getPuerta());
	}

	@Test
	void testAsignarTerminal() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", 35.67, dateTerm, 60, "AB", 1056);
		vm1.asignarTerminal(t);
		assertEquals(t.getId(), vm1.getTerminal().getId());
	}

	@Test
	void testAsignarPuerta() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", 35.67, dateTerm, 60, "AB", 1056);
		vm1.asignarTerminal(t);
		vm1.asignarPuerta("AB2");
		assertEquals("AB2", vm1.getPuerta().getCod());
	}

	@Test
	void testGetId() {
		assertEquals("H1893", vm1.getId());
	}

	@Test
	void testGetHoraLlegada() {
		assertEquals(timeLlegada, vm1.getHoraLlegada());
	}

	@Test
	void testGetHoraSalida() {
		assertEquals(timeSalida, vm1.getHoraSalida());
	}

	@Test
	void testGetHoraLlegadaEfectivaNotInit() {
		assertNull(vm1.getHoraLlegadaEfectiva());
	}

	@Test
	void testGetHoraSalidaEfectivaNotInit() {
		assertNull(vm1.getHoraSalidaEfectiva());
	}

	@Test
	void testGetCompartido() {
		assertEquals(false, vm1.getCompartido());
	}

	@Test
	void testGetFinger() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm1.asignarLocAterrizaje(f);
		assertEquals(true, vm1.getFinger());
	}

	@Test
	void testGetLlegada() {
		assertEquals(false, vm1.getLlegada());
	}

	@Test
	void testGetAerolineas() {
		assertEquals(a.getId(), vm1.getAerolinea().getId());
		assertEquals(a.getNombre(), vm1.getAerolinea().getNombre());
	}

	@Test
	void testGetPeriodicidad() {
		assertEquals(Periodicidad.NO_PERIODICO, vm1.getPeriodicidad());
	}

	@Test
	void testGetEstVueloNotInit() {
		assertNull(vm1.getEstVuelo());
	}

	@Test
	void testGetLocAterrizajeNotInit() {
		assertNull(vm1.getLocAterrizaje());
	}

	@Test
	void testGetPistaNotInit() {
		assertNull(vm1.getPista());
	}

	@Test
	void testSetHoraLlegadaEfectiva() {
		LocalDateTime llegadaEfec = LocalDateTime.of(2025, 2, 11, 16, 55);
		vm1.setHoraLlegadaEfectiva(llegadaEfec);
		assertEquals(llegadaEfec, vm1.getHoraLlegadaEfectiva());
	}

	@Test
	void testSetHoraSalidaEfectiva() {
		LocalDateTime salidaEfec = LocalDateTime.of(2025, 2, 11, 14, 10);
		vm1.setHoraSalidaEfectiva(salidaEfec);
		assertEquals(salidaEfec, vm1.getHoraSalidaEfectiva());
	}

	@Test
	void testSetEstVuelo() {
		vm1.setEstVuelo(EstadoVuelo.DESCARGA_INI);
		assertEquals(EstadoVuelo.DESCARGA_INI, vm1.getEstVuelo());
	}

	@Test
	void testCalcularRetraso() {
		LocalDateTime llegadaEfec = LocalDateTime.of(2025, 2, 11, 16, 55);
		vm1.setHoraLlegadaEfectiva(llegadaEfec);
		LocalDateTime salidaEfec = LocalDateTime.of(2025, 2, 11, 14, 10);
		vm1.setHoraSalidaEfectiva(salidaEfec);
		assertEquals(10, vm1.calcularRetraso());
	}

	@Test
	void testAsignarLocAterrizajeFingerFail() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 15.7);
		assertEquals(false, vm1.asignarLocAterrizaje(f));
	}
	
	@Test
	void testAsignarLocAterrizajeFingerSuccess() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		assertEquals(true, vm1.asignarLocAterrizaje(f));
		vm1.asignarLocAterrizaje(f);
		assertEquals("F001", vm1.getLocAterrizaje().getId());
		assertEquals(true, vm1.getFinger());
	}
	
	@Test
	void testAsignarLocAterrizajeZonaParkingFail() {
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 16.77, 56.9, 66.89);
		assertEquals(false, vm1.asignarLocAterrizaje(zp));
	}
	
	@Test
	void testAsignarLocAterrizajeZonaParkingSuccess() {
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 18.77, 65.9, 67.89);
		assertEquals(true, vm1.asignarLocAterrizaje(zp));
		vm1.asignarLocAterrizaje(zp);
		assertEquals("ZP001", vm1.getLocAterrizaje().getId());
		assertEquals(false, vm1.getFinger());
	}

	@Test
	void testAsignarPista() {
		Pista p = new Pista("P001", 11.5, LocalDate.of(2022, 5, 5), false, 35);
		assertEquals(true, vm1.asignarPista(p));
		vm1.asignarPista(p);
		assertEquals("P001", vm1.getPista().getId());
	}

}
