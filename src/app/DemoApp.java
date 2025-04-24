package app;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import aerolineas.*;
import aeropuertos.*;
import aviones.*;
import elementos.*;
import facturas.*;
import sistema.*;
import usuarios.Controlador;
import usuarios.Gestor;
import usuarios.Operador;
import vuelos.EstadoVuelo;
import vuelos.Periodicidad;
import vuelos.Vuelo;
import vuelos.VueloPasajeros;
import vuelos.VueloMercancias;


public class DemoApp {
    private static SkyManager app;

    public static void main(String[] args) {
        File archivo = new File("resources\\skyManagerDatos.dat");
        if (archivo.exists()) { // Verifica si el archivo existe
        	archivo.delete();  //eliminamos datos antiguos para que sea mas facil ver como se van añadiendo los objetos en esta demo
        }
        //obtener la instancia del sistema
        app = SkyManager.getInstance();
        
        //actualizar las pistas de despegue cada 5 minutos
        Runnable tareaActualizarCola = () -> {
			for(Pista pista: app.getPistas().values())
			    if (pista.isDespegue()) {
			        pista.actualizarColaVuelos();
			    }
		};
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(tareaActualizarCola, 0, 5, TimeUnit.MINUTES);
        
        //iniciar sesión como el gestor del sistema (generado automáticamente al crear la app)
        app.logIn("01020304A", "password123");
        
        configurarAeropuerto();
        app.cargarDatosAeropuertos("resources//AeropuertosData.dat");
        
        registrarUsuarios();
       
        //Configurar notificaciones gestor
        Gestor g = (Gestor) app.getUsuarios().get("01020304A");
        g.seguirCamEstado(EstadoVuelo.EN_TIEMPO, EstadoVuelo.RETRASADO);
        g.seguirCamEstado(EstadoAvion.EN_FINGER, EstadoAvion.EN_HANGAR);
        
        
        //Ahora vamos a iniciar sesion como un operador
        app.logIn("67891234A", "SkyOps2025!");
        
        darDeAltaAviones();
        programarVuelos();
        System.out.println(app);
        
        //el gestor debe haber recibo las notificaciones de que debe asignar terminal y controlador a los vuelos
        System.out.println("Notificaciones Gestor: "+g.getNotificaciones());
        Vuelo v = app.buscarVueloPorCodigo("AE0010000");
        //asignamos el vuelo a una terminal y un controlador disponibles.
        Terminal t = app.getTerminalesDisponibles(v).get(0);
        v.asignarTerminal(t);
        t.getControladores().get(0).asignarVuelo(v);
        
        //el gestor va a seguir este vuelo
        v.addObserver(g); 
        
        //Simulacion de que el vuelo se realice
        v.setEstVuelo(EstadoVuelo.ESPERANDO_PISTA_A);
        v.asignarPista(app.getPistasDisponibles(v).get(0));
        v.asignarLocAterrizaje(app.getFingersDisponibles(v).get(0));
        
        v.setEstVuelo(EstadoVuelo.ESPERANDO_ATERRIZAJE);
        v.setEstVuelo(EstadoVuelo.ATERRIZADO);
        
        v.asignarPuerta(t.getPuertasDisponibles().get(0));
        
        v.setEstVuelo(EstadoVuelo.DESEMBARQUE_INI);
        v.setEstVuelo(EstadoVuelo.DESEMBARQUE_FIN);
        v.getAvion().asignarHangar(app.getHangaresDisponibles(v.getAvion()).get(0));
        v.setEstVuelo(EstadoVuelo.EN_HANGAR);
        System.out.println(app);
        
        v.getPuerta().getHistorailUsos().get(v).setHoraDesuso(v.getPuerta().getHistorailUsos().get(v).getHoraDesuso().plusHours(1));
        v.getLocAterrizaje().getHistorailUsos().get(v).setHoraDesuso(v.getLocAterrizaje().getHistorailUsos().get(v).getHoraDesuso().plusHours(1));
        
        app.logIn("01020304A", "password123");
        System.out.println("Estadisticas del Gestor: \n"+app.verEstadisticasGestor());
        
        
        
       //Generar una factura y pagarla para que se vea que funciona
        Aerolinea a = v.getAerolinea();
        generarFacturaMensual(a);
        
        
        
       //Generar las estadisticas
        EstadisticasVuelos est = a.getEstadisticas();
        ArrayList<Vuelo> vuelos = est.vuelosEnTiempo();
        System.out.println("Vuelos en tiempo de la aerolinea: " + a.getNombre() + " (" + est.numVuelosEnTiempo() + ")\n");
        for(Vuelo v2: vuelos) {
        	System.out.println(v2.toString() + "\n");
        }
        vuelos = est.vuelosRetrasados();
        System.out.println("Vuelos retrasados de la aerolinea: " + a.getNombre() + " (" + est.numVuelosRetrasados() + ")\n");
        for(Vuelo v2: vuelos) {
        	System.out.println(v2.toString() + "\n");
        }

        
        
      //Verificar que los datos se mantienen correctamente al guardarlos y cargarlos
        app.guardarDatos();
        scheduler.shutdown();
        SkyManager app2 = SkyManager.getInstance() ;
        System.out.println("\n\n Instancia 2:\n");
        System.out.println(app2);
        scheduler.shutdown();
    }


    
    
