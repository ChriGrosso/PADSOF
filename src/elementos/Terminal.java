package elementos;

import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class Terminal extends ElementoEstructural {
	private int numeroPuertas;
	private String prefijoPuerta;
	private HashMap<String, Puerta> puertas;
	
	public Terminal(String id, double costeph,LocalDateTime fchRegistro, int numeroPuertas,  String prefijoPuerta) {
		super(id,costeph,fchRegistro);
		this.setNumeroPuertas(numeroPuertas);
		this.setPrefijoPuerta(prefijoPuerta);
		HashMap<String, Puerta> puertas = new HashMap<String, Puerta>();
		for(int i=0; i < numeroPuertas; i++) {
			String cod = prefijoPuerta + i+1;
			puertas.put(cod, new Puerta(cod));
		}
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
	public HashMap<String, Puerta> getPuertas() {
		return puertas;
	}

	/**
	 * @param puertas the puertas to set
	 */
	public void setPuertas(HashMap<String, Puerta> puertas) {
		this.puertas = puertas;
	}

}
