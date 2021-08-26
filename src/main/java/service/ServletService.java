package service;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import contract.IServletHandleable;
import validator.ServletActionValidator;

import java.util.Arrays;
import java.util.HashMap;

import constant.Error;
import exception.NothingFoundException;
import java.util.NoSuchElementException;

public final class ServletService {
	
	public void invokeAction(
			String verb,
			HttpServletRequest request, 
			HttpServletResponse response, 
			IServletHandleable servletHandler
	) throws Exception {
		var servletHandlerClass = servletHandler.getClass();
		(new ServletActionValidator()).validate(verb, request, response, servletHandlerClass);
		try {
		servletHandlerClass
		.getMethod(request.getParameter("action"), HttpServletRequest.class, HttpServletResponse.class)
		.invoke(servletHandler, request, response);
		} catch (Exception e) {
			throw new Exception(e.getCause().getMessage());
		}
	}
	
	public String getCookie(HttpServletRequest request, String name) throws Exception, NothingFoundException {
		
	    var cookieFound = Arrays.stream(request.getCookies())
				.filter(cookie -> name.equals(cookie.getName()))
				.map(cookie -> cookie.getValue())
				.findFirst();
	    
		String cookieValue = null;
		try {
			cookieValue = cookieFound.get();
		} catch (NoSuchElementException e) {
			throw new NothingFoundException(Error.COOKIE_NOT_FOUND.get());
		}
		
		return cookieValue;
	}
	
	public HashMap<String, String> getRequestBody(HttpServletRequest request) throws Exception {
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	      sb.append(line + "\n");
	      line = reader.readLine();
	    }
	    reader.close();
	    String params = sb.toString();
	    String[] _params = params.split("&");
	    HashMap<String, String> result = new HashMap<String, String>();
	    for (String param : _params) {
	      String[] keyValue = param
	    		  .replaceAll("[{,},\"]", "")
	    		  .split(":");	
	      result.put(keyValue[0], keyValue[1]);
	    }
	    return result;
	  }
	
	public void jsonError(Exception exception, HttpServletResponse response) throws IOException {
		exception.printStackTrace();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(String.format("{\"error\":\"%s\"}", exception.getMessage()));
		response.getWriter().flush();  
	}
}
