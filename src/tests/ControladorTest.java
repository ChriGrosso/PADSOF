package tests;

import static org.junit.jupiter.api.Assertions.*;

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
import elementos.Terminal;
import elementos.TerminalMercancias;
import usuarios.Controlador;
import vuelos.Periodicidad;
import vuelos.Vuelo;
import vuelos.VueloMercancias;

class ControladorTest {
	
	private Controlador controlador;
    private Terminal terminal;
    private Vuelo vuelo1, vuelo2;

	@BeforeEach
	void setUp() throws Exception {
		terminal = new TerminalMercancias("T1", LocalDate.now(), 2, "P1", 10);
        
        AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		Avion av = new Avion("0001", LocalDate.of(2023, 3, 14), m, LocalDate.of(2024, 6, 20)); 
		Aerolinea a = new Aerolinea("IBE", "Iberia");
		ArrayList<Aerolinea> arrayA = new ArrayList<Aerolinea>();
		arrayA.add(a);

		ArrayList<Temporada> temp1 = new ArrayList<Temporada>();
		temp1.add(new Temporada (MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(2, 5)));
		ArrayList<Temporada> temp2 = new ArrayList<Temporada>();
		temp2.add(new Temporada(MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(9, 9)));
		temp2.add(new Temporada(MonthDay.of(10, 9), LocalTime.of(5, 0), LocalTime.of(1, 0), MonthDay.of(2, 5)));
		Aeropuerto ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, temp1, Direccion.NORTE);
		Aeropuerto ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, temp2, Direccion.OESTE);
		vuelo1 = new VueloMercancias(ap1, ap2, LocalDateTime.of(2025, 2, 11, 14, 0), LocalDateTime.of(2025, 2, 11, 17, 0),
				arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
		vuelo2 = new VueloMercancias(ap2, ap1, LocalDateTime.of(2025, 3, 9, 13, 0), LocalDateTime.of(2025, 3, 9, 17, 0),
				arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
		
		controlador = new Controlador("01020304M", "jperez", "password123", terminal);
		controlador.asignarVuelo(vuelo1);

	}

	@Test
	void testEsGestor() {
		assertFalse(controlador.esGestor());
	}

	@Test
	void testEsControlador() {
		assertTrue(controlador.esControlador());
	}

	@Test
	void testEsOperador() {
		assertFalse(controlador.esOperador());
	}

	@Test
	void testGetTerminal() {
		assertEquals(terminal, controlador.getTerminal());
	}

	@Test
	void testGetVuelosASuCargo() {
		assertTrue(controlador.getVuelosASuCargo().contains(vuelo1));
	}

	@Test
	void testSetTerminal() {
		Terminal terminal2 = new TerminalMercancias("T2", LocalDate.now(), 2, "P2", 10);
		controlador.setTerminal(terminal2);
		assertEquals(terminal2, controlador.getTerminal());
	}

	@Test
	void testAsignarVuelo() {
		assertTrue(controlador.asignarVuelo(vuelo2));
	}

}
