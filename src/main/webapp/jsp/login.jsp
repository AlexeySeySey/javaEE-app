<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign-in</title>
</head>
<body>
<form action="security?action=handleLogin" method="POST">
<label for="login">Login:</label>
<input id="login" type="text" name="login">
<label for="password">Password:</label>
<input id="password" type="password" name="password">
<input type="submit" value="Send">
</form>
</body>
</html>