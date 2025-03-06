package elementos;

import java.util.List;

public abstract class Terminal {
	private int numeroPuertas;
	private String prefijoPuerta;
	private List <Puerta> puertas;
	
	public Terminal(int numeroPuertas,  String prefijoPuerta) {
		this.setNumeroPuertas(numeroPuertas);
		this.setPrefijoPuerta(prefijoPuerta);
	}
	
	public int numPuertasOcupadasTerm() {
		return 0;
	}

	/**
	 * @return the numeroPuertas
	 */
	public int getNumeroPuertas() {
		return numeroPuertas;
	}

	/**
	 * @param numeroPuertas the numeroPuertas to set
	 */
	public void setNumeroPuertas(int numeroPuertas) {
		this.numeroPuertas = numeroPuertas;
	}

	/**
	 * @return the prefijoPuerta
	 */
	public String getPrefijoPuerta() {
		return prefijoPuerta;
	}

	/**
	 * @param prefijoPuerta the prefijoPuerta to set
	 */
	public void setPrefijoPuerta(String prefijoPuerta) {
		this.prefijoPuerta = prefijoPuerta;
	}

	/**
	 * @return the puertas
	 */
	public List <Puerta> getPuertas() {
		return puertas;
	}

	/**
	 * @param puertas the puertas to set
	 */
	public void setPuertas(List <Puerta> puertas) {
		this.puertas = puertas;
	}

}
