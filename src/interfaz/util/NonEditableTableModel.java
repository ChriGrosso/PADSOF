package interfaz.util;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
