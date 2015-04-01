/*
 *
 */
package ObjectLabEnterpriseSoftware;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static org.apache.commons.io.FileUtils.directoryContains;

/**
 *
 * @author nick
 */
public class UtilController 
{
    private static final boolean SUCCESS = true;
    private static final boolean FAILURE = false;
    
    private static void print(ArrayList<ArrayList<Object>> q)
    {
        for(int i = 0; i < q.size(); i++)
        {
            ArrayList<Object> row = q.get(i);
            for(int j = 0; j < row.size(); j++)
                System.out.print(" " + (String) row.get(j) + " ");
            System.out.println("\nRow 0");
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
                    
                    SendEmail rejectionEmail = new SendEmail(fName, lName, reasonForRejection, file, results.getString("idJobs"));
                    rejectionEmail.Send();
                    
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
    private static ArrayList<ArrayList<Object>> readyOutputForViewPage(ResultSet queryResult)
    {
        ArrayList<String> columnNames = getColumnNames(queryResult);
        ArrayList<ArrayList<Object>> retval = new ArrayList<>();
        
        /* Process data column by column and add that data into dataVector */
        try 
        {  
            ArrayList<Object> tempRow;
            
            /* Goes through ResultSet row by row adding the row data into retval as an arraylist of type string */
            while (queryResult.next()) 
            {
                tempRow = new ArrayList<>();
                for (String columnName : columnNames) 
                    tempRow.add((Object) queryResult.getString((String) columnName));
                
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
        
        ArrayList<ArrayList<Object>> retval = readyOutputForViewPage(queryResult);
        
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        for (ArrayList<Object> retval1 : retval) 
            dataHolder.addRow(retval1.toArray());
    }
     
    public static String[] returnAvailablePrinters()
    {    
        /*
          Fetch available printers
        */
        
        //Initialize the return value of String [] 
        SQLMethods dbconn = new SQLMethods();

        ResultSet printersAvailableResult = dbconn.getAvailablePrinters();
        ArrayList<ArrayList<Object>> printersAvailableAL = readyOutputForViewPage(printersAvailableResult);
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();
        
        /*
        Convert results to desired format
        */
        String [] printersAvailble = new String[printersAvailableAL.size()];
        for(int row = 0; row < printersAvailableAL.size(); row++)
            printersAvailble[row] = (String) printersAvailableAL.get(row).get(0);
        
        return printersAvailble;
    }
    
     /**
     * Updates view for making a build.
     * This will show files/jobs (student submissions) that need to be put into a build
     * UNFINISHED ****
     * 
     * This method is called in:
     *      PrinterBuild.updateView
     * 
     * @param printer the printer being viewed
     * @return 
     */
    public static ArrayList<ArrayList<Object>> updatePrinterBuildView(String printer) 
    {
        SQLMethods dbconn = new SQLMethods();

        ResultSet result = dbconn.searchApprovedJobsNotPrinted(printer);
        ArrayList<ArrayList<Object>> approvedForPrinter = readyOutputForViewPage(result);
        dbconn.closeDBConnection();

        return approvedForPrinter;       
    }
    
    /**
     * Updates build name in pending jobs table in the database
     * 
     * This method is called in:
     *      PrinterBuild.submit
     * 
     * @param b build name
     * @param f file name
     **/
    public static void updateRecordInPendingJobsTable(String b, String f) 
    { 
        SQLMethods dbconn = new SQLMethods();
        File buildName = new File(b);
        String bName = buildName.getName();
        //System.out.println("Updating " + b + " to be associated with " + f);
        dbconn.updatePendingJobsBuildName(bName, f);
        
        dbconn.closeDBConnection();
    }
    
    /**
     * This method is called when an unfinished print build is exited out of
     * It takes the information previously stored in the Database and removes it.
     * 
     * This method is called in:
     *      ZCorpDialog.ZCorpDialogStart.WindowClosing
     *      SolidscapeDialog.SolidscapeDialogStart.WindowClosing
     *      ObjetDialog.ObjetDialogStart.WindowClosing
     * 
     * @param buildPath 
     * @param printer 
     */
    public static void revertBuild(String buildPath, String printer)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet r = dbconn.searchPendingByBuildName(buildPath);
        String print = printer;
        try 
        {
            while(r.next())
            {
                dbconn.updatePendingJobsBuildName(r.getString("buildName"), r.getString("fileName"));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //This won't be the final way this is done, but is currently here for testing purposes. Working on creating a universal "search[printer]ByBuildName" method
        ResultSet s = null;//this is set equal to null so line 381 will compile.
        switch (print) {
            case "solidscape":
                s = dbconn.searchSolidscapeByBuildName(buildPath);
                break;
            case "zcorp":
                s = dbconn.searchZCorpByBuildName(buildPath);
                break;
            case "objet":
                s = dbconn.searchObjetByBuildName(buildPath);
                break;
        }
        try 
        {
            while(s.next())
            {
                dbconn.deleteByBuildName(s.getString("buildName"), printer);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dbconn.closeDBConnection();
    }
    
    /**
     * This method is called when an administrator submits a printer build. It takes the information they typed in and stores it in the database.
     * This method is called in:
     *      ZCorpDialog.ZCorpDialogStart.submitBtnActionPerformed
     *      SolidscapeDialog.SolidscapeDialogStart.submitBtnActionPerformed
     *      ObjetDialog.ObjetDialogStart.submitBtnActionPerformed
     * 
     * @param buildName The name of the build, used to gather information from the database.
     * @param printer this gets the name of the printer, 
     *                for now, the "printer" parameter is used to determine which sql method is used and the directory of the submitted file because it is different for every printer
     *                This will be changed when the dynamic database is being used
     */
    public static void submitBuildInfoToDB(String buildName, String printer)
    {
        SQLMethods dbconn = new SQLMethods();
        try 
        {
       
                    /* queries the DB for everything that has the buildName = to build name passed in as parameter */
                    ResultSet res1 = dbconn.searchPendingByBuildName(buildName);
                    ArrayList list = new ArrayList();
                    try 
                    {
                        while (res1.next()) 
                        {
                            list.add(res1.getString("buildName"));
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Iterator itr = list.iterator();
                    //Date date = Calendar.getInstance().getTime();
                    //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    
                    /* itr contains a list of student submissions that where selected for the build process in the previous screen PrinterBuild.java */
                    while (itr.hasNext()) 
                    {
                        ResultSet res2 = dbconn.searchPendingByBuildName(itr.next().toString());
                        if (res2.next()) 
                        {
                            String ID = res2.getString("idJobs");
                            System.out.println(ID);
                            String Printer = res2.getString("printer");
                            String firstName = res2.getString("firstName");
                            String lastName = res2.getString("lastName");
                            String course = res2.getString("course");
                            String section = res2.getString("section");
                            String fileName = res2.getString("fileName");
                            System.out.println(fileName);
                            File newDir = null;
                            
                            switch (printer) {
                                case "ZCorp":
                                    newDir = new File(ZCorpMain.getInstance().getZcorpPrinted());
                                    FileUtils.moveFileToNewDirectory(new File(ZCorpMain.getInstance().getZcorpToPrint() + fileName), newDir, true);
                                    break;
                                case "Solidscape":
                                    newDir = new File(SolidscapeMain.getInstance().getSolidscapePrinted());
                                    FileUtils.moveFileToNewDirectory(new File(SolidscapeMain.getInstance().getSolidscapeToPrint() + fileName), newDir, true);
                                    break;
                                case "Objet":
                                    newDir = new File(ObjetMain.getInstance().getObjetPrinted());
                                    FileUtils.moveFileToNewDirectory(new File(ObjetMain.getInstance().getObjetToPrint() + fileName), newDir, true);
                                    break;
                            }
                            

                            String filePath = newDir.getAbsolutePath().replace("\\", "\\\\"); //Needs to be changed
                            String dateStarted = res2.getString("dateStarted");
                            String Status = "completed";
                            String Email = res2.getString("Email");
                            String Comment = res2.getString("comment");
                            String nameOfBuild = res2.getString("buildName");
                            double volume = Double.parseDouble(res2.getString("volume"));
                            //double cost = Double.parseDouble(res3.getString("cost"));

                            dbconn.insertIntoCompletedJobs(ID, Printer, firstName, lastName, course, section, fileName, filePath, dateStarted, Status, Email, Comment, nameOfBuild, volume, 0.00 /*placeholder since cost isn't being used*/);
                            dbconn.delete("pendingjobs", ID);
                            //In Open Builds, it should go back and change status to complete so it doesn't show up again if submitted
                        }
                    }

            // if there is no matching record
            switch (printer) {
                case "ZCorp":
                    dbconn.insertIntoZcorp(buildName, ZCorpDialog.monoBinder, ZCorpDialog.yellowBinder, ZCorpDialog.magentaBuilder, ZCorpDialog.cyanBuilder, ZCorpDialog.cubicInches, ZCorpDialog.modelAmount, ZCorpDialog.comments, 0.00/*placeholder since cost isn't being used*/, "complete");
                    break;
                case "Solidscape":
                    dbconn.insertIntoSolidscape(buildName, SolidscapeDialog.modelAmount, SolidscapeDialog.ResolutionVar, SolidscapeDialog.buildTime, SolidscapeDialog.comments, 0.00/*placeholder since cost isn't being used*/);
                    break;
                case "Objet":
                    dbconn.insertIntoObjet(buildName, ObjetDialog.BuildConsumed, ObjetDialog.SupportConsumed, ObjetDialog.modelAmount,  ObjetDialog.materialType, ObjetDialog.Resolution, ObjetDialog.comments, 0.00 /*placeholder since cost isn't being used*/);
                    break;
            }
                    
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbconn.closeDBConnection();
    }
    
}
