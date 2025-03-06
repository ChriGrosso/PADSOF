/**
 * 
 */
package elementos;

import aerolineas.Avion;

/**
 * 
 */
public class ZonaParking {
	private int numPlazas;
	private double alturaPlaza;
	private double anchuraPlaza;
	private double largoPlaza;
	
	public ZonaParking(int numPlazas,double alturaPlaza,double anchuraPlaza,double largoPlaza) {
		this.alturaPlaza=alturaPlaza;
		this.anchuraPlaza=anchuraPlaza;
		this.numPlazas=numPlazas;
		this.largoPlaza=largoPlaza;
	}
	
	public int numPlazasOcupadasPark() {
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
