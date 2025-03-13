package usuarios;

import aerolineas.Aerolinea;

public class Operador extends Usuario{
	private Aerolinea aerolinea;
	
	public Operador(String dni, String nombre, String password, Aerolinea aerolinea) {
		super(dni, nombre, password);
		super.setTipo("OPERADOR");
		this.aerolinea = aerolinea;
	}
	
	public Aerolinea getAerolinea() {
		return this.aerolinea;
	}
}
