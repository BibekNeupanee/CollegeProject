package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
	private static Connection connection;
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/billing_system";
		final String uname = "root";
		final String pwd = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, uname, pwd);
			return connection;
		} catch (Exception ex) {
			System.out.println(ex);

		}
		return null;

	}

}