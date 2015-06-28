package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.govst.advdb.finalproject.models.Account;
import edu.govst.advdb.finalproject.models.Customer;

public class AccountDAO implements basicCrud<Account, String> {

	private List<Account> accounts;

	private DbTypes dbType = DbTypes.ORACLE;

	private String username = "pots15";

	private String password = "pots15";

	private String driver = "oracle.jdbc.driver.OracleDriver";

	public AccountDAO() {
		//this.dbType = dbType;
		accounts = new ArrayList<Account>();
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	@Override
	public void createRecord(Account record) {
		final String COMMAND ="INSERT INTO Account (Customer_ID, Account_Type, Balance)" +
				"VALUES (?, ?, ?)";

		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement(COMMAND);
			stmt.setLong(1, record.getCustomerId());
			stmt.setString(2, record.getAccountType());
			stmt.setDouble(3, record.getCurrentBalance());

			stmt.execute();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = getConnection();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(Account_No) FROM Account");
			rs.next();

			record.setAccountNumber(rs.getInt(1));

			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		accounts.add(record);
	}

	@Override
	public boolean deleteRecord(String record) {

		final String COMMAND = "DELETE FROM Account WHERE Account_No = ?";
		boolean recordFound = false;

		for(int x = 0; x < accounts.size(); x++){
			if(Long.toString(accounts.get(x).getAccountNumber()).equals(record)) {
				accounts.remove(x);
				recordFound = true;
				break;
			}
		}

		if(recordFound) {
			try {
				Connection conn = getConnection();

				PreparedStatement stmt = conn.prepareStatement(COMMAND);

				stmt.setString(1, record);
				stmt.execute();
				stmt.close();
				conn.commit();
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbType.toString(), username, password);
			conn.setAutoCommit(true);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;

	}

	@Override
	public boolean updateRecord(Account record) {
		final String COMMAND ="	UPDATE Account " +
				"SET Customer_ID = ?, " +
				"Account_Type = ?, " +
				"Balance = ?" +
				"WHERE Account_No = ?";

		for(int x = 0; x < accounts.size(); x++) {
			if(accounts.get(x).getAccountNumber()==record.getAccountNumber()) {
				try {
					Connection conn = getConnection();

					PreparedStatement stmt = conn.prepareStatement(COMMAND);
					stmt.setLong(1, record.getCustomerId());
					stmt.setString(2, record.getAccountType());
					stmt.setDouble(3, record.getCurrentBalance());
					stmt.setDouble(4, record.getAccountNumber());

					stmt.execute();
					stmt.close();

					conn.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				int index = indexOfById(accounts, record);
				accounts.set(index, record);
			} 
		}
		return false;
	}
	
	static int indexOfById(List<Account> list, Account searchedObject) {
		  int x = 0;
		  for (Account a : list) {
		    if (a.getAccountNumber() == searchedObject.getAccountNumber()) return x;
		    x++;
		  }
		  return -1;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	@Override
	public Account getRecord(String record) {
		Account account = null;

		for(int x = 0; x < accounts.size(); x++) {
			if(Long.toString(accounts.get(x).getAccountNumber()).equals(record)) {
				account = accounts.get(x);
				break; 
			}
		}
		return account;
	}

	public Account getRecord(int record) {
		Account account = null;


		account = accounts.get(record);

		return account;

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
