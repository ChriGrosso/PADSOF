package elementos;

import java.time.LocalDateTime;

public class TerminalMercancias extends Terminal {
	
	private double capacidadToneladas;
	
	public TerminalMercancias(String id, double costeph,LocalDateTime fchRegistro,int numeroPuertas,  String prefijoPuerta, double capacidadToneladas) {
		super(id,costeph,fchRegistro,numeroPuertas,prefijoPuerta);
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

}
