package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authenticator {

	public static boolean login(User user) {
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
					// found a match, set the users account id and return
					user.setId(userId);
					return true; 
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return false; //did not find a match
	}
	

	public static boolean signUp(User user) {
        //before we add a new user, we need to check if the username is taken first

        	String username = user.getUsername();
        	String password = user.getPassword();

        	Connection con = null;
        	Statement statement = null;
        	ResultSet rs = null;

        	String usernameDB = "";

        	try {
            	con = Link.linkToDB();//connect to db
            	statement = con.createStatement();
            	rs = statement.executeQuery("select user_name from users");

            	while(rs.next()) { // loop through the table and check for matches
                	usernameDB = rs.getString("user_name");

                	if(username.equals(usernameDB)) {
                    	//invalid username return false
                    	return false;
                	}

            	} 
        	}catch(Exception e) {
            	    e.printStackTrace();
        	}

        	//valid id 
        	try {
            	    statement.executeUpdate("insert into users (user_name,user_password) values ('"+username+"', '"+password+"');");
        	}catch(Exception e) {
            	    e.printStackTrace();
        	}
       		return true;
    	}

}
		


