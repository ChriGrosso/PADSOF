package interfaz;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

public class Login extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	private JButton login;
	
	public Login() {
		// Asignar Layout
		this.setLayout(new SpringLayout());
		
		// Crear Componentes
		JLabel etiquetaUsuario = new JLabel("NIF");
		JLabel etiquetaPassword = new JLabel("Password");
		campoUsuario = new JTextField(10);
		campoPassword = new JPasswordField(10);
		login = new JButton("Login");
		
		// añadir componentes al panel
		this.add(etiquetaUsuario);
		this.add(etiquetaPassword);
		this.add(campoUsuario);
		this.add(campoPassword);
		this.add(login);
	}
	
	// método para asignar un controlador al botón
	public void setControlador(ActionListener c) {  
		login.addActionListener(c);
	}
	
	// método que devuelve el nif del usuario (contenido del campo JTextField)
	public String getNifUsuario () {
		return campoUsuario.getText();
	}
	
	// método que devuelve el nif del usuario (contenido del campo JTextField)
	public char[] getPswUsuario () {
		return campoPassword.getPassword();
	}
	
	// método que actualiza el valor de los campos
	public void update () {
		campoUsuario.setText("");
		campoUsuario.grabFocus();
		campoPassword.setText("");
		campoPassword.grabFocus();
	}
}
