package usuarios;

public class Gestor extends Usuario {
	
	public Gestor(String dni, String nombre, String password) {
		super(dni, nombre, password);
		super.setTipo("GESTOR");
	}

}
