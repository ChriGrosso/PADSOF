package elementos;
import java.time.*;

public class Pista extends ElementoEstructural{
	
	private boolean despegue;
	private int longitud;
	
	public Pista(String id, double costePorHora, LocalDateTime fchRegistro, boolean despegue, int longitud) {
		super(id,costePorHora,fchRegistro);
		this.setDespegue(despegue);
		this.setLongitud(longitud);
	}
	
	public boolean enUso() {
		return false;
	}

	/**
	 * @return the despegue
	 */
	public boolean isDespegue() {
		return despegue;
	}

	/**
	 * @param despegue the despegue to set
	 */
	public void setDespegue(boolean despegue) {
		this.despegue = despegue;
	}

	/**
	 * @return the longitud
	 */
	public int getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	
}
