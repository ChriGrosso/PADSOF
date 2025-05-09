package interfaz.panelesGestor;
import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import aeropuertos.Aeropuerto;
import aeropuertos.Direccion;
import elementos.Finger;
import elementos.Hangar;
import elementos.HangarMercancias;
import elementos.HangarPasajeros;
import elementos.Pista;
import elementos.Puerta;
import elementos.Terminal;
import elementos.TerminalMercancias;
import elementos.TerminalPasajeros;
import elementos.ZonaParking;
import interfaz.util.NonEditableTableModel;
import sistema.SkyManager;

/**
 * Clase ControlGestorGestionAeropuerto - Controlador encargado de gestionar
 * todas las acciones relacionadas con la administración del aeropuerto propio
 * y sus elementos (pistas, terminales, hangares, zonas de parking, etc.).
 *
 * Contiene lógica para inicializar formularios, cargar tablas, validar entradas,
 * y actualizar el modelo SkyManager con los datos proporcionados por el usuario.
 * Utiliza diseños CardLayout para alternar entre vistas de lista y formularios.
 *
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */

public class ControlGestorGestionAeropuerto {
    private GestorGestionAeropuerto vista;

    /**
     * Constructor que recibe la vista principal y lanza la inicialización de todos los paneles.
     * @param vista instancia de la clase vista que contiene todos los paneles.
     */
    public ControlGestorGestionAeropuerto(GestorGestionAeropuerto vista) {
        this.vista = vista;
        inizializza();
     // Listener cambio di tab
        
    }

