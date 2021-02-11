package manager;

public class User {

	String username;
	String password;
	int userId;

	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public int getId() {
		return userId;
	}

}

