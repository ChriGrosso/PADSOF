package aviones;

/**
 * Clase que representa un avión diseñado para el transporte de pasajeros.
 * Hereda de la clase abstracta TipoAvion y define una característica específica: el número de plazas 
 * disponibles para pasajeros.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 */
public class AvionPasajeros extends TipoAvion {
	private static final long serialVersionUID = 1L;
	private int numPlazas;

	/**
     * Constructor de la clase AvionPasajeros.
     *
     * @param marca       Marca del avión de pasajeros.
     * @param modelo      Modelo del avión de pasajeros.
     * @param autonomia   Autonomía del avión en kilómetros.
     * @param altura      Altura del avión en metros.
     * @param anchura     Anchura del avión en metros.
     * @param largo       Largo del avión en metros.
     * @param numPlazas   Número de plazas disponibles para pasajeros.
     */
	public AvionPasajeros(String marca, String modelo, double autonomia, double altura, double anchura, double largo, int numPlazas) {
		super(marca, modelo, autonomia, altura, anchura, largo);
		this.numPlazas = numPlazas;
	}

	/**
     * Obtiene el número de plazas disponibles para pasajeros en el avión.
     *
     * @return Número de plazas en el avión de pasajeros.
     */
	public int getNumPlazas() {
		return this.numPlazas;
	}

	/**
     * Verifica si el avión es de mercancías.
     *
     * @return False siempre, ya que esta clase representa un avión de pasajeros.
     */
	@Override
	public boolean isMercancias() {
		return false;
	}

	/**
     * Obtiene la capacidad del avión en términos de número de pasajeros.
     *
     * @return Capacidad del avión, representada por el número de plazas para pasajeros.
     */
	@Override
	public double getCapacidad() {
		return this.numPlazas;
	}
}
