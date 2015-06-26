package edu.govst.advdb.finalproject.dal;

import java.util.ArrayList;
import java.util.List;

import edu.govst.advdb.finalproject.models.Saving;

public class SavingDAO implements basicCrud<Saving,String> {

	private DbTypes type;
	List<Saving> savingsAccounts;
	
	public SavingDAO(DbTypes type) {
		this.type = type;
		savingsAccounts = new ArrayList<Saving>();
	}

	@Override
	public void createRecord(Saving record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteRecord(String record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(Saving record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Saving getRecord(String record) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
