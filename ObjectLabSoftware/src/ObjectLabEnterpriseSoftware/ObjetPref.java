/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morgan
 */
public class ObjetPref extends javax.swing.JFrame {

    boolean errFree;
    SQLMethods dba = new SQLMethods();
    public void ObjetPrefStart() {
        initComponents();
        uncheckBoxes();
        hideEditErrorFields();
        ResultSet res = dba.searchPrinterSettings("objet");
        try {
            if (res.next()) {
                WhiteText.setText(res.getString("materialCostPerUnit"));
                BlackText.setText(res.getString("materialCostPerUnit2"));
                BlueText.setText(res.getString("materialCostPerUnit3"));
                GreyText.setText(res.getString("materialCostPerUnit4"));
                DWhiteText.setText(res.getString("materialCostPerUnit5"));
            } else {
                WhiteText.setText("0");
                BlackText.setText("0");
                BlueText.setText("0");
                GreyText.setText("0");
                DWhiteText.setText("0");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ObjetPref.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ObjetPref.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        setVisible(true);
    }

    public void errorCheck() {
        try {
            float WhitePrice = Float.parseFloat(WhiteText.getText());
            System.out.println(WhitePrice);
        } catch (Exception e) {
            if (WhiteBox.isSelected()) {
                errFree = false;
                WhiteError.setText("*No price set");
                WhiteError.setVisible(true);
            }
        }
        try {
            float BlackPrice = Float.parseFloat(BlackText.getText());
            System.out.println(BlackPrice);
        } catch (Exception e) {
            if (BlackBox.isSelected()) {
                errFree = false;
                BlackError.setText("*No price set");
                BlackError.setVisible(true);
            }
        }
        try {
            float BluePrice = Float.parseFloat(BlueText.getText());
            System.out.println(BluePrice);
        } catch (Exception e) {
            if (BlueBox.isSelected()) {
                errFree = false;
                BlueError.setText("*No price set");
                BlueError.setVisible(true);
            }
        }
        try {
            float GreyPrice = Float.parseFloat(GreyText.getText());
            System.out.println(GreyPrice);
        } catch (Exception e) {
            if (GreyBox.isSelected()) {
                errFree = false;
                GreyError.setText("*No price set");
                GreyError.setVisible(true);
            }
        }

        try {
            float DWhitePrice = Float.parseFloat(DWhiteText.getText());
            System.out.println(DWhitePrice);
        } catch (Exception e) {
            if (dWhiteBox.isSelected()) {
                errFree = false;
                DWhiteError.setText("*No price set");
                DWhiteError.setVisible(true);
            }
        }
    }

    public void getPrice() {
        float White = Float.parseFloat(WhiteText.getText());
        float Black = Float.parseFloat(BlackText.getText());
        float Blue = Float.parseFloat(BlueText.getText());
        float Grey = Float.parseFloat(GreyText.getText());
        float DWhite = Float.parseFloat(DWhiteText.getText());
        if ((White != 0) && (Black != 0) && (Blue != 0) && (Grey != 0) && (DWhite != 0)) {
            //Update
            dba.updatePrinterSettings("Objet", "VeroWhitePlus", "ml", White, "VeroBlackPlus", "ml", Black, "VeroBlue", "ml", Blue, "VeroGrey", "ml", Grey, "DurusWhite", "ml", DWhite);
            System.out.println("Updated Price Settings");
        } else {
            //create new printer
            dba.insertPrinterSettings("Objet", "VeroWhitePlus", "ml", White, "VeroBlackPlus", "ml", Black, "VeroBlue", "ml", Blue, "VeroGrey", "ml", Grey, "DurusWhite", "ml", DWhite);
            System.out.println("Created New Price Settings");
        }
    }

    private void hideEditErrorFields() {
        WhiteError.setVisible(false);
        BlackError.setVisible(false);
        BlueError.setVisible(false);
        GreyError.setVisible(false);
        DWhiteError.setVisible(false);
    }

    private void uncheckBoxes() {
        WhiteBox.setSelected(false);
        BlackBox.setSelected(false);
        BlueBox.setSelected(false);
        GreyBox.setSelected(false);
        dWhiteBox.setSelected(false);

        WhiteText.setEditable(false);
        BlackText.setEditable(false);
        BlueText.setEditable(false);
        GreyText.setEditable(false);
        DWhiteText.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        DWhiteText = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        WhiteError = new javax.swing.JLabel();
        WhiteBox = new javax.swing.JCheckBox();
        BlackError = new javax.swing.JLabel();
        BlackBox = new javax.swing.JCheckBox();
        BlueError = new javax.swing.JLabel();
        BlueBox = new javax.swing.JCheckBox();
        GreyError = new javax.swing.JLabel();
        GreyBox = new javax.swing.JCheckBox();
        DWhiteError = new javax.swing.JLabel();
        dWhiteBox = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        WhiteText = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        BlackText = new javax.swing.JTextField();
        BlueText = new javax.swing.JTextField();
        GreyText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Objet Pricing Settings");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 350, 10));

        DWhiteText.setEditable(false);
        getContentPane().add(DWhiteText, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 259, 129, -1));

        jButton3.setText("Confirm Changes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 311, -1, -1));

        WhiteError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        WhiteError.setForeground(new java.awt.Color(255, 0, 0));
        WhiteError.setText("Error Text");
        getContentPane().add(WhiteError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 96, 130, -1));

        WhiteBox.setText("VeroWhitePlus:");
        WhiteBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WhiteBoxActionPerformed(evt);
            }
        });
        getContentPane().add(WhiteBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, -1, -1));

        BlackError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BlackError.setForeground(new java.awt.Color(255, 0, 0));
        BlackError.setText("Error Text");
        getContentPane().add(BlackError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 137, 130, -1));

        BlackBox.setText("VeroBlackPlus:");
        BlackBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlackBoxActionPerformed(evt);
            }
        });
        getContentPane().add(BlackBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 99, -1));

        BlueError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        BlueError.setForeground(new java.awt.Color(255, 0, 0));
        BlueError.setText("Error Text");
        getContentPane().add(BlueError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 178, 130, -1));

        BlueBox.setText("VeroBlue:");
        BlueBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlueBoxActionPerformed(evt);
            }
        });
        getContentPane().add(BlueBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 176, 99, -1));

        GreyError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        GreyError.setForeground(new java.awt.Color(255, 0, 0));
        GreyError.setText("Error Text");
        getContentPane().add(GreyError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 219, 130, -1));

        GreyBox.setText("VeroGrey:");
        GreyBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GreyBoxActionPerformed(evt);
            }
        });
        getContentPane().add(GreyBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 217, 99, -1));

        DWhiteError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        DWhiteError.setForeground(new java.awt.Color(255, 0, 0));
        DWhiteError.setText("Error Text");
        getContentPane().add(DWhiteError, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 130, -1));

        dWhiteBox.setText("DurusWhite:");
        dWhiteBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dWhiteBoxActionPerformed(evt);
            }
        });
        getContentPane().add(dWhiteBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 258, 99, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel23.setText("Select price(s) to change:");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 68, 170, -1));

        WhiteText.setEditable(false);
        getContentPane().add(WhiteText, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 95, 129, -1));

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 311, -1, -1));

        BlackText.setEditable(false);
        getContentPane().add(BlackText, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 136, 129, -1));

        BlueText.setEditable(false);
        getContentPane().add(BlueText, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 177, 129, -1));

        GreyText.setEditable(false);
        getContentPane().add(GreyText, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 218, 129, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/black and white bg.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 430, 340));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Double vWhite = 0.0;
        Double vBlack = 0.0;
        Double vBlue = 0.0;
        Double vGrey = 0.0;
        Double dWhite = 0.0;
        boolean errFree = true;

        hideEditErrorFields();
        uncheckBoxes();
        File costs = new File("Objet Material Costs.txt");
        try {
            PrintStream output = new PrintStream(costs);
            try {
                vWhite = Double.parseDouble(WhiteText.getText());
            } catch (Exception e) {
                errFree = false;
                WhiteError.setText("*Numbers only please");
                WhiteError.setVisible(true);
            }
            try {
                vBlack = Double.parseDouble(BlackText.getText());
            } catch (Exception e) {
                errFree = false;
                BlackError.setText("*Numbers only please");
                BlackError.setVisible(true);
            }
            try {
                vBlue = Double.parseDouble(BlueText.getText());
            } catch (Exception e) {
                errFree = false;
                BlueError.setText("*Numbers only please");
                BlueError.setVisible(true);
            }
            try {
                vGrey = Double.parseDouble(GreyText.getText());
            } catch (Exception e) {
                errFree = false;
                GreyError.setText("*Numbers only please");
                GreyError.setVisible(true);
            }
            try {
                dWhite = Double.parseDouble(DWhiteText.getText());
            } catch (Exception e) {
                errFree = false;
                DWhiteError.setText("*Numbers only please");
                DWhiteError.setVisible(true);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ObjetPref.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
         try {
         buildMatCosts();
         } catch (FileNotFoundException ex) {
         Logger.getLogger(Objet.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        if (errFree == true) {
            PrintStream output;
            try {
                output = new PrintStream(costs);
                output.println(vWhite);
                output.println(vBlack);
                output.println(vBlue);
                output.println(vGrey);
                output.println(dWhite);
                dba.updatePrinterSettings("Objet", "VeroWhitePlus", "", vWhite, "VeroBlackPlus", "", vBlack, "VeroBlue", "", vBlue, "VeroGrey", "", vGrey, "DurusWhite", "", vGrey);
                System.out.println("Updated Price Settings");
                dispose();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ObjetPref.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void WhiteBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WhiteBoxActionPerformed
        if (WhiteBox.isSelected()) {
            WhiteText.setEditable(true);
        } else {
            WhiteText.setEditable(false);
        }
    }//GEN-LAST:event_WhiteBoxActionPerformed

    private void BlackBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlackBoxActionPerformed
        if (BlackBox.isSelected()) {
            BlackText.setEditable(true);
        } else {
            BlackText.setEditable(false);
        }
    }//GEN-LAST:event_BlackBoxActionPerformed

    private void BlueBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlueBoxActionPerformed
        if (BlueBox.isSelected()) {
            BlueText.setEditable(true);
        } else {
            BlueText.setEditable(false);
        }
    }//GEN-LAST:event_BlueBoxActionPerformed

    private void GreyBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GreyBoxActionPerformed
        if (GreyBox.isSelected()) {
            GreyText.setEditable(true);
        } else {
            GreyText.setEditable(false);
        }
    }//GEN-LAST:event_GreyBoxActionPerformed

    private void dWhiteBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dWhiteBoxActionPerformed
        if (dWhiteBox.isSelected()) {
            DWhiteText.setEditable(true);
        } else {
            DWhiteText.setEditable(false);
        }
    }//GEN-LAST:event_dWhiteBoxActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox BlackBox;
    private javax.swing.JLabel BlackError;
    private javax.swing.JTextField BlackText;
    private javax.swing.JCheckBox BlueBox;
    private javax.swing.JLabel BlueError;
    private javax.swing.JTextField BlueText;
    private javax.swing.JLabel DWhiteError;
    private javax.swing.JTextField DWhiteText;
    private javax.swing.JCheckBox GreyBox;
    private javax.swing.JLabel GreyError;
    private javax.swing.JTextField GreyText;
    private javax.swing.JCheckBox WhiteBox;
    private javax.swing.JLabel WhiteError;
    private javax.swing.JTextField WhiteText;
    private javax.swing.JCheckBox dWhiteBox;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
