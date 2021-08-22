package constant;

public enum Mail {
	
	FROM("sinyavskij00@gmail.com"),
	HOST("smtp.gmail.com");
	
	private String data;
	
	Mail(String data) {
		this.data = data;
	}
	
	public String get() {
		return this.data;
	}
}
