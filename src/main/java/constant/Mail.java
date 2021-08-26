package constant;

public enum Mail {
	
	FROM("doe674263@gmail.com"),
	PASSWD("6ab59832-7958-4b96-952b-88f0866be58e"),
	HOST("smtp.gmail.com");
	
	private String data;
	
	Mail(String data) {
		this.data = data;
	}
	
	public String get() {
		return this.data;
	}
}