    private static void configurarAeropuerto() {
    	ArrayList<Temporada> temporadas = new ArrayList<>(Arrays.asList(new Temporada(MonthDay.of(Month.JANUARY, 1), LocalTime.of(8, 0), LocalTime.of(10, 30), MonthDay.of(Month.JULY, 31)),
    			new Temporada(MonthDay.of(Month.AUGUST, 1), LocalTime.of(7, 0), LocalTime.of(11, 00), MonthDay.of(Month.DECEMBER, 31))));
    	Aeropuerto infoPropia = new Aeropuerto("Aeropuerto Internacional Solaria", "SOL", "Madrid", "España", 0, 0, temporadas, Direccion.NORTE);
    	app.setInformacionPropia(infoPropia);
    	app.setCosteBaseLlegada(20);
    	app.setCosteBaseSalida(35);
    	app.setCosteExtraMercancias(25);
    	app.setCosteExtraPasajeros(10);
    	app.setDiasAntelacionProgVuelo(3);
    	
    	//Configurar infraestructura aeropuerto
    	//2 terminales con 2 puertas cada una
    	Terminal t1 = new TerminalMercancias("TM0000", LocalDate.now(), 2, "PM", 300);
    	app.registrarTerminal(t1);
    	Terminal t2 = new TerminalPasajeros("TP0000", LocalDate.now(), 2, "PP", 500);
    	app.registrarTerminal(t2);
    	
    	//2 Hangares
    	Hangar h1 = new HangarMercancias("HM0000", 20, LocalDate.now(), 35, 28, 72, 80);
    	app.registrarHangar(h1);
    	Hangar h2 = new HangarPasajeros("HP0000", 15, LocalDate.now(), 30, 25, 71, 80);
    	app.registrarHangar(h2);
    	
    	//3 pistas
    	Pista p1 = new Pista("PS0000", LocalDate.now(), false, 1500);
    	app.registrarPista(p1);
    	Pista p2 = new Pista("PS0001", LocalDate.now(), true, 1500);
    	app.registrarPista(p2);
    	Pista p3 = new Pista("PS0002", LocalDate.now(), true, 1500);
    	app.registrarPista(p3);
    	
    	//2 fingers
    	Finger f1 = new Finger("FI0000", 10, LocalDate.now(), 26);
    	app.registrarFinger(f1);
    	Finger f2 = new Finger("FI0001", 10, LocalDate.now(), 28);
    	app.registrarFinger(f2);
    	
    	//2 Zonas de Parking
    	ZonaParking zk1 = new ZonaParking("PK0000", 12, LocalDate.now(), 20, 26, 82, 82);
    	app.registrarZonaParking(zk1);
    	ZonaParking pk2 = new ZonaParking("PK0001", 12, LocalDate.now(), 25, 28, 82, 83);
    	app.registrarZonaParking(pk2);
    	
    	
    	//3 Aerolineas
    	Aerolinea ae1 = new Aerolinea("AE000", "American Airlines");
    	app.registrarAerolinea(ae1);
    	Aerolinea ae2 = new Aerolinea("AE001", "Delta Air Lines");
    	app.registrarAerolinea(ae2);
    	Aerolinea ae3 = new Aerolinea("AE002", "United Airlines");
    	app.registrarAerolinea(ae3);
    	
    }
    
    private static void registrarUsuarios() {
    	ArrayList<Terminal> terminales = new ArrayList<Terminal>(app.getTerminales().values());
    	ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>(app.getAerolineas().values());
    	Terminal t1 = app.getTerminales().get("TP0000"), t2 = app.getTerminales().get("TM0000");
    	//tres controladores
    	Controlador c1 = new Controlador("34567891X", "Javier Gómez", "TowerCtrl!2025", terminales.get(0));
    	app.registrarUsuario(c1);
    	c1.setTerminal(t1);//le hemos asignado una terminal de pasajeros
    	t1.addControlador(c1);
    	Controlador c2 = new Controlador("45678912Y", "Laura Méndez", "ApproachSafe2024$", terminales.get(1));
    	app.registrarUsuario(c2);
    	c2.setTerminal(t1);
    	t1.addControlador(c2);
    	Controlador c3 = new Controlador("56789123Z", "Miguel Torres", "GroundCtrl!2025", terminales.get(0));
    	app.registrarUsuario(c3);
    	c3.setTerminal(t2);
    	t2.addControlador(c3);
    	
    	//cuatro operadores
    	Operador o1 = new Operador("67891234A", "Ana Ramírez", "SkyOps2025!", aerolineas.get(0));
    	app.registrarUsuario(o1);
    	Operador o2 = new Operador("78912345B", "Daniel Fernández", "AeroFlight2024@", aerolineas.get(1));
    	app.registrarUsuario(o2);
    	Operador o3 = new Operador("89012345C", "Sergio López", "FixJet#2025", aerolineas.get(1));
    	app.registrarUsuario(o3);
    	Operador o4 = new Operador("90123456D", "Patricia Navarro", "RoutePlan#2024", aerolineas.get(2));
    	app.registrarUsuario(o4);
    	
    	
    }
    
