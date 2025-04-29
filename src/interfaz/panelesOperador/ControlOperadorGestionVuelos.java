package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;

public class ControlOperadorGestionVuelos implements ActionListener{
	
	public ControlOperadorGestionVuelos() {
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
