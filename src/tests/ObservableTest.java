package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;

import vuelos.EstadoVuelo;
import vuelos.Periodicidad;
import vuelos.Vuelo;
import vuelos.VueloMercancias;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.EstadoAvion;
import usuarios.Gestor;
import usuarios.Usuario;

class ObservableTest {
	
	private Vuelo vuelo;
	private Avion avion;
	private Usuario u;

	@BeforeEach
	void setUp() throws Exception {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		AvionMercancias av1 = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		avion = new Avion("0001", date, av1, date2); 
		
		LocalDateTime timeSalida = LocalDateTime.of(2025, 2, 11, 14, 0);
		LocalDateTime timeLlegada = LocalDateTime.of(2025, 2, 11, 17, 0);
		AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		Avion av = new Avion("0001", date, m, date2); 
		
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
		vuelo = new VueloMercancias("H1893", ap1, ap2, timeSalida, timeLlegada, arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
		
		u = new Gestor("01020304M", "Pepe", "password12");
	}

	@Test
	void testGetId() {
		assertEquals("0001", avion.getId());
	}
	
	@Test
	void testGetObservers() {
		avion.addObserver(u);
		assertTrue(avion.getObservers().contains(u));
	}

	@Test
	void testAddObserver() {
		vuelo.addObserver(u);
		assertTrue(vuelo.getObservers().contains(u));
		assertThrows(IllegalArgumentException.class, () -> vuelo.addObserver(null));
	}

	@Test
	void testDeleteObserver() {
		vuelo.addObserver(u);
		vuelo.deleteObserver(u);
		assertFalse(vuelo.getObservers().contains(u));
		assertThrows(IllegalArgumentException.class, () -> vuelo.deleteObserver(null));
	}


	@Test
	void testNotifyObserversEstadoVueloEstadoVuelo() {
		vuelo.addObserver(u);
		Gestor gestor = (Gestor)u;
		int sizeAnterior = u.getNotificaciones().size();
		
		gestor.seguirCamEstado(EstadoVuelo.EN_TIEMPO, EstadoVuelo.RETRASADO);
		vuelo.setEstVuelo(EstadoVuelo.EN_TIEMPO);
		vuelo.setEstVuelo(EstadoVuelo.RETRASADO);
		assertTrue(u.getNotificaciones().size()>sizeAnterior);
		
	}

	@Test
	void testNotifyObserversEstadoAvionEstadoAvion() {
		avion.addObserver(u);
		Gestor gestor = (Gestor)u;
		int sizeAnterior = u.getNotificaciones().size();
		
		gestor.seguirCamEstado(EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR);
		avion.setEstadoAvion(EstadoAvion.EN_FINGER);
		avion.setEstadoAvion(EstadoAvion.EN_HANGAR);
		assertTrue(u.getNotificaciones().size()>sizeAnterior);
	}

}
