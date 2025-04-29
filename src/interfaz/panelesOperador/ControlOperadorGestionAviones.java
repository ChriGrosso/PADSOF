package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

public class ControlOperadorGestionAviones implements ActionListener{
	
	public ControlOperadorGestionAviones() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Nuevo Avión")) {     
			this.nuevoAvion();
		}
		else if (e.getActionCommand().equals("Nuevo Tipo Avión")) {     
			this.nuevoTipoAvion();
		}
	}
	
	
	private void nuevoAvion() {
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showNuevoAvion();
	}
	
	private void nuevoTipoAvion() {
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showNuevoTipoAvion();
	}
}
