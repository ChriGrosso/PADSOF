package aviones;

public class AvionMercancias extends TipoAvion{
	private double capacidad;
	private boolean productosPeligrosos;

	public AvionMercancias(String marca, String modelo, double autonomia, double altura, double anchura, double largo, double capacidad, boolean productosPeligrosos) {
		super(marca, modelo, autonomia, altura, anchura, largo);
		this.capacidad = capacidad;
		this.productosPeligrosos = productosPeligrosos;
	}

	public double getCapacidad() {
		return this.capacidad;
	}
	public boolean getProductosPeligrosos() {
		return this.productosPeligrosos;
	}
}
