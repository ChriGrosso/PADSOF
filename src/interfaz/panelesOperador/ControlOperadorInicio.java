package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlOperadorInicio implements ActionListener{
	private SkyManager modelo;
	
	public ControlOperadorInicio() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("🔓 Cerrar Sesión")) {     // si se ha pulsado "Cerrar Sesión"
			this.cerrarSesion();
		}
		else if (e.getActionCommand().equals("🔔 Notificaciones")) {     // si se ha pulsado "Notificaciones"
			this.verNotificaciones();
		}
	}

	
	private void cerrarSesion() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showLogin();
	}
	
	private void verNotificaciones() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showNotificaciones();
	}
}
