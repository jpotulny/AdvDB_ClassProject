//Coded by Mike Schmidt, 2015

package edu.govst.advdb.finalproject.legacy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.govst.advdb.finalproject.legacy.RMIInterface;

public class RemoteImplementation extends UnicastRemoteObject implements
		RMIInterface {

	private static final long serialVersionUID = 1L;
	private Connection con;
	String host = "jdbc:sqlserver://localhost;database=Bank";
	String uName = "sa";
	String uPass = "pswd";
	int userID = -1;

	protected RemoteImplementation() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}

	public int checkLogin(String name, String pass) {

		Statement stmt = null;
		String query = "SELECT UserID FROM Accounts "
				+ "WHERE UserName = '" + name + "' AND Password = '" + pass +"'";
		
		try {
			con = DriverManager.getConnection(host, uName, uPass);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				return -1;
			} else
				while (rs.next()) {
					userID = rs.getInt("UserID");
					return userID;
				}

		} catch (SQLException e) {
			System.out.println(e);
			return -1;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return userID;
	}
	
	public float checkBalance(int choice) {
		float balance = 0;
		String account = "";
		if (choice == 1) {
			account = "Checking";
		} else if (choice == 2) {
			account = "Savings";
		}
		Statement stmt = null;
		String query = "SELECT " + account + "Balance " + "FROM accounts "
				+ "WHERE UserID = " + userID;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				return balance;
			} else
				while (rs.next()) {
					balance = rs.getFloat("CheckingBalance");
					return balance;
				}
		} catch (SQLException e) {
			System.out.println(e);
			return balance;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return balance;
	}
	
	public boolean depositFunds(float money) {
		float balance = money;
		int rows = 0;
		Statement stmt = null;
		String query = "SELECT CheckingBalance FROM Accounts " +
				       "WHERE UserID = " + userID;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				return false;
			} else
				while (rs.next()) {
					balance += rs.getFloat("CheckingBalance");
				}
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		query = "UPDATE Accounts SET CheckingBalance = " + balance +
				"WHERE UserID = " + userID;
		try {
			stmt = con.createStatement();
			rows = stmt.executeUpdate(query);
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean checkPassword(String pass) {
		String currentPass = "";
		Statement stmt = null;
		String query = "SELECT Password FROM Accounts "
				+ "WHERE UserID = " + userID;
		
		try {
			con = DriverManager.getConnection(host, uName, uPass);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				return false;
			} else
				while (rs.next()) {
					currentPass = rs.getString("Password");
				}

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (pass == currentPass)
			return true;
		return false;
	}

	public boolean updatePassword(String pass) {
		Statement stmt = null;
		int rows = 0;
		String query = "UPDATE Accounts SET Password = '" + pass +
				"' WHERE UserID = " + userID;
		try {
			stmt = con.createStatement();
			rows = stmt.executeUpdate(query);
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	

	

	

}
