package contract;

import java.util.ArrayList;

import entity.User;
import exception.NothingFoundException;

public interface ITodoRepository extends IBaseRepository {
	public ArrayList<IEntity> findAllFor(User owner) throws Exception, NothingFoundException;
	public void create(long userId, String text) throws Exception;
	public void update(long id, String text) throws Exception;
}
