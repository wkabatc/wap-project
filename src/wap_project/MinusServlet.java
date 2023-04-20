package wap_project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/MinusServlet")
public class MinusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MinusServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>Activity</title>"+cssTag+"</head>");
	    out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String ifrated = "";
			String query = request.getParameter("minus");
			String username = request.getParameter("username");
			String answer = request.getParameter("answer");

			String query3 = "SELECT * from rateanswer WHERE answer=\"" + answer + "\" AND username=\"" + username
					+ "\" AND minus=1";

			Statement stmt3 = DBManager.getConnection().createStatement();
			ResultSet query3Result = stmt3.executeQuery(query3);
			ResultSetMetaData meta3 = query3Result.getMetaData();
			int col3Count = meta3.getColumnCount();

			while (query3Result.next()) {

				String valueCol3 = query3Result.getObject(1).toString();

				for (int col3 = 1; col3 <= col3Count; col3++) {
					Object value3 = query3Result.getObject(col3);
					if (value3 != null) {
						ifrated = value3.toString();
					}
				}
			}
			out.println("</table>");
			query3Result.close();
			stmt3.close();

			if (ifrated != "") {
				out.println("You have already rated this answer!");
			} else {

				String query1 = "INSERT INTO rateanswer(username,answer,plus,minus) values('" + username + "','"
						+ answer + "',0,1)";

				if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
						|| query.substring(0, 6).equalsIgnoreCase("update")
						|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
					Statement stmt = DBManager.getConnection().createStatement();
					stmt.executeUpdate(query);
					out.println("Comment rated successfully!</br>");
					stmt.close();

					Statement stmt1 = DBManager.getConnection().createStatement();
					stmt1.executeUpdate(query1);
					stmt1.close();
				}
			}

			out.println(
					"<form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><input class=\"input-button\" type=\"submit\" value=\"Back\">");

		} catch (ClassNotFoundException e) {
			out.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}