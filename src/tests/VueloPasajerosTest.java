package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import elementos.Finger;
import elementos.Pista;
import elementos.TerminalMercancias;
import elementos.TerminalPasajeros;
import elementos.ZonaParking;
import vuelos.EstadoVuelo;
import vuelos.Periodicidad;
import vuelos.VueloPasajeros;

class VueloPasajerosTest {
	private VueloPasajeros vp1;
	private VueloPasajeros vp2;
	private LocalDateTime timeLlegada;
	private LocalDateTime timeSalida;
	private ArrayList<Aerolinea> arrayA;
	private Aeropuerto ap1;
	private Aeropuerto ap2;
	private Avion av;
	
	@BeforeEach
	void setUp() throws Exception {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		timeSalida = LocalDateTime.of(2025, 2, 11, 14, 0);
		timeLlegada = LocalDateTime.of(2025, 2, 11, 17, 0);
		AvionPasajeros p = new AvionPasajeros("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 200);
		av = new Avion("0001", date, p, date2);
		
		Aerolinea a = new Aerolinea("IBE", "Iberia");
		arrayA = new ArrayList<Aerolinea>();
		arrayA.add(a);
		
		ArrayList<Temporada> temp1 = new ArrayList<Temporada>();
		temp1.add(new Temporada (MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(2, 5)));
		ArrayList<Temporada> temp2 = new ArrayList<Temporada>();
		temp2.add(new Temporada(MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(9, 9)));
		temp2.add(new Temporada(MonthDay.of(10, 9), LocalTime.of(5, 0), LocalTime.of(1, 0), MonthDay.of(2, 5)));
		ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, temp1, Direccion.NORTE);
		ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, temp2, Direccion.OESTE);
		vp1 = new VueloPasajeros("H1894", ap1, ap2, LocalDateTime.of(2025, 2, 11, 14, 0), LocalDateTime.of(2025, 2, 11, 17, 0), arrayA, true, 155, av, "V D");
		vp2 = new VueloPasajeros("H1897", ap2, ap1, LocalDateTime.of(2025, 2, 11, 17, 50), LocalDateTime.of(2025, 2, 11, 20, 50), arrayA, false, 155, av, "V D");
		
