package interfaz.panelesControlador;

import interfaz.elementosComunes.BotonVolver;
import interfaz.util.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;

public class ControladorGestionVuelos extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private NonEditableTableModel modelo;
    private BotonVolver botonVolver;
    private JButton botonModificar;

    private JPanel panelPrincipal;
    private JPanel panelFormulario;
    private CardLayout cardLayout;

    private JComboBox<String> comboEstado;
    private JComboBox<String> comboPistas;
    private JButton botonGuardar;
    private JButton botonCancelar;

    public ControladorGestionVuelos() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(173, 216, 230));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        botonVolver = new BotonVolver("resources/atras.png");
        panelPrincipal.add(botonVolver, BorderLayout.NORTH);

        String[] columnas = {"ID", "Origen", "Destino", "Fecha", "Tipo Avión", "Estado", "Pista Asignata"};
        modelo = new NonEditableTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.setRowHeight(50);
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.setGridColor(new Color(75, 135, 185));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(112, 128, 144)));
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        botonModificar = new JButton("Modificar");
        personalizarBottone(botonModificar);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(new Color(173, 216, 230));
        panelBoton.add(botonModificar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Form modifica (senza botonVolver)
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Stato Volo:"), gbc);
        gbc.gridx = 1;
        comboEstado = new JComboBox<>();
        panelFormulario.add(comboEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Pista Assegnata:"), gbc);
        gbc.gridx = 1;
        comboPistas = new JComboBox<>();
        panelFormulario.add(comboPistas, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        botonGuardar = new JButton("Salva");
        personalizarBottone(botonGuardar);
        panelFormulario.add(botonGuardar, gbc);

        gbc.gridx = 1;
        botonCancelar = new JButton("Annulla");
        personalizarBottone(botonCancelar);
        panelFormulario.add(botonCancelar, gbc);

        add(panelPrincipal, "principal");
        add(panelFormulario, "formulario");
    }

    private void personalizarBottone(JButton boton) {
        boton.setBackground(new Color(135, 206, 250));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(120, 40));
    }

    public JTable getTabla() { return tabla; }
    public NonEditableTableModel getModelo() { return modelo; }
    public BotonVolver getBotonVolver() { return botonVolver; }
    public JButton getBotonModificar() { return botonModificar; }

    public JComboBox<String> getComboEstado() { return comboEstado; }
    public JComboBox<String> getComboPistas() { return comboPistas; }
    public JButton getBotonGuardar() { return botonGuardar; }
    public JButton getBotonCancelar() { return botonCancelar; }

    public void mostraFormModifica() {
        cardLayout.show(this, "formulario");
    }

    public void mostraVistaPrincipale() {
        cardLayout.show(this, "principal");
    }

	public void setControlador(ControlControladorGestionVuelos controlControladorGestionVuelos) {
		botonVolver.setControladorVolver(e -> controlControladorGestionVuelos.tornaIndietro());
		
	}
}