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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RetrasoPorMes extends JPanel{
	private static final long serialVersionUID = 1L;
	JComboBox<String> meses = new JComboBox<>();
	JLabel resultado = new JLabel();
	JButton calcular = new JButton();
	
	public RetrasoPorMes() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		
		JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(173, 216, 230));
        
        // Título
        JLabel titulo = new JLabel("Retraso medio de vuelos por mes (minutos)");
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
        JLabel etiquetaMes = new JLabel("Selecciona un mes:");
        etiquetaMes.setForeground(Color.BLACK);	
        // Agregar todos los meses al JComboBox
        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : nombresMeses) {
            meses.addItem(mes);
        }
        
        // Ubicar los elementos
        panelContenido.add(etiquetaMes, gbc);
        
        gbc.gridx++;
        panelContenido.add(meses, gbc);
        add(panelContenido, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(173, 216, 230));
        
        calcular = createContentButton("Calcular retraso medio");
        formatoBotones(calcular);
        calcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(calcular);
        resultado.setText("Retraso promedio de vuelos en el mes ");
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
	
	
	public JComboBox<String> getMeses() {
		return meses;
	}
	
	public JLabel getResultado() {
		return resultado;
	}
}
