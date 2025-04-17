package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sistema.SkyManager;

public class ControlNuevoTipoAvion implements ActionListener{
	private SkyManager modelo;
	
	public ControlNuevoTipoAvion() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
