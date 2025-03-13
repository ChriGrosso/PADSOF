package vuelos;

public enum Periodicidad {
	NO_PERIODICO(0), 
	DIARIO(1), 
	DIAS_ALTERNOS(2), 
	SEMANAL(7), 
	MENSUAL(30);
	
	private Periodicidad (int p) {periodo = p;}
	
	private final int periodo;
	
	public int valor() {return this.periodo;}
}
