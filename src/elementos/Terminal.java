package elementos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import vuelos.Vuelo;

public abstract class Terminal extends ElementoEstructural {
	private int numeroPuertas;
	private String prefijoPuerta;
	private HashMap<String, Puerta> puertas;
	private ArrayList<Vuelo> vuelos;
	
	public Terminal(String id, double costeph,LocalDate fchRegistro, int numeroPuertas,  String prefijoPuerta) {
		super(id,costeph,fchRegistro);
		this.numeroPuertas = numeroPuertas;
		this.prefijoPuerta = prefijoPuerta;
		this.puertas = new HashMap<String, Puerta>();
		this.vuelos = new ArrayList<Vuelo>();
		
		for(int i = 1; i<= numeroPuertas; i++) {
			String nomP = prefijoPuerta + i;
			Puerta p = new Puerta(nomP);
			this.puertas.put(nomP, p);
		}
	}
	
	public int numPuertasOcupadasTerm() {
		int p_ocupadas = 0;
		for (Puerta p : puertas.values()){
			if(p.enUso()) {
				p_ocupadas += 1;
			}
		}
		return p_ocupadas;
	}

	/**
	 * @return the numeroPuertas
	 */
	public int getNumeroPuertas() {
		return this.numeroPuertas;
	}

	/**
	 * @param numeroPuertas the numeroPuertas to set
	 */
	public void setNumeroPuertas(int numeroPuertas) {
		if(numeroPuertas < this.numeroPuertas) {
			for(int i = this.numeroPuertas; i > numeroPuertas; i--) {
				String nomP = this.prefijoPuerta + i;
				this.puertas.remove(nomP);
			}
		} else if(numeroPuertas > this.numeroPuertas) {
			for(int i = this.numeroPuertas; i <= numeroPuertas; i++) {
				String nomP = this.prefijoPuerta + i;
				this.puertas.remove(nomP);
			}
		} 
		
		this.numeroPuertas = numeroPuertas;
	}

	/**
	 * @return the prefijoPuerta
	 */
	public String getPrefijoPuerta() {
		return this.prefijoPuerta;
	}

	/**
	 * @param prefijoPuerta the prefijoPuerta to set
	 */
	public void setPrefijoPuerta(String prefijoPuerta) {
		// Cambiar el código de las puertas con el perfijo correspondiente
		for(int i = 1; i<= this.numeroPuertas; i++) {
			String nomP = prefijoPuerta + i;
			Puerta p = new Puerta(nomP);
			this.puertas.put(nomP, p);
		}
		
		this.prefijoPuerta = prefijoPuerta;
	}

	/**
	 * @return the puertas
	 */
	public HashMap<String, Puerta> getPuertas() {
		return puertas;
	}

	/**
	 * @return the puerta con código "cod"
	 */
	public Puerta buscarPuertaPorCod(String cod) {
		return this.getPuertas().get(cod);
	}
	
	public ArrayList<Vuelo> getVuelos() {
		return this.vuelos;
	}
}
