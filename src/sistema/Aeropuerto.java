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
	
	public String getNombre() {
		return this.nombre;
	}
	public String getCodigo() {
		return this.codigo;
	}
	public String getCiudadMasCercana() {
		return this.ciudadMasCercana;
	}
	public String getPais() {
		return this.pais;
	}
	public double getDistancia() {
		return this.distancia;
	}
	public int getZonaHorario() {
		return this.zonaHorario;
	}
	public String getHorarioOperativo() {
		return this.horarioOperativo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
		return;
	}
	public void setCiudadMasCercana(String ciudad) {
		this.ciudadMasCercana = ciudad;
		return;
	}
	public void setPais(String pais) {
		this.pais = pais;
		return;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
		return;
	}
	public void setZonaHorario(int zonaHorario) {
		this.zonaHorario = zonaHorario;
		return;
	}
	public void setHorarioOperativo(String horarioOperativo) {
		this.horarioOperativo = horarioOperativo;
		return;
	}
	
}
