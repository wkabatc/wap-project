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

@WebServlet("/AdminQuestionServlet")
public class AdminQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminQuestionServlet() {

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
		String result2 = "";
		String comque = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = request.getParameter("query");

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
						result = q.toString();
						out.println(value.toString());
					}
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");

			queryResult.close();
			stmt.close();

			String query1 = "SELECT comments.comment FROM questions INNER JOIN comments ON comments.id_question=questions.id_question WHERE questions.question=\'"
					+ result + "\'";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			out.println("</br><table class=\"styled-table\">");

			out.println("<tr>");
			for (int col1 = 1; col1 <= col1Count; col1++) {
				out.println("<th>");
				out.println("comments");
				out.println("</th>");
			}
			out.println("</tr>");

			while (query1Result.next()) {

				out.println("<tr>");

				String valueCol1 = query1Result.getObject(1).toString();

				for (int col1 = 1; col1 <= col1Count; col1++) {
					Object value1 = query1Result.getObject(col1);
					out.println("<td>");
					if (value1 != null) {
						comque = value1.toString();
						out.println(value1.toString());
					}
					out.println("</td>");
				}
				out.println(
						"<td><form action=\"DeleteCommentServlet\" method=\"post\"><input type=\"hidden\" name=\"que\" value=\""
								+ result + "\"/><button class=\"btn\" type=\"submit\" name=\"commentdel\" value=\"" + comque
								+ "\">DELETE</button></form></td>");
				out.println("</tr>");
			}
			out.println("</table>");
			query1Result.close();
			stmt1.close();

			String query2 = "SELECT answers.answer, answers.rate FROM answers INNER JOIN questions ON questions.id_question=answers.id_question WHERE questions.question=\'"
					+ result + "\' ORDER BY RATE DESC";

			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(query2);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();
			out.println("</br><table class=\"styled-table\">");

			out.println("<tr>");
			for (int col2 = 1; col2 <= col2Count; col2++) {
				out.println("<th>");
				out.println(meta2.getColumnLabel(col2));
				out.println("</th>");
			}
			out.println("</tr>");

			while (query2Result.next()) {

				out.println("<tr>");

				String valueCol2 = query2Result.getObject(1).toString();

				Object valueres = query2Result.getObject(1);
				result2 = valueres.toString();

				for (int col2 = 1; col2 <= col2Count; col2++) {
					Object value2 = query2Result.getObject(col2);
					out.println("<td>");
					if (value2 != null) {
						out.println(value2.toString());
					}
					out.println("</td>");
				}
				out.println(
						"<td><form action=\"AdminShowCommentsServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"show\" value=\"SELECT answer from answers WHERE answer=\'"
								+ result2 + "\'\">SHOW COMMENTS</button></form></td>");
				out.println(
						"<td><form action=\"DeleteAnswerServlet\" method=\"post\"><input type=\"hidden\" name=\"question\" value=\""
								+ result + "\"/><button class=\"btn\" type=\"submit\" name=\"answerdel\" value=\"" + result2
								+ "\">DELETE</button></form></td>");
				out.println("</tr>");
			}
			out.println("</table></br>");

			out.println("<form action=\"AdminForumServlet\" method=\"post\"><button class=\"btn2\">BACK TO FORUM</button></form>");

			query2Result.close();
			stmt2.close();
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