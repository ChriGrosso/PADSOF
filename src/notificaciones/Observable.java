package notificaciones;

import java.util.Collection;
import java.util.HashMap;

import usuarios.Usuario;

public class Observable {
	private HashMap<String, Usuario> observers;
	
	public Observable() {
		observers = new HashMap<String, Usuario>();
	}
	
	public void addObserver(Usuario user) {
		if (observers.containsKey(user.getDni()) == false) {
			observers.put(user.getDni(), user);
		}
		return;
	}
	public void deleteObserver(Usuario user) {
		if (observers.containsKey(user.getDni()) == true) {
			observers.remove(user.getDni());
		}
		return;
	}
	public void notifyObserver(Usuario u, String mensaje) {
		if (this.observers.containsKey(u.getDni())== true) {
			u.update(this, mensaje);
		}
		return;
	}
	public void notifyObservers(String mensaje) {
		Collection<Usuario> usuarios = observers.values();
		
		for (Usuario u: usuarios) {
			u.update(this, mensaje);
		}
		return;
	}
	
}
