package interfaz.panelesGestor;

import javax.swing.*;

import aeropuertos.Direccion;

import java.awt.*;

public class GestorGestionAeropuerto extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton botonVolver;
    private JTabbedPane tabbedPane;
    private JPanel panelPistas, panelTerminales, panelFingers, panelZonasParking, panelHangares, panelAeropuertosExternos, panelAeropuertoPropio;
    private JPanel panelPuertas;
    
    private JTextField campoCodigoAeropuerto;
    private JTextField campoNombreAeropuerto;
    private JTextField campoPaisAeropuerto;
    private JTextField campoCiudadAeropuerto;
    private JTextField campoDistanciaAeropuerto;
    private JTextField campoGMTAeropuerto;
    private JComboBox<Direccion> comboDireccionAeropuerto;
    private JTextField campoCostoBaseFactura;
    private JTextField campoCostoHoraPista;
    private JTextField campoCostoHoraTerminal;
    private JTextField campoCostoHoraFinger;
    private JTextField campoCostoHoraHangar;
    private JTextField campoCostoHoraAutobus;


    public GestorGestionAeropuerto() {
        setLayout(new BorderLayout());

     // Parte superiore: Panel con boton Volver
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonVolver = new JButton("Atrás"); // o "Volver" come preferisci
        botonVolver.setIcon(new ImageIcon("resources/icons/atras_icon.png")); // ✅ icona
        botonVolver.setBackground(new Color(135, 206, 250)); // ✅ colore sfondo
        botonVolver.setFocusPainted(false); // ✅ niente focus brutto
        botonVolver.setBorderPainted(false); // ✅ niente bordo brutto
        botonVolver.setContentAreaFilled(true); // ✅ area interna colorata
        botonVolver.setPreferredSize(new java.awt.Dimension(120, 35)); // ✅ dimensione coerente
        panelSuperior.add(botonVolver);
        add(panelSuperior, BorderLayout.NORTH);

        // Parte centrale: TabbedPane
        tabbedPane = new JTabbedPane();
        
        panelFingers = new JPanel(new CardLayout());
        panelZonasParking = new JPanel(new CardLayout());
        panelHangares = new JPanel(new CardLayout());
        panelAeropuertosExternos = new JPanel(new CardLayout());
        panelAeropuertoPropio = new JPanel(new CardLayout());
        panelPistas = new JPanel(new CardLayout());
        panelTerminales = new JPanel(new CardLayout());
        panelPuertas = new JPanel(new CardLayout());
        
        

        
        tabbedPane.addTab("Pistas", panelPistas);
        tabbedPane.addTab("Terminales", panelTerminales);
        tabbedPane.addTab("Fingers", panelFingers);
        tabbedPane.addTab("Zonas Parking", panelZonasParking);
        tabbedPane.addTab("Hangares", panelHangares);
        tabbedPane.addTab("Aeropuertos Externos", panelAeropuertosExternos);
        tabbedPane.addTab("Aeropuerto Propio", panelAeropuertoPropio);
        tabbedPane.addTab("Puertas", panelPuertas);


        add(tabbedPane, BorderLayout.CENTER);
    }
    
 // Getter per i campi del formulario Aeropuerto Propio
    public JTextField getCampoCodigoAeropuerto() {
        return campoCodigoAeropuerto;
    }

    public JTextField getCampoNombreAeropuerto() {
        return campoNombreAeropuerto;
    }

    public JTextField getCampoPaisAeropuerto() {
        return campoPaisAeropuerto;
    }

    public JTextField getCampoCiudadAeropuerto() {
        return campoCiudadAeropuerto;
    }

    public JTextField getCampoDistanciaAeropuerto() {
        return campoDistanciaAeropuerto;
    }

    public JTextField getCampoGMTAeropuerto() {
        return campoGMTAeropuerto;
    }

    public JComboBox<Direccion> getComboDireccionAeropuerto() {
        return comboDireccionAeropuerto;
    }

    public JTextField getCampoCostoBaseFactura() {
        return campoCostoBaseFactura;
    }

    public JTextField getCampoCostoHoraPista() {
        return campoCostoHoraPista;
    }

    public JTextField getCampoCostoHoraTerminal() {
        return campoCostoHoraTerminal;
    }

    public JTextField getCampoCostoHoraFinger() {
        return campoCostoHoraFinger;
    }

    public JTextField getCampoCostoHoraHangar() {
        return campoCostoHoraHangar;
    }

    public JTextField getCampoCostoHoraAutobus() {
        return campoCostoHoraAutobus;
    }


    public JButton getBotonVolver() {
        return botonVolver;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getPanelPistas() {
        return panelPistas;
    }

    public JPanel getPanelTerminales() {
        return panelTerminales;
    }

    public JPanel getPanelFingers() {
        return panelFingers;
    }

    public JPanel getPanelZonasParking() {
        return panelZonasParking;
    }

    public JPanel getPanelHangares() {
        return panelHangares;
    }

    public JPanel getPanelAeropuertosExternos() {
        return panelAeropuertosExternos;
    }

    public JPanel getPanelAeropuertoPropio() {
        return panelAeropuertoPropio;
    }
    
    public JPanel getPanelPuertas() {
        return panelPuertas;
    }
    


	public void setControlador(ControlGestorGestionAeropuerto controlGestorGestAeropuerto) {
				
	}
}
