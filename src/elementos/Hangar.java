package elementos;


import java.time.LocalDate;
import aviones.Avion;

public abstract class Hangar extends ElementoEstructural {
	private int numPlazas;
	private double alturaPlaza;
	private double anchuraPlaza;
	private double largoPlaza;

	public Hangar(String id, double costeph, LocalDate fchRegistro, int numPlazas, double alturaPlaza,double anchuraPlaza,double largoPlaza) {
		super(id, costeph, fchRegistro);
		this.setAlturaPlaza(anchuraPlaza);
		this.setNumPlazas(numPlazas);
		this.setAnchuraPlaza(anchuraPlaza);
		this.setLargoPlaza(largoPlaza);
	}
	
	public int numPlazasOcupadasHangar() {
		return 0;
	}
	
	public boolean comprobarCompatibilidad(Avion a) {
		return false;
	}

	/**
	 * @return the numPlazas
	 */
	public int getNumPlazas() {
		return numPlazas;
	}

	/**
	 * @param numPlazas the numPlazas to set
	 */
	public void setNumPlazas(int numPlazas) {
		this.numPlazas = numPlazas;
	}

	/**
	 * @return the alturaPlaza
	 */
	public double getAlturaPlaza() {
		return alturaPlaza;
	}

	/**
	 * @param alturaPlaza the alturaPlaza to set
	 */
	public void setAlturaPlaza(double alturaPlaza) {
		this.alturaPlaza = alturaPlaza;
	}

	/**
	 * @return the anchuraPlaza
	 */
	public double getAnchuraPlaza() {
		return anchuraPlaza;
	}

	/**
	 * @param anchuraPlaza the anchuraPlaza to set
	 */
	public void setAnchuraPlaza(double anchuraPlaza) {
		this.anchuraPlaza = anchuraPlaza;
	}

	/**
	 * @return the largoPlaza
	 */
	public double getLargoPlaza() {
		return largoPlaza;
	}

	/**
	 * @param largoPlaza the largoPlaza to set
	 */
	public void setLargoPlaza(double largoPlaza) {
		this.largoPlaza = largoPlaza;
	}

}
