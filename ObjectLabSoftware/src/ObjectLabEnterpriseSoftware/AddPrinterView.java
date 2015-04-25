package ObjectLabEnterpriseSoftware;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddPrinterView extends javax.swing.JFrame 
{
	private static final String NAME_OF_PAGE = "Add Printer";

    ArrayList<String> currentDevices = UtilController.getListOfPrinters();
    Device device;
    JTextField tfield;
    JLabel tlabel;
    JCheckBox cbox;
    //ArrayList of text fields and labels for adding and removing
    ArrayList<JTextField> fields = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JCheckBox> boxes = new ArrayList<>();
    //Positioning variable for labels and fields
    private int xTF;
    private int xL;
    private int xB;
    private int y;
    private final int YSPACING = 30;
    private final int COUNTMAX = 15;
    private final int COUNTMIN = 3;
    //Current count of labels and fields
    private int count = COUNTMIN;

    public void AddPrinterStart() {
        initComponents();
        fields.add(printerNameTF);
        fields.add(fileExtensionTF);
        fields.add(fieldTF0);
        labels.add(printerNameL);
        labels.add(fileExtensionL);
        labels.add(fieldL0);
        boxes.add(numberCB);
        xTF=fieldTF0.getX();
        xL=fieldL0.getX();
        xB=numberCB.getX();
        y=fieldTF0.getY();
        setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        printerNameTF = new javax.swing.JTextField();
        fileExtensionTF = new javax.swing.JTextField();
        printerNameL = new javax.swing.JLabel();
        removeFieldButton = new javax.swing.JButton();
        fieldL0 = new javax.swing.JLabel();
        addFieldButton = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        fieldTF0 = new javax.swing.JTextField();
        numberCB = new javax.swing.JCheckBox();
        studentSubmissionCB = new javax.swing.JCheckBox();
        fileExtensionL = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        EditMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(UtilController.getPageName(NAME_OF_PAGE));
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(530, 475));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Add Printer");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 21, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, 470, 10));
        getContentPane().add(printerNameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 124, -1));
        getContentPane().add(fileExtensionTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 124, -1));

        printerNameL.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        printerNameL.setText("Printer Name:");
        getContentPane().add(printerNameL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        removeFieldButton.setText("Remove Field");
        removeFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFieldButtonActionPerformed(evt);
            }
        });
        getContentPane().add(removeFieldButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 100, 20));

        fieldL0.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fieldL0.setText("Field #1:");
        getContentPane().add(fieldL0, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        addFieldButton.setText("Add Field");
        addFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFieldButtonActionPerformed(evt);
            }
        });
        getContentPane().add(addFieldButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 90, 20));

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        getContentPane().add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 90, 20));

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        getContentPane().add(cancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 100, 20));

        fieldTF0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldTF0ActionPerformed(evt);
            }
        });
        getContentPane().add(fieldTF0, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 124, -1));

        numberCB.setText("Number Value");
        getContentPane().add(numberCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, -1, -1));

        studentSubmissionCB.setSelected(true);
        studentSubmissionCB.setText("Require Student Submission (recommended)");
        studentSubmissionCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentSubmissionCBActionPerformed(evt);
            }
        });
        getContentPane().add(studentSubmissionCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, -1, 20));

        fileExtensionL.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fileExtensionL.setText("File Ext.:");
        getContentPane().add(fileExtensionL, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ObjectLabEnterpriseSoftware/images/white_bg.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 370));

        EditMenu.setText("Help");

        jMenuItem1.setText("Contents");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        EditMenu.add(jMenuItem1);

        jMenuBar1.add(EditMenu);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
	    //home.studentSubmissionButton.setVisible(false);
	    dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed


    private void addFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFieldButtonActionPerformed
	boolean add = true;
	for (int i = 0; i < count; i++) {
	    if (fields.get(i).getText().equals("")) {
		add = false;
		JOptionPane.showMessageDialog(this, "Please fill out the previous fields before adding new ones.");
		break;
	    }
	}

	if (count <= COUNTMAX && add == true) {
	    tfield = new JTextField();
	    tfield.setName("tfield" + (count - 1));
	    fields.add(tfield);
	    tlabel = new JLabel();
	    tlabel.setName("fieldL" + (count - 1));
	    tlabel.setText("Field #" + (count - 1));
	    labels.add(tlabel);
	    cbox = new JCheckBox();
	    cbox.setName("numberCB" + (count - 2));
	    cbox.setText("Number Value");
	    boxes.add(cbox);
	    y += YSPACING;
	    getContentPane().add(fields.get(count), new org.netbeans.lib.awtextra.AbsoluteConstraints(xTF, y, 124, -1), 4);
	    getContentPane().add(labels.get(count), new org.netbeans.lib.awtextra.AbsoluteConstraints(xL, y, -1, -1), 3);
	    getContentPane().add(boxes.get(count - 2), new org.netbeans.lib.awtextra.AbsoluteConstraints(xB, y, -1, -1), 5);
	    fields.get(count).setVisible(true);
	    labels.get(count).setVisible(true);
	    boxes.get(count - 2).setVisible(true);
	    count++;
	    revalidate();
	    repaint();
	}
    }//GEN-LAST:event_addFieldButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
	boolean exit = true;
	device = new Device();
	//Check for empty text fields
	for (int i = 0; i < count; i++) {
	    if (fields.get(i).getText().equals("")) {
		exit = false;
		JOptionPane.showMessageDialog(this, "Cannot save with empty fields!");
		break;
	    }
	}
	//Check for duplicate entries
	if (exit == true) {
	    for (int i = 0; i < count; i++) {
		for (int j = 0; j < count; j++) {
		    if (i != j && fields.get(i).getText().equals(fields.get(j).getText())) {
			exit = false;
			JOptionPane.showMessageDialog(this, "Cannot save with duplicate entries!");
			break;
		    }
		}
		if(exit==false)
		    break;
	    }
	}
	//Save printer device here
	if (exit == true) {
	    //First two textfields are name and file extension.
	    //Create new device object using these.
	    device = new Device(printerNameTF.getText(),
		    new ArrayList(Arrays.asList(fileExtensionTF.getText().split(" "))));
	    //Fields start at index 2
	    for (int i = 2; i < count; i++) {
		//If number value is checked make value double, if not then string
		if(boxes.get(i-2).isSelected()==true)
                    device.addField(fields.get(i).getText(), new Double("0"));
		else
		    device.addField(fields.get(i).getText(), "");
	    }
            //device.setRequireSubmission(studentSubmissionCB.isSelected());
            if(currentDevices.contains(device.getDeviceName()))
                JOptionPane.showMessageDialog(this, "Could not save! Device '" + device.getDeviceName() + "' already exists!");
            else if(UtilController.addDevice(device)==true){
                JOptionPane.showMessageDialog(this, "Device '"+device.getDeviceName()+"' was saved and added to the printer list!");
                dispose();
            }else
                JOptionPane.showMessageDialog(this, "There was an error while saving the printer.");
	}
    }//GEN-LAST:event_saveBtnActionPerformed

        private void removeFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFieldButtonActionPerformed
	    if (count != COUNTMIN) {
		y -= YSPACING;
		count--;
		getContentPane().remove(fields.get(count));
		getContentPane().remove(labels.get(count));
		getContentPane().remove(boxes.get(count-2));
		fields.remove(count);
		labels.remove(count);
		boxes.remove(count-2);
		revalidate();
		repaint();
	    }
        }//GEN-LAST:event_removeFieldButtonActionPerformed

        private void fieldTF0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldTF0ActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_fieldTF0ActionPerformed

    private void studentSubmissionCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentSubmissionCBActionPerformed
        if(studentSubmissionCB.isSelected()==false){
            if(JOptionPane.showConfirmDialog(null, "Continue? Students will NOT be required to submit reports for this device if unchecked (which IS recommended for lazer cutters). "
                    + "We recommend selecting 'No' and keeping checked if unsure.","Warning",JOptionPane.YES_OPTION)==JOptionPane.YES_OPTION)
                studentSubmissionCB.setSelected(false);
            else
                studentSubmissionCB.setSelected(true);
        }
    }//GEN-LAST:event_studentSubmissionCBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu EditMenu;
    private javax.swing.JButton addFieldButton;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel fieldL0;
    private javax.swing.JTextField fieldTF0;
    private javax.swing.JLabel fileExtensionL;
    private javax.swing.JTextField fileExtensionTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox numberCB;
    private javax.swing.JLabel printerNameL;
    private javax.swing.JTextField printerNameTF;
    private javax.swing.JButton removeFieldButton;
    private javax.swing.JButton saveBtn;
    private javax.swing.JCheckBox studentSubmissionCB;
    // End of variables declaration//GEN-END:variables
}
