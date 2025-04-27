package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import aerolineas.Aerolinea;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;

public class ControlRetrasoPorFranja implements ActionListener{
	private SkyManager modelo;
	
	public ControlRetrasoPorFranja() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Calcular retraso medio")) {   
			mostrarRetraso();
		}
	}

	private void mostrarRetraso() {
		var v = Aplicacion.getInstance().getOpEstadisticas().getRetPorFranja();
		
		LocalTime inicio = null, fin = null;
		inicio = v.getHoraSalida();
		fin = v.getHoraLlegada();
		
		// Mostramos el retraso medio
		Operador op = (Operador) modelo.getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		v.getResultado().setText("Retraso promedio de todos los vuelos desde las " + inicio + " hasta las " + fin + ": " + a.getEstadisticas().retrasoMedioPorFranjaHorariaMinutos(inicio, fin) + " minutos");
	}

}
