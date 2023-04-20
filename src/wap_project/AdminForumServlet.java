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

@WebServlet("/AdminForumServlet")
public class AdminForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminForumServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Login</title>"+cssTag+"</head>");
	    out.println("<body>");

		String result = "";
		String que = "";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT questions.question, categories.category, users.username FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user ORDER BY id_question";

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
					Object q = queryResult.getObject(1);
					out.println("<td>");
					if (value != null) {
						result = value.toString();
						que = q.toString();
						out.println(value.toString());
					}
					out.println("</td>");
				}

				out.println(
						"<td><form action=\"AdminQuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"query\" value=\"SELECT questions.question, categories.category, users.username FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user WHERE question=\'"
								+ que + "\'\">READ MORE</button></form></td>");
				out.println(
						"<td><form action=\"DeleteQuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"deleteque\" value=\"DELETE FROM QUESTIONS WHERE question=\'"
								+ que + "\'\">DELETE</button></form></td>");

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