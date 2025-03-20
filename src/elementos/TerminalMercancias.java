package elementos;

import java.time.LocalDate;
import java.util.ArrayList;

import vuelos.VueloMercancias;

public class TerminalMercancias extends Terminal {
	
	private double capacidadToneladas;
	private ArrayList<VueloMercancias> vuelos; 
	
	public TerminalMercancias(String id, double costeph,LocalDate fchRegistro,int numeroPuertas,  String prefijoPuerta, double capacidadToneladas) {
		super(id,costeph,fchRegistro,numeroPuertas,prefijoPuerta);
		this.setCapacidadToneladas(capacidadToneladas);
		this.vuelos = new ArrayList<VueloMercancias>();
	}

	/**
	 * @return the capacidadToneladas
	 */
	public double getCapacidadToneladas() {
		return capacidadToneladas;
	}

	/**
	 * @param capacidadToneladas the capacidadToneladas to set
	 */
	public void setCapacidadToneladas(double capacidadToneladas) {
		this.capacidadToneladas = capacidadToneladas;
	}

	public double getCargaTotal() {
		if(this.vuelos.isEmpty()) {
			return 0;
		}
		double total = 0;
		for(VueloMercancias v: this.vuelos) {
			total = v.getCarga();
		}
		return total;
	}
}
