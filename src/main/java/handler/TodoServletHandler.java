package handler;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import repository.TodoRepository;
import validator.*;
import annotation.*;
import service.ServletService;
import service.SecurityService;
import contract.IServletHandleable;
import entity.User;

public final class TodoServletHandler implements IServletHandleable {
	
	private TodoRepository todoRepository;
	
	private ServletService servletService;
	
	private SecurityService securityService;
	
	public TodoServletHandler() throws Exception {
		this.todoRepository = new TodoRepository();
		this.servletService = new ServletService();
		this.securityService = new SecurityService();
	}
	
	@Action
	@Authenticated
	public void getTodos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		var currentUser = this.securityService.getCurrentUser(request);
		
		var todos = this.todoRepository.findAllFor(currentUser);
		
		request.setAttribute("todos", todos);
		
		request.getRequestDispatcher("jsp/todos.jsp").forward(request, response);
	}
	
	@Action
	@Authenticated
	public void createTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String todoText = this.servletService
				.getRequestBody(request)
			 	.get("todo_text");
		
		new CreateTodoValidator().validate(todoText);
		
		User currentUser = this.securityService.getCurrentUser(request);
		
		this.todoRepository.create(currentUser.getId(), todoText);
	}
	
	@Action
	@Authenticated
	public void updateTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String todoText = (String) this.servletService
				.getRequestBody(request)
				.get("todo_text");
		
		new UpdateTodoValidator().validate(todoText);
		
		this.todoRepository.update(request.getParameter("id"), todoText);
	}
	
	@Action
	@Authenticated
	public void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		new DropTodoValidator().validate(request);
		
		this.todoRepository.delete(request.getParameter("id"));
	}
}
