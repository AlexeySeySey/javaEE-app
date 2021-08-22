package handler;

import contract.IServletHandleable;
import entity.User;
import annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import repository.UserRepository;
import repository.TokenRepository;
import validator.UserCredentialsValidator;
import service.*;
import constant.Security;
import javax.servlet.http.Cookie;

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
	
	@Action
	@Guest
	public void showRegistration(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
	}
	
	@Action
	@Guest
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
	
	@Action
	@Guest
	public void showLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
	}
	
	@Action
	@Guest
	public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		new UserCredentialsValidator().validate(request);
		
		User user = this.userRepository.findBy("email", request.getParameter("email")).get(0);
		
        String token = this.securityService.encryptString(user.getId() + Security.AUTH_SALT.get());
        
        this.tokenRepository.create(user.getId(), token, this.helperService.getCurrentDate());
        
        response.addCookie(new Cookie(Security.AUTH_TOKEN.get(), token));
        
        response.sendRedirect(request.getContextPath() + "/todo?action=getTodos");
	}
	
	@Action
	@Authenticated
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String token = this.servletService.getCookie(request, Security.AUTH_TOKEN.get());
		
		this.tokenRepository.deleteWhere(Security.AUTH_TOKEN.get(), token);
		
		response.sendRedirect(request.getContextPath() + "/security?action=showLogin");
	}
}
