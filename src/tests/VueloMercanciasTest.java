package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
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
import vuelos.VueloMercancias;

class VueloMercanciasTest {
	private VueloMercancias vm1;
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
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		av = new Avion("0001", date, m, date2);
		
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
		vm1 = new VueloMercancias(ap1, ap2, timeSalida, timeLlegada, arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
		
		a.addTipoAvion(m);
		a.addAvion(av);
		a.addVuelo(vm1);
	}
	
	@AfterEach
    void limpiar() {
        vm1.resetGenId();
    }

	@Test
	void testExceptionConstructor() {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		AvionPasajeros p = new AvionPasajeros("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 200);
		Avion av2 = new Avion("0001", date, p, date2);
		
		// Usar assertThrows para verificar que se lanza la excepción correcta
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Aquí llamas al constructor o método que debe lanzar la excepción
            @SuppressWarnings("unused")
			VueloMercancias vm2 = new VueloMercancias(ap1, ap2, timeSalida, timeLlegada, arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av2);
        });
		
		assertEquals("Un vuelo de mercancías debe tener un avión para mercancías", exception.getMessage());
	}

	@Test
	void testVueloDiasAlternos() {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		Avion av = new Avion("0001", date, m, date2);
		
		VueloMercancias vm2 = new VueloMercancias(ap1, ap2, timeSalida, timeLlegada, arrayA, false, 155.64, false, av, "L J S");
		ArrayList<DayOfWeek> diasAlt = new ArrayList<DayOfWeek>();
		diasAlt.add(DayOfWeek.MONDAY);
		diasAlt.add(DayOfWeek.THURSDAY);
		diasAlt.add(DayOfWeek.SATURDAY);
		assertEquals(diasAlt, vm2.getDiasAlternos());
	}

	@Test
	void testGetCarga() {
		assertEquals(155.64, vm1.getCarga());
	}

	@Test
	void testGetMercanciasPeligrosas() {
		assertFalse(vm1.getMercanciasPeligrosas());
	}

	@Test
	void testGetId() {
		assertEquals("IBE0000", vm1.getId());
	}

	@Test
	void testIsVueloMercancias() {
		assertTrue(vm1.isVueloMercancias());
	}

	@Test
	void testGetOrigen() {
		assertEquals(ap1, vm1.getOrigen());
	}

	@Test
	void testGetDestino() {
		assertEquals(ap2, vm1.getDestino());
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
		assertEquals(arrayA, vm1.getAerolineas());
	}

	@Test
	void testGetAerolinea() {
		assertEquals(arrayA.get(0), vm1.getAerolinea());
	}

	@Test
	void testGetPeriodicidad() {
		assertEquals(Periodicidad.NO_PERIODICO, vm1.getPeriodicidad());
	}

	@Test
	void testGetEstVueloNotInit() {
		assertEquals(vm1.getEstVuelo(), EstadoVuelo.EN_TIEMPO);
	}

	@Test
	void testGetAvion() {
		assertEquals(av, vm1.getAvion());
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
	void testGetPuertaNotInit() {
		assertNull(vm1.getPuerta());
	}

	@Test
	void testGetMapaElemClave() {
		assertTrue(vm1.getMapaElemClave().isEmpty());
	}

	@Test
	void testGetTerminalNotInit() {
		assertNull(vm1.getTerminal());
	}

	@Test
	void testGetDiasAlternos() {
		assertNull(vm1.getDiasAlternos());
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
		// Probar estados para SALIDA
		vm1.setEstVuelo(EstadoVuelo.RETRASADO);
		assertEquals(EstadoVuelo.RETRASADO, vm1.getEstVuelo());
		
		// CARGA
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm1.asignarLocAterrizaje(f);
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		vm1.asignarTerminal(t);
		vm1.asignarPuerta(t.getPuertas().get("AB2"));
		vm1.setEstVuelo(EstadoVuelo.CARGA);
		assertEquals(EstadoVuelo.CARGA, vm1.getEstVuelo());
		assertTrue(vm1.getAerolinea().getHistorialUsos().containsKey(vm1.getMapaElemClave().get(vm1.getPuerta())));
		assertTrue(vm1.getAerolinea().getHistorialUsos().containsKey(vm1.getMapaElemClave().get(vm1.getLocAterrizaje())));
		
		// ESPERANDO_PISTA_D
		vm1.setEstVuelo(EstadoVuelo.ESPERANDO_PISTA_D);
		assertEquals(EstadoVuelo.ESPERANDO_PISTA_D, vm1.getEstVuelo());
		
		// ESPERANDO_DESP-EN_VUELO
		Pista p = new Pista("P001", LocalDate.of(2022, 5, 5), true, 35);
		vm1.asignarPista(p);
		assertFalse(vm1.setEstVuelo(EstadoVuelo.ESPERANDO_DESPEGUE));
		vm1.setEstVuelo(EstadoVuelo.EN_VUELO);
		assertEquals(EstadoVuelo.EN_VUELO, vm1.getEstVuelo());
		assertTrue(vm1.getAerolinea().getHistorialUsos().get(vm1.getMapaElemClave().get(vm1.getPuerta())).getHoraDesuso() != null);
		assertTrue(vm1.getAerolinea().getHistorialUsos().get(vm1.getMapaElemClave().get(vm1.getLocAterrizaje())).getHoraDesuso() != null);
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
		assertTrue(vm1.asignarLocAterrizaje(f));
		vm1.asignarLocAterrizaje(f);
		assertEquals("F001", vm1.getLocAterrizaje().getId());
		assertTrue(vm1.getFinger());
	}
	
	@Test
	void testAsignarLocAterrizajeZonaParkingFail() {
		ZonaParking zp = new ZonaParking("ZP001", 6.78, LocalDate.of(2022, 5, 5), 200, 16.77, 56.9, 66.89);
		assertFalse(vm1.asignarLocAterrizaje(zp));
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
		Pista p = new Pista("P001", LocalDate.of(2022, 5, 5), true, 35);
		assertEquals(true, vm1.asignarPista(p));
		vm1.asignarPista(p);
		assertEquals("P001", vm1.getPista().getId());
		assertTrue(vm1.setEstVuelo(EstadoVuelo.EN_VUELO));
		vm1.setEstVuelo(EstadoVuelo.EN_VUELO);
		assertNull(vm1.getPista().getUsando());
		assertFalse(vm1.getPista().enUso());
	}

	@Test
	void testAsignarTerminal() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias tm = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		TerminalPasajeros tp = new TerminalPasajeros("T1", dateTerm, 60, "CD", 1056);
		assertFalse(vm1.asignarTerminal(tp));
		vm1.asignarTerminal(tm);
		assertEquals(tm.getId(), vm1.getTerminal().getId());
	}

	@Test
	void testAsignarPuerta() {
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		vm1.asignarTerminal(t);
		vm1.asignarPuerta(t.getPuertas().get("AB2"));
		assertEquals("AB2", vm1.getPuerta().getCod());
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm1.setLocAterrizaje(f);
		assertTrue(vm1.setEstVuelo(EstadoVuelo.CARGA));
	}

}
