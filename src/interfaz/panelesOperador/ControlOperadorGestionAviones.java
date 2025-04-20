package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlOperadorGestionAviones implements ActionListener{
	private SkyManager modelo;
	
	public ControlOperadorGestionAviones() {
		this.modelo = SkyManager.getInstance();
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
