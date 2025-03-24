package elementos;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;

import vuelos.Vuelo;

/**
 * Clase abstracta que representa un elemento estructural del aeropuerto,
 * como pistas, hangares, puertas, etc. Incluye funcionalidades comunes como
 * cálculo de coste por uso, historial de usos y estadísticas.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public abstract class ElementoEstructural implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // Identificador único del elemento
	private double costePorHora; // Coste de uso por hora
	private LocalDate fchRegistro; // Fecha en que el elemento fue registrado
	private HashMap<Vuelo, Uso> historialUsos; // Historial de usos por vuelo

	/**
	 * Constructor base para cualquier elemento estructural.
	 * @param id identificador único del elemento
	 * @param costeph coste por hora de uso
	 * @param fchRegistro fecha de alta del elemento
	 */
	public ElementoEstructural(String id, double costeph, LocalDate fchRegistro) {
		this.setId(id);
		this.setCostePorHora(costeph);
		this.setFchRegistro(fchRegistro);
		this.historialUsos = new HashMap<>();
	}

	/**
	 * Calcula el total de horas que este recurso ha sido utilizado en el día actual.
	 * Solo considera los usos que tienen fecha igual a hoy.
	 * @return total de horas de uso del día actual
	 */
	public double horasUsoDiario() {
		LocalDate diaHoy = LocalDate.now();
		double horasUsoHoy = 0;

		// Convierte los valores del HashMap en una lista y recorre desde el final
		ArrayList<Uso> usos = new ArrayList<>(this.historialUsos.values());

		for (int i = usos.size() - 1;
		     i >= 0 && usos.get(i).getHoraUso().getDayOfYear() == diaHoy.getDayOfYear()
		         && usos.get(i).getHoraUso().getYear() == diaHoy.getYear();
		     i--) {
			horasUsoHoy += usos.get(i).calcularDuracion();
		}
		return horasUsoHoy;
	}
	
	/**
	 * Calcula la media de horas que este recurso ha sido utilizado.
	 * Se consideran todos los usos.
	 * @return media Horas de uso diario
	 */
	public double mediaHorasUsoDiario() {
	    if (this.historialUsos.isEmpty()) {
	        return 0.0;
	    }

	    HashMap<LocalDate, Double> usoPorDia = new HashMap<>();

	    for (Uso uso : this.historialUsos.values()) {
	        LocalDate fecha = uso.getHoraUso().toLocalDate();
	        double duracion = uso.calcularDuracion();

	        usoPorDia.put(fecha, usoPorDia.getOrDefault(fecha, 0.0) + duracion);
	    }

	    double totalHoras = 0.0;
	    for (double horas : usoPorDia.values()) {
	        totalHoras += horas;
	    }

	    return totalHoras / usoPorDia.size();
	}

	// Getters y setters con documentación básica

	/**
	 * @return la fecha de registro del elemento
	 */
	public LocalDate getFchRegistro() {
		return fchRegistro;
	}

	/**
	 * Establece la fecha de registro del elemento.
	 */
	public void setFchRegistro(LocalDate fchRegistro2) {
		this.fchRegistro = fchRegistro2;
	}

	/**
	 * @return el identificador del elemento
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del elemento.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el coste por hora del elemento
	 */
	public double getCostePorHora() {
		return costePorHora;
	}

	/**
	 * Establece el coste por hora del elemento.
	 */
	public void setCostePorHora(double costePorHora) {
		this.costePorHora = costePorHora;
	}

	/**
	 * Devuelve el historial de usos del elemento.
	 */
	public HashMap<Vuelo, Uso> getHistorailUsos() {
		return this.historialUsos;
	}

	/**
	 * Registra un nuevo uso de este elemento por parte de un vuelo en un momento dado.
	 * @param vuelo vuelo que usa el recurso
	 * @param horaUso hora en la que inicia el uso
	 * @return true si el uso fue registrado correctamente
	 */
	public boolean addUso(Vuelo vuelo, LocalDateTime horaUso) {
		Uso u = new Uso(horaUso, this);
		this.historialUsos.put(vuelo, u);
		return true;
	}

	/**
	 * Elimina todo el historial de usos del elemento.
	 */
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
	}
}
