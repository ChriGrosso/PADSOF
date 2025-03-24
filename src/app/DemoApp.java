package app;

import java.io.*;
import java.util.*;
import aerolineas.*;
import aeropuertos.*;
import aviones.*;
import elementos.*;
import facturas.*;
import notificaciones.*;
import sistema.*;
import vuelos.*;

public class DemoApp {

    private static final String DATA_FILE = System.getProperty("user.home") + File.separator + "aeropuerto.dat";
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Vuelo> vuelos = new ArrayList<>();
    private static List<ElementoEstructural> elementos = new ArrayList<>();
    private static List<Aerolinea> aerolineas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatos();

        System.out.println("Bienvenido a SkyManager - DemoApp");

        Usuario admin = new Usuario("12345678A", "admin123", "Admin");
        if (usuarios.stream().noneMatch(u -> u.getDni().equals(admin.getDni()))) {
            usuarios.add(admin);
        }

        Usuario logueado = login();
        if (logueado == null || !"Admin".equals(logueado.getTipo())) {
            System.out.println("Acceso denegado.");
            return;
        }
        
        crearUsuariosDemo();
        configurarAeropuertoDemo();
        programarVuelosDemo();
        generarFacturacionDemo();

        boolean salir = false;
        while (!salir) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear usuarios demo");
            System.out.println("2. Configurar aeropuerto demo");
            System.out.println("3. Programar vuelos demo");
            System.out.println("4. Generar facturación demo");
            System.out.println("5. Ver usuarios");
            System.out.println("6. Ver vuelos");
            System.out.println("7. Guardar datos");
            System.out.println("8. Cargar datos");
            System.out.println("9. Crear vuelo manual");
            System.out.println("10. Ver facturación y simular pago");
            System.out.println("11. Ver facturas pendientes");
            System.out.println("12. Buscar vuelos por destino");
            System.out.println("13. Buscar vuelos por fecha (YYYY-MM-DD)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> crearUsuariosDemo();
                case "2" -> configurarAeropuertoDemo();
                case "3" -> programarVuelosDemo();
                case "4" -> generarFacturacionDemo();
                case "5" -> usuarios.forEach(System.out::println);
                case "6" -> vuelos.forEach(System.out::println);
                case "7" -> guardarDatos();
                case "8" -> cargarDatos();
                case "9" -> crearVueloManual();
                case "10" -> verFacturacionYSimularPago();
                case "11" -> verFacturasPendientes();
                case "12" -> buscarVuelosPorDestino();
                case "13" -> buscarVuelosPorFecha();
                case "0" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
        
    }

    private static Usuario login() {
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        return usuarios.stream()
                .filter(u -> u.getDni().equals(dni) && u.getContrasena().equals(pass))
                .findFirst()
                .orElse(null);
    }

    private static void crearUsuariosDemo() {
        if (usuarios.stream().noneMatch(u -> u.getDni().equals("98765432B"))) {
            usuarios.add(new Usuario("98765432B", "op123", "Operador"));
        }
        if (usuarios.stream().noneMatch(u -> u.getDni().equals("11223344C"))) {
            usuarios.add(new Usuario("11223344C", "ctrl123", "Controlador"));
        }
    }

    private static void configurarAeropuertoDemo() {
        elementos.add(new Pista(1, "Despegue"));
        elementos.add(new Pista(2, "Aterrizaje"));
        elementos.add(new Terminal("T1", 100));
        elementos.add(new Hangar("H1", 5));
    }

    private static void programarVuelosDemo() {
        Aerolinea al = new Aerolinea("AirDemo", "ADM");
        aerolineas.add(al);

        Avion a = new Avion("EC-DMO", "Pasajeros", 180, 0);
        Date fecha = new Date();
        Vuelo v = new Vuelo("Madrid", "Barcelona", a, fecha, "Programado");

        boolean conflicto = vuelos.stream().anyMatch(
            vuelo -> Math.abs(vuelo.getFecha().getTime() - v.getFecha().getTime()) < 3600000
        );

        if (!conflicto) {
            vuelos.add(v);
            System.out.println("Vuelo programado correctamente.");
        } else {
            System.out.println("Conflicto de horario: ya existe un vuelo en una hora cercana.");
        }
    }

    private static void generarFacturacionDemo() {
        System.out.println("Generando factura para aerolíneas...");
        for (Aerolinea a : aerolineas) {
            System.out.println("Factura generada para " + a.getNombre() + " (500 EUR)");
        }
    }

    private static void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(usuarios);
            out.writeObject(vuelos);
            out.writeObject(elementos);
            out.writeObject(aerolineas);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    private static void crearVueloManual() {
        System.out.print("Origen: ");
        String origen = scanner.nextLine();
        System.out.print("Destino: ");
        String destino = scanner.nextLine();
        System.out.print("Fecha (timestamp en ms): ");
        long timestamp = Long.parseLong(scanner.nextLine());
        Date fecha = new Date(timestamp);
        System.out.print("Matrícula avión: ");
        String matricula = scanner.nextLine();
        System.out.print("Tipo (Pasajeros/Mercancias): ");
        String tipo = scanner.nextLine();
        System.out.print("Plazas: ");
        int plazas = Integer.parseInt(scanner.nextLine());
        System.out.print("Carga (kg): ");
        double carga = Double.parseDouble(scanner.nextLine());

        Avion avion = new Avion(matricula, tipo, plazas, carga);
        Vuelo nuevoVuelo = new Vuelo(origen, destino, avion, fecha, "Programado");

        boolean conflicto = vuelos.stream().anyMatch(
            vuelo -> Math.abs(vuelo.getFecha().getTime() - nuevoVuelo.getFecha().getTime()) < 3600000
        );

        if (!conflicto) {
            vuelos.add(nuevoVuelo);
            System.out.println("Vuelo creado exitosamente.");
        } else {
            System.out.println("Conflicto de horario detectado. Vuelo no creado.");
        }
    }

