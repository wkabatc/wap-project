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

@WebServlet("/DeleteQuestionServlet")
public class DeleteQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteQuestionServlet() {

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

			String query = request.getParameter("deleteque");

			if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
					|| query.substring(0, 6).equalsIgnoreCase("update")
					|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
				Statement stmt = DBManager.getConnection().createStatement();
				stmt.executeUpdate(query);
				out.println(
						"Question deleted successfully!<form action=\"AdminForumServlet\" method=\"post\"><input class=\"btn3\" type=\"submit\" value=\"Back\">");
				stmt.close();
			}

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