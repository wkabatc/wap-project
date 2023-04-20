<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remind password</title>
<link rel='stylesheet' type='text/css' href='css/style.css'>
</head>
<body>
	<h1>Remind password</h1>
    <form action="EmailSendingServlet" method="post">
        <span style="margin-left:10px">To remind the password, please enter the e-mail used to create the account</span><br> 
		<input class="input-text" type="text" name="e-mail" value="" required><br>
		<input class="input-button" type="submit" value="Remind">
    </form>
</body>
</html>
