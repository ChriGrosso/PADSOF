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
		if (e.getActionCommand().equals("Cerrar Sesión")) {     // si se ha pulsado "Cerrar Sesión"
			this.cerrarSesion();
		}
		else if (e.getActionCommand().equals("Notificaciones")) {     // si se ha pulsado "Notificaciones"
			this.verNotificaciones();
		}
		else if (e.getActionCommand().equals("Nuevo Avión")) {     // si se ha pulsado "Notificaciones"
			this.nuevoAvion();
		}
		else if (e.getActionCommand().equals("Nuevo Tipo Avión")) {     // si se ha pulsado "Notificaciones"
			this.nuevoTipoAvion();
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
	
	private void nuevoAvion() {
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showNuevoAvion();
	}
	
	private void nuevoTipoAvion() {
		Aplicacion.getInstance().getOpAviones().setVisible(false);
		Aplicacion.getInstance().showNuevoTipoAvion();
	}
}
