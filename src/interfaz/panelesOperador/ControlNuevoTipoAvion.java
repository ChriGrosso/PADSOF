package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import aerolineas.Aerolinea;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;

/**
 * Clase que controla el registro de nuevos tipos de avión en la aerolínea.
 * Implementa ActionListener para manejar eventos relacionados con el registro de aviones en la interfaz de usuario.
 *
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlNuevoTipoAvion implements ActionListener{
	private SkyManager modelo;
	
	/**
     * Constructor de la clase ControlNuevoTipoAvion.
     * Inicializa el modelo del sistema (SkyManager) para gestionar el registro de aviones.
     */
	public ControlNuevoTipoAvion() {
		this.modelo = SkyManager.getInstance();
	}
	
	/**
     * Maneja los eventos de acción generados en la interfaz.
     * 
     * @param e Evento de acción recibido.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Registrar Tipo de Avión")) {     // si se ha pulsado "Registrar Tipo de Avión"
			this.registrarTipoAvion();
		}
		else if (e.getActionCommand().equals("Mercancías")) {   
			Aplicacion.getInstance().getNuevoTipoAvion().getMercPeligrosas().setVisible(true);
			Aplicacion.getInstance().getNuevoTipoAvion().getMercNoPeligrosas().setVisible(true);
			Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadPTextField().setVisible(false);
			Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadMTextField().setVisible(true);
		}
		else if (e.getActionCommand().equals("Pasajeros")) {   
			Aplicacion.getInstance().getNuevoTipoAvion().getMercPeligrosas().setVisible(false);
			Aplicacion.getInstance().getNuevoTipoAvion().getMercNoPeligrosas().setVisible(false);
			Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadMTextField().setVisible(false);
			Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadPTextField().setVisible(true);
		}
	}
	
	/**
     * Registra un nuevo tipo de avión en la aerolínea del operador actual.
     * Valida los datos ingresados por el usuario y verifica que el modelo de avión sea compatible con la aerolínea.
     */
	private void registrarTipoAvion() {
		// obtener datos de la vista
		String marca = Aplicacion.getInstance().getNuevoTipoAvion().getMarca();
		if(marca.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Debe introducir una marca de avión.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String modelo = Aplicacion.getInstance().getNuevoTipoAvion().getModelo();
		if(modelo.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Debe introducir un modelo de avión.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		double autonomia = Aplicacion.getInstance().getNuevoTipoAvion().getAutonomia();
		if(autonomia < 0) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor de la autonomía es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		double anchura = Aplicacion.getInstance().getNuevoTipoAvion().getAnchura();
		if(anchura < 0) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor de la anchura es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		double altura = Aplicacion.getInstance().getNuevoTipoAvion().getAltura();
		if(altura < 0) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor de la altura es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		double largo = Aplicacion.getInstance().getNuevoTipoAvion().getLargo();
		if(largo < 0) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor del largo es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		boolean merc;
		boolean peligrosas = false;
		if(Aplicacion.getInstance().getNuevoTipoAvion().esMercancias()) {
			merc = true;
			if(Aplicacion.getInstance().getNuevoTipoAvion().esMercPeligrosas()) {
				peligrosas = true;
			}
			else if(Aplicacion.getInstance().getNuevoTipoAvion().esMercNoPeligrosas()) {
				peligrosas = false;
			} else {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Debe introducir si el modelo de avión de mercancías puede transportar mercancías peligrosas o no.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(Aplicacion.getInstance().getNuevoTipoAvion().esPasajeros()) {
			merc = false;
		} else {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Debe especificar si el modelo de avión es de mercancías o de pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Registrar tipo de avión
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		// TipoAvion mercancias (precaución con las mercancías)
		if(merc) {
			double capacidadM = Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadM();
			if(capacidadM < 0) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor de la capcaidad es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			AvionMercancias avm = new AvionMercancias(marca, modelo, autonomia, altura, anchura, largo, capacidadM, peligrosas);
			boolean res = a.addTipoAvion(avm);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Fallo al registrar el modelo de avión en la aerolínea.\n Dicho modelo puede ya estar registrado.", "Error", JOptionPane.ERROR_MESSAGE);
				Aplicacion.getInstance().getNuevoTipoAvion().update(); 
				return;
			}
		}
		else {
			int capacidadP = Aplicacion.getInstance().getNuevoTipoAvion().getCapacidadP();
			if(capacidadP < 0) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "El valor de la capcaidad es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			AvionPasajeros avp = new AvionPasajeros(marca, modelo, autonomia, altura, anchura, largo, capacidadP);
			boolean res = a.addTipoAvion(avp);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Fallo al registrar el modelo de avión en la aerolínea.\n Dicho modelo puede ya estar registrado.", "Error", JOptionPane.ERROR_MESSAGE);
				Aplicacion.getInstance().getNuevoTipoAvion().update(); 
				return;
			}
		}
		
		// Actualizar el sistema
		JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoTipoAvion(), "Se ha realizado correctamente el registro del modelo del avión avión " + marca + "_" + modelo + ".", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
		Aplicacion.getInstance().getNuevoTipoAvion().update();
		this.modelo.guardarDatos();
	}

}
