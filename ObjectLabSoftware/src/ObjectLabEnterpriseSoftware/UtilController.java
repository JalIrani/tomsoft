package ObjectLabEnterpriseSoftware;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/* We want to move this into its own class. For making excel documents based on the DefaultTableModel */
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class UtilController
{

    private static final boolean SUCCESS = true;
    private static final boolean FAILURE = false;
	
	private static final String SOFTWARE_NAME = "OLI";
	private static final String SOFTWARE_VERSION = "v0.6"; //Should be dynamic
	private static final String USER_GUIDE_URL = "http://triton.towson.edu/~jirani2/adminHelp.pdf";
	
	public static String getPageName(String pageName)
	{
		return SOFTWARE_NAME + " " + SOFTWARE_VERSION + " - " + pageName;
	}
	
    private static void print(ArrayList<ArrayList<Object>> q)
    {
        for (int i = 0; i < q.size(); i++)
        {
            ArrayList<Object> row = q.get(i);
            for (int j = 0; j < row.size(); j++)
            {
                System.out.print(" " + (String) row.get(j) + " ");
            }
            System.out.println("\nRow 0");
        }
    }

    public static void openAdminHelpPage(){
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI(USER_GUIDE_URL));
            } catch (IOException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
    
    
    public static ArrayList<String> getListOfPrinters()
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.getAvailablePrinters();
        ArrayList<String> printers = new ArrayList<String>();
        ArrayList<ArrayList<Object>> data = readyOutputForViewPage(queryResult);

        for (ArrayList<Object> data1 : data)
        {
            printers.add(data1.get(0).toString());
        }

        dbconn.closeDBConnection();
        return printers;
    }
    public static String[] getReportColumnHeaders(String printer_name)
    {
        try
        {
            SQLMethods dbconn = new SQLMethods();
            ResultSet queryResult = dbconn.getReport(printer_name);
            /* Must process results found in ResultSet before the connection is closed! */

            ResultSetMetaData rsmd = queryResult.getMetaData();
            String[] headers = new String[rsmd.getColumnCount()];
            //System.out.println(rsmd.getColumnName(5));
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                headers[i - 1] = rsmd.getColumnName(i);
            }

            dbconn.closeDBConnection();
            return headers;
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String[] getStatusJobsHeaders(String status)
    {
        try
        {
            SQLMethods dbconn = new SQLMethods();
            ResultSet queryResult = dbconn.searchJobsStatus(status);
            /* Must process results found in ResultSet before the connection is closed! */

            ResultSetMetaData rsmd = queryResult.getMetaData();
            String[] headers = new String[rsmd.getColumnCount()];
            //System.out.println(rsmd.getColumnName(5));
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                headers[i - 1] = rsmd.getColumnName(i);
            }

            dbconn.closeDBConnection();
            return headers;
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public static ArrayList<ArrayList<Object>> updateReportTableData(String printer_name)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.getReport(printer_name);

        ArrayList<ArrayList<Object>> retval = readyOutputForViewPage(queryResult);

        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        return retval;

    }

    public static ArrayList<ArrayList<Object>> updateReportTableData(String column, String value, String printer_name)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.getReport(column, value, printer_name);

        ArrayList<ArrayList<Object>> retval = readyOutputForViewPage(queryResult);

        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        return retval;
    }

    public void exportReportToFile(DefaultTableModel model, String[] header)
    {

        Workbook wb = new HSSFWorkbook();
        //TODO: pick better sheet name
        Sheet sheet = wb.createSheet("new sheet");
        Row row = null;
        Cell cell = null;

        for (int i = 0; i < model.getRowCount() + 1; i++)
        {
            row = sheet.createRow(i);
            if (i == 0)
            {
                for (int j = 0; j < header.length; j++)
                {
                    cell = row.createCell(j);
                    cell.setCellValue(header[j]);
                }
            } else
            {
                for (int j = 0; j < model.getColumnCount(); j++)
                {
                    cell = row.createCell(j);
                    cell.setCellValue((String) model.getValueAt(i - 1, j));
                }
            }
        }

        boolean didSave = FileManager.saveReport("ReportName", wb);

        if (didSave)
        {
            JOptionPane.showMessageDialog(new JFrame(), "Succesfully Exported File");
        } else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Unable To Exported File");
        }
    }

    public static void exportReportsForPrinters()
    {

        ArrayList<String> printers = getListOfPrinters();

        Workbook wb = new HSSFWorkbook();

        String[] columnHeaders;
        ArrayList<ArrayList<Object>> data;
        Sheet sheet;

        for (int x = 0; x < printers.size(); x++)
        {

            sheet = wb.createSheet(printers.get(x));
            columnHeaders = getReportColumnHeaders(printers.get(x));
            data = updateReportTableData(printers.get(x));
            Row row = null;
            Cell cell = null;
            if (data.size() > 0)
            {
                for (int i = 0; i < data.size() + 1; i++)
                {
                    row = sheet.createRow(i);
                    if (i == 0)
                    {
                        for (int j = 0; j < data.get(i).size(); j++)
                        {
                            cell = row.createCell(j);
                            cell.setCellValue(columnHeaders[j]);
                        }
                    } else
                    {
                        for (int j = 0; j < data.get(i - 1).size(); j++)
                        {
                            cell = row.createCell(j);
                            cell.setCellValue((String) data.get(i - 1).get(j));
                        }
                    }
                }
            }
        }
        boolean didSave = FileManager.saveReport("MasterReport", wb);

        if (didSave)
        {
            JOptionPane.showMessageDialog(new JFrame(), "Succesfully Exported File");
        } else
        {
            JOptionPane.showMessageDialog(new JFrame(), "Unable To Exported File");
        }
    }

    public static boolean rejectStudentSubmission(String file, String reasonForRejection)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet results = dbconn.searchID((file) );

        try
        {
            String emailadr, emailMessage, primaryKey, fName, lName;
            File locationOfRejectedFiles, rejectionFile;

            /* Query the DB for our emailadr here */
            if (results.next())
            {
                primaryKey = results.getString("job_id");
                
                ResultSet queryResultData = dbconn.searchWithJobID(primaryKey);
                if (queryResultData.next())
                {
                    emailadr = queryResultData.getString("email");
                    fName=queryResultData.getString("first_name");
                    lName=queryResultData.getString("last_name");
                    
                } else
                {
                    dbconn.closeDBConnection();
                    return FAILURE;
                }
            } else
            {
                dbconn.closeDBConnection();
                return FAILURE;
            }

            /* Create rejected directory if it does not exist
             if(!cloudStorageOperations.doesFileExist(cloudStorageOperations.getRejected()))
             cloudStorageOperations.create(cloudStorageOperations.getRejected());
             */
            /* Move our rejected file to the rejected files directory */
            locationOfRejectedFiles = new File(FileManager.getRejected());
            rejectionFile = new File(FileManager.getSubmission() + file);

            if (rejectionFile.exists())
            {
                FileUtils.moveFileToDirectory(rejectionFile, locationOfRejectedFiles, true);
            } else
            {
                dbconn.closeDBConnection();
                return FAILURE;
            }

            /* 
             Delete the job that was rejected from the pendingjobs table. Close socket conn after we do so 
             */
            
            dbconn.updateStatus( "rejected",Integer.parseInt(primaryKey));
            dbconn.closeDBConnection();

            emailMessage = "Dear " + fName + " " + lName + ", \n\nAfter analyzing your file submission, "
                    + file + ", we have found the following error: \n\nComment: " + reasonForRejection
                    + "\n\nPlease fix the file and resubmit." + "\n\nThank you,\nObject Lab Staff";
            //Backup email
            //TowsonOli@gmail.com passwordTowson
            return new EmailUtils(emailadr, "TowsonuObjectLab@gmail.com", "oblabsoftware", emailMessage).send();
        } catch (SQLException ex)
        {
            System.out.println("Program crashed in reject subm\n" + ex);
        } catch (IOException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbconn.closeDBConnection();
        return FAILURE;
    }

    public static void approveStudentSubmission(String fileName, double volume)
    {
        /* Make the connection to our DB and query for the PK of pendingjobs which is a combination of
         all the fields input in the searchID method call
         */
        SQLMethods dbconn = new SQLMethods();
        ResultSet result = dbconn.searchID((fileName) );

        String ID;

        try
        {
            /* 
             If the row exist, then query for the PK and then use that to update 
             the pendingjobs table fileLocation. -Nick 
             */
            if (result.next())
            {
                ID = result.getString("job_id");
                String printer=result.getString("printer_name");
                String updatedDirectoryLocation = FileManager.getDrive() + "\\ObjectLabPrinters\\" + printer + "\\ToPrint";
                String updatedFileLocation = updatedDirectoryLocation + "\\";
                String currentFileLocation = FileManager.getSubmission() + "\\" + fileName;

                /* This moves the file from the submissions folder to the toPrint folder in folder specified by 
                 *  the printer variable -Nick
                 */
                FileUtils.moveFileToDirectory(new File(currentFileLocation), new File(updatedFileLocation), true);

                /* In order to properly update the file location we need to gurantee there are '\\' seperating
                 *  the dir names.
                 *  If ther are no double backslashes than the character will not be escaped properly, and
                 *  the DB will not contain the correct file location.
                 *   - Nick
                 */
                dbconn.updateJobVolume(Integer.parseInt(ID), volume);
                dbconn.updateJobFLocation(Integer.parseInt(ID), updatedFileLocation.replace("\\", "\\\\"));
                dbconn.changeJobStatus(Integer.parseInt(ID), "approved");
                dbconn.closeDBConnection();
            }
        } catch (SQLException | IOException ex)
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

        ResultSet result = dbconn.searchID((fileName) );

        try
        {
            if (result.next())
            {
                filePath = new File(result.getString("file_path"));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbconn.closeDBConnection();
        return filePath;
    }

    /**
     * *
     * @param filepath
     * @return
     *
     * Check if the file exists in the given filepath. If it doesn't, prompt the
     * user to search for the file. If user is unable to find the file, delete
     * it from the database.
     */
    public static boolean checkFileExists(String filepath)
    {
        boolean exists = FileManager.doesFileExist(filepath);
        //If the files does not exist and the user does not locate it
        if (!exists)
        {
            //TODO: update file location in database
        }
        return true;
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

            for (int column = 1; column <= columnCount; column++)
            {

                columnNames.add(meta.getColumnName(column));
            }
        } catch (SQLException ex)
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
                {
                    tempRow.add((Object) queryResult.getString((String) columnName));
                }

                retval.add(tempRow);
            }
        } catch (SQLException sqle)
        {
            System.out.println(sqle);
            return retval;
        }
        return retval;
    }

    public static ArrayList<ArrayList<Object>> updatePendingTableData(String status)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.searchJobsStatus(status); 

        ArrayList<ArrayList<Object>> retval = readyOutputForViewPage(queryResult);

        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();
        return retval;
    }

    public static String[] returnAvailablePrinters()
    {
        /*
         Fetch available printers
         */

        SQLMethods dbconn = new SQLMethods();
        ResultSet printersAvailableResult = dbconn.getAvailablePrinters();
        ArrayList<ArrayList<Object>> printersAvailableAL = readyOutputForViewPage(printersAvailableResult);
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        /*
         Convert results to desired format
         */
        String[] printersAvailble = new String[printersAvailableAL.size()];
        for (int row = 0; row < printersAvailableAL.size(); row++)
        {
            printersAvailble[row] = (String) printersAvailableAL.get(row).get(0);
        }

        return printersAvailble;
    }

    public static String[] returnAvailableClasses()
    {
        /*
         Fetch available classes
         */

        SQLMethods dbconn = new SQLMethods();
        ResultSet classesAvailableResult = dbconn.getClasses(true);
        ArrayList<ArrayList<Object>> classesAvailableAL = readyOutputForViewPage(classesAvailableResult);
        /* Must process results found in ResultSet before the connection is closed! */
        dbconn.closeDBConnection();

        /*
         Convert results to desired format
         */
        String[] classesAvailble = new String[classesAvailableAL.size()];
        for (int row = 0; row < classesAvailableAL.size(); row++)
        {
            String tempRow = "";
            ArrayList<Object> tmplist;

            tmplist = (ArrayList<Object>) classesAvailableAL.get(row);

            for (int column = 0; column < tmplist.size(); column++)
            {
                tempRow += tmplist.get(column) + " ";
            }

            classesAvailble[row] = tempRow;
        }

        return classesAvailble;
    }

    public static void submitStudentFile(String userID, String fileLocation, String fileName, String printerName, int classFK)
    {
        /*
         Establish connection to DB
         */
        SQLMethods dbconn = new SQLMethods();

        try
        {
            /*
                Get new file location for submission location
             */
            String newFileLoc = FileManager.getSubmission() + fileName;
            newFileLoc = newFileLoc.replace("\\", "\\\\");

            /*
                Now copy the old file to the new file location
             */
            FileUtils.copyFile(new File(fileLocation), new File(newFileLoc));
            dbconn.insertIntoJob(fileName, newFileLoc, classFK, userID, printerName);
            
        } catch (IOException e)
        {
            javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), "IOException! File couldn't be navigated.");
        }
        /*
         Close the DB connection
         */
        dbconn.closeDBConnection();
    }

    public static String getCurrentTimeFromDB()
    {
        /*
         Establish connection to DB
         */
        SQLMethods dbconn = new SQLMethods();

        /*
         Parse result as single string
         */
        String currTime = null;
        ResultSet res = dbconn.getCurrentTime();
        try
        {
            int count = 1;
            while (res.next())
            {
                currTime = res.getString(count);
                count++;
            }
        } catch (SQLException sqlError)
        {
            sqlError.printStackTrace();
        }

        /*
         Append a "_" so that project names can be differentiated from timestamp
         */
        currTime = "_" + currTime;
        dbconn.closeDBConnection();

        return (String) currTime.trim();
    }

    /**
     * Updates view for making a build. This will show files/jobs (student
     * submissions) that need to be put into a build UNFINISHED ****
     *
     * This method is called in: PrinterBuild.updateView
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
     * This method is called in: PrinterBuild.submit
     *
     * @param b build name
     * @param f file name
     *
     */
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
     * This method is called when an unfinished print build is exited out of It
     * takes the information previously stored in the Database and removes it.
     *
     * This method is called in: ZCorpDialogView.ZCorpDialogStart.WindowClosing
     * SolidscapeDialogView.SolidscapeDialogStart.WindowClosing
     * ObjetDialogView.ObjetDialogStart.WindowClosing
     *
     * @param buildPath
     * @param printer
     */
    public static void revertBuild(String buildPath, String printer)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet r = dbconn.searchPendingByBuildName(buildPath);
        try
        {
            while (r.next())
            {
                dbconn.updatePendingJobsBuildName("", r.getString("fileName"));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbconn.closeDBConnection();
    }

    /**
     * This method is called when an administrator submits a printer build. It
     * takes the information they typed in and stores it in the database. This
     * method is called in:
     * ZCorpDialogView.ZCorpDialogStart.submitBtnActionPerformed
     * SolidscapeDialogView.SolidscapeDialogStart.submitBtnActionPerformed
     * ObjetDialogView.ObjetDialogStart.submitBtnActionPerformed
     *
     * @param buildName The name of the build, used to gather information from
     * the database.
     * @param printer this gets the name of the printer, for now, the "printer"
     * parameter is used to determine which sql method is used and the directory
     * of the submitted file because it is different for every printer This will
     * be changed when the dynamic database is being used
     * @return success or failure status
     */
    public static boolean submitBuildInfoToDB(String buildName, String printer)
    {
        if (!printer.equals("zcorp") && !printer.equals("solidscape") && !printer.equals("objet"))
        {
            return false;
        }

        SQLMethods dbconn = new SQLMethods();

        try
        {
            /* queries the DB for everything that has the buildName = to build name passed in as parameter */
            ResultSet res1 = dbconn.searchPendingByBuildName(buildName);
            ArrayList list = new ArrayList();
            System.out.println("String buildName: " + buildName);

            try
            {
                while (res1.next())
                {
                    list.add(res1.getString("buildName"));
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* itr contains a list of student submissions that where selected for the build process in the previous screen PrinterBuild.java */
            Iterator itr = list.iterator();
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

                    newDir = new File(FileManager.getPrinterLocation(printer));
                    FileManager.moveFileToNewDirectory(new File(FileManager.getPrinterLocation(printer)), newDir, true);

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
            switch (printer)
            {
                case "zcorp":
                    dbconn.insertIntoZcorp(buildName, ZCorpDialogView.monoBinder, ZCorpDialogView.yellowBinder, ZCorpDialogView.magentaBuilder, ZCorpDialogView.cyanBuilder, ZCorpDialogView.cubicInches, ZCorpDialogView.modelAmount, ZCorpDialogView.comments, 0.00/*placeholder since cost isn't being used*/, "complete");
                    break;
                case "solidscape":
                    dbconn.insertIntoSolidscape(buildName, SolidscapeDialogView.modelAmount, SolidscapeDialogView.ResolutionVar, SolidscapeDialogView.buildTime, SolidscapeDialogView.comments, 0.00/*placeholder since cost isn't being used*/);
                    break;
                case "objet":
                    dbconn.insertIntoObjet(buildName, ObjetDialogView.BuildConsumed, ObjetDialogView.SupportConsumed, ObjetDialogView.modelAmount, ObjetDialogView.materialType, ObjetDialogView.Resolution, ObjetDialogView.comments, 0.00 /*placeholder since cost isn't being used*/);
                    break;
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbconn.closeDBConnection();
        return true;
    }

   public static void archive(String from, String to)
    {
        try
        {
            exportReportsForPrinters();
            String fileName = new String();
            String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
            fileName = "Archive " + date;
            System.out.println((from.replace("\\", "\\\\") + "\\\\") + " : " + (to.replace("\\", "\\\\") + "\\\\" + fileName + ".zip"));
            FileManager.zip((from.replace("\\", "\\\\") + "\\\\"), (to.replace("\\", "\\\\") + "\\\\" + fileName + ".zip"));
            
            if (FileManager.doesFileExist((to.replace("\\", "\\\\") + "\\\\" + fileName + ".zip")))
            {
                JOptionPane.showMessageDialog(new JFrame(), "Archive Successful");
            } else
            {
                JOptionPane.showMessageDialog(new JFrame(), "Archive Failed");
            }
        } catch (IOException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   public static void clearData(){
   
       SQLMethods dbconn = new SQLMethods();
       dbconn.clearData();
       dbconn.closeDBConnection();
   
   }

    public static void updateAvailableClasses(ArrayList<String> classData)
    {
        SQLMethods dbconn = new SQLMethods();

        // SET all classes in current's current value to true and all else to false
        dbconn.setAllClassesInvisible();
        
        for(String currentClass : classData)
            dbconn.updateCurrentClasses(Integer.parseInt(currentClass.split(" ")[0]));
        
        dbconn.closeDBConnection();
    }

    public static boolean addDevice(Device deviceModel)
    {
        SQLMethods dbconn = new SQLMethods();
        String deviceName = deviceModel.getDeviceName();
        ArrayList<String> fieldNames = deviceModel.getFieldNames();
        ArrayList<String> fileExt = deviceModel.getFileExtensions();

        /* Insert our printer into the printer table. For right now just adding in the first
         file extension added from UI (DB does not support multiple file extensions)
         */
        dbconn.insertIntoPrinter(deviceName, true);

        for (String ext : fileExt)
        {
            dbconn.insertIntoAcceptedFiles(deviceName, ext);
        }

        for (String trackableField : fieldNames)
        {
            boolean isNumerical = deviceModel.getFieldType(trackableField) == Device.TYPE_DOUBLE;
            dbconn.insertIntoCustom(deviceName, trackableField, isNumerical);
        }

        dbconn.closeDBConnection();
        return true;
    }

    private static DefaultListModel returnClassesArray(boolean status)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet result2 = dbconn.getClasses(status);
        DefaultListModel classList = new DefaultListModel();;
        try
        {
            while (result2.next())
            {
                classList.addElement(result2.getString("class_id") + " " + result2.getString("class_name") + " " + result2.getString("class_section"));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(PrinterBuildView.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbconn.closeDBConnection();
        return classList;
    }
    
    public static DefaultListModel returnNonCurrentClasses()
    {
        return returnClassesArray(false);
    }
    
    public static DefaultListModel returnCurrentClasses()
    {
        return returnClassesArray(true);
    }
    
    public static void insertNewClass(String className, String classNumber, String sectionNumber, String professor)
    {
        SQLMethods dbconn = new SQLMethods();
        System.out.println(className + " " + classNumber + " " + sectionNumber + " " + professor);
        dbconn.insertIntoClasses(className + " " + classNumber, sectionNumber, professor);

        dbconn.closeDBConnection();
    }
    
    public static boolean userIDExist(String studentId)
    {
        boolean flag = false;
        SQLMethods dbconn = new SQLMethods();
        ResultSet usersCountQuery = dbconn.checkUserExists(studentId);
        try
        {
            flag = usersCountQuery.next();
        } catch (SQLException ex)
        {
            Logger.getLogger(UtilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbconn.closeDBConnection();
        return flag;
    }
    
    public static int addUser(String studentId, String firstname, String lastname, String email)
    {
		if(userIDExist(studentId))
			return -25;
        SQLMethods dbconn = new SQLMethods();
        int flag = dbconn.insertIntoUsers(studentId, firstname, lastname, email);
        dbconn.closeDBConnection();
		
		return flag;
    }
    
     public static ArrayList<ArrayList<Object>> returnTableHeader(String printerName) throws SQLException{
        SQLMethods dbconn = new SQLMethods();
        ArrayList<ArrayList<Object>> toSend = new ArrayList();
        ArrayList<Object> fillData = new ArrayList();
        ArrayList<Object> fillType = new ArrayList();
        printerName = printerName.trim();
        ResultSet queryResult = dbconn.forSean(printerName);
        //putting data into an array of arrays
        while(queryResult.next()){
            fillData.add(queryResult.getString(1));
            fillType.add(queryResult.getString(2));
        }
        toSend.add(fillData);
        toSend.add(fillType);
        dbconn.closeDBConnection();
        return toSend;
    }
     
    public static ArrayList<ArrayList<Object>> returnApprovedBuildsForPrinter(String printerName)
    {
        SQLMethods dbconn = new SQLMethods();
        ResultSet queryResult = dbconn.searchJobsStatusPrinter("approved", printerName);
        ArrayList<ArrayList<Object>> parsedResult = readyOutputForViewPage(queryResult);
        dbconn.closeDBConnection();
        return parsedResult;
    }
}
