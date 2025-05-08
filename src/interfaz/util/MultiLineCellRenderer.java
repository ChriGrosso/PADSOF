package interfaz.util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * Clase MultiLineCellRenderer - Renderizador de celdas para tablas que permite mostrar
 * texto en múltiples líneas dentro de una celda.
 *
 * Utiliza un JTextArea como componente de renderizado, ajustando dinámicamente
 * la altura de las filas para mostrar todo el contenido del texto.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de MultiLineCellRenderer.
     * Configura el JTextArea para permitir el ajuste de línea y renderizado de fondo.
     */
    public MultiLineCellRenderer() {
        setLineWrap(true); // Permite que el texto se divida en líneas
        setWrapStyleWord(true); // Rompe por palabras completas
        setOpaque(true); // Necesario para que el fondo de la celda se pinte correctamente
    }

    /**
     * Devuelve el componente que se usará para renderizar la celda especificada de la tabla.
     * Ajusta el texto, la altura de la fila y los colores de selección si corresponde.
     *
     * @param table La tabla que contiene la celda.
     * @param value El valor que se va a representar en la celda.
     * @param isSelected Indica si la celda está seleccionada.
     * @param hasFocus Indica si la celda tiene el foco del teclado.
     * @param row Fila de la celda que se va a renderizar.
     * @param column Columna de la celda que se va a renderizar.
     * @return El componente preparado para renderizar la celda.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText(value != null ? value.toString() : "");

        // Ajusta la altura de la fila para que el contenido sea completamente visible
        int alturaRecomendada = getPreferredSize().height;
        if (table.getRowHeight(row) < alturaRecomendada) {
            table.setRowHeight(row, alturaRecomendada);
        }

        // Establece los colores en función del estado de selección
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }
}