    /**
     * Método principal que inicializa todos los paneles funcionales del aeropuerto.
     */
    private void inizializza() {
    	vista.getTabbedPane().addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int selectedIndex = sourceTabbedPane.getSelectedIndex();
            String selectedTitle = sourceTabbedPane.getTitleAt(selectedIndex);

            if ("Aeropuerto Propio".equals(selectedTitle)) {
                mostrarTabAeropuertoPropio();
            }
        });
    	
        inicializarPanelPistas();
        inicializarPanelFingers();
        inicializarPanelHangar();
        inicializarPanelTerminales();
        inicializarPanelPuertas();
        inicializarPanelAeropuertosExternos();
        inicializarPanelZonaParking();
        inicializarPanelAeropuertoPropio();
    }
    
    /**
     * Inicializa el panel de gestión de pistas dentro de la interfaz de gestión del aeropuerto.
     * Configura tanto la vista de lista como el formulario de edición/inserción de pistas,
     * y asigna los correspondientes eventos a los botones de acción.
     *
     * Se crean componentes gráficos para mostrar la lista de pistas existentes,
     * así como un formulario que permite añadir o modificar pistas, definiendo
     * tipo (despegue/aterrizaje) y longitud.
     *
     * Los botones permiten al usuario:
     * <ul>
     *   <li>Añadir una nueva pista</li>
     *   <li>Modificar una pista seleccionada</li>
     *   <li>Eliminar una pista (si no está en uso)</li>
     *   <li>Cancelar la edición</li>
     *   <li>Guardar una nueva pista o una modificación</li>
     * </ul>
     *
     * También se encarga de cargar las pistas ya existentes desde el modelo {@code SkyManager}
     * y mostrarlas en una tabla no editable.
     *
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelPistas() {
	    JPanel panelLista = new JPanel(new BorderLayout());
	    JPanel panelFormulario = new JPanel(new GridBagLayout());
	
	    // Componenti per lista
	    JTable tablaPistas = new JTable(new NonEditableTableModel(new Object[]{"ID", "Tipo", "Longitud"}, 0));
	    personalizarTabla(tablaPistas);
	    JScrollPane scrollPane = new JScrollPane(tablaPistas);
	
	    JPanel barraBotones = new JPanel();
	    barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
	    JButton botonAñadir = new JButton("Añadir");
	    JButton botonModificar = new JButton("Modificar");
	    JButton botonEliminar = new JButton("Eliminar");
	
	    barraBotones.add(botonAñadir);
	    barraBotones.add(botonModificar);
	    barraBotones.add(botonEliminar);
	    personalizarBoton(botonAñadir);
	    personalizarBoton(botonModificar);
	    personalizarBoton(botonEliminar);
	
	    panelLista.add(barraBotones, BorderLayout.SOUTH);
	    panelLista.add(scrollPane, BorderLayout.CENTER);
	
	    // Componenti per formulario
	    JLabel labelTipo = new JLabel("Tipo:");
	    JRadioButton radioDespegue = new JRadioButton("Despegue", true);
	    JRadioButton radioAterrizaje = new JRadioButton("Aterrizaje");
	    ButtonGroup grupoTipo = new ButtonGroup();
	    grupoTipo.add(radioDespegue);
	    grupoTipo.add(radioAterrizaje);
	
	    JLabel labelLongitud = new JLabel("Longitud (m):");
	    JTextField campoLongitud = new JTextField(10);
	
	    JButton botonGuardar = new JButton("Guardar");
	    personalizarBoton(botonGuardar);
	    JButton botonCancelar = new JButton("Cancelar");
	    personalizarBoton(botonCancelar);
	
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5,5,5,5);
	    gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelTipo, gbc);
	    gbc.gridx = 1; gbc.gridy = 0; panelFormulario.add(radioDespegue, gbc);
	    gbc.gridx = 2; gbc.gridy = 0; panelFormulario.add(radioAterrizaje, gbc);
	
	    gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(labelLongitud, gbc);
	    gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; panelFormulario.add(campoLongitud, gbc);
	
	    gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(botonGuardar, gbc);
	    gbc.gridx = 1; gbc.gridy = 2; panelFormulario.add(botonCancelar, gbc);
	
	    // CardLayout su panelPistas
	    JPanel panelPistas = vista.getPanelPistas();
	    panelPistas.add(panelLista, "listaPistas");
	    panelPistas.add(panelFormulario, "formularioPistas");
	
	    CardLayout cardLayout = (CardLayout) panelPistas.getLayout();
	    cardLayout.show(panelPistas, "listaPistas");
	
	    // ACCESSO AL MODELLO
	    SkyManager modelo = SkyManager.getInstance();
	
	    // Carichiamo le piste già esistenti
	    NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaPistas.getModel();
	    for (Pista p : modelo.getPistas().values()) {
	        modeloTabla.addRow(new Object[]{
	            p.getId(),
	            p.isDespegue() ? "Despegue" : "Aterrizaje",
	            p.getLongitud()
	        });
	    }
	
	    // Variabile per sapere se stiamo modificando
	    final Pista[] pistaSeleccionada = {null};
	
	    // EVENTI
	    botonAñadir.addActionListener(_ -> {
	        campoLongitud.setText("");
	        radioDespegue.setSelected(true);
	        pistaSeleccionada[0] = null;
	        cardLayout.show(panelPistas, "formularioPistas");
	    });
	
	    botonModificar.addActionListener(_ -> {
	        int fila = tablaPistas.getSelectedRow();
	        if (fila == -1) {
	            JOptionPane.showMessageDialog(null, "Seleccione una pista para modificar.");
	            return;
	        }
	        String id = (String) tablaPistas.getValueAt(fila, 0);
	        pistaSeleccionada[0] = modelo.getPistas().get(id);
	        if (pistaSeleccionada[0] != null) {
	            radioDespegue.setSelected(pistaSeleccionada[0].isDespegue());
	            radioAterrizaje.setSelected(!pistaSeleccionada[0].isDespegue());
	            campoLongitud.setText(String.valueOf(pistaSeleccionada[0].getLongitud()));
	            cardLayout.show(panelPistas, "formularioPistas");
	        }
	    });
	
	    botonEliminar.addActionListener(_ -> {
	        int fila = tablaPistas.getSelectedRow();
	        if (fila == -1) {
	            JOptionPane.showMessageDialog(null, "Seleccione una pista para eliminar.");
	            return;
	        }
	        String id = (String) tablaPistas.getValueAt(fila, 0);
	        Pista pista = modelo.getPistas().get(id);
	
	        if (pista.enUso() || !pista.getVuelos().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No se puede eliminar: la pista está en uso o tiene vuelos asignados.");
	            return;
	        }
	
	        modelo.getPistas().remove(id);
	        ((NonEditableTableModel) tablaPistas.getModel()).removeRow(fila);
	        JOptionPane.showMessageDialog(null, "Pista eliminada correctamente.");
	    });
	
	    botonCancelar.addActionListener(_ -> {
	        cardLayout.show(panelPistas, "listaPistas");
	    });
	
	    botonGuardar.addActionListener(_ -> {
	        String longitudStr = campoLongitud.getText().trim();
	        if (longitudStr.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Por favor, ingrese la longitud.");
	            return;
	        }
	
	        double longitud;
	        try {
	            longitud = Double.parseDouble(longitudStr);
	            if (longitud <= 0) throw new NumberFormatException();
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, "La longitud debe ser un número positivo.");
	            return;
	        }
	
	        boolean despegue = radioDespegue.isSelected();
	
	        if (pistaSeleccionada[0] == null) {
	            // NUEVA PISTA
	            Pista nuevaPista = new Pista(LocalDate.now(), despegue, longitud);
	            modelo.getPistas().put(nuevaPista.getId(), nuevaPista);
	            ((NonEditableTableModel) tablaPistas.getModel()).addRow(new Object[]{
	                nuevaPista.getId(),
	                nuevaPista.isDespegue() ? "Despegue" : "Aterrizaje",
	                nuevaPista.getLongitud()
	            });
	        } else {
	            // MODIFICAR EXISTENTE
	            pistaSeleccionada[0].setDespegue(despegue);
	            pistaSeleccionada[0].setLongitud(longitud);
	
	            int fila = tablaPistas.getSelectedRow();
	            tablaPistas.setValueAt(despegue ? "Despegue" : "Aterrizaje", fila, 1);
	            tablaPistas.setValueAt(longitud, fila, 2);
	        }
	
	        pistaSeleccionada[0] = null;
	        cardLayout.show(panelPistas, "listaPistas");
	    });
	}
    
    /**
     * Inicializa el panel correspondiente a la gestión de terminales en el aeropuerto.
     * Define la vista principal de lista y el formulario de creación/modificación de terminales.
     *
     * Los terminales pueden ser de tipo "Pasajeros" o "Mercancías", y se configuran 
     * especificando el número de puertas por cada prefijo. Durante la edición, no se permite 
     * cambiar el tipo ni los prefijos ya definidos.
     *
     * Funcionalidades disponibles:
     * <ul>
     *   <li>Añadir un nuevo terminal con sus puertas generadas automáticamente.</li>
     *   <li>Modificar el número de puertas de un terminal existente (no los prefijos ni el tipo).</li>
     *   <li>Eliminar un terminal si no contiene puertas asignadas.</li>
     *   <li>Cancelar la edición o la creación.</li>
     * </ul>
     *
     * Los datos se cargan directamente desde el modelo {@code SkyManager} y se representan en una tabla.
     *
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelTerminales() {
        JPanel panelLista = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());

        // Componenti per lista
        JTable tablaTerminales = new JTable(new NonEditableTableModel(new Object[]{"ID", "Tipo", "Puertas"}, 0));
        personalizarTabla(tablaTerminales);
        JScrollPane scrollPane = new JScrollPane(tablaTerminales);

        JPanel barraBotones = new JPanel();
        barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonAñadir = new JButton("Añadir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        personalizarBoton(botonAñadir);
        personalizarBoton(botonModificar);
        personalizarBoton(botonEliminar);

        barraBotones.add(botonAñadir);
        barraBotones.add(botonModificar);
        barraBotones.add(botonEliminar);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        // Componenti per formulario
        JLabel labelTipoTerminal = new JLabel("Tipo:");
        JRadioButton rdbtnPasajeros = new JRadioButton("Pasajeros", true);
        JRadioButton rdbtnMercancias = new JRadioButton("Mercancias");
        ButtonGroup grupoTipoTerminal = new ButtonGroup();
        grupoTipoTerminal.add(rdbtnPasajeros);
        grupoTipoTerminal.add(rdbtnMercancias);

        JLabel labelNumeroPuertas = new JLabel("Número de Puertas por Prefijo:");
        JTextField campoNumeroPuertas = new JTextField(10);

        JLabel labelPrefijosPuertas = new JLabel("Prefijos (separados por coma):");
        JTextField campoPrefijosPuertas = new JTextField(10);

        JButton botonGuardar = new JButton("Guardar");
        personalizarBoton(botonGuardar);
        JButton botonCancelar = new JButton("Cancelar");
        personalizarBoton(botonCancelar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelTipoTerminal, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelFormulario.add(rdbtnPasajeros, gbc);
        gbc.gridx = 2; gbc.gridy = 0; panelFormulario.add(rdbtnMercancias, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(labelNumeroPuertas, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; panelFormulario.add(campoNumeroPuertas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(labelPrefijosPuertas, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; panelFormulario.add(campoPrefijosPuertas, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // CardLayout su panelTerminales
        JPanel panelTerminales = vista.getPanelTerminales();
        panelTerminales.add(panelLista, "listaTerminales");
        panelTerminales.add(panelFormulario, "formularioTerminales");

        CardLayout cardLayout = (CardLayout) panelTerminales.getLayout();
        cardLayout.show(panelTerminales, "listaTerminales");

        // ACCESSO AL MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // Carichiamo terminali esistenti
        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaTerminales.getModel();
        for (Terminal t : modelo.getTerminales().values()) {
            String tipo = (t instanceof TerminalPasajeros) ? "Pasajeros" : "Mercancias";
            modeloTabla.addRow(new Object[]{
                t.getId(),
                tipo,
                t.getPuertas().size()
            });
        }

        final Terminal[] terminalSeleccionado = {null};

        // EVENTI
        botonAñadir.addActionListener(_ -> {
            campoNumeroPuertas.setText("");
            campoPrefijosPuertas.setText("");
            rdbtnPasajeros.setSelected(true);
            rdbtnMercancias.setSelected(false);
            rdbtnPasajeros.setEnabled(true);
            rdbtnMercancias.setEnabled(true);
            terminalSeleccionado[0] = null;
            cardLayout.show(panelTerminales, "formularioTerminales");
        });

        botonModificar.addActionListener(_ -> {
            int fila = tablaTerminales.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un terminal para modificar.");
                return;
            }
            String id = (String) tablaTerminales.getValueAt(fila, 0);
            terminalSeleccionado[0] = modelo.getTerminales().get(id);
            if (terminalSeleccionado[0] != null) {
                campoNumeroPuertas.setText(String.valueOf(terminalSeleccionado[0].getNumeroPuertas()));
                campoPrefijosPuertas.setText(""); // Lasciamo vuoto, non modificabile
                rdbtnPasajeros.setEnabled(false);
                rdbtnMercancias.setEnabled(false);
                if (terminalSeleccionado[0] instanceof TerminalPasajeros) {
                    rdbtnPasajeros.setSelected(true);
                } else {
                    rdbtnMercancias.setSelected(true);
                }
                cardLayout.show(panelTerminales, "formularioTerminales");
            }
        });

        botonEliminar.addActionListener(_ -> {
            int fila = tablaTerminales.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un terminal para eliminar.");
                return;
            }
            String id = (String) tablaTerminales.getValueAt(fila, 0);
            Terminal terminal = modelo.getTerminales().get(id);

            if (!terminal.getPuertas().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: el terminal tiene puertas asignadas.");
                return;
            }

            modelo.getTerminales().remove(id);
            ((NonEditableTableModel) tablaTerminales.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Terminal eliminado correctamente.");
        });

        botonCancelar.addActionListener(_ -> {
            cardLayout.show(panelTerminales, "listaTerminales");
        });

        botonGuardar.addActionListener(_ -> {
            String numeroPuertasStr = campoNumeroPuertas.getText().trim();
            String prefijosStr = campoPrefijosPuertas.getText().trim();

            if (numeroPuertasStr.isEmpty() || prefijosStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el número de puertas y los prefijos.");
                return;
            }

            int numeroPuertas;
            try {
                numeroPuertas = Integer.parseInt(numeroPuertasStr);
                if (numeroPuertas <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El número de puertas debe ser un número entero positivo.");
                return;
            }

            String[] prefijos = prefijosStr.split(",");

            if (prefijos.length == 0) {
                JOptionPane.showMessageDialog(null, "Debe ingresar al menos un prefijo.");
                return;
            }

            if (terminalSeleccionado[0] == null) {
                // NUOVO TERMINAL
                Terminal nuevoTerminal;
                if (rdbtnPasajeros.isSelected()) {
                    nuevoTerminal = new TerminalPasajeros(LocalDate.now(), numeroPuertas, prefijos);
                } else {
                    nuevoTerminal = new TerminalMercancias(LocalDate.now(), numeroPuertas, prefijos);
                }
                modelo.getTerminales().put(nuevoTerminal.getId(), nuevoTerminal);
                ((NonEditableTableModel) tablaTerminales.getModel()).addRow(new Object[]{
                    nuevoTerminal.getId(),
                    (nuevoTerminal instanceof TerminalPasajeros) ? "Pasajeros" : "Mercancias",
                    nuevoTerminal.getPuertas().size()
                });
            } else {
                // Solo aggiorna numero di porte visivamente
                terminalSeleccionado[0].setNumeroPuertas(numeroPuertas);
                int fila = tablaTerminales.getSelectedRow();
                tablaTerminales.setValueAt(terminalSeleccionado[0].getPuertas().size(), fila, 2);
            }

            terminalSeleccionado[0] = null;
            cardLayout.show(panelTerminales, "listaTerminales");
        });
    }
    
    /**
     * Inicializa el panel de gestión de puertas, mostrando una tabla con todas las puertas
     * disponibles en los terminales del aeropuerto y permitiendo su eliminación.
     *
     * Las puertas se listan con su código y estado actual ("Ocupada" o "Libre").
     * Solo es posible eliminar puertas que no estén en uso. La eliminación se refleja 
     * tanto visualmente en la tabla como en la estructura de datos interna del terminal correspondiente.
     *
     * Funcionalidades disponibles:
     * <ul>
     *   <li>Visualizar todas las puertas agrupadas por terminal.</li>
     *   <li>Eliminar una puerta si está libre.</li>
     * </ul>
     *
     * Los datos se obtienen dinámicamente desde el modelo {@code SkyManager} y sus terminales registrados.
     *
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelPuertas() {
        JPanel panelLista = new JPanel(new BorderLayout());

        JTable tablaPuertas = new JTable(new NonEditableTableModel(new Object[]{"Código", "Estado"}, 0));
        personalizarTabla(tablaPuertas);
        JScrollPane scrollPane = new JScrollPane(tablaPuertas);

        JPanel barraBotones = new JPanel();
        barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonEliminar = new JButton("Eliminar");

        personalizarBoton(botonEliminar);

        barraBotones.add(botonEliminar);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        JPanel panelPuertas = vista.getPanelPuertas();
        panelPuertas.add(panelLista, "listaPuertas");

        CardLayout cardLayout = (CardLayout) panelPuertas.getLayout();
        cardLayout.show(panelPuertas, "listaPuertas");

        // ACCESSO AL MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // Carichiamo tutte le puertas
        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaPuertas.getModel();
        for (Terminal t : modelo.getTerminales().values()) {
            for (Puerta p : t.getPuertas().values()) {
                modeloTabla.addRow(new Object[]{
                    p.getCod(),
                    p.enUso() ? "Ocupada" : "Libre"
                });
            }
        }

        // EVENTO
        botonEliminar.addActionListener(_ -> {
            int fila = tablaPuertas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una puerta para eliminar.");
                return;
            }

            String codPuerta = (String) tablaPuertas.getValueAt(fila, 0);

            // Cerca la puerta e il terminal che la contiene
            Terminal terminalEncontrado = null;
            Puerta puertaEncontrada = null;

            for (Terminal t : modelo.getTerminales().values()) {
                Puerta p = t.getPuertas().get(codPuerta);
                if (p != null) {
                    terminalEncontrado = t;
                    puertaEncontrada = p;
                    break;
                }
            }

            if (puertaEncontrada == null || terminalEncontrado == null) {
                JOptionPane.showMessageDialog(null, "Error: puerta no encontrada.");
                return;
            }

            if (puertaEncontrada.enUso()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: la puerta está ocupada.");
                return;
            }

            // Elimina
            terminalEncontrado.getPuertas().remove(codPuerta);
            ((NonEditableTableModel) tablaPuertas.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Puerta eliminada correctamente.");
        });
    }
    
    /**
     * Inicializa el panel de gestión de fingers en la interfaz gráfica.
     *
     * Este método configura dos vistas: una lista de fingers existentes en una tabla
     * y un formulario para añadir o modificar un finger. Permite al usuario:
     * <ul>
     *   <li>Visualizar la lista de fingers con su altura máxima y estado (ocupado o libre)</li>
     *   <li>Añadir un nuevo finger especificando su altura máxima</li>
     *   <li>Modificar la altura de un finger existente</li>
     *   <li>Eliminar un finger, siempre que no esté en uso</li>
     * </ul>
     *
     * Todos los cambios se reflejan en el modelo {@link SkyManager} y se actualiza
     * la tabla en consecuencia. Se utiliza un {@link CardLayout} para cambiar entre
     * la vista de lista y el formulario de edición.
     */

    private void inicializarPanelFingers() {
        JPanel panelLista = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());

        JTable tablaFingers = new JTable(new NonEditableTableModel(new Object[]{"ID", "Altura Máxima", "Estado"}, 0));
        personalizarTabla(tablaFingers);
        JScrollPane scrollPane = new JScrollPane(tablaFingers);

        JPanel barraBotones = new JPanel();
        barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonAñadir = new JButton("Añadir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        personalizarBoton(botonAñadir);
        personalizarBoton(botonModificar);
        personalizarBoton(botonEliminar);

        barraBotones.add(botonAñadir);
        barraBotones.add(botonModificar);
        barraBotones.add(botonEliminar);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        // Formulario
        JLabel labelAlturaMaxima = new JLabel("Altura Máxima (m):");
        JTextField campoAlturaMaxima = new JTextField(10);

        JButton botonGuardar = new JButton("Guardar");
        personalizarBoton(botonGuardar);
        JButton botonCancelar = new JButton("Cancelar");
        personalizarBoton(botonCancelar);

        personalizarBoton(botonGuardar);
        personalizarBoton(botonCancelar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelAlturaMaxima, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; panelFormulario.add(campoAlturaMaxima, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        JPanel panelFingers = vista.getPanelFingers();
        panelFingers.add(panelLista, "listaFingers");
        panelFingers.add(panelFormulario, "formularioFingers");

        CardLayout cardLayout = (CardLayout) panelFingers.getLayout();
        cardLayout.show(panelFingers, "listaFingers");

        // ACCESSO MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // Caricare fingers esistenti
        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaFingers.getModel();
        for (Finger f : modelo.getFingers().values()) {
            modeloTabla.addRow(new Object[]{
                f.getId(),
                f.getAlturaMax(),
                f.enUso() ? "Ocupado" : "Libre"
            });
        }

        final Finger[] fingerSeleccionado = {null};

        // EVENTI
        botonAñadir.addActionListener(_ -> {
            campoAlturaMaxima.setText("");
            fingerSeleccionado[0] = null;
            cardLayout.show(panelFingers, "formularioFingers");
        });

        botonModificar.addActionListener(_ -> {
            int fila = tablaFingers.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un finger para modificar.");
                return;
            }
            String id = (String) tablaFingers.getValueAt(fila, 0);
            fingerSeleccionado[0] = modelo.getFingers().get(id);
            if (fingerSeleccionado[0] != null) {
                campoAlturaMaxima.setText(String.valueOf(fingerSeleccionado[0].getAlturaMax()));
                cardLayout.show(panelFingers, "formularioFingers");
            }
        });

        botonEliminar.addActionListener(_ -> {
            int fila = tablaFingers.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un finger para eliminar.");
                return;
            }

            String id = (String) tablaFingers.getValueAt(fila, 0);
            Finger finger = modelo.getFingers().get(id);

            if (finger != null && finger.enUso()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: el finger está ocupado.");
                return;
            }

            modelo.getFingers().remove(id);
            ((NonEditableTableModel) tablaFingers.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Finger eliminado correctamente.");
        });

        botonCancelar.addActionListener(_ -> {
            cardLayout.show(panelFingers, "listaFingers");
        });

        botonGuardar.addActionListener(_ -> {
            String alturaStr = campoAlturaMaxima.getText().trim();
            if (alturaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese la altura máxima.");
                return;
            }

            double alturaMax;
            try {
                alturaMax = Double.parseDouble(alturaStr);
                if (alturaMax <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La altura debe ser un número positivo.");
                return;
            }

            if (fingerSeleccionado[0] == null) {
                // NUEVO FINGER
                Finger nuevoFinger = new Finger(alturaMax);
                modelo.getFingers().put(nuevoFinger.getId(), nuevoFinger);
                ((NonEditableTableModel) tablaFingers.getModel()).addRow(new Object[]{
                    nuevoFinger.getId(),
                    nuevoFinger.getAlturaMax(),
                    "Libre"
                });
            } else {
                // MODIFICARE FINGER ESISTENTE
                fingerSeleccionado[0].setAlturaMax(alturaMax);
                int fila = tablaFingers.getSelectedRow();
                tablaFingers.setValueAt(fingerSeleccionado[0].getAlturaMax(), fila, 1);
            }

            fingerSeleccionado[0] = null;
            cardLayout.show(panelFingers, "listaFingers");
        });
    }

    /**
     * Inicializa el panel de gestión de Hangares,
     * permitiendo visualizar, añadir, modificar y eliminar hangares existentes.
     * 
     * Cada finger se muestra con su ID, altura máxima permitida y su estado actual 
     * ("Libre" o "Ocupado"). El formulario asociado permite definir o actualizar
     * la altura máxima del finger.
     * 
     * Funcionalidades:
     * <ul>
     *   <li>Visualizar todos los hangares registrados en el sistema.</li>
     *   <li>Añadir un nuevo hangar especificando su altura máxima.</li>
     *   <li>Modificar la altura máxima de un hangar existente.</li>
     *   <li>Eliminar un hangar siempre que no esté ocupado.</li>
     * </ul>
     * 
     * Los datos se gestionan a través del modelo {@code SkyManager}, que centraliza
     * la información de infraestructura del aeropuerto.
     * 
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelHangar() {
        JPanel panelLista = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());

        JTable tablaHangars = new JTable(new NonEditableTableModel(new Object[]{"ID", "Tipo", "Plazas", "Dimensiones", "Materiales Peligrosos"}, 0));
        personalizarTabla(tablaHangars);
        JScrollPane scrollPane = new JScrollPane(tablaHangars);

        JPanel barraBotones = new JPanel();
        barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonAñadir = new JButton("Añadir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        personalizarBoton(botonAñadir);
        personalizarBoton(botonModificar);
        personalizarBoton(botonEliminar);

        barraBotones.add(botonAñadir);
        barraBotones.add(botonModificar);
        barraBotones.add(botonEliminar);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        // Formulario
        JLabel labelTipo = new JLabel("Tipo:");
        JRadioButton rdbtnPasajeros = new JRadioButton("Pasajeros", true);
        JRadioButton rdbtnMercancias = new JRadioButton("Mercancías");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbtnPasajeros);
        grupoTipo.add(rdbtnMercancias);

        JLabel labelNumPlazas = new JLabel("Número de Plazas:");
        JTextField campoNumPlazas = new JTextField(10);

        JLabel labelAltura = new JLabel("Altura Plaza (m):");
        JTextField campoAltura = new JTextField(10);

        JLabel labelAnchura = new JLabel("Anchura Plaza (m):");
        JTextField campoAnchura = new JTextField(10);

        JLabel labelLargo = new JLabel("Largo Plaza (m):");
        JTextField campoLargo = new JTextField(10);

        JCheckBox chkMaterialesPeligrosos = new JCheckBox("Permitir Materiales Peligrosos");

        JButton botonGuardar = new JButton("Guardar");
        personalizarBoton(botonGuardar);
        JButton botonCancelar = new JButton("Cancelar");
        personalizarBoton(botonCancelar);

        personalizarBoton(botonGuardar);
        personalizarBoton(botonCancelar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelTipo, gbc);
        gbc.gridx = 1; panelFormulario.add(rdbtnPasajeros, gbc);
        gbc.gridx = 2; panelFormulario.add(rdbtnMercancias, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(labelNumPlazas, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNumPlazas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(labelAltura, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoAltura, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(labelAnchura, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoAnchura, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(labelLargo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoLargo, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; panelFormulario.add(chkMaterialesPeligrosos, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        JPanel panelHangar = vista.getPanelHangares();
        panelHangar.add(panelLista, "listaHangars");
        panelHangar.add(panelFormulario, "formularioHangars");

        CardLayout cardLayout = (CardLayout) panelHangar.getLayout();
        cardLayout.show(panelHangar, "listaHangars");

        // ACCESSO MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // Caricare hangars esistenti
        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaHangars.getModel();
        for (Hangar h : modelo.getHangares().values()) {
            modeloTabla.addRow(new Object[]{
                h.getId(),
                (h instanceof HangarPasajeros) ? "Pasajeros" : "Mercancías",
                h.getNumPlazas(),
                String.format("%.1fm × %.1fm × %.1fm", h.getAlturaPlaza(), h.getAnchuraPlaza(), h.getLargoPlaza()),
                (h instanceof HangarMercancias && ((HangarMercancias)h).isMaterialesPeligrosos()) ? "Sí" : "No"
            });
        }

        final Hangar[] hangarSeleccionado = {null};

        // EVENTI
        botonAñadir.addActionListener(_ -> {
            campoNumPlazas.setText("");
            campoAltura.setText("");
            campoAnchura.setText("");
            campoLargo.setText("");
            chkMaterialesPeligrosos.setSelected(false);
            rdbtnPasajeros.setSelected(true);
            hangarSeleccionado[0] = null;
            cardLayout.show(panelHangar, "formularioHangars");
        });

        botonModificar.addActionListener(_ -> {
            int fila = tablaHangars.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un hangar para modificar.");
                return;
            }
            String id = (String) tablaHangars.getValueAt(fila, 0);
            hangarSeleccionado[0] = modelo.getHangares().get(id);
            if (hangarSeleccionado[0] != null) {
                campoNumPlazas.setText(String.valueOf(hangarSeleccionado[0].getNumPlazas()));
                campoAltura.setText(String.valueOf(hangarSeleccionado[0].getAlturaPlaza()));
                campoAnchura.setText(String.valueOf(hangarSeleccionado[0].getAnchuraPlaza()));
                campoLargo.setText(String.valueOf(hangarSeleccionado[0].getLargoPlaza()));
                if (hangarSeleccionado[0] instanceof HangarMercancias) {
                    rdbtnMercancias.setSelected(true);
                    chkMaterialesPeligrosos.setSelected(((HangarMercancias) hangarSeleccionado[0]).isMaterialesPeligrosos());
                } else {
                    rdbtnPasajeros.setSelected(true);
                    chkMaterialesPeligrosos.setSelected(false);
                }
                cardLayout.show(panelHangar, "formularioHangars");
            }
        });

        botonEliminar.addActionListener(_ -> {
            int fila = tablaHangars.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un hangar para eliminar.");
                return;
            }
            String id = (String) tablaHangars.getValueAt(fila, 0);
            Hangar hangar = modelo.getHangares().get(id);

            if (!hangar.getAviones().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: el hangar tiene aviones asignados.");
                return;
            }

            modelo.getHangares().remove(id);
            ((NonEditableTableModel) tablaHangars.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Hangar eliminado correctamente.");
        });

        botonCancelar.addActionListener(_ -> {
            cardLayout.show(panelHangar, "listaHangars");
        });

        botonGuardar.addActionListener(_ -> {
            String numPlazasStr = campoNumPlazas.getText().trim();
            String alturaStr = campoAltura.getText().trim();
            String anchuraStr = campoAnchura.getText().trim();
            String largoStr = campoLargo.getText().trim();

            if (numPlazasStr.isEmpty() || alturaStr.isEmpty() || anchuraStr.isEmpty() || largoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                return;
            }

            int numPlazas;
            double altura, anchura, largo;
            try {
                numPlazas = Integer.parseInt(numPlazasStr);
                altura = Double.parseDouble(alturaStr);
                anchura = Double.parseDouble(anchuraStr);
                largo = Double.parseDouble(largoStr);
                if (numPlazas <= 0 || altura <= 0 || anchura <= 0 || largo <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Todos los valores deben ser números positivos.");
                return;
            }

            boolean materialesPeligrosos = chkMaterialesPeligrosos.isSelected();

            if (hangarSeleccionado[0] == null) {
                // NUEVO
                Hangar nuevoHangar;
                if (rdbtnPasajeros.isSelected()) {
                    nuevoHangar = new HangarPasajeros(LocalDate.now(), numPlazas, altura, anchura, largo);
                } else {
                    nuevoHangar = new HangarMercancias(LocalDate.now(), numPlazas, altura, anchura, largo, materialesPeligrosos);
                }
                modelo.getHangares().put(nuevoHangar.getId(), nuevoHangar);
                modeloTabla.addRow(new Object[]{
                    nuevoHangar.getId(),
                    (nuevoHangar instanceof HangarPasajeros) ? "Pasajeros" : "Mercancías",
                    nuevoHangar.getNumPlazas(),
                    String.format("%.1fm × %.1fm × %.1fm", nuevoHangar.getAlturaPlaza(), nuevoHangar.getAnchuraPlaza(), nuevoHangar.getLargoPlaza()),
                    (nuevoHangar instanceof HangarMercancias && ((HangarMercancias)nuevoHangar).isMaterialesPeligrosos()) ? "Sí" : "No"
                });
            } else {
                // MODIFICARE
                hangarSeleccionado[0].setNumPlazas(numPlazas);
                hangarSeleccionado[0].setAlturaPlaza(altura);
                hangarSeleccionado[0].setAnchuraPlaza(anchura);
                hangarSeleccionado[0].setLargoPlaza(largo);
                if (hangarSeleccionado[0] instanceof HangarMercancias) {
                    ((HangarMercancias) hangarSeleccionado[0]).setMaterialesPeligrosos(materialesPeligrosos);
                }

                int fila = tablaHangars.getSelectedRow();
                tablaHangars.setValueAt(numPlazas, fila, 2);
                tablaHangars.setValueAt(String.format("%.1fm × %.1fm × %.1fm", altura, anchura, largo), fila, 3);
                tablaHangars.setValueAt(
                    (hangarSeleccionado[0] instanceof HangarMercancias && ((HangarMercancias)hangarSeleccionado[0]).isMaterialesPeligrosos()) ? "Sí" : "No",
                    fila, 4);
            }

            hangarSeleccionado[0] = null;
            cardLayout.show(panelHangar, "listaHangars");
        });
    }
    
    /**
     * Inicializa el panel de gestión de Zonas de Parking, permitiendo visualizar,
     * añadir, modificar y eliminar zonas donde los aviones pueden estacionarse.
     * 
     * Cada zona incluye información sobre el número de plazas disponibles, sus
     * dimensiones (altura, anchura, largo) y cuántas de esas plazas están ocupadas
     * actualmente.
     * 
     * Funcionalidades:
     * <ul>
     *   <li>Visualización de todas las zonas de parking existentes.</li>
     *   <li>Creación de una nueva zona especificando plazas y dimensiones.</li>
     *   <li>Modificación de los parámetros de una zona ya existente.</li>
     *   <li>Eliminación de zonas vacías (sin vuelos asignados).</li>
     * </ul>
     * 
     * La información se gestiona a través del modelo {@code SkyManager}, 
     * que centraliza todos los datos del aeropuerto.
     * 
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelZonaParking() {
        JPanel panelLista = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());

        JTable tablaZonaParking = new JTable(new NonEditableTableModel(new Object[]{"ID", "Plazas", "Dimensiones", "Plazas Ocupadas"}, 0));
        personalizarTabla(tablaZonaParking);
        JScrollPane scrollPane = new JScrollPane(tablaZonaParking);

        JPanel barraBotones = new JPanel();
        barraBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonAñadir = new JButton("Añadir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        personalizarBoton(botonAñadir);
        personalizarBoton(botonModificar);
        personalizarBoton(botonEliminar);

        barraBotones.add(botonAñadir);
        barraBotones.add(botonModificar);
        barraBotones.add(botonEliminar);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        // Formulario
        JLabel labelNumPlazas = new JLabel("Número de Plazas:");
        JTextField campoNumPlazas = new JTextField(10);

        JLabel labelAltura = new JLabel("Altura Plaza (m):");
        JTextField campoAltura = new JTextField(10);

        JLabel labelAnchura = new JLabel("Anchura Plaza (m):");
        JTextField campoAnchura = new JTextField(10);

        JLabel labelLargo = new JLabel("Largo Plaza (m):");
        JTextField campoLargo = new JTextField(10);

        JButton botonGuardar = new JButton("Guardar");
        personalizarBoton(botonGuardar);
        JButton botonCancelar = new JButton("Cancelar");
        personalizarBoton(botonCancelar);

        personalizarBoton(botonGuardar);
        personalizarBoton(botonCancelar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelNumPlazas, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNumPlazas, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(labelAltura, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoAltura, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(labelAnchura, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoAnchura, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(labelLargo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoLargo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        JPanel panelZonaParking = vista.getPanelZonasParking();
        panelZonaParking.add(panelLista, "listaZonaParking");
        panelZonaParking.add(panelFormulario, "formularioZonaParking");

        CardLayout cardLayout = (CardLayout) panelZonaParking.getLayout();
        cardLayout.show(panelZonaParking, "listaZonaParking");

        // ACCESSO MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // Caricare zona parking esistenti
        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaZonaParking.getModel();
        for (ZonaParking zp : modelo.getZonasParking().values()) {
            modeloTabla.addRow(new Object[]{
                zp.getId(),
                zp.getNumPlazas(),
                String.format("%.1fm × %.1fm × %.1fm", zp.getAlturaPlaza(), zp.getAnchuraPlaza(), zp.getLargoPlaza()),
                zp.numPlazasOcupadasPark()
            });
        }

        final ZonaParking[] zonaSeleccionada = {null};

        // EVENTI
        botonAñadir.addActionListener(_ -> {
            campoNumPlazas.setText("");
            campoAltura.setText("");
            campoAnchura.setText("");
            campoLargo.setText("");
            zonaSeleccionada[0] = null;
            cardLayout.show(panelZonaParking, "formularioZonaParking");
        });

        botonModificar.addActionListener(_ -> {
            int fila = tablaZonaParking.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una zona para modificar.");
                return;
            }
            String id = (String) tablaZonaParking.getValueAt(fila, 0);
            zonaSeleccionada[0] = modelo.getZonasParking().get(id);
            if (zonaSeleccionada[0] != null) {
                campoNumPlazas.setText(String.valueOf(zonaSeleccionada[0].getNumPlazas()));
                campoAltura.setText(String.valueOf(zonaSeleccionada[0].getAlturaPlaza()));
                campoAnchura.setText(String.valueOf(zonaSeleccionada[0].getAnchuraPlaza()));
                campoLargo.setText(String.valueOf(zonaSeleccionada[0].getLargoPlaza()));
                cardLayout.show(panelZonaParking, "formularioZonaParking");
            }
        });

        botonEliminar.addActionListener(_ -> {
            int fila = tablaZonaParking.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una zona para eliminar.");
                return;
            }
            String id = (String) tablaZonaParking.getValueAt(fila, 0);
            ZonaParking zona = modelo.getZonasParking().get(id);

            if (!zona.getVuelos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: la zona tiene vuelos asignados.");
                return;
            }

            modelo.getZonasParking().remove(id);
            ((NonEditableTableModel) tablaZonaParking.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Zona de parking eliminada correctamente.");
        });

        botonCancelar.addActionListener(_ -> {
            cardLayout.show(panelZonaParking, "listaZonaParking");
        });

        botonGuardar.addActionListener(_ -> {
            String numPlazasStr = campoNumPlazas.getText().trim();
            String alturaStr = campoAltura.getText().trim();
            String anchuraStr = campoAnchura.getText().trim();
            String largoStr = campoLargo.getText().trim();

            if (numPlazasStr.isEmpty() || alturaStr.isEmpty() || anchuraStr.isEmpty() || largoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                return;
            }

            int numPlazas;
            double altura, anchura, largo;
            try {
                numPlazas = Integer.parseInt(numPlazasStr);
                altura = Double.parseDouble(alturaStr);
                anchura = Double.parseDouble(anchuraStr);
                largo = Double.parseDouble(largoStr);
                if (numPlazas <= 0 || altura <= 0 || anchura <= 0 || largo <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Todos los valores deben ser números positivos.");
                return;
            }

            if (zonaSeleccionada[0] == null) {
                // NUEVA ZONA
                ZonaParking nuevaZona = new ZonaParking(numPlazas, altura, anchura, largo);
                modelo.getZonasParking().put(nuevaZona.getId(), nuevaZona);
                modeloTabla.addRow(new Object[]{
                    nuevaZona.getId(),
                    nuevaZona.getNumPlazas(),
                    String.format("%.1fm × %.1fm × %.1fm", nuevaZona.getAlturaPlaza(), nuevaZona.getAnchuraPlaza(), nuevaZona.getLargoPlaza()),
                    nuevaZona.numPlazasOcupadasPark()
                });
            } else {
                // MODIFICARE ESISTENTE
                zonaSeleccionada[0].setNumPlazas(numPlazas);
                zonaSeleccionada[0].setAlturaPlaza(altura);
                zonaSeleccionada[0].setAnchuraPlaza(anchura);
                zonaSeleccionada[0].setLargoPlaza(largo);

                int fila = tablaZonaParking.getSelectedRow();
                tablaZonaParking.setValueAt(numPlazas, fila, 1);
                tablaZonaParking.setValueAt(String.format("%.1fm × %.1fm × %.1fm", altura, anchura, largo), fila, 2);
            }

            zonaSeleccionada[0] = null;
            cardLayout.show(panelZonaParking, "listaZonaParking");
        });
    }
    
    /**
     * Inicializa el panel de gestión de Aeropuertos Externos, permitiendo visualizar,
     * añadir, modificar y eliminar aeropuertos con los que el aeropuerto principal
     * puede interactuar (vuelos de llegada/salida).
     * 
     * Cada aeropuerto incluye datos como código (3 letras), nombre, país, ciudad,
     * distancia a la ciudad más cercana, zona horaria (GMT) y dirección cardinal.
     * 
     * Funcionalidades:
     * <ul>
     *   <li>Visualización de aeropuertos externos registrados.</li>
     *   <li>Creación de nuevos aeropuertos con validación de código y campos obligatorios.</li>
     *   <li>Modificación de datos existentes (excepto el código, que es único).</li>
     *   <li>Eliminación de aeropuertos si no están vinculados a vuelos.</li>
     *   <li>Import de aeropuertos de fichero externo</li>
     * </ul>
     * 
     * Los datos se gestionan a través del modelo {@code SkyManager} que mantiene
     * una lista de aeropuertos externos.
     * 
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelAeropuertosExternos() {
        JPanel panelLista = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());

        JTable tablaAeropuertos = new JTable(new NonEditableTableModel(
                new Object[]{"Código", "Nombre", "País", "Ciudad", "Distancia (km)", "GMT", "Dirección"}, 0));
        personalizarTabla(tablaAeropuertos);
        JScrollPane scrollPane = new JScrollPane(tablaAeropuertos);

        JPanel barraBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonAñadir = new JButton("Añadir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonImport = new JButton("Importar...");

        personalizarBoton(botonAñadir);
        personalizarBoton(botonModificar);
        personalizarBoton(botonEliminar);
        personalizarBoton(botonImport);

        barraBotones.add(botonAñadir);
        barraBotones.add(botonModificar);
        barraBotones.add(botonEliminar);
        barraBotones.add(botonImport);

        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.add(barraBotones, BorderLayout.SOUTH);

        // Formulario
        JLabel labelCodigo = new JLabel("Código (3 letras):");
        JTextField campoCodigo = new JTextField(10);

        JLabel labelNombre = new JLabel("Nombre Aeropuerto:");
        JTextField campoNombre = new JTextField(20);

        JLabel labelPais = new JLabel("País:");
        JTextField campoPais = new JTextField(20);

        JLabel labelCiudad = new JLabel("Ciudad más cercana:");
        JTextField campoCiudad = new JTextField(20);

        JLabel labelDistancia = new JLabel("Distancia Ciudad (km):");
        JTextField campoDistancia = new JTextField(10);

        JLabel labelGMT = new JLabel("Diferencia Horaria (GMT):");
        JTextField campoGMT = new JTextField(5);

        JLabel labelDireccion = new JLabel("Dirección:");
        JComboBox<Direccion> comboDireccion = new JComboBox<>(Direccion.values());

        JButton botonGuardar = new JButton("Guardar");
        personalizarBoton(botonGuardar);
        JButton botonCancelar = new JButton("Cancelar");
        personalizarBoton(botonCancelar);

        personalizarBoton(botonGuardar);
        personalizarBoton(botonCancelar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(labelCodigo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(labelNombre, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(labelPais, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoPais, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(labelCiudad, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCiudad, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(labelDistancia, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoDistancia, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panelFormulario.add(labelGMT, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoGMT, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panelFormulario.add(labelDireccion, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(comboDireccion, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        JPanel panelAeropuertos = vista.getPanelAeropuertosExternos();
        panelAeropuertos.add(panelLista, "listaAeropuertos");
        panelAeropuertos.add(panelFormulario, "formularioAeropuertos");

        CardLayout cardLayout = (CardLayout) panelAeropuertos.getLayout();
        cardLayout.show(panelAeropuertos, "listaAeropuertos");

        // ACCESSO MODELLO
        SkyManager modelo = SkyManager.getInstance();

        NonEditableTableModel modeloTabla = (NonEditableTableModel) tablaAeropuertos.getModel();
        for (Aeropuerto a : modelo.getAeropuertosExternos().values()) {
            modeloTabla.addRow(new Object[]{
                a.getCodigo(),
                a.getNombre(),
                a.getPais(),
                a.getCiudadMasCercana(),
                a.getDistanciaCiudadMasCercana(),
                a.getDiferenciaHoraria(),
                (a.getDireccion() != null) ? a.getDireccion().toString() : ""
            });
        }

        final Aeropuerto[] aeropuertoSeleccionado = {null};

        // EVENTI
        botonImport.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar archivo .dat para importar");
            fileChooser.setAcceptAllFileFilterUsed(false); // Disattiva "Todos los archivos"
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos DAT (*.dat)", "dat"));

            int seleccion = fileChooser.showOpenDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
                String rutaArchivo = archivoSeleccionado.getAbsolutePath();

                if (!rutaArchivo.toLowerCase().endsWith(".dat")) {
                    JOptionPane.showMessageDialog(null, "El archivo debe tener extensión .dat", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Logica di importazione qui
                System.out.println("Archivo .dat seleccionado: " + rutaArchivo);
                JOptionPane.showMessageDialog(null, "Archivo seleccionado:\n" + rutaArchivo);

                SkyManager.getInstance().cargarDatosAeropuertos(rutaArchivo);
            }
        });
        
        botonAñadir.addActionListener(_ -> {
            campoCodigo.setText("");
            campoNombre.setText("");
            campoPais.setText("");
            campoCiudad.setText("");
            campoDistancia.setText("");
            campoGMT.setText("");
            comboDireccion.setSelectedIndex(0);
            campoCodigo.setEditable(true);
            aeropuertoSeleccionado[0] = null;
            cardLayout.show(panelAeropuertos, "formularioAeropuertos");
        });

        botonModificar.addActionListener(_ -> {
            int fila = tablaAeropuertos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un aeropuerto para modificar.");
                return;
            }
            String codigo = (String) tablaAeropuertos.getValueAt(fila, 0);
            aeropuertoSeleccionado[0] = modelo.getAeropuertosExternos().get(codigo);
            if (aeropuertoSeleccionado[0] != null) {
                campoCodigo.setText(aeropuertoSeleccionado[0].getCodigo());
                campoCodigo.setEditable(false);
                campoNombre.setText(aeropuertoSeleccionado[0].getNombre());
                campoPais.setText(aeropuertoSeleccionado[0].getPais());
                campoCiudad.setText(aeropuertoSeleccionado[0].getCiudadMasCercana());
                campoDistancia.setText(String.valueOf(aeropuertoSeleccionado[0].getDistanciaCiudadMasCercana()));
                campoGMT.setText(String.valueOf(aeropuertoSeleccionado[0].getDiferenciaHoraria()));
                if (aeropuertoSeleccionado[0].getDireccion() != null) {
                    comboDireccion.setSelectedItem(aeropuertoSeleccionado[0].getDireccion());
                } else {
                    comboDireccion.setSelectedIndex(0);
                }
                cardLayout.show(panelAeropuertos, "formularioAeropuertos");
            }
        });

        botonEliminar.addActionListener(_ -> {
            int fila = tablaAeropuertos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un aeropuerto para eliminar.");
                return;
            }
            String codigo = (String) tablaAeropuertos.getValueAt(fila, 0);
            Aeropuerto aeropuerto = modelo.getAeropuertosExternos().get(codigo);

            boolean usadoEnVuelos = modelo.getVuelos().values().stream()
                    .anyMatch(v -> v.getOrigen().equals(aeropuerto) || v.getDestino().equals(aeropuerto));

            if (usadoEnVuelos) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar: el aeropuerto está asociado a vuelos.");
                return;
            }

            modelo.getAeropuertosExternos().remove(codigo);
            ((NonEditableTableModel) tablaAeropuertos.getModel()).removeRow(fila);
            JOptionPane.showMessageDialog(null, "Aeropuerto eliminado correctamente.");
        });

        botonCancelar.addActionListener(_ -> {
            cardLayout.show(panelAeropuertos, "listaAeropuertos");
        });

        botonGuardar.addActionListener(_ -> {
            String codigo = campoCodigo.getText().trim().toUpperCase();
            String nombre = campoNombre.getText().trim();
            String pais = campoPais.getText().trim();
            String ciudad = campoCiudad.getText().trim();
            String distanciaStr = campoDistancia.getText().trim();
            String gmtStr = campoGMT.getText().trim();
            Direccion direccion = (Direccion) comboDireccion.getSelectedItem();

            if (codigo.isEmpty() || nombre.isEmpty() || pais.isEmpty() || ciudad.isEmpty() ||
                distanciaStr.isEmpty() || gmtStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
                return;
            }

            if (!codigo.matches("^[A-Z]{3}$")) {
                JOptionPane.showMessageDialog(null, "El código debe tener exactamente 3 letras mayúsculas.");
                return;
            }

            double distancia;
            int gmt;
            try {
                distancia = Double.parseDouble(distanciaStr);
                gmt = Integer.parseInt(gmtStr);
                if (distancia < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Distancia debe ser un número positivo y GMT un número entero.");
                return;
            }

            if (aeropuertoSeleccionado[0] == null && modelo.getAeropuertosExternos().containsKey(codigo)) {
                JOptionPane.showMessageDialog(null, "Ya existe un aeropuerto con este código.");
                return;
            }

            if (aeropuertoSeleccionado[0] == null) {
                Aeropuerto nuevoAeropuerto = new Aeropuerto(nombre, codigo, ciudad, pais, distancia, gmt, new ArrayList<>(), direccion);
                modelo.getAeropuertosExternos().put(codigo, nuevoAeropuerto);
                modeloTabla.addRow(new Object[]{
                    nuevoAeropuerto.getCodigo(),
                    nuevoAeropuerto.getNombre(),
                    nuevoAeropuerto.getPais(),
                    nuevoAeropuerto.getCiudadMasCercana(),
                    nuevoAeropuerto.getDistanciaCiudadMasCercana(),
                    nuevoAeropuerto.getDiferenciaHoraria(),
                    (direccion != null) ? direccion.toString() : ""
                });
            } else {
                aeropuertoSeleccionado[0].setNombre(nombre);
                aeropuertoSeleccionado[0].setPais(pais);
                aeropuertoSeleccionado[0].setCiudadMasCercana(ciudad);
                aeropuertoSeleccionado[0].setDistanciaCiudadMasCercana(distancia);
                aeropuertoSeleccionado[0].setDiferenciaHoraria(gmt);
                aeropuertoSeleccionado[0].setDireccion(direccion);

                int fila = tablaAeropuertos.getSelectedRow();
                tablaAeropuertos.setValueAt(nombre, fila, 1);
                tablaAeropuertos.setValueAt(pais, fila, 2);
                tablaAeropuertos.setValueAt(ciudad, fila, 3);
                tablaAeropuertos.setValueAt(distancia, fila, 4);
                tablaAeropuertos.setValueAt(gmt, fila, 5);
                tablaAeropuertos.setValueAt((direccion != null) ? direccion.toString() : "", fila, 6);
            }

            aeropuertoSeleccionado[0] = null;
            cardLayout.show(panelAeropuertos, "listaAeropuertos");
        });
    }
    
    /**
     * Inicializa el panel de configuración del Aeropuerto Propio, permitiendo
     * visualizar y modificar tanto sus datos generales como los costos asociados.
     * 
     * Este panel incluye tres secciones principales:
     * <ul>
     *   <li><b>Datos Generales:</b> código del aeropuerto (3 letras), nombre, país, ciudad cercana,
     *       distancia a la ciudad, diferencia horaria (GMT) y dirección cardinal.</li>
     *   <li><b>Costos Base:</b> costo base por factura.</li>
     *   <li><b>Costos por Hora:</b> tarifas horarias para pista, terminal, finger, hangar y autobús.</li>
     * </ul>
     * 
     * Los campos se rellenan automáticamente a partir de los datos cargados en el modelo
     * {@code SkyManager}. Al pulsar el botón "Guardar Cambios", los datos se validan y
     * se actualizan en el modelo correspondiente.
     * 
     * Este panel no permite crear ni eliminar aeropuertos, ya que representa al aeropuerto principal
     * del sistema.
     * 
     * @author Christian Grosso - christian.grosso@estudiante.uam.es
     */
    private void inicializarPanelAeropuertoPropio() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sección: Datos Generales
        JLabel labelDatos = new JLabel("Datos Generales");
        labelDatos.setFont(labelDatos.getFont().deriveFont(Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(labelDatos, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Código (3 letras):"), gbc);
        JTextField campoCodigo = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Nombre Aeropuerto:"), gbc);
        JTextField campoNombre = new JTextField(20);
        gbc.gridx = 1; panel.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("País:"), gbc);
        JTextField campoPais = new JTextField(20);
        gbc.gridx = 1; panel.add(campoPais, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Ciudad más cercana:"), gbc);
        JTextField campoCiudad = new JTextField(20);
        gbc.gridx = 1; panel.add(campoCiudad, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Distancia Ciudad (km):"), gbc);
        JTextField campoDistancia = new JTextField(10);
        gbc.gridx = 1; panel.add(campoDistancia, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Diferencia Horaria (GMT):"), gbc);
        JTextField campoGMT = new JTextField(5);
        gbc.gridx = 1; panel.add(campoGMT, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel("Dirección:"), gbc);
        JComboBox<Direccion> comboDireccion = new JComboBox<>(Direccion.values());
        gbc.gridx = 1; panel.add(comboDireccion, gbc);

        // Sección: Costos Base
        JLabel labelCostosBase = new JLabel("Costos Base");
        labelCostosBase.setFont(labelCostosBase.getFont().deriveFont(Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panel.add(labelCostosBase, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 9; panel.add(new JLabel("Costo Base Factura:"), gbc);
        JTextField campoCostoBase = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoBase, gbc);

        // Sección: Costos por Hora
        JLabel labelCostosHora = new JLabel("Costos por Hora");
        labelCostosHora.setFont(labelCostosHora.getFont().deriveFont(Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        panel.add(labelCostosHora, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 11; panel.add(new JLabel("Costo Hora Pista:"), gbc);
        JTextField campoCostoPista = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoPista, gbc);

        gbc.gridx = 0; gbc.gridy = 12; panel.add(new JLabel("Costo Hora Terminal:"), gbc);
        JTextField campoCostoTerminal = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoTerminal, gbc);

        gbc.gridx = 0; gbc.gridy = 13; panel.add(new JLabel("Costo Hora Finger:"), gbc);
        JTextField campoCostoFinger = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoFinger, gbc);

        gbc.gridx = 0; gbc.gridy = 14; panel.add(new JLabel("Costo Hora Hangar:"), gbc);
        JTextField campoCostoHangar = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoHangar, gbc);

        gbc.gridx = 0; gbc.gridy = 15; panel.add(new JLabel("Costo Hora Autobús:"), gbc);
        JTextField campoCostoAutobus = new JTextField(10);
        gbc.gridx = 1; panel.add(campoCostoAutobus, gbc);

        // Botón Guardar Cambios
        JButton botonGuardar = new JButton("Guardar Cambios");
        personalizarBoton(botonGuardar);
        personalizarBoton(botonGuardar);
        gbc.gridx = 0; gbc.gridy = 16; gbc.gridwidth = 2;
        panel.add(botonGuardar, gbc);

        // ACCESO AL MODELLO
        SkyManager modelo = SkyManager.getInstance();

        // CARGAR DATOS INICIALI
        cargarDatosAeroPropio(modelo, campoCodigo, campoNombre, campoPais, campoCiudad,
                              campoDistancia, campoGMT, comboDireccion,
                              campoCostoBase, campoCostoPista, campoCostoTerminal,
                              campoCostoFinger, campoCostoHangar, campoCostoAutobus);

        // ACCIONES
        botonGuardar.addActionListener(_ -> {
            guardarDatosAeroPropio(modelo, campoCodigo,campoNombre, campoPais, campoCiudad,
                                   campoDistancia, campoGMT, comboDireccion,
                                   campoCostoBase, campoCostoPista, campoCostoTerminal,
                                   campoCostoFinger, campoCostoHangar, campoCostoAutobus);
        });

        // AGGIUNGIAMO AL PANNELLO
        vista.getPanelAeropuertoPropio().add(panel, "panelDatosAeropuertoPropio");
        ((CardLayout) vista.getPanelAeropuertoPropio().getLayout()).show(vista.getPanelAeropuertoPropio(), "panelDatosAeropuertoPropio");
    }
    
    /**
     * Carga los datos del aeropuerto propio y de los costos base y horarios
     * desde el modelo {@code SkyManager} en los campos del formulario correspondiente.
     * 
     * Esta función se utiliza para inicializar los valores mostrados en el panel
     * del aeropuerto propio, incluyendo tanto la información general del aeropuerto
     * como las tarifas de uso de recursos.
     * 
     * Si el aeropuerto propio está definido en el modelo, se cargan sus atributos:
     * código, nombre, país, ciudad más cercana, distancia a la ciudad, GMT y dirección.
     * Además, se cargan los costos base y por hora definidos en el modelo para pista,
     * terminal, finger, hangar y autobús.
     * 
     * @param modelo             Instancia del modelo {@code SkyManager}.
     * @param campoCodigo        Campo de texto para el código del aeropuerto.
     * @param campoNombre        Campo de texto para el nombre del aeropuerto.
     * @param campoPais          Campo de texto para el país.
     * @param campoCiudad        Campo de texto para la ciudad más cercana.
     * @param campoDistancia     Campo de texto para la distancia a la ciudad (en km).
     * @param campoGMT           Campo de texto para la diferencia horaria (GMT).
     * @param comboDireccion     ComboBox para seleccionar la dirección cardinal.
     * @param campoCostoBase     Campo de texto para el costo base de la factura.
     * @param campoCostoPista    Campo de texto para el costo por hora de pista.
     * @param campoCostoTerminal Campo de texto para el costo por hora de terminal.
     * @param campoCostoFinger   Campo de texto para el costo por hora de finger.
     * @param campoCostoHangar   Campo de texto para el costo por hora de hangar.
     * @param campoCostoAutobus  Campo de texto para el costo por hora de autobús.
     */
    private void cargarDatosAeroPropio(SkyManager modelo,
    		JTextField campoCodigo, JTextField campoNombre, JTextField campoPais, JTextField campoCiudad,
            JTextField campoDistancia, JTextField campoGMT, JComboBox<Direccion> comboDireccion,
            JTextField campoCostoBase, JTextField campoCostoPista, JTextField campoCostoTerminal,
            JTextField campoCostoFinger, JTextField campoCostoHangar, JTextField campoCostoAutobus) {
			Aeropuerto aeropuerto = SkyManager.getInstance().getAeropuertoPropio();
			
			if (aeropuerto != null) {
			campoCodigo.setText(aeropuerto.getCodigo());
			campoNombre.setText(aeropuerto.getNombre());
			campoPais.setText(aeropuerto.getPais());
			campoCiudad.setText(aeropuerto.getCiudadMasCercana());
			campoDistancia.setText(String.valueOf(aeropuerto.getDistanciaCiudadMasCercana()));
			campoGMT.setText(String.valueOf(aeropuerto.getDiferenciaHoraria()));
			if (aeropuerto.getDireccion() != null) {
			comboDireccion.setSelectedItem(aeropuerto.getDireccion());
			} else {
			comboDireccion.setSelectedIndex(0);
			}
			}
			
			campoCostoBase.setText(""+modelo.getCostoBaseFactura());
			campoCostoPista.setText(""+modelo.getCostoHoraPista());
			campoCostoTerminal.setText(""+modelo.getCostoHoraTerminal());
			campoCostoFinger.setText(""+modelo.getCostoHoraFinger());
			campoCostoHangar.setText(""+modelo.getCostoHoraHangar());
			campoCostoAutobus.setText(""+modelo.getCostoHoraAutobus());
			
	}
	
    /**
     * Guarda los datos modificados del aeropuerto propio y los costos definidos por el usuario
     * en el modelo {@code SkyManager}.
     * 
     * Esta función actualiza tanto los atributos del aeropuerto propio (si existe) como
     * los costos base y por hora definidos en el sistema, los cuales son aplicados a todos
     * los recursos relevantes (pistas, terminales, fingers, hangares).
     * 
     * Al finalizar, muestra un mensaje de confirmación y guarda los datos en persistencia.
     *
     * @param modelo             Instancia del modelo {@code SkyManager}.
     * @param campoCodigo        Campo de texto con el código del aeropuerto.
     * @param campoNombre        Campo de texto con el nombre del aeropuerto.
     * @param campoPais          Campo de texto con el país del aeropuerto.
     * @param campoCiudad        Campo de texto con la ciudad más cercana.
     * @param campoDistancia     Campo de texto con la distancia a la ciudad (en km).
     * @param campoGMT           Campo de texto con la diferencia horaria (GMT).
     * @param comboDireccion     ComboBox para seleccionar la dirección cardinal.
     * @param campoCostoBase     Campo de texto con el costo base de la factura.
     * @param campoCostoPista    Campo de texto con el costo por hora de uso de pista.
     * @param campoCostoTerminal Campo de texto con el costo por hora de uso de terminal.
     * @param campoCostoFinger   Campo de texto con el costo por hora de uso de finger.
     * @param campoCostoHangar   Campo de texto con el costo por hora de uso de hangar.
     * @param campoCostoAutobus  Campo de texto con el costo por hora de uso de autobús.
     */
    private void guardarDatosAeroPropio(SkyManager modelo, JTextField campoCodigo, JTextField campoNombre, JTextField campoPais, JTextField campoCiudad,
	    JTextField campoDistancia, JTextField campoGMT, JComboBox<Direccion> comboDireccion,
	    JTextField campoCostoBase, JTextField campoCostoPista, JTextField campoCostoTerminal,
	    JTextField campoCostoFinger, JTextField campoCostoHangar, JTextField campoCostoAutobus) {
	
		// Salvataggio Aeroporto
		Aeropuerto aeropuerto = SkyManager.getInstance().getAeropuertoPropio();
	
		if (aeropuerto != null) {
			aeropuerto.setNombre(campoCodigo.getText().trim());
		aeropuerto.setNombre(campoNombre.getText().trim());
		aeropuerto.setPais(campoPais.getText().trim());
		aeropuerto.setCiudadMasCercana(campoCiudad.getText().trim());
		aeropuerto.setDistanciaCiudadMasCercana(Double.parseDouble(campoDistancia.getText().trim()));
		aeropuerto.setDiferenciaHoraria(Integer.parseInt(campoGMT.getText().trim()));
		aeropuerto.setDireccion((Direccion) comboDireccion.getSelectedItem());
		}
	
		// Salvataggio Costi
		modelo.setCostoBaseFactura(Double.parseDouble(campoCostoBase.getText().trim()));
		modelo.setCostoHoraPista(Double.parseDouble(campoCostoPista.getText().trim()));
		modelo.setCostoHoraTerminal(Double.parseDouble(campoCostoTerminal.getText().trim()));
		modelo.setCostoHoraFinger(Double.parseDouble(campoCostoFinger.getText().trim()));
		modelo.setCostoHoraHangar(Double.parseDouble(campoCostoHangar.getText().trim()));
		modelo.setCostoHoraAutobus(Double.parseDouble(campoCostoAutobus.getText().trim()));
		
		// Aggiorniamo i costi orari negli oggetti esistenti
		modelo.actualizarCosteHoraPistas(modelo.getCostoHoraPista());
		modelo.actualizarCosteHoraTerminales(modelo.getCostoHoraTerminal());
		modelo.actualizarCosteHoraFingers(modelo.getCostoHoraFinger());
		modelo.actualizarCosteHoraHangares(modelo.getCostoHoraHangar());
		
		JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
		modelo.guardarDatos();
    }
    
    /**
     * Muestra la pestaña de edición del aeropuerto propio en la interfaz gráfica y
     * carga en ella los datos actuales almacenados en el modelo {@code SkyManager}.
     *
     * Este método obtiene los datos del aeropuerto propio y los costos asociados
     * desde el modelo, rellenando los campos del formulario correspondiente.
     * Luego, cambia la vista al panel de datos del aeropuerto propio utilizando un {@code CardLayout}.
     */

    public void mostrarTabAeropuertoPropio() {
        SkyManager modelo = SkyManager.getInstance();

        cargarDatosAeroPropio(modelo,
                              vista.getCampoCodigoAeropuerto(),
                              vista.getCampoNombreAeropuerto(),
                              vista.getCampoPaisAeropuerto(),
                              vista.getCampoCiudadAeropuerto(),
                              vista.getCampoDistanciaAeropuerto(),
                              vista.getCampoGMTAeropuerto(),
                              vista.getComboDireccionAeropuerto(),
                              vista.getCampoCostoBaseFactura(),
                              vista.getCampoCostoHoraPista(),
                              vista.getCampoCostoHoraTerminal(),
                              vista.getCampoCostoHoraFinger(),
                              vista.getCampoCostoHoraHangar(),
                              vista.getCampoCostoHoraAutobus());
        ((CardLayout) vista.getPanelAeropuertoPropio().getLayout())
                .show(vista.getPanelAeropuertoPropio(), "panelDatosAeropuertoPropio");

    }

    /**
     * Aplica un estilo visual uniforme a un botón de la interfaz gráfica.
     *
     * Este método define el color de fondo, el color del texto, la fuente y las dimensiones
     * del botón, asegurando una apariencia coherente con el diseño general del sistema,
     * especialmente alineada con el estilo de la sección de "Gestión de Usuarios".
     *
     * @param boton el botón al que se aplicará la personalización de estilo
     */
    private void personalizarBoton(JButton boton) {
        boton.setBackground(new java.awt.Color(135, 206, 250)); // Azzurrino chiaro (stile GestionUsuarios)
        boton.setForeground(java.awt.Color.BLACK);               // Testo nero
        boton.setFocusPainted(false);                             // Nessun bordo blu di focus
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Font coerente con il resto
        boton.setPreferredSize(new java.awt.Dimension(120, 40));           // Dimensioni uniformi
    }
    
    /**
     * Aplica un estilo visual personalizado a una tabla para mejorar su apariencia y legibilidad.
     *
     * Este método configura el fondo, el color del texto, la fuente, la altura de las filas,
     * el color de las líneas de la cuadrícula y el estilo del encabezado de la tabla.
     * Se utiliza para mantener una presentación coherente en toda la interfaz de usuario.
     *
     * @param tabla la JTable a la que se aplicará la personalización de estilo
     */
    private void personalizarTabla(JTable tabla) {
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setRowHeight(25);
        tabla.setGridColor(new Color(75, 135, 185));
        tabla.getTableHeader().setBackground(new Color(70, 130, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
    }
}
