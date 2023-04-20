package wap_project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/TempBlockServlet")
public class TempBlockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TempBlockServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
		out.println("<html>");
		out.println("<head><title>Forum</title>"+cssTag+"</head>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String blockDate = request.getParameter("temp-block");
			String userId = request.getParameter("user-button");

			if (blockDate != "") {
				String query = "UPDATE users SET block_date_to = '" + blockDate + "' WHERE id_user= '" + userId + "'";

				Statement stmt = DBManager.getConnection().createStatement();
				stmt.executeUpdate(query);
				out.println("Temporary block applied successfully!");
				stmt.close();
			} else {
				out.println("The block date has not been selected!");
			}
			out.println("<form action=\"AdminUsersServlet\" method=\"post\"><input class=\"btn3\" type=\"submit\" value=\"Back\">");

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