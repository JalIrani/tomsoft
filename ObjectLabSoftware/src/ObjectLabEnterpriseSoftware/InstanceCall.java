/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

/**
 *
 * @author Alexander
 */
public class InstanceCall {

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

    public InstanceCall() {
        //Use the directory provided on piazza and change the file paths below to test
        drive = "C:\\Sync";
        rejected = drive + "\\ObjectLabPrinters\\Rejected\\";
        submission = drive + "\\ObjectLabPrinters\\Submissions\\";
        PDFAdmin = drive + "\\TomSoft Help Admin.pdf";
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
    }

    /**
     * @return the zcorpToPrint
     */
    public String getZcorpToPrint() {
        return zcorpToPrint;
    }

    /**
     * @param zcorpToPrint the zcorpToPrint to set
     */
    public void setZcorpToPrint(String zcorpToPrint) {
        this.zcorpToPrint = zcorpToPrint;
    }

    /**
     * @return the zcorpToBuild
     */
    public String getZcorpToBuild() {
        return zcorpToBuild;
    }

    /**
     * @return the solidscapeToPrint
     */
    public String getSolidscapeToPrint() {
        return solidscapeToPrint;
    }

    /**
     * @return the solidscapeToBuild
     */
    public String getSolidscapeToBuild() {
        return solidscapeToBuild;
    }

    /**
     * @return the zcorpToBuildDir
     */
    public String getZcorpToBuildDir() {
        return zcorpToBuildDir;
    }

    /**
     * @return the objetToPrint
     */
    public String getObjetToPrint() {
        return objetToPrint;
    }

    /**
     * @return the objetToBuild
     */
    public String getObjetToBuild() {
        return objetToBuild;
    }

    /**
     * @return the zcorpPrinted
     */
    public String getZcorpPrinted() {
        return zcorpPrinted;
    }

    /**
     * @return the solidscapePrinted
     */
    public String getSolidscapePrinted() {
        return solidscapePrinted;
    }

    /**
     * @return the objetPrinted
     */
    public String getObjetPrinted() {
        return objetPrinted;
    }

    /**
     * @return the PDF
     */
    public String getPDFAdmin() {
        return PDFAdmin;
    }

        /**
     * @return the PDF
     */
    public String getPDFStudent() {
        return PDFStudent;
    }
    
    /**
     * @return the submitted
     */
    public String getSubmitted() {
        return submitted;
    }

    /**
     * @return the submission
     */
    public String getSubmission() {
        return submission;
    }

    /**
     * @return the drive
     */
    public String getDrive() {
        return drive;
    }

    /**
     * @return the rejected
     */
    public String getRejected() {
        return rejected;
    }
}
