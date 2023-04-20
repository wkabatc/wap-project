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

@WebServlet("/AdminShowCommentsServlet")
public class AdminShowCommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminShowCommentsServlet() {

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
		String comment = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = request.getParameter("show");

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
						result = value.toString();
						out.println(value.toString());
					}
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</table></br>");
			queryResult.close();
			stmt.close();

			String query1 = "SELECT comments.comment FROM answers INNER JOIN comments ON comments.id_answer=answers.id_answer WHERE answers.answer=\'"
					+ result + "\'";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			out.println("<table class=\"styled-table\">");

			out.println("<tr>");
			for (int col1 = 1; col1 <= col1Count; col1++) {
				out.println("<th>");
				out.println(meta1.getColumnLabel(col1));
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
						comment = value1.toString();
						out.println(value1.toString());
					}
					out.println("</td>");
				}
				out.println(
						"<td><form action=\"DeleteCommentAServlet\" method=\"post\"><input type=\"hidden\" name=\"ans\" value=\""
								+ result + "\"/><button class=\"btn\" type=\"submit\" name=\"answerdel\" value=\"" + comment
								+ "\">DELETE</button></form></td>");
				out.println("</tr>");
			}
			out.println("</table></br>");

			out.println(
					"<button class=\"btn2\" onclick=\"goBack()\">Go Back</button><script>function goBack() {window.history.back();}</script>");

			query1Result.close();
			stmt1.close();
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