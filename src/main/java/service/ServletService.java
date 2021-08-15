package service;

import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import contract.IServletHandleable;
import validator.ServletActionValidator;
import java.util.HashMap;

public final class ServletService {
	
	public void invokeAction(
			HttpServletRequest request, 
			HttpServletResponse response, 
			IServletHandleable servletHandler
	) throws Exception {
		var action = request.getParameter("action");
		var servletHandlerClass = servletHandler.getClass();
		(new ServletActionValidator()).validate(action, servletHandlerClass);		
		servletHandlerClass
		.getMethod(action, HttpServletRequest.class, HttpServletResponse.class)
		.invoke(servletHandler, request, response);
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
}
