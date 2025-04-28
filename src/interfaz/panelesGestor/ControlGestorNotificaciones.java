package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

public class ControlGestorNotificaciones implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "CONFIGURAR":
            	Aplicacion.getInstance().showConfiguracionNotificaciones();
                break;

            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
}
