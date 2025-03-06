package elementos;

public class TerminalMercancias extends Terminal {
	
	private double capacidadToneladas;
	
	public TerminalMercancias(int numeroPuertas,  String prefijoPuerta, double capacidadToneladas) {
		super(numeroPuertas,prefijoPuerta);
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
