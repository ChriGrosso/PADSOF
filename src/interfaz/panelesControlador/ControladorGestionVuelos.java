package interfaz.panelesControlador;

import interfaz.elementosComunes.BotonVolver;
import interfaz.elementosComunes.MenuLateral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControladorGestionVuelos extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modelo;
    private BotonVolver botonVolver;

    public ControladorGestionVuelos() {
        setLayout(new BorderLayout());

        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);

        botonVolver = new BotonVolver("resources/back_icon.png");
        panelContenido.add(botonVolver, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panelContenido.add(scroll, BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);
    }

    public JTable getTabla() { return tabla; }
    public DefaultTableModel getModelo() { return modelo; }
    public BotonVolver getBotonVolver() { return botonVolver; }

	public void setControlador(ControlControladorGestionVuelos controlControladorGestionVuelos) {
		
		
	}
}
