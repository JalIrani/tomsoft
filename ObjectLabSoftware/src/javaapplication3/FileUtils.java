/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javaapplication3.PendingJobsView.dba;
import org.apache.commons.io.FileExistsException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Miguel
 */
public class FileUtils {
    
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

    //Sets default locations that will be shared by all installations
    public FileUtils() {
        //Use the directory provided on piazza and change the file paths below to test
        drive = "C:\\Sync";
        rejected = drive + "\\ObjectLabPrinters\\Rejected\\";
        submission = drive + "\\ObjectLabPrinters\\Submissions\\";
        PDFAdmin = "\\TomSoft Help Admin.pdf";
        PDFStudent = "\\TomSoft Help Student.pdf";
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
    }
    
    public boolean deleteFile(String path){
        File newDir = new File(path);
        if(newDir.exists()){
            newDir.delete();
        }
        
        if(newDir.exists()) return true;
        
        return false;
    }
    
    public boolean doesFileExist(String path){
    
        File newDir = new File(path);
        if(newDir.exists()){
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
                org.apache.commons.io.FileUtils.moveFileToDirectory(new File(PendingJobsView.inst.getSubmission() + "\\" + FileName), newDir, true);
            } catch (FileExistsException e) {
                org.apache.commons.io.FileUtils.deleteQuietly(new File(newDir.getAbsoluteFile() + FileName));
                newDir = new File(PendingJobsView.inst.getDrive() + "\\ObjectLabPrinters\\" + printer + "\\ToPrint");
                try {
                    org.apache.commons.io.FileUtils.moveFileToDirectory(new File(PendingJobsView.inst.getSubmission() + "\\" + FileName), newDir, true);
                } catch (IOException ex) {
                    Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
