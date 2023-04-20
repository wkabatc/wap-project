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

@WebServlet("/ForumServlet")
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForumServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
		out.println("<html>");
		out.println("<head><title>Forum</title>"+cssTag+"</head>");
		out.println("<body>");

		String que = "";
		String us = "";
		String avatarPath = "";
		String userRank = "";
		try {

			Class.forName("com.mysql.jdbc.Driver");

			String query2 = "SELECT actualuser FROM actualuser order by id_actualuser desc LIMIT 1";

			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(query2);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();
			out.println("<table class=\"tab_act\">");

			while (query2Result.next()) {

				out.println("<tr>");

				for (int col2 = 1; col2 <= col2Count; col2++) {
					Object value2 = query2Result.getObject(col2);

					out.println("<td style=\"border-width:0;\">");
					if (value2 != null) {
						us = value2.toString();
						out.println("<b>" + value2.toString()+"</b>");
					}
				}
			}
			String query5 = "SELECT rank FROM users WHERE username = '" + us + "'";

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
			String query4 = "SELECT avatar_path FROM users WHERE username = '" + us + "'";

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
			out.println(
					"<form action=\"UserPanelServlet\" method=\"post\"><input type=\"hidden\"></input><button class=\"btn2\" type=\"submit\" value=\""
							+ us + "\" name=\"username\">User panel</button></form>");
			out.println(
					"<form action=\"ActivPanelServlet\" method=\"post\"><input type=\"hidden\"></input><button class=\"btn2\" type=\"submit\" value=\""
							+ us + "\" name=\"username\">Activity panel</button></form>");
			out.println(
					"<form action=\"RankingServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\"  value=\""
							+ us + "\" name=\"username\">User ranking</button></form>");
			out.println("<form action=\"login.jsp\"><button class=\"btn4\" >Log out</button></form>");
			query2Result.close();
			stmt2.close();

			out.println(
					"<h2>Add question</h2><form action=\"AddQuestionServlet\" method=\"post\"><textarea name=\"text\" rows=\"10\" cols=\"50\" required>You can ask a question...</textarea>");

			String query1 = "SELECT category FROM CATEGORIES";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			out.println("</br><span style=\"margin-left:10px\">Category:</span> <select name=\"category\">");
			int id_option = 0;

			while (query1Result.next()) {

				for (int col1 = 1; col1 <= col1Count; col1++) {
					id_option++;
					Object value1 = query1Result.getObject(col1);

					out.println("<option value=\"" + id_option + "\">");
					if (value1 != null) {
						out.println(value1.toString());

					}
					out.println("</option>");

				}

			}
			out.println("</select><button class=\"btn3\" type=\"submit\" value=\"" + us
					+ "\" name=\"username\">Add question</button></form>");
			query1Result.close();
			stmt1.close();

			out.println("<br><h2>Browse forum</h2><form action=\"ForumServlet\" method=\"post\">");

			Statement stmt3 = DBManager.getConnection().createStatement();
			ResultSet query3Result = stmt3.executeQuery(query1);
			ResultSetMetaData meta3 = query3Result.getMetaData();
			out.println("<div class=\"form-title-row\">");
			out.println("<label>");
			out.println("<input type=\"checkbox\" style=\"margin-left:10px\" name=\"category\" value=\"is not null\" checked>ALL");
			int id_option3 = 0;

			while (query3Result.next()) {

				for (int col3 = 1; col3 <= col1Count; col3++) {
					id_option3++;
					Object value3 = query3Result.getObject(col3);
					out.println("<input type=\"checkbox\" name=\"category\" value=\" = " + id_option3 + "\">");
					if (value3 != null) {
						out.println(value3.toString());
					}
				}
			}
			out.println("</label>");
			out.println("</div>");
			out.println("<br>");
			out.println(
					"<script> document.getElementById('date').value = new Date().toISOString().substring(0, 10); </script>");
			out.println("<label style=\"margin-left:10px\">oldest date: </label><input id=\"date\" class=\"input-text\" type=\"date\" name=\"date_start\" >");
			out.println("<label style=\"margin-left:10px\">latest date: </label><input id=\"date\" class=\"input-text\" type=\"date\" name=\"date_end\"  >");
			out.println(
					"<br><br><span style=\"margin-left:10px\">Minimum quantity of answers:</span> <input type=\"range\" value=\"0\" min=\"0\" max=\"100\" name=\"ans-qty\" oninput=\"this.nextElementSibling.value = this.value\"><output>0</output>");
			out.println("<br><br><button class=\"btn3\" type=\"submit\" value=\"" + us
					+ "\" name=\"username\">Select</button></form></br>");
			query1Result.close();
			stmt1.close();

			String categories[] = request.getParameterValues("category");
			String where = "";
			for (int i = 0; i < categories.length; i++) {
				where = where + categories[i] + " OR categories.id_category";
			}

			where = where.substring(0, where.length() - 25);

			String startDate = request.getParameter("date_start");
			String endDate = request.getParameter("date_end");

			if (startDate != null && endDate != null && startDate != "" && endDate != "") {
				where = where + "AND questions.date_added BETWEEN '" + startDate + "' AND '" + endDate + "'";
			}

			String minAnsQty = request.getParameter("ans-qty");
			String havingCond = " ";

			if (minAnsQty != null) {
				havingCond = " HAVING count(answers.answer) >= " + minAnsQty + " ";
			}

			String query = "SELECT questions.question, categories.category, users.username, count(answers.answer) as answers, questions.views, questions.date_added as 'add date' FROM questions INNER JOIN users ON questions.id_user = users.id_user INNER JOIN categories ON categories.id_category=questions.id_category left JOIN answers ON questions.id_question = answers.id_question WHERE categories.id_category "
					+ where + " GROUP BY questions.id_question" + havingCond + "ORDER BY questions.id_question";

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

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);
					Object q = queryResult.getObject(1);
					out.println("<td>");
					if (value != null) {
						que = q.toString();
						out.println(value.toString());
					}
					out.println("</td>");

				}

				out.println(
						"<td><form action=\"QuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"query\" value=\"SELECT questions.question, categories.category, users.username, questions.views + 1 as views FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user WHERE question=\'"
								+ que + "\'\">READ MORE</button></form></td>");
				out.println("</tr>");

			}
			out.println("</table>");
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