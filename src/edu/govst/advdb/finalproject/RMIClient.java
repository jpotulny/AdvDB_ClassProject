//Coded by Mike Schmidt, 2015
package edu.govst.advdb.finalproject;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.util.Scanner;
import edu.govst.advdb.finalproject.Constant;
import edu.govst.advdb.finalproject.RMIInterface;

public class RMIClient {

	public static void main(String args[]) throws RemoteException,
			NotBoundException {

		boolean run = false;
		Scanner scan = new Scanner(System.in);
		Registry registry = LocateRegistry.getRegistry("localhost",
				Constant.RMI_port);
		RMIInterface remote = (RMIInterface) registry.lookup(Constant.RMI_ID);

		while (!run) {
			int input = 0;
			int accountSelection = 0;
			float money = 0;
			String userName;
			String password;
			int userID = -1; 
			DecimalFormat df = new DecimalFormat("#.00");

			System.out.println("Welcome to the Banking Application");
			System.out.println("Please enter your user name: ");
			userName = scan.nextLine();
			System.out.println("Please enter your password: ");
			password = scan.nextLine();
			//userID = 1;
			userID = remote.checkLogin(userName, password);
			if (userID > -1) {

				System.out.println("Main Menu");
				System.out.println("1. Check Balance");
				System.out.println("2. Deposit Funds");
				System.out.println("3. Withdraw Funds");
				System.out.println("4. Change Password");
				System.out.println("5. Logout");
				input = scan.nextInt();

				switch (input) {
				case 1:
					System.out
							.println("Which account balance would you like to check?");
					System.out.println("1. Checking");
					System.out.println("2. Savings");
					System.out.println("3. Return to Main Menu");
					accountSelection = scan.nextInt();
					switch (accountSelection) {
					case 1:
						System.out
								.println("Your checking account balance is: $"
										+ df.format(remote
												.checkBalance(accountSelection)));
						break;
					case 2:
						System.out.println("Your savings account balance is: $"
								+ df.format(remote
										.checkBalance(accountSelection)));
						break;
					case 3:
						break;
					}
					break;

				case 2:
					System.out
							.print("How much would you like to deposit into your checking account?");
					money = scan.nextFloat();
					if (remote.depositFunds(money)) {
						System.out.println("$" + df.format(money)
								+ " has been added to your checking account");
					} else {
						System.out
								.println("Funds were not received. Please try again later.");
					}
					break;

				case 3:
					System.out
							.print("How much would you like to withdraw from your checking account?");
					money = scan.nextFloat();
					if (remote.depositFunds(money)) {
						System.out
								.println("$"
										+ df.format(money)
										+ " has been deducted from your checking account");
					} else {
						System.out
								.println("Funds could not be withdrawn. Please try again later.");
					}

					break;

				case 4:
					System.out.println("Please enter current password: ");
					password = scan.nextLine();
					if (remote.checkPassword(password)) {
						System.out.println("Please enter new password: ");
						password = scan.nextLine();
						if(remote.updatePassword(password)) {
						System.out.println("Password successfully updated.");
						}
					} else{
						System.out.println("Password not correct. Returning to main menu.");
					}					
					break;

				case 5:
					run = true;
					break;
				}
			}
		}
		scan.close();
		System.out.println("Goodbye");
		System.out.println("Exiting " + Constant.RMI_ID);
	}

}
