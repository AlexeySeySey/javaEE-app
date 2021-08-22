package entity;

public final class User {
	
	private long id;
	
	private String email;
	
	private String password;
	
	public User(long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
}
