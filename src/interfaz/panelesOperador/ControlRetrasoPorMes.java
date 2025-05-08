package interfaz.panelesOperador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.util.HashMap;

import aerolineas.Aerolinea;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Operador;

/**
 * Controlador para manejar el cálculo del retraso promedio por mes.
 * Implementa ActionListener para responder a eventos de la interfaz gráfica.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es 
 */
public class ControlRetrasoPorMes implements ActionListener{
	private SkyManager modelo;
	
	/**
	 * Constructor que inicializa el modelo a la instancia única de SkyManager.
	 */
	public ControlRetrasoPorMes() {
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
	 * Convierte el mes seleccionado en la interfaz a un valor del enum Month,
	 * y muestra el retraso promedio en minutos correspondiente al mes.
	 */
	private void mostrarRetraso() {
		var v = Aplicacion.getInstance().getOpEstadisticas().getRetPorMes();
		String seleccion = (String) v.getMeses().getSelectedItem();
		Month mes = null;
		HashMap<String, Month> dicc = new HashMap<String, Month>();
		dicc.put("Enero", Month.JANUARY);
		dicc.put("Febrero", Month.FEBRUARY);
		dicc.put("Marzo", Month.MARCH);
		dicc.put("Abril", Month.APRIL);
		dicc.put("Mayo", Month.MAY);
		dicc.put("Junio", Month.JUNE);
		dicc.put("Julio", Month.JULY);
		dicc.put("Agosto", Month.AUGUST);
		dicc.put("Septiembre", Month.SEPTEMBER);
		dicc.put("Octubre", Month.OCTOBER);
		dicc.put("Noviembre", Month.NOVEMBER);
		dicc.put("Diciembre", Month.DECEMBER);
		// Depende del mes seleccionado
		mes = dicc.get(seleccion);
		
		// Mostramos el retraso medio
		Operador op = (Operador) modelo.getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		v.getResultado().setText("Retraso promedio de vuelos en el mes de " + seleccion + ": "
												+ a.getEstadisticas().retrasoMedioPorMesMinutos(mes) + " minutos");
	}

}
