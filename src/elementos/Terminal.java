package elementos;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import vuelos.Vuelo;

public abstract class Terminal extends ElementoEstructural {
	private int numeroPuertas;
	private String prefijoPuerta;
	private List <Puerta> puertas;
	private List <Vuelo> vuelosQueSirve;
	
	public Terminal(String id, double costeph,LocalDateTime fchRegistro, int numeroPuertas,  String prefijoPuerta) {
		super(id,costeph,fchRegistro);
		this.setNumeroPuertas(numeroPuertas);
		this.setPrefijoPuerta(prefijoPuerta);
	}
	
	public int numPuertasOcupadasTerm() {
		for (Puerta p : puertas){
			
		}
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
