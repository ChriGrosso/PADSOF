package usuarios;
import java.time.LocalDate;

public class Notificacion {
	private String mensaje;
	private LocalDate  fchEmision;
	private Usuario emisor;
	
	public Notificacion(String mensaje, Usuario emisor) {
		this.mensaje = mensaje;
		this.emisor = emisor;
		this.fchEmision = LocalDate.now();
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	public LocalDate getfchEmision() {
		return this.fchEmision;
	}
	public Usuario getEmisor() {
		return this.emisor;
	}
	
}
