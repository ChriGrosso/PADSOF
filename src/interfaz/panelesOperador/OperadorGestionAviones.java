package interfaz.panelesOperador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import aerolineas.Aerolinea;
import aviones.Avion;
import sistema.SkyManager;
import usuarios.Operador;

public class OperadorGestionAviones extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton nuevoAvion, nuevoTipoAv;
	private JTable tablaAviones;
	
	public OperadorGestionAviones() {
		// Configurar el Layout
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes

        // Título
        JLabel titulo = new JLabel("Gestión de Aviones");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Ocupa 3 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        // Tabla
        tablaAviones = new JTable(); // La tabla se actualizará dinámicamente con los datos
        JScrollPane scrollPane = new JScrollPane(tablaAviones);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Ocupa todo el ancho
        gbc.fill = GridBagConstraints.BOTH; // Expandir horizontal y verticalmente
        gbc.weightx = 1.0; // Permitir expansión horizontal
        gbc.weighty = 1.0; // Permitir expansión vertical
        add(scrollPane, gbc);

        // Botones debajo de la tabla
        gbc.gridwidth = 1; // Cada botón ocupa una columna
        gbc.fill = GridBagConstraints.NONE; // Tamaño por defecto
        gbc.weightx = 0; // No expandir botones horizontalmente
        gbc.weighty = 0; // No expandir botones verticalmente

        nuevoAvion = new JButton("Nuevo Avión");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nuevoAvion, gbc);

        nuevoTipoAv = new JButton("Nuevo Tipo Avión");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(nuevoTipoAv, gbc);
         
        
	}
	
	
	public void actualizarPantalla() {
		// Colocar los nombres de las columnas de los aviones
		String [] columnas = {"Matrícula", "Fecha de Compra", "Última Revisión", "Modelo", "Estado"};
		// Recoger los datos de los aviones
		Operador op = (Operador) SkyManager.getInstance().getUsuarioActual();
		Aerolinea a = op.getAerolinea();
		Collection<Avion> aviones = a.getAviones().values();
		String [][] datos = new String[aviones.size()][5];
		int i = 0;
		// Rellenar la matriz con los datos de los aviones
		for (Avion avion: aviones) {
		    datos[i][0] = avion.getMatricula();         
		    datos[i][1] = String.valueOf(avion.getFechaCompra());   
		    datos[i][2] = String.valueOf(avion.getFechaUltimaRevision());  
		    datos[i][3] = avion.getTipoAvion().getMarca() + " " + avion.getTipoAvion().getModelo();
		    datos[i][4] = String.valueOf(avion.getEstadoAvion());     
		    i++;
		}
		// Actualizar contenido de la tabla con nuevos datos
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        tablaAviones.setModel(model);
        model.fireTableDataChanged();
	}
	
	// método para asignar un controlador a los botones
	public void setControlador(ActionListener c) {  
		nuevoAvion.addActionListener(c);
		nuevoTipoAv.addActionListener(c);
	}
}
