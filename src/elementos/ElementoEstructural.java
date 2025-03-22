package elementos;

import java.time.*;
import java.util.ArrayList;

public abstract class ElementoEstructural {
	private String id;
	private double costePorHora;
	private LocalDate fchRegistro;
	private ArrayList<Uso> historialUsos;
	
	public ElementoEstructural(String id, double costeph,LocalDate fchRegistro) {
		this.setId(id);
		this.setCostePorHora(costeph);
		this.setFchRegistro(fchRegistro);
		this.historialUsos = new ArrayList<Uso>();
	}
	
	public double horasUsoDiario() {
		LocalDate diaHoy = LocalDate.now();
		double horasUsoHoy = 0;
		for(int i = this.historialUsos.size()-1; this.historialUsos.get(i).getHoraUso().getDayOfYear() == diaHoy.getDayOfYear() &&
			this.historialUsos.get(i).getHoraUso().getYear() == diaHoy.getYear(); i--) {
			horasUsoHoy += this.historialUsos.get(i).calcularDuracion();
		}
		return horasUsoHoy;
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

	
	public ArrayList<Uso> getHistorailUsos() {
		return this.historialUsos;
	}
	
	public boolean addUso(LocalDateTime horaUso) {
		Uso u = new Uso(horaUso, this);
		if(this.historialUsos.isEmpty()) {
			this.historialUsos.addLast(u);
			return true;
		}
		if(this.historialUsos.getLast().getHoraDesuso() == null) {
			return false;
		}
		this.historialUsos.addLast(u);
		return true;
	}
	
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
		return;
	}
}
