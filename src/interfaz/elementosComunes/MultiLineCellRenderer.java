package interfaz.elementosComunes;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true); // Necesario para que el fondo se pinte bien
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText(value != null ? value.toString() : "");

        // Ajusta el alto de la fila para que se vea todo el contenido
        int alturaRecomendada = getPreferredSize().height;
        if (table.getRowHeight(row) < alturaRecomendada) {
            table.setRowHeight(row, alturaRecomendada);
        }

        // Manejo de selección de celdas
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
