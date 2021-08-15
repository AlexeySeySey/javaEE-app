package service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import constant.Database;

public final class DatabaseService {
	
	private Connection conn;
	
	public DatabaseService() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection(
				Database.URL.get(),
				Database.USERNAME.get(),
				Database.PASSWORD.get()
	    );
	}
	
	public Statement getStatement() throws Exception {
		return this.conn.createStatement();
	}
	
	public PreparedStatement prepare(String query) throws Exception {
		return this.conn.prepareStatement(query);
	}
}
