package contract;

import java.util.ArrayList;
import java.util.Map;

import exception.NothingFoundException;

public interface IBaseRepository {
	public void deleteById(long id) throws Exception;
	public ArrayList<IEntity> findBy(String field, String value) throws Exception, NothingFoundException;
	public ArrayList<IEntity> findBy(Map<String, String> fields) throws Exception, NothingFoundException;
}
