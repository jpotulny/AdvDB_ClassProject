package edu.govst.advdb.finalproject.models;

public class Checking implements BankAccount {

	private long customer; //Customer ID
	private long accountNumber;
	private double checkingBalance = 0;
	private double minimumBalance;
	private double overdraftCharge;
	
	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}
	
	public long getCustomer() {
		return customer;
	}
	public void setCheckingBalance(double checkingBalance) {
		this.checkingBalance = checkingBalance;
	}
	public double getMinimumBalance() {
		return minimumBalance;
	}
	public void setMinimumBalance(double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	public double getOverdraftCharge() {
		return overdraftCharge;
	}
	public void setOverdraftCharge(double overdraftCharge) {
		this.overdraftCharge = overdraftCharge;
	}
	@Override
	public double getCurrentBalance() {
		return checkingBalance;
	}
	@Override
	public void deposit(double amount) {
		checkingBalance += amount;
	}
	@Override
	public void withdraw(double amount) {
		checkingBalance -= amount;
		
	}
	
}
