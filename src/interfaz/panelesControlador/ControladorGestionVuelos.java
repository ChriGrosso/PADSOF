package interfaz.panelesControlador;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import interfaz.util.NonEditableTableModel;
import sistema.SkyManager;

import java.awt.*;

/**
 * Clase ControladorGestionVuelos - Vista principal para la gestión de vuelos por parte del controlador.
 * 
 * Permite visualizar una tabla de vuelos y acceder a un formulario para modificar el estado
 * y la pista asignada de un vuelo seleccionado. Usa un diseño con CardLayout para alternar
 * entre la vista principal y el formulario de edición.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
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

    /**
     * Constructor de la clase. Inicializa los componentes gráficos y configura el layout principal.
     */
    public ControladorGestionVuelos() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // === PANEL PRINCIPAL ===
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(173, 216, 230));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        botonVolver = new BotonVolver("resources/atras_icon.png");
        botonVolver.setControladorVolver(_ -> paginaAnterior());
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

        // === FORMULARIO DE MODIFICACIÓN ===
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

    /**
     * Vuelve a la pantalla de inicio del controlador, guardando los datos actuales.
     */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showContInicio();
	}

	/**
     * Aplica un estilo visual uniforme a los botones.
     *
     * @param boton Botón al que se le aplicará el estilo.
     */
    private void personalizarBottone(JButton boton) {
        boton.setBackground(new Color(135, 206, 250));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(120, 40));
    }

    // === MÉTODOS GETTER PARA ACCESO EXTERNO A LOS COMPONENTES ===

    /**
     * Devuelve la tabla de vuelos.
     * @return JTable de vuelos.
     */
    public JTable getTabla() { return tabla; }

    /**
     * Devuelve el modelo de la tabla.
     * @return Modelo no editable de la tabla.
     */
    public NonEditableTableModel getModelo() { return modelo; }

    /**
     * Devuelve el botón de volver.
     * @return Botón personalizado de volver.
     */
    public BotonVolver getBotonVolver() { return botonVolver; }

    /**
     * Devuelve el botón "Modificar".
     * @return Botón de modificación de vuelo.
     */
    public JButton getBotonModificar() { return botonModificar; }

    /**
     * Devuelve el combo de estados de vuelo.
     * @return JComboBox de estados.
     */
    public JComboBox<String> getComboEstado() { return comboEstado; }

    /**
     * Devuelve el combo de pistas disponibles.
     * @return JComboBox de pistas.
     */
    public JComboBox<String> getComboPistas() { return comboPistas; }

    /**
     * Devuelve el botón "Guardar".
     * @return Botón para confirmar los cambios.
     */
    public JButton getBotonGuardar() { return botonGuardar; }

    /**
     * Devuelve el botón "Cancelar".
     * @return Botón para cancelar la edición.
     */
    public JButton getBotonCancelar() { return botonCancelar; }

    /**
     * Muestra el formulario de modificación de vuelo.
     */
    public void mostraFormModifica() {
        cardLayout.show(this, "formulario");
    }

    /**
     * Muestra la vista principal con la tabla de vuelos.
     */
    public void mostraVistaPrincipale() {
        cardLayout.show(this, "principal");
    }

    /**
     * Asigna el controlador a los componentes interactivos de esta vista.
     *
     * @param controlControladorGestionVuelos Controlador correspondiente.
     */
    
}
