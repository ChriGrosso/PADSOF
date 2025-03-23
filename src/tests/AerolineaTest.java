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
import aviones.AvionPasajeros;
import vuelos.Periodicidad;
import vuelos.VueloMercancias;

class AerolineaTest {
	private VueloMercancias vm1;
	private Aerolinea a;
	private Aeropuerto ap1;
	private Aeropuerto ap2;
	
	@BeforeEach
	void setUp() throws Exception {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		LocalDateTime timeSalida = LocalDateTime.of(2025, 2, 11, 14, 0);
		LocalDateTime timeLlegada = LocalDateTime.of(2025, 2, 11, 17, 0);
		
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		AvionPasajeros p = new AvionPasajeros("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 200);
		Avion av = new Avion("0001", date, m, date2); 
		
		ArrayList<Temporada> temp1 = new ArrayList<Temporada>();
		temp1.add(new Temporada (MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(2, 5)));
		ArrayList<Temporada> temp2 = new ArrayList<Temporada>();
		temp2.add(new Temporada(MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(9, 9)));
		temp2.add(new Temporada(MonthDay.of(10, 9), LocalTime.of(5, 0), LocalTime.of(1, 0), MonthDay.of(2, 5)));
		ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, temp1, Direccion.NORTE);
		ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, temp2, Direccion.OESTE);
		
		vm1 = new VueloMercancias("H1893", ap1, ap2, timeSalida, timeLlegada, a, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
		
		a = new Aerolinea("IBE", "Iberia");
	}

	@Test
	void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNombre() {
		fail("Not yet implemented");
	}

	@Test
	void testGetVuelos() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAviones() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTiposAvion() {
		fail("Not yet implemented");
	}

	@Test
	void testAddVuelo() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAvion() {
		fail("Not yet implemented");
	}

	@Test
	void testAddTipoAvion() {
		fail("Not yet implemented");
	}

	@Test
	void testAddUso() {
		fail("Not yet implemented");
	}

	@Test
	void testSetEndUso() {
		fail("Not yet implemented");
	}

	@Test
	void testLimpiarHistorialUsos() {
		fail("Not yet implemented");
	}

}
