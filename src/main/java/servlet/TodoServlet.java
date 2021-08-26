package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import handler.TodoServletHandler;
import service.ServletService;

/**
 * Servlet implementation class TodoServlet
 */
@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private TodoServletHandler todoServletHandler;
	
	private ServletService servletService;
	
	public void init(ServletConfig config) throws ServletException {
		try {
		this.todoServletHandler = new TodoServletHandler();
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
			this.servletService.invokeAction("GET", request, response, this.todoServletHandler);
		} catch (Exception e) {
			this.servletService.jsonError(e, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.servletService.invokeAction("POST", request, response, this.todoServletHandler);
		} catch (Exception e) {
			this.servletService.jsonError(e, response);
		}
	}
}
