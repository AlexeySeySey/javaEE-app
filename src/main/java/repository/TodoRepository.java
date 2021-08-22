package repository;

import service.DatabaseService;
import java.util.ArrayList;
import entity.Todo;
import entity.User;
import mapper.TodoMapper;
import exception.NothingFoundException;
import constant.Error;

public class TodoRepository {
	
	public static final String table = "todos";
	
	private DatabaseService dbService;
	
	private TodoMapper todoMapper;
	
	public TodoRepository() throws Exception {
		this.dbService = new DatabaseService();
		this.todoMapper = new TodoMapper();
	}
	
	public ArrayList<Todo> findAllFor(User owner) throws Exception, NothingFoundException {
		
		var stmt = this.dbService.prepare("select * from " + table + " where user_id=?");
		stmt.setLong(1, owner.getId());
		
		var results = this.todoMapper.toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_TODO_FOUND.get());
		}
		return results;
	}
	
	public void create(long userId, String text) throws Exception {
		var preparedStatement = this.dbService.prepare("insert into " + table + "(user_id, text) values(?,?)");
		preparedStatement.setLong(1, userId);
		preparedStatement.setString(2, text);
		preparedStatement.execute();
	} 
	
	public void update(String id, String text) throws Exception {
		var preparedStatement = this.dbService.prepare("update " + table + " set text=? where id=?");
		preparedStatement.setString(1, text);
		preparedStatement.setString(2, id);
		preparedStatement.execute();
	}
	
	public void delete(String id) throws Exception {
		var preparedStatement = this.dbService.prepare("delete from " + table + " where id=?");
		preparedStatement.setString(1, id);
		preparedStatement.execute();
	}
}
