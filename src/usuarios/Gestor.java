package usuarios;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import aviones.EstadoAvion;
import vuelos.EstadoVuelo;

public class Gestor extends Usuario {
	private static final long serialVersionUID = 1L;
	private List<Map.Entry<EstadoAvion, EstadoAvion>> cambiosEstadoAvion;
	private List<Map.Entry<EstadoVuelo, EstadoVuelo>> cambiosEstadoVuelo;

	public Gestor(String dni, String nombre, String password) {
		super(dni, nombre, password);
		this.cambiosEstadoAvion = new ArrayList<>();
		this.cambiosEstadoVuelo = new ArrayList<>();
	}
	
	public Boolean esGestor() {
		return true;
	}
	public Boolean esControlador() {
		return false;
	}
	public Boolean esOperador() {
		return false;
	}
	
	public void seguirCamEstado(EstadoVuelo anterior, EstadoVuelo nuevo){
		this.cambiosEstadoVuelo.add(new AbstractMap.SimpleEntry<>(anterior, nuevo));
		
	}
	public void seguirCamEstado(EstadoAvion anterior, EstadoAvion nuevo){
		this.cambiosEstadoAvion.add(new AbstractMap.SimpleEntry<>(anterior, nuevo));
	}
	
	public void dejarDeSeguirCamEstado(EstadoVuelo anterior, EstadoVuelo nuevo){
		this.cambiosEstadoVuelo.removeIf(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
	}
	public void dejarDeSeguirCamEstado(EstadoAvion anterior, EstadoAvion nuevo){
		this.cambiosEstadoAvion.removeIf(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
	}

	@Override
	public Boolean sigueCambioEstadoVuelo(EstadoVuelo anterior, EstadoVuelo nuevo) {
		return cambiosEstadoVuelo.stream()
                .anyMatch(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
    }

	@Override
	public Boolean sigueCambioEstadoAvion(EstadoAvion anterior, EstadoAvion nuevo) {
		return cambiosEstadoAvion.stream()
                .anyMatch(c -> c.getKey().equals(anterior) && c.getValue().equals(nuevo));
    }	
}
