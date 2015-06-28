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

public class CustomerDAO implements basicCrud<Customer,Integer> {

	private List<Customer> customers;

	private DbTypes dbType;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String username;

	private String password;

	public CustomerDAO() {
		this.dbType = DbTypes.ORACLE;
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

	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public void createRecord(Customer record) {
		final String CUSTOMER_COMMAND ="INSERT INTO Customer (First_Name, Last_Name, ssn, Street_Address, City, State, Zip, Primary_Phone, Other_Phone)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		final String PIN_COMMAND ="INSERT INTO BANK_SECURITY (Customer_Id, Pin) VALUES (?,?)";
		final String GET_CUST_ID ="SELECT MAX(Customer_ID) FROM Customer"; //Not thread safe

		try {
			Connection conn = getConnection();

			PreparedStatement stmt = conn.prepareStatement(CUSTOMER_COMMAND);
			stmt.setString(1, record.getFirstName());
			stmt.setString(2, record.getLastName());
			stmt.setString(3, record.getSsn());
			stmt.setString(4, record.getAddress());
			stmt.setString(5, record.getCity());
			stmt.setString(6, record.getState());
			stmt.setString(7, record.getZip());
			stmt.setString(8, record.getHomePhone());
			stmt.setString(9, record.getOtherPhone());

			stmt.execute();
			stmt.close();

			int customer = -1;

			Statement custStmt = conn.createStatement();

			ResultSet rs = custStmt.executeQuery(GET_CUST_ID);
			rs.next();
			customer = rs.getInt(1);
			rs.close();
			custStmt.close();

			PreparedStatement pinStmt = conn.prepareStatement(PIN_COMMAND);
			pinStmt.setLong(1, customer);
			pinStmt.setString(2, record.getPin());

			pinStmt.execute();
			pinStmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		customers.add(record);
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbType.toString(),username,password);
			conn.setAutoCommit(true);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}


	@Override
	public boolean deleteRecord(Integer record) {
		final String COMMAND = "DELETE FROM Customer WHERE Customer_Id = ?";
		boolean recordFound = false;

		for(int x = 0; x < customers.size(); x++){
			if(Integer.valueOf(customers.get(x).getCustomerId()).equals(record)) {
				customers.remove(x);
				recordFound = true;
				break;
			}
		}

		Connection conn = null;
		
		if(recordFound) {
			try {
				conn = getConnection();

				PreparedStatement stmt = conn.prepareStatement("DELETE FROM ACCOUNT WHERE Customer_ID = ?");
				stmt.setInt(1,record.intValue());
				stmt.execute();
				
				PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM BANK_SECURITY WHERE Customer_Id = ?");
				stmt2.setInt(1, record.intValue());
				stmt2.execute();
				
				PreparedStatement stmt3 = conn.prepareStatement(COMMAND);
				stmt3.setInt(1, record);
				stmt3.execute();
				stmt3.close();


			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
				conn.rollback();
				conn.close();
				} catch(SQLException f) {
					f.printStackTrace();
				}
			}
			
			return true;
		}
		return false; 
	}

	@Override
	public boolean updateRecord(Customer record) {
		final String COMMAND ="	UPDATE Customer " +
				"SET Customer_ID = ?, " +
				"First_Name = ?, " +
				"Last_Name = ?, " +
				"ssn = ?, " +
				"street_address = ?, " +
				"city = ?, " +
				"state = ?, " +
				"zip = ?, " +
				"primary_phone = ?, " +
				"other_phone = ? " +
				"WHERE Customer_Id = ?";

		for(int x = 0; x < customers.size(); x++) {
			if(customers.get(x).getCustomerId() == record.getCustomerId()) {
				try {
					Connection conn = getConnection();

					PreparedStatement stmt = conn.prepareStatement(COMMAND);

					stmt.setInt(1, record.getCustomerId());
					stmt.setString(2, record.getFirstName());
					stmt.setString(3, record.getLastName());
					stmt.setString(4, record.getSsn());
					stmt.setString(5, record.getAddress());
					stmt.setString(6, record.getCity());
					stmt.setString(7, record.getState());
					stmt.setString(8, record.getZip());
					stmt.setString(9, record.getHomePhone());
					stmt.setString(10, record.getCellPhone());
					stmt.setInt(11, record.getCustomerId());

					stmt.execute();
					stmt.close();
					conn.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				int index = indexOfById(customers, record);
				customers.set(index, record);
			}}
		return false;
	}

	static int indexOfById(List<Customer> list, Customer searchedObject) {
		int x = 0;
		for (Customer c : list) {
			if (c.getCustomerId() == searchedObject.getCustomerId()) return x;
			x++;
		}
		return -1;
	}  

	@Override
	public Customer getRecord(Integer record) {
		Customer customer = null;

		for(int x = 0; x < customers.size(); x++) {
			if(customers.get(x).getCustomerId() == record.intValue()) {
				customer = customers.get(x);
				break; 
			}
		}
		return customer;
	}	
}
