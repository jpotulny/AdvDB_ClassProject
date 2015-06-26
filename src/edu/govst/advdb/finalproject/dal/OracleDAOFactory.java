package edu.govst.advdb.finalproject.dal;

public class OracleDAOFactory extends AbstractConnectionFactory {

	private static final String DB_URL = DbTypes.ORACLE.toString();

	@Override
	public AccountDAO getAccountDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
