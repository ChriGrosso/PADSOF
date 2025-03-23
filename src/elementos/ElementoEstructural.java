package elementos;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;

import vuelos.Vuelo;

public abstract class ElementoEstructural implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private double costePorHora;
	private LocalDate fchRegistro;
	private HashMap<ClaveVueloHoraUso,Uso> historialUsos;
	
	public ElementoEstructural(String id, double costeph,LocalDate fchRegistro) {
		this.setId(id);
		this.setCostePorHora(costeph);
		this.setFchRegistro(fchRegistro);
		this.historialUsos = new HashMap<ClaveVueloHoraUso,Uso>();
	}
	
	public double horasUsoDiario() {
		LocalDate diaHoy = LocalDate.now();
		double horasUsoHoy = 0;
		for(int i = this.historialUsos.size()-1; ((ArrayList<Uso>) this.historialUsos.values()).get(i).getHoraUso().getDayOfYear() == diaHoy.getDayOfYear() &&
			((ArrayList<Uso>) this.historialUsos.values()).get(i).getHoraUso().getYear() == diaHoy.getYear(); i--) {
			horasUsoHoy += ((ArrayList<Uso>) this.historialUsos.values()).get(i).calcularDuracion();
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

	
	public HashMap<ClaveVueloHoraUso, Uso> getHistorailUsos() {
		return this.historialUsos;
	}
	
	public boolean addUso(Vuelo vuelo, LocalDateTime horaUso) {
		Uso u = new Uso(horaUso, this);
		ClaveVueloHoraUso clave = new ClaveVueloHoraUso(vuelo, horaUso);
		this.historialUsos.put(clave, u);
		return true;
	}
	
	public void LimpiarHistorialUsos() {
		this.historialUsos.clear();
		return;
	}
}
