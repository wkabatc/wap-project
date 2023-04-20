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

@WebServlet("/AdminUsersServlet")
public class AdminUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminUsersServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Forum</title>"+cssTag+"</head>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT * FROM USERS WHERE id_role=2 AND NOT username=\"deleted\"";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			out.println("<table class=\"styled-table\">");

			out.println("<tr>");
			for (int col = 1; col <= colCount; col++) {
				out.println("<th>");
				out.println(meta.getColumnLabel(col));
				out.println("</th>");
			}
			out.println("</tr>");

			while (queryResult.next()) {

				out.println("<tr>");

				String valueCol1 = queryResult.getObject(1).toString();

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);

					out.println("<td>");
					if (value != null) {
						out.println(value.toString());

					}
					out.println("</td>");

				}

				out.println(
						"<td><form action=\"TempBlockServlet\" method=\"post\"><input class=\"input-text\" name=\"temp-block\" type=\"datetime-local\"><button class=\"btn\" type=\"submit\" name=\"user-button\" value="
								+ valueCol1
								+ " >TEMPORARY BLOCK</button></form></td>");

				out.println(
						"<td><form action=\"DeleteUserServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"delete\" value=\"UPDATE USERS set password=\'\', username=\'deleted\' WHERE id_user="
								+ valueCol1 + "\">DELETE USER</button></form></td>");

				out.println("</tr>");

			}
			out.println("</table>");
			out.println("</br><form action=\"admin.jsp\" method=\"post\"><button class=\"btn2\">Back</button></form>");

			queryResult.close();
			stmt.close();

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