/*
 *
 */
package ObjectLabEnterpriseSoftware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static org.apache.commons.io.FileUtils.directoryContains;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 *
 * @author nick
 */
public class UtilController 
{
    private static final boolean SUCCESS = true;
    private static final boolean FAILURE = false;
    
    public static String[] getReportColumnHeaders(int reportID){
        try {
            SQLMethods dbconn = new SQLMethods();
            ResultSet queryResult = dbconn.getReport();
            /* Must process results found in ResultSet before the connection is closed! */
            
            ResultSetMetaData rsmd = queryResult.getMetaData();
            String[] headers = new String[rsmd.getColumnCount()];
            //System.out.println(rsmd.getColumnName(5));
            for(int i = 1; i <= rsmd.getColumnCount();i++){
                headers[i-1] = rsmd.getColumnName(i);
            }
            
            dbconn.closeDBConnection();
            return headers;
        } catch (SQLException ex) {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ArrayList<ArrayList<String>> updateReportTableData() 
    {     
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.getReport();
        
        ArrayList<ArrayList<String>> retval = readyOutputForViewPage(queryResult);
        
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();
        
        return retval;
        
    }
    
    public static ArrayList<ArrayList<String>> updateReportTableData(String column, String value) 
    {     
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.getReport(column, value);
        
        ArrayList<ArrayList<String>> retval = readyOutputForViewPage(queryResult);
        
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();
        
        return retval;
    }
    
    public void exportReportToFile(DefaultTableModel model, String[] header){
    
        FileUtils fileManager = new FileUtils();
        
        Workbook wb = new HSSFWorkbook();
        //TODO: pick better sheet name
        Sheet sheet = wb.createSheet("new sheet");
        Row row = null;
        Cell cell = null;
 
        for (int i = 0; i < model.getRowCount()+1; i++) 
        {
            row = sheet.createRow(i);
            if(i == 0){
               for(int j = 0; j < header.length; j++){
                   cell = row.createCell(j);
                   cell.setCellValue(header[j]);
               }
            }
            else
            {
                for (int j = 0; j < model.getColumnCount(); j++) 
                {
                    cell = row.createCell(j);
                    cell.setCellValue((String) model.getValueAt(i-1, j));
                }
            }
        }
        
        boolean didSave = fileManager.saveReport("ReportName", wb);

        if(didSave){
            JOptionPane.showMessageDialog(new JFrame(), "Succesfully Exported File");
        }
        else{
            JOptionPane.showMessageDialog(new JFrame(), "Unable To Exported File");
        }
        
    }
    
    public static boolean rejectStudentSubmission(String file, String fName, String lName, String dateOfSubmission, String reasonForRejection)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet results = dbconn.searchID("pendingjobs", fName, lName, file, dateOfSubmission);
        InstanceCall cloudStorageOperations = new InstanceCall();
        
        
        
        try 
        {
            if (results.next()) 
            {
                /*
                   This checks to see if the file exists in the rejected location if it does the rejection will fail
                    and the file that is in the reject location will be deleted. *hot fix for bug*
                   If the file does not exist it is moved into the rejected location and the entry in the DB for that
                    submission will be deleted. An email is sent to the user that the file was not accepted.
                    -Nick 
                */
                File locationOfRejectedFiles = new File(cloudStorageOperations.getRejected());
                
                if(!directoryContains(locationOfRejectedFiles, new File(cloudStorageOperations.getRejected() + file)))
                {
                    org.apache.commons.io.FileUtils.moveFileToDirectory(new File(cloudStorageOperations.getSubmission() + file), locationOfRejectedFiles, true);
                    
                    //SendEmail rejectionEmail = new SendEmail(fName, lName, reasonForRejection, file, results.getString("idJobs"));
                    EmailUtils sendEmails = new EmailUtils(fName, lName, reasonForRejection, file, results.getString("idJobs"));
                    sendEmails.Send();
                    //rejectionEmail.Send();
                    
                    dbconn.delete("pendingjobs", results.getString("idJobs"));
                    dbconn.closeDBConnection();
                    
                    return SUCCESS;
                }
                else
                {
                    System.out.println("File deleted");
                    org.apache.commons.io.FileUtils.forceDelete(new File(cloudStorageOperations.getRejected() + file));
                }
                
            }
            
            return FAILURE;
        } 
        catch (SQLException | IOException ex) 
        {
            System.out.println("Program crashed in reject subm\n" + ex);
            return FAILURE;
        }
    }
    
    public static void approveStudentSubmission(String fileName, String firstName, String lastName, String printer, String dateStarted, double volume)
    {
        /* Make the connection to our DB and query for the PK of pendingjobs which is a combination of
           all the fields input in the searchID method call
        */
        SQLMethods dbconn = new SQLMethods();
        ResultSet result = dbconn.searchID("pendingjobs", firstName, lastName, fileName, dateStarted);
        InstanceCall cloudStorageOperations = new InstanceCall();
        
        String ID;

        try 
        { 
            /* If the row exist, then query for the PK and then use that to update the pendingjobs table fileLocation. -Nick */
            if (result.next())
            {
                ID = result.getString("idJobs");
                String updatedDirectoryLocation = cloudStorageOperations.getDrive() + "\\ObjectLabPrinters\\" + printer + "\\ToPrint";
                String updatedFileLocation = updatedDirectoryLocation + "\\" + fileName;
                String currentFileLocation = cloudStorageOperations.getSubmission() + "\\" + fileName;
                
                /* This moves the file from the submissions folder to the toPrint folder in folder specified by 
                 *  the printer variable -Nick
                 */
                org.apache.commons.io.FileUtils.moveFileToDirectory(new File(currentFileLocation), new File(updatedDirectoryLocation), true);
                
                /* In order to properly update the file location we need to gurantee there are '\\' seperating
                 *  the dir names.
                 *  If ther are no double backslashes than the character will not be escaped properly, and
                 *  the DB will not contain the correct file location.
                 *   - Nick
                 */           
                dbconn.updatePendingJobVolume(ID, volume);
                dbconn.updatePendingJobFLocation(ID, updatedFileLocation.replace("\\", "\\\\"));
                dbconn.approve(ID);
                dbconn.closeDBConnection();
            }
        } 
        catch (SQLException | IOException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* This function has the params that make up the primary key in pending jobs.
       It returns the filePath found in the DB where all the parameters specify the file
       that is associated for that users submission.
     */
    public static File getFilePath(String firstName, String lastName, String fileName, String dateSubmitted)
    {
        SQLMethods dbconn = new SQLMethods();
        File filePath = null;
        
        
        ResultSet result = dbconn.searchID("pendingjobs", firstName, lastName, fileName, dateSubmitted);
                        
        try 
        {
            
            if (result.next())
                filePath = new File(result.getString("filePath"));
            
            dbconn.closeDBConnection();
            
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filePath;
        
    }
    
    /**     * 
     * @param filepath
     * @return 
     * 
     * Check if the file exists in the given filepath. If it doesn't, prompt the user to search for the file. 
     * If user is unable to find the file, delete it from the database.
     */
    public static boolean checkFileExists(String filepath){
        boolean exists = FileUtils.doesFileExist(filepath);
        //If the files does not exist and the user does not locate it
        if(!exists){
            //TODO: update file location in database
        }
        return true;
    }
    
     /**
      * This is probably something that should be in a general Utils class for the front end or the various "views".
      * I'm leaving this here for now because I don't want to change or add anything else that could affect other 
      * groups. -Nick
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
             * -Nick
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
      * -Nick
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
    
    /* I'm still considering making the return type the array of arrylists<String> since it is more general
     * than having the UI pass in its table model -Nick
    */
    public static void updatePendingTableData(DefaultTableModel dataHolder)
    {     
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.searchPending();
        
        ArrayList<ArrayList<String>> retval = readyOutputForViewPage(queryResult);
        
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        for (ArrayList<String> retval1 : retval) 
            dataHolder.addRow(retval1.toArray());
    }
     
    
}
