package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import aerolineas.Aerolinea;
import aeropuertos.Aeropuerto;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;
import vuelos.Periodicidad;
import vuelos.VueloMercancias;
import vuelos.VueloPasajeros;

/**
 * Clase que controla el registro de nuevos vuelos en la aerolínea.
 * Implementa ActionListener para gestionar eventos de la interfaz.
 * 
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlNuevoVuelo implements ActionListener{
	private SkyManager modelo;
	
	/**
     * Constructor de la clase ControlNuevoVuelo.
     * Inicializa el modelo del sistema para gestionar vuelos.
     */
	public ControlNuevoVuelo() {
		this.modelo = SkyManager.getInstance();
	}
	
	/**
     * Maneja los eventos de acción generados en la interfaz.
     * 
     * @param e Evento de acción recibido.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object fuente = e.getSource(); // quién disparó el evento
		var v = Aplicacion.getInstance().getNuevoVuelo();
		
		if (e.getActionCommand().equals("Registrar Vuelo")) {     // si se ha pulsado "Registrar Avión"
			this.registrarVuelo();
		}
		else if (e.getActionCommand().equals("Mercancías")) {   
			v.getNumPLabel().setVisible(false);
			v.getnumPTextField().setVisible(false);
			v.getMercPeligrosas().setVisible(true);
			v.getMercNoPeligrosas().setVisible(true);
			v.getCargaLabel().setVisible(true);
			v.getCargaTextField().setVisible(true);
		}
		else if (e.getActionCommand().equals("Pasajeros")) {   
			v.getMercPeligrosas().setVisible(false);
			v.getMercNoPeligrosas().setVisible(false);
			v.getCargaLabel().setVisible(false);
			v.getCargaTextField().setVisible(false);
			v.getNumPLabel().setVisible(true);
			v.getnumPTextField().setVisible(true);
		}
		else if (e.getActionCommand().equals("Vuelo Compartido")) {   
			v.getAerolineaSecundaria().setVisible(true);
			v.getEtiqAeS().setVisible(true);
		}
		else if (e.getActionCommand().equals("Vuelo No Compartido")) {   
			v.getAerolineaSecundaria().setVisible(false);
			v.getEtiqAeS().setVisible(false);
		}
		else if (e.getActionCommand().equals("Vuelo de Salida")) {   
			v.getOrigenTextField().setText(SkyManager.getInstance().getInformacionPropia().getCodigo());
			v.getDestinoTextField().setText("");
		}
		else if (e.getActionCommand().equals("Vuelo de Llegada")) {   
			v.getDestinoTextField().setText(SkyManager.getInstance().getInformacionPropia().getCodigo());
			v.getOrigenTextField().setText("");
		}
		else if (fuente instanceof JComboBox) {
			JComboBox<?> combo = (JComboBox<?>) fuente;
			String seleccion = (String) combo.getSelectedItem();

			var panel = Aplicacion.getInstance().getNuevoVuelo().getPanelCheckbox();

			if ("Días Alternos".equals(seleccion)) {
				panel.setVisible(true);
			} else {
				panel.setVisible(false); // Para "No periódico" o "Diario"
			}
		}
	}

	/**
     * Registra un nuevo vuelo en el sistema validando los datos ingresados.
     */
	private void registrarVuelo() {
		// obtener valores de la vista
		String mat = Aplicacion.getInstance().getNuevoVuelo().getMat();
		if(mat.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe introducir la matrícula del avión del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String origen = Aplicacion.getInstance().getNuevoVuelo().getOrigen();
		if(origen.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir un aeropuerto de salida.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String destino = Aplicacion.getInstance().getNuevoVuelo().getDestino();
		if(destino.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe introducir un aeropuerto de llegada.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		LocalDateTime salida = Aplicacion.getInstance().getNuevoVuelo().getFechaHoraSalida();
		LocalDateTime llegada = Aplicacion.getInstance().getNuevoVuelo().getFechaHoraLlegada();
		// Mercancías / Pasajeros
		boolean merc;
		boolean peligrosas = false;
		if(Aplicacion.getInstance().getNuevoVuelo().esMercancias()) {
			merc = true;
			if(Aplicacion.getInstance().getNuevoVuelo().esMercPeligrosas()) {
				peligrosas = true;
			}
			else if(Aplicacion.getInstance().getNuevoVuelo().esMercNoPeligrosas()) {
				peligrosas = false;
			} else {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe introducir si el vuelo de mercancías transporta mercancías peligrosas o no.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(Aplicacion.getInstance().getNuevoVuelo().esPasajeros()) {
			merc = false;
		} else {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe especificar si el vuelo es de mercancías o de pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Compartido / No ''
		boolean comp;
		String as = null;
		if(Aplicacion.getInstance().getNuevoVuelo().esCompartido()) {
			comp = true;
			as = Aplicacion.getInstance().getNuevoVuelo().txtAerolineaSecundaria();
			if(as.equals("")) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir la aerolínea secundaria del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(Aplicacion.getInstance().getNuevoVuelo().esNoCompartido()) {
			comp = false;
		} else {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe especificar si el vuelo es compartido o no.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Periodicidad
		Periodicidad periodicidad = null;
		String diasAlt = "";
		var v = Aplicacion.getInstance().getNuevoVuelo();
		if(v.getPeriodicidad().equals("No Periódico")) {
			periodicidad = Periodicidad.NO_PERIODICO;
		} else if(v.getPeriodicidad().equals("Diario")) {
			periodicidad = Periodicidad.DIARIO;
		} else if(v.getPeriodicidad().equals("Días Alternos")){
			periodicidad = Periodicidad.DIAS_ALTERNOS;
			// Obtener los días que opera
			ArrayList<DayOfWeek> dias = v.getDiasSeleccionados();
			if(dias.isEmpty()) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe seleccionar los días que operará el vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(dias.contains(DayOfWeek.MONDAY)) { diasAlt.concat("L "); }
			if(dias.contains(DayOfWeek.TUESDAY)) { diasAlt.concat("M "); }
			if(dias.contains(DayOfWeek.WEDNESDAY)) { diasAlt.concat("X "); }
			if(dias.contains(DayOfWeek.THURSDAY)) { diasAlt.concat("J "); }
			if(dias.contains(DayOfWeek.FRIDAY)) { diasAlt.concat("V "); }
			if(dias.contains(DayOfWeek.SATURDAY)) { diasAlt.concat("S "); }
			if(dias.contains(DayOfWeek.SUNDAY)) { diasAlt.concat("D "); }
		} else {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "Debe especificar la periodicidad del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Registrar vuelo
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		// Avion
		Avion av = a.getAviones().get(mat);
		if(av == null) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "La aerolíneas no tiene un avión con matrícula: " + mat + ".", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Aeropuertos
		Aeropuerto aeO = SkyManager.getInstance().getAeropuertosExternos().get(origen);
		if(aeO == null) {
			if(origen.equals(SkyManager.getInstance().getInformacionPropia().getCodigo())) {
				aeO = SkyManager.getInstance().getInformacionPropia();
			} else {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "No existe el aeropuerto con código: " + origen + ".", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		Aeropuerto aeD = SkyManager.getInstance().getAeropuertosExternos().get(destino);
		if(aeD == null) {
			if(destino.equals(SkyManager.getInstance().getInformacionPropia().getCodigo())) {
				aeD = SkyManager.getInstance().getInformacionPropia();
			} else {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "No existe el aeropuerto con código: " + destino + ".", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		// Definir si el vuelo es de salida o llegada
		boolean esLlegada = false;
		if(Aplicacion.getInstance().getNuevoVuelo().esSalida()) { esLlegada = false; }
		if(Aplicacion.getInstance().getNuevoVuelo().esLlegada()) { esLlegada = true; }
		// Aerolineas implicadas
		Aerolinea aSec = null;
		if(as != null) { aSec = SkyManager.getInstance().getAerolineas().get(as); }
		ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>();
		aerolineas.add(a);
		if(aSec != null) { aerolineas.add(aSec); }
		// Vuelo de mercancías
		if(merc) {
			if(!(av.getTipoAvion() instanceof AvionMercancias)) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El avión no está equipado para transportar mercancías.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			double carga = Aplicacion.getInstance().getNuevoVuelo().getCarga();
			if(carga < 0) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El valor de la carga no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			VueloMercancias vm = new VueloMercancias(aeO, aeD, salida, llegada, aerolineas, esLlegada, carga, peligrosas, periodicidad, av);
			boolean res = SkyManager.getInstance().registrarVuelo(vm);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en el sistema o no se ha registrado con el mínimo de " + SkyManager.getInstance().getDiasAntelacionProgVuelo() + " dias de antelación.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			res = a.addVuelo(vm);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en " + a.getNombre() + ".", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(comp) {
				res = aSec.addVuelo(vm);
				if(!res) {
					JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en " + aSec.getNombre() + ".", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} 
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Se ha realizado correctamente el registro del vuelo " + vm.getId() + ".", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
		}
		// Vuelo de pasajeros
		else {
			if(!(av.getTipoAvion() instanceof AvionPasajeros)) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El avión no está equipado para transportar pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int numP = Aplicacion.getInstance().getNuevoVuelo().getNumP();
			if(numP < 0) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El valor del número de pasajeros no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			VueloPasajeros vp = new VueloPasajeros(aeO, aeD, salida, llegada, aerolineas, esLlegada, numP, periodicidad, av);
			boolean res = SkyManager.getInstance().registrarVuelo(vp);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en el sistema o no se ha registrado con el mínimo de " + SkyManager.getInstance().getDiasAntelacionProgVuelo() + " dias de antelación.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			res = a.addVuelo(vp);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en " + a.getNombre() + ".", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(comp) {
				res = aSec.addVuelo(vp);
				if(!res) {
					JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoVuelo(), "El vuelo ya está registrado en " + aSec.getNombre() + ".", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} 
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Se ha realizado correctamente el registro del vuelo " + vp.getId() + ".", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
		}
		
		// Actualizar registros de la aerolínea y el sistema
		Aplicacion.getInstance().getOpVuelos().actualizarPantalla();
		Aplicacion.getInstance().getNuevoVuelo().update();
		this.modelo.guardarDatos();
	}

}
