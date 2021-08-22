package service;

import entity.User;
import repository.TokenRepository;
import javax.servlet.http.HttpServletRequest;

import constant.Security;

import java.security.MessageDigest;
import java.lang.StringBuilder;

public final class SecurityService {

	private ServletService servletService;
	
	private TokenRepository tokenRepository;
	
	public SecurityService() throws Exception {
		this.servletService = new ServletService();
		this.tokenRepository = new TokenRepository();
	}

	public String encryptString(String data) throws Exception {
		
		var bytes = MessageDigest.getInstance("SHA-1").digest(data.getBytes());
		
		var stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}
		return stringBuilder.toString();
    }

	public User getCurrentUser(HttpServletRequest request) throws Exception {

		String token = this.servletService.getCookie(request, Security.AUTH_TOKEN.get());
		
		return this.tokenRepository.findUserByToken(token);
	}
}