    private static void darDeAltaAviones() {
    	ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>(app.getAerolineas().values());
    	
    	TipoAvion ta1 = new AvionMercancias("Boeing", "747-8F", 14200.0, 19.4, 68.4, 69.3, 10, false);
    	TipoAvion ta2 = new AvionPasajeros("Airbus", "A350-900", 15000.0, 17.0, 64.8, 66.8, 440);
    	aerolineas.get(0).addTipoAvion(ta1);
    	aerolineas.get(1).addTipoAvion(ta2);
    	aerolineas.get(2).addTipoAvion(ta1);
    	Avion a1 = new Avion ("AV-9876XP", LocalDate.now(), ta1);
    	aerolineas.get(0).addAvion(a1);
    	Avion a2 = new Avion ("AV-4532LM", LocalDate.now(), ta2);
    	aerolineas.get(1).addAvion(a2);
    	Avion a3 = new Avion ("AV-1209QW", LocalDate.now(), ta1);
    	aerolineas.get(2).addAvion(a3);
    }
    
    private static void programarVuelos() {
    	ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>(app.getAerolineas().values());
    	ArrayList<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>(app.getAeropuertosExternos().values());
    	Avion avion = aerolineas.get(1).getAviones().get("AV-4532LM");
    	
    	//Vuelo de pasajeros de llegada
    	Vuelo v1 = new VueloPasajeros(aeropuertos.get(0), aeropuertos.get(2), LocalDateTime.of(2025, 5, 10, 14, 30), LocalDateTime.of(2025, 5, 10, 17, 45), 
    			new ArrayList<Aerolinea>(Arrays.asList(aerolineas.get(0), aerolineas.get(1))), true, 150, Periodicidad.NO_PERIODICO, avion);
    	aerolineas.get(0).addVuelo(v1); 
    	aerolineas.get(1).addVuelo(v1);
    	app.registrarVuelo(v1);
    	String s = "Vuelo "+v1.getId()+" a espera de asignación de Terminal y controlador";
    	app.getUsuarioActual().enviarNotificacion(s, app.getUsuarios().get("01020304A"));
    	
    	//Vuelo de Mercancias de llegada
    	avion = aerolineas.get(2).getAviones().get("AV-1209QW");
    	Vuelo v2 = new VueloMercancias(aeropuertos.get(4), aeropuertos.get(3), LocalDateTime.of(2025, 4, 15, 12, 30), LocalDateTime.of(2025, 4, 15, 15, 45), 
    			new ArrayList<Aerolinea>(Arrays.asList(aerolineas.get(2))), false, 10, false, Periodicidad.NO_PERIODICO, avion);
    	aerolineas.get(2).addVuelo(v2);
    	app.registrarVuelo(v2);
    	s = "Vuelo "+v2.getId()+" a espera de asignación de Terminal y controlador";
    	app.getUsuarioActual().enviarNotificacion(s, app.getUsuarios().get("01020304A"));
    	
    }
    
    public static void generarFacturaMensual(Aerolinea aerolinea) {
        LocalDate hoy = LocalDate.now();
        String idFactura = "FAC-" + aerolinea.getId() + "-" + hoy.toString();
        String logo = "./resources/logo.png";

        Factura factura = new Factura(idFactura, 100.0, 0.0, hoy, aerolinea, logo);

        // Aggiunge tutti gli usi — sarà la Factura a filtrare quelli del mese precedente
        for (Uso uso : aerolinea.getArrayUsos()) {
            factura.addUso(uso);
        }

        double total = factura.calcularFactura(aerolinea);
        System.out.println(total);
        factura.setTotal(total);
        app.registrarFactura(factura);

        try {
            String path = "./resources/" + idFactura + ".pdf";
            factura.generarFactura(path);
            System.out.println("Factura mensual generada: " + path);
        } catch (Exception e) {
            System.out.println("Error al generar la factura: " + e.getMessage());
        }
    }

    
    
}
