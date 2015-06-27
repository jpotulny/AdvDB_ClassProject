package edu.govst.advdb.finalproject.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.govst.advdb.finalproject.models.Customer;

public class CustomerDAO implements basicCrud<Customer,String> {
	
	private List<Customer> customers;

	private DbTypes dbType;

	private String username;

	private String password;

	public CustomerDAO(DbTypes dbType) {
		this.dbType = dbType;
		this.customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void createRecord(Customer record) {
		final String CUSTOMER_COMMAND ="INSERT INTO Customer (First_Name, Last_Name, ssn, Street_Address, City, State, Zip, Primary_Phone, Other_Phone)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		final String PIN_COMMAND ="INSERT INTO Security (Customer_Id, Pin) VALUES (?,?)";
		customers.add(record);
		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement(CUSTOMER_COMMAND);
			stmt.setString(1, record.getFirstName());
			stmt.setString(2, record.getLastName());
			stmt.setString(3, record.getSsn());
			stmt.setString(4, record.getAddress());
			stmt.setString(5, record.getCity());
			stmt.setString(6, record.getState());
			stmt.setLong(7, record.getZip());
			stmt.setString(8, record.getHomePhone());
			stmt.setString(9, record.getOtherPhone());

			stmt.execute();
			stmt.close();
			
			Customer customer = getRecord(record.getFormattedName());
			
			PreparedStatement pinStmt = conn.prepareStatement(PIN_COMMAND);
			stmt.setLong(1, customer.getCustomerId());
			stmt.setInt(2, record.getPin());
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
				try {
					conn = DriverManager.getConnection(dbType.toString());
				} catch (SQLException e) {
					try {
						conn = DriverManager.getConnection(dbType.toString(),username,password);
					} catch (SQLException f) {
						f.printStackTrace();
						throw f;
					}
				}
		return conn;
	}

	
	@Override
	public boolean deleteRecord(String record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(Customer record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer getRecord(String record) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Customer getRecord(long record) {
		// TODO
		return null;
	}
}
