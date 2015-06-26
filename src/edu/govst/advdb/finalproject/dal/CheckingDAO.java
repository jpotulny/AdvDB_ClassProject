package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.govst.advdb.finalproject.models.Checking;

public class CheckingDAO implements basicCrud<Checking, String> {

	private List<Checking> checkingAccounts;

	private DbTypes dbType;

	public CheckingDAO(DbTypes dbType) {
		this.dbType = dbType;
		checkingAccounts = new ArrayList<Checking>();
	}

	public void addExistingAccount(Checking checking) {
		checkingAccounts.add(checking);
	}

	@Override
	public void createRecord(Checking record) {
		final String COMMAND ="INSERT INTO Checking (CustomerID, MinimumBalance, OverdraftCharge, CurrentBalance)" +
				"VALUES (?, ?, ?, ?)";
		checkingAccounts.add(record);
		try {
			Connection conn = DriverManager.getConnection(dbType.toString());

			PreparedStatement stmt = conn.prepareStatement(COMMAND);
			stmt.setLong(0, record.getCustomer());
			stmt.setDouble(1, record.getMinimumBalance());
			stmt.setDouble(2, record.getOverdraftCharge());
			stmt.setDouble(3, record.getCurrentBalance());

			stmt.execute();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteRecord(String record) {

		final String COMMAND = "DELETE FROM CHECKING WHERE AccountNumber = ?";
		boolean recordFound = false;

		for(int x = 0; x < checkingAccounts.size(); x++){
			if(Long.toString(checkingAccounts.get(x).getAccountNumber()).equals(record)) {
				checkingAccounts.remove(x);
				recordFound = true;
				break;
			}
		}

		if(recordFound) {
			try {
				Connection conn = DriverManager.getConnection(dbType.toString());

				PreparedStatement stmt = conn.prepareStatement(COMMAND);

				stmt.setString(0, record);
				stmt.execute();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateRecord(Checking record) {
		final String COMMAND ="	UPDATE Checking " +
				"SET CustomerID = ?, " +
				"MinimumBalance = ?, " +
				"OverdraftCharge = ?, " +
				"CurrentBalance = ?" +
				"WHERE AccountNumber = ?";

		if(checkingAccounts.contains(record)) {
			try {
				Connection conn = DriverManager.getConnection(dbType.toString());

				PreparedStatement stmt = conn.prepareStatement(COMMAND);
				stmt.setLong(0, record.getCustomer());
				stmt.setDouble(1, record.getMinimumBalance());
				stmt.setDouble(2, record.getOverdraftCharge());
				stmt.setDouble(3, record.getCurrentBalance());
				stmt.setLong(4, record.getAccountNumber());

				stmt.execute();
				stmt.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int index = checkingAccounts.indexOf(record);
			checkingAccounts.set(index, record);
		}
		return false;
	}
	@Override
	public Checking getRecord(String record) {
		// TODO Auto-generated method stub
		return null;
	}
}
