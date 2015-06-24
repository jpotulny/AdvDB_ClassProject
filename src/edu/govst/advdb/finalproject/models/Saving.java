package edu.govst.advdb.finalproject.models;

public class Saving implements BankAccount {

	private double savingsBalance;
	private double minimumBalance;
	private double interestRate;
	private CompoundingType compoundingType = CompoundingType.ANNUALLY;
	
	
	public void setSavingsBalance(double savingsBalance) {
		this.savingsBalance = savingsBalance;
	}

	@Override
	public double getCurrentBalance() {
		return savingsBalance;
	}
	@Override
	public void deposit(double amount) {
		savingsBalance += amount;
	}
	@Override
	public void withdraw(double amount) {
		savingsBalance -= amount;
	}

	public double getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public CompoundingType getCompoundingType() {
		return compoundingType;
	}

	public void setCompoundingType(CompoundingType compoundingType) {
		this.compoundingType = compoundingType;
	}

	protected enum CompoundingType {
		ANNUALLY,
		BIANNUALLY,
		QUARTERLY,
		MONTHLY,
		WEEKLY,
		DAILY,
		CONTINUOSLY
	}
}
