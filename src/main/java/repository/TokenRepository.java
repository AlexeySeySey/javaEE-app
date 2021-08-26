package repository;

import constant.Error;
import contract.IMapper;
import exception.NothingFoundException;
import mapper.UserMapper;
import entity.User;
import service.HelperService;
import java.util.ArrayList;
import contract.IEntity;
import mapper.TokenMapper;
import entity.Token;

public final class TokenRepository extends BaseRepository {
	
	private HelperService helperService;
	
	private UserRepository userRepository;
	
	private TokenMapper tokenMapper;
	
	public TokenRepository() throws Exception {
		super();
		this.userRepository = new UserRepository();
		this.helperService = new HelperService();
		this.tokenMapper = new TokenMapper();
	}
	
	@Override
	public String getTable() {
		return "tokens";
	}

	@Override
	protected IMapper getMapper() {
		return this.tokenMapper;
	}
	
	public User findUserByToken(String token) throws Exception, NothingFoundException {  
		
		String currentDate = this.helperService.getCurrentDate();

		var stmt = this.dbService.prepare(
				"select u.* from " + this.getTable() + " t left join " + this.userRepository.getTable() + " u on t.user_id=u.id where t.token=? and t.expired_at >= ? limit 1"
		);
		stmt.setString(1, token);
		stmt.setString(2, currentDate);
		
		ArrayList<IEntity> results = new UserMapper().toArrayList(stmt.executeQuery());
		if (results.size() == 0) {
			throw new NothingFoundException(Error.NO_ITEM_FOUND.get());
		}
		return (User) results.get(0);
	}
	
	public void create(long userId, String token, String expiredAt) throws Exception {
		var stmt = this.dbService.prepare("insert into " + this.getTable() + " (user_id, token, expired_at) values(?,?,?)");
		stmt.setLong(1, userId);
		stmt.setString(2, token);
		stmt.setString(3, expiredAt);
		stmt.execute();
	}
}
