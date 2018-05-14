package darzelis.model;

public class User {
	private String name;
	private String surname;
	private String username;
	private String password;
	private int userlevel;
	private String email;
	
	public User() {
	}

	public User(String username, String name, String surname,  String password, int userlevel, String email) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.userlevel = userlevel;
		this.email = email;
	}

	public User(String username, String name, String surname, int userlevel, String email) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.userlevel = userlevel;
		this.email = email;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
