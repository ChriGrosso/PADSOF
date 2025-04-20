package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;
import sistema.SkyManager;

public class ControlOperadorGestionVuelos implements ActionListener{
	private SkyManager modelo;
	
	public ControlOperadorGestionVuelos() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Nuevo Vuelo")) {     
			this.nuevoVuelo();
		}
	}

	
	private void nuevoVuelo() {
		Aplicacion.getInstance().getOpVuelos().setVisible(false);
		Aplicacion.getInstance().getNuevoVuelo().actualizarPantalla();
		Aplicacion.getInstance().showNuevoVuelo();
	}

}
