package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import aerolineas.Aerolinea;
import aviones.Avion;
import aviones.EstadoAvion;
import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Gestor;
import usuarios.Usuario;
import vuelos.EstadoVuelo;
import vuelos.Vuelo;

/**
 * Clase que controla la configuración de notificaciones de vuelos y aviones.
 * Permite a los usuarios seguir cambios de estado en vuelos y aviones, así como registrarse para recibir notificaciones.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ControlConfiguracionNotificaciones implements ActionListener {
	/**
     * Maneja eventos de acción generados en la interfaz.
     * 
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        ConfiguracionNotificaciones c = Aplicacion.getInstance().getConfiguracionNotificaciones();

        switch (comando) {
            case "CONFIRMAR_A":
            	this.confirmarA();
                break;
            case "CONFIRMAR_V":
            	this.confirmarV();
                break;
            case "SEGUIR_V":
            	this.seguirV();
                break;
            case "SEGUIR_A":
            	this.seguirA();
                break;

            default:
                break;
        }
        
        c.limpiarCampos();
        c.actualizarTablas();
    }
    
    /**
     * Confirma que el usuario desea seguir una transición específica de estados en aviones.
     */
    private void confirmarA() {
    	ConfiguracionNotificaciones vista = Aplicacion.getInstance().getConfiguracionNotificaciones();
        Usuario u = SkyManager.getInstance().getUsuarioActual();

        EstadoAvion desde = vista.getEstadoAvionInicio();
        EstadoAvion hasta = vista.getEstadoAvionFin();
        if (u.sigueCambioEstadoAvion(desde, hasta)) {
        	JOptionPane.showMessageDialog(null, "Ya sigues el cambio de estados: "+desde+" -> "+hasta);
        	return;
        }
        if (u.esGestor()) {
        	Gestor g = (Gestor) u;
        	g.seguirCamEstado(desde, hasta);
        	JOptionPane.showMessageDialog(null, "Transición de estado seguida correctamente");
        }
    }
    
    /**
     * Confirma que el usuario desea seguir una transición específica de estados en vuelos.
     */
    private void confirmarV() {
    	ConfiguracionNotificaciones vista = Aplicacion.getInstance().getConfiguracionNotificaciones();
        Usuario u = SkyManager.getInstance().getUsuarioActual();
    	
    	EstadoVuelo desde = vista.getEstadoVueloInicio();
	    EstadoVuelo hasta = vista.getEstadoVueloFin();
	    if (u.sigueCambioEstadoVuelo(desde, hasta)) {
        	JOptionPane.showMessageDialog(null, "Ya sigues el cambio de estados: "+desde+" -> "+hasta);
        	return;
        }
        if (u.esGestor()) {
        	Gestor g = (Gestor) u;
        	g.seguirCamEstado(desde, hasta);
        	JOptionPane.showMessageDialog(null, "Transición de estado seguida correctamente");
        }

    }
    
    /**
     * Permite al usuario seguir un avión específico.
     */
    private void seguirA() {
    	ConfiguracionNotificaciones c = Aplicacion.getInstance().getConfiguracionNotificaciones();
        Usuario u = SkyManager.getInstance().getUsuarioActual();
        Avion avion = null;
        
        String id_avion = c.getContenidoCampoAviones();
        if (id_avion.isBlank()) {
        	JOptionPane.showMessageDialog(null, "Debe introducir el identificador del avión que deseé seguir");
        	return;
        }
        
    	for (Aerolinea a: SkyManager.getInstance().getAerolineas().values()) {
    		avion = a.getAviones().get(id_avion);
    		if (avion != null) {
    			if (avion.comprobarObserver(u)) {
        			JOptionPane.showMessageDialog(null, "Ya sigues al avión "+id_avion);
    			} else {
					avion.addObserver(u);
					JOptionPane.showMessageDialog(null, "Avión "+id_avion+" seguido correctamente");
					break;
    			}
    		}
    	}
    	if (avion == null) {
    		JOptionPane.showMessageDialog(null, "No hay ningún avión registrado con identificador: "+id_avion);
    	}
    	
    }
    
    /**
     * Permite al usuario seguir un vuelo específico.
     */
    private void seguirV() {
    	ConfiguracionNotificaciones c = Aplicacion.getInstance().getConfiguracionNotificaciones();
        Usuario u = SkyManager.getInstance().getUsuarioActual();
        
        String id_vuelo = c.getContenidoCampoVuelos();
        if (id_vuelo.isBlank()) {
        	JOptionPane.showMessageDialog(null, "Debe introducir el identificador del vuelo que deseé seguir");
        	return;
        }
        Vuelo v = SkyManager.getInstance().getVuelos().get(id_vuelo);
        if (v == null) {
    		JOptionPane.showMessageDialog(null, "No hay ningún vuelo registrado con identificador: "+id_vuelo);
    	}
        else {
        	if (v.comprobarObserver(u)) {
    			JOptionPane.showMessageDialog(null, "Ya sigues el vuelo "+id_vuelo);
			} else {
				v.addObserver(u);
				JOptionPane.showMessageDialog(null, "Avión "+id_vuelo+" seguido correctamente");
			}
        }
    }

}
