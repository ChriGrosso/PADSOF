
package interfaz.elementosComunes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FabricaBotones {

    public static JButton crearBotonMenu(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(180, 50));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(66, 66, 66));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        ImageIcon icono = new ImageIcon(rutaIcono);
        Image imagenEscalada = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenEscalada));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(10);

        return boton;
    }

    public static JButton crearBotonPrincipal(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 26));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setPreferredSize(new Dimension(300, 300)); // Botón cuadrado por defecto

        ImageIcon iconoOriginal = new ImageIcon(rutaIcono);

        boton.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int lado = Math.min(boton.getWidth(), boton.getHeight()); // Forza quadrato
                int dimensionIcona = (int) (lado * 0.5); // 50% dello spazio disponibile

                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                        dimensionIcona, dimensionIcona, Image.SCALE_SMOOTH
                );
                boton.setIcon(new ImageIcon(imagenEscalada));
            }
        });

        return boton;
    }
}
