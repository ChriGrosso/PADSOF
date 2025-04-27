package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import interfaz.Aplicacion;
import interfaz.elementosComunes.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;

public class NuevoVuelo extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton registrarVuelo;
	JComboBox<String> mat = new JComboBox<>();
	private JTextField aeS, origen, destino, carga, numP;
	SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
	SpinnerDateModel model2 = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
	JSpinner salida = new JSpinner(model);
	JSpinner llegada = new JSpinner(model2);
	private JRadioButton mercancias, pasajeros, mercPeligrosas, mercNoPeligrosas, comp, noComp, llega, sale;
	ButtonGroup tipoAvion, tipoMerc, compartido, esLlegada;
    String[] opcionesPeriodo = {"No Periódico", "Diario", "Días Alternos"};
    JComboBox<String> periodicidad = new JComboBox<String>(opcionesPeriodo);
    JCheckBox lunes = new JCheckBox("Lunes"); 
    JCheckBox martes = new JCheckBox("Martes"); 
    JCheckBox miercoles = new JCheckBox("Miercoles"); 
    JCheckBox jueves = new JCheckBox("Jueves");
    JCheckBox viernes = new JCheckBox("Viernes"); 
    JCheckBox sabado = new JCheckBox("Sábado");
    JCheckBox domingo = new JCheckBox("Domingo");
    JPanel panelCheckbox = new JPanel(new GridLayout(4,2));
    JLabel etiquetaAeS = new JLabel("Nombre Aerolínea Secundaria:");
    JLabel etiquetaCarga = new JLabel("Carga [Tn]:");
    JLabel etiquetaPasajeros = new JLabel("Número de pasajeros:");
	
	public NuevoVuelo() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 40, 60));

        // Contenedor en la esquina superior derecha
        BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras_icon.png");
        panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

        // Añadir el contenedor al panel principal
        add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		
		JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));
        panelContenido.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        JLabel etiquetaMatricula = new JLabel("Matrícula del avión:");
        etiquetaMatricula.setForeground(Color.BLACK);
        JLabel etiquetaOrigen = new JLabel("Nombre Aeropuerto de Origen:");
        etiquetaOrigen.setForeground(Color.BLACK);
        JLabel etiquetaDestino = new JLabel("Nombre Aeropuerto de Destino:");
        etiquetaDestino.setForeground(Color.BLACK);
        JLabel etiquetaSalida = new JLabel("Fecha y hora de salida:");
        etiquetaSalida.setForeground(Color.BLACK);
        JLabel etiquetaLlegada = new JLabel("Fecha y hora de llegada:");
        etiquetaLlegada.setForeground(Color.BLACK);
        JLabel etiquetaPeriodo = new JLabel("Periodicidad del vuelo:");
        etiquetaPeriodo.setForeground(Color.BLACK);
        origen = new JTextField(15);
        destino = new JTextField(15);
        aeS = new JTextField(15);
        carga = new JTextField(15);
        numP = new JTextField(15);
        // Mostrar la fecha Y la hora
        JSpinner.DateEditor editor = new JSpinner.DateEditor(salida, "dd/MM/yyyy HH:mm");
        salida.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(llegada, "dd/MM/yyyy HH:mm");
        llegada.setEditor(editor2);
        mercancias = new JRadioButton("Mercancías");
        mercancias.setBackground(new Color(173, 216, 230));
        pasajeros = new JRadioButton("Pasajeros");
        pasajeros.setBackground(new Color(173, 216, 230));
        mercPeligrosas = new JRadioButton("Lleva mercancías peligrosas");
        mercPeligrosas.setBackground(new Color(173, 216, 230));
        mercNoPeligrosas = new JRadioButton("No lleva mercancías peligrosas");
        mercNoPeligrosas.setBackground(new Color(173, 216, 230));
        comp = new JRadioButton("Vuelo Compartido");
        comp.setBackground(new Color(173, 216, 230));
        noComp = new JRadioButton("Vuelo No Compartido");
        noComp.setBackground(new Color(173, 216, 230));
        llega = new JRadioButton("Vuelo de Llegada");
        llega.setBackground(new Color(173, 216, 230));
        sale = new JRadioButton("Vuelo de Salida");
        sale.setBackground(new Color(173, 216, 230));
        // Fusionar ambas opciones de tipo de avión y de tipo de mercancias
        tipoAvion = new ButtonGroup();
        tipoAvion.add(mercancias);
        tipoAvion.add(pasajeros);
        tipoMerc = new ButtonGroup();
        tipoMerc.add(mercPeligrosas);
        tipoMerc.add(mercNoPeligrosas);
        compartido = new ButtonGroup();
        compartido.add(comp);
        compartido.add(noComp);
        esLlegada = new ButtonGroup();
        esLlegada.add(llega);
        esLlegada.add(sale);
        // Panel con todas las opciones para para vuelos alternos 
        panelCheckbox.add(new JLabel("Selecciona los días que operará el vuelo:")); 
        panelCheckbox.add(lunes); 
        panelCheckbox.add(martes); 
        panelCheckbox.add(miercoles); 
        panelCheckbox.add(jueves);
        panelCheckbox.add(viernes); 
        panelCheckbox.add(sabado); 
        panelCheckbox.add(domingo); 
        panelCheckbox.setBackground(new Color(173, 216, 230));
        lunes.setBackground(new Color(173, 216, 230));
        martes.setBackground(new Color(173, 216, 230));
        miercoles.setBackground(new Color(173, 216, 230));
        jueves.setBackground(new Color(173, 216, 230));
        viernes.setBackground(new Color(173, 216, 230));
        sabado.setBackground(new Color(173, 216, 230));
        domingo.setBackground(new Color(173, 216, 230));
        // Panel periodicidad
        periodicidad.setSelectedIndex(-1); // Sin ninguna opción seleccionada por defecto
        registrarVuelo = createContentButton("Registrar Vuelo");
        
        // Ubicar los elementos
        panelContenido.add(etiquetaMatricula, gbc);
        
        gbc.gridx++;
        panelContenido.add(mat, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(llega, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(sale, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaOrigen, gbc);
        
        gbc.gridx++;
        panelContenido.add(origen, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaDestino, gbc);
        
        gbc.gridx++;
        panelContenido.add(destino, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaSalida, gbc);
        
        gbc.gridx++;
        panelContenido.add(salida, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaLlegada, gbc);
        
        gbc.gridx++;
        panelContenido.add(llegada, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(comp, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(noComp, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaAeS, gbc);
        
        gbc.gridx++;
        panelContenido.add(aeS, gbc);
        
        etiquetaAeS.setVisible(false);
        aeS.setVisible(false);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(mercancias, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(pasajeros, gbc);
        
        gbc.gridx = -1;
        gbc.gridy++;
        panelContenido.add(mercPeligrosas, gbc);
        
        gbc.gridx++;
        panelContenido.add(etiquetaPasajeros, gbc);
        
        gbc.gridx++;
        panelContenido.add(mercNoPeligrosas, gbc);
        panelContenido.add(numP, gbc);
        
        mercPeligrosas.setVisible(false);
        mercNoPeligrosas.setVisible(false);
        etiquetaPasajeros.setVisible(false);
        numP.setVisible(false);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaCarga, gbc);
        
        gbc.gridx++;
        panelContenido.add(carga, gbc);
        
        etiquetaCarga.setVisible(false);
        carga.setVisible(false);
        
        gbc.gridx = -2;
        gbc.gridy++;
        panelContenido.add(etiquetaPeriodo, gbc);
        
        gbc.gridx++;
        panelContenido.add(periodicidad, gbc);
        
        gbc.gridx++;
        panelContenido.add(panelCheckbox, gbc);
        
        panelCheckbox.setVisible(false);
        
        add(panelContenido, BorderLayout.CENTER);
        
        // Panel inferior para los botones
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new GridLayout(1, 2, 20, 0));
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 150, 0, 150));
		  
		//Añadir el botón de registro
        formatoBotones(registrarVuelo);
        panelInferior.add(registrarVuelo, gbc);
        
        add(panelInferior, BorderLayout.SOUTH);
	}

	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getNuevoVuelo().setVisible(false);
		Aplicacion.getInstance().showOpVuelos();
	}

	
	private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 48));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
	
	void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
	
	
	public void setControlador(ActionListener c) {
		registrarVuelo.addActionListener(c);
	 	mercancias.addActionListener(c);
	 	pasajeros.addActionListener(c);
	 	comp.addActionListener(c);
	 	noComp.addActionListener(c);
	 	periodicidad.addActionListener(c);
	 	llega.addActionListener(c);
	 	sale.addActionListener(c);
	}

	
	// Obtener la matrícula del avión
	public String getMat() {
		return (String) mat.getSelectedItem();
	}
	
	// Verificar si el vuelo es de llegada
	public boolean esLlegada() {
		return llega.isSelected();
	}

	// Verificar si el vuelo no es de salida
	public boolean esSalida() {
		return sale.isSelected();
	}

	// Obtener el nombre del aeropuerto de origen
	public String getOrigen() {
	    return origen.getText().trim();
	}
	public JTextField getOrigenTextField() {
    	return origen;
    }

	// Obtener el nombre del aeropuerto de destino
	public String getDestino() {
	    return destino.getText().trim();
	}
	public JTextField getDestinoTextField() {
    	return destino;
    }

	// Obtener el nombre de la aerolínea secundaria
	public String txtAerolineaSecundaria() {
	    return aeS.getText().trim();
	}
	
	public JTextField getAerolineaSecundaria() {
	    return aeS;
	}
	
	public JLabel getEtiqAeS() {
		return etiquetaAeS;
	}
	
	// Obtener la fecha y hora de salida
	public LocalDateTime getFechaHoraSalida() {
	    Date date = (Date) salida.getValue();
	    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	// Obtener la fecha y hora de llegada
	public LocalDateTime getFechaHoraLlegada() {
	    Date date = (Date) llegada.getValue();
	    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	// Verificar si el vuelo es de tipo mercancías
	public boolean esMercancias() {
	    return mercancias.isSelected();
	}

	// Verificar si el vuelo es de tipo pasajeros
	public boolean esPasajeros() {
	    return pasajeros.isSelected();
	}
	
	// Verificar si el vuelo lleva mercancías peligrosas
	public boolean esMercPeligrosas() {
	    return mercPeligrosas.isSelected();
	}
	
	public JRadioButton getMercPeligrosas() {
    	return mercPeligrosas;
    }

	// Verificar si el vuelo no lleva mercancías peligrosas
	public boolean esMercNoPeligrosas() {
	    return mercNoPeligrosas.isSelected();
	}
	
	public JRadioButton getMercNoPeligrosas() {
    	return mercNoPeligrosas;
    }
	
	// Verificar si el vuelo es compartido
	public boolean esCompartido() {
	    return comp.isSelected();
	}

	// Verificar si el vuelo no es compartido
	public boolean esNoCompartido() {
	    return noComp.isSelected();
	}
	
	// Obtener la periodicidad seleccionada
	public String getPeriodicidad() {
	    return (String) periodicidad.getSelectedItem();
	}
	
	public ArrayList<DayOfWeek> getDiasSeleccionados() {
	    ArrayList<DayOfWeek> dias = new ArrayList<>();

	    if (lunes.isSelected()) dias.add(DayOfWeek.MONDAY);
	    if (martes.isSelected()) dias.add(DayOfWeek.TUESDAY);
	    if (miercoles.isSelected()) dias.add(DayOfWeek.WEDNESDAY);
	    if (jueves.isSelected()) dias.add(DayOfWeek.THURSDAY);
	    if (viernes.isSelected()) dias.add(DayOfWeek.FRIDAY);
	    if (sabado.isSelected()) dias.add(DayOfWeek.SATURDAY);
	    if (domingo.isSelected()) dias.add(DayOfWeek.SUNDAY);

	    return dias;
	}
	
	public JPanel getPanelCheckbox() {
		return panelCheckbox;
	}
	
	// Obtener el dato (int o double) del campo "carga"
    public Double getCarga() {
    	try {
            return Double.parseDouble(carga.getText().trim());
        } catch (NumberFormatException e) {
            return -1.0; // Retorna -1 si el texto no es un número válido
        }
    }
    public JTextField getCargaTextField() {
    	return carga;
    }
    public JLabel getCargaLabel() {
    	return etiquetaCarga;
    }
    public Integer getNumP() {
    	try {
            return Integer.parseInt(numP.getText().trim());
        } catch (NumberFormatException e) {
            return -1; // Retorna -1 si el texto no es un número válido
        }
    }
    public JTextField getnumPTextField() {
    	return numP;
    }
    public JLabel getNumPLabel() {
    	return etiquetaPasajeros;
    }
    
    
    public void actualizarPantalla() {
    	Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
    	if(op != null) {
    		List<String> listaAviones = new ArrayList<>();
            for (String s : op.getAerolinea().getAviones().keySet()) {
                listaAviones.add(s);
            }
            DefaultComboBoxModel<String> modeloAviones = new DefaultComboBoxModel<>(listaAviones.toArray(new String[0]));
            mat.setModel(modeloAviones);
    	}
    }
	
	
	public void update() {
		// Reiniciar campos de texto
	    origen.setText("");
	    destino.setText("");
	    aeS.setText("");
	    carga.setText("");
	    numP.setText("");

	    // Reiniciar spinners a las fechas actuales o iniciales
	    salida.setValue(new Date()); // Fecha actual
	    llegada.setValue(new Date()); // Fecha actual

	    // Desmarcar todos los radio buttons y los combo box
	    tipoAvion.clearSelection();
	    tipoMerc.clearSelection();
	    compartido.clearSelection();
	    esLlegada.clearSelection();
	    periodicidad.setSelectedIndex(-1);
	    mat.setSelectedIndex(-1);

	    // Ocultar los radio buttons relacionados con mercancías peligrosas (si están visibles por algún evento)
	    // También los días para los vuelos alternos y los campos de "inventario" carga/pasajeros
	    mercPeligrosas.setVisible(false);
	    mercNoPeligrosas.setVisible(false);
	    panelCheckbox.setVisible(false);
	    etiquetaCarga.setVisible(false);
	    etiquetaPasajeros.setVisible(false);
	    carga.setVisible(false);
	    numP.setVisible(false);
	}
}
