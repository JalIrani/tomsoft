/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ObjectLabEnterpriseSoftware;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class NewStudentView extends javax.swing.JFrame {

    /**
     * Creates new form NewStudentView
     */
    
    public static SQLMethods dba;
    TomSoftMainView home;
    
    public void NewStudentMainStart(){
        
        home = new TomSoftMainView();
        dba = new SQLMethods();
        initComponents();
        
        
        //have database send in saved courses from admin page 
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewStudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // close sockets, etc
               //home.studentSubmissionButton.setVisible(false);
                home.setPrintersVisible(false);
                home.setVisible(true);
            }
        });
        
        setVisible(true);
    }
    //public NewStudentView() {  
    //}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newStudentLabel = new javax.swing.JLabel();
        firstName = new javax.swing.JLabel();
        lastName = new javax.swing.JLabel();
        netID = new javax.swing.JLabel();
        tuID = new javax.swing.JLabel();
        emailExtension = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        tuIDEntry = new javax.swing.JTextField();
        firstNameEntry = new javax.swing.JTextField();
        lastNameEntry = new javax.swing.JTextField();
        emailEntry = new javax.swing.JTextField();
        netIDlEntry = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newStudentLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        newStudentLabel.setForeground(new java.awt.Color(255, 255, 255));
        newStudentLabel.setText("New Student");
        getContentPane().add(newStudentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        firstName.setForeground(new java.awt.Color(255, 255, 255));
        firstName.setText("First Name:");
        getContentPane().add(firstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        lastName.setForeground(new java.awt.Color(255, 255, 255));
        lastName.setText("Last Name:");
        getContentPane().add(lastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, -1));

        netID.setForeground(new java.awt.Color(255, 255, 255));
        netID.setText("Net ID:");
        getContentPane().add(netID, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        tuID.setForeground(new java.awt.Color(255, 255, 255));
        tuID.setText("TU ID:");
        getContentPane().add(tuID, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));

        emailExtension.setForeground(new java.awt.Color(255, 255, 255));
        emailExtension.setText("ex:  jsmith1");
        getContentPane().add(emailExtension, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, 20));

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        getContentPane().add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, -1, -1));
        getContentPane().add(tuIDEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 100, -1));
        getContentPane().add(firstNameEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 100, -1));
        getContentPane().add(lastNameEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 100, -1));

        emailEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailEntryActionPerformed(evt);
            }
        });
        getContentPane().add(emailEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 100, -1));

        netIDlEntry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/backgroundrender.png"))); // NOI18N
        getContentPane().add(netIDlEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        
        
        String tuID = tuIDEntry.getText();
        String firstName = firstNameEntry.getText();
        String lastName = lastNameEntry.getText();
        String email = emailEntry.getText();
        
        boolean exit=true;
	    if(tuID.equals("")||firstName.equals("")||lastName.equals("")||email.equals("")){
		    JOptionPane.showMessageDialog(this,"Cannot save with empty fields!");
		    exit=false;
	    }
	    if(exit==true)
                    home.setPrintersVisible(true);
                    home.setVisible(true);
		    dispose();
        
    }//GEN-LAST:event_submitActionPerformed

    private void emailEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailEntryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailEntryActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewStudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewStudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewStudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewStudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewStudentView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailEntry;
    private javax.swing.JLabel emailExtension;
    private javax.swing.JLabel firstName;
    private javax.swing.JTextField firstNameEntry;
    private javax.swing.JLabel lastName;
    private javax.swing.JTextField lastNameEntry;
    private javax.swing.JLabel netID;
    private javax.swing.JLabel netIDlEntry;
    private javax.swing.JLabel newStudentLabel;
    private javax.swing.JButton submit;
    private javax.swing.JLabel tuID;
    private javax.swing.JTextField tuIDEntry;
    // End of variables declaration//GEN-END:variables
}

