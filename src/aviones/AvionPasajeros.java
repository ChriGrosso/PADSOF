package aviones;

public class AvionPasajeros extends TipoAvion {
	private static final long serialVersionUID = 1L;
	private int numPlazas;

	public AvionPasajeros(String marca, String modelo, double autonomia, double altura, double anchura, double largo, int numPlazas) {
		super(marca, modelo, autonomia, altura, anchura, largo);
		this.numPlazas = numPlazas;
	}

	public int getNumPlazas() {
		return this.numPlazas;
	}

	@Override
	public boolean isMercancias() {
		return false;
	}
}
