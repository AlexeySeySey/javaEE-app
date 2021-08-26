package handler;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import repository.TodoRepository;
import validator.*;
import annotation.*;
import service.ServletService;
import service.SecurityService;
import contract.IServletHandleable;
import contract.ITodoRepository;
import proxy.TodoRepositoryProxy;
import entity.User;
import exception.NothingFoundException;
import constant.Error;

public final class TodoServletHandler implements IServletHandleable {
	
	private ITodoRepository todoRepository;
	
	private ServletService servletService;
	
	private SecurityService securityService;
	
	public TodoServletHandler() throws Exception {
		this.todoRepository = new TodoRepositoryProxy();
		this.servletService = new ServletService();
		this.securityService = new SecurityService();
	}
	
	@Authenticated
	@Action(verb="GET")
	public void getTodos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User currentUser = null;
		try {
		  currentUser = this.securityService.getCurrentUser(request);
		} catch (NothingFoundException e) {
			throw new Exception(Error.FORBIDDEN.get());
		}
		
		var todos = this.todoRepository.findAllFor(currentUser);
		
		request.setAttribute("todos", todos);
		
		request.getRequestDispatcher("jsp/todos.jsp").forward(request, response);
	}
	
	@Authenticated
	@Action(verb="POST")
	public void createTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String todoText = this.servletService
				.getRequestBody(request)
			 	.get("todo_text")
			 	.replace("\\n", "<br>");
		
		new CreateTodoValidator().validate(todoText);
		
		long creatorId = this.securityService
				.getCurrentUser(request)
				.getId();
		
		this.todoRepository.create(creatorId, todoText);
	}
	
	@Authenticated
	@Action(verb="POST")
	public void updateTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String todoText = (String) this.servletService
				.getRequestBody(request)
				.get("todo_text");
		
		new UpdateTodoValidator().validate(todoText);
		
		this.todoRepository.update(Long.parseLong(request.getParameter("id")), todoText);
	}
	
	@Authenticated
	@Action(verb="POST")
	public void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		new DropTodoValidator().validate(request);
		
		this.todoRepository.deleteById(Long.parseLong(request.getParameter("id")));
	}
}
