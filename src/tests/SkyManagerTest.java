package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import aeropuertos.Temporada;
import aviones.Avion;
import aviones.AvionMercancias;
import elementos.Finger;
import elementos.Hangar;
import elementos.HangarMercancias;
import elementos.Pista;
import elementos.Terminal;
import elementos.TerminalMercancias;
import elementos.ZonaParking;
import facturas.Factura;
import sistema.SkyManager;
import usuarios.Gestor;
import usuarios.Usuario;
import vuelos.Periodicidad;
import vuelos.Vuelo;
import vuelos.VueloMercancias;

class SkyManagerTest {

	private SkyManager skyManager;
	private Vuelo vuelo;
	private Aerolinea a;
    
    @BeforeEach
    void setUp() throws Exception {
        skyManager = SkyManager.getInstance();
        AvionMercancias m = new AvionMercancias("Airbus", "A350-900", 14815.96, 17.05, 64.75, 66.89, 2, false);
		Avion av = new Avion("0001", LocalDate.of(2023, 3, 14), m, LocalDate.of(2024, 6, 20)); 
		a = new Aerolinea("IBE", "Iberia");
		ArrayList<Aerolinea> arrayA = new ArrayList<Aerolinea>();
		arrayA.add(a);

		ArrayList<Temporada> temp1 = new ArrayList<Temporada>();
		temp1.add(new Temporada (MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(2, 5)));
		ArrayList<Temporada> temp2 = new ArrayList<Temporada>();
		temp2.add(new Temporada(MonthDay.of(3, 5), LocalTime.NOON, LocalTime.MIDNIGHT, MonthDay.of(9, 9)));
		temp2.add(new Temporada(MonthDay.of(10, 9), LocalTime.of(5, 0), LocalTime.of(1, 0), MonthDay.of(2, 5)));
		Aeropuerto ap1 = new Aeropuerto("Madrid Barajas", "MAD", "Madrid", "España", 15.6, +1, temp1, Direccion.NORTE);
		Aeropuerto ap2 = new Aeropuerto("Londres-Heathrow", "LHR", "Londres", "Inglaterra", 20.8, +0, temp2, Direccion.OESTE);
		vuelo = new VueloMercancias("H1893", ap1, ap2, LocalDateTime.of(2025, 2, 11, 14, 0), LocalDateTime.of(2025, 2, 11, 17, 0),
				arrayA, false, 155.64, false, Periodicidad.NO_PERIODICO, av);
    }

    @Test
    void testGetInstance() {
        assertNotNull(SkyManager.getInstance());
    }
    
    @Test
    void testClone() {
        assertThrows(CloneNotSupportedException.class, () -> skyManager.clone());
    }
    
    @Test
    void testGuardarDatos() {
        assertDoesNotThrow(() -> skyManager.guardarDatos());
    }
    
    @Test
    void testCargarDatosAeropuertos() {
        assertDoesNotThrow(() -> skyManager.cargarDatosAeropuertos("resources\\AeropuertosData.dat"));
    }

    @Test
    void testGetCosteBaseLlegada() {
    	skyManager.setCosteBaseLlegada(10);
        assertEquals(10.0, skyManager.getCosteBaseLlegada());
    }

    @Test
    void testGetCosteBaseSalida() {
    	skyManager.setCosteBaseSalida(40);
        assertEquals(40.0, skyManager.getCosteBaseSalida());
    }

    @Test
    void testGetCosteExtraMercancias() {
    	skyManager.setCosteExtraMercancias(100);
        assertEquals(100, skyManager.getCosteExtraMercancias());
    }

    @Test
    void testGetCosteExtraPasajeros() {
    	skyManager.setCosteExtraPasajeros(40);
        assertEquals(40.0, skyManager.getCosteExtraPasajeros());
    }

    @Test
    void testGetAeropuertosExternos() {
        assertNotNull(skyManager.getAeropuertosExternos());
    }

