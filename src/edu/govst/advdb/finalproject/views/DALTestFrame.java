package edu.govst.advdb.finalproject.views;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.govst.advdb.finalproject.dal.AbstractConnectionFactory;
import edu.govst.advdb.finalproject.dal.AccountDAO;
import edu.govst.advdb.finalproject.dal.CustomerDAO;
import edu.govst.advdb.finalproject.dal.DAOFactory;
import edu.govst.advdb.finalproject.dal.DbTypes;
import edu.govst.advdb.finalproject.dal.TransactionDAO;
import edu.govst.advdb.finalproject.models.Account;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;



public class DALTestFrame {




	public static void main(String[] args)
	{
		String separator1 = "--------------------------------------------------";
		String separator2 = "==================================================";
		DAOFactory dao = (DAOFactory)AbstractConnectionFactory.getAbstractConnectionFactory(DbTypes.ORACLE);

		AccountDAO accounts = dao.getAccountDAO();
		CustomerDAO customers = dao.getCustomerDAO();
		TransactionDAO transactions = dao.getTransactionDAO();


		ArrayList<Customer> custTest = new ArrayList<Customer>();

		int[] custSelections = new int[10];
		int[] tranSelections = new int[10];
		int[] acctSelections = new int[10];

		custSelections[0] = 1;
		custSelections[1] = 128;
		custSelections[2] = 215;
		custSelections[3] = 13;
		custSelections[4] = 500;

		acctSelections[0] = 1;
		acctSelections[1] = 128;
		acctSelections[2] = 215;
		acctSelections[3] = 13;
		acctSelections[4] = 500;
		
		tranSelections[0] = 1;
		tranSelections[1] = 645;
		tranSelections[2] = 1234;
		tranSelections[3] = 1822;
		tranSelections[4] = 2000;

		Random rand = new Random();
		
		for(int x = 5; x < 10; x++) {
			custSelections[x] = rand.nextInt(500) +1;
			acctSelections[x] = rand.nextInt(500) +1;
			tranSelections[x] = rand.nextInt(2000) +1;
		}		
		
//		System.out.println("5 predetermined customers, 5 random customers");
//		System.out.println(separator2);
//		System.out.println("Index\tName\t\tSSN\tPIN\tPhone");
//		System.out.println(separator1);
//		for(int x = 0; x < 10; x++) {
//			Customer cTemp = customers.getRecord(custSelections[x]);
//			
//			System.out.println((x+1) + ".\t" + cTemp.getFirstName() + " " + cTemp.getLastName() + "\t" +
//			cTemp.getSsn() + "\t" + cTemp.getHomePhone() + "\t" + cTemp.getPin());
//		}
//		
//		System.out.println(separator1 + "\n\n\n");
//		System.out.println("5 predetermined accounts, 5 random accounts");
//		System.out.println(separator2);
//		System.out.println("Index\tAccount No\tBalance\tCustomerID\tAccountType");
//		System.out.println(separator1);
//		for(int x = 0; x < 10; x++) {
//			Account aTemp = accounts.getRecord(x);
//
//			System.out.println((x+1) + ".\t" + aTemp.getAccountNumber() + "\t" + aTemp.getCurrentBalance() + "\t" +
//			aTemp.getCustomerId() + "\t" + aTemp.getAccountType());
//		}
//		
//		System.out.println(separator1 + "\n\n\n");
//		System.out.println("5 predetermined transactions, 5 random transactions");
//		System.out.println(separator2);
//		System.out.println("Index\tTransactionNumber\tAmount\tAccount No\tTransaction Name");
//		System.out.println(separator1);
//		for(int x = 0; x < 10; x++) {
//			Transaction tTemp = transactions.getRecord(String.valueOf(tranSelections[x]));
//			System.out.println((x+1) + ".\t" + tTemp.getTransactionNumber() + "\t" + tTemp.getAmount() + "\t" +
//			tTemp.getAccountNumber() + "\t" + tTemp.getTransactionName());
//		}
//		System.out.println(separator1 + "\n\n\n");
//		
		System.out.println("Exercising Record UPDATE:");
		
		Account acct = new Account();
		Transaction trans = new Transaction();
		Customer cust = new Customer();
		
		cust.setAddress("123 NOT Fake Street");
		cust.setCellPhone("1234");
		cust.setCity("CHICAGO");
		cust.setFirstName("Michael");
		cust.setLastName("Jordan");
		cust.setHomePhone("1234");
		cust.setPin("1234");
		cust.setSsn("1234");
		cust.setState("IL");
		cust.setZip("1234");
		cust.setCustomerId(510);
		
		acct.setAccountNumber(511);
		acct.setAccountType("Checking");
		acct.setCheckingBalance(999999.99);
		acct.setCustomer(510);
		
		acct.withdraw(5000);

		customers.deleteRecord(510);
		accounts.deleteRecord(Long.toString(acct.getAccountNumber()));
	
		
		
		
	}	
}
