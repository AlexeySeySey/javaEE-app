package proxy;

import repository.TodoRepository;

import java.util.ArrayList;
import java.util.Map;

import contract.IEntity;
import contract.ITodoRepository;
import entity.User;
import exception.NothingFoundException;

public final class TodoRepositoryProxy implements ITodoRepository {
	
	private TodoRepository todoRepository;
	
	public TodoRepositoryProxy() throws Exception {
		this.todoRepository = new TodoRepository();
	}

	@Override
	public ArrayList<IEntity> findAllFor(User owner) throws Exception, NothingFoundException {
		return this.todoRepository.findAllFor(owner);
	}

	@Override
	public void create(long userId, String text) throws Exception {
		text = text.replace("\\n", "<br>");
		this.todoRepository.create(userId, text);
	}

	@Override
	public void update(long id, String text) throws Exception {
		text = text.replace("\\n", "<br>");
		this.todoRepository.update(id, text);
	}

	@Override
	public void deleteById(long id) throws Exception {
		this.todoRepository.deleteById(id);
	}

	@Override
	public ArrayList<IEntity> findBy(String field, String value) throws Exception, NothingFoundException {
		return this.todoRepository.findBy(field, value);
	}

	@Override
	public ArrayList<IEntity> findBy(Map<String, String> fields) throws Exception, NothingFoundException {
		return this.todoRepository.findBy(fields);
	}
}
