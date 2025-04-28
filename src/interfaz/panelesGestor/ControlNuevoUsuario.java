package interfaz.panelesGestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import interfaz.Aplicacion;
import sistema.SkyManager;
import usuarios.Controlador;
import usuarios.Operador;
import usuarios.Usuario;

public class ControlNuevoUsuario implements ActionListener {
	private SkyManager modelo;

    public ControlNuevoUsuario() {
        this.modelo = SkyManager.getInstance();
    }

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
    
    private boolean esDniValido(String dni) {
        return dni.matches("^\\d{8}[A-Z]$");
    }

    
}