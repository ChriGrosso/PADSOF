package interfaz;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class Login extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	private JButton login;
	
	public Login() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Crear Componentes
        JLabel etiquetaUsuario = new JLabel("NIF:");
        JLabel etiquetaPassword = new JLabel("Password:");
        campoUsuario = new JTextField(15);
        campoPassword = new JPasswordField(15);
        login = new JButton("Login");

        // Configurar constraints para centrar los elementos
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado
        this.add(etiquetaUsuario, gbc);

        gbc.gridx = 1;
        this.add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(etiquetaPassword, gbc);

        gbc.gridx = 1;
        this.add(campoPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Para que el botón esté centrado
        this.add(login, gbc);
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JRootPane rootPane = javax.swing.SwingUtilities.getRootPane(this);
            if (rootPane != null) {
                rootPane.setDefaultButton(login);
            }
        });
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
