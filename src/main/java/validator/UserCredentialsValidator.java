package validator;

import javax.servlet.http.HttpServletRequest;
import constant.Error;

public final class UserCredentialsValidator {
	
	public void validate(HttpServletRequest request) throws Exception {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || password == null) {
			throw new Exception(Error.CREDENTIALS_LACK.get());
		}

		if (!email.contains("@") || !email.contains(".")) {
			throw new Exception(Error.INVALID_EMAIL.get());
		}

		if (password.length() < 8) {
			throw new Exception(Error.SHORT_PASSWORD.get());
		}
	}
}
