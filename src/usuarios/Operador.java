package usuarios;

public class Operador extends Usuario{
	
	public Operador(String dni, String nombre, String password) {
		super(dni, nombre, password);
		super.setTipo("OPERADOR");
	}
}
