package manager;

import java.sql.Connection;
import java.sql.DriverManager;

public class Link {

	public static Connection linkToDB() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/PasswordManager";
		String username = "manager";
		String password = "Manager@123";

		try {
			con = DriverManager.getConnection(url, username, password); // connect to database

		}catch(Exception e) {
			e.printStackTrace();//for testing
		}
		return con; // return the connection if suceeded
	}



}

