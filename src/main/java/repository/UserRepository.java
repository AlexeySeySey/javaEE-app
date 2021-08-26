package repository;

import mapper.UserMapper;
import contract.IMapper;

public final class UserRepository extends BaseRepository {
	
	private UserMapper userMapper;
	
	public UserRepository() throws Exception {
		super();
		this.userMapper = new UserMapper();
	}
	
	@Override
	public String getTable() {
		return "users";
	}

	@Override
	protected IMapper getMapper() {
		return this.userMapper;
	}
	
	public void create(String email, String password) throws Exception {
		var stmt = this.dbService.prepare("insert into " + this.getTable() + "(email, password) values(?,?)");
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.execute();
	}
}
