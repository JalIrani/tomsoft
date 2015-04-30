/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
//import static javafx.print.PrintColor.COLOR;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mike
 */
public class ArchiveView extends javax.swing.JFrame {

    JFrame report;
   
    File archiveDirectory = null;
    AdminSettingsView settings;
    /**
     * Creates new form ArchiveView
     */
    
     public ArchiveView() {
        getContentPane().setBackground(Color.white);
        initComponents();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public ArchiveView(final JFrame f) {
        report = f;
        getContentPane().setSize(600, 200);
        getContentPane().setBackground(Color.white);
        initComponents();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        report.setEnabled(false);
        settings = new AdminSettingsView();
        this.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                report.setEnabled(true);  
                settings.AdminSettingsViewStart();
                dispose();
            }
        });
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jSeparator1 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Arhiving");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(650, 119));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 500, 10));

        jTextField1.setEditable(false);
        jTextField1.setText("C:\\Sync\\ObjectLabPrinters");
        jTextField1.setName("fromFolderLocation"); // NOI18N
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 280, -1));

        jLabel1.setText("Archive Destination:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jLabel1.getAccessibleContext().setAccessibleName("");

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Wipe Data");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel2.setText("Archiving Data From: ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jTextField2.setEditable(false);
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 280, -1));

        jButton2.setText("Start Archiving");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/images/back_arrow_button.png"))); // NOI18N
        jButton3.setToolTipText("Back");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Archive");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/images/white_bg.jpg"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 560, 260));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       JFileChooser fileopen = new JFileChooser();  
		//in brackets, add Syncthing directory or new Drive's address for default location
        fileopen.setAcceptAllFileFilterUsed(false);
        fileopen.setMultiSelectionEnabled(false);
        fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int ret = fileopen.showDialog(null, "Select Directory");

        if (ret == JFileChooser.APPROVE_OPTION) 
		{
            archiveDirectory = fileopen.getSelectedFile();
            //Inputs the file location into the textbox "fileName"
            jTextField2.setText(archiveDirectory.toString().replaceAll("'", ""));
           
			
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        
        if(jCheckBox1.isSelected()){
            int j = JOptionPane.showConfirmDialog(null, "WARNING: You are about to wipe all files.\n Do you wish to continue?", "Wipe Data", JOptionPane.YES_NO_OPTION);
            if(j == 0){
                jCheckBox1.setSelected(true);
            }else{
                jCheckBox1.setSelected(false);
            }
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       if(archiveDirectory != null){
        if(archiveDirectory.isDirectory()){
            UtilController.archive(jTextField1.getText(), jTextField2.getText());
            if(jCheckBox1.isSelected()){
                JOptionPane.showMessageDialog(null, "Archiving Started\nData is being wiped");
                UtilController.clearData();
                //WIPE DATA AND ARCHIVE
            }else{
             JOptionPane.showMessageDialog(null, "Archiving Started\nData will not be wiped");
             
               //JUST DO ARCHIVE
            }
            report.setEnabled(true);
            report.setFocusable(true);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Invaild Directory, Please choose another Directory");
        }
        
       }else{
            JOptionPane.showMessageDialog(null, "No directory Selected, Please choose a directory");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        settings.AdminSettingsViewStart();
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ArchiveView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArchiveView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArchiveView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArchiveView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArchiveView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
