package mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import entity.User;

public final class UserMapper {
	
	public ArrayList<User> toArrayList(ResultSet resultSet) throws Exception {
		
		ArrayList<User> users = new ArrayList<User>();
	
		while(resultSet.next()) {
			users.add(
					new User(resultSet.getLong("id"), resultSet.getString("email"), resultSet.getString("password"))
			);
		}
		
		return users;
	}
}
