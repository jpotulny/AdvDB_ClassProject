package edu.govst.advdb.finalproject.models;

public interface BankAccount {

	public double getCurrentBalance();
	public void deposit(double amount);
	public void withdraw(double amount);
}
