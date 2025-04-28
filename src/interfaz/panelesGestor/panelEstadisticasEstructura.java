package interfaz.panelesGestor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;

import elementos.Finger;
import elementos.Hangar;
import elementos.Puerta;
import elementos.Terminal;
import elementos.ZonaParking;
import sistema.SkyManager;

public class panelEstadisticasEstructura extends JPanel {
	private static final long serialVersionUID = 1L;

	public panelEstadisticasEstructura(String tipo) {
        setLayout(new BorderLayout());
        setBackground(new Color(192, 214, 236));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(192, 214, 236));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Info del elemento más usado y horas totales
        JLabel elementoMasUsado = new JLabel(tipo + " más usado: " + obtenerElementoMasUsado(tipo));
        elementoMasUsado.setForeground(Color.BLACK);
        elementoMasUsado.setFont(new Font("Arial", Font.BOLD, 16));
        elementoMasUsado.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(elementoMasUsado);

        infoPanel.add(Box.createVerticalStrut(10));

        JLabel horasTotales = new JLabel("Horas Totales: " + obtenerHorasTotales(tipo));
        horasTotales.setForeground(Color.BLACK);
        horasTotales.setFont(new Font("Arial", Font.BOLD, 16));
        horasTotales.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(horasTotales);

        add(infoPanel, BorderLayout.WEST);

        // Gráfico
        add(crearGraficoDeBarras(tipo), BorderLayout.CENTER);
    }

    private JPanel crearGraficoDeBarras(String tipo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        HashMap<String, Double> datos = obtenerDatos(tipo);

        for (String nombre : datos.keySet()) {
            dataset.addValue(datos.get(nombre), "Horas de uso", nombre);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Media Horas de Uso de "+ tipo+ (tipo.equals("Hangar")? "es" : "s"),
            "Estructuras",
            "Horas de uso",
            dataset
        );
        
        // Cambiar color de las barras (azul)
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(70, 130, 180));  
        // Ajustar márgenes
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));  // Márgenes para que no quede pegado al borde
        // Cambiar el fondo del gráfico
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        // Ajustar el eje Y para que vaya de 0 a 24
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 24.0);  // Limitar el eje Y de 0 a 24

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(192, 214, 236));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        return chartPanel;
    }

    private HashMap<String, Double> obtenerDatos(String tipo) {
        HashMap<String, Double> datos = new HashMap<>();
        SkyManager modelo = SkyManager.getInstance();

        switch (tipo) {
            case "Finger":
                for (Finger f : modelo.getFingers().values()) {
                    datos.put(f.getId(), f.mediaHorasUsoDiario());
                }
                break;
            case "Puerta":
            	ArrayList<Puerta> puertas = new ArrayList<>();
            	for (Terminal t: modelo.getTerminales().values()) {
            		puertas.addAll(t.getPuertas().values());
            	}
                for (Puerta p : puertas) {
                    datos.put(p.getId(), p.mediaHorasUsoDiario());
                }
                break;
            case "Aparcamiento":
                for (ZonaParking p : SkyManager.getInstance().getZonasParking().values()) {
                    datos.put(p.getId(), p.mediaHorasUsoDiario());
                }
                break;
            case "Hangar":
                for (Hangar h : SkyManager.getInstance().getHangares().values()) {
                    datos.put(h.getId(), h.mediaHorasUsoDiario());
                }
                break;
        }

        return datos;
    }

    private String obtenerElementoMasUsado(String tipo) {
        String masUsado = "N/A";
        double maxHoras = -1;

        HashMap<String, Double> datos = obtenerDatos(tipo);
        for (String nombre : datos.keySet()) {
            double horas = datos.get(nombre);
            if (horas > maxHoras) {
                maxHoras = horas;
                masUsado = nombre;
            }
        }
        return masUsado;
    }

    private int obtenerHorasTotales(String tipo) {
        int total = 0;

        HashMap<String, Double> datos = obtenerDatos(tipo);
        for (double horas : datos.values()) {
            total += (int) horas;
        }
        return total;
    }
}

