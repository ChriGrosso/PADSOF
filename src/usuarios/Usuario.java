package usuarios;

public abstract class Usuario {
	private String dni;
	private String nombre;
	private String password;
	private String tipo;
	
	public Usuario(String dni, String nombre, String password) {
		this.dni = dni;
		this.nombre = nombre;
		this.password = password;
	}
	
	public String getDni() {
		return this.dni;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getPassword() {
		return this.password;
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setPassword(String p) {
		this.password = p;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
		return ""+ this.nombre + " dni: "+this.dni+" , contraseña: "+ this.password + " ("+ this.tipo+")\n";
	}
	
	public void enviarNotificacion(String mensaje, Usuario u) {
		
		return;
	}
}
