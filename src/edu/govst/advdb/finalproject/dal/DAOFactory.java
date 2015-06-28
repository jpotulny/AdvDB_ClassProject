package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import edu.govst.advdb.finalproject.models.Account;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;

public class DAOFactory extends AbstractConnectionFactory {

	private static DbTypes type;
	private static String username ="pots15";
	private static String password ="pots15";
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	
	public DAOFactory(String driver) {
		this.driver = driver;
		type = DbTypes.ORACLE;
	}

	public static Connection getConnection() throws SQLException {
		
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(type.toString(), username, password);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
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
		AccountDAO accountDAO = new AccountDAO();

		final String GET_ACCOUNTS = "SELECT Account_No, " +
				"Customer_ID, " +
				"Account_Type, " +
				"Balance FROM Account";

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
				account.setCustomer(results.getLong("Customer_ID"));
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
				"Deposit FROM Bank_Transaction";
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
		CustomerDAO customerDAO = new CustomerDAO();

		final String GET_CUSTOMERS = "SELECT Customer_ID, " + 
				"First_Name, " +
				"Last_Name, " +
				"SSN, " +
				"Street_Address, " +
				"City, " +
				"State, " +
				"Zip, " +
				"Primary_Phone, " +
				"Other_Phone FROM Customer";
		
		final String GET_SECURITY = "SELECT Customer_Id, PIN FROM Bank_Security";
	
		customerDAO.setPassword(password);
		customerDAO.setUsername(username);
		
		Connection conn = null;
		
		try {
			conn=getConnection();
			Statement customerStmt = conn.createStatement();
			Statement pinStmt = conn.createStatement();
			
			HashMap<Integer, String> pins = new HashMap<Integer, String>();
			
			ResultSet pinResults = pinStmt.executeQuery(GET_SECURITY);
			while(pinResults.next()){
				pins.put(Integer.valueOf(pinResults.getInt("customer_id")), pinResults.getString("pin"));
			}
			
			pinResults.close();
			pinStmt.close();
			
			ResultSet customerResults = customerStmt.executeQuery(GET_CUSTOMERS);
			while(customerResults.next()) {
				Customer customer = new Customer();
				
				customer.setCustomerId(customerResults.getInt("customer_Id"));
				customer.setFirstName(customerResults.getString("first_name"));
				customer.setLastName(customerResults.getString("last_name"));
				customer.setSsn(customerResults.getString("ssn"));
				customer.setAddress(customerResults.getString("street_address"));
				customer.setCity(customerResults.getString("city"));
				customer.setState(customerResults.getString("state"));
				customer.setZip(customerResults.getString("zip"));
				customer.setHomePhone(customerResults.getString("Primary_Phone"));
				customer.setCellPhone(customerResults.getString("Other_Phone"));
				customer.setPin(pins.get(Integer.valueOf(customer.getCustomerId())));
				
				customerDAO.addCustomer(customer);
			}
			customerResults.close();
			customerStmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerDAO;
	}
}
