package interfaz.panelesControlador;

import interfaz.elementosComunes.BotonVolver;
import interfaz.elementosComunes.MenuLateral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControladorBusquedaVuelos extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField campoOrigen;
    private JTextField campoDestino;
    private JTextField campoIdAvion;
    private JTextField campoIdVuelo;
    private JComboBox<String> comboEstado;
    private JButton botonBuscar;
    private JButton botonReset;
    private JTable tabla;
    private DefaultTableModel modelo;
    private BotonVolver botonVolver;

    public ControladorBusquedaVuelos() {
        setLayout(new BorderLayout());

        MenuLateral menu = new MenuLateral("resources/logo_icon.png");
        add(menu, BorderLayout.WEST);

        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);

        // Botón volver en la parte superior
        botonVolver = new BotonVolver("resources/atras_icon.png");
        panelContenido.add(botonVolver, BorderLayout.NORTH);

        // Panel con filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoOrigen = new JTextField(10);
        campoDestino = new JTextField(10);
        campoIdAvion = new JTextField(10);
        campoIdVuelo = new JTextField(10);
        comboEstado = new JComboBox<>(new String[] {"Todos", "Programado", "Aterrizado", "Retrasado", "Cancelado"});
        botonBuscar = new JButton("Buscar");
        botonReset = new JButton("Reset");

        gbc.gridx = 0; gbc.gridy = 0; panelFiltros.add(new JLabel("Origen:"), gbc);
        gbc.gridx = 1; panelFiltros.add(campoOrigen, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelFiltros.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1; panelFiltros.add(campoDestino, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panelFiltros.add(new JLabel("Id Avión:"), gbc);
        gbc.gridx = 1; panelFiltros.add(campoIdAvion, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panelFiltros.add(new JLabel("Id Vuelo:"), gbc);
        gbc.gridx = 1; panelFiltros.add(campoIdVuelo, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panelFiltros.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; panelFiltros.add(comboEstado, gbc);

        gbc.gridx = 2; gbc.gridy = 1; panelFiltros.add(botonBuscar, gbc);
        gbc.gridx = 2; gbc.gridy = 2; panelFiltros.add(botonReset, gbc);

        panelContenido.add(panelFiltros, BorderLayout.CENTER);

        String[] columnas = {"ID Vuelo", "Origen", "Destino", "Estado", "Terminal", "Fecha", "ID Avión"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelContenido.add(scrollPane, BorderLayout.SOUTH);

        add(panelContenido, BorderLayout.CENTER);
    }

    public JTextField getCampoOrigen() { return campoOrigen; }
    public JTextField getCampoDestino() { return campoDestino; }
    public JTextField getCampoIdAvion() { return campoIdAvion; }
    public JTextField getCampoIdVuelo() { return campoIdVuelo; }
    public JComboBox<String> getComboEstado() { return comboEstado; }
    public JButton getBotonBuscar() { return botonBuscar; }
    public JButton getBotonReset() { return botonReset; }
    public DefaultTableModel getModeloTabla() { return modelo; }
    public BotonVolver getBotonVolver() { return botonVolver; }

    public void setControlador(ControlControladorBusquedaVuelos control) {
        botonBuscar.addActionListener(e -> control.filtraTabella());
        botonReset.addActionListener(e -> control.resettaCampi());
        botonVolver.setControladorVolver(e -> control.tornaIndietro());
    }
}
