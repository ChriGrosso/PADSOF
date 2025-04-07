package interfaz;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sistema.SkyManager;
import usuarios.Usuario;

public class ControlLogin implements ActionListener{
	private Login vista;
	private Aplicacion frame;
	private SkyManager modelo;
	
	public ControlLogin(Aplicacion frame) {
		this.frame = frame;
		this.vista = frame.getLogin();
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
		String nifUser = vista.getNifUsuario();
		char[] pswUser = vista.getPswUsuario();
		String psw = new String(pswUser);
		if (nifUser.equals("")) {
			JOptionPane.showMessageDialog(vista, "Debe introducir un nif.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(psw.equals("")) {
			JOptionPane.showMessageDialog(vista, "Debe introducir una password.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// modificar modelo
		try {
			modelo.logIn(nifUser, psw);
		}
		catch(IllegalArgumentException excep) {
			JOptionPane.showMessageDialog(vista, excep.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			this.vista.update();
			return;
		}

		// mostrar nueva vista (se ha iniciado sesión correctamente)
		this.vista.setVisible(false);		
		// obtener el usuario por el nif
		Usuario user = this.modelo.getUsuarioActual();
		CardLayout cl = (CardLayout) this.frame.getCartas().getLayout();
		if(user.esOperador()) { cl.show(frame.getCartas(), Aplicacion.OP_INICIO); }
		else if(user.esControlador()) { cl.show(frame.getCartas(), Aplicacion.CONT_INICIO); }
		else if(user.esGestor()) { cl.show(frame.getCartas(), Aplicacion.GESTOR_INICIO); }
		this.vista.update();
		return;
	}
}
