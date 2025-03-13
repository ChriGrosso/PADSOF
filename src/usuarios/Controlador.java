package usuarios;

import java.util.ArrayList;

import vuelos.Vuelo;
import elementos.Terminal;

public class Controlador extends Usuario{
	private Terminal terminal;
	private ArrayList<Vuelo> vuelosASuCargo; 
	
	public Controlador(String dni, String nombre, String password, Terminal terminal, ArrayList<Vuelo> vuelos) {
		super(dni, nombre, password);
		super.setTipo("CONTROLADOR");
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
}
