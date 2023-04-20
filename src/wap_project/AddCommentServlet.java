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

@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddCommentServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>Comment</title>"+cssTag+"</head>");
	    out.println("<body>");
	    
		int id_question = 0;
		String idUser = "";
		String username = request.getParameter("username");
		String question = request.getParameter("comment");
		String text = request.getParameter("text");
		String result = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT id_user from USERS where username='" + username + "'";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int col1Count = meta.getColumnCount();
			while (queryResult.next()) {

				String valueCol1 = queryResult.getObject(1).toString();

				for (int col1 = 1; col1 <= col1Count; col1++) {
					Object value1 = queryResult.getObject(col1);

					if (value1 != null) {
						idUser = value1.toString();
					}
				}
			}
			queryResult.close();
			stmt.close();

			String query2 = "SELECT id_question from questions where question='" + question + "'";

			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(query2);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();

			while (query2Result.next()) {

				for (int col2 = 1; col2 <= col2Count; col2++) {
					Object value2 = query2Result.getObject(col2);
					if (value2 != null) {
						id_question = Integer.parseInt(value2.toString());
					}
				}
			}
			query2Result.close();
			stmt2.close();

			String stringQue = text;
			String[] words = stringQue.split("\\s+");
			String likesWords = "";
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("[^\\w]", "");
				likesWords += " '" + words[i] + "' OR word LIKE";
			}
			likesWords = likesWords.substring(0, likesWords.length() - 12);
			String checkVocQue = "SELECT count(word) FROM unwantedvocab WHERE word LIKE" + likesWords;
			Statement stmt3 = DBManager.getConnection().createStatement();

			ResultSet query3Result = stmt3.executeQuery(checkVocQue);

			ResultSetMetaData meta3 = query3Result.getMetaData();
			int colCount = meta3.getColumnCount();
			while (query3Result.next()) {
				for (int col = 1; col <= colCount; col++) {
					Object value = query3Result.getObject(col);
					if (value != null) {
						result = value.toString();
					}
				}
			}
			stmt3.close();

			int vulgarQty = Integer.parseInt(result);
			if (vulgarQty == 0) {

				String query1 = "insert into comments (comment, id_question, id_user) values ('" + text + "',"
						+ id_question + "," + idUser + ")";

				if (query1.length() > 6 && (query1.substring(0, 6).equalsIgnoreCase("insert")
						|| query1.substring(0, 6).equalsIgnoreCase("update")
						|| query1.substring(0, 6).equalsIgnoreCase("delete"))) {
					Statement stmt1 = DBManager.getConnection().createStatement();
					stmt1.executeUpdate(query1);
					out.println("Comment added!");
					stmt1.close();
				}
			} else {
				String blockCntQue = "UPDATE users SET block_counter = block_counter + 1 WHERE id_user= '" + idUser
						+ "'";
				Statement stmt4 = DBManager.getConnection().createStatement();
				stmt4.executeUpdate(blockCntQue);
				stmt4.close();
				out.println("You cannot add comment, because of vulgar words!");

				String checkBlockCnt = "SELECT block_counter FROM users WHERE id_user=" + idUser;
				Statement stmt5 = DBManager.getConnection().createStatement();

				ResultSet queryCheckBloCnt = stmt5.executeQuery(checkBlockCnt);

				ResultSetMetaData meta4 = queryCheckBloCnt.getMetaData();
				int colCount1 = meta4.getColumnCount();
				String blockCntStr = "";
				int blockCnt = 0;
				while (queryCheckBloCnt.next()) {
					for (int col = 1; col <= colCount1; col++) {
						Object value = queryCheckBloCnt.getObject(col);
						if (value != null) {
							blockCntStr = value.toString();
							blockCnt = Integer.parseInt(blockCntStr);
						}
					}
				}
				stmt5.close();
				if (blockCnt >= 10) {
					response.sendRedirect("http://localhost:8080/wap_project/block.jsp");
				}
			}
			out.println(
					"<form action=\"QuestionServlet\" method=\"post\"><button class=\"btn3\" type=\"submit\" name=\"query\" value=\"SELECT question from questions WHERE question=\'"
							+ question + "\'\">BACK</button></form>");

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