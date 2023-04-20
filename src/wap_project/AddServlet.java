package wap_project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Statement;

import wap_project.VerifyRecaptcha;

@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String cssTag = "<link rel='stylesheet' type='text/css' href='css/style.css'>";
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>New User</title>" + cssTag + "</head>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String email = request.getParameter("e-mail");
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
			System.out.println(gRecaptchaResponse);
			boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
			if (verify) {

				String query = "INSERT INTO USERS (password,username,id_role,block_counter,rank,avatar_path,email) VALUES(AES_ENCRYPT('"
						+ password + "', 'key'),'" + username + "',2,0,'Greenhorn','./avatars/default.png', '" + email
						+ "')";

				if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
						|| query.substring(0, 6).equalsIgnoreCase("update")
						|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
					Statement stmt = DBManager.getConnection().createStatement();
					stmt.executeUpdate(query);
					out.println(
							"User created successfully! Now you can log in!</br><input class=\"btn3\" type=\"submit\" value=\"Log in\" onClick=\"javascript:window.location='login.jsp';\">");
					stmt.close();
				}
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/add.jsp");
				PrintWriter out1 = response.getWriter();
				if (verify) {
					out1.println("<font color=red>Either user name or password is wrong.</font>");
				} else {
					out1.println("<font color=red>You missed the Captcha.</font>");
				}
				rd.include(request, response);
			}

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