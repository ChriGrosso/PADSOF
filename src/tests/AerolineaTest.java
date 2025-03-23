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
import aerolineas.ClaveVueloElemento;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import elementos.Finger;
import elementos.Hangar;
import elementos.HangarMercancias;
import elementos.Pista;
import elementos.TerminalMercancias;
import vuelos.EstadoVuelo;
import vuelos.Periodicidad;
import vuelos.VueloMercancias;

class AerolineaTest {
	private VueloMercancias vm1;
	private VueloMercancias vm2;
	private Aerolinea a;
	private Aeropuerto ap1;
	private Aeropuerto ap2;
	private Aeropuerto ap3;
	private Aeropuerto ap4;
	private Avion avm1;
	private Avion avm2;
	private Avion avp1;
	private Avion avp2;
	private AvionMercancias m;
	private AvionMercancias m2;
	private AvionPasajeros p;
	
	@BeforeEach
	void setUp() throws Exception {
		LocalDate date = LocalDate.of(2023, 3, 14);
		LocalDate date2 = LocalDate.of(2024, 6, 20);
		
		m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 280, false);
		m2 = new AvionMercancias("Airbus", "A350-600", 10000, 15.05, 44.75, 46.89, 150, true);
		p = new AvionPasajeros("Airbus", "A320-200", 3700, 11.8, 34.1, 37.6, 175);
		avm1 = new Avion("0001", date, m, date2); 
		avm2 = new Avion("0002", date, m, date2); 
		avp1 = new Avion("0003", date, p, date2); 
		avp2 = new Avion("0004", date, p, date2); 
		
		ArrayList<Temporada> temp1 = new ArrayList<Temporada>();
		temp1.add(new Temporada (MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(2, 5)));
		ArrayList<Temporada> temp2 = new ArrayList<Temporada>();
		temp2.add(new Temporada(MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(9, 9)));
		temp2.add(new Temporada(MonthDay.of(10, 9), LocalTime.of(5, 0), LocalTime.of(1, 0), MonthDay.of(2, 5)));
		ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, temp1, Direccion.NORTE);
		ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, temp2, Direccion.OESTE);
		ap3 = new Aeropuerto("Flughafen Berlin Brandenburg", "BER", "Berlin", "Alemaina", 24.5, +1, temp1, Direccion.SUR);
		ap4 = new Aeropuerto("Budapest-Ferenc Liszt", "BUD", "Budapest", "Hungria", 20.8, +0, temp2, Direccion.OESTE);
		
		a = new Aerolinea("IBE", "Iberia");
		
		vm1 = new VueloMercancias("H1893", ap1, ap2, LocalDateTime.of(2025, 2, 11, 14, 0), LocalDateTime.of(2025, 2, 11, 17, 0), a, false, 155.64, false, Periodicidad.NO_PERIODICO, avm1);
		vm2 = new VueloMercancias("H1899", ap2, ap1, LocalDateTime.of(2025, 2, 12, 14, 0), LocalDateTime.of(2025, 2, 12, 17, 0), a, true, 155.64, false, Periodicidad.NO_PERIODICO, avm2);
		
		a.addTipoAvion(m);
		a.addTipoAvion(p);
		a.addAvion(avm1);
		a.addAvion(avm2);
		a.addAvion(avp1);
		a.addVuelo(vm1);
	}

	@Test
	void testGetId() {
		assertEquals("IBE", a.getId());
	}

	@Test
	void testGetNombre() {
		assertEquals("Iberia", a.getNombre());
	}

	@Test
	void testGetVuelos() {
		assertTrue(a.getVuelos().contains(vm1));
		assertEquals(1, a.getVuelos().size());
	}

	@Test
	void testGetAviones() {
		assertTrue(a.getAviones().containsValue(avm1));
		assertTrue(a.getAviones().containsValue(avm2));
		assertTrue(a.getAviones().containsValue(avp1));
		assertEquals(3, a.getAviones().size());
	}

	@Test
	void testGetTiposAvion() {
		assertTrue(a.getTiposAvion().contains(m));
		assertTrue(a.getTiposAvion().contains(p));
		assertEquals(2, a.getTiposAvion().size());
	}

	@Test
	void testAddVuelo() {
		a.addVuelo(vm2);
		assertTrue(a.getVuelos().contains(vm2));
	}

	@Test
	void testAddAvion() {
		a.addAvion(avp2);
		assertTrue(a.getAviones().containsValue(avp2));
	}

	@Test
	void testAddTipoAvion() {
		a.addTipoAvion(m2);
		assertTrue(a.getTiposAvion().contains(m2));
	}

	@Test
	void testAddUso() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm2.setLocAterrizaje(f);
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		vm2.asignarTerminal(t);
		vm2.asignarPuerta(t.getPuertas().get("AB2"));
		vm2.setEstVuelo(EstadoVuelo.DESCARGA_INI);
		assertTrue(a.getHistorialUsos().containsKey(new ClaveVueloElemento(vm2, f)));
	}

	@Test
	void testSetEndUso() throws InterruptedException {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm2.setLocAterrizaje(f);
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		vm2.asignarTerminal(t);
		vm2.asignarPuerta(t.getPuertas().get("AB2"));
		vm2.setEstVuelo(EstadoVuelo.DESCARGA_INI);
		Thread.sleep(1000);
		vm2.setEstVuelo(EstadoVuelo.DESCARGA_FIN);
		Hangar h = new HangarMercancias("HM001", 14.33, dateTerm, 50, 23.45, 70, 70);
		vm2.getAvion().asignarHangar(h);
		vm2.setEstVuelo(EstadoVuelo.EN_HANGAR);
		assertTrue(a.getHistorialUsos().get(new ClaveVueloElemento(vm2, f)).getHoraDesuso() != null);
	}

	@Test
	void testLimpiarHistorialUsos() {
		Finger f = new Finger("F001", 12.34, LocalDate.of(2022, 5, 5), 18.7);
		vm2.setLocAterrizaje(f);
		LocalDate dateTerm = LocalDate.of(2020, 1, 22);
		TerminalMercancias t = new TerminalMercancias("T1", dateTerm, 60, "AB", 1056);
		vm2.asignarTerminal(t);
		vm2.asignarPuerta(t.getPuertas().get("AB2"));
		vm2.setEstVuelo(EstadoVuelo.DESCARGA_INI);
		a.LimpiarHistorialUsos();
		assertTrue(a.getHistorialUsos().isEmpty());
	}

}
