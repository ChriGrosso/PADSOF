package interfaz;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sistema.SkyManager;


public class ControlControladorInicio implements ActionListener {
	private Login vista;
	private Aplicacion frame;
	private SkyManager modelo;
	
	public ControlControladorInicio() {
		
		this.frame = Aplicacion.getInstance();
		this.vista = frame.getLogin();
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
		CardLayout cl = (CardLayout) this.frame.getCartas().getLayout();
		cl.show(frame.getCartas(), Aplicacion.LOGIN);
	}
	
	private void verNotificaciones() {
		modelo.guardarDatos();
		this.vista.setVisible(false);
		CardLayout cl = (CardLayout) this.frame.getCartas().getLayout();
		cl.show(frame.getCartas(), Aplicacion.VER_NOTIFICACIONES);
	}
}
