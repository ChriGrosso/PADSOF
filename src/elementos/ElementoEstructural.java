package elementos;

import java.time.*;

public abstract class ElementoEstructural {
	private String id;
	private double costePorHora;
	private LocalDate fchRegistro;
	
	public ElementoEstructural(String id, double costeph,LocalDate fchRegistro2) {
		this.setId(id);
		this.setCostePorHora(costeph);
		this.setFchRegistro(fchRegistro2);
	}
	
	public double horasUsoDiario() {
		return 0;
	}
	
	public double mediaHorasUsoDiario() {
		return 0;
	}

	/**
	 * @return the fchRegistro
	 */
	public LocalDate getFchRegistro() {
		return fchRegistro;
	}

	/**
	 * @param fchRegistro2 the fchRegistro to set
	 */
	public void setFchRegistro(LocalDate fchRegistro2) {
		this.fchRegistro = fchRegistro2;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the costePorHora
	 */
	public double getCostePorHora() {
		return costePorHora;
	}

	/**
	 * @param costePorHora the costePorHora to set
	 */
	public void setCostePorHora(double costePorHora) {
		this.costePorHora = costePorHora;
	}

}
