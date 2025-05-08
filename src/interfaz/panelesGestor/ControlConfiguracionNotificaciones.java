package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlConfiguracionNotificaciones implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "CONFIRMAR_A":
            	this.confirmarA();
                break;
            case "CONFIRMAR_V":
            	this.confirmarV();
                break;

            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
    
    private void confirmarA() {
    	
    }
    
    private void confirmarV() {
    	
    }
}
