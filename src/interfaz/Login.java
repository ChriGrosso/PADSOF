package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Clase que representa la pantalla de inicio de sesión de la aplicación.
 * Permite a los usuarios ingresar sus credenciales para autenticarse en el sistema.
 * 
 * @author Sofia Castro - sofiai.castro@estudiante.uam.es
 */
public class Login extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	private JButton login;
	
	/**
     * Constructor de la clase Login.
     * Configura la interfaz gráfica con los campos de usuario, contraseña y el botón de login.
     */
	public Login() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new GridBagLayout());
        setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();

        // Crear Componentes
        JLabel etiquetaUsuario = new JLabel("NIF:");
        etiquetaUsuario.setForeground(new Color(112, 128, 144));
        JLabel etiquetaPassword = new JLabel("Password:");
        etiquetaPassword.setForeground(new Color(112, 128, 144));
        campoUsuario = new JTextField(15);
        campoPassword = new JPasswordField(15);
        login = new JButton("Login");
        formatoBotones(login, 100, 25);

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
	
	/**
     * Aplica formato visual a los botones de la interfaz.
     * 
     * @param boton Botón a formatear.
     * @param ancho Ancho deseado.
     * @param alto Alto deseado.
     */
	private void formatoBotones(JButton boton,  int ancho, int alto) {
		boton.setPreferredSize(new Dimension(ancho, alto));
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(5, 10, 20)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 16));
	    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}
	
	/**
     * Asigna un controlador de eventos al botón de login.
     * 
     * @param c Controlador de eventos a asignar.
     */
	public void setControlador(ActionListener c) {  
		login.addActionListener(c);
	}
	
	/**
     * Obtiene el NIF ingresado por el usuario.
     * 
     * @return El NIF como una cadena de texto.
     */
	public String getNifUsuario () {
		return campoUsuario.getText();
	}
	
	 /**
     * Obtiene la contraseña ingresada por el usuario.
     * 
     * @return Arreglo de caracteres con la contraseña ingresada.
     */
	public char[] getPswUsuario () {
		return campoPassword.getPassword();
	}
	
	 /**
     * Restablece los campos de usuario y contraseña, dejándolos vacíos.
     * Además, coloca el foco en el campo de usuario.
     */
	public void update () {
		campoUsuario.setText("");
		campoUsuario.grabFocus();
		campoPassword.setText("");
		campoPassword.grabFocus();
	}
}
