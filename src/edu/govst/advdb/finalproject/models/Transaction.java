package edu.govst.advdb.finalproject.models;

public class Transaction {
	
	private long transactionNumber;
	private String transactionName;
	private long accountNumber;
	private double amount;
	
	public long getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
