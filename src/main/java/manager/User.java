package manager;

import java.util.ArrayList;

public class User {

	private String username;
	private String password;
	private int userId;
	private ArrayList<Logins> userLogins;

	public User (String username, String password) {
		this.username = username;
		this.password = password;
		this.userLogins = new ArrayList<>();
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

	public void addUserLogin(Logins l) {
		userLogins.add(l);
	}

	public void showLogins() {
		for(Logins l: userLogins) {
			System.out.println(l.getUrl());
		}
	}



}

