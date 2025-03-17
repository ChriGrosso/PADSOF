package usuarios;

import java.util.ArrayList;

import notificaciones.Observable;
import notificaciones.Observer;

public abstract class Usuario implements Observer{
	private String dni;
	private String nombre;
	private String password;
	private String tipo;
	private ArrayList<String> notificaciones;
	
	public Usuario(String dni, String nombre, String password) {
		this.dni = dni;
		this.nombre = nombre;
		this.password = password;
		notificaciones = new ArrayList<String>();		
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
	
	@Override
    public void update(Observable o, String mensage) {
        this.notificaciones.add(mensage);
    }
	
	public void enviarNotificacion(String mensaje, Usuario u) {
		
		return;
	}
}
