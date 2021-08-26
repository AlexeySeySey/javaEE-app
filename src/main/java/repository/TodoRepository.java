package repository;

import java.util.ArrayList;
import contract.IEntity;
import contract.IMapper;
import entity.User;
import mapper.TodoMapper;
import exception.NothingFoundException;
import contract.ITodoRepository;

public class TodoRepository extends BaseRepository implements ITodoRepository {
	
	private TodoMapper todoMapper;
	
	public TodoRepository() throws Exception {
		super();
		this.todoMapper = new TodoMapper();
	}
	
	@Override
	public String getTable() {
		return "todos";
	}

	@Override
	protected IMapper getMapper() {
		return this.todoMapper;
	}
	
	@Override
	public ArrayList<IEntity> findAllFor(User owner) throws Exception, NothingFoundException {
		
		var stmt = this.dbService.prepare("select * from " + this.getTable() + " where user_id=?");
		stmt.setLong(1, owner.getId());
		
		return this.todoMapper.toArrayList(stmt.executeQuery());
	}
	
	@Override
	public void create(long userId, String text) throws Exception {
		var preparedStatement = this.dbService.prepare("insert into " + this.getTable() + "(user_id, text) values(?,?)");
		preparedStatement.setLong(1, userId);
		preparedStatement.setString(2, text);
		preparedStatement.execute();
	} 
	
	@Override
	public void update(long id, String text) throws Exception {
		var preparedStatement = this.dbService.prepare("update " + this.getTable() + " set text=? where id=?");
		preparedStatement.setString(1, text);
		preparedStatement.setLong(2, id);
		preparedStatement.execute();
	}
}
