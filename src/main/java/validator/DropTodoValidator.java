package validator;

import javax.servlet.http.HttpServletRequest;

public final class DropTodoValidator {
	
	public void validate(HttpServletRequest request) throws Exception {
		if (Long.parseLong(request.getParameter("id")) <= 0) {
			throw new Exception("Invalid todo identifier");
		}
	}
}
