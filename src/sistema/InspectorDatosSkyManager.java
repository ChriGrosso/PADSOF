package sistema;

import elementos.Pista;
import elementos.Finger;
import elementos.ZonaParking;
import elementos.Hangar;
import elementos.Terminal;
import vuelos.Vuelo;
import usuarios.Usuario;
import facturas.Factura;
import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;

public class InspectorDatosSkyManager {
    public static void main(String[] args) {
        try {
            SkyManager modelo = SkyManager.getInstance();

            System.out.println("===== INSPECCIÓN SKYMANAGER =====\n");

            System.out.println(">>> Usuarios:");
            for (Usuario u : modelo.getUsuarios().values()) {
                System.out.println("- " + u.getDni() + ": " + u.getNombre());
            }

            System.out.println("\n>>> Aerolíneas:");
            for (Aerolinea a : modelo.getAerolineas().values()) {
                System.out.println("- " + a.getNombre());
            }

            System.out.println("\n>>> Aeropuertos Externos:");
            for (Aeropuerto a : modelo.getAeropuertosExternos().values()) {
                System.out.println("- " + a.getNombre() + " (" + a.getCodigo() + ")");
            }

            System.out.println("\n>>> Aeropuerto Propio:");
            Aeropuerto propio = modelo.getInformacionPropia();
            if (propio != null) {
                System.out.println("- " + propio.getNombre() + " (" + propio.getCodigo() + ")");
            } else {
                System.out.println("- No configurado");
            }

            System.out.println("\n>>> Vuelos:");
            for (Vuelo v : modelo.getVuelos().values()) {
                System.out.println("- ID: " + v.getId() + ", Origen: " + v.getOrigen().getCodigo() +
                        ", Destino: " + v.getDestino().getCodigo() + ", Estado: " + v.getEstVuelo());
            }

            System.out.println("\n>>> Pistas:");
            for (Pista p : modelo.getPistas().values()) {
                System.out.println("- " + p.getId() + " (" + (p.isDespegue() ? "Despegue" : "Aterrizaje") + ")");
            }

            System.out.println("\n>>> Fingers:");
            for (Finger f : modelo.getFingers().values()) {
                System.out.println("- " + f.getId());
            }

            System.out.println("\n>>> Zonas Parking:");
            for (ZonaParking z : modelo.getZonasParking().values()) {
                System.out.println("- " + z.getId());
            }

            System.out.println("\n>>> Hangares:");
            for (Hangar h : modelo.getHangares().values()) {
                System.out.println("- " + h.getId());
            }

            System.out.println("\n>>> Terminales:");
            for (Terminal t : modelo.getTerminales().values()) {
                System.out.println("- " + t.getId());
            }

            System.out.println("\n>>> Facturas:");
            for (Factura f : modelo.getFacturas().values()) {
                System.out.println("- " + f.getId() + " (Pagado: " + f.isPagado() + ")");
            }

            System.out.println("\n===== FIN INSPECCIÓN =====");

        } catch (Exception e) {
            System.out.println("Error cargando SkyManager: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

