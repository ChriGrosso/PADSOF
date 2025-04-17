package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sistema.SkyManager;
import interfaz.Aplicacion;

public class ControlNuevoAvion implements ActionListener{
	private SkyManager modelo;
	
	public ControlNuevoAvion() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cerrar Sesión")) {     // si se ha pulsado "Cerrar Sesión"
			this.cerrarSesion();
		}
		else if (e.getActionCommand().equals("Notificaciones")) {     // si se ha pulsado "Notificaciones"
			this.verNotificaciones();
		}
		
	}

	
	private void cerrarSesion() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showLogin();
	}
	
	private void verNotificaciones() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showNotificaciones();
	}
}
