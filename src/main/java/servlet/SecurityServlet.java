package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ServletService;
import contract.IServletHandleable;
import handler.SecurityServletHandler;
import java.io.IOException;

/**
 * Servlet implementation class SecurityServlet
 */
@WebServlet("/security")
public final class SecurityServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private IServletHandleable servletHandler;
	
	private ServletService servletService;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
		this.servletHandler = new SecurityServletHandler();
        this.servletService = new ServletService();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		this.servletService.invokeAction("GET", request, response, this.servletHandler);
		} catch (Exception e) {
			this.servletService.jsonError(e, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.servletService.invokeAction("POST", request, response, this.servletHandler);
		} catch (Exception e) {
			this.servletService.jsonError(e, response);
		}
	}

}
