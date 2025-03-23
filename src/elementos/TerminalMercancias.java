package elementos;

import java.time.LocalDate;
import vuelos.Vuelo;
import vuelos.VueloMercancias;

public class TerminalMercancias extends Terminal {
	private static final long serialVersionUID = 1L;
	private double capacidadToneladas; 
	
	public TerminalMercancias(String id,LocalDate fchRegistro,int numeroPuertas,  String prefijoPuerta, double capacidadToneladas) {
		super(id,fchRegistro,numeroPuertas,prefijoPuerta);
		this.setCapacidadToneladas(capacidadToneladas);
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
		if(this.getVuelos().isEmpty()) {
			return 0;
		}
		double total = 0;
		for(Vuelo v: this.getVuelos()) {
			total = ((VueloMercancias) v).getCarga();
		}
		return total;
	}
}
