package mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import contract.IEntity;
import contract.IMapper;
import entity.Todo;

public final class TodoMapper implements IMapper {
	
	@Override
	public ArrayList<IEntity> toArrayList(ResultSet resultSet) throws Exception {
		
		ArrayList<IEntity> todos = new ArrayList<IEntity>();
		
		while(resultSet.next()) {
			todos.add(
					new Todo(resultSet.getLong("id"), resultSet.getLong("user_id"), resultSet.getString("text"))
			);
		}	
		return todos;
	}
}
