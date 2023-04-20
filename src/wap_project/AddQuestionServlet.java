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
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddQuestionServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Question</title>"+cssTag+"</head>");
	    out.println("<body>");
	    
		String idUser = "";
		String username = request.getParameter("username");
		String text = request.getParameter("text");
		String category = request.getParameter("category");
		String result = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query1 = "SELECT id_user from USERS where username='" + username + "'";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(query1);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			while (query1Result.next()) {

				for (int col1 = 1; col1 <= col1Count; col1++) {
					Object value1 = query1Result.getObject(col1);

					if (value1 != null) {
						idUser = value1.toString();
					}
				}
			}
			query1Result.close();
			stmt1.close();

			String stringQue = text;
			String[] words = stringQue.split("\\s+");
			String likesWords = "";
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("[^\\w]", "");
				likesWords += " '" + words[i] + "' OR word LIKE";
			}
			likesWords = likesWords.substring(0, likesWords.length() - 12);
			String checkVocQue = "SELECT count(word) FROM unwantedvocab WHERE word LIKE" + likesWords;
			Statement stmt2 = DBManager.getConnection().createStatement();

			ResultSet queryResult = stmt2.executeQuery(checkVocQue);

			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			while (queryResult.next()) {
				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);
					if (value != null) {
						result = value.toString();
					}
				}
			}
			stmt2.close();

			int vulgarQty = Integer.parseInt(result);
			if (vulgarQty == 0) {
				String dateAdd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				String query = "insert into questions (question, id_category, id_user, views, date_added) values ('" + text + "', '"
						+ category + "', '" + idUser + "', 0, '"+ dateAdd +"')";

				if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
						|| query.substring(0, 6).equalsIgnoreCase("update")
						|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
					Statement stmt3 = DBManager.getConnection().createStatement();
					stmt3.executeUpdate(query);
					out.println("Question added successfully!");
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

				ResultSetMetaData meta2 = queryCheckBloCnt.getMetaData();
				int colCount1 = meta2.getColumnCount();
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
					"<form action=\"ForumServlet\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn3\">Go Back</button></form>");

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