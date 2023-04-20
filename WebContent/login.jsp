<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/style.css'>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://www.google.com/recaptcha/api.js"></script>
<title>Login page</title>
</head>
<body>
	<h1>LOGIN PAGE</h1>
	<form action="LoginServlet" method="post">
		<span style="margin-left:10px">username:</span><br> 
		<input class="input-text" type="text" name="username" value="" required><br>
		<span style="margin-left: 10px">password:</span><br>
		<input class="input-text" name="password" type="password" value="" required><br>
		<a href="remind.jsp" style="float:left !important; margin-left: 10px;">I forgot my password...</a><br><br><br>
		<div class="g-recaptcha" style="margin-left: 10px" data-sitekey="6LcTTfwaAAAAAIbiBisqxRp6REty3I3MOWsTDQZh"></div>
		<br>
		<input class="input-button" type="submit" value="Log in">
	</form>
	<input class="input-button" type="submit" value="Cancel"
		onClick="javascript:window.location='query.jsp'">
</body>
</html>