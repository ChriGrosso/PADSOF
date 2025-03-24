package usuarios;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import aviones.EstadoAvion;
import vuelos.EstadoVuelo;

/**
 * Clase Gestor, que representa a un usuario con permisos de gestión en el sistema.
 * 
 * Un gestor puede seguir y dejar de seguir cambios de estado en aviones y vuelos,
 * y es notificado cuando ocurren estos cambios.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es 
 */
public class Gestor extends Usuario {
	private static final long serialVersionUID = 1L;
	private List<Map.Entry<EstadoAvion, EstadoAvion>> cambiosEstadoAvion;
	private List<Map.Entry<EstadoVuelo, EstadoVuelo>> cambiosEstadoVuelo;
	
	/**
     * Constructor de la clase Gestor.
     *
     * @param dni      Documento de identidad del gestor.
     * @param nombre   Nombre del gestor.
     * @param password Contraseña del gestor.
     */
	public Gestor(String dni, String nombre, String password) {
		super(dni, nombre, password);
		this.cambiosEstadoAvion = new ArrayList<>();
		this.cambiosEstadoVuelo = new ArrayList<>();
	}
	
	/**
     * Indica que este usuario es un gestor.
     * @return true siempre, ya que esta clase representa un gestor.
     */
	public Boolean esGestor() {
		return true;
	}
	
	/**
     * Indica que este usuario no es un controlador.
     * @return false siempre, ya que esta clase representa un gestor.
     */
	public Boolean esControlador() {
		return false;
	}
	
	/**
     * Indica que este usuario no es un operador.
     * @return false siempre, ya que esta clase representa un gestor.
     */
	public Boolean esOperador() {
		return false;
	}
	
	/**
     * Registra un cambio de estado de vuelo a seguir por el gestor.
     *
     * @param anterior Estado anterior del vuelo.
     * @param nuevo    Estado nuevo del vuelo.
     */
	public void seguirCamEstado(EstadoVuelo anterior, EstadoVuelo nuevo){
		this.cambiosEstadoVuelo.add(new AbstractMap.SimpleEntry<>(anterior, nuevo));
	}
	
	 /**
     * Registra un cambio de estado de avión a seguir por el gestor.
     *
     * @param anterior Estado anterior del avión.
     * @param nuevo    Estado nuevo del avión.
     */
	public void seguirCamEstado(EstadoAvion anterior, EstadoAvion nuevo){
		this.cambiosEstadoAvion.add(new AbstractMap.SimpleEntry<>(anterior, nuevo));
	}
	
	/**
     * Deja de seguir un cambio de estado de vuelo específico.
     *
     * @param anterior Estado anterior del vuelo.
     * @param nuevo    Estado nuevo del vuelo.
     */
	public void dejarDeSeguirCamEstado(EstadoVuelo anterior, EstadoVuelo nuevo){
		this.cambiosEstadoVuelo.removeIf(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
	}
	
	/**
     * Deja de seguir un cambio de estado de avión específico.
     *
     * @param anterior Estado anterior del avión.
     * @param nuevo    Estado nuevo del avión.
     */
	public void dejarDeSeguirCamEstado(EstadoAvion anterior, EstadoAvion nuevo){
		this.cambiosEstadoAvion.removeIf(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
	}
	
	/**
     * Determina si el gestor sigue un cambio de estado de un vuelo.
     *
     * @param anterior Estado anterior del vuelo.
     * @param nuevo    Estado nuevo del vuelo.
     * @return true si el gestor sigue este cambio de estado, false en caso contrario.
     */
	@Override
	public Boolean sigueCambioEstadoVuelo(EstadoVuelo anterior, EstadoVuelo nuevo) {
		return cambiosEstadoVuelo.stream()
                .anyMatch(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
    }
	
	/**
     * Determina si el gestor sigue un cambio de estado de un avión.
     *
     * @param anterior Estado anterior del avión.
     * @param nuevo    Estado nuevo del avión.
     * @return true si el gestor sigue este cambio de estado, false en caso contrario.
     */
	@Override
	public Boolean sigueCambioEstadoAvion(EstadoAvion anterior, EstadoAvion nuevo) {
		return cambiosEstadoAvion.stream()
                .anyMatch(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
    }	
}