    @Test
    void testGetInformacionPropia() {
    	ArrayList<Temporada> temps = new ArrayList<Temporada>(Arrays.asList(new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31)), 
                new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31))));
    	Aeropuerto aeropuerto = new Aeropuerto("Aeropuerto Internacional", "ABC123", "Madrid", "España", 15.5, 1, temps, Direccion.NORTE);
    	skyManager.setInformacionPropia(aeropuerto);
        assertNotNull(skyManager.getInformacionPropia());
    }

    @Test
    void testGetUsuarios() {
        assertNotNull(skyManager.getUsuarios());
        assertTrue(skyManager.getUsuarios() instanceof HashMap);
    }

    @Test
    void testGetVuelos() {
        assertNotNull(skyManager.getVuelos());
        assertTrue(skyManager.getVuelos() instanceof HashMap);
    }

    @Test
    void testGetAerolineas() {
        assertNotNull(skyManager.getAerolineas());
        assertTrue(skyManager.getAerolineas() instanceof HashMap);
    }

    @Test
    void testGetFacturas() {
        assertNotNull(skyManager.getFacturas());
        assertTrue(skyManager.getFacturas() instanceof HashMap);
    }
    
	@Test
    void testGetPistas() {
        assertNotNull(skyManager.getPistas());
        assertTrue(skyManager.getPistas() instanceof HashMap);
    }
	
	@Test
    void testGetFingers() {
        assertNotNull(skyManager.getFingers());
        assertTrue(skyManager.getFingers() instanceof HashMap);
    }
	
	@Test
    void testGetZonasParking() {
        assertNotNull(skyManager.getZonasParking());
        assertTrue(skyManager.getZonasParking() instanceof HashMap);
    }
	
	@Test
    void testGetHangares() {
        assertNotNull(skyManager.getHangares());
        assertTrue(skyManager.getHangares() instanceof HashMap);
    }
	
	@Test
    void testGetUsuarioActual() {
        assertNull(skyManager.getUsuarioActual());
    }

    @Test
    void testSetCosteBaseLlegada() {
        skyManager.setCosteBaseLlegada(120.0);
        assertEquals(120.0, skyManager.getCosteBaseLlegada());
    }

    @Test
    void testSetCosteBaseSalida() {
        skyManager.setCosteBaseSalida(180.0);
        assertEquals(180.0, skyManager.getCosteBaseSalida());
    }

    @Test
    void testSetCosteExtraMercancias() {
        skyManager.setCosteExtraMercancias(60.0);
        assertEquals(60.0, skyManager.getCosteExtraMercancias());
    }

    @Test
    void testSetCosteExtraPasajeros() {
        skyManager.setCosteExtraPasajeros(85.0);
        assertEquals(85.0, skyManager.getCosteExtraPasajeros());
    }

	@Test
	void testSetInformacionPropia() {
		ArrayList<Temporada> temps = new ArrayList<Temporada>(Arrays.asList(new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31)), 
                new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31))));
    	Aeropuerto aeropuerto = new Aeropuerto("Aeropuerto Internacional", "ABC123", "Madrid", "España", 15.5, 1, temps, Direccion.NORTE);
    	skyManager.setInformacionPropia(aeropuerto);
    	assertTrue(skyManager.getInformacionPropia().equals(aeropuerto));
	}

	@Test
    void testRegistrarUsuario() {
		Usuario usuario = new Gestor("01020304B", "Gestor", "password");
        skyManager.registrarUsuario(usuario);
        assertTrue(skyManager.getUsuarios().containsKey("01020304A"));
    }

    @Test
    void testRegistrarAerolinea() {
        Aerolinea aerolinea = new Aerolinea("IBE", "Iberia");
        skyManager.registrarAerolinea(aerolinea);
        assertTrue(skyManager.getAerolineas().containsKey("IBE"));
    }
    
    @Test
    void testRegistrarVuelo() {
		skyManager.registrarAerolinea(a);
        skyManager.registrarVuelo(vuelo);
        assertTrue(skyManager.getVuelos().containsKey("H1893"));
    }

    @Test
    void testRegistrarAeropuertoExterno() {
    	ArrayList<Temporada> temps = new ArrayList<Temporada>(Arrays.asList(new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31)), 
                new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31))));
    	Aeropuerto aeropuerto = new Aeropuerto("Aeropuerto Internacional", "ABC123", "Madrid", "España", 15.5, 1, temps, Direccion.NORTE);
        skyManager.registrarAeropuertoExterno(aeropuerto);
        assertTrue(skyManager.getAeropuertosExternos().containsKey("ABC123"));
    }

    @Test
    void testRegistrarFactura() {
    	Aerolinea a = new Aerolinea("IBE", "Iberia");
        Factura factura = new Factura("010203", 1000, 0, null, a, null);
        skyManager.registrarFactura(factura);
        assertTrue(skyManager.getFacturas().containsKey("010203"));
    }

    @Test
    void testRegistrarTerminal() {
        Terminal terminal = new TerminalMercancias("Terminal1", LocalDate.now(), 3, "PU", 30.0);
        skyManager.registrarTerminal(terminal);
        assertTrue(skyManager.getTerminales().containsKey("Terminal1"));
    }

    @Test
    void testRegistrarPista() {
        Pista pista = new Pista("Pista1", LocalDate.now(), true, 200);
        skyManager.registrarPista(pista);
        assertTrue(skyManager.getPistas().containsKey(pista.getId()));
    }

    @Test
    void testRegistrarFinger() {
        Finger finger = new Finger("Finger1", 0, null, 0);
        skyManager.registrarFinger(finger);
        assertTrue(skyManager.getFingers().containsKey("Finger1"));
    }

    @Test
    void testRegistrarZonaParking() {
        ZonaParking zonaParking = new ZonaParking("Parking1", 0, null, 0, 0, 0, 0);
        skyManager.registrarZonaParking(zonaParking);
        assertTrue(skyManager.getZonasParking().containsKey("Parking1"));
    }

    @Test
    void testRegistrarHangar() {
        Hangar hangar = new HangarMercancias("Hangar1", 0, null, 0, 0, 0, 0);
        skyManager.registrarHangar(hangar);
        assertTrue(skyManager.getHangares().containsKey("Hangar1"));
    }

	@Test
	void testLogIn() {
		Usuario usuario = new Gestor("11111111A", "Gestor", "password");
		skyManager.registrarUsuario(usuario);
		skyManager.logIn("11111111A", "password");
		assertEquals("11111111A", skyManager.getUsuarioActual().getDni());
	}

	@Test
    void testBuscarVueloPorCodigo() {
		skyManager.registrarAerolinea(a);
        skyManager.registrarVuelo(vuelo);
        assertEquals(vuelo.getId(), skyManager.buscarVueloPorCodigo(vuelo.getId()).getId());
    }

	@Test
	void testBuscarVuelosPorTerminal() {
		skyManager.registrarAerolinea(a);
        skyManager.registrarVuelo(vuelo);
        Terminal terminal = new TerminalMercancias("Terminal1", LocalDate.now(), 3, "PU", 30.0);
        skyManager.registrarTerminal(terminal);
        terminal.addVuelo(vuelo);
        assertTrue(skyManager.buscarVuelosPorTerminal(terminal).contains(vuelo));
	}

	@Test
	void testBuscarVuelosPorHoraLlegada() {
		skyManager.registrarAerolinea(a);
        skyManager.registrarVuelo(vuelo);
        assertTrue(skyManager.buscarVuelosPorHoraLlegada(vuelo.getHoraLlegada()).size()>0);
	}

	@Test
	void testBuscarVuelosPorHoraSalida() {
		skyManager.registrarAerolinea(a);
        skyManager.registrarVuelo(vuelo);
        assertTrue(skyManager.buscarVuelosPorHoraSalida(vuelo.getHoraSalida()).size()>0);
	}

	@Test
	void testVerEstadisticasGestor() {
		Usuario usuario = new Gestor("11111111A", "Gestor", "password");
		skyManager.registrarUsuario(usuario);
		skyManager.logIn("11111111A", "password");
		assertTrue(skyManager.verEstadisticasGestor().length()>0);
	}
	
	@Test
	void testVerFacturasPorEstatusDePago() {
		Factura factura = new Factura("INV-999", 500.0, 750.0, LocalDate.now(), a, "logo.png");
		skyManager.registrarFactura(factura);
		ArrayList<Factura> facs = skyManager.verFacturasPorEstatusDePago(false);
		int flag =0;
		for (Factura f: facs) {
			if (f.getId().equals("INV-999")) {
				assertTrue(true);
				flag = 1;
			}
		}
		if(flag==0) {
			assertTrue(false);
		}
	}
	
	@Test
	void testGetTerminalesDisponibles() {
		Terminal terminal = new TerminalMercancias("Terminal1"+LocalDateTime.now(), LocalDate.now(), 3, "PU", 30.0);
		
		skyManager.registrarTerminal(terminal);
		
		ArrayList<Terminal> terminales = skyManager.getTerminalesDisponibles(vuelo);
		int flag =0;
		for (Terminal t: terminales) {
			if (t.getId().equals(terminal.getId())) {
				assertTrue(true);
				flag = 1;
			}
		}
		if(flag==0) {
			assertTrue(false);
		}
	}

}
