package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sistema.SkyManager;

public class ControlLogin implements ActionListener{
	private Login vista;
	private Aplicacion frame;
	private SkyManager modelo;
	
	public ControlLogin(Aplicacion frame) {
		this.frame = frame;
		this.vista = frame.getLogin();
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {     // si se ha pulsado "Login"
			
		}			 
	}

}
