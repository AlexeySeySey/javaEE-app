package repository;

import service.DatabaseService;

import constant.Error;

import exception.NothingFoundException;

import mapper.UserMapper;

import entity.User;

import service.HelperService;

public final class TokenRepository {
	
	public static final String table = "tokens";
	
	private DatabaseService dbService;
	
	private UserMapper userMapper;
	
	private HelperService helperService;
	
	public TokenRepository() throws Exception {
		this.dbService = new DatabaseService();
		this.userMapper = new UserMapper();
		this.helperService = new HelperService();
	}
	
	public User findUserByToken(String token) throws Exception, NothingFoundException {  
		
		String currentDate = this.helperService.getCurrentDate();

		var stmt = this.dbService.prepare(
				"select u.* from " + table + " t left join " + UserRepository.table + " u where t.token=? where t.expired_at < ? limit 1"
		);
		stmt.setString(1, token);
		stmt.setString(2, currentDate);
	
		var results = this.userMapper.toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_USER_FOUND.get());
		}
		return results.get(0);
	}
	
	public void create(long userId, String token, String expiredAt) throws Exception {
		var stmt = this.dbService.prepare("insert into " + table + " (user_id, token, expired_at) values(?,?,?)");
		stmt.setLong(1, userId);
		stmt.setString(2, token);
		stmt.setString(3, expiredAt);
		stmt.execute();
	}
	
	public void deleteWhere(String field, String value) throws Exception {
		var stmt = this.dbService.prepare("delete from " + table + " where ?=?");
		stmt.setString(1, field);
		stmt.setString(2, value);
		stmt.execute();
	}
}
