package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.govst.advdb.finalproject.models.Transaction;

public class TransactionDAO implements basicCrud<Transaction,String> {

	private DbTypes dbtype;
	private String username;
	private String password;
	private List<Transaction> transactions;
	
	public TransactionDAO(DbTypes type) {
		this.dbtype = type;
		transactions = new ArrayList<Transaction>();
	}
	
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
		conn = DriverManager.getConnection(dbtype.toString());
		} catch (SQLException e) {
			try {
				conn = DriverManager.getConnection(dbtype.toString(), username, password);
			} catch(SQLException f) {
				f.printStackTrace();
				throw f;
			}
		}
		return conn;
	}
	
	@Override
	public void createRecord(Transaction record) {
		final String DEPOSIT_COMMAND = "INSERT INTO Transaction (Transaction_Name, Account_No, Withdraw, Deposit) VALUES (?, ?, 0, ?)";
		final String WITHDRAW_COMMAND = "INSERT INTO Transaction (Transaction_Name, Account_No, Withdraw, Deposit) VALUES (?, ?, ?, 0)";
		
		try {
			Connection conn = getConnection();
			PreparedStatement stmt = null;
			if(record.getAmount() <= 0) {
				stmt = conn.prepareStatement(WITHDRAW_COMMAND);
				stmt.setString(1, "Withdraw");
			} else {
				stmt = conn.prepareStatement(DEPOSIT_COMMAND);
				stmt.setString(1, "Deposit");
			}
			
			stmt.setLong(2, record.getAccountNumber());
			stmt.setDouble(3, record.getAmount());
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteRecord(String record) {
		final String COMMAND = "DELETE FROM Transaction WHERE Transaction_No = ?";
		boolean recordFound = false;

		for(int x = 0; x < transactions.size(); x++){
			if(Long.toString(transactions.get(x).getTransactionNumber()).equals(record)) {
				transactions.remove(x);
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
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateRecord(Transaction record) {
		final String COMMAND ="	UPDATE Transaction " +
				"SET Transaction_Name = ?, " +
				"Account_No = ?, " +
				"Withdraw = ?" +
				"Deposit = ?" + 
				"WHERE Transaction_No = ?";

		if(transactions.contains(record)) {
			try {
				Connection conn = getConnection();

				PreparedStatement stmt = conn.prepareStatement(COMMAND);
				stmt.setString(1, record.getTransactionName());
				stmt.setLong(2, record.getAccountNumber());
				stmt.setLong(5, record.getTransactionNumber());
				if(record.getAmount() < 0)
				{
					stmt.setDouble(3, record.getAmount());
					stmt.setDouble(4, 0);
				} else {
					stmt.setDouble(3, 0);
					stmt.setDouble(4, record.getAmount());
				}
				

				stmt.execute();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			int index = transactions.indexOf(record);
			transactions.set(index, record);
		}
		return false;
	}

	@Override
	public Transaction getRecord(String record) {
		Transaction transaction = null;
		
		for(int x = 0; x < transactions.size(); x++) {
			if(Long.toString(transactions.get(x).getTransactionNumber()).equals(record)) {
				transaction = transactions.get(x);
				break; 
			}
		}
		return transaction;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

}
