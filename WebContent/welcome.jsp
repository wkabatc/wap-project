<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/style.css'>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		request.getSession(false);
		if (session == null) {
	%>
	<a href="query.jsp"> Home</a>
	<a href="login.jsp"> Login</a>
	<%
		} else {
			// Already created.
	%>
	<a href="query.jsp"> Logout</a>
	<%
		}
		String name = request.getParameter("name");
	%>
	<br>
	<br>
	<%
		out.println("<div style=\"margin-top: -20px\">Welcome: " + name+"</div>");

		out.println(
				"</br><form action=\"UserPanelServlet\" method=\"post\"><input type=\"hidden\"></input><button class=\"btn2\" type=\"submit\" value=\"INSERT INTO actualuser (actualuser) VALUES('"
						+ name + "')\" name=\"username\">User panel</button></form>");
		out.println(
				"<form action=\"ActivPanelServlet\" method=\"post\"><input type=\"hidden\"></input><button class=\"btn2\" type=\"submit\" value=\""
						+ name + "\" name=\"username\">Activity panel</button></form>");
		out.println(
				"<form action=\"RankingServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\"  value=\""
						+ name + "\" name=\"username\">User ranking</button></form>");
		out.println(
				"<form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\" value=\"INSERT INTO actualuser (actualuser) VALUES('"
						+ name + "')\" name=\"username\">Go to forum!</button></form>");
	%>
</body>
</html>