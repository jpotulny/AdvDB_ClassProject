package edu.govst.advdb.finalproject.dal;

import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;

public abstract class AbstractConnectionFactory {

	DbTypes database;

	public abstract AccountDAO getAccountDAO();
	public abstract TransactionDAO getTransactionDAO();
	public abstract CustomerDAO getCustomerDAO();

	public static AbstractConnectionFactory getAbstractConnectionFactory(
			DbTypes db) {

		switch (db) {
		case ORACLE: 
			return new DAOFactory("oracle.jdbc.driver.OracleDriver");
		case MSSQL:
			return new DAOFactory("oracle.jdbc.driver.OracleDriver");      
		default: 
			throw new IllegalArgumentException();
		}
	}
}