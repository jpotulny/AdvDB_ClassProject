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

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import edu.govst.advdb.finalproject.dal.AbstractConnectionFactory;
import edu.govst.advdb.finalproject.dal.DbTypes;


public class BankFrame {

	private JFrame frmBankStart;
	private JPanel loginPanel;
	private JPanel menuPanel;
	private JPanel choicePanel;
	private JPanel changePINPanel;
	private boolean fChecking = false, fSavings = false, fWithdraw = false, fDeposit = false;
	private JTextField txtCurrentBalance;
	private JTextField txtAmount;
	private JTextField txtUsername;
	private JPasswordField pswdPIN;
	private JLabel lblInvalid;
	private JLabel lblSelectAction;
	private JTextField txtOldPin;
	private JTextField txtNewPin;
	
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
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
*/
	/**
	 * Create the application.
	 */
	public BankFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkLogin(txtUsername.getText(), pswdPIN.getPassword())) {
					loginPanel.setVisible(false);
					menuPanel.setVisible(true);
				}else {
					lblInvalid.setVisible(true);
				}
				
			}
		});
		btnLogin.setForeground(SystemColor.windowText);
		btnLogin.setBackground(UIManager.getColor("Button.light"));
		btnLogin.setBounds(347, 351, 75, 25);
		loginPanel.add(btnLogin);
		
		JLabel lblBanner = new JLabel("<html><center>Welcome to the banking application.</center><center> Please login to proceed.</center>");
		lblBanner.setForeground(Color.WHITE);
		lblBanner.setBackground(Color.DARK_GRAY);
		lblBanner.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
		lblBanner.setBounds(235, 48, 334, 85);
		loginPanel.add(lblBanner);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Username");
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtUsername.setBackground(UIManager.getColor("TextField.light"));
		txtUsername.setBounds(347, 228, 125, 25);
		txtUsername.setColumns(10);
		loginPanel.add(txtUsername);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setLabelFor(txtUsername);
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(235, 230, 100, 25);
		loginPanel.add(lblUsername);
		
		pswdPIN = new JPasswordField();
		pswdPIN.setToolTipText("PIN");
		pswdPIN.setBackground(UIManager.getColor("TextField.light"));
		pswdPIN.setBounds(347, 296, 125, 25);
		loginPanel.add(pswdPIN);
						
		JLabel lblPIN = new JLabel("PIN");
		lblPIN.setForeground(new Color(255, 255, 255));
		lblPIN.setLabelFor(pswdPIN);
		lblPIN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPIN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPIN.setBounds(235, 295, 100, 25);
		loginPanel.add(lblPIN);
		
		lblInvalid = new JLabel("Invalid Username or PIN.  Please try again.");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setForeground(Color.RED);
		lblInvalid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInvalid.setBounds(203, 416, 396, 62);
		lblInvalid.setVisible(false);
		loginPanel.add(lblInvalid);
		
		JButton btnChecking = new JButton("Checking");
		btnChecking.setBounds(349, 236, 125, 25);
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
		btnSavings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fChecking = false;
				fSavings = true;
				menuPanel.setVisible(false);
				choicePanel.setVisible(true);
			}
		});
		btnSavings.setBounds(349, 294, 125, 25);
		menuPanel.add(btnSavings);
		
		JButton btnChangePin = new JButton("Change PIN");
		btnChangePin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(false);
				changePINPanel.setVisible(true);
			}
		});
		btnChangePin.setBounds(349, 355, 125, 25);
		menuPanel.add(btnChangePin);
		
		JLabel lblMenu = new JLabel("<html><center>Please select one of the following options:</center>");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setForeground(Color.WHITE);
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMenu.setBackground(Color.DARK_GRAY);
		lblMenu.setBounds(246, 134, 334, 50);
		menuPanel.add(lblMenu);
			
		JTextField lblCurrentBalance = new JTextField();
		lblCurrentBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentBalance.setEditable(false);
		lblCurrentBalance.setForeground(Color.WHITE);
		lblCurrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCurrentBalance.setBackground(Color.DARK_GRAY);
		lblCurrentBalance.setText("Current Balance: ");
		lblCurrentBalance.setBounds(172, 226, 160, 25);
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
		txtCurrentBalance.setBounds(407, 226, 165, 25);
		choicePanel.add(txtCurrentBalance);
		
		String labels[] = {"", "Withdraw", "Deposit"};
		JComboBox cmboChoice = new JComboBox(labels);
		cmboChoice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmboChoice.setBounds(172, 297, 160, 25);
		choicePanel.add(cmboChoice);
		
		txtAmount = new JTextField();
		txtAmount.setToolTipText("Enter Amount");
		txtAmount.setBounds(407, 297, 160, 25);
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
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSubmit.setBounds(321, 392, 97, 25);
		choicePanel.add(btnSubmit);
		
		lblSelectAction = new JLabel("Please make a selection.");
		lblSelectAction.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSelectAction.setForeground(Color.RED);
		lblSelectAction.setBounds(270, 470, 234, 25);
		lblSelectAction.setVisible(false);
		choicePanel.add(lblSelectAction);
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(true);
				choicePanel.setVisible(false);
			}
		});
		btnBackToMain.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBackToMain.setBounds(255, 590, 246, 40);
		choicePanel.add(btnBackToMain);
		
		changePINPanel = new JPanel();
		changePINPanel.setBackground(Color.DARK_GRAY);
		frmBankStart.getContentPane().add(changePINPanel, "name_680254008249323");
		changePINPanel.setLayout(null);
		
		JLabel lblCurrentPin = new JLabel("Current PIN");
		lblCurrentPin.setForeground(Color.WHITE);
		lblCurrentPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentPin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCurrentPin.setBounds(164, 248, 153, 33);
		changePINPanel.add(lblCurrentPin);
		
		txtOldPin = new JTextField();
		txtOldPin.setBounds(341, 248, 169, 33);
		changePINPanel.add(txtOldPin);
		txtOldPin.setColumns(10);
		
		txtNewPin = new JTextField();
		txtNewPin.setColumns(10);
		txtNewPin.setBounds(341, 309, 169, 33);
		changePINPanel.add(txtNewPin);
		
		JLabel lblNewPin = new JLabel("New PIN");
		lblNewPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPin.setForeground(Color.WHITE);
		lblNewPin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewPin.setBounds(164, 309, 153, 33);
		changePINPanel.add(lblNewPin);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePIN();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.setBounds(303, 407, 97, 25);
		changePINPanel.add(button);
		
		JLabel lblInvalidPIN = new JLabel("Current PIN is invalid");
		lblInvalidPIN.setForeground(Color.RED);
		lblInvalidPIN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInvalidPIN.setBounds(268, 462, 260, 33);
		lblInvalidPIN.setVisible(false);
		changePINPanel.add(lblInvalidPIN);
		
		JButton btnMainMenu = new JButton("Back to Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePINPanel.setVisible(false);
				menuPanel.setVisible(true);
			}
		});
		btnMainMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMainMenu.setBounds(230, 551, 246, 40);
		changePINPanel.add(btnMainMenu);
	}
	
	private boolean checkLogin(String name, char[] pswd) {
		//check login name and PIN with database and if a match the return true, else return false
		return true;
	}
	
	private void updatePIN(){
		//change the PIN stored in the database
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
	}
	
	private void depositSavings(float amount) {
		//add amount to the database here
	}
	
	private void withdrawChecking(float amount) {
		//add amount to the database here
	}
	
	private void withdrawSavings(float amount) {
		//add amount to the database here
	}
}
