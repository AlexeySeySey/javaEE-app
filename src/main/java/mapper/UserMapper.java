package mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import entity.User;
import contract.IMapper;
import contract.IEntity;

public final class UserMapper implements IMapper {
	
	@Override
	public ArrayList<IEntity> toArrayList(ResultSet resultSet) throws Exception {
		
		ArrayList<IEntity> users = new ArrayList<IEntity>();
	
		while(resultSet.next()) {
			users.add(
					new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"))
			);
		}
		return users;
	}
}
