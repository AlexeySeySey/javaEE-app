package handler;

import contract.IServletHandleable;
import annotation.Action;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public final class SecurityServletHandler implements IServletHandleable {
	
	@Action
	public void showRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO: allowed if user is guest
		request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
	}
	
	@Action
	public void handleRegistration(HttpServletRequest request, HttpServletResponse response) {
		// TODO: allowed if user is guest
		// TODO: register user and call this.handleLogin afterwords, you got login and password from form
	}
	
	@Action
	public void showLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO: allowed if user is guest
		request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
	}
	
	@Action
	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO: allowed if user is guest
		// TODO: sign in user, you got login and password from form, redirect to todos aferwords
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO: allowed if user logged in
		// TODO: logout user, call showLogin
	}
}
