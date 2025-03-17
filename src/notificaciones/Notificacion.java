package notificaciones;
import java.time.LocalDate;

import usuarios.Usuario;

public class Notificacion {
	private String mensaje;
	private LocalDate  fchEmision;
	private Usuario emisor;
	private Boolean leida;
	
	public Notificacion(String mensaje, Usuario emisor) {
		this.mensaje = mensaje;
		this.emisor = emisor;
		this.fchEmision = LocalDate.now();
		this.leida = false;
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
	public Boolean getLeida() {
		return this.leida;
	}
	public void setLeida(Boolean leida) {
		this.leida = leida;
		return;
	}
}
