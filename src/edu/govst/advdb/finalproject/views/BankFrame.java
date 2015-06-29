//View class and all UX by Mike Schmidt 2015. 

package edu.govst.advdb.finalproject.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.UIManager;

import java.awt.SystemColor;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import edu.govst.advdb.finalproject.dal.AbstractConnectionFactory;
import edu.govst.advdb.finalproject.dal.AccountDAO;
import edu.govst.advdb.finalproject.dal.CustomerDAO;
import edu.govst.advdb.finalproject.dal.DbTypes;
import edu.govst.advdb.finalproject.dal.TransactionDAO;
import edu.govst.advdb.finalproject.models.Customer;
import edu.govst.advdb.finalproject.models.Transaction;


public class BankFrame {

	private JFrame frmBankStart;
	private JPanel loginPanel, menuPanel, choicePanel, changePINPanel;
	private JTextField txtCurrentBalance, txtAmount, txtCustomerID;
	private JPasswordField pswdPIN;
	private JLabel lblInvalid, lblSelectAction;
	private JTextField txtOldPin, txtNewPin;
	private DbTypes dbtype;
	private String customerID;
	private boolean fChecking = false, fSavings = false, fWithdraw = false, fDeposit = false;
	
	private CustomerDAO customers;
	private TransactionDAO transactions;
	private AccountDAO accounts;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankFrame window = new BankFrame();
					window.frmBankStart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BankFrame() {
		initialize();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				customers = AbstractConnectionFactory.getAbstractConnectionFactory(DbTypes.ORACLE).getCustomerDAO();
				transactions = AbstractConnectionFactory.getAbstractConnectionFactory(DbTypes.ORACLE).getTransactionDAO();
				accounts = AbstractConnectionFactory.getAbstractConnectionFactory(DbTypes.ORACLE).getAccountDAO();
				customers.setCustomerAccounts();
			}
		});
		t.run();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dbtype = DbTypes.ORACLE;
		
		frmBankStart = new JFrame();
		frmBankStart.setTitle("Banking Application");
		frmBankStart.getContentPane().setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().setLayout(new CardLayout(0, 0));
		
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().add(loginPanel, "name_513654269379283");
		loginPanel.setLayout(null);
		
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().add(menuPanel, "name_513659053279224");
		menuPanel.setLayout(null);
				
		choicePanel = new JPanel();
		choicePanel.setBorder(null);
		choicePanel.setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().add(choicePanel, "name_513661453458437");
		choicePanel.setLayout(null);
		
		frmBankStart.setBounds(100, 100, 800, 800);
		frmBankStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (checkLogin(txtCustomerID.getText(), pswdPIN.getPassword())) {
						loginPanel.setVisible(false);
						menuPanel.setVisible(true);
					}else {
						lblInvalid.setVisible(true);
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnLogin.setForeground(SystemColor.windowText);
		btnLogin.setBackground(UIManager.getColor("Button.light"));
		btnLogin.setBounds(350, 350, 100, 50);
		loginPanel.add(btnLogin);
		
		JLabel lblBanner = new JLabel("<html><center>Welcome to the banking application.</center><center> Please login to proceed.</center>");
		lblBanner.setForeground(Color.WHITE);
		lblBanner.setBackground(Color.DARK_GRAY);
		lblBanner.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
		lblBanner.setBounds(225, 100, 350, 85);
		loginPanel.add(lblBanner);
		
		txtCustomerID = new JTextField();
		txtCustomerID.setToolTipText("CustomerID");
		txtCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtCustomerID.setBackground(UIManager.getColor("TextField.light"));
		txtCustomerID.setBounds(325, 225, 150, 25);
		txtCustomerID.setColumns(10);
		loginPanel.add(txtCustomerID);
		
		JLabel lblCustomerID = new JLabel("CustomerID");
		lblCustomerID.setForeground(new Color(255, 255, 255));
		lblCustomerID.setLabelFor(txtCustomerID);
		lblCustomerID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustomerID.setBounds(215, 225, 100, 25);
		loginPanel.add(lblCustomerID);
		
		pswdPIN = new JPasswordField();
		pswdPIN.setToolTipText("PIN");
		pswdPIN.setBackground(UIManager.getColor("TextField.light"));
		pswdPIN.setBounds(325, 275, 150, 25);
		loginPanel.add(pswdPIN);
						
		JLabel lblPIN = new JLabel("PIN");
		lblPIN.setForeground(new Color(255, 255, 255));
		lblPIN.setLabelFor(pswdPIN);
		lblPIN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPIN.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPIN.setBounds(215, 275, 100, 25);
		loginPanel.add(lblPIN);
		
		lblInvalid = new JLabel("Invalid CustomerID or PIN.  Please try again.");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setForeground(Color.RED);
		lblInvalid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInvalid.setBounds(190, 425, 420, 62);
		lblInvalid.setVisible(false);
		loginPanel.add(lblInvalid);
		
		JButton btnChecking = new JButton("Checking");
		btnChecking.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnChecking.setBounds(325, 225, 150, 50);
		btnChecking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fChecking = true;
				fSavings = false;
				menuPanel.setVisible(false);
				choicePanel.setVisible(true);
			}
		});
		menuPanel.add(btnChecking);
		
		JButton btnSavings = new JButton("Savings");
		btnSavings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSavings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fChecking = false;
				fSavings = true;
				menuPanel.setVisible(false);
				choicePanel.setVisible(true);
			}
		});
		btnSavings.setBounds(325, 300, 150, 50);
		menuPanel.add(btnSavings);
		
		JButton btnChangePin = new JButton("Change PIN");
		btnChangePin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnChangePin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(false);
				changePINPanel.setVisible(true);
			}
		});
		btnChangePin.setBounds(325, 375, 150, 50);
		menuPanel.add(btnChangePin);
		
		JLabel lblMenu = new JLabel("Main Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setForeground(Color.WHITE);
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblMenu.setBackground(Color.DARK_GRAY);
		lblMenu.setBounds(225, 134, 350, 50);
		menuPanel.add(lblMenu);
			
		JTextField lblCurrentBalance = new JTextField();
		lblCurrentBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentBalance.setEditable(false);
		lblCurrentBalance.setForeground(Color.WHITE);
		lblCurrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCurrentBalance.setBackground(Color.DARK_GRAY);
		lblCurrentBalance.setText("Current Balance: ");
		lblCurrentBalance.setBounds(215, 225, 160, 25);
		lblCurrentBalance.setBorder(new EmptyBorder(0, 0, 0, 0));
		choicePanel.add(lblCurrentBalance);
		lblCurrentBalance.setColumns(10);
		
		txtCurrentBalance = new JTextField();
		txtCurrentBalance.setHorizontalAlignment(SwingConstants.LEFT);
		txtCurrentBalance.setForeground(Color.WHITE);
		txtCurrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCurrentBalance.setEditable(false);
		txtCurrentBalance.setColumns(10);
		txtCurrentBalance.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtCurrentBalance.setBackground(Color.DARK_GRAY);
		txtCurrentBalance.setBounds(425, 225, 160, 25);
		choicePanel.add(txtCurrentBalance);
		
		String labels[] = {"", "Withdraw", "Deposit"};
		JComboBox cmboChoice = new JComboBox(labels);
		cmboChoice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmboChoice.setBounds(215, 300, 160, 25);
		choicePanel.add(cmboChoice);
		
		txtAmount = new JTextField();
		txtAmount.setToolTipText("Enter Amount");
		txtAmount.setBounds(425, 300, 160, 25);
		choicePanel.add(txtAmount);
		txtAmount.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmboChoice.getSelectedIndex() == 1) {
					fWithdraw = true;
					fDeposit = false;
					makeTransaction();
				}else if(cmboChoice.getSelectedIndex() == 2) {
					fDeposit = true;
					fWithdraw = false;
					makeTransaction();
					
				} else {
					lblSelectAction.setVisible(true);
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSubmit.setBounds(350, 375, 100, 50);
		choicePanel.add(btnSubmit);
		
		lblSelectAction = new JLabel("Please make a selection.");
		lblSelectAction.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAction.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectAction.setForeground(Color.RED);
		lblSelectAction.setBounds(275, 470, 250, 25);
		lblSelectAction.setVisible(false);
		choicePanel.add(lblSelectAction);
		
		JButton btnBackToMain = new JButton("Main Menu");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(true);
				choicePanel.setVisible(false);
			}
		});
		btnBackToMain.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBackToMain.setBounds(340, 600, 120, 30);
		choicePanel.add(btnBackToMain);
		
		changePINPanel = new JPanel();
		changePINPanel.setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().add(changePINPanel, "name_680254008249323");
		changePINPanel.setLayout(null);
		
		JLabel lblCurrentPin = new JLabel("Current PIN");
		lblCurrentPin.setForeground(Color.WHITE);
		lblCurrentPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentPin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCurrentPin.setBounds(215, 225, 100, 25);
		changePINPanel.add(lblCurrentPin);
		
		txtOldPin = new JTextField();
		txtOldPin.setBounds(325, 225, 150, 25);
		changePINPanel.add(txtOldPin);
		txtOldPin.setColumns(10);
		
		txtNewPin = new JTextField();
		txtNewPin.setColumns(10);
		txtNewPin.setBounds(325, 275, 150, 25);
		changePINPanel.add(txtNewPin);
		
		JLabel lblNewPin = new JLabel("New PIN");
		lblNewPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPin.setForeground(Color.WHITE);
		lblNewPin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewPin.setBounds(215, 275, 100, 25);
		changePINPanel.add(lblNewPin);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePIN();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button.setBounds(350, 350, 100, 50);
		changePINPanel.add(button);
		
		JLabel lblInvalidPIN = new JLabel("Current PIN is invalid");
		lblInvalidPIN.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalidPIN.setForeground(Color.RED);
		lblInvalidPIN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInvalidPIN.setBounds(270, 450, 260, 33);
		lblInvalidPIN.setVisible(false);
		changePINPanel.add(lblInvalidPIN);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePINPanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		btnMainMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMainMenu.setBounds(340, 600, 120, 30);
		changePINPanel.add(btnMainMenu);
	}
	
	private boolean checkLogin(String cusID, char[] pin) throws ClassNotFoundException {
		//check login name and PIN with database and if a match then return true, else return false
		final String CHECK_LOGIN = "SELECT COUNT(*) FROM BANK_Security WHERE Customer_ID = " + cusID + " AND PIN = " + String.valueOf(pin);
		ResultSet rs;
		Statement stmt;
		Connection conn;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(CHECK_LOGIN);
			rs.next();
			if (rs.getInt(1) == 1) {
				customerID = cusID;
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private void updatePIN(){
		//TODO Not implemented, requires a PIN to come from somewhere
		String dummyVar = "";
		for(Customer c : customers.getCustomers()) {
			if(c.getPin() == dummyVar) {
				c.setPin(dummyVar);
				customers.updateRecord(c);
				break;
			}
		}
	}
	
	private void makeTransaction(){
		float amount = 0;
		amount += Float.parseFloat(txtAmount.getText());
		if (fDeposit) {
			if(fChecking) {
				depositChecking(amount);
			} else {
				depositSavings(amount);
			}
		} else {
			if(fChecking) {
				withdrawChecking(amount);
			} else {
				withdrawSavings(amount);
			}
		}
	}
	
	private void depositChecking(float amount) {
		//add amount to the database here
		Transaction transaction = new Transaction();
		Customer customer = new Customer();
		customers.getRecord(Integer.parseInt(customerID));
		
		transaction.setAccountNumber(customer.getAccounts().get(0).getAccountNumber());	//TODO - fix terribad algorithm behind this.
		transaction.setAmount(-1 * amount);
		transaction.setTransactionName("Checking");
		transactions.createRecord(transaction);
	}
	
	private void depositSavings(float amount) {
		//add amount to the database here
		Transaction transaction = new Transaction();
		Customer customer = new Customer();
		customers.getRecord(Integer.parseInt(customerID));
		
		transaction.setAccountNumber(customer.getAccounts().get(0).getAccountNumber());  //TODO - fix terribad algorithm behind this.
		transaction.setAmount(amount);
		transaction.setTransactionName("Saving");
		transactions.createRecord(transaction);
	}
	
	private void withdrawChecking(float amount) {
		Transaction transaction = new Transaction();
		Customer customer = new Customer();
		customers.getRecord(Integer.parseInt(customerID));
		
		transaction.setAccountNumber(customer.getAccounts().get(0).getAccountNumber());  //TODO - fix terribad algorithm behind this.
		transaction.setAmount(-1 * amount);
		transaction.setTransactionName("Savings");
		transactions.createRecord(transaction);
	}
	
	private void withdrawSavings(float amount) {
		//add amount to the database here
		Transaction transaction = new Transaction();
		Customer customer = new Customer();
		customers.getRecord(Integer.parseInt(customerID));
		
		transaction.setAccountNumber(customer.getAccounts().get(0).getAccountNumber());  //TODO - fix terribad algorithm behind this.
		transaction.setAmount(amount);
		transaction.setTransactionName("Savings");
		transactions.createRecord(transaction);
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		try {
		conn = DriverManager.getConnection(dbtype.toString(), "pots15", "pots15");
		conn.setAutoCommit(true); 			//12c doesn't respect this default setting so we put it in explicitly.
		} catch (SQLException e) {
			e.printStackTrace();
			//throw e;	
		}
		return conn;
	}
}
