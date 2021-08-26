package validator;

import java.util.Arrays;
import annotation.*;
import java.lang.Class;
import contract.IServletHandleable;
import service.SecurityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exception.NothingFoundException;
import constant.Error;

public final class ServletActionValidator {
	
	private SecurityService securityService;
	
	public ServletActionValidator() throws Exception {
		this.securityService = new SecurityService();
	}
	
	public void validate(
			String verb,
			HttpServletRequest request,
			HttpServletResponse response,
			Class<? extends IServletHandleable> handler
	) throws Exception {
		
		String reqAction = request.getParameter("action");
		
		if (reqAction == null) {
			throw new Exception(Error.ACTION_MISS.get());
		}
		
		boolean validAction = Arrays.stream(handler.getDeclaredMethods())
				.filter(action -> action.isAnnotationPresent(Action.class))
				.filter(action -> action.getAnnotation(Action.class).verb().equals(verb))
				.map(action -> action.getName())
				.anyMatch(reqAction::equals);
		
		if (!validAction) {
			throw new Exception(Error.ACTION_INVALID.get());
		}
		var currentAction = handler.getDeclaredMethod(reqAction, HttpServletRequest.class, HttpServletResponse.class);
		
		boolean sharedAction = !currentAction.isAnnotationPresent(Guest.class) && !currentAction.isAnnotationPresent(Authenticated.class);
		boolean guestOnlyAction = currentAction.isAnnotationPresent(Guest.class) && !currentAction.isAnnotationPresent(Authenticated.class);
		boolean authOnlyAction = !currentAction.isAnnotationPresent(Guest.class) && currentAction.isAnnotationPresent(Authenticated.class);
		
		
		try {
		  this.securityService.getCurrentUser(request);
		} catch (NothingFoundException e) {
			if (guestOnlyAction || sharedAction) {
				return;
			}
			throw new Exception(Error.FORBIDDEN.get());
		}
		if (authOnlyAction || sharedAction) {
			return;
		}
		throw new Exception(Error.FORBIDDEN.get());
	}
}
