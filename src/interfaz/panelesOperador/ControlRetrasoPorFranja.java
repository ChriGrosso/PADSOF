package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import aerolineas.Aerolinea;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;

/**
 * Controlador para manejar el cálculo del retraso promedio por franja horaria.
 * Implementa ActionListener para responder a eventos de la interfaz gráfica.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es 
 */
public class ControlRetrasoPorFranja implements ActionListener{
	private SkyManager modelo;
	
	/**
	 * Constructor que inicializa el modelo a la instancia única de SkyManager.
	 */
	public ControlRetrasoPorFranja() {
		this.modelo = SkyManager.getInstance();
	}

	/**
	 * Maneja los eventos de acción provenientes de la interfaz gráfica.
	 * Ejecuta el cálculo de retraso si se selecciona el comando correspondiente.
	 * 
	 * @param e el evento de acción
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Calcular retraso medio")) {   
			mostrarRetraso();
		}
	}

	/**
	 * Obtiene las horas de inicio y fin desde la vista y calcula el retraso
	 * promedio en minutos para esa franja horaria, mostrándolo en la interfaz.
	 */
	private void mostrarRetraso() {
		var v = Aplicacion.getInstance().getOpEstadisticas().getRetPorFranja();
		
		LocalTime inicio = null, fin = null;
		inicio = v.getHoraSalida();
		fin = v.getHoraLlegada();
		
		// Mostramos el retraso medio
		Operador op = (Operador) modelo.getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		v.getResultado().setText("Retraso promedio de todos los vuelos desde las " + inicio + " hasta las " + fin + ": " + a.getEstadisticas().retrasoMedioPorFranjaHorariaMinutos(inicio, fin) + " minutos");
	}

}
