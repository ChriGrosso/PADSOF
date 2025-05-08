package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;

public class GestorEstadisticas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public GestorEstadisticas() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(0, 60, 60, 60));
		
		JPanel panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		panelSuperiorIzquierdo.setBackground(new Color(173, 216, 230));
		
		// Contenedor en la esquina superior derecha
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> paginaAnterior());

        // Título
	    JLabel titulo = new JLabel("Estadísticas", SwingConstants.CENTER);
	    titulo.setForeground(new Color(70, 130, 180));
	    titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
	    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
	    
	    // Añadir el contenedor al panel superior
        panelSuperiorIzquierdo.add(panelAtras, BorderLayout.NORTH);
	    panelSuperiorIzquierdo.add(titulo, BorderLayout.AFTER_LAST_LINE);
	    add(panelSuperiorIzquierdo, BorderLayout.NORTH);
        
        //Crear las pestañas para mostrar cada tipo de estadisticas
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Fingers", new panelEstadisticasEstructura("Finger"));
        tabs.addTab("Puertas", new panelEstadisticasEstructura("Puerta"));
        tabs.addTab("Aparcamientos", new panelEstadisticasEstructura("Aparcamiento"));
        tabs.addTab("Hangares", new panelEstadisticasEstructura("Hangar"));
        add(tabs, BorderLayout.CENTER);

	}
	
	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showGestorInicio();
	}
	
}
