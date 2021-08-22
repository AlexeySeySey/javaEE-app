package validator;

import javax.servlet.http.HttpServletRequest;
import constant.Error;

public final class DropTodoValidator {
	
	public void validate(HttpServletRequest request) throws Exception {
		if (Long.parseLong(request.getParameter("id")) <= 0) {
			throw new Exception(Error.INVALID_TODO_ID.get());
		}
	}
}
