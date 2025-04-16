/*package interfaz.panelesControlador;

import javax.swing.*;

import sistema.SkyManager;

import java.awt.*;
import java.awt.event.ActionListener;

public class ControladorInicio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton notificaciones;
	private JButton cerrarSesion;
	private JButton busquedaVuelos;
	private JButton gestionVuelos;
	
	public ControladorInicio() {
		// Usar GridBagLayout para centrar componentes
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel mensajeBienvenida = null;
        // Crear Componentes
        if(this.isShowing()) {
        	mensajeBienvenida = new JLabel("Bienvenid@ " + SkyManager.getInstance().getUsuarioActual().getNombre());
        }
        notificaciones = new JButton("Notificaciones");
        cerrarSesion = new JButton("Cerrar Sesión");
        busquedaVuelos = new JButton("Buscar Vuelos");
        gestionVuelos = new JButton("Gestionar Vuelos");
        
     // === Columna izquierda: Notificaciones y Cerrar Sesión ===
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(notificaciones, gbc);

        gbc.gridy++;
        this.add(cerrarSesion, gbc);

        // === Centro: Mensaje de bienvenida ===
        if(this.isShowing()) {
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.gridwidth = 2;
	        gbc.anchor = GridBagConstraints.CENTER;
			this.add(mensajeBienvenida, gbc);
        }

        // === Botones distribuidos centralmente en el espacio derecho ===
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(busquedaVuelos, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(gestionVuelos, gbc);
	}

	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		notificaciones.addActionListener(c);
		cerrarSesion.addActionListener(c);
		busquedaVuelos.addActionListener(c);
		gestionVuelos.addActionListener(c);
	}
}

package interfaz.panelesControlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton notificaciones;
    private JButton cerrarSesion;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;

    public ControladorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERALE ===
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(45, 45, 45));

        notificaciones = createMenuButton("🔔 Notificaciones");
        cerrarSesion = createMenuButton("🔓 Cerrar Sesión");
        JButton iconoAvion = new JButton(new ImageIcon("resources/plane_icon.png"));
        iconoAvion.setEnabled(false);
        iconoAvion.setBorderPainted(false);
        iconoAvion.setContentAreaFilled(false);

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(iconoAvion);
        panelMenu.add(Box.createVerticalStrut(30));
        panelMenu.add(notificaciones);
        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(cerrarSesion);

        // === CONTENUTO CENTRALE ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");
        gestionVuelos = createContentButton("🛬 Gestión Vuelos");

        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx++;
        panelContenido.add(gestionVuelos, gbc);

        // === ASSEMBLA ===
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(66, 66, 66));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }

    private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 80));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
    
    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                // Limitar el tamaño del icono (por ejemplo, máximo 64x64)
                int iconWidth = Math.min(64, btnWidth / 5);
                int iconHeight = Math.min(64, btnHeight / 4);

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

    public void setControlador(ActionListener c) {
        notificaciones.addActionListener(c);
        cerrarSesion.addActionListener(c);
        busquedaVuelos.addActionListener(c);
        gestionVuelos.addActionListener(c);
    }
}

package interfaz.panelesControlador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton notificaciones;
    private JButton cerrarSesion;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;

    public ControladorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERALE ===
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(45, 45, 45));

        notificaciones = createMenuButton("Notificaciones", "resources/notification_icon.png");
        cerrarSesion = createMenuButton("Cerrar Sesión", "resources/logout_icon.png");
        ImageIcon icon = new ImageIcon("resources/logo_icon.png");

	    // Imposta dimensioni massime (es. 64x64)
	    int maxWidth = 64;
	    int maxHeight = 64;
	
	    // Scala l'immagine
	    Image scaledImage = icon.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);
	
	    // Crea il bottone con l'icona scalata
	    JButton iconoAvion = new JButton(scaledIcon);
	    iconoAvion.setPreferredSize(new Dimension(maxWidth + 20, maxHeight + 20)); // padding opzionale
	    iconoAvion.setMaximumSize(new Dimension(maxWidth + 20, maxHeight + 20)); // utile per BoxLayout
	
	    // Disattiva bordi e sfondo
	    iconoAvion.setEnabled(false);
	    iconoAvion.setBorderPainted(false);
	    iconoAvion.setContentAreaFilled(false);

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(iconoAvion);
        panelMenu.add(Box.createVerticalStrut(30));
        panelMenu.add(notificaciones);
        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(cerrarSesion);

        // === CONTENUTO CENTRALE ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");

        gestionVuelos = createContentButton("Gestión Vuelos");
        setScaledIcon(gestionVuelos, "resources/plane_icon.png");

        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx++;
        panelContenido.add(gestionVuelos, gbc);

        // === ASSEMBLA ===
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 50));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(66, 66, 66));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(scaled));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);

        return btn;
    }

    private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }

    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                int iconWidth = Math.min(64, btnWidth / 5);
                int iconHeight = Math.min(64, btnHeight / 4);

                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        iconWidth, iconHeight, Image.SCALE_SMOOTH
                );

                button.setIcon(new ImageIcon(scaledImage));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        });
    }

    public void setControlador(ActionListener c) {
        notificaciones.addActionListener(c);
        cerrarSesion.addActionListener(c);
        busquedaVuelos.addActionListener(c);
        gestionVuelos.addActionListener(c);
    }
}

package interfaz.panelesControlador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton notificaciones;
    private JButton cerrarSesion;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;

    public ControladorInicio() {
        setLayout(new BorderLayout());

        // === MENU LATERALE ===
        JPanel panelMenu = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getPreferredSize() {
                int parentWidth = getParent() != null ? getParent().getWidth() : 800;
                int calculatedWidth = (int) (parentWidth * 0.18);
                return new Dimension(calculatedWidth, super.getPreferredSize().height);
            }
        };
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate(); // forza il ricalcolo del layout con la nuova larghezza
            }
        });
        
        panelMenu.setBackground(new Color(45, 45, 45));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int menuWidth = (int) (screenSize.width * 0.18);
        


        // --- ICONA CENTRALE IN ALTO ---
        ImageIcon icon = new ImageIcon("resources/logo_icon.png");
        int maxWidth = 100;
        int maxHeight = 100;
        Image scaledImage = icon.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton iconoAvion = new JButton(scaledIcon);
        iconoAvion.setEnabled(false);
        iconoAvion.setBorderPainted(false);
        iconoAvion.setContentAreaFilled(false);
        iconoAvion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(new Color(45, 45, 45));
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));
        iconPanel.add(Box.createVerticalStrut(20));
        iconPanel.add(iconoAvion);
        iconPanel.add(Box.createVerticalStrut(30));

        // --- BOTTONI IN BASSO ---
        notificaciones = createMenuButton("Notificaciones", "resources/notification_icon.png");
        cerrarSesion = createMenuButton("Cerrar Sesión", "resources/logout_icon.png");

        JPanel bottomButtons = new JPanel();
        bottomButtons.setBackground(new Color(45, 45, 45));
        bottomButtons.setLayout(new BoxLayout(bottomButtons, BoxLayout.Y_AXIS));
        bottomButtons.add(Box.createVerticalGlue());
        bottomButtons.add(notificaciones);
        bottomButtons.add(Box.createVerticalStrut(10));
        bottomButtons.add(cerrarSesion);
        bottomButtons.add(Box.createVerticalStrut(20));

        // Assembla il menu laterale
        panelMenu.add(iconPanel, BorderLayout.NORTH);
        panelMenu.add(bottomButtons, BorderLayout.SOUTH);

        // === CONTENUTO CENTRALE ===
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        busquedaVuelos = createContentButton("Buscar Vuelos");
        setScaledIcon(busquedaVuelos, "resources/searchFlights_icon.png");

        gestionVuelos = createContentButton("Gestión Vuelos");
        setScaledIcon(gestionVuelos, "resources/plane_icon.png");

        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx++;
        panelContenido.add(gestionVuelos, gbc);

        // === ASSEMBLA ===
        add(panelMenu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 50));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(66, 66, 66));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(scaled));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);

        return btn;
    }

    private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        return btn;
    }

    public void setScaledIcon(JButton button, String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int btnWidth = button.getWidth();
                int btnHeight = button.getHeight();

                int iconWidth = Math.min(150, btnWidth / 5);
                int iconHeight = Math.min(150, btnHeight / 4);

                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        iconWidth, iconHeight, Image.SCALE_SMOOTH
                );

                button.setIcon(new ImageIcon(scaledImage));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
            }
        });
    }

    public void setControlador(ActionListener c) {
        notificaciones.addActionListener(c);
        cerrarSesion.addActionListener(c);
        busquedaVuelos.addActionListener(c);
        gestionVuelos.addActionListener(c);
    }
}*/

package interfaz.panelesControlador;

import interfaz.elementosComunes.FabricaBotones;
import interfaz.elementosComunes.MenuLateral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class ControladorInicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton busquedaVuelos;
    private JButton gestionVuelos;

    public ControladorInicio(ActionListener listener) {
        setLayout(new BorderLayout());

        // === MENU LATERALE riutilizzabile ===

        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        // === CONTENUTO CENTRALE ===
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        busquedaVuelos = FabricaBotones.crearBotonPrincipal("Buscar Vuelos", "resources/searchFlights_icon.png");
        gestionVuelos = FabricaBotones.crearBotonPrincipal("Gestión Vuelos", "resources/plane_icon.png");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelContenido.add(busquedaVuelos, gbc);

        gbc.gridx++;
        panelContenido.add(gestionVuelos, gbc);

        add(panelContenido, BorderLayout.CENTER);

    }

	// método para asignar un controlador a los botones
 	public void setControlador(ActionListener c) {  
 		busquedaVuelos.addActionListener(c);
 		gestionVuelos.addActionListener(c);
 	}
}



