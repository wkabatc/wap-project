<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://www.google.com/recaptcha/api.js"></script>
<link rel='stylesheet' type='text/css' href='css/style.css'>
<title>Create new user</title>
</head>
<body>
	<h1>CREATING NEW ACCOUNT</h1>
	<form name="queryForm" action="AddServlet" method="post">
		<span style="margin-left: 10px">e-mail:</span><br> <input
			class="input-text" type="text" name="e-mail" value="" required>
		<br> <span style="margin-left: 10px">username:</span><br> <input
			class="input-text" type="text" name="username" value="" required>
		<br> <span style="margin-left: 10px">password:</span><br> <input
			class="input-text" type="text" name="password" value="" required>
		<br> <br>
		<div class="g-recaptcha"
			data-sitekey="6LcTTfwaAAAAAIbiBisqxRp6REty3I3MOWsTDQZh"></div>
		<br> <input class="input-button" type="submit" value="Sign up">
	</form>
	<input class="input-button" type="submit" value="Cancel"
		onClick="javascript:window.location='query.jsp'">
</body>
</html>