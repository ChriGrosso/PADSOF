package interfaz.panelesGestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import interfaz.Aplicacion;
import interfaz.Notificaciones;
import sistema.SkyManager;

public class GestorNotificaciones extends Notificaciones {
	private static final long serialVersionUID = 1L;
	
	JButton botonConfiguracion;
	
	public GestorNotificaciones() {
		super();
		
		// Panel inferior para boton de configurar
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new BorderLayout());
	    panelInferior.setBackground(new Color(173, 216, 230));
	    panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		  
		//Añadir los botones
	    // Botón "Nuevo Usuario" centrado
		botonConfiguracion = new JButton("Configurar Notificaciones");
		this.formatoBotones(botonConfiguracion);
		
	    panelInferior.add(botonConfiguracion, BorderLayout.CENTER);
		super.addPanelConfiguracionGestor(panelInferior);
		
	}
	
	void formatoBotones(JButton boton) {
		boton.setForeground(Color.WHITE);
	    boton.setBackground(new Color(70, 130, 180)); 
	    boton.setFocusPainted(false);
	    boton.setFont(new Font("SansSerif", Font.BOLD, 11));
	}
	
	@Override
	public void paginaAnterior() {
		SkyManager.getInstance().guardarDatos();
		Aplicacion.getInstance().showGestorInicio();
	}
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		botonConfiguracion.setActionCommand("CONFIGURAR");
		
		botonConfiguracion.addActionListener(c);
	}

}
