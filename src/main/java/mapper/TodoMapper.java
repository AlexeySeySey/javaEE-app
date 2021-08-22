package mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import entity.Todo;

public final class TodoMapper {
	
	public ArrayList<Todo> toArrayList(ResultSet resultSet) throws Exception {
		
		ArrayList<Todo> todos = new ArrayList<Todo>();
		
		while(resultSet.next()) {
			todos.add(
					new Todo(resultSet.getLong("id"), resultSet.getLong("user_id"), resultSet.getString("text"))
			);
		}
		
		return todos;
	}
}
