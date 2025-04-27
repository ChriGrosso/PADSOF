package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;

public class ControlRetrasoPorVuelo implements ActionListener{
	private SkyManager modelo;
	
	public ControlRetrasoPorVuelo() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var v = Aplicacion.getInstance().getOpEstadisticas().getRetPorVuelo();
		if (e.getActionCommand().equals("Vuelos de Salida")) {   
			v.getOrigenJCombo().setVisible(false);
			v.getAeO().setVisible(true);
			v.getDestinoJCombo().setVisible(true);
			v.getAeD().setVisible(false);
		}
		else if (e.getActionCommand().equals("Vuelos de Llegada")) {   
			v.getOrigenJCombo().setVisible(true);
			v.getAeO().setVisible(false);
			v.getDestinoJCombo().setVisible(false);
			v.getAeD().setVisible(true);
		}
		else if (e.getActionCommand().equals("Calcular retraso medio")) {   
			mostrarRetraso();
		}
	}

	private void mostrarRetraso() {
		var v = Aplicacion.getInstance().getOpEstadisticas().getRetPorVuelo();
		// Definir si el vuelo es de salida o llegada
		boolean esLlegada = false;
		String seleccion = null;
		if(v.esSalida()) { 
			esLlegada = false; 
			seleccion = (String) v.getDestinoJCombo().getSelectedItem();
		}
		if(v.esLlegada()) { 
			esLlegada = true; 
			seleccion = (String) v.getOrigenJCombo().getSelectedItem();
		}
		
		Aeropuerto or = null, des = null;
		int inicioCodigo = seleccion.indexOf("(") + 1; // Posición después del paréntesis de apertura
		int finCodigo = seleccion.indexOf(")"); // Posición del paréntesis de cierre
		String codigo = seleccion.substring(inicioCodigo, finCodigo); // Extraer el código
		if(esLlegada) {
			des = modelo.getInformacionPropia();
			or = modelo.getAeropuertosExternos().get(codigo);
		} 
		else {
			or = modelo.getInformacionPropia();
			des = modelo.getAeropuertosExternos().get(codigo);
		}
		
		// Mostramos el retraso medio
		Operador op = (Operador) modelo.getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		v.getResultado().setText("Retraso promedio de vuelos para viajes desde " + or.getNombre() + "(" + or.getCiudadMasCercana() + ") a (" +
								des.getNombre() + des.getCiudadMasCercana() + "): " + a.getEstadisticas().retrasoMedioPorVueloMinutos(or, des) + " minutos");
	}

}
