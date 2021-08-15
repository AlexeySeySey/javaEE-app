package constant;

public enum Database {
	
	URL("jdbc:mysql://localhost:3306/jee_app_db"),
	USERNAME("root"),
	PASSWORD("root");
	
	private String data;
	
	Database(String data) {
		this.data = data;
	}
	
	public String get() {
		return this.data;
	}
}
