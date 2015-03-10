/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Alexander
 */
public class SendEmail {

    private static String fName;
    private static String lName;
    private static String Error;
    private static String FileName;
    private static String Ident;


    public SendEmail(String fname, String lname, String Err, String file, String ID) {
        fName = fname;
        lName = lname;
        Error = Err;
        FileName = file;
        Ident = ID;
    }

    public void Send() {
        System.out.println(Ident);
        
        /* Will need to remove the use of DB querying in this class.
           Left this in here to preserve current working state
           commented this out as well becuase it does not exist:
            ResultSet Res = PendingJobsView.dba.searchPendingWithID(Ident);
            - Nick
        */
        SQLMethods dbconn = new SQLMethods();
        ResultSet Res = dbconn.searchPendingWithID(Ident);
        
        String Email ="";
        /* Coppied this back over from the EmailUtils class to fix the compile error... */
        try {
            while(Res.next()){
                Email = Res.getString("email");
            }
            /* Close our DB connection ASAP -Nick */
            dbconn.closeDBConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("The Email is:" +Email);
        final String username = "towsonuobjectlab@gmail.com";
        final String password = "oblabsoftware";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

            Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            //Students Email Should go here
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Email));
            message.setSubject("Towson University Object Lab: No_Reply");
            message.setText("Dear " + fName +" "+lName + " , \n\nAfter analyzing your file submission, "
                    + FileName + ", we have found an error in your file: \n"
                    + Error + "\nPlease fix this error and resubmit."
                    + "\n\nThank you,\nObject Lab Staff");
        
            Transport.send(message);
            System.out.println("Email Sent");
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
