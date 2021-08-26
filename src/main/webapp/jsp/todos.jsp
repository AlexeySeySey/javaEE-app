<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entity.Todo"%>
<%@ page import="java.lang.Math"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Todos</title>
<link rel="icon" href="storage/home--v1.png" sizes="any">
<style>
li {
	margin: 1%;
}

button:hover {
	cursor: pointer;
}

* {
	margin: 0;
	padding: 0;
}

.container {
	height: 100%;
	width: 100%;
}

.left, .right {
	display: inline-block;
	*display: inline;
	zoom: 1;
	vertical-align: top;
}

.left {
	width: 25%;
}

.right {
	width: 25%;
}
</style>
</head>
<body>
	<div class="container">
		<div class="left">
			<div id="create_todo_form">
				<textarea id="todo_text"></textarea>
				<br>
				<button onclick="createTodo()">Save</button>
			</div>
			<div id="update_todo_form" style="display: none">
				<textarea id="todo_text_to_edit"></textarea>
				<br>
				<button id="todo_edit_submit">Update</button>
			</div>
		</div>
		<div class="right">
			<button onclick="logout()">Logout</button>
		</div>
	</div>
	<hr>
	<div>
		<%
		ArrayList<Todo> todos = (ArrayList<Todo>) request.getAttribute("todos");
		%>
		<%
		for (Todo todo : todos) {
		%>
		<div class="container">
			<div class="left" style="border-bottom: 1px solid grey;">
			<p>
				<%=todo.getText()%>
			</p>
			</div>
			<div class="right">
				<button
					onclick="showUpdateForm(`<%=todo.getId()%>`,`<%=todo.getText()%>`)">Edit</button>
				<button onclick="dropTodo('<%=todo.getId()%>')">Delete</button>
			</div>
		</div>
		<br>
		<%
		}
		%>
	</div>
	<script>
function createTodo() {
	let text = document.getElementById("todo_text").value;
	if (!text) return;
	fetch("?action=createTodo", {
		method: "POST",
		body: JSON.stringify({
			todo_text: text
		})
	})
	.then(() => window.location.reload())
	.catch(e => console.warn(e));
}
function dropTodo(id) {
	if (!confirm("You sure?")) return;
	fetch("?action=deleteTodo&id=" + id, {method: "POST"})
	.then(() => window.location.reload())
	.catch(e => console.warn(e));
}
function showUpdateForm(id, text) {
	document.getElementById("create_todo_form").style.display = "none";
	document.getElementById("update_todo_form").style.display = "block";
	document.getElementById("todo_text_to_edit").value = text.replaceAll("<br>", "\n");
	document.getElementById("todo_edit_submit").onclick = () => {
		if (!document.getElementById("todo_text_to_edit").value) return; 
		fetch("?action=updateTodo&id=" + id, {
			method: "POST",
			body: JSON.stringify({
				todo_text: document.getElementById("todo_text_to_edit").value
			})
		})
		.then(() => window.location.reload())
		.catch(e => console.warn(e));
	}
}
function logout() {
	if (!confirm("You sure?")) return;
	window.location.href = "/EE/security?action=handleLogout";
}
</script>
</body>
</html>