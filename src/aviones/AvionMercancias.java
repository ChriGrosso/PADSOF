package aviones;

/**
 * Clase que representa un avión diseñado para el transporte de mercancías. 
 * Hereda de la clase abstracta TipoAvion y define características específicas como la capacidad 
 * de carga y la posibilidad de transportar productos peligrosos.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class AvionMercancias extends TipoAvion{
	private static final long serialVersionUID = 1L;
	private double capacidad;
	private boolean productosPeligrosos;

	/**
     * Constructor de la clase AvionMercancias.
     *
     * @param marca               Marca del avión de mercancías.
     * @param modelo              Modelo del avión de mercancías.
     * @param autonomia           Autonomía del avión en kilómetros.
     * @param altura              Altura del avión en metros.
     * @param anchura             Anchura del avión en metros.
     * @param largo               Largo del avión en metros.
     * @param capacidad           Capacidad de carga del avión en toneladas.
     * @param productosPeligrosos Indica si el avión puede transportar productos peligrosos (true/false).
     */
	public AvionMercancias(String marca, String modelo, double autonomia, double altura, double anchura, double largo, double capacidad, boolean productosPeligrosos) {
		super(marca, modelo, autonomia, altura, anchura, largo);
		this.capacidad = capacidad;
		this.productosPeligrosos = productosPeligrosos;
	}

	/**
     * Obtiene la capacidad máxima de carga del avión de mercancías.
     *
     * @return Capacidad de carga del avión en toneladas.
     */
	@Override
	public double getCapacidad() {
		return this.capacidad;
	}
	
	/**
     * Verifica si el avión está autorizado para transportar productos peligrosos.
     *
     * @return True si el avión puede transportar productos peligrosos, False en caso contrario.
     */
	public boolean getProductosPeligrosos() {
		return this.productosPeligrosos;
	}

	/**
     * Verifica si el avión es de mercancías.
     *
     * @return True siempre, ya que esta clase representa un avión de mercancías.
     */
	@Override
	public boolean isMercancias() {
		return true;
	}
}
