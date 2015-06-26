package edu.govst.advdb.finalproject.dal;

public enum DbTypes {
	ORACLE, MSSQL;
	
@Override
public String toString() {
	switch(this) {
		case ORACLE: 
			return "jdbc:oracle:thin:@//localhost:1521/advdbfinal";
		case MSSQL: 
			return "jdbc:sqlserver://localhost:1433\\MSSQLSERVER;databasename=advdbfinal;integratedsecurity=false;user=sa;password=secret";
		default:
			throw new IllegalArgumentException();
		}
	}
}
