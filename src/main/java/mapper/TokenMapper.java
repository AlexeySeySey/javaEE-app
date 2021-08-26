package mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import contract.IEntity;
import contract.IMapper;
import entity.Token;

public final class TokenMapper implements IMapper {
	
	@Override
	public ArrayList<IEntity> toArrayList(ResultSet resultSet) throws Exception {
		
		ArrayList<IEntity> tokens = new ArrayList<IEntity>();
		
		while(resultSet.next()) {
			tokens.add(
					new Token(
							resultSet.getLong("id"), 
							resultSet.getLong("user_id"), 
							resultSet.getString("token"), 
							resultSet.getString("expired_at")
					)
			);
		}	
		return tokens;
	}
}
