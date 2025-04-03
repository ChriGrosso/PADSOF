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
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
