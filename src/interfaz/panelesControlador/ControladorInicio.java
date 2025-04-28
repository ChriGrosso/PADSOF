package interfaz.panelesControlador;

import interfaz.elementosComunes.FabricaBotones;
import interfaz.elementosComunes.MenuLateral;
import sistema.SkyManager;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;
    private JLabel bienvenida;

    public ControladorInicio() {
    	setLayout(new BorderLayout());

        // === MENU LATERALE ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        // === CONTENUTO CENTRALE ===
        JPanel panelGeneral = new JPanel(new GridBagLayout());
        panelGeneral.setBackground(new Color(173, 216, 230));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // === MESSAGGIO BENVENUTO ===
        bienvenida = new JLabel();
        bienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        bienvenida.setForeground(new Color(112, 128, 144));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelGeneral.add(bienvenida, gbc);

        // === BOTTONI CENTRALI ===
        gbc.gridwidth = 1;
        gbc.gridy++;

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 30, 30));
        panelBotones.setBackground(new Color(173, 216, 230));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        busquedaVuelos = new JButton("Buscar Vuelos");
        formatoBotones(busquedaVuelos);
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");
        panelBotones.add(busquedaVuelos);

        gestionVuelos = new JButton("Gestión Vuelos");
        formatoBotones(gestionVuelos);
        setScaledIcon(gestionVuelos, "resources/plane_icon.png");
        panelBotones.add(gestionVuelos);

        gbc.gridwidth = 2;
        panelGeneral.add(panelBotones, gbc);

        add(panelGeneral, BorderLayout.CENTER);
    }
    
    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                // Limitar el tamaño del icono (por ejemplo, máximo 64x64)
                int iconWidth = Math.min(192, btnWidth / 2);
                int iconHeight = Math.min(192, btnHeight / 2);
                if ((iconWidth != iconHeight)) {
                	iconHeight = Math.min(iconHeight, btnWidth);
                	iconWidth = Math.min(iconHeight, btnWidth);
                }

                // Escalar manteniendo proporciones
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        iconWidth, iconHeight, Image.SCALE_SMOOTH
                );

                button.setIcon(new ImageIcon(scaledImage));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        });
    }
    
    private void formatoBotones(JButton boton) {
		boton.setFont(new Font("Arial", Font.BOLD, 18));
		boton.setBackground(new Color(112, 128, 144));
		boton.setForeground(Color.WHITE);
	}

	// método para asignar un controlador a los botones
 	public void setControlador(ActionListener c) {  
 		busquedaVuelos.addActionListener(c);
 		gestionVuelos.addActionListener(c);
 	}

 	public void actualizarPantalla() {
    	Usuario usuarioActual = SkyManager.getInstance().getUsuarioActual();
    	if(usuarioActual != null) {
    		bienvenida.setText("Bienvenid@ " + usuarioActual.getNombre());
    	}
    }
}



