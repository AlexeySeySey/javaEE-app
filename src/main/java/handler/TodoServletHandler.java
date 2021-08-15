package handler;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import repository.TodoRepository;
import validator.*;
import annotation.Action;
import service.ServletService;
import contract.IServletHandleable;

public final class TodoServletHandler implements IServletHandleable {
	
	private TodoRepository todoRepository;
	
	private ServletService servletService;
	
	public TodoServletHandler() throws Exception {
		this.todoRepository = new TodoRepository();
		this.servletService = new ServletService();
	}
	
	@Action
	public void getTodos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("todos", this.todoRepository.findAll());
		request.getRequestDispatcher("jsp/todos.jsp").forward(request, response);
	}
	
	@Action
	public void createTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String todoText = this.servletService
				.getRequestBody(request)
			 	.get("todo_text");
		(new CreateTodoValidator()).validate(todoText);
		this.todoRepository.create(todoText);
	}
	
	@Action
	public void updateTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String todoText = (String) this.servletService
				.getRequestBody(request)
				.get("todo_text");
		(new UpdateTodoValidator()).validate(todoText);
		this.todoRepository.update(request.getParameter("id"), todoText);
	}
	
	@Action
	public void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		(new DropTodoValidator()).validate(request);
		this.todoRepository.delete(request.getParameter("id"));
	}
}