		a.addTipoAvion(p);
		a.addAvion(av);
		a.addVuelo(vp1);
		a.addVuelo(vp2);
	}


	@Test
	void testExceptionConstructor() {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		Avion av2 = new Avion("0001", date, m, date2);
		
		// Usar assertThrows para verificar que se lanza la excepción correcta
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Aquí llamas al constructor o método que debe lanzar la excepción
            @SuppressWarnings("unused")
			VueloPasajeros vp2 = new VueloPasajeros("H1893", ap1, ap2, timeSalida, timeLlegada, arrayA, false, 155, Periodicidad.NO_PERIODICO, av2);
        });
		
		assertEquals("Un vuelo de pasajeros debe tener un avión para pasajeros", exception.getMessage());
	}

	@Test
	void testVueloDiasAlternos() {
		ArrayList<DayOfWeek> diasAlt = new ArrayList<DayOfWeek>();
		diasAlt.add(DayOfWeek.FRIDAY);
		diasAlt.add(DayOfWeek.SUNDAY);
		assertEquals(diasAlt, vp1.getDiasAlternos());
	}

	@Test
	void testGetNumPasajeros() {
		assertEquals(155, vp1.getNumPasajeros());
	}

	@Test
	void testGetId() {
		assertEquals("H1894", vp1.getId());
	}

	@Test
	void testIsVueloMercancias() {
		assertFalse(vp1.isVueloMercancias());
	}

	@Test
	void testGetOrigen() {
		assertEquals(ap1, vp1.getOrigen());
	}

	@Test
	void testGetDestino() {
		assertEquals(ap2, vp1.getDestino());
	}

	@Test
	void testGetHoraLlegada() {
		assertEquals(timeLlegada, vp1.getHoraLlegada());
	}

	@Test
	void testGetHoraSalida() {
		assertEquals(timeSalida, vp1.getHoraSalida());
	}

	@Test
	void testGetHoraLlegadaEfectivaNotInit() {
		assertNull(vp1.getHoraLlegadaEfectiva());
	}

	@Test
	void testGetHoraSalidaEfectivaNotInit() {
		assertNull(vp1.getHoraSalidaEfectiva());
	}

	@Test
	void testGetCompartido() {
		assertEquals(false, vp1.getCompartido());
	}

	@Test
	void testGetFinger() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vp1.asignarLocAterrizaje(f);
		assertEquals(true, vp1.getFinger());
	}

	@Test
	void testGetLlegada() {
		assertEquals(true, vp1.getLlegada());
	}

	@Test
	void testGetAerolineas() {
		assertEquals(arrayA, vp1.getAerolineas());
	}

	@Test
	void testGetAerolinea() {
		assertEquals(arrayA.get(0), vp1.getAerolinea());
	}

	@Test
	void testGetPeriodicidad() {
		assertEquals(Periodicidad.DIAS_ALTERNOS, vp1.getPeriodicidad());
	}

	@Test
	void testGetEstVueloNotInit() {
		assertEquals(vp1.getEstVuelo(), EstadoVuelo.EN_TIEMPO);
	}

	@Test
	void testGetAvion() {
		assertEquals(av, vp1.getAvion());
	}

	@Test
	void testGetLocAterrizajeNotInit() {
		assertNull(vp1.getLocAterrizaje());
	}

	@Test
	void testGetPistaNotInit() {
		assertNull(vp1.getPista());
	}

	@Test
	void testGetPuertaNotInit() {
		assertNull(vp1.getPuerta());
	}

	@Test
	void testGetMapaElemClave() {
		assertTrue(vp1.getMapaElemClave().isEmpty());
	}

	@Test
	void testGetTerminalNotInit() {
		assertNull(vp1.getTerminal());
	}

	@Test
	void testGetDiasAlternos() {
		ArrayList<DayOfWeek> diasAlt = new ArrayList<DayOfWeek>();
		diasAlt.add(DayOfWeek.FRIDAY);
		diasAlt.add(DayOfWeek.SUNDAY);
		assertEquals(diasAlt, vp1.getDiasAlternos());
	}

	@Test
	void testSetHoraLlegadaEfectiva() {
		LocalDateTime llegadaEfec = LocalDateTime.of(2025, 2, 11, 16, 55);
		vp1.setHoraLlegadaEfectiva(llegadaEfec);
		assertEquals(llegadaEfec, vp1.getHoraLlegadaEfectiva());
	}

	@Test
	void testSetHoraSalidaEfectiva() {
		LocalDateTime salidaEfec = LocalDateTime.of(2025, 2, 11, 14, 10);
		vp1.setHoraSalidaEfectiva(salidaEfec);
		assertEquals(salidaEfec, vp1.getHoraSalidaEfectiva());
	}

	
	@Test
	void testSetEstVuelo() {
		// Probar estados para LLEGADA
		vp1.setEstVuelo(EstadoVuelo.RETRASADO);
		assertEquals(EstadoVuelo.RETRASADO, vp1.getEstVuelo());
		
		// ESPERANDO_PISTA_A
		vp1.setEstVuelo(EstadoVuelo.ESPERANDO_PISTA_A);
		assertEquals(EstadoVuelo.ESPERANDO_PISTA_A, vp1.getEstVuelo());
		
		// ESPERANDO_AT-ATERRIZADO
		Pista p = new Pista("P001", LocalDate.of(2022, 5, 5), false, 35);
		vp1.asignarPista(p);
		vp1.setEstVuelo(EstadoVuelo.ATERRIZADO);
		assertEquals(EstadoVuelo.ATERRIZADO, vp1.getEstVuelo());
		
		// DESEMBARQUE
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 18.77, 65.9, 67.89);
		vp1.asignarLocAterrizaje(zp);
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalPasajeros t = new TerminalPasajeros("T1", dateTerm, 60, "CD", 1056);
		vp1.asignarTerminal(t);
		vp1.asignarPuerta(t.getPuertas().get("CD3"));
		vp1.setEstVuelo(EstadoVuelo.DESEMBARQUE_INI);
		assertEquals(EstadoVuelo.DESEMBARQUE_INI, vp1.getEstVuelo());
		
		// OPERATIVO-EN_HANGAR
		vp1.setHoraLlegadaEfectiva(LocalDateTime.of(2025, 2, 11, 17, 10));
		assertFalse(vp1.setEstVuelo(EstadoVuelo.EN_HANGAR));
		vp1.setEstVuelo(EstadoVuelo.OPERATIVO);
		assertEquals(EstadoVuelo.OPERATIVO, vp1.getEstVuelo());
		assertTrue(vp1.getAerolinea().getHistorialUsos().get(vp1.getMapaElemClave().get(vp1.getPuerta())).getHoraDesuso() != null);
		assertTrue(vp1.getAerolinea().getHistorialUsos().get(vp1.getMapaElemClave().get(vp1.getLocAterrizaje())).getHoraDesuso() != null);
	}


	@Test
	void testCalcularRetraso() {
		LocalDateTime llegadaEfec = LocalDateTime.of(2025, 2, 11, 17, 10);
		vp1.setHoraLlegadaEfectiva(llegadaEfec);
		LocalDateTime salidaEfec = LocalDateTime.of(2025, 2, 11, 13, 55);
		vp1.setHoraSalidaEfectiva(salidaEfec);
		assertEquals(10, vp1.calcularRetraso());
	}

	@Test
	void testAsignarLocAterrizajeFingerFail() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 15.7);
		assertEquals(false, vp1.asignarLocAterrizaje(f));
	}
	
	@Test
	void testAsignarLocAterrizajeFingerSuccess() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		assertTrue(vp1.asignarLocAterrizaje(f));
		vp1.asignarLocAterrizaje(f);
		assertEquals("F001", vp1.getLocAterrizaje().getId());
		assertTrue(vp1.getFinger());
	}
	
	@Test
	void testAsignarLocAterrizajeZonaParkingFail() {
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 16.77, 56.9, 66.89);
		assertFalse(vp1.asignarLocAterrizaje(zp));
	}
	
	@Test
	void testAsignarLocAterrizajeZonaParkingSuccess() {
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 18.77, 65.9, 67.89);
		assertEquals(true, vp1.asignarLocAterrizaje(zp));
		vp1.asignarLocAterrizaje(zp);
		assertEquals("ZP001", vp1.getLocAterrizaje().getId());
		assertEquals(false, vp1.getFinger());
	}

	@Test
	void testAsignarPista() {
		Pista p = new Pista("P001", LocalDate.of(2022, 5, 5), false, 35);
		assertEquals(true, vp1.asignarPista(p));
		vp1.asignarPista(p);
		assertEquals("P001", vp1.getPista().getId());
		assertTrue(vp1.setEstVuelo(EstadoVuelo.ATERRIZADO));
		vp1.setEstVuelo(EstadoVuelo.ATERRIZADO);
		assertNull(vp1.getPista().getUsando());
		assertFalse(vp1.getPista().enUso());
	}

	@Test
	void testAsignarTerminal() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias tm = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		TerminalPasajeros tp = new TerminalPasajeros("T1", dateTerm, 60, "CD", 1056);
		assertFalse(vp1.asignarTerminal(tm));
		vp1.asignarTerminal(tp);
		assertEquals(tp.getId(), vp1.getTerminal().getId());
	}

	@Test
	void testAsignarPuerta() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalPasajeros t = new TerminalPasajeros("T1", dateTerm, 60, "CD", 1056);
		vp1.asignarTerminal(t);
		vp1.asignarPuerta(t.getPuertas().get("CD3"));
		assertEquals("CD3", vp1.getPuerta().getCod());
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vp1.setLocAterrizaje(f);
		assertTrue(vp1.setEstVuelo(EstadoVuelo.DESEMBARQUE_INI));
	}

}
