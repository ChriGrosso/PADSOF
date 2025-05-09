package interfaz;

import java.awt.EventQueue;

/**
 * Clase que inicia la aplicación y asegura que la interfaz gráfica se ejecute en el hilo de eventos de Swing.
 * 
 */
public class Prueba {
	/**
     * Método principal que lanza la aplicación dentro del hilo de eventos de Swing (`EventQueue`).
     * 
     * @param args Argumentos pasados por la línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			 /**
             * Método que ejecuta la inicialización de la aplicación.
             * Captura posibles excepciones para evitar errores en el arranque.
             */
			public void run() {
				try {						
					Aplicacion.getInstance().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
