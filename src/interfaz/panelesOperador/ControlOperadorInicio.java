package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interfaz.Aplicacion;


public class ControlOperadorInicio implements ActionListener{
	
	public ControlOperadorInicio() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Gestionar Aviones")) {     
			this.gestionarAviones();
		}
		else if (e.getActionCommand().equals("Gestionar Vuelos")) {     
			this.gestionarVuelos();
		}
		else if (e.getActionCommand().equals("Gestionar Facturas")) {     
			this.gestionarFacturas();
		}
		else if (e.getActionCommand().equals("Buscar Vuelos")) {     
			this.buscarVuelos();
		}
		else if (e.getActionCommand().equals("Estadísticas")) {     
			this.mostrarEstadisticas();
		}
	}

	
	private void mostrarEstadisticas() {
		Aplicacion.getInstance().getOpEstadisticas().actualizarPantalla();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showOpEstadisticas();
	}

	private void buscarVuelos() {
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showBusquedaVuelos();
	}

	private void gestionarAviones() {
		Aplicacion.getInstance().getOpAviones().actualizarPantalla();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showOpAviones();
	}
	
	private void gestionarVuelos() {
		Aplicacion.getInstance().getOpVuelos().actualizarPantalla();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showOpVuelos();
	}
	
	private void gestionarFacturas() {
		Aplicacion.getInstance().getOpFacturas().actualizarPantalla();
		Aplicacion.getInstance().getOpInicio().setVisible(false);
		Aplicacion.getInstance().showOpFacturas();
	}
}
