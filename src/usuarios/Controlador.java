package usuarios;

public class Controlador extends Usuario{
	
	public Controlador(String dni, String nombre, String password) {
		super(dni, nombre, password);
		super.setTipo("CONTROLADOR");
	}
}
