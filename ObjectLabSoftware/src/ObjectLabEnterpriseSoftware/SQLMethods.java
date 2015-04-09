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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
public class SQLMethods {
	/* Same url and connection to the DB until it is closed */
	private final String url;
	private Connection conn;
	private ResultSet res;
	private PreparedStatement stmt;

	public SQLMethods() {
		/* To resolve hostname to an IP adr */
		NsLookup look = new NsLookup();
		File f = new File("C:\\Sync\\computername.txt");
		String line, ip = "";

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(f.getAbsolutePath());
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
				ip = line;

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file!");
			String temp = JOptionPane.showInputDialog("Please enter the exact name of the computer hosting the database server: ");
			ip = look.resolve(temp);
			// create and write to file

			try {
				PrintWriter writer = new PrintWriter("C:\\Sync\\computername.txt", "UTF-8");
				writer.println(temp);
				writer.close();
			} catch (FileNotFoundException ex1) {
				Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,null, ex1);
			} catch (UnsupportedEncodingException ex1) {
				Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,null, ex1);
			}
		} catch (IOException ex) {
			System.out.println("Couldn't read file! IOException!");
		}

		url = "jdbc:mysql://" + ip + ":3306/";
		connectToDatabase("com.mysql.jdbc.Driver", url + "jobsdb", "admin","password");
	}

	private void connectToDatabase(String driver, String urlDatabaseName,
			String userName, String pw) {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(urlDatabaseName, userName, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found / created. Exception!\n"+ e);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			System.out.println(e);
		}
	}

	public void closeDBConnection() {
		try {
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(SQLMethods.class.getName()).log(Level.SEVERE,null, ex);
		}
	}
	// Begining of Select Methods
	// _______________________________________________________________________________________________________________________
	public ResultSet selectAllFromTable(String table) {// select entire table
														// (probably not useful)
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

	public ResultSet searchTwo(String table, String column1, String column2,String key1) {
		res = null;
		String query = "SELECT * FROM " + table + " WHERE concat(" + column1
				+ ", ' ', " + column2 + ")" + "LIKE ?";
		try {
			stmt = this.conn.prepareStatement(query);
			stmt.setString(1, key1);
			res = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet searchOne(String table, String column1, String key) {
		res = null;
		String query = "SELECT * FROM " + table + " WHERE " + column1 + " = ?";
		try {
			stmt = this.conn.prepareStatement(query);
			stmt.setString(1, key);
			res = stmt.executeQuery();			
		} catch (Exception e) {
			e.printStackTrace();
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
	//____________________________________________________________________________________
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

}