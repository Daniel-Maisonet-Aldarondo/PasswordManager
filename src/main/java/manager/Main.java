package manager;

public class Main {
	public static void main(String[] args) {
		User person = new User("bob","password");
		User person1 = new User("fred", "hola");
		
		System.out.println(Authenticator.signUp(person1));
		System.out.println(Authenticator.login(person));
		System.out.println(Authenticator.login(person1));

		Logins log = new Logins("www.brugh","username","password");
		Logins log1 = new Logins("www.woe","brugh","nuw");
		Logins log2 = new Logins("www.dab", "hello", "nonono");
		Update.updateDB(person1, log);
		Update.updateDB(person,log1);
		Update.updateDB(person1,log2);
		Update.getLoginsDB(person1);
		Update.getLoginsDB(person);
		
		person1.showLogins();
		person.showLogins();

		

	}
}
