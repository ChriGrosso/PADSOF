package usuarios;

import aviones.EstadoAvion;
import vuelos.EstadoVuelo;

public class Gestor extends Usuario {

	public Gestor(String dni, String nombre, String password) {
		super(dni, nombre, password);
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

	@Override
	public Boolean sigueCambioEstadoVuelo(EstadoVuelo estado) {
		return null;
	}

	@Override
	public Boolean sigueCambioEstadoAvion(EstadoAvion estado) {
		return null;
	}
	
	
}
