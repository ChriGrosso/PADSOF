package sistema;

public class Aeropuerto {
	private String nombre;
	private String codigo;
	private String ciudadMasCercana;
	private String pais;
	private double distancia;
	private int zonaHorario;
	private String horarioOperativo;
	
	public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double distancia, int zonaHorario, String horarioOperativo) {
		this.nombre= nombre;
		this.codigo = codigo;
		this.ciudadMasCercana = ciudad;
		this.pais = pais;
		this.distancia = distancia;
		this.zonaHorario = zonaHorario;
		this.horarioOperativo = horarioOperativo;
	}
	
}
