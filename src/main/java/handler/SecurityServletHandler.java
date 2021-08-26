package handler;

import contract.IServletHandleable;
import entity.User;
import exception.NothingFoundException;
import annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import repository.UserRepository;
import repository.TokenRepository;
import validator.UserCredentialsValidator;
import service.*;
import constant.Security;
import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.ArrayList;
import entity.Token;
import contract.IEntity;
import constant.Error;

public final class SecurityServletHandler implements IServletHandleable {

	private UserRepository userRepository;

	private TokenRepository tokenRepository;

	private SecurityService securityService;

	private HelperService helperService;

	private ServletService servletService;

	private MailService mailService;

	private ThreadService threadService;

	public SecurityServletHandler() throws Exception {
		this.userRepository = new UserRepository();
		this.tokenRepository = new TokenRepository();
		this.securityService = new SecurityService();
		this.helperService = new HelperService();
		this.servletService = new ServletService();
		this.mailService = new MailService();
		this.threadService = new ThreadService();
	}

	@Guest
	@Action(verb = "GET")
	public void showRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
	}

	@Guest
	@Action(verb = "POST")
	public void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {

		new UserCredentialsValidator().validate(request);

		String userEmail = request.getParameter("email");

		String encryptedPassword = this.securityService.encryptString(request.getParameter("password"));

		this.userRepository.create(userEmail, encryptedPassword);

		this.threadService.go(() -> {
			this.mailService.sendMail(userEmail, "Registration", "Registration succeed");
		});

		response.sendRedirect(request.getContextPath() + "/security?action=showLogin");
	}

	@Guest
	@Action(verb = "GET")
	public void showLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
	}

	@Guest
	@Action(verb = "POST")
	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		new UserCredentialsValidator().validate(request);

		User user = null;
		try {
		   user = (User) this.userRepository.findBy("email", request.getParameter("email")).get(0);
		} catch (NothingFoundException e) {
			throw new Exception(Error.INVALID_EMAIL.get());
		}

		String token = this.securityService.encryptString(user.getId() + Security.AUTH_SALT.get());

		this.tokenRepository.create(user.getId(), token, this.helperService.getCurrentDate());
	
		response.addCookie(new Cookie(Security.AUTH_TOKEN.get(), token));

		response.sendRedirect(request.getContextPath() + "/todo?action=getTodos");
	}

	@Authenticated
	@Action(verb = "GET")
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String token = this.servletService.getCookie(request, Security.AUTH_TOKEN.get());

		Token tokenEntity = (Token) this.tokenRepository.findBy("token", token).get(0);

		this.tokenRepository.deleteById(tokenEntity.getId());

		var cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(Security.AUTH_TOKEN.get())) {
					cookie.setMaxAge(0);
					cookie.setValue(null);
					response.addCookie(cookie);
				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/security?action=showLogin");
	}
}
