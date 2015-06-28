package edu.govst.advdb.finalproject.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	private int customerId;
	private String firstName;
	private String lastName;
	private String ssn;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String homePhone;
	private String otherPhone;
	private String pin;
	private List<Account> accounts;
	
	
	public String getFormattedName() {
		return lastName + ", " + firstName;
	}
	
	public Customer() {
		accounts = new ArrayList<Account>();
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
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
		return otherPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.otherPhone = cellPhone;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setCity(String city) {
		this.city  = city;
	}
	
	public String getCity() {
		// TODO Auto-generated method stub
		return this.city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
}
