package interfaz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilidadesNotificaciones {
    public static String extraerIdVuelo(String mensaje) {
        Pattern pattern = Pattern.compile("vuelo\\s+(\\S+)\\s+con");
        Matcher matcher = pattern.matcher(mensaje);

        if (matcher.find()) {
            return matcher.group(1); // El primer grupo de captura
        }
        return null; // No encontrado
    }
}
