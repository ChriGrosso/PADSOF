package interfaz.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Clase BotonVolver - Representa un botón personalizado con un icono que se coloca en la parte superior izquierda
 * del panel para permitir volver a un panel anterior.
 * 
 * El botón incluye un icono escalado y permite asignar una acción mediante un ActionListener.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class BotonVolver extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton volver;

    /**
     * Constructor de la clase BotonVolver.
     * Crea un botón con un icono especificado por la ruta proporcionada y lo añade al panel.
     *
     * @param logoPath Ruta del archivo de imagen que se utilizará como icono del botón.
     */
    public BotonVolver(String logoPath) {
        setLayout(new FlowLayout(FlowLayout.LEFT)); // Posiciona el botón a la izquierda
        setOpaque(false); // Fondo transparente

        volver = new JButton();
        volver.setPreferredSize(new Dimension(60, 40)); // Tamaño del botón
        volver.setFocusPainted(false);
        volver.setBackground(new Color(70, 130, 180)); // Color azul de fondo

        ImageIcon icon = new ImageIcon(logoPath);
        volver.setIcon(escalarImagen(icon, 32, 32)); // Establece el icono escalado
        volver.setHorizontalAlignment(SwingConstants.RIGHT); // Alineación del icono

        add(volver); // Añade el botón al panel
    }

    /**
     * Escala una imagen a un tamaño específico.
     *
     * @param originalIcon Icono original que se desea escalar.
     * @param width Anchura deseada.
     * @param height Altura deseada.
     * @return Una nueva instancia de ImageIcon escalada al tamaño especificado.
     */
    private ImageIcon escalarImagen(ImageIcon originalIcon, int width, int height) {
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Devuelve el botón interno para volver.
     *
     * @return El componente JButton asociado al botón de volver.
     */
    public JButton getVolver() {
        return this.volver;
    }

    /**
     * Asigna un ActionListener al botón de volver.
     *
     * @param actionListener El ActionListener que se ejecutará al hacer clic en el botón.
     */
    public void setControladorVolver(ActionListener actionListener) {
        volver.addActionListener(actionListener);
    }
}
