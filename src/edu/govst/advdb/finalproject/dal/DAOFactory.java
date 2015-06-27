package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.govst.advdb.finalproject.models.Account;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;

public class DAOFactory extends AbstractConnectionFactory {

	private static DbTypes type;
	private static String username ="";
	private static String password ="";

	public DAOFactory() {
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
	public AccountDAO getAccountDAO() {
		AccountDAO accountDAO = new AccountDAO(type);

		final String GET_ACCOUNTS = "SELECT AccountNumber, " +
				"CustomerID, " +
				"MinimumBalance, " +
				"OverdraftCharge, " +
				"CurrentBalance FROM Checking";

		accountDAO.setPassword(password);
		accountDAO.setUsername(username);

		Connection conn = null;
		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(GET_ACCOUNTS);
			while(results.next())
			{
				Account account = new Account();
				account.setAccountNumber(results.getLong("Account_No"));
				account.setCheckingBalance(results.getDouble("Balance"));
				account.setCheckingBalance(results.getLong("Customer_ID"));
				account.setAccountType(results.getString("Account_Type"));

				accountDAO.addAccount(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountDAO;
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		TransactionDAO transactionDAO = new TransactionDAO(type);

		final String GET_TRANSACTIONS = "SELECT Transaction_No, " + 
				"Transaction_Name, " +
				"Account_No, " +
				"Withdraw, " +
				"Deposit FROM Transaction";
		transactionDAO.setPassword(password);
		transactionDAO.setUsername(username);

		Connection conn = null;

		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();

			ResultSet results = stmt.executeQuery(GET_TRANSACTIONS);
			while(results.next()) {
				Transaction transaction = new Transaction();
				transaction.setAccountNumber(results.getLong("Account_No"));
				transaction.setTransactionName(results.getString("Transaction_Name"));
				transaction.setTransactionNumber(results.getLong("Transaction_No"));

				if((int)results.getDouble("Withdraw") != 0) 
					transaction.setAmount(results.getDouble("Withdraw"));
				else
					transaction.setAmount(results.getDouble("Deposit"));

				transactionDAO.addTransaction(transaction);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactionDAO;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		// TODO Auto-generated method stub
		return null;
	}



}
