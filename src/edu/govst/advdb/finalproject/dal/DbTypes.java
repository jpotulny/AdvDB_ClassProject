package edu.govst.advdb.finalproject.dal;

public enum DbTypes {
	ORACLE, MSSQL;
	
@Override
public String toString() {
	switch(this) {
		case ORACLE: 
			return "jdbc:oracle:thin:@192.168.1.150:1521:orcl";
		case MSSQL: 
			return "jdbc:sqlserver://localhost:1433\\MSSQLSERVER;databasename=advdbfinal;integratedsecurity=false;user=sa;password=secret";
		default:
			throw new IllegalArgumentException();
		}
	}
}
