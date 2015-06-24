package edu.govst.advdb.finalproject.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	private long customerId;
	private String firstName;
	private String lastName;
	private String ssn;
	private String address;
	private String homePhone;
	private String cellPhone;
	private int pin;
	private List<BankAccount> accounts;
	
	public Customer() {
		accounts = new ArrayList<BankAccount>();
	}
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public List<BankAccount> getAccounts() {
		return accounts;
	}
}
