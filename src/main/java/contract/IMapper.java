package contract;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IMapper {
	public ArrayList<IEntity> toArrayList(ResultSet resultSet) throws Exception;
}
