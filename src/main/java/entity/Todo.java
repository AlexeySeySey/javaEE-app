package entity;

public final class Todo {
	
	private long id;
	
	private String text;
	
	public Todo(long id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getText() {
		return this.text;
	}
}
