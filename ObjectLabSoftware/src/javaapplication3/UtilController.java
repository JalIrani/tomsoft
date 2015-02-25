/*
 *
 */
package javaapplication3;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author nick
 */
public class UtilController 
{
     /**
      * gets row number of matching column in dm
      * @param dm the table model
      * @param column
      * @param selectedRow current row selected
      * @return 
      */
    public static int getSelectedRowNum(DefaultTableModel dm, int column, int selectedRow)
    {
        for (int i = 0; i < dm.getRowCount(); i++)
        {
            if (dm.getValueAt(i, column).equals(dm.getValueAt(selectedRow, column)))
            {
                return i;
            }
        }
        return -1;
    }
    
}
