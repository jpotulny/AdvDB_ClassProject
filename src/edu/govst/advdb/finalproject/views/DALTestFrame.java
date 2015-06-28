package edu.govst.advdb.finalproject.views;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.govst.advdb.finalproject.dal.AbstractConnectionFactory;
import edu.govst.advdb.finalproject.dal.AccountDAO;
import edu.govst.advdb.finalproject.dal.DAOFactory;
import edu.govst.advdb.finalproject.dal.DbTypes;
import edu.govst.advdb.finalproject.models.Account;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;



public class DALTestFrame {

	
	
	
	public static void main(String[] args)
	{

		JPanel jpanel = new JPanel();
		
		DAOFactory dao = (DAOFactory)AbstractConnectionFactory.getAbstractConnectionFactory(DbTypes.ORACLE);

		List<Account> accounts = dao.getAccountDAO().getAccounts();
		List<Transaction> transactions = dao.getTransactionDAO().getTransactions();
		List<Customer> customers = dao.getCustomerDAO().getCustomers();
		
		int pincount = 0;
		
		for(Customer c : customers) {
			if(c.getPin() != null)
				pincount++;
		}
		
				
//		manager.addLayoutComponent("A", lblCountOfCustomers);
//		manager.addLayoutComponent("B", lblCountOfTransactions);
//		manager.addLayoutComponent("C", lblCountOfAccounts);
//		manager.addLayoutComponent("D", lblDidPinsWork);
		
		jpanel.setSize(new Dimension(480, 640));
		jpanel.setLayout( new BoxLayout(jpanel, BoxLayout.Y_AXIS));
		
		JLabel lblCountOfCustomers = new JLabel("Customer Count:\t" + customers.size());
		JLabel lblCountOfTransactions = new JLabel("Transaction Count:\t" + transactions.size());
		JLabel lblCountOfAccounts = new JLabel("Account Count:\t" + accounts.size());
		JLabel lblDidPinsWork = new JLabel("Pins Retrieved:\t" + pincount);
		
		jpanel.add(lblCountOfCustomers);
		jpanel.add(lblCountOfTransactions);
		jpanel.add(lblCountOfAccounts);
		jpanel.add(lblDidPinsWork);
		
		jpanel.setVisible(true);
		System.out.println("Customer Count:\t" + customers.size());
		System.out.println("Transaction Count:\t" + transactions.size());
		System.out.println("Account Count:\t" + accounts.size());
		System.out.println("Pins Retrieved:\t" + pincount);
	}


	
}
