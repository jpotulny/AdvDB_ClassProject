//Coded by Mike Schmidt, 2015

package edu.govst.advdb.finalproject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

	public int checkLogin(String name, String password) throws RemoteException;
	public float checkBalance(int choice) throws RemoteException;
	public boolean depositFunds(float amount) throws RemoteException;
	public boolean checkPassword(String pass) throws RemoteException;
	public boolean updatePassword(String pass) throws RemoteException;
}