package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.govst.advdb.finalproject.models.Checking;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Saving;
import edu.govst.advdb.finalproject.models.Transaction;

public class MicrosoftDAOFactory extends AbstractConnectionFactory {

	private static DbTypes type;
	private static String username ="";
	private static String password ="";

	public MicrosoftDAOFactory() {
		type = DbTypes.MSSQL;
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(type.toString());

		} catch (SQLException e) {
			try {
				conn = DriverManager.getConnection(type.toString(), username, password);
			} catch (SQLException f) {
				throw f;
			}
			e.printStackTrace();
		}

		return conn;
	}

	public static void setUsername(String user) {
		username=user;
	}

	public static void setPassword(String pass) {
		password=pass;
	}

	public static void setType(DbTypes t) {
		type = t;
	}

	@Override
	public CheckingDAO getCheckingAccountDAO() {
		CheckingDAO checkingDAO = new CheckingDAO(type);

		final String GET_CHECKING_ACCOUNTS = "SELECT AccountNumber, " +
				"CustomerID, " +
				"MinimumBalance, " +
				"OverdraftCharge, " +
				"CurrentBalance FROM Checking";

		Connection conn;
		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(GET_CHECKING_ACCOUNTS);
			while(results.next())
			{
				Checking checking = new Checking();
				checking.setMinimumBalance(results.getDouble("MinimumBalance"));
				checking.setAccountNumber(results.getLong("AccountNumber"));
				checking.setCheckingBalance(results.getDouble("CurrentBalance"));
				checking.setCheckingBalance(results.getLong("CustomerID"));
				checking.setOverdraftCharge(results.getDouble("OverdraftCharge"));

				checkingDAO.addExistingAccount(checking);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkingDAO;
	}

	@Override
	public SavingDAO getSavingAccountDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		// TODO Auto-generated method stub
		return null;
	}



}
