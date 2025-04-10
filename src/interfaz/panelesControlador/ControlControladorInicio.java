package interfaz.panelesControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import interfaz.Login;
import sistema.SkyManager;


public class ControlControladorInicio implements ActionListener {
	private Login vista;
	private SkyManager modelo;
	
	public ControlControladorInicio() {
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
		this.vista.setVisible(false);
		Aplicacion.getInstance().showLogin();
	}
	
	private void verNotificaciones() {
		modelo.guardarDatos();
		this.vista.setVisible(false);
		Aplicacion.getInstance().showNotificaciones();
	}
}
