package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sistema.SkyManager;
import usuarios.Usuario;

public class ControlLogin implements ActionListener{
	private SkyManager modelo;
	
	public ControlLogin() {
		this.modelo = SkyManager.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {     // si se ha pulsado "Login"
			this.iniciarSesion();
		}			 
	}

	
	private void iniciarSesion() {
		// validar valores en la vista
		String nifUser = Aplicacion.getInstance().getLogin().getNifUsuario();
		char[] pswUser = Aplicacion.getInstance().getLogin().getPswUsuario();
		String psw = new String(pswUser);
		if (nifUser.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), "Debe introducir un nif.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(psw.equals("")) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), "Debe introducir una password.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// modificar modelo
		try {
			modelo.logIn(nifUser, psw);
		}
		catch(IllegalArgumentException excep) {
			JOptionPane.showMessageDialog(Aplicacion.getInstance().getLogin(), excep.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			Aplicacion.getInstance().getLogin().update();
			return;
		}

		// mostrar nueva vista (se ha iniciado sesión correctamente)
		Aplicacion.getInstance().getLogin().setVisible(false);		
		// obtener el usuario por el nif
		Usuario user = this.modelo.getUsuarioActual();
		if(user.esOperador()) { 
			Aplicacion.getInstance().getOpInicio().actualizarPantalla();
			Aplicacion.getInstance().showOpInicio(); 
		}
		else if(user.esControlador()) { 
			Aplicacion.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
			Aplicacion.getInstance().showContInicio();
			 // schermo intero
			}
		else if(user.esGestor()) { 
			Aplicacion.getInstance().getGestorInicio().actualizarPantalla();
			Aplicacion.getInstance().showGestorInicio(); 
		}
		Aplicacion.getInstance().getLogin().update();
		return;
	}
}
