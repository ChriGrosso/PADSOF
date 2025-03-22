package usuarios;

import java.util.ArrayList;

import aviones.EstadoAvion;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;
import elementos.Terminal;

public class Controlador extends Usuario{
	private Terminal terminal;
	private ArrayList<Vuelo> vuelosASuCargo; 
	
	public Controlador(String dni, String nombre, String password, Terminal terminal, ArrayList<Vuelo> vuelos) {
		super(dni, nombre, password);
		this.terminal = terminal;
		this.vuelosASuCargo = vuelos;
	}
	
	public Terminal getTerminal() {
		return this.terminal;
	}
	public ArrayList<Vuelo> getVuelosASuCargo() {
		return this.vuelosASuCargo;
	}
	
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
		return;
	}
	
	public Boolean asignarVuelo(Vuelo v) {
		this.vuelosASuCargo.add(v);
		return true;
	}
	
	public Boolean esGestor() {
		return false;
	}
	public Boolean esControlador() {
		return true;
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
