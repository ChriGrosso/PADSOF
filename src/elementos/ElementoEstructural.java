package elementos;

import java.time.*;

public abstract class ElementoEstructural {
	private String id;
	private double costePorHora;
	private LocalDateTime fchRegistro;
	
	public ElementoEstructural(String id, double costeph,LocalDateTime fchRegistro) {
		this.setId(id);
		this.setCostePorHora(costeph);
		this.setFchRegistro(fchRegistro);
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
	public LocalDateTime getFchRegistro() {
		return fchRegistro;
	}

	/**
	 * @param fchRegistro the fchRegistro to set
	 */
	public void setFchRegistro(LocalDateTime fchRegistro) {
		this.fchRegistro = fchRegistro;
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
