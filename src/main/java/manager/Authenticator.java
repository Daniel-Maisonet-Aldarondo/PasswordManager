package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authenticator {

	public static User authenticate(User user) {
		//initial values
		String username = user.getUsername();
		String password = user.getPassword();

		
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;

		String usernameDB = "";
		String passwordDB = "";
		int userId; // account id will be assigned once credentails are matched

		try {
			con = Link.linkToDB();// connecto to the database
			statement = con.createStatement();
			rs = statement.executeQuery("select user_id,user_name,user_password from users"); 

			while(rs.next()) { // loop through the database until a match is found for the username/password or until you reach the end
				userId = rs.getInt("user_id");
				usernameDB = rs.getString("user_name");
				passwordDB = rs.getString("user_password");
			

				if(username.equals(usernameDB) && password.equals(passwordDB)) {
					// found a match, set the account id and return the user
					user.setId(userId);
					return user; 
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null; //did not find a match
	}





}
		


