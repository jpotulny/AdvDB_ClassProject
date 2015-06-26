package edu.govst.advdb.finalproject.dal;

public interface basicCrud<T,U> {

	public void createRecord(T record);
	public boolean deleteRecord(U record);
	public boolean updateRecord(T record);
	public T getRecord(U record);	
}
