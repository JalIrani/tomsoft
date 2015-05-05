package ObjectLabEnterpriseSoftware;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.awt.*;

public class PasswordDialogView extends javax.swing.JFrame
{

    private static final String NAME_OF_PAGE = "Admin Password";
    private static MainView home = new MainView();
    public static boolean adminLoginStatus;

    public PasswordDialogView()
    {
        initComponents();
        passwordError.setVisible(false);
		Password.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent capsLockCheck)
            {
                if(capsLockDetector())
				{
					passwordError.setVisible(true);
					passwordError.setText("WARNING: Caps Lock is on");
				}
					passwordError.setText("");
            }
        });
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
               dispose();
               home.setPrintersVisible(false);
               home.showStudentOptions();
               home.setVisible(true);
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

        jLabel1 = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        SubmitButton = new javax.swing.JButton();
        Password = new javax.swing.JPasswordField();
        passwordError = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(UtilController.getPageName(NAME_OF_PAGE));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Administrator Password:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                CancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        SubmitButton.setText("Submit");
        SubmitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SubmitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(SubmitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, 20));
        getContentPane().add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 136, -1));

        passwordError.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passwordError.setForeground(new java.awt.Color(255, 0, 0));
        passwordError.setText("Invalid password");
        getContentPane().add(passwordError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/images/white_bg.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 210, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
        /*Hashes the predefined password and the password entered into the
         *JPasswordField and then compares the two
         */
        boolean wasLoginSuccessful = UtilController.checkAdminLogin(new String(Password.getPassword()));

        if (wasLoginSuccessful)
        {
            System.out.println("Password passed!");
            adminLoginStatus = true;
            dispose();
            home.resetAdminMode();
        } else
        {
            passwordError.setVisible(!wasLoginSuccessful);
            System.out.println("Password failed!");
            adminLoginStatus = false;
        }
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_CancelButtonActionPerformed
    {//GEN-HEADEREND:event_CancelButtonActionPerformed
        dispose();
        home.setPrintersVisible(false);
        home.showStudentOptions();
        home.setVisible(true);
    }//GEN-LAST:event_CancelButtonActionPerformed

    private boolean capsLockDetector()
    {
        //Detects if Caps Lock is on. If it is it returns a warning
        if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
        {
            return true;
        }
		return false;
    }

    /**
     * @param args the command line arguments
     */
    /*
     Eventually update passwordText array to take a constant for its size
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(PasswordDialogView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(PasswordDialogView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(PasswordDialogView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(PasswordDialogView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new PasswordDialogView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JPasswordField Password;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel passwordError;
    // End of variables declaration//GEN-END:variables
}
