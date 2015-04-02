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
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Miguel
 */
public class FileUtils {
    
    private static final InstanceCall cloudStorageOperations = new InstanceCall();
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
    public FileUtils() {
        //Use the directory provided on piazza and change the file paths below to test
        drive = "C:\\Sync";
         File temp = new File(drive);
         temp.mkdirs();
        rejected = drive + "\\ObjectLabPrinters\\Rejected\\";
        temp = new File(rejected);
         temp.mkdirs();
        submission = drive + "\\ObjectLabPrinters\\Submissions\\";
        temp = new File(submission);
         temp.mkdirs();
        PDFAdmin = "\\TomSoft Help Admin.pdf";
        temp = new File(PDFAdmin);
         temp.mkdirs();
        PDFStudent = "\\TomSoft Help Student.pdf";
        temp = new File(PDFStudent);
         temp.mkdirs();
        zcorpToPrint = drive + "\\ObjectLabPrinters\\Zcorp\\ToPrint\\";
        temp = new File(zcorpToPrint);
         temp.mkdirs();
        zcorpPrinted = drive + "\\ObjectLabPrinters\\Zcorp\\Printed\\";
        temp = new File(zcorpPrinted);
         temp.mkdirs();
        zcorpToBuild = drive + "\\ObjectLabPrinters\\Zcorp\\Build Files\\";
        temp = new File(zcorpToBuild);
         temp.mkdirs();
        solidscapeToPrint = drive + "\\ObjectLabPrinters\\Solidscape\\ToPrint\\";
        temp = new File(solidscapeToPrint);
         temp.mkdirs();
        solidscapePrinted = drive + "\\ObjectLabPrinters\\Solidscape\\Printed\\";
        temp = new File(solidscapePrinted);
         temp.mkdirs();
        solidscapeToBuild = drive + "\\ObjectLabPrinters\\Solidscape\\Build Files\\";
        temp = new File(solidscapeToBuild);
         temp.mkdirs();
        objetToPrint = drive + "\\ObjectLabPrinters\\Objet\\ToPrint\\";
        temp = new File(objetToPrint);
         temp.mkdirs();
        objetPrinted = drive + "\\ObjectLabPrinters\\Objet\\Printed\\";
        temp = new File(objetPrinted);
         temp.mkdirs();
        objetToBuild = drive + "\\ObjectLabPrinters\\Objet\\Build Files\\";
        temp = new File(objetToBuild);
         temp.mkdirs();
        excelFilePath = drive + "\\Export\\";
        temp = new File(excelFilePath);
         temp.mkdirs();
        ZIPPED_FOLDER = drive + "\\ObjectLabPrinters\\";
        temp = new File(ZIPPED_FOLDER);
         temp.mkdirs();
        INPUT_FOLDER = drive + "\\ObjectLabPrinters\\";
        temp = new File(INPUT_FOLDER);
         temp.mkdirs();
        
        
        
    }
    
    public boolean deleteFile(String path){
        File newDir = new File(path);
        if(newDir.exists()){
            newDir.delete();
        }
        
        if(newDir.exists()) return true;
        
        return false;
    }
    
    public static boolean doesFileExist(String path){
    
        File newDir = new File(path);
        if(newDir.exists()){
            return true;
        }
        else{
            String fileLoc = browseForFile();
            if(fileLoc != null)
                return true;
        }
        return false;
    }
    
    public boolean rejectFile(String FileName){
        try {
            org.apache.commons.io.FileUtils.moveFileToDirectory(new File(submission + FileName), new File(rejected), true);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean approveFile(String FileName, String printer){
    
        File newDir = new File(drive + "\\ObjectLabPrinters\\" + printer + "\\ToPrint");
            try {
                org.apache.commons.io.FileUtils.moveFileToDirectory(new File(cloudStorageOperations.getSubmission() + "\\" + FileName), newDir, true);
            } catch (FileExistsException e) {
                org.apache.commons.io.FileUtils.deleteQuietly(new File(newDir.getAbsoluteFile() + FileName));
                newDir = new File(cloudStorageOperations.getDrive() + "\\ObjectLabPrinters\\" + printer + "\\ToPrint");
                try {
                    org.apache.commons.io.FileUtils.moveFileToDirectory(new File(cloudStorageOperations.getSubmission() + "\\" + FileName), newDir, true);
                } catch (IOException ex) {
                    //Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } catch (IOException ex) {
                //Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    
        return true;
    }
    
    public boolean saveReport(String name, Workbook wb){
        
        try {
            FileOutputStream out = new FileOutputStream(excelFilePath + name + ".xls");
            wb.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public boolean submitFile(String fileLocation){
        
        try {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(fileLocation), new File(submission));
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public static String browseForFile(){
    
        JFileChooser fileopen = new JFileChooser();  //in brackets, add Syncthing directory or new Drive's address for default location
        //Limits selected files to the following types. TODO fix list
        fileopen.setAcceptAllFileFilterUsed(false);
        fileopen.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Object Files", "obj", "zpr", "stl");
        fileopen.setFileFilter(filter);
        int ret = fileopen.showDialog(null, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            //Inputs the file location into the textbox "fileName"
            return file.toString().replaceAll("'", "");
        }
        return null;
        
    }
    
    public boolean zipFilesTo(String zipFileName){
        String zipTo =  ZIPPED_FOLDER + zipFileName + ".zip";
        try {
            zip(INPUT_FOLDER, zipTo);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
           return true;
    }
    public static void zip(String inputFolder,String targetZippedFolder)  throws IOException {

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

    public static void zipFolder(ZipOutputStream zipOutputStream,File inputFolder, String parentName)  throws IOException {
 
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

    public static void zipFile(File inputFile,String parentName,ZipOutputStream zipOutputStream) throws IOException{
 
        // A ZipEntry represents a file entry in the zip archive
        // We name the ZipEntry after the original file's name

        ZipEntry zipEntry = new ZipEntry(parentName+inputFile.getName());

        zipOutputStream.putNextEntry(zipEntry);

        FileInputStream fileInputStream = new FileInputStream(inputFile);

        byte[] buf = new byte[1024];

        int bytesRead;

        // Read the input file by chucks of 1024 bytes
        // and write the read bytes to the zip stream
        while ((bytesRead = fileInputStream.read(buf)) > 0) {
            
            zipOutputStream.write(buf, 0, bytesRead);
        }
        // close ZipEntry to store the stream to the file
        zipOutputStream.closeEntry();
 
       // System.out.println("Regular file :" + parentName+inputFile.getName() +" is zipped to archive :"+ZIPPED_FOLDER);
    }

       
    
}
