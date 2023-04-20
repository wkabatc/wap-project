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

@WebServlet("/QuestionServlet")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QuestionServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Question</title><script src=\"https://kit.fontawesome.com/a076d05399.js\" crossorigin=\"anonymous\"></script>"+cssTag+"</head>");
	    out.println("<body>");
	    
		String result = "";
		String result2 = "";
		String avatarPath = "";
		String userRank = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query3 = "SELECT actualuser FROM actualuser order by id_actualuser desc LIMIT 1";
			String user = "";

			Statement stmt3 = DBManager.getConnection().createStatement();
			ResultSet query3Result = stmt3.executeQuery(query3);
			ResultSetMetaData meta3 = query3Result.getMetaData();
			int col3Count = meta3.getColumnCount();
			out.println("<table class=\"tab_act\">");

			// header row:

			while (query3Result.next()) {

				out.println("<tr>");

				String valueCol3 = query3Result.getObject(1).toString();

				for (int col3 = 1; col3 <= col3Count; col3++) {
					Object value3 = query3Result.getObject(col3);

					out.println("<td style=\"border-width:0;\">");
					if (value3 != null) {
						user = value3.toString();
						out.println("<b>" + value3.toString() +"</b>");
					}
				}
			}
			String query5 = "SELECT rank FROM users WHERE username = '" + user + "'";

			Statement stmt5 = DBManager.getConnection().createStatement();
			ResultSet query5Result = stmt5.executeQuery(query5);
			ResultSetMetaData meta5 = query5Result.getMetaData();
			int col5Count = meta5.getColumnCount();

			while (query5Result.next()) {

				for (int col5 = 1; col5 <= col5Count; col5++) {
					Object value5 = query5Result.getObject(col5);

					if (value5 != null) {
						userRank = value5.toString();
						out.println("</br>" + userRank);
					}
					out.println("</td>");
				}
			}
			String query4 = "SELECT avatar_path FROM users WHERE username = '" + user + "'";

			Statement stmt4 = DBManager.getConnection().createStatement();
			ResultSet query4Result = stmt4.executeQuery(query4);
			ResultSetMetaData meta4 = query4Result.getMetaData();
			int col4Count = meta4.getColumnCount();

			while (query4Result.next()) {

				for (int col4 = 1; col4 <= col4Count; col4++) {
					Object value4 = query4Result.getObject(col4);

					if (value4 != null) {
						avatarPath = value4.toString();
						out.println("<td><img height=\"50px\" src=\"" + avatarPath + "\"/></td>");
					}
				}
			}
			out.println("</tr>");
			out.println("</table>");

			out.println("<form action=\"login.jsp\"><button class=\"btn4\">Log out</button></form>");
			query3Result.close();
			stmt3.close();

			String query = request.getParameter("query");

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			out.println("<table class=\"styled-table\" >");

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

			out.println(
					"<h2>Add answer</h2><form action=\"AddAnswerServlet\" method=\"post\"><textarea name=\"textA\" rows=\"10\" cols=\"100\" required>Write answer to question...</textarea><br><input type=\"hidden\" name=\"username\" value=\""
							+ user + "\"><button class=\"btn3\" type=\"submit\" value=\"" + result
							+ "\" name=\"answer\">Add answer</button></form>");

			out.println(
					"<h2>Add comment to question</h2><form action=\"AddCommentServlet\" method=\"post\"><textarea name=\"text\" rows=\"10\" cols=\"30\" required>Write comment to question...</textarea><br><input type=\"hidden\" name=\"username\" value=\""
							+ user + "\"><button class=\"btn3\" type=\"submit\" value=\"" + result
							+ "\" name=\"comment\">Add comment</button></form>");
			
			String query6 = "UPDATE questions SET views = views + 1 WHERE question= '" + result + "'";
			Statement stmt6 = DBManager.getConnection().createStatement();
			stmt6.executeUpdate(query6);
			stmt6.close();

			String query1 = "SELECT comments.comment FROM questions INNER JOIN comments ON comments.id_question=questions.id_question WHERE questions.question=\'"
					+ result + "\'";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			out.println("<table class=\"styled-table\" >");

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
						out.println(value1.toString());

					}
					out.println("</td>");

				}

				out.println("</tr>");

			}
			out.println("</table></br><h2>Browse answers</h2>");
			query1Result.close();
			stmt1.close();

			String query2 = "SELECT answers.answer, answers.rate FROM answers INNER JOIN questions ON questions.id_question=answers.id_question WHERE questions.question=\'"
					+ result + "\' ORDER BY RATE DESC";


			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(query2);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();
			out.println("<table class=\"styled-table\" >");

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
						"<td><form action=\"MinusServlet\" method=\"post\"><input type=\"hidden\" name=\"username\" value=\""
								+ user + "\"></input><input type=\"hidden\" name=\"answer\" value=\"" + result2
								+ "\"></input><button class=\"btn\" type=\"submit\" name=\"minus\" value=\"UPDATE answers SET rate=rate-1 WHERE answer=\'"
								+ result2 + "\'\"><i class=\"fas fa-thumbs-down\"></i></button></form></td>");
				out.println(
						"<td><form action=\"PlusServlet\" method=\"post\"><input type=\"hidden\" name=\"username\" value=\""
								+ user + "\"></input><input type=\"hidden\" name=\"answer\" value=\"" + result2
								+ "\"></input><button class=\"btn\" type=\"submit\" name=\"plus\" value=\"UPDATE answers SET rate=rate+1 WHERE answer=\'"
								+ result2 + "\'\"><i class=\"fas fa-thumbs-up\"></i></button></form></td>");
				out.println(
						"<td><form action=\"ShowCommentsServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"show\" value=\"SELECT answer from answers WHERE answer=\'"
								+ result2 + "\'\">SHOW COMMENTS</button></form></td>");
				out.println(
						"<td><form action=\"AddCommentToAServlet\" method=\"post\"><textarea name=\"comment_answer\" rows=\"3\" cols=\"30\" required>Write comment to answer...</textarea><br><input type=\"hidden\" name=\"username\" value=\""
								+ user + "\"><button class=\"btn3\" style=\"float:right;\" type=\"submit\" value=\"" + result2
								+ "\" name=\"answer\">Add comment</button></form></td>");
				out.println("</tr>");

			}
			out.println("</table>");

			out.println(
					"</br><form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\">BACK TO FORUM</button></form>");

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