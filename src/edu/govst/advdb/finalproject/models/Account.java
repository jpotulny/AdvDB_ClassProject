package edu.govst.advdb.finalproject.models;

public class Account {

	private long customer; //Customer ID
	private long accountNumber;
	private double checkingBalance;
	private String accountType;
	
	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}
	
	public void setCheckingBalance(double checkingBalance) {
		this.checkingBalance = checkingBalance;
	}
	public double getCurrentBalance() {
		return checkingBalance;
	}
	public void deposit(double amount) {
		checkingBalance += amount;
	}
	public void withdraw(double amount) {
		checkingBalance -= amount;
		
	}

	public long getCustomerId() {
		return this.customer;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
