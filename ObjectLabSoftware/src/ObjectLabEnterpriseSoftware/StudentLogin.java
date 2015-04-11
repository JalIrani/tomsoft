/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectLabEnterpriseSoftware;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gateway
 */
public class StudentLogin {
    public static void main(String[] args) {//in place of the Submit button
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        do{
            String id = JOptionPane.showInputDialog(new java.awt.Frame(), "Enter Towson ID:");
            
            if (id.length() == 7) {
                if(true){//if statement to check if the TUID is in the Database
                    //if it is recognised then lead to the Student_Submission page
                    JOptionPane.showMessageDialog(new java.awt.Frame(), "Correct");
                    break;
                } else {
                    JOptionPane.showMessageDialog(new java.awt.Frame(), "Your ID is not recognized.");
                    //if it is not recognised then prompt them to enter their information (stored in database)
                }
            } else {
                JOptionPane.showMessageDialog(new java.awt.Frame(), "Incorrect ID! Please enter a valid Towson ID!");
            }
        }while(true);
        
        frame.setVisible(true);
        frame.pack();
    }
}
