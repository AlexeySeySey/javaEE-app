package validator;

import java.util.Arrays;
import annotation.Action;
import java.lang.Class;
import contract.IServletHandleable;

public final class ServletActionValidator {
	
	public void validate(
			String reqAction, 
			Class<? extends IServletHandleable> handler
	) throws Exception {
		if (reqAction == null) throw new Exception("Action missing");
		boolean validAction = Arrays.stream(handler.getDeclaredMethods())
				.filter(action -> action.isAnnotationPresent(Action.class))
				.map(action -> action.getName())
				.anyMatch(reqAction::equals);
		if (!validAction) throw new Exception("Invalid action");
	}
}
