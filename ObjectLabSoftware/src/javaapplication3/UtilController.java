/*
 *
 */
package javaapplication3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author nick
 */
public class UtilController 
{
    public static void rejectStudentSubmission (int row, String s, String s2, String s3)
    {
        new RejectDescription().rejectDesc(row, s, s2, s3); 
    }
    
     /**
      * gets row number of matching column in DefaultTableModel dm
      * @param dm the table model
      * @param column
      * @param selectedRow current row selected
      * @return 
      */
    public static int getSelectedRowNum(DefaultTableModel dm, int selectedRow, int column)
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
    
    private static void getColumnNames(ResultSet queryResult, Object[] columnNames)
    { 
        try 
        {
            /* Meta data contains number of columns and there names.
             * This is used to get entire rows of data in order to put into dataVector
             */
            ResultSetMetaData meta = queryResult.getMetaData();
            int columnCount = meta.getColumnCount();
            columnNames = new Object[columnCount];
            
            for(int column = 0; column < columnCount; column++)
            {
                columnNames[column] = meta.getColumnName(column);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
     /* This function is called to take the ResultSet return type form a DB query and re-format it into general
      * structures that make it easy for the View classes to display / modify.
      * Therefore the View classes would not have to know about the data and column names ect... 
      */
    public static boolean readyOutputForViewPage(ResultSet queryResult, Object[][] dataVector, Object[] columnNames)
    {
        /* This function takes the column names found in the queryResult and inserts them into columnNames */
        getColumnNames(queryResult, columnNames);
        /* Process data column by column and add that data into dataVector */
        try 
        {
            while (queryResult.next()) 
            {
                /* While a row is availabe for processing 
                   Process by column
                */
                for(int col = 0; col < columnNames.length - 1; col++)
                    dataVector[queryResult.getRow()][col] = queryResult.getString((String) columnNames[col]);
             }
        }
        catch (SQLException sqle)
        {
            System.out.println(sqle);
            return false;
        }
        return true;
    } 
    
    /* Returns true if updated
     * false if nothing to update
    */
    private boolean updatePendingTable() throws FileNotFoundException, IOException, SQLException
    {
        ResultSet results = dba.searchPending();
        
        if(!results.next())
            return false;
        
        while (results.next()) 
        {
            // Build a Vector of Strings for the table row
            List<String> data = new LinkedList<>();
            data.add(results.getString("fileName"));
            data.add(results.getString("firstName") + " " + results.getString("lastName"));
            data.add(results.getString("printer"));
            data.add(results.getString("dateStarted"));
            /* Data retrieved from query is added into our object that we can use to display in the JTable */
            this.allFileTableModel.addRow(data.toArray());
        }
        
        return true;
    }
     
    
}
