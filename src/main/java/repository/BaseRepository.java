package repository;

import java.util.ArrayList;
import java.util.Map;
import contract.IMapper;
import contract.IEntity;
import constant.Error;
import exception.NothingFoundException;
import service.DatabaseService;
import java.lang.Number;
import java.lang.StringBuilder;

public abstract class BaseRepository {

	protected DatabaseService dbService;

	public abstract String getTable();

	protected abstract IMapper getMapper();

	public BaseRepository() throws Exception {
		this.dbService = new DatabaseService();
	}
	
	public void deleteById(long id) throws Exception {
		var preparedStatement = this.dbService.prepare("delete from " + this.getTable() + " where id=?");
		preparedStatement.setLong(1, id);
		preparedStatement.execute();
	}

	public ArrayList<IEntity> findBy(String field, String value) throws Exception, NothingFoundException {

		var stmt = this.dbService.prepare("select * from " + this.getTable() + " where " + field + "=?");
		stmt.setString(1, value);

		var results = this.getMapper().toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_ITEM_FOUND.get());
		}
		return results;
	}

	public ArrayList<IEntity> findBy(Map<String, String> fields) throws Exception, NothingFoundException {

		var query = new StringBuilder("select * from " + this.getTable() + " where ");
		for (String key : fields.keySet()) {
			query.append(key + "=? and ");
		}
		
		String queryString = query.toString();
		queryString = queryString.substring(0, queryString.length() - 5);
		
		var stmt = this.dbService.prepare(queryString);
		int iteration = 1;
		for (var value : fields.values().toArray()) {
			stmt.setString(iteration, (String) value);
			iteration += 1;
		}
		
		ArrayList<IEntity> results = this.getMapper().toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_ITEM_FOUND.get());
		}
		
		return results;
	}
}
