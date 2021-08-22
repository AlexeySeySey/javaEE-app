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
<label for="email">Login:</label>
<input id="email" type="text" name="email" required>
<label for="password">Password:</label>
<input id="password" type="password" name="password" required>
<input type="submit" value="Send">
</form>
</body>
</html>