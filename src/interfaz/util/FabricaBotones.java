package interfaz.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Clase FabricaBotones - Proporciona métodos estáticos para crear botones personalizados
 * utilizados en la interfaz gráfica, tanto para menús como para acciones principales.
 * 
 * Esta clase actúa como una fábrica para centralizar el estilo y el comportamiento de los botones.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class FabricaBotones {

    /**
     * Crea un botón de menú con estilo predefinido y un icono.
     *
     * @param texto Texto que se mostrará en el botón.
     * @param rutaIcono Ruta al archivo de imagen que se utilizará como icono.
     * @return Un objeto JButton con formato para usarse en menús laterales u opciones de navegación.
     */
    public static JButton crearBotonMenu(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(180, 50));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(66, 66, 66)); // Gris oscuro
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        ImageIcon icono = new ImageIcon(rutaIcono);
        Image imagenEscalada = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenEscalada));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(10); // Separación entre el icono y el texto

        return boton;
    }

    /**
     * Crea un botón principal de gran tamaño con texto e icono centrados,
     * cuyo icono se ajusta dinámicamente al tamaño del botón.
     *
     * @param texto Texto que se mostrará en el botón.
     * @param rutaIcono Ruta al archivo de imagen que se utilizará como icono.
     * @return Un objeto JButton de gran tamaño con estilo centrado, ideal para acciones principales.
     */
    public static JButton crearBotonPrincipal(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 26));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setPreferredSize(new Dimension(300, 300)); // Botón cuadrado por defecto

        ImageIcon iconoOriginal = new ImageIcon(rutaIcono);

        // Escala el icono dinámicamente cuando el tamaño del botón cambia
        boton.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int lado = Math.min(boton.getWidth(), boton.getHeight());
                int dimensionIcona = (int) (lado * 0.5); // El icono ocupa el 50% del botón

                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                        dimensionIcona, dimensionIcona, Image.SCALE_SMOOTH
                );
                boton.setIcon(new ImageIcon(imagenEscalada));
            }
        });

        return boton;
    }
}
