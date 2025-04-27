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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class RetrasoPorFranja extends JPanel{
	private static final long serialVersionUID = 1L;
	JLabel resultado = new JLabel();
	SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
	SpinnerDateModel model2 = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
	JSpinner salida = new JSpinner(model);
	JSpinner llegada = new JSpinner(model2);
	JButton calcular = new JButton();
	
	public RetrasoPorFranja() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(173, 216, 230));
        
        // Título
        JLabel titulo = new JLabel("Retraso medio de vuelos por franja horaria (minutos)");
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
        JLabel etiquetaInicio = new JLabel("Hora de inicio:");
        etiquetaInicio.setForeground(Color.BLACK);	
        JLabel etiquetaFin = new JLabel("Hora de fin:");
        etiquetaFin.setForeground(Color.BLACK);
        // Mostrar solo la hora, no la fecha
        JSpinner.DateEditor editor = new JSpinner.DateEditor(salida, "HH:mm");
        salida.setEditor(editor);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(llegada, "HH:mm");
        llegada.setEditor(editor2);
        
        // Ubicar los elementos
        panelContenido.add(etiquetaInicio, gbc);
        
        gbc.gridx++;
        panelContenido.add(salida, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        panelContenido.add(etiquetaFin, gbc);
        
        gbc.gridx++;
        panelContenido.add(llegada, gbc);
        add(panelContenido, BorderLayout.CENTER);
        
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(173, 216, 230));
        
        calcular = createContentButton("Calcular retraso medio");
        formatoBotones(calcular);
        calcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(calcular);
        resultado.setText("Retraso promedio de todos los vuelos desde ");
        resultado.setForeground(Color.BLACK);
        resultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(resultado);
        add(panelInferior, BorderLayout.SOUTH);
	}
	
	public void setControlador(ActionListener c) {
		calcular.addActionListener(c);
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
	
	
	public JLabel getResultado() {
		return resultado;
	}
	
	// Obtener la fecha y hora de salida
	public LocalTime getHoraSalida() {
		Date date = (Date) salida.getValue();
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}

	// Obtener la fecha y hora de llegada
	public LocalTime getHoraLlegada() {
		Date date = (Date) llegada.getValue();
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}
}
