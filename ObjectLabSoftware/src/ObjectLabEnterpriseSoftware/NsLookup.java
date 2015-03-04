/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * I dont know what this does yet - Nich
 */
package ObjectLabEnterpriseSoftware;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NsLookup
{

  public String resolve(String host)
  {
    try
    {
      InetAddress inetAddress = InetAddress.getByName(host);
      //when setting up the on computers for first time install must enter the name of the magix computer
      System.out.println("Host: " +
          inetAddress.getHostName());
      System.out.println("IP Address: " +
          inetAddress.getHostAddress());
          return inetAddress.getHostAddress();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
      return null;
    }
  }

}
