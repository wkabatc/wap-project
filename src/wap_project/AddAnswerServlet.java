package wap_project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap_project.DBManager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/AddAnswerServlet")
public class AddAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAnswerServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Answer</title>"+cssTag+"</head>");
	    out.println("<body>");
	    
		int id_answer = 0;
		String idUser = "";
		String username = request.getParameter("username");
		String answer = request.getParameter("answer");
		String textA = request.getParameter("textA");
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

			String query1 = "SELECT id_question from questions where question='" + answer + "'";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col2Count = meta1.getColumnCount();

			while (query1Result.next()) {
				for (int col1 = 1; col1 <= col2Count; col1++) {
					Object value1 = query1Result.getObject(col1);
					if (value1 != null) {
						id_answer = Integer.parseInt(value1.toString());
					}
				}
			}
			query1Result.close();
			stmt1.close();

			String stringQue = textA;
			String[] words = stringQue.split("\\s+");
			String likesWords = "";
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("[^\\w]", "");
				likesWords += " '" + words[i] + "' OR word LIKE";
			}
			likesWords = likesWords.substring(0, likesWords.length() - 12);
			String checkVocQue = "SELECT count(word) FROM unwantedvocab WHERE word LIKE" + likesWords;
			Statement stmt2 = DBManager.getConnection().createStatement();

			ResultSet query2Result = stmt2.executeQuery(checkVocQue);

			ResultSetMetaData meta2 = query2Result.getMetaData();
			int colCount = meta2.getColumnCount();
			while (query2Result.next()) {
				for (int col = 1; col <= colCount; col++) {
					Object value = query2Result.getObject(col);
					if (value != null) {
						result = value.toString();
					}
				}
			}
			stmt2.close();

			int vulgarQty = Integer.parseInt(result);
			if (vulgarQty == 0) {
				String query2 = "insert into answers (answer, id_question, id_user, rate) values ('" + textA + "',"
						+ id_answer + "," + idUser + ",0)";

				if (query2.length() > 6 && (query2.substring(0, 6).equalsIgnoreCase("insert")
						|| query2.substring(0, 6).equalsIgnoreCase("update")
						|| query2.substring(0, 6).equalsIgnoreCase("delete"))) {
					Statement stmt3 = DBManager.getConnection().createStatement();
					stmt3.executeUpdate(query2);
					out.println("Answer added!");
					stmt3.close();
				}
			} else {
				String blockCntQue = "UPDATE users SET block_counter = block_counter + 1 WHERE id_user= '" + idUser
						+ "'";
				Statement stmt4 = DBManager.getConnection().createStatement();
				stmt4.executeUpdate(blockCntQue);
				stmt4.close();
				out.println("You cannot add answer, because of vulgar words!");

				String checkBlockCnt = "SELECT block_counter FROM users WHERE id_user=" + idUser;
				Statement stmt5 = DBManager.getConnection().createStatement();

				ResultSet queryCheckBloCnt = stmt5.executeQuery(checkBlockCnt);

				ResultSetMetaData meta3 = queryCheckBloCnt.getMetaData();
				int colCount1 = meta3.getColumnCount();
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
							+ answer + "\'\">BACK</button></form>");
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