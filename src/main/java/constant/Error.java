package constant;

public enum Error {
	
	NO_ITEM_FOUND("No items found"),
	SHORT_TEXT("Text is too short"),
	INVALID_TODO_ID("Invalid todo identifier"),
	ACTION_MISS("Action missing"),
	ACTION_INVALID("Action is invalid"),
	FORBIDDEN("Action is not allowed"),
	CREDENTIALS_LACK("Not enough credentials"),
	INVALID_EMAIL("Invalid email"),
	COOKIE_NOT_FOUND("Cookie not found"),
	SHORT_PASSWORD("Password should be 8 character length or longer");
	
	private String data;
	
	Error(String data) {
		this.data = data;
	}
	
	public String get() {
		return this.data;
	}
}
