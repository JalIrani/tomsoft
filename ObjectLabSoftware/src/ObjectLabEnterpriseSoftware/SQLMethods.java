package ObjectLabEnterpriseSoftware;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
/**
 * FYI I used select * so far in most cases since I don't know what you want it limited to. Some refinement will be needed. 
 * @author Database
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
public class SQLMethods 
{
    /* Same url and connection to the DB until it is closed */
    private final String url;
    private Connection conn;
    private ResultSet res;
    private PreparedStatement stmt;

    public SQLMethods() 
    {
        /* To resolve hostname to an IP adr */
        File f = new File("C:\\Sync\\computername.txt");
        String line, ip = "";
        
        try 
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(f.getAbsolutePath());
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) 
                ip = line;

            // Always close files.
            bufferedReader.close();
        }
        catch (IOException ex) 
        {
            System.out.println("Couldn't read file! IOException!");
			ex.printStackTrace();
        }
        url = "jdbc:mysql://" + ip + ":3306/";
        connectToDatabase("com.mysql.jdbc.Driver", url + "jobsdb", "admin", "password");
    }
    
    private void connectToDatabase(String driver, String urlDatabaseName, String userName, String pw)
    {
        try 
        {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(urlDatabaseName, userName, pw);
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Driver class not found / created. Exception!\n" + e);
        } 
        catch (InstantiationException | IllegalAccessException | SQLException e) 
        {
            System.out.println(e);
        }
    }
    
    public void closeDBConnection()
    {
        try 
        {
            conn.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    	// Begining of Select Methods
	// _______________________________________________________________________________________________________________________
	public ResultSet selectAllFromTable(String table) {// select entire table
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM " + table);
			res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

	public ResultSet selectAllPrintStatus(String status) {// select all info from job based onstatus ((probably that not useful)
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM job WHERE status= ?");
			stmt.setString(1, status);
			res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

	public ResultSet searchPrinterFilesTypes(String printer) {// selects the filetypes for the printer
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT file_extension FROM printer WHERE printer_name = ?");
			stmt.setString(1, printer);
			res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

	public ResultSet searchJobsStatusPrinter(String status, String printer) // returns filename,first name,lastname ,submission_date, printer for based off status and printer
	{
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT Job.file_name, Users.first_name, Users.last_name, Job.submission_date ,Job.printer_name  "
							+ "FROM Job, Users  "
							+ "WHERE status = ? AND printer_name = ? AND Users.towson_id = Job.student_id");
			stmt.setString(1, status);
			stmt.setString(2, printer);
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public ResultSet searchPrintersByBuildName(int buildId) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM job WHERE build_id = ?");
			stmt.setInt(1, buildId);
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet selectIDFromJob(int id) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM Job Where student_id = ?");
			stmt.setInt(1, id);
                        res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

	public ResultSet searchJobsByBuildName(String buildName) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM job, printer_build "
							+ "WHERE buildName = ?  AND printer_build.build_id= Job.build_id");
			stmt.setString(1, buildName);
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet searchID(String fileName) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT submission_id, file_path FROM job WHERE file_name = ? ");
			stmt.setString(1, fileName);
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet searchPrinterSettings(String printer) {
        res = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM printers WHERE printer = ?");
            stmt.setString(1, printer);
            res = stmt.executeQuery();
            System.out.println(stmt);
        } catch (SQLException e) {
            System.err.println("SQL Execution Error.");
        }
        return res;
    }

	public ResultSet selectClasses() {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM class ");
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet selectPassFromadmin(String admin) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT pass FROM admin Where username = ? ");
			stmt.setString(1, admin);
			res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

	public ResultSet selectJobForClass(int classes, String status) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("SELECT * FROM  job Where class_id = ? AND status = ? ");
			stmt.setInt(1, classes);
			stmt.setString(2, status);
			res = stmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("SQL Execution Error.");
		}
		return res;
	}

        public ResultSet selectColumnNames(String printer) {
        res = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM Custom_printer_column_names Where printer_name = ? " );
            stmt.setString(1, printer);
            res = stmt.executeQuery();
            
        } catch (SQLException e) {
            System.err.println("SQL Execution Error.");
        }
        return res;
    }
        
public ResultSet selectBuildData(int id) {
        res = null;
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM column_build_data Where build_id = ? " );
            res = stmt.executeQuery();
            stmt.setInt(1, id);
        } catch (SQLException e) {
            System.err.println("SQL Execution Error.");
        }
        return res;
    }
	// END OF SELECT METHODS
	// _____________________________________________________________________________________________________________________

	// BEGGINING OF INSERT METHODS
	// _____________________________________________________________________________________________________________________

	public void insertIntoClasses(String className, String classSection, String professor) {
		try {
			stmt = conn.prepareStatement("INSERT INTO class (class_name, class_section, professor) values (?,?,?)");
			stmt.setString(1, className);
			stmt.setString(2, classSection);
			stmt.setString(3, professor);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoJob(String filename, String filePath, int class_id,
			int user_id, String printer, int buildId,String comment) {
		try {
			stmt = conn.prepareStatement("INSERT INTO job (file_name, file_path, class_id, student_id, printer_name, submission_date,"
							+ " build_id, status, comment) values (?,?,?,?,?,NOW(),?,'pending',?)");
			stmt.setString(1, filename);
			stmt.setString(2, filePath);
			stmt.setInt(3, class_id);
			stmt.setInt(4, user_id);
			stmt.setString(5, printer);
                        stmt.setInt(6, buildId);
			stmt.setString(7, comment);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoPrinter(String printer, String fileExtention) {
		try {
			stmt = conn.prepareStatement("INSERT INTO printer (printer_name, file_extension, current) values (?,?, 'current')");
			stmt.setString(1, printer);
			stmt.setString(2, fileExtention);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoUsers(int idusers, String firstName, String lastName,
			String email) {
		try {
			stmt = conn.prepareStatement("INSERT INTO users(towson_id, first_name, last_name, email) values (?,?,?,?)");
			stmt.setInt(1, idusers);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setString(4, email);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoAdmin(int user_id, String userName, String pass) {
		try {
			stmt = conn.prepareStatement("insert into admin (user_id, username, date_created, pass) values (?,?,NOW(),?)");
			stmt.setInt(1, user_id);
			stmt.setString(2, userName);
			stmt.setString(3, pass);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoBuild(String buildname, int runtime, int models, String printer) {
		try {
			stmt = conn.prepareStatement("insert into printer_build ( build_name, date_created, total_runtime_seconds, number_of_models, printer_name) values (?,NOW(),0, ?, ?)");
			stmt.setString(1, buildname);
			stmt.setInt(2, models);
			stmt.setString(3, printer);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoColumn(int buildid, int columnid, String data) {
		try {
			stmt = conn.prepareStatement("insert into column_build_data ( build_id, column_name_id, column_field_data) values (?,?, ?)");
			stmt.setInt(1, buildid);
			stmt.setInt(2, columnid);
			stmt.setString(3, data);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoCustom(String printer, String name) {
		try {
			stmt = conn.prepareStatement("insert into custom_printer_column_names ( printer_name, custom_field_name) values (?,?)");
			stmt.setString(1, printer);
			stmt.setString(2, name);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// END OF INSERT METHODS
	// _____________________________________________________________________________________________________________________

	// BEGGINIGNG OF UPDATE METHODS
	// _____________________________________________________________________________________________________________________
	public void changeJobStatus(String file_name, String status)
			throws SQLException {
		stmt = this.conn.prepareStatement("UPDATE job SET status = ? WHERE file_name = ?");
		stmt.setString(1, status);
		stmt.setString(2, file_name);
		stmt.executeUpdate();
	}

	public void updateJobFLocation(String filename, String fLocation) {
		try {
			stmt = this.conn.prepareStatement("UPDATE job SET file_path = ?" + " WHERE file_name = ?");
			stmt.setString(1, fLocation);
			stmt.setString(2, filename);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public void updateJobVolume(String file_name, double volume) {
		try {
			stmt = this.conn.prepareStatement("UPDATE job SET volume = "
					+ volume + " WHERE file_name = ?");
			stmt.setString(1, file_name);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public void updatePendingJobsBuildName(int build, String fileName) {
		try {
			stmt = this.conn.prepareStatement("UPDATE job SET build_id = ? WHERE file_name = ?");
			stmt.setInt(1, build);
			stmt.setString(2, fileName);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public void updateFirstName(String updatedFirstName, int id) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("UPDATE users SET first_name = ? WHERE towson_id = ?");
			stmt.setString(1, updatedFirstName);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLastName(String updatedLastName, int id) {
		res = null;
		try {
			stmt = this.conn.prepareStatement("UPDATE users SET last_name = ? WHERE towson_id = ?");
			stmt.setString(1, updatedLastName);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(String statusUpdate, int primaryKey) {
		res = null;
		try {
			stmt = conn.prepareStatement("UPDATE job SET status = ? WHERE submission_id = ?  ");
			stmt.setString(1, statusUpdate);
			stmt.setInt(2, primaryKey);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePassword(String password, String username) {
		res = null;
		try {
			stmt = conn.prepareStatement("UPDATE admin SET pass= ? WHERE username= ?  ");
			stmt.setString(1, password);
			stmt.setString(2, username);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateEmail(String newEmail, int id) {
		res = null;
		try {
			stmt = conn.prepareStatement("UPDATE users SET email= ? WHERE towson_id= ?  ");
			stmt.setString(1, newEmail);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// END OF UPDATE METHODS
	// _____________________________________________________________________________________________________________________

	// BEGINGING OF DELETE METHODS
	// _____________________________________________________________________________________________________________________

	public void deletebyID(int id){
		try{
                    stmt = this.conn.prepareStatement("DELETE FROM job WHERE submission_id = ?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                } catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteByBuildId(int buildid){
		try{
                    stmt = this.conn.prepareStatement("DELETE FROM Job WHERE build_id = ?");
                    stmt.setInt(1, buildid);
                    stmt.executeUpdate();
                }
                 catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromJob(String filename) {
		try {
			stmt = conn.prepareStatement("Delete From job WHERE file_name = ? ");
			stmt.setString(1, filename);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePrinter(String printerName) {
		try {
			stmt = conn.prepareStatement("DELETE FROM printer WHERE printer_name = ? ");
			stmt.setString(1, printerName);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromUser(int id) {
		try {
			stmt = conn.prepareStatement("DELETE FROM users WHERE towson_id = ? ");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromClass(int class_id) {
		try {
			stmt = conn.prepareStatement("DELETE FROM class WHERE class_id = ? ");
			stmt.setInt(1, class_id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFromAdmin(int id) {
		try {
			stmt = conn.prepareStatement("delete from admin where user_id= ? ");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFromBuild(String buildName)
	{
		try {
			stmt=this.conn.prepareStatement("DELETE FROM printer_build WHERE build_name = ?");
	    	stmt.setString(1, buildName); 
	    	stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
			

		
		
	}

	// END OF DELETE METHODS
	// _____________________________________________________________________________________________________________________

	public void runQuery(String query) {
		res = null;
		try {
			stmt = this.conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
        
    public void insertPrinterSettings(String printer, String Type1, String Unit1, float Cost1, String Type2, String Unit2, float Cost2,
            String Type3, String Unit3, float Cost3, String Type4, String Unit4, float Cost4, String Type5, String Unit5, float Cost5) {
        try {
            stmt = this.conn.prepareStatement(
                    "INSERT INTO printers "
                    + "(printer, "
                    + "materialType, "
                    + "materialUnit, "
                    + "materialCostPerUnit, "
                    + "materialType2, "
                    + "materialUnit2, "
                    + "materialCostPerUnit2, "
                    + "materialType3, "
                    + "materialUnit3, "
                    + "materialCostPerUnit3, "
                    + "materialType4, "
                    + "materialUnit4, "
                    + "materialCostPerUnit4)"
                    + "VALUES ('" + printer + "', '" + Type1 + "', '" + Unit1 + "', '" + Cost1 + "', '" + Type2 + "', '" + Unit2 + "', "
                    + "'" + Cost2 + "', '" + Type3 + "', '" + Unit3 + "', '" + Cost3 + "', '" + Type4 + "', '" + Unit4 + "', '" + Cost4 + "')");

            stmt.executeUpdate();
            System.out.println("Successfully inserted value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getReport(int reportID) 
    {
        res = null;
        try 
        {
            switch (reportID){
                case 0:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, monobinder, yellowBinder, magentaBinder,\n" +
        "cyanBinder, cubicInches, noModels, runTime "
                        + " from Zcorp "
                        + "order by "
                        + "dateRun;"
                    );
                    break;
                case 1:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, daterun, buildconsumed, supportconsumed, noModels,\n" +
                        "buildMaterials from objet "+
                        " order by "+
                        " daterun;"
                    );
                    break;
                case 2:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, noModels, runtime from solidscape " +
                        " order by daterun;"
                    );
                    break;
                case 3:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, monobinder, yellowBinder, magentaBinder,\n" 
						+ "cyanBinder, cubicInches, noModels, runTime "
                        + " from Zcorp "
                        + "order by "
                        + "dateRun;"
                    );
                    break;
            }
            
            
            res = stmt.executeQuery();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return res;
    }
    
    public ResultSet getReport(String column, String value, int reportID) 
    {
        res = null;
        try 
        {
            switch (reportID){
                case 0:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, monobinder, yellowBinder, magentaBinder,\n" +
        "cyanBinder, cubicInches, noModels, runTime "
                        + " from Zcorp "
                        + " where "
                        + column + " = '" + value + "' "
                        + "order by "
                        + "dateRun;"
                    );
                    break;
                case 1:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, daterun, buildconsumed, supportconsumed, noModels,\n"
                        + "buildMaterials from objet "
                        + " where "
                        + column + " = '" + value + "' "
                        + " order by "
                        + " daterun;"
                    );
                    break;
                case 2:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, noModels, runtime from solidscape "
                        + " where "
                        + column + " = '" + value + "' "
                        + " order by daterun;"
                        
                    );
                    break;
                case 3:
                    stmt = this.conn.prepareStatement
                    (
                        "select buildname, dateRun, monobinder, yellowBinder, magentaBinder,\n" +
        "cyanBinder, cubicInches, noModels, runTime "
                        + " from Zcorp "
                        + " where "
                        + column + " = '" + value + "' "
                        + "order by "
                        + "dateRun;"
                    );
                    break;
            }
            
            res = stmt.executeQuery();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return res;
    }
    
    public ResultSet searchPending() 
    {
        res = null;
        try 
        {
            stmt = this.conn.prepareStatement
            (
                "SELECT filename, firstName, lastName, printer, dateStarted "
                + "FROM pendingjobs "
                + "WHERE "
                + "status = 'pending'"
            );
            
            res = stmt.executeQuery();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return res;
    }

    public ResultSet searchSolidscapeByBuildName(String buildName) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT idSolidscape "
                    + "FROM solidscape "
                    + "WHERE "
                    + "buildName = ?");
            stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchObjetByBuildName(String buildName) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT idObjet "
                    + "FROM objet "
                    + "WHERE "
                    + "buildName = ?");
            stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchZCorpByBuildName(String buildName) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT idZcorp "
                    + "FROM zcorp "
                    + "WHERE "
                    + "buildName = ?");
            stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public ResultSet searchCompleted(String query, String column) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM completedJobs "
                    + "WHERE "
                    + column + " LIKE '%" + query + "%'");
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchPrintersByBuildName(String buildName, String printer) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM "
                    + printer + " "
                    + "WHERE "
                    + "buildName = ?");
            
            stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchPendingWithID(String ID) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM pendingjobs "
                    + "WHERE "
                    + "status = 'pending' "
                    + "AND idJobs = ?");
            stmt.setString(1, ID);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchIncompletePendingByBuild(String buildName) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM pendingjobs "
                    + "WHERE "
                    + "status = 'incomplete' "
                    + "AND buildName = ?");
            stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchPendingByBuildName(String buildName) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM pendingjobs "
                    + "WHERE "
                    + "buildName = '" + buildName + "'");
            //stmt.setString(1, buildName);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchID(String table, String firstName, String lastName, String fileName, String dateStarted) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT idJobs, filePath "
                    + "FROM " + table + " "
                    + "WHERE "
                    + "firstName = ? "
                    + "AND lastName = ? "
                    + "AND fileName = ? "
                    + "AND dateStarted = ?");
            //stmt.setString(, table);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, fileName);
            stmt.setString(4, dateStarted);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchZcorpByID(String id) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM zcorp "
                    + "WHERE "
                    + "AND idJobs = ?");
            //stmt.setString(, table);
            stmt.setString(1, id);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchObjetByID(String id) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM objet "
                    + "WHERE "
                    + "AND idJobs = ?");
            stmt.setString(1, id);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchSolidscapeByID(String id) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM solidscape "
                    + "WHERE "
                    + "AND idJobs = ?");
            stmt.setString(1, id);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet searchPendingWithoutName(String table, String fileName, String dateStarted) {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT idJobs "
                    + "FROM " + table + " "
                    + "WHERE "
                    + "AND fileName = ? "
                    + "AND dateStarted = ?");
            //stmt.setString(, table);
            stmt.setString(3, fileName);
            stmt.setString(4, dateStarted);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void delete(String table, String id) throws SQLException {
        stmt = this.conn.prepareStatement(
                "DELETE "
                + "FROM " + table + " "
                + "WHERE idJobs = ?");
        // stmt.setString(1, table);
        stmt.setString(1, id);
        System.out.println(stmt);
        stmt.executeUpdate();
    }

    public void deleteByBuildName(String buildName, String table) throws SQLException {
        stmt = this.conn.prepareStatement(
                "DELETE "
                + "FROM " + table
                + " WHERE buildName = ?");
        stmt.setString(1, buildName);
        System.out.println(stmt);
        stmt.executeUpdate();
    }

    public void approve(String id) throws SQLException {
        stmt = this.conn.prepareStatement(
                "UPDATE pendingjobs "
                + "SET status = 'approved', "
                + "dateUpdated = now() "
                + "WHERE idJobs = ?");
        stmt.setString(1, id);
        System.out.println(stmt);
        stmt.executeUpdate();
    }

    public void updatePendingJobFLocation(String idJob, String fLocation) {
        try 
        {
            stmt = this.conn.prepareStatement
            (
                "UPDATE pendingjobs"
                + " SET filePath = '" + fLocation + "'"
                + " WHERE idJobs = '" + idJob + "'"
            );
            
            System.out.println(stmt);
            stmt.executeUpdate() ;    
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateToPrint(String idJob) throws SQLException {
        stmt = this.conn.prepareStatement(
                "UPDATE pendingjobs "
                + "SET status = 'to print' "
                + "WHERE idJobs = '?';");
        stmt.setString(1, idJob);
        System.out.println(stmt);
    }

    /* This querys for jobs that have been approved (status = approved). 
        With this query we can display the student's submissions that needs to
        be inserted into a build. The search is based on the name of the printer.
    */
    public ResultSet searchApprovedJobsNotPrinted(String printer) 
    {
        res = null;
        
        try 
        {
            stmt = this.conn.prepareStatement
            (
                    "SELECT fileName, dateStarted "
                    + "FROM pendingjobs "
                    + "WHERE "
                    + "printer = ? "
                    + "AND status = 'approved'"
            );
            
            stmt.setString(1, printer);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return res;
    }

    public void updatePendingJobVolume(String idJob, double volume) {
        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE pendingjobs "
                    + "SET volume = " + volume + " "
                    + "WHERE idJobs = '" + idJob + "'");
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException ex) 
		{
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePendingJobByCost(String idJob, double cost) {
        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE pendingjobs "
                    + "SET cost = " + cost + " "
                    + "WHERE idJobs = '" + idJob + "'");
            System.out.println(stmt);
            stmt.executeUpdate();

        } 
		catch (SQLException ex) 
		{
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* PLEASE NOT THIS WILL NOT RUN IN MYSQL WORK BENCH B/C OF SAFETY MODE! 
        Safe UPDATE mode only allows table modifications if the WHERE clause
        is a key column -Nick
    
        ---------------THIS IS NOT SAFE--------------
        SET buildName = ? WHERE filename = ?
    */
    public void updatePendingJobsBuildName(String build, String fileName) {
        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE pendingjobs "
                    + "SET buildName = ? WHERE filename = ?");
            stmt.setString(1, build);
            stmt.setString(2, fileName);
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertIntoPendingJobs(String printer, String firstName, String lastName, String Class, String section, String fileName, String filePath, String email) {
        try {
            stmt = this.conn.prepareStatement("INSERT INTO pendingjobs "
                    + "(idJobs, "
                    + "printer, "
                    + "firstName, "
                    + "lastName, "
                    + "course, "
                    + "section, "
                    + "fileName, "
                    + "filePath, "
                    + "status, "
                    + "email) "
                    + "VALUES"
					/*
					File name now includes timestamp, so it now can function as a Primary Key
					*/
                    + "('" 
					+ lastName + "_" + firstName + "_" + fileName + "', '"
                    + printer + "', '"
                    + firstName + "', '"
                    + lastName + "', '"
                    + Class + "', '"
                    + section + "', '"
                    + fileName.replace("'", "\'") + "', '"
                    + filePath.replace("'", "\'") + "', '"
                    + "pending', '"
                    + email + "')");

            stmt.executeUpdate();
            System.out.println(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertIntoSolidscape(String bn, int models, double resolution, String BuildTime, String comment, double cost) {
        String date = "(CURTIME())";
        try {
            stmt = this.conn.prepareStatement(
                    "INSERT "
                    + "INTO solidscape "
                    + "(idSolidscape, "
                    + "buildName, "
                    + "dateRun, "
                    + "noModels, "
                    + "resolution, "
                    + "runTime, "
                    + "comments, "
                    + "costOfBuild) "
                    + "VALUES"
                    + "(DATE_FORMAT(NOW(), '%Y-%m-%d_%H-%i-%s_" + bn + "'), '"
                    + "" + bn + "', " + date + ", '" + models + "', '" + resolution + "', '" + BuildTime + "', '" + comment + "', '" + cost + "')");
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertIntoZcorp(String bn, double mb, double yb, double mab, double cb, double ci, int models, String comment, double cost, String status) {
        String id = "DATE_FORMAT(NOW(), '%Y-%m-%d_%H-%i-%s_" + bn + "')";
        String date = "(CURTIME())";
        try {
            stmt = this.conn.prepareStatement(
                    "INSERT "
                    + "INTO zcorp "
                    + "(idZcorp, "
                    + "buildName, "
                    + "dateRun, "
                    + "monoBinder, "
                    + "yellowBinder, "
                    + "magentaBinder, "
                    + "cyanBinder, "
                    + "cubicInches, "
                    + "noModels, "
                    + "comments, "
                    + "costOfBuild, "
                    + "status) "
                    + "VALUES (" + id + ", '" + bn + "'," + date + ", '" + mb + "', '" + yb + "', '" + mab + "', '" + cb + "', '" + ci + "', '" + models + "', '" + comment + "','" + cost + "','" + status + "')");
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* WHY IS THERE A CONNECTIN CREATED IN HERE ????  - Nick Liccione*/
    public void insertIntoObjet(String bn, double bc, double sc, int models, String bm, double resolution, String comment, double cost) {
        String date = "(CURTIME())";
        try 
        {
            stmt = this.conn.prepareStatement
            (
                    "INSERT INTO objet "
                    + "(idObjet, "
                    + "buildName, "
                    + "dateRun, "
                    + "buildConsumed, "
                    + "supportConsumed, "
                    + "noModels, "
                    + "buildMaterials, "
                    + "resolution, "
                    + "comments, "
                    + "costOfBuild) "
                    + "VALUES(DATE_FORMAT(NOW(), '%Y-%m-%d_%H-%i-%s_" + bn + "'),"
                    + "'" + bn + "'," + date + ",'" + bc + "','" + sc + "','" + models 
                    + "','" + bm + "','" + resolution + "','" + comment + "','" + cost + "');"
            );
            
            if (stmt.executeUpdate() == 1)
                System.out.print("Successfully inserted value");
            
            System.out.println(stmt);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void insertIntoClasses(String course, String second) {
        try {
            stmt = this.conn.prepareStatement(
                    "INSERT INTO classes (className, classSection, current) VALUES('" + course + "','" + second + "', false)");
            System.out.println(stmt);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAllClassesInvisible() {
        try {
            stmt = this.conn.prepareStatement("UPDATE classes SET current = false");
            System.out.println(stmt);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCurrentClasses(String course, String section) {
        try {
            stmt = this.conn.prepareStatement(
                    "UPDATE classes "
                    + "SET current = true WHERE className = ? and classSection = ?");
            stmt.setString(1, course);
            stmt.setString(2, section);
            System.out.println(stmt);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SQLMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //completed
    public void insertIntoCompletedJobs(String id, String printer, String firstName, String lastName, String course, String section, String fileName, String filePath,
            String dateStarted, String status, String email, String comment, String buildName, Double volume, Double cost) {
        try {
            stmt = this.conn.prepareStatement("INSERT INTO completedJobs "
                    + "(idCompJobs, "
                    + "printer, "
                    + "firstName, "
                    + "lastName, "
                    + "course, "
                    + "section, "
                    + "fileName, "
                    + "filePath, "
                    + "dateStarted, "
                    + "dateCompleted, "
                    + "status, "
                    + "email, "
                    + "comment, "
                    + "buildName, "
                    + "volume, "
                    + "cost) "
                    + "VALUES"
                    + "('" + id + "', "
                    + "'" + printer + "', "
                    + "'" + firstName + "', '"
                    + "" + lastName + "', "
                    + "'" + course + "', "
                    + "'" + section + "', "
                    + "'" + fileName + "', "
                    + "'" + filePath + "', "
                    + "'" + dateStarted + "', "
                    + "(CURDATE()), "
                    + "'pending', "
                    + "'" + email + "', "
                    + "'" + comment + "', "
                    + "'" + buildName + "', "
                    + "'" + volume + "', "
                    + "'" + cost + "')");
            stmt.executeUpdate();
            System.out.println(stmt);
            System.out.println("Complete Record Entered!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getClasses() {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM classes "
                    + "WHERE "
                    + "current = false");
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet getCurrentClasses() {
        res = null;
        try {
            stmt = this.conn.prepareStatement(
                    "SELECT * "
                    + "FROM classes "
                    + "WHERE "
                    + "current = true");
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public ResultSet getAvailablePrinters() 
    {
        res = null;
        try {
            stmt = this.conn.prepareStatement
            (
                    "SELECT printer"
                    + " FROM printers"
            );
            System.out.println(stmt);
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
	
	public ResultSet getCurrentTime()
	{
        res = null;
        try 
		{
            stmt = this.conn.prepareStatement
            (
                    "SELECT (DATE_FORMAT(NOW(), '%Y-%m-%d_%H-%i-%s'))"
			);
            System.out.println(stmt);
            res = stmt.executeQuery();
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
        return res;
	}
    
}