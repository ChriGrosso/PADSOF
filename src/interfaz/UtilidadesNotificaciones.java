package interfaz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilidadesNotificaciones {
	/**
     * Extrae el identificador del vuelo desde un mensaje de notificación.
     * El formato esperado en el mensaje es: "vuelo <ID>:"
     *
     * @param mensaje El mensaje de notificación que contiene el ID del vuelo.
     * @return Una cadena con el ID del vuelo si se encuentra, o {@code null} si no se detecta ninguna coincidencia.
     */
    public static String extraerIdVuelo(String mensaje) {
        Pattern pattern = Pattern.compile("vuelo\\s+(\\S+)\\s+con");
        Matcher matcher = pattern.matcher(mensaje);

        if (matcher.find()) {
            return matcher.group(1); // El primer grupo de captura
        }
        return null; // No encontrado
    }
    
    /**
     * Extrae todas las fechas y horas en formato ISO_LOCAL_DATE_TIME (yyyy-MM-ddTHH:mm)
     * presentes dentro de un mensaje de notificación.
     *
     * @param mensaje El mensaje del cual se extraerán las fechas.
     * @return Una lista de objetos LocalDateTime encontrados en el mensaje.
     *         Si no se encuentran coincidencias, se devuelve una lista vacía.
     */
    public static List<LocalDateTime> extraerFechas(String mensaje) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(mensaje);
        List<LocalDateTime> fechas = new ArrayList<>();
        while (matcher.find()) {
            fechas.add(LocalDateTime.parse(matcher.group()));
        }
        return fechas;
    }
    
    /**
     * Extrae el identificador del vuelo desde un mensaje de notificación que muestra horas alternativas para un vuelo.
     * El formato esperado en el mensaje es: "vuelo <ID>:"
     *
     * @param mensaje El mensaje de notificación que contiene el ID del vuelo.
     * @return Una cadena con el ID del vuelo si se encuentra, o {@code null} si no se detecta ninguna coincidencia.
     */
    public static String extraerIdVueloEnHorasAlternativas(String mensaje) {
        Pattern pattern = Pattern.compile("vuelo (\\w+):");
        Matcher matcher = pattern.matcher(mensaje);
        return matcher.find() ? matcher.group(1) : null;
    }
}
