package edu.govst.advdb.finalproject.dal;

import edu.govst.advdb.finalproject.models.Checking;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Saving;
import edu.govst.advdb.finalproject.models.Transaction;

public abstract class AbstractConnectionFactory {

	DbTypes database;

	public abstract CheckingDAO getCheckingAccountDAO();
	public abstract SavingDAO getSavingAccountDAO();
	public abstract TransactionDAO getTransactionDAO();
	public abstract CustomerDAO getCustomerDAO();

	public static AbstractConnectionFactory getAbstractConnectionFactory(
			DbTypes db) {

		switch (db) {
		case ORACLE: 
			return new OracleDAOFactory();
		case MSSQL:
			MicrosoftDAOFactory
			return new MicrosoftDAOFactory();      
		default: 
			throw new IllegalArgumentException();
		}
	}
}