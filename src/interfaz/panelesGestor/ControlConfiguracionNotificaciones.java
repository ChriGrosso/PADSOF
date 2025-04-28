package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlConfiguracionNotificaciones implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "CONFIGURAR":
            	
                break;

            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
}
