package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Controlador;
import usuarios.Operador;
import usuarios.Usuario;

/**
 * Clase que gestiona la creación de nuevos usuarios en el sistema.
 * Implementa ActionListener para manejar los eventos de la interfaz de registro de usuarios.
 * 
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 */
public class ControlNuevoUsuario implements ActionListener {
	private SkyManager modelo;
	
	/**
     * Constructor de la clase ControlNuevoUsuario.
     * Inicializa el modelo del sistema (SkyManager) para gestionar el registro de usuarios.
     */
    public ControlNuevoUsuario() {
        this.modelo = SkyManager.getInstance();
    }
    
    /**
     * Maneja los eventos de acción generados en la interfaz de registro de usuarios.
     * 
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        NuevoUsuario nu = Aplicacion.getInstance().getNuevoUsuario();

        switch (comando) {
            case "ACEPTAR":
                aceptar();
                break;

            case "CANCELAR":
            	nu.limpiarCampos();
            	Aplicacion.getInstance().showGestorGestionUsuarios();
                break;

            case "OPERADOR":
            	nu.mostrarAerolineas();
                break;
            case "CONTROLADOR":
	            nu.mostrarTerminales();
                break;    
			
            default:
                System.out.println("Comando desconocido:  " + comando);
        }
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * Valida los datos ingresados antes de proceder con el registro.
     */
    private void aceptar() {
    	NuevoUsuario nu = Aplicacion.getInstance().getNuevoUsuario();
    	GestorGestionUsuarios gest = Aplicacion.getInstance().getGestorGestionUsuarios();
    	Usuario nuevo;
    	String dni = nu.getDniUsuario();
    	String nombre = nu.getNombreUsuario();
    	String contrasena = nu.getPasswordUsuario();
    	String aerolinea = nu.getAerolineaSeleccionada();
    	String terminal = nu.getTerminalSeleccionada();
    	
    	if (dni.isBlank() || nombre.isBlank() || contrasena.isBlank() /*|| (aerolinea.isBlank() && terminal.isBlank())*/) {
    		JOptionPane.showMessageDialog(null, "Todos los campos deben estar completados para poder añadir un usuario");
    		return;
    	}
    	else if (!esDniValido(dni)) {
    		JOptionPane.showMessageDialog(null, "El DNI introducido no cumple el formato correcto");
    		return;
    	}
    	else if (modelo.getUsuarios().containsKey(dni)) {
    		JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese DNI");
    		return;
    	}
    	
    	if (nu.esControladorSeleccionado()) {
    		nuevo = new Controlador(dni, nombre, contrasena, modelo.getTerminales().get(terminal));
    	} else { 
    		String id = aerolinea.split(" - ")[0];
    		nuevo = new Operador(dni, nombre, contrasena, modelo.getAerolineas().get(id)); 
    	}
    	
    	modelo.registrarUsuario(nuevo);
     	modelo.guardarDatos();
     	gest.addFila(nuevo);
     	
        JOptionPane.showMessageDialog(null, "Usuario añadido correctamente");
        nu.limpiarCampos();
    }
    
    /**
     * Verifica si el formato del DNI ingresado es válido.
     * 
     * @param dni DNI a validar.
     * @return `true` si el DNI tiene un formato correcto, `false` en caso contrario.
     */
    private boolean esDniValido(String dni) {
        return dni.matches("^\\d{8}[A-Z]$");
    }

    
}