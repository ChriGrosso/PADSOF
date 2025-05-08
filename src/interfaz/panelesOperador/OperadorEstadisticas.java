package interfaz.panelesOperador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import aerolineas.Aerolinea;
import interfaz.Aplicacion;
import interfaz.util.BotonVolver;
import sistema.SkyManager;
import usuarios.Operador;

public class OperadorEstadisticas extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel titulo, vuelosEnTiempo, vuelosRetrasados, retrasos;
	// Retraso por MES
	private RetrasoPorMes porMes = new RetrasoPorMes();
	private RetrasoPorVuelo porVuelo = new RetrasoPorVuelo();
	private RetrasoPorFranja porFranja = new RetrasoPorFranja();
	
	public OperadorEstadisticas() {
		// Configurar el Layout
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		        
		// Contenedor en la esquina superior derecha
		BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras.png");
		panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());

		// Añadir el contenedor al panel principal
		add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		        
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridBagLayout());
		panelContenido.setBackground(new Color(173, 216, 230));
		panelContenido.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;

		// Título
		titulo = new JLabel("Estadísticas");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(new Color(70, 130, 180)); 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3; // Ocupa 3 columnas
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc); 
		
		// Núm. vuelos en tiempo
		gbc.gridy+= 3;
		vuelosEnTiempo = new JLabel("Número de vuelos en tiempo: ");
		vuelosEnTiempo.setForeground(Color.BLACK); 
		panelContenido.add(vuelosEnTiempo, gbc);
		
		// Núm. vuelos retrasados
		gbc.gridy++;
		vuelosRetrasados = new JLabel("Número de vuelos retrasados: ");
		vuelosRetrasados.setForeground(Color.BLACK); 
		panelContenido.add(vuelosRetrasados, gbc);
		
		// Retrasos promedio
		gbc.gridy++;
		retrasos = new JLabel("Retrasos Promedio");
		retrasos.setForeground(Color.BLACK);
		retrasos.setFont(new Font("Arial", Font.BOLD, 16));
		retrasos.setHorizontalAlignment(SwingConstants.CENTER);
		panelContenido.add(retrasos, gbc);
		
		// Collección de paneles apara distintos tipos de retrasos medios
		JTabbedPane tipos = new JTabbedPane();
		ControlRetrasoPorMes contPorMes = new ControlRetrasoPorMes();
		porMes.setControlador(contPorMes);
		tipos.addTab("POR MES", porMes);
		ControlRetrasoPorVuelo contPorVuelo = new ControlRetrasoPorVuelo();
		porVuelo.setControlador(contPorVuelo);
		tipos.addTab("POR ORIGEN Y DESTINO", porVuelo);
		ControlRetrasoPorFranja contPorFranja = new ControlRetrasoPorFranja();
		porFranja.setControlador(contPorFranja);
		tipos.addTab("POR FRANJA HORARIA", porFranja);
		
		gbc.gridy++;
		panelContenido.add(tipos, gbc);
		
		add(panelContenido, BorderLayout.CENTER);
	}
	
	
	public void actualizarPantalla() {
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		titulo.setText("Estadísticas de " + a.getNombre());
		vuelosEnTiempo.setText("Número de vuelos en tiempo: " + a.getEstadisticas().numVuelosEnTiempo());
		vuelosRetrasados.setText("Número de vuelos retrasados: " + a.getEstadisticas().numVuelosRetrasados());
	}

	
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpEstadisticas().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}

	
	public void setControlador(ControlOperadorEstadisticas controlOpEst) {
		// TODO Auto-generated method stub
		
	}

	
	public RetrasoPorMes getRetPorMes() {
		return porMes;
	}
	
	public RetrasoPorVuelo getRetPorVuelo() {
		return porVuelo;
	}
	
	public RetrasoPorFranja getRetPorFranja() {
		return porFranja;
	}
}
