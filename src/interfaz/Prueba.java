package interfaz;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

public class Prueba {
	public static int main(String[] args) {
		// Crear ventana para toda la interfaz
			JFrame ventana = new JFrame("Sky Manager");
			
		// Obtener contenedor de la ventana para settear layout
		Container contenedor = ventana.getContentPane();
		contenedor.setLayout(new SpringLayout());
		return 0;
	}
}


