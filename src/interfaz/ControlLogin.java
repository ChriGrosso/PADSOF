package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sistema.SkyManager;
import usuarios.Usuario;

/**
 * Clase que gestiona el proceso de inicio de sesión en la aplicación.
 * Implementa ActionListener para responder a eventos de login y validar credenciales de usuario.
 * 
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class ControlLogin implements ActionListener{
	private SkyManager modelo;
	
	/**
     * Constructor de la clase ControlLogin.
     * Obtiene la instancia única del modelo SkyManager para manejar la autenticación de usuarios.
     */
	public ControlLogin() {
		this.modelo = SkyManager.getInstance();
	}
	
	/**
     * Maneja eventos de acción generados en la interfaz.
     * Responde al evento de "Login" ejecutando el proceso de inicio de sesión.
     * 
     * @param e Evento de acción recibido.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {     // si se ha pulsado "Login"
			this.iniciarSesion();
		}			 
	}

	/**
     * Realiza el proceso de autenticación del usuario.
     * Valida los datos de entrada, verifica las credenciales y muestra la vista correspondiente según el tipo de usuario.
     */
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
			Aplicacion.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
			Aplicacion.getInstance().getOpInicio().actualizarPantalla();
			Aplicacion.getInstance().showOpInicio(); 
		}
		else if(user.esControlador()) { 
			Aplicacion.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
			Aplicacion.getInstance().getContInicio().actualizarPantalla();
			Aplicacion.getInstance().showContInicio();
			}
		else if(user.esGestor()) { 
			Aplicacion.getInstance().setExtendedState(JFrame.MAXIMIZED_BOTH);
			Aplicacion.getInstance().getGestorInicio().actualizarPantalla();
			Aplicacion.getInstance().showGestorInicio(); 
		}
		Aplicacion.getInstance().getLogin().update();
		return;
	}
}
