<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
request.getSession(false);
if (session == null) { %>
  <a href="query.jsp"> Home</a>
  <a href="login.jsp"> Login</a>
  <% 
} else {
    // Already created.
    %>
  <a href="query.jsp"> Logout</a><% 
}
String name = request.getParameter("name");
%>
<br><br>
<%
out.println("Welcome: admin");
%>
<form action="AdminForumServlet" method="post">
  <input type="submit" value="Manage Q&A">
</form> 
<form action="AdminUsersServlet" method="post">
  <input type="submit" value="Manage Users">
</form> 
<form action="AdminCategoriesServlet" method="post">
  <input type="submit" value="Add categories">
</form> 
</body>
</html>