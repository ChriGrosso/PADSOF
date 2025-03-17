package facturas;

import java.util.Date;

import aerolineas.Aerolinea;

public class Factura {
	private String id;
	private double total;
	private Date fchEmision;
	private Date fchPago;
	private boolean pagado;
	

	public Factura(String id,double total,Date fchEmision) {
		this.setId(id);
		this.setTotal(total);
		this.setFchEmision(fchEmision);
		this.pagar(false);
	}
	
	public double calcularFactura(Aerolinea a) {
		return 0;
	}
	
	


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}


	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}


	/**
	 * @return the fchEmision
	 */
	public Date getFchEmision() {
		return fchEmision;
	}


	/**
	 * @param fchEmision the fchEmision to set
	 */
	public void setFchEmision(Date fchEmision) {
		this.fchEmision = fchEmision;
	}


	/**
	 * @return the fchPago
	 */
	public Date getFchPago() {
		return fchPago;
	}


	/**
	 * @param fchPago the fchPago to set
	 */
	public void setFchPago(Date fchPago) {
		this.fchPago = fchPago;
	}


	/**
	 * @return the pagado
	 */
	public boolean pagar() {
		return pagado;
	}


	/**
	 * @param pagado the pagado to set
	 */
	public void pagar(boolean pagado) {
		this.pagado = pagado;
	}

}
