package wap_project;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmailSendingServlet")
public class EmailSendingServlet extends HttpServlet {
	private String host;
	private String port;
	private String user;
	private String pass;

	public void init() {
		ServletContext context = getServletContext();
		host = context.getInitParameter("host");
		port = context.getInitParameter("port");
		user = context.getInitParameter("user");
		pass = context.getInitParameter("pass");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username;
		String password;
		String recipient = request.getParameter("e-mail");
		String subject = "";
		String content = "";

		String resultMessage = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String query = "SELECT username, CONVERT(AES_DECRYPT(password,'key'), CHAR CHARACTER SET utf8mb4) as password FROM users WHERE email = '"
					+ recipient + "'";
			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			if (queryResult.next()) {
				username = queryResult.getString(1);
				password = queryResult.getString(2);
				subject = "Password reminder for the " + username + " account";
				content = "Your password is: " + password + " \nWe suggest changing the current password.";
				EmailUtility.sendEmail(host, port, user, pass, recipient, subject, content);
				resultMessage = "The e-mail with password was sent successfully<button class=\"btn3\" type=\"submit\" name=\"query\" onClick=\"javascript:window.location='login.jsp';\">LOG IN</button>";
			} else {
				resultMessage = "There is no user with the given e-mail address!";
			}
			queryResult.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			resultMessage = "There were an error: " + ex.getMessage();
		} finally {
			request.setAttribute("Message", resultMessage);
			getServletContext().getRequestDispatcher("/result.jsp").forward(request,response);
		}
	}
}