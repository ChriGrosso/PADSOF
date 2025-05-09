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

/**
 * Panel gráfico que muestra estadísticas relacionadas con los vuelos
 * de la aerolínea asociada al operador actual. Incluye número de vuelos
 * en tiempo, retrasados, y estadísticas promedio por mes, por vuelo
 * y por franja horaria.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class OperadorEstadisticas extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel titulo, vuelosEnTiempo, vuelosRetrasados, retrasos;
	private RetrasoPorMes porMes = new RetrasoPorMes();
	private RetrasoPorVuelo porVuelo = new RetrasoPorVuelo();
	private RetrasoPorFranja porFranja = new RetrasoPorFranja();

	/**
	 * Constructor del panel de estadísticas del operador.
	 * Inicializa y organiza los componentes gráficos.
	 */
	public OperadorEstadisticas() {
		setLayout(new BorderLayout());
		setBackground(new Color(173, 216, 230));
		setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

		BotonVolver panelSuperiorIzquierdo = new BotonVolver("resources/atras.png");
		panelSuperiorIzquierdo.setControladorVolver(_ -> paginaAnterior());
		add(panelSuperiorIzquierdo, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new GridBagLayout());
		panelContenido.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;

		titulo = new JLabel("Estadísticas");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(new Color(70, 130, 180)); 
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		panelContenido.add(titulo, gbc);

		gbc.gridy += 3;
		vuelosEnTiempo = new JLabel("Número de vuelos en tiempo: ");
		vuelosEnTiempo.setForeground(Color.BLACK); 
		panelContenido.add(vuelosEnTiempo, gbc);

		gbc.gridy++;
		vuelosRetrasados = new JLabel("Número de vuelos retrasados: ");
		vuelosRetrasados.setForeground(Color.BLACK); 
		panelContenido.add(vuelosRetrasados, gbc);

		gbc.gridy++;
		retrasos = new JLabel("Retrasos Promedio");
		retrasos.setForeground(Color.BLACK);
		retrasos.setFont(new Font("Arial", Font.BOLD, 16));
		retrasos.setHorizontalAlignment(SwingConstants.CENTER);
		panelContenido.add(retrasos, gbc);

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

	/**
	 * Actualiza los datos estadísticos mostrados en pantalla,
	 * extrayendo la información desde la aerolínea del operador actual.
	 */
	public void actualizarPantalla() {
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		titulo.setText("Estadísticas de " + a.getNombre());
		vuelosEnTiempo.setText("Número de vuelos en tiempo: " + a.getEstadisticas().numVuelosEnTiempo());
		vuelosRetrasados.setText("Número de vuelos retrasados: " + a.getEstadisticas().numVuelosRetrasados());
	}

	/**
	 * Vuelve a la pantalla principal del operador, guardando los datos.
	 */
	private void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().getOpEstadisticas().setVisible(false);
		Aplicacion.getInstance().showOpInicio();
	}

	/**
	 * Asigna el controlador de eventos principal para el panel de estadísticas.
	 * Actualmente no implementado.
	 * 
	 * @param controlOpEst Controlador de estadísticas del operador.
	 */
	public void setControlador(ControlOperadorEstadisticas controlOpEst) {
		// TODO Auto-generated method stub
	}

	/**
	 * Devuelve el panel con estadísticas de retraso por mes.
	 * @return Panel de tipo RetrasoPorMes
	 */
	public RetrasoPorMes getRetPorMes() {
		return porMes;
	}

	/**
	 * Devuelve el panel con estadísticas de retraso por vuelo.
	 * @return Panel de tipo RetrasoPorVuelo
	 */
	public RetrasoPorVuelo getRetPorVuelo() {
		return porVuelo;
	}

	/**
	 * Devuelve el panel con estadísticas de retraso por franja horaria.
	 * @return Panel de tipo RetrasoPorFranja
	 */
	public RetrasoPorFranja getRetPorFranja() {
		return porFranja;
	}
}
