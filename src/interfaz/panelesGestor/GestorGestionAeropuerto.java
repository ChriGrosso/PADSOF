package interfaz.panelesGestor;

import javax.swing.*;

import aeropuertos.Direccion;
import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;


import java.awt.*;

public class GestorGestionAeropuerto extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton botonVolver;
    private JTabbedPane tabbedPane;
    private JPanel panelPistas, panelTerminales, panelFingers, panelZonasParking, panelHangares, panelAeropuertosExternos, panelAeropuertoPropio;
    private JPanel panelPuertas;
    
    private JLabel labelCodigoAeropuerto;
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
        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        // Pannello superiore (bottone + titolo)
        JPanel panelSuperiore = new JPanel(new BorderLayout());
        panelSuperiore.setBackground(new Color(173, 216, 230));

        // Bottone "Volver"
        BotonVolver panelAtras = new BotonVolver("resources/atras_icon.png");
        panelAtras.setControladorVolver(_ -> {
            SkyManager.getInstance().guardarDatos();
            Aplicacion.getInstance().showGestorInicio();
        });
        panelSuperiore.add(panelAtras, BorderLayout.NORTH);

        // Titolo
        JLabel titolo = new JLabel("Gestión de Aeropuerto", SwingConstants.CENTER);
        titolo.setForeground(new Color(70, 130, 180));
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelSuperiore.add(titolo, BorderLayout.AFTER_LAST_LINE);

        add(panelSuperiore, BorderLayout.NORTH);

        // TabbedPane (parte centrale)
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(new Color(70, 130, 180));
        tabbedPane.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));

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

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(new Color(173, 216, 230));
        centro.add(tabbedPane, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    
 // Getter per i campi del formulario Aeropuerto Propio
    public JLabel getCampoCodigoAeropuerto() {
        return labelCodigoAeropuerto;
    }
    
    public void setCampoCodigoAeropuerto(JLabel label) {
        this.labelCodigoAeropuerto = label;
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
