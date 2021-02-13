package manager;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class Update {

	public static void updateDB(User user, Logins login) {
		int userId = user.getId();
		String url = login.getUrl();
		String username = login.getUsername();
		String password = login.getPassword();

		Connection con = null;
		Statement statement = null;

		try {
			//connect to database
			con = Link.linkToDB();
			statement = con.createStatement();

			statement.executeUpdate("insert into container (user_id, user_url, user_name, user_password)" +
				" values ('"+userId+"', '" +url+"', '"+username+"', '"+password+"');");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void getLoginsDB(User user) {
		int userId = user.getId();
		int accountId; 
		String url = "";
		String username = "";
		String password = "";

		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;

		try{
			con = Link.linkToDB();
			statement = con.createStatement();
			rs = statement.executeQuery("select * from container;");

			//iterate through the table until you find ID match, then add the login info to the user
			while(rs.next()) {
				accountId = rs.getInt("user_id");
				url = rs.getString("user_url");
				username = rs.getString("user_name");
				password = rs.getString("user_password");
				if(accountId == userId) {
					user.addUserLogin(new Logins(url,username,password));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}


		




	
