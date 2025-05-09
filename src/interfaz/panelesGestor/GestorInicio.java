package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaz.util.MenuLateral;
import sistema.SkyManager;
import usuarios.Usuario;

/**
 * Clase que representa la pantalla de inicio del gestor dentro del sistema.
 * Permite al gestor acceder a diferentes módulos de administración, como gestión de vuelos, usuarios y estadísticas.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class GestorInicio extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton busquedaVuelos;
	private JButton gestionVuelos;
	private JButton gestionAeropuerto;
	private JButton gestionFacturas;
	private JButton gestionUsuarios;
	private JButton estadisticas;
	private JLabel bienvenida;
	
	/**
     * Constructor de la clase `GestorInicio`.
     * Configura la interfaz gráfica con los botones de acceso a los diferentes módulos de administración.
     */
	public GestorInicio() {
		setLayout(new BorderLayout());
		
		// === MENU LATERAL ===
        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        
        // Mensaje bienvenida
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new GridBagLayout());
        panelGeneral.setBackground(new Color(173, 216, 230));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Bienvenida (si hay usuario)
        bienvenida = new JLabel();
        bienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        bienvenida.setForeground(new Color(112, 128, 144));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelGeneral.add(bienvenida, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(2, 3, 30, 30));
        panelCentral.setBackground(new Color(173, 216, 230));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botones principales
        gestionUsuarios = new JButton("Gestión Usuarios");
        formatoBotones(gestionUsuarios);
        setScaledIcon(gestionUsuarios, "resources/iconoGestionUsuarios.png");
        panelCentral.add(gestionUsuarios);
        
        gestionFacturas = new JButton("Gestión Facturas");
        formatoBotones(gestionFacturas);
        setScaledIcon(gestionFacturas, "resources/iconoFacturas.png");
        panelCentral.add(gestionFacturas);
        
        gestionAeropuerto = new JButton("Gestión Aeropuerto");
        formatoBotones(gestionAeropuerto);
        setScaledIcon(gestionAeropuerto, "resources/iconoGestionAeropuerto.png");
        panelCentral.add(gestionAeropuerto);
        
        busquedaVuelos = new JButton("Búsqueda Vuelos");
        formatoBotones(busquedaVuelos);
        setScaledIcon(busquedaVuelos, "resources/iconoBusquedaVuelos.png");
        panelCentral.add(busquedaVuelos);

        estadisticas = new JButton("Estadísticas");
        formatoBotones(estadisticas);
        setScaledIcon(estadisticas, "resources/iconoEstadisticas.png");
        panelCentral.add(estadisticas);
        
        gestionVuelos = new JButton("Gestión Vuelos");
        formatoBotones(gestionVuelos);
        setScaledIcon(gestionVuelos, "resources/iconoGestionVuelos.png");
        panelCentral.add(gestionVuelos);
        
        gbc.gridy = 1;  // Mover una fila hacia abajo
        gbc.gridwidth = 2;
        panelGeneral.add(panelCentral, gbc);

        // Agregar los paneles
        add(menu, BorderLayout.WEST);
        add(panelGeneral, BorderLayout.CENTER);
   }
		
	/**
     * Aplica formato de estilo a los botones de la interfaz.
     * 
     * @param boton Botón a formatear.
     */
	private void formatoBotones(JButton boton) {
		boton.setFont(new Font("Arial", Font.BOLD, 18));
		boton.setBackground(new Color(112, 128, 144));
		boton.setForeground(Color.WHITE);
	}

	/**
     * Asigna un controlador de eventos a los botones de la interfaz.
     * 
     * @param c Controlador de eventos que manejará la acción de cada botón.
     */
	public void setControlador(ActionListener c) {
		busquedaVuelos.setActionCommand("BUSQUEDA_VUELOS");
		gestionVuelos.setActionCommand("GESTION_VUELOS");
		gestionAeropuerto.setActionCommand("GESTION_AEROPUERTO");
		gestionFacturas.setActionCommand("GESTION_FACTURAS");
		gestionUsuarios.setActionCommand("GESTION_USUARIOS");
		estadisticas.setActionCommand("ESTADISTICAS");
		
		busquedaVuelos.addActionListener(c);
		gestionVuelos.addActionListener(c);
		gestionAeropuerto.addActionListener(c);
		gestionFacturas.addActionListener(c);
		gestionUsuarios.addActionListener(c);
		estadisticas.addActionListener(c);
	}
	
	/**
     * Configura y ajusta el tamaño de los iconos en los botones de la interfaz.
     * 
     * @param button Botón al que se le asignará un icono.
     * @param imagePath Ruta de la imagen utilizada como icono.
     */
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
	
	/**
     * Actualiza la pantalla con el nombre del usuario actual en el mensaje de bienvenida.
     */
	public void actualizarPantalla() {
    	Usuario usuarioActual = SkyManager.getInstance().getUsuarioActual();
    	if(usuarioActual != null) {
    		bienvenida.setText("Bienvenid@ " + usuarioActual.getNombre());
    	}
    }

}
