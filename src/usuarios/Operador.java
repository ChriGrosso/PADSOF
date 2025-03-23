package usuarios;

import aerolineas.Aerolinea;

public class Operador extends Usuario{
	private static final long serialVersionUID = 1L;
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
}
