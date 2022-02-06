package com.revature.bank.util.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private final String fileLocation = "src/main/resources/db.properties";
	
	//eager singleton : we need right now not need to wait
	private static final ConnectionFactory connectionFactory = new ConnectionFactory();
	private Properties prop = new Properties();

	static {
		try {
			Class.forName("org.postgresql.Driver");
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ConnectionFactory() {
		try {
			prop.load(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection connectPostgre() {
		Connection conn = null;
		String url="jdbc:postgresql://postgre-java.postgres.database.azure.com:5432/postgres?currentSchema=bank";
			String admin="az_admin";
			String password="Elgailani1";	
		try {
//			System.out.println("url:- " + prop.getProperty("url"));
//			System.out.println("admin:- " + prop.getProperty("admin"));
//			System.out.println("password:- " + prop.getProperty("password"));
//			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("admin"),
//					prop.getProperty("password"));
			conn = DriverManager.getConnection(url,admin,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public static ConnectionFactory getInstance() {
		return connectionFactory;
	}
}
