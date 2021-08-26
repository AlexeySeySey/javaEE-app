<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign-in</title>
</head>
<body>
<h2>Sign in</h2>
<br>
<form action="security?action=handleLogin" method="POST">
<label for="email">Email:</label>
<input id="email" type="text" name="email" required>
<label for="password">Password:</label>
<input id="password" type="password" name="password" required>
<input type="submit" value="Send">
</form>
</body>
</html>