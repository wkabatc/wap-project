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

@WebServlet("/ActivPanelServlet")
public class ActivPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActivPanelServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Activity</title>"+cssTag+"</head>");
	    out.println("<body>");

		String username = request.getParameter("username");
		String idUser = "";
		String result = "";
		String que = "";
		String idQue = "";
		String us = "";
		String avatarPath = "";
		String userRank = "";

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

			stmt.close();

			String query1 = "SELECT actualuser FROM actualuser order by id_actualuser desc LIMIT 1";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();

			out.println("<table class=\"tab_act\">");

			while (query1Result.next()) {

				out.println("<tr>");

				for (int col1 = 1; col1 <= col1Count; col1++) {
					Object value1 = query1Result.getObject(col1);

					out.println("<td style=\"border-width:0;\">");
					if (value1 != null) {
						out.println("<b>" + value1.toString()+"</b>");
						us = value1.toString();

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

			out.println("<form action=\"login.jsp\"><button class=\"btn4\">Log out</button></form>");

			stmt1.close();

			out.println("<h2>My questions</h2>");

			String myQueQue = "SELECT question FROM questions WHERE id_user = " + idUser + "";

			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(myQueQue);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();
			out.println("<table class=\"styled-table\" >");

			out.println("<tr>");
			for (int col2 = 1; col2 <= col2Count; col2++) {
				out.println("<th>");
				out.println("question");
				out.println("</th>");
			}
			out.println("</tr>");

			while (query2Result.next()) {

				out.println("<tr>");

				for (int col2 = 1; col2 <= col2Count; col2++) {
					Object value = query2Result.getObject(col2);
					Object q = query2Result.getObject(1);
					out.println("<td>");
					if (value != null) {
						result = value.toString();
						que = q.toString();
						out.println(value.toString());
					}
					out.println("</td>");

				}

				out.println(
						"<td><form action=\"QuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"query\" value=\"SELECT questions.question, categories.category, users.username FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user WHERE question=\'"
								+ que + "\'\">READ MORE</button></form></td>");
				out.println("</tr>");

			}
			out.println("</table>");
			stmt2.close();

			out.println("<h2>My answers</h2>");

			String myAnsQue = "SELECT answer, id_question FROM answers WHERE id_user = " + idUser + "";

			Statement stmt3 = DBManager.getConnection().createStatement();
			ResultSet query3Result = stmt3.executeQuery(myAnsQue);
			ResultSetMetaData meta3 = query3Result.getMetaData();
			int col3Count = meta3.getColumnCount();
			out.println("<table class=\"styled-table\" >");

			out.println("<tr>");
			for (int col3 = 1; col3 <= col3Count - 1; col3++) {
				out.println("<th>");
				out.println(meta3.getColumnLabel(col3));
				out.println("</th>");
			}
			out.println("</tr>");

			while (query3Result.next()) {

				out.println("<tr>");

				for (int col3 = 1; col3 <= col3Count; col3++) {
					Object value = query3Result.getObject(col3);
					if (col3 == 1) {
						out.println("<td>");
						if (value != null) {
							result = value.toString();
							out.println(value.toString());
						}
						out.println("</td>");
					}
					if (col3 == 2) {
						idQue = value.toString();
					}
				}

				out.println(
						"<td><form action=\"QuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"query\" value=\"SELECT questions.question, categories.category, users.username FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user WHERE id_question=\'"
								+ idQue + "\'\">READ MORE</button></form></td>");
				out.println("</tr>");

			}
			out.println("</table>");
			stmt3.close();

			out.println("<h2>My comments</h2>");

			String myCommQue = "SELECT comment, id_question FROM comments WHERE id_user = " + idUser
					+ " AND id_question IS NOT NULL";

			Statement stmt6 = DBManager.getConnection().createStatement();
			ResultSet query6Result = stmt6.executeQuery(myCommQue);
			ResultSetMetaData meta6 = query6Result.getMetaData();
			int col6Count = meta6.getColumnCount();
			out.println("<table class=\"styled-table\" >");

			out.println("<tr>");
			for (int col6 = 1; col6 <= col3Count - 1; col6++) {
				out.println("<th>");
				out.println(meta6.getColumnLabel(col6));
				out.println("</th>");
			}
			out.println("</tr>");

			while (query6Result.next()) {

				out.println("<tr>");

				for (int col6 = 1; col6 <= col6Count; col6++) {
					Object value = query6Result.getObject(col6);
					if (col6 == 1) {
						out.println("<td>");
						if (value != null) {
							result = value.toString();
							out.println(value.toString());
						}
						out.println("</td>");
					}
					if (col6 == 2) {
						idQue = value.toString();
					}
				}

				out.println(
						"<td><form action=\"QuestionServlet\" method=\"post\"><button class=\"btn\" type=\"submit\" name=\"query\" value=\"SELECT questions.question, categories.category, users.username FROM QUESTIONS inner join categories ON categories.id_category=questions.id_category INNER JOIN users ON users.id_user=questions.id_user WHERE id_question=\'"
								+ idQue + "\'\">READ MORE</button></form></td>");
				out.println("</tr>");

			}
			out.println("</table>");
			stmt3.close();
			out.println(
					"</br><form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\">Go to forum!</button></form>");

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