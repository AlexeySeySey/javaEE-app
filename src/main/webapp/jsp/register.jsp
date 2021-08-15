<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
</head>
<body>
<form action="security?action=handleRegistration" method="POST">
<label for="login">Login:</label>
<input id="login" type="text" name="login">
<label for="password">Password:</label>
<input id="password" type="password" name="password">
<input type="submit" value="Send">
</form>
</body>
</html>