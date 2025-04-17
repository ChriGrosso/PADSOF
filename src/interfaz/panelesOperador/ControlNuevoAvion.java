package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JOptionPane;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.AvionMercancias;
import aviones.AvionPasajeros;
import aviones.TipoAvion;
import sistema.SkyManager;
import usuarios.Operador;
import interfaz.Aplicacion;

public class ControlNuevoAvion implements ActionListener{
	private SkyManager modelo;
	
	public ControlNuevoAvion() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cerrar Sesión")) {     // si se ha pulsado "Cerrar Sesión"
			this.cerrarSesion();
		}
		else if (e.getActionCommand().equals("Notificaciones")) {     // si se ha pulsado "Notificaciones"
			this.verNotificaciones();
		}
		else if (e.getActionCommand().equals("Registrar Avión")) {     // si se ha pulsado "Registrar Avión"
			this.registrarAvion();
		}
		else if (e.getActionCommand().equals("Mercancías")) {   
			Aplicacion.getInstance().getNuevoAvion().getMercPeligrosas().setVisible(true);
			Aplicacion.getInstance().getNuevoAvion().getMercNoPeligrosas().setVisible(true);
		}
		else if (e.getActionCommand().equals("Pasajeros")) {   
			Aplicacion.getInstance().getNuevoAvion().getMercPeligrosas().setVisible(false);
			Aplicacion.getInstance().getNuevoAvion().getMercNoPeligrosas().setVisible(false);
		}
		else if (e.getActionCommand().equals("Volver")) {   
			this.paginaAnterior();
		}
	}

	
	private void registrarAvion() {
		// obtener valores de la vista
		String matricula = Aplicacion.getInstance().getNuevoAvion().getMatricula();
		if(matricula.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir una matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String marca = Aplicacion.getInstance().getNuevoAvion().getMarca();
		if(marca.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir una marca de avión.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String modelo = Aplicacion.getInstance().getNuevoAvion().getModelo();
		if(modelo.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir un modelo de avión.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		LocalDate compra = Aplicacion.getInstance().getNuevoAvion().getFechaCompra();
		LocalDate ultRev = Aplicacion.getInstance().getNuevoAvion().getFechaUltimaRevision();
		boolean merc;
		boolean peligrosas = false;
		if(Aplicacion.getInstance().getNuevoAvion().esMercancias()) {
			merc = true;
			if(Aplicacion.getInstance().getNuevoAvion().esMercPeligrosas()) {
				peligrosas = true;
			}
			else if(Aplicacion.getInstance().getNuevoAvion().esMercNoPeligrosas()) {
				peligrosas = false;
			} else {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe introducir si el avión de mercancías puede transportar mercancías peligrosas o no.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(Aplicacion.getInstance().getNuevoAvion().esPasajeros()) {
			merc = false;
		} else {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "Debe especificar si el avión es de mercancías o de pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Registrar avión
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		// Comprobar si la aerolínea tiene el tipo de avión para el registro
		String clave = marca + "_" + modelo;
		if(!a.getTiposAvion().containsKey(clave)) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "La aerolínea no posee el modelo de avión para el registro que tratas de hacer.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// TipoAvion mercancias (precaución con las mercancías)
		if(merc) {
			if(!a.getTiposAvion().get(clave).isMercancias()) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "No puedes registrar un avión de mercancías si su modelo no es para mercancías.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			AvionMercancias avm = (AvionMercancias) a.getTiposAvion().get(clave);
			if(peligrosas && !avm.getProductosPeligrosos()) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "No puedes registrar un avión capaz de transportar productos peligrosos si su modelo no puede hacerlo.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Avion av = new Avion(matricula, compra, avm, ultRev);
			boolean res = a.addAvion(av);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), "Fallo al registrar el avión en la aerolínea.\n La matrícula puede estar repetida.", "Error", JOptionPane.ERROR_MESSAGE);
				Aplicacion.getInstance().getNuevoAvion().update(); 
				return;
			}
		} 
		// TipoAvion pasajeros
		else {
			if(a.getTiposAvion().get(clave).isMercancias()) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getNuevoAvion(), "No puedes registrar un avión de pasajeros si su modelo no es para pasajeros.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			AvionPasajeros avp = (AvionPasajeros) a.getTiposAvion().get(clave);
			Avion av = new Avion(matricula, compra, avp, ultRev);
			boolean res = a.addAvion(av);
			if(!res) {
				JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), "Fallo al registrar el avión en la aerolínea.\n La matrícula puede estar repetida.", "Error", JOptionPane.ERROR_MESSAGE);
				Aplicacion.getInstance().getNuevoAvion().update(); 
				return;
			}
		}
		
		// Actualizar registro de la aerolínea y el sistema
		Aplicacion.getInstance().getOpAviones().actualizarPantalla();
		JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), "Se ha realizado correctamente el registro del avión " + matricula + ".", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
		Aplicacion.getInstance().getNuevoAvion().update();
		this.modelo.guardarDatos();
	}

	private void cerrarSesion() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getNuevoAvion().setVisible(false);
		Aplicacion.getInstance().showLogin();
	}
	
	private void verNotificaciones() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getNuevoAvion().setVisible(false);
		Aplicacion.getInstance().showNotificaciones();
	}
	
	private void paginaAnterior() {
		modelo.guardarDatos();
		Aplicacion.getInstance().getNuevoAvion().setVisible(false);
		Aplicacion.getInstance().showOpAviones();
	}
}
