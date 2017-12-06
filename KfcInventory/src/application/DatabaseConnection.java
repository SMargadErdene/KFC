package application;

import java.sql.*;

public class DatabaseConnection {

	private Connection connection;
	private String url = "jdbc:mysql://localhost/kfc";
	private String user = "root";
	private String pass = "mysql2017";

	public DatabaseConnection() {
		connection = null;
	}
	
	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException ex1) {
			System.err.println("Error: " + ex1.getMessage());
		} catch (InstantiationException ex2) {
			System.err.println("Error: " + ex2.getMessage());
		} catch (IllegalAccessException ex3) {
			System.err.println("Error: " + ex3.getMessage());
		} catch (SQLException ex4) {
			System.err.println("Error: " + ex4.getMessage());
		}
		return this.connection;
	}

	public Connection getConnection(){
		try {
			if (connection != null && !connection.isClosed())
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connect();
		return this.connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement createStatement() {
		// TODO Auto-generated method stub
		return null;
	}
}
