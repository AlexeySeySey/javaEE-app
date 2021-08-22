package repository;

import entity.User;
import service.DatabaseService;
import mapper.UserMapper;
import java.util.ArrayList;
import java.util.Map;
import java.lang.StringBuilder;
import exception.NothingFoundException;
import constant.Error;

public final class UserRepository {
	
    public static String table = "users";
	
	private DatabaseService dbService;
	
	private UserMapper userMapper;
	
	public UserRepository() throws Exception {
		this.dbService = new DatabaseService();
		this.userMapper = new UserMapper();
	}
	
	public ArrayList<User> findBy(String field, String value) throws Exception, NothingFoundException {
		
		var stmt = this.dbService.prepare("select * from " + table + " where " + field + "=?");
		stmt.setString(1, value);

		var results = this.userMapper.toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_USER_FOUND.get());
		}
		return results;
	}
	
    public ArrayList<User> findBy(Map<String, String> fields) throws Exception, NothingFoundException {
    	
    	var query = new StringBuilder("select * from " + table + " where ");
    	for (int i = 0; i < fields.size(); i++) {
    		query.append("?=? and ");
    	}
    	int iteration = 0;
    	String queryString = query.toString();
    	queryString = queryString.substring(0, queryString.length() - 5);
    	var stmt = this.dbService.prepare(queryString);
    	var iterator = fields.entrySet().iterator();
		while (iterator.hasNext()) {
			iteration += 1;
			var entry = iterator.next();
			stmt.setString(iteration, entry.getKey());
			iteration += 1;
			stmt.setString(iteration, entry.getValue());
		}
		var results = this.userMapper.toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_USER_FOUND.get());
		}
		return results;
	}
	
	public void create(String email, String password) throws Exception {
		var stmt = this.dbService.prepare("insert into " + table + "(email, password) values(?,?)");
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.execute();
	}
}
