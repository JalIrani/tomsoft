/*
 *
 */
package javaapplication3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author nick
 */
public class UtilController 
{
    public static void rejectStudentSubmission(String file, String fName, String lName, String dateOfSubmission)
    {
        new RejectDescription().rejectDesc(file, fName, lName, dateOfSubmission); 
    }
    
    public static void approveStudentSubmission(String fileName, String firstName, String lastName, String printer, String dateStarted)
    {
        ResultSet result = PendingJobsView.dba.searchID("pendingjobs", firstName, lastName, fileName, dateStarted);
        String fileLocation = "", ID = "";
            
        try 
        {
            while (result.next()) 
            {
                ID = result.getString("idJobs");
                System.out.println("ID: " + ID);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        ResultSet res = PendingJobsView.dba.searchPendingWithID(ID);
            
        try 
        {
            while (res.next()) 
                fileLocation = res.getString("filePath");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!fileLocation.equals(""))
        {
            ApprovePage apage = new ApprovePage();
            apage.approveSubmission(fileName, printer, fileLocation, ID); 
        }
        else
        {
            System.out.println("ERROR MSG: File path not found");
            System.exit(1);
        }
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
        if (selectedRow < 0)
            return -1;
        
        for (int i = 0; i < dm.getRowCount(); i++)
            if (dm.getValueAt(i, column).equals(dm.getValueAt(selectedRow, column)))
                return i;
        
        return -1;
    }
    
    /* This function takes the column names found in the queryResult and inserts them into columnNames */
    private static ArrayList<String> getColumnNames(ResultSet queryResult)
    { 
        ArrayList<String> columnNames = new ArrayList<>();
        
        try 
        {
            /* Meta data contains number of columns and there names.
             * This is used to get entire rows of data in order to put into dataVector
             */
            ResultSetMetaData meta = queryResult.getMetaData();
            int columnCount = meta.getColumnCount();
            
            for(int column = 1; column <= columnCount; column++)
                columnNames.add(meta.getColumnName(column));
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE, null, ex);
            return columnNames;
        }
        
        return columnNames;
        
    }
    
     /* This function is called to take the query result "ResultSet" return type form a DB query and re-format it into 
      * general structures that make it easy for the View classes to display / modify.
      * Therefore the View classes would not have to know about the data and column names ect... 
      */
    private static ArrayList<ArrayList<String>> readyOutputForViewPage(ResultSet queryResult)
    {
        ArrayList<String> columnNames = getColumnNames(queryResult);
        ArrayList<ArrayList<String>> retval = new ArrayList<>();
        
        /* Process data column by column and add that data into dataVector */
        try 
        {  
            ArrayList<String> tempRow;
            
            /* Goes through ResultSet row by row adding the row data into retval as an arraylist of type string */
            while (queryResult.next()) 
            {
                tempRow = new ArrayList<>();
                for (String columnName : columnNames) 
                    tempRow.add(queryResult.getString((String) columnName));
                
                retval.add(tempRow);
            }
        }
        catch (SQLException sqle)
        {
            System.out.println(sqle);
            return retval;
        }
        return retval;
    }
    
    public static void updatePendingTableData(DefaultTableModel dataHolder)
    {     
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.searchPending();
        
        ArrayList<ArrayList<String>> retval = readyOutputForViewPage(queryResult);
        
        for (ArrayList<String> retval1 : retval) 
            dataHolder.addRow(retval1.toArray());
    }
     
    
}
