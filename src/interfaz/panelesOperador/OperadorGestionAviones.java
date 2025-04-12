package interfaz.panelesOperador;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OperadorGestionAviones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton nuevoAvion;
	private JButton nuevoTipoAv;
	
	public OperadorGestionAviones() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Crear componentes
        notificaciones = new JButton("Notificaciones");
        cerrarSesion = new JButton("Cerrar Sesión");
        nuevoAvion = new JButton("Nuevo Avión");
        nuevoTipoAv = new JButton("Nuevo Tipo Avión");
        
        // === Columna izquierda: Notificaciones y Cerrar Sesión ===
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(notificaciones, gbc);

        gbc.gridy++;
        this.add(cerrarSesion, gbc);
	}
	
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		notificaciones.addActionListener(c);
		cerrarSesion.addActionListener(c);
		nuevoAvion.addActionListener(c);
		nuevoTipoAv.addActionListener(c);;
	}
}
