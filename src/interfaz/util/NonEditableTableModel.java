package interfaz.util;

import javax.swing.table.DefaultTableModel;

/**
 * Clase NonEditableTableModel - Modelo de tabla personalizado en el que ninguna celda es editable.
 * 
 * Se utiliza para crear tablas que muestran datos sin permitir su modificación directa
 * por parte del usuario desde la interfaz gráfica.
 * 
 * Extiende {@link DefaultTableModel} y sobrescribe el método {@code isCellEditable}.
 * 
 * @author Christian Grosso - christian.grosso@estudiante.uam.es
 */
public class NonEditableTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que inicializa el modelo con nombres de columnas y número de filas.
     *
     * @param columnNames Array con los nombres de las columnas.
     * @param rowCount Número inicial de filas.
     */
    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    /**
     * Constructor que inicializa el modelo con datos y nombres de columnas.
     *
     * @param data Datos iniciales de la tabla.
     * @param columnNames Nombres de las columnas.
     */
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    /**
     * Indica si una celda específica es editable.
     * Este modelo siempre retorna {@code false}, haciendo todas las celdas no editables.
     *
     * @param row Índice de la fila.
     * @param column Índice de la columna.
     * @return {@code false}, indicando que la celda no se puede editar.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
