package interfaz.elementosComunes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BotonVolver extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton volver;

	public BotonVolver(String logoPath) {
		// Contenedor en la esquina superior derecha
		setLayout(new FlowLayout(FlowLayout.LEFT));
        setOpaque(false); // Fondo transparente
        
        // Configurar el botón
        // Crear el botón para ir al panel anterior
        volver = new JButton();
        volver.setPreferredSize(new Dimension(60, 40)); // Tamaño inicial del botón
        volver.setFocusPainted(false);
        volver.setBackground(new Color(70, 130, 180)); // Color de fondo azul

        // Añadir el logo al botón
        ImageIcon icon = new ImageIcon(logoPath);
        volver.setIcon(escalarImagen(icon, 32, 32)); // Escalar el logo al tamaño adecuado
        volver.setHorizontalAlignment(SwingConstants.RIGHT); // Alineación del icono
        
        add(volver);
    }

    // Método para escalar la imagen
    private ImageIcon escalarImagen(ImageIcon originalIcon, int width, int height) {
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    public JButton getVolver() {
    	return this.volver;
    }
    
    // Método para asignar acción al botón
    public void setControladorVolver(ActionListener actionListener) {
        volver.addActionListener(actionListener);
    }
}
