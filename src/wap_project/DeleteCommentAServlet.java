package wap_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCommentAServlet")
public class DeleteCommentAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteCommentAServlet() {
		super();
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

			String answerdel = request.getParameter("answerdel");
			String ans = request.getParameter("ans");
			String query = "DELETE FROM comments WHERE comment='" + answerdel + "'";

			if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
					|| query.substring(0, 6).equalsIgnoreCase("update")
					|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
				Statement stmt = DBManager.getConnection().createStatement();
				stmt.executeUpdate(query);
				out.println(
						"Comment deleted successfully!<form action=\"AdminShowCommentsServlet\" method=\"post\"><button class=\"btn3\" type=\"submit\" value=\"SELECT answer from answers WHERE answer=\'"
								+ ans + "\'\" name=\"show\" >BACK</button>");
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
