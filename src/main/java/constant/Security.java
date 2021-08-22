package constant;

public enum Security {

	AUTH_TOKEN("jee_todo_list_token"),
	AUTH_SALT("83f02448-f589-46ae-9729-45add76f03a4");
	
	private String data;
	
	Security(String data) {
		this.data = data;
	}
	
	public String get() {
		return this.data;
	}
}
