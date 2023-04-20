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

@WebServlet("/ChangeUsernameServlet")
public class ChangeUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangeUsernameServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Username</title>"+cssTag+"</head>");
	    out.println("<body>");

		String idUser = "";
		String username = request.getParameter("username");
		String newUsername = request.getParameter("new-username");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT id_user from USERS where username='" + username + "'";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			while (queryResult.next()) {

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);

					if (value != null) {
						idUser = value.toString();
					}
				}
			}
			queryResult.close();
			stmt.close();

			String newPassQue = "UPDATE users SET username = '" + newUsername + "' WHERE id_user = '" + idUser + "'";
			Statement stmt1 = DBManager.getConnection().createStatement();
			stmt1.executeUpdate(newPassQue);
			stmt1.close();
			String newActUserQue = "INSERT INTO actualuser (actualuser) VALUES('" + newUsername + "')";
			Statement stmt2 = DBManager.getConnection().createStatement();
			stmt2.executeUpdate(newActUserQue);
			stmt2.close();
			out.println("Username changed successfully!");
			out.println(
					"<form action=\"UserPanelServlet\" method=\"post\"><button class=\"btn3\" type=\"submit\">BACK</button></form>");

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