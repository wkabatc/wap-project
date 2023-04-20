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

@WebServlet("/UserPanelServlet")
public class UserPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserPanelServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag = "<link rel='stylesheet' type='text/css' href='css/style.css'>";
		out.println("<html>");
		out.println("<head><title>User Panel</title>" + cssTag + "</head>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT actualuser FROM actualuser order by id_actualuser desc LIMIT 1";
			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			out.println("<table class=\"tab_act\">");

			String user = "";
			String avatarPath = "";
			String userRank = "";
			String inputDisabled = "";

			while (queryResult.next()) {

				out.println("<tr>");

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);

					out.println("<td style=\"border-width:0;\">");
					if (value != null) {
						user = value.toString();
						out.println("<b>" + user+"</b>");
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
			queryResult.close();
			stmt.close();

			if (userRank.equals("Greenhorn")) {
				inputDisabled = "disabled";
			}
			out.println(
					"<h2>Change username</h2><form action=\"ChangeUsernameServlet\" method=\"post\"><label style=\"margin-left:10px\">New username: </label></br><input class=\"input-text\" name=\"new-username\" required></br><button class=\"btn3\" type=\"submit\" value=\""
							+ user + "\" name=\"username\">Change</button></form>");
			out.println(
					"<h2>Change password</h2><form action=\"ChangePassServlet\" method=\"post\"><label style=\"margin-left:10px\">Old password: </label></br><input class=\"input-text\" name=\"old-pass\" type=\"password\" required></br><label style=\"margin-left:10px\">New password: </label></br><input class=\"input-text\" name=\"new-pass\" type=\"password\" required></br><button class=\"btn3\" type=\"submit\" value=\""
							+ user + "\" name=\"username\">Change</button></form>");
			out.println(
					"<h2>Change avatar</h2><form action=\"AvatarUploadServlet\" enctype=\"multipart/form-data\" method=\"post\"><input type=\"file\" name=\"file2\" accept=\"image/png, image/jpeg\" "
							+ inputDisabled + " /><br><input class=\"btn3\" type=\"submit\" value=\"Change\" " + inputDisabled
							+ " /> </form>");
			if (userRank.equals("Greenhorn")) {
				out.println("Only users with a higher rank can change the avatar.</br></br>");
			}
			out.println(
					"<br><br><form action=\"ForumServlet\" method=\"post\"><input type=\"hidden\" name=\"category\" value=\" is not null\"></input><button class=\"btn2\" type=\"submit\">Back to forum</button></form>");

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