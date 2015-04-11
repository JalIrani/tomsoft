/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

public class FileManager 
{
    
    private final String submission;
    private final String drive;
    private String zcorpToPrint;
    private final String zcorpPrinted;
    private String zcorpToBuildDir;
    private final String zcorpToBuild;
    private final String solidscapeToPrint;
    private final String solidscapePrinted;
    private final String solidscapeToBuild;
    private final String objetToPrint;
    private final String objetPrinted;
    private final String objetToBuild;
    private final String PDFAdmin;
    private final String PDFStudent;
    private String submitted;
    private final String rejected;
    private final String excelFilePath;
    private final String INPUT_FOLDER;
    private final String ZIPPED_FOLDER;


    //Sets default locations that will be shared by all installations
    public FileManager() 
	{
        //Use the directory provided on piazza and change the file paths below to test
        drive = "C:\\Sync";
        rejected = drive + "\\ObjectLabPrinters\\Rejected\\";
        submission = drive + "\\ObjectLabPrinters\\Submissions\\";
        PDFAdmin = drive + "\\TomSoft Help Admin.pdf";
        PDFStudent = drive + "\\TomSoft Help Student.pdf";
        zcorpToPrint = drive + "\\ObjectLabPrinters\\Zcorp\\ToPrint\\";
        zcorpPrinted = drive + "\\ObjectLabPrinters\\Zcorp\\Printed\\";
        zcorpToBuild = drive + "\\ObjectLabPrinters\\Zcorp\\Build Files\\";
        solidscapeToPrint = drive + "\\ObjectLabPrinters\\Solidscape\\ToPrint\\";
        solidscapePrinted = drive + "\\ObjectLabPrinters\\Solidscape\\Printed\\";
        solidscapeToBuild = drive + "\\ObjectLabPrinters\\Solidscape\\Build Files\\";
        objetToPrint = drive + "\\ObjectLabPrinters\\Objet\\ToPrint\\";
        objetPrinted = drive + "\\ObjectLabPrinters\\Objet\\Printed\\";
        objetToBuild = drive + "\\ObjectLabPrinters\\Objet\\Build Files\\";
        excelFilePath = drive + "\\Export\\";
        ZIPPED_FOLDER = drive + "\\ObjectLabPrinters\\";
        INPUT_FOLDER = drive + "\\ObjectLabPrinters\\";
    }
    
    public boolean deleteFile(String path)
	{
        File newDir = new File(path);
        if(newDir.exists())
		{
            newDir.delete();
        }
        
        if(newDir.exists()) 
            return true;
        
        return false;
    }
    /* String fileLoc = browseForFile();
            if(fileLoc != null)
                return true;
    */
    
    public boolean doesFileExist(String path)
    {
        return new File(path).exists();
    }
    
    public boolean create(String path)
    {
        return new File(path).mkdir();
    }
    
    public boolean rejectFile(String FileName)
    {
        try 
        {
            FileUtils.moveFileToDirectory(new File(submission + FileName), new File(rejected), true);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }   
        return true;
    }
    
