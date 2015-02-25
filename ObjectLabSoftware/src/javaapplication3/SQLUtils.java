/*
 * Any SQL queries will be called here
 * From there the UtilController class will 
 * Decide whether or not to call readyOutputForViewPage()
 * 
 */
package javaapplication3;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author nick
 */
public class SQLUtils 
{
    /* Called by UtilController to format the output for the UI */
    public static boolean readyOutputForViewPage(ResultSet queryResult, Object[][] dataVector, Object[] columnNames)
    {
        try 
        {
            /* Meta data contains number of columns and there names.
             * This is used to get entire rows of data in order to put into dataVector
             */
            ResultSetMetaData meta = queryResult.getMetaData();
            columnNames = new Object[meta.getColumnCount()];
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(SQLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    
}
