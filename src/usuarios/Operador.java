package usuarios;

import aerolineas.Aerolinea;
import aviones.EstadoAvion;
import vuelos.EstadoVuelo;

public class Operador extends Usuario{
	private Aerolinea aerolinea;
	
	public Operador(String dni, String nombre, String password, Aerolinea aerolinea) {
		super(dni, nombre, password);
		this.aerolinea = aerolinea;
	}
	
	public Aerolinea getAerolinea() {
		return this.aerolinea;
	}
	

	public Boolean esGestor() {
		return false;
	}
	public Boolean esControlador() {
		return false;
	}
	public Boolean esOperador() {
		return true;
	}

	@Override
	public Boolean sigueCambioEstadoVuelo(EstadoVuelo estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sigueCambioEstadoAvion(EstadoAvion estado) {
		// TODO Auto-generated method stub
		return null;
	}
}