    public boolean approveFile(String FileName, String printer){
    
        File newDir = new File(drive + "\\ObjectLabPrinters\\" + printer + "\\ToPrint");
            try 
			{
                org.apache.commons.io.FileUtils.moveFileToDirectory(new File(submission + "\\" + FileName), newDir, true);
            } catch (FileExistsException e) 
			{
                org.apache.commons.io.FileUtils.deleteQuietly(new File(newDir.getAbsoluteFile() + FileName));
                newDir = new File(drive + "\\ObjectLabPrinters\\" + printer + "\\ToPrint");
                try 
				{
                    org.apache.commons.io.FileUtils.moveFileToDirectory(new File(submission + "\\" + FileName), newDir, true);
                } catch (IOException ex) 
				{
                    //Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } catch (IOException ex) 
			{
                //Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    
        return true;
    }
    
    public boolean saveReport(String name, Workbook wb)
	{
        
        try 
		{
            FileOutputStream out = new FileOutputStream(excelFilePath + name + ".xls");
            wb.write(out);
            out.close();
        } 
		catch (FileNotFoundException ex) 
		{
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) 
		{
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public boolean submitFile(String fileLocation)
	{
        
        try 
		{
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(fileLocation), new File(submission));
        } 
		catch (IOException ex) 
		{
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    /**
     * This method was added in by David Prince to allow the method "submitBuild" in UtilController to work properly
     * This class was causing trouble when I was trying to access the FileUtil method "moveFileToDirectory"
     * So I added this as a way to access the method from UtilController
     * 
     * @param srcFile
     * @param destDir
     * @param createDestDir 
     */
    public static void moveFileToNewDirectory(File srcFile,File destDir,boolean createDestDir)
    {
        try 
		{
            org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile ,destDir ,createDestDir);
        } catch (IOException ex) 
		{
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String browseForFile()
    {
        JFileChooser fileopen = new JFileChooser();  //in brackets, add Syncthing directory or new Drive's address for default location
        //Limits selected files to the following types. TODO fix list
        fileopen.setAcceptAllFileFilterUsed(false);
        fileopen.setMultiSelectionEnabled(false);
        /* PLEASE NOTE THIS IS NOT DYNAMIC!!!! WILL NEED TO GET ALLOWED FILE TYPES BASED ON PRINTER */
        fileopen.setFileFilter(new FileNameExtensionFilter("Object Files", "obj", "zpr", "stl"));
        int ret = fileopen.showDialog(null, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileopen.getSelectedFile();
            //Inputs the file location into the textbox "fileName"
            return file.toString().replaceAll("'", "");
        }
        
        return null;
        
    }
    
    public boolean zipFilesTo(String zipFileName)
	{
        String zipTo =  ZIPPED_FOLDER + zipFileName + ".zip";
        try 
		{
            zip(INPUT_FOLDER, zipTo);
        } 
		catch (IOException ex) 
		{
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
           return true;
    }
	
    public static void zip(String inputFolder,String targetZippedFolder)  throws IOException 
	{

        FileOutputStream fileOutputStream = null;

        fileOutputStream = new FileOutputStream(targetZippedFolder);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        File inputFile = new File(inputFolder);
        if (inputFile.isFile())
            zipFile(inputFile,"",zipOutputStream);
        else if (inputFile.isDirectory())
            zipFolder(zipOutputStream,inputFile,"");


        zipOutputStream.close();
    }

    public static void zipFolder(ZipOutputStream zipOutputStream,
			File inputFolder, String parentName)  throws IOException 
	{
 
        String myname = parentName +inputFolder.getName()+"\\";

        ZipEntry folderZipEntry = new ZipEntry(myname);
        zipOutputStream.putNextEntry(folderZipEntry);

        
        File[] contents = inputFolder.listFiles();
        for (File f : contents){
            if (f.isFile())
                zipFile(f,myname,zipOutputStream);

            else if(f.isDirectory())
                zipFolder(zipOutputStream,f, myname);
        }
        zipOutputStream.closeEntry();
    }

    public static void zipFile(File inputFile,String parentName,
			ZipOutputStream zipOutputStream) throws IOException
	{
 
        // A ZipEntry represents a file entry in the zip archive
        // We name the ZipEntry after the original file's name

        ZipEntry zipEntry = new ZipEntry(parentName+inputFile.getName());

        zipOutputStream.putNextEntry(zipEntry);

        FileInputStream fileInputStream = new FileInputStream(inputFile);

        byte[] buf = new byte[1024];

        int bytesRead;

        // Read the input file by chucks of 1024 bytes
        // and write the read bytes to the zip stream
        while ((bytesRead = fileInputStream.read(buf)) > 0) 
		{
            
            zipOutputStream.write(buf, 0, bytesRead);
        }
        // close ZipEntry to store the stream to the file
        zipOutputStream.closeEntry();
 
       // System.out.println("Regular file :" + parentName+inputFile.getName() +" is zipped to archive :"+ZIPPED_FOLDER);
    }
    //getters and setters moved from InstanceCall by Emily and Miguel
    /**
     * @return the zcorpToPrint
     */
    public String getZcorpToPrint() 
	{
        return zcorpToPrint;
    }

    /**
     * @param zcorpToPrint the zcorpToPrint to set
     */
    public void setZcorpToPrint(String zcorpToPrint) 
	{
        this.zcorpToPrint = zcorpToPrint;
    }

    /**
     * @return the zcorpToBuild
     */
    public String getZcorpToBuild() 
	{
        return zcorpToBuild;
    }

    /**
     * @return the solidscapeToPrint
     */
    public String getSolidscapeToPrint() 
	{
        return solidscapeToPrint;
    }

    /**
     * @return the solidscapeToBuild
     */
    public String getSolidscapeToBuild() 
	{
        return solidscapeToBuild;
    }

    /**
     * @return the zcorpToBuildDir
     */
    public String getZcorpToBuildDir() 
	{
        return zcorpToBuildDir;
    }

    /**
     * @return the objetToPrint
     */
    public String getObjetToPrint() 
	{
        return objetToPrint;
    }

    /**
     * @return the objetToBuild
     */
    public String getObjetToBuild() 
	{
        return objetToBuild;
    }

    /**
     * @return the zcorpPrinted
     */
    public String getZcorpPrinted() 
	{
        return zcorpPrinted;
    }

    /**
     * @return the solidscapePrinted
     */
    public String getSolidscapePrinted() 
	{
        return solidscapePrinted;
    }

    /**
     * @return the objetPrinted
     */
    public String getObjetPrinted() 
	{
        return objetPrinted;
    }

    /**
     * @return the PDF
     */
    public String getPDFAdmin() 
	{
        return PDFAdmin;
    }

        /**
     * @return the PDF
     */
    public String getPDFStudent() 
	{
        return PDFStudent;
    }
    
    /**
     * @return the submitted
     */
    public String getSubmitted() 
	{
        return submitted;
    }

    /**
     * @return the submission
     */
    public String getSubmission() 
	{
        return submission;
    }

    /**
     * @return the drive
     */
    public String getDrive() 
	{
        return drive;
    }

    /**
     * @return the rejected
     */
    public String getRejected() 
	{
        return rejected;
    }
       
   //added getters
     public String getExcelFilePath() 
	 {
        return excelFilePath;
    }

    public String getINPUT_FOLDER() 
	{
        return INPUT_FOLDER;
    }

    public String getZIPPED_FOLDER() 
	{
        return ZIPPED_FOLDER;
    }
}//end of class
