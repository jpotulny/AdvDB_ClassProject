//Coded by Mike Schmidt, 2015


package edu.govst.advdb.finalproject;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.govst.advdb.finalproject.Constant;

public class RMIServer {

	public static void main(String args[]) throws RemoteException, AlreadyBoundException{
		RemoteImplementation impl = new RemoteImplementation();
		Registry registry = LocateRegistry.createRegistry(Constant.RMI_port);
		registry.bind(Constant.RMI_ID, impl);
		System.out.println(Constant.RMI_ID + " has started on port " + Constant.RMI_port);
	}
}
