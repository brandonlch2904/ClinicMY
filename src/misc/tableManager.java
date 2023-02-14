package misc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class tableManager {
    public static String[] getDataFromTable(JTable table, int column) {

        String details = "";
        String detailsArray[];
        // Get the selected row
        int row = table.getSelectedRow();
        // Get the value of the selected row
        for (int i = 0; i < column; i++) {
            details += table.getModel().getValueAt(row, i).toString() + ",";
        }

        // Split the string into an array
        detailsArray = details.split(",");

        return detailsArray;
    }

    public static void setDataInTable(String[] Array, String[] columnNames, String[][] datas, JTable Table) {
        for (int i = 0; i < Array.length; i++) {
            String[] data = Array[i].split(",");
            for (int j = 0; j < data.length; j++) {
                datas[i][j] = data[j];
            }
        }
        Table.setModel(new DefaultTableModel(datas, columnNames));
    }
}
