/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class Calculations {

    static SQLMethods dba;
    static SolidscapePref SSPref;
    static String[][] array;
    static ResultSet res;

    public Calculations() {
        array = new String[50][2];
        dba = new SQLMethods();
    }

    public static void BuildtoProjectCost(String BuildName, String Printer, double priceOfBuild) {
        int x = 0;
        double totalVolume = 0;
        res = dba.searchPendingByBuildName(BuildName);
        //System.out.println("OK ... associated thingies");
        try {
            while (res.next()) {
                array[x][0] = res.getString("idJobs");
                array[x][1] = res.getString("volume");
                totalVolume = totalVolume + Double.parseDouble(res.getString("volume"));
                x++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculations.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int y = 0; y < x; y++) {
            // (Vol(y)/totalVolume) * Cost = z
            double Cost = (Double.parseDouble(array[y][1]) / totalVolume) * priceOfBuild;
            dba.updatePendingJobByCost(array[y][0], Cost);
        }
    }


    public static double ZcorpCost(double cubicInches) {
        double Price = 0;
        ResultSet res = dba.searchPrinterSettings("Zcorp");
        try {
            if (res.next()) {
                Price = Double.parseDouble(res.getString("materialCostPerUnit"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SolidscapePref.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cubicInches*Price;
    }

    public static double SolidscapeCost(int days, int hours, int minutes) {
        double Price = 0;
        ResultSet res = dba.searchPrinterSettings("Solidscape");
        try {
            if (res.next()) {
                Price = Double.parseDouble(res.getString("materialCostPerUnit"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SolidscapePref.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get cost from database;
        double allTime = ((days * 24) + hours + (minutes / 60));
        return allTime * Price; //* cost
    }

    public static double ObjetCost(Double buildConsumed, String MaterialType) {
        ResultSet res = dba.searchPrinterSettings("Objet");
        double Price = 0;
        try {
            if (res.next()) {
                switch (MaterialType) {
                    case "VeroWhitePlus":
                        Price = Double.parseDouble(res.getString("materialCostPerUnit"));
                        break;
                    case "VeroBlackPlus":
                        Price = Double.parseDouble(res.getString("materialCostPerUnit2"));
                        break;
                    case "VeroBlue":
                        Price = Double.parseDouble(res.getString("materialCostPerUnit3"));
                        break;
                    case "VeroGrey":
                        Price = Double.parseDouble(res.getString("materialCostPerUnit4"));
                        break;
                    case "DurusWhite":
                        Price = Double.parseDouble(res.getString("materialCostPerUnit5"));
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception occured.");
        }
        double percentCartUsed = buildConsumed / 1000;
        return Price * percentCartUsed;
    }

}
