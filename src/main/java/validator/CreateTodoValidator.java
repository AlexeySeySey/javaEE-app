package validator;

import constant.Error;

public final class CreateTodoValidator {
	
	public void validate(String text) throws Exception {
		if (text == null || (text != null && text.length() == 0)) {
			throw new Exception(Error.SHORT_TEXT.get());
		}
	}
}