    private static void verFacturacionYSimularPago() {
        if (aerolineas.isEmpty()) {
            System.out.println("No hay aerolíneas registradas.");
            return;
        }

        for (Aerolinea a : aerolineas) {
            if (a.isFacturaPagada()) {
                System.out.println("Factura para " + a.getNombre() + ": YA PAGADA");
                continue;
            }
            System.out.println("Factura para " + a.getNombre() + ": 500 EUR");
            System.out.print("¿Desea pagar esta factura? (s/n): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                a.setFacturaPagada(true);
                System.out.println("Factura pagada con éxito.");
            } else {
                System.out.println("Factura pendiente de pago.");
            }
        }
    }

    private static void verFacturasPendientes() {
        boolean hayPendientes = false;
        for (Aerolinea a : aerolineas) {
            if (!a.isFacturaPagada()) {
                System.out.println("Factura pendiente: " + a.getNombre() + " (500 EUR)");
                hayPendientes = true;
            }
        }
        if (!hayPendientes) {
            System.out.println("No hay facturas pendientes.");
        }
    }

    private static void buscarVuelosPorDestino() {
        System.out.print("Ingrese destino a buscar: ");
        String destino = scanner.nextLine().trim().toLowerCase();
        boolean encontrados = false;
        for (Vuelo v : vuelos) {
            if (v.getDestino().toLowerCase().contains(destino)) {
                System.out.println(v);
                encontrados = true;
            }
        }
        if (!encontrados) {
            System.out.println("No se encontraron vuelos con ese destino.");
        }
    }

    private static void buscarVuelosPorFecha() {
        System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
        String input = scanner.nextLine();
        try {
            String[] parts = input.split("-");
            Calendar target = Calendar.getInstance();
            target.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            target.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
            target.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
            target.set(Calendar.HOUR_OF_DAY, 0);
            target.set(Calendar.MINUTE, 0);
            target.set(Calendar.SECOND, 0);
            target.set(Calendar.MILLISECOND, 0);
            long start = target.getTimeInMillis();
            long end = start + 86400000;

            boolean encontrados = false;
            for (Vuelo v : vuelos) {
                long t = v.getFecha().getTime();
                if (t >= start && t < end) {
                    System.out.println(v);
                    encontrados = true;
                }
            }
            if (!encontrados) {
                System.out.println("No se encontraron vuelos para esa fecha.");
            }
        } catch (Exception e) {
            System.out.println("Formato inválido. Use YYYY-MM-DD.");
        }
    }

    private static void cargarDatos() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            usuarios = (List<Usuario>) in.readObject();
            vuelos = (List<Vuelo>) in.readObject();
            elementos = (List<ElementoEstructural>) in.readObject();
            aerolineas = (List<Aerolinea>) in.readObject();
            System.out.println("Datos cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }
}

// Clases base semplificate per il dimostratore
class Usuario implements Serializable {
    private String dni, contrasena, tipo;
    public Usuario(String d, String c, String t) { dni = d; contrasena = c; tipo = t; }
    public String getDni() { return dni; }
    public String getContrasena() { return contrasena; }
    public String getTipo() { return tipo; }
    @Override public String toString() {
        return "Usuario{" + "dni='" + dni + '\'' + ", tipo='" + tipo + '\'' + '}';
    }
}

class Vuelo implements Serializable {
    private String origen, destino;
    private Avion avion;
    private Date fecha;
    private String estado;
    public Vuelo(String o, String d, Avion a, Date f, String e) {
        origen = o; setDestino(d); avion = a; setFecha(f); estado = e;
    }
    @Override public String toString() {
        return "Vuelo{" + "origen='" + origen + '\'' + ", destino='" + getDestino() + '\'' + ", estado='" + estado + '\'' + '}';
    }
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
}

class Avion implements Serializable {
    private String matricula, tipo;
    private int plazas;
    private double carga;
    public Avion(String m, String t, int p, double c) {
        matricula = m; tipo = t; plazas = p; carga = c;
    }
    @Override public String toString() {
        return "Avion{" + "matricula='" + matricula + '\'' + ", tipo='" + tipo + '\'' + '}';
    }
}

class Aerolinea implements Serializable {
    private boolean facturaPagada = false;
    private String nombre, codigo;
    public Aerolinea(String n, String c) { nombre = n; codigo = c; }
    public String getNombre() { return nombre; }
    public boolean isFacturaPagada() { return facturaPagada; }
    public void setFacturaPagada(boolean pagada) { this.facturaPagada = pagada; }
    @Override public String toString() {
        return "Aerolinea{" + "nombre='" + nombre + '\'' + ", codigo='" + codigo + '\'' + '}';
    }
}

abstract class ElementoEstructural implements Serializable {}

class Pista extends ElementoEstructural {
    private int numero;
    private String tipo;
    public Pista(int n, String t) { numero = n; tipo = t; }
    @Override public String toString() {
        return "Pista{" + "numero=" + numero + ", tipo='" + tipo + '\'' + '}';
    }
}

class Terminal extends ElementoEstructural {
    private String nombre;
    private int capacidad;
    public Terminal(String n, int c) { nombre = n; capacidad = c; }
    @Override public String toString() {
        return "Terminal{" + "nombre='" + nombre + '\'' + ", capacidad=" + capacidad + '}';
    }
}

class Hangar extends ElementoEstructural {
    private String nombre;
    private int plazas;
    public Hangar(String n, int p) { nombre = n; plazas = p; }
    @Override public String toString() {
        return "Hangar{" + "nombre='" + nombre + '\'' + ", plazas=" + plazas + '}';
    }
}

