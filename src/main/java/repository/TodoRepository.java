package repository;

import service.DatabaseService;
import java.util.ArrayList;
import entity.Todo;
import mapper.TodoMapper;

public class TodoRepository {
	
	private String table = "todos";
	
	private DatabaseService dbService;
	
	private TodoMapper todoMapper;
	
	public TodoRepository() throws Exception {
		this.dbService = new DatabaseService();
		this.todoMapper = new TodoMapper();
	}
	
	public ArrayList<Todo> findAll() throws Exception {
		
		var resultSet = this.dbService
				.getStatement()
				.executeQuery("select * from " + this.table);
		
		return this.todoMapper.toArrayList(resultSet);
	}
	
	public void create(String text) throws Exception {
		var preparedStatement = this.dbService.prepare("insert into " + this.table + "(text) values(?)");
		preparedStatement.setString(1, text);
		preparedStatement.execute();
	} 
	
	public void update(String id, String text) throws Exception {
		var preparedStatement = this.dbService.prepare("update " + this.table + " set text=? where id=?");
		preparedStatement.setString(1, text);
		preparedStatement.setString(2, id);
		preparedStatement.execute();
	}
	
	public void delete(String id) throws Exception {
		var preparedStatement = this.dbService.prepare("delete from " + this.table + " where id=?");
		preparedStatement.setString(1, id);
		preparedStatement.execute();
	}
}
