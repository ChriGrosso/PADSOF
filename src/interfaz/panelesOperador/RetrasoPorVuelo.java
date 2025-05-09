package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import aeropuertos.Aeropuerto;
import sistema.SkyManager;

/**
 * Panel que permite calcular el retraso medio para un vuelo específico.
 * Incluye un campo para ingresar el ID del vuelo y muestra el resultado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class RetrasoPorVuelo extends JPanel{
	private static final long serialVersionUID = 1L;
	JComboBox<String> origen = new JComboBox<>();
	JComboBox<String> destino = new JComboBox<>();
	SkyManager sk = SkyManager.getInstance();
	JLabel nuestroAeO = new JLabel(((sk.getInformacionPropia() != null) ? sk.getInformacionPropia().getNombre() : ("Aeropuerto Propio")) + "(" + ((sk.getInformacionPropia() != null) ? sk.getInformacionPropia().getCodigo() : "AP") +")");
	JLabel nuestroAeD = new JLabel(((sk.getInformacionPropia() != null) ? sk.getInformacionPropia().getNombre() : "Aeropuerto Propio") + "(" + ((sk.getInformacionPropia() != null) ? sk.getInformacionPropia().getCodigo() : "AP")+")");
	JLabel resultado = new JLabel();
	private JRadioButton llega, sale;
	ButtonGroup esLlegada;
	JButton calcular = new JButton();
	
	/**
	 * Constructor que inicializa y configura todos los componentes del panel.
	 */
	public RetrasoPorVuelo() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(173, 216, 230));
        
        // Título
        JLabel titulo = new JLabel("Retraso medio de vuelos por aeropuertos de origen y destino (minutos)");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSuperior.add(titulo);
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBackground(new Color(173, 216, 230));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Crear Componentes
        llega = new JRadioButton("Vuelos de Llegada");
        llega.setBackground(new Color(173, 216, 230));
        sale = new JRadioButton("Vuelos de Salida");
        sale.setBackground(new Color(173, 216, 230));
        esLlegada = new ButtonGroup();
        esLlegada.add(llega);
        esLlegada.add(sale);
        JLabel etiquetaOrigen = new JLabel("Selecciona un aeropuerto de Origen:");
        etiquetaOrigen.setForeground(Color.BLACK);	
        JLabel etiquetaDestino = new JLabel("Selecciona un aeropuerto de Destino:");
        etiquetaDestino.setForeground(Color.BLACK);
        // Agregar todos los aeropuertos externos 
        for(Aeropuerto a: SkyManager.getInstance().getAeropuertosExternos().values()) {
        	origen.addItem(a.getNombre() + "(" + a.getCodigo() + ")");
        	destino.addItem(a.getNombre() + "(" + a.getCodigo() + ")");
        }
        
        // Ubicar los elementos
        gbc.gridx = -1;
        panelContenido.add(llega, gbc);
        
        gbc.gridx+=2;
        panelContenido.add(sale, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaOrigen, gbc);
        
        gbc.gridx++;
        panelContenido.add(nuestroAeO, gbc);
        panelContenido.add(origen, gbc);
        nuestroAeO.setVisible(false);
        origen.setVisible(false);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaDestino, gbc);
        
        gbc.gridx++;
        panelContenido.add(nuestroAeD, gbc);
        panelContenido.add(destino, gbc);
        nuestroAeD.setVisible(false);
        destino.setVisible(false);
        add(panelContenido, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(173, 216, 230));
        
        calcular = createContentButton("Calcular retraso medio");
        formatoBotones(calcular);
        calcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(calcular);
        resultado.setText("Retraso promedio de vuelos para viajes de ");
        resultado.setForeground(Color.BLACK);
        resultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(resultado);
        add(panelInferior, BorderLayout.SOUTH);
	}
	
	/**
	 * Establece el controlador para el botón de cálculo.
	 * 
	 * @param c ActionListener a asociar.
	 */
	public void setControlador(ActionListener c) {
		calcular.addActionListener(c);
	}
	
	
	/**
	 * Crea un botón con el formato de estilo estándar.
	 * 
	 * @param text Texto a mostrar en el botón.
	 * @return JButton configurado.
	 */
	private JButton createContentButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 48));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }
	
	/**
	 * Aplica formato estético estándar a un botón.
	 * 
	 * @param boton El botón a formatear.
	 */
	void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
	
	
	/**
	 * Obtiene el JLabel con el resultado del cálculo.
	 * 
	 * @return JLabel del resultado.
	 */
	public JLabel getResultado() {
		return resultado;
	}
	
	/**
	 * Devuelve el componente JComboBox que contiene los aeropuertos de origen.
	 * 
	 * @return JComboBox con los aeropuertos de origen disponibles.
	 */
	public JComboBox<String> getOrigenJCombo() {
		return origen;
	}
	
	/**
	 * Devuelve el componente JComboBox que contiene los aeropuertos de destino.
	 * 
	 * @return JComboBox con los aeropuertos de destino disponibles.
	 */
	public JComboBox<String> getDestinoJCombo() {
		return destino;
	}
	

	/**
	 * Devuelve el JLabel correspondiente al aeropuerto de origen seleccionado.
	 * 
	 * @return JLabel que representa el aeropuerto de origen.
	 */
	public JLabel getAeO() {
		return nuestroAeO;
	}
	
	/**
	 * Devuelve el JLabel correspondiente al aeropuerto de destino seleccionado.
	 * 
	 * @return JLabel que representa el aeropuerto de destino.
	 */
	public JLabel getAeD() {
		return nuestroAeD;
	}
	
	
	/**
	 * Indica si el vuelo seleccionado es de llegada.
	 * 
	 * @return true si el vuelo es de llegada; false en caso contrario.
	 */
	public boolean esLlegada() {
		return llega.isSelected();
	}

	/**
	 * Indica si el vuelo seleccionado es de salida.
	 * 
	 * @return true si el vuelo es de salida; false en caso contrario.
	 */
	public boolean esSalida() {
		return sale.isSelected();
	}
}
