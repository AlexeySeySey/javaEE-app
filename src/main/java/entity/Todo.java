package entity;

public final class Todo {
	
	private long id;
	
	private long userId;
	
	private String text;
	
	public Todo(long id, long userId, String text) {
		this.id = id;
		this.text = text;
		this.userId = userId;
	}
	
	public long getId() {
		return this.id;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public String getText() {
		return this.text;
	}
}
