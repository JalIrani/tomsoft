/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javaapplication3.PendingJobs.allFileTableModel;
import static javaapplication3.PendingJobs.dba;

/**
 *
 * @author Alexander
 */
public class PendingUpdater extends Thread {

    public void run() {
        while (!PendingJobs.closing) {
            try {
                TimeUnit.SECONDS.sleep(20);
                ResultSet results = dba.searchPending();
                // clear existing rows
                while (allFileTableModel.getRowCount() > 0) {
                    allFileTableModel.removeRow(0);
                }
                try {
                    // While there are more results to process
                    while (results.next()) {
                        // Build a Vector of Strings for the table row
                        List<String> data = new LinkedList<>();
                        data.add(results.getString("fileName"));
                        data.add(results.getString("firstName") + " " + results.getString("lastName"));
                        data.add(results.getString("printer"));
                        data.add(results.getString("dateStarted"));
                        allFileTableModel.addRow(data.toArray());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ApprovePage.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("20 Seconds have past");
            } catch (InterruptedException e) {
                //Handle exception
                System.out.println("Error");
            }

            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                System.out.println("Current IP address : " + ip.getHostAddress());
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                System.out.print("Current MAC address : ");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println(sb.toString());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
}
