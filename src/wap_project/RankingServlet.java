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

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RankingServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Ranking</title>"+cssTag+"</head>");
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

			out.println("<table class=\"tab_act\" >");

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

			////////////////////////////

			out.println("<h2>User ranking</h2>");

			String query2 = "DELETE FROM ranking";
			Statement stmt2 = DBManager.getConnection().createStatement();
			stmt2.executeUpdate(query2);
			stmt2.close();

			String query3 = "INSERT INTO ranking (id_user, points) SELECT id_user, count(id_user)*2 AS total_quest FROM questions group by id_user order by total_quest desc;";
			Statement stmt3 = DBManager.getConnection().createStatement();
			stmt3.executeUpdate(query3);
			stmt3.close();

			String query7 = "INSERT INTO ranking (id_user, points) SELECT id_user, count(id_user)*3 AS total_ans FROM answers group by id_user order by total_ans desc;";
			Statement stmt7 = DBManager.getConnection().createStatement();
			stmt7.executeUpdate(query7);
			stmt7.close();

			String query8 = "INSERT INTO ranking (id_user, points) SELECT id_user, sum(rate) AS total_rate FROM answers group by id_user order by total_rate desc;";
			Statement stmt8 = DBManager.getConnection().createStatement();
			stmt8.executeUpdate(query8);
			stmt8.close();

			String query6 = "SELECT users.id_user, users.username, sum(ranking.points) AS points, IF(sum(ranking.points)>30,\"Master\",IF(sum(ranking.points)>20,\"Average\",IF(sum(ranking.points)>10,\"Junior\",\"Novice\"))) AS rank FROM ranking INNER JOIN  users on ranking.id_user = users.id_user group by ranking.id_user order by sum(ranking.points) desc";

			Statement stmt6 = DBManager.getConnection().createStatement();
			ResultSet query6Result = stmt6.executeQuery(query6);
			ResultSetMetaData meta6 = query6Result.getMetaData();
			int col6Count = meta6.getColumnCount();
			out.println("<table class=\"styled-table\" >");

			out.println("<tr>");
			out.println("<th>position</th>");
			for (int col = 2; col <= col6Count; col++) {
				out.println("<th>");
				out.println(meta6.getColumnLabel(col));
				out.println("</th>");
			}
			out.println("</tr>");
			int position = 0;
			while (query6Result.next()) {
				position++;
				out.println("<tr>");
				out.println("<td>");
				out.println(position);
				out.println("</td>");
				
				int userId = 0;

				for (int col = 1; col <= col6Count; col++) {
					if (col == 1) {
						Object value = query6Result.getObject(col);
						if (value != null) {
							userId = Integer.parseInt(value.toString());  
						}
					}
					if (col > 1) {
						Object value = query6Result.getObject(col);
						Object q = query6Result.getObject(1);
						out.println("<td>");
						if (value != null) {
							que = q.toString();
							out.println(value.toString());
						}
						out.println("</td>");
					}
					if (col == col6Count) {
						Object value = query6Result.getObject(col);
						if (value != null) {
							userRank = value.toString();  
						}
						String rankQue = "UPDATE users SET rank = '" + userRank + "' WHERE id_user= " + userId + "";
						Statement stmt9 = DBManager.getConnection().createStatement();
						stmt9.executeUpdate(rankQue);
						stmt9.close();
					}

				}
				out.println("</tr>");

			}
			out.println("</table>");

			out.println(
					"</br></br><form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\">Go to forum!</button></form>");

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