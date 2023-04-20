package wap_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap_project.VerifyRecaptcha;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Login</title>"+cssTag+"</head>");
	    out.println("<body>");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String result = "";
		int blockCheck = 0;

		try {
			if (username.equals("admin")) {
				Statement stmt = DBManager.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select username,password from users where username='" + username
						+ "' and password=AES_ENCRYPT('" + password + "', 'key')");

				if (rs.next()) {

					response.sendRedirect(
							"http://localhost:8080/wap_project/admin.jsp?name=" + rs.getString("username"));
					HttpSession session = request.getSession();
					session.setAttribute("username", username);

				} else {
					out.println("Wrong id or password");
				}
				stmt.close();
			}

			else {

				Statement stmt = DBManager.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select username,password from users where username='" + username
						+ "' and password=AES_ENCRYPT('" + password + "', 'key')");

				Statement stmt1 = DBManager.getConnection().createStatement();
				String query = "SELECT block_date_to FROM users WHERE username='" + username
						+ "' and password=AES_ENCRYPT('" + password + "', 'key')";
				ResultSet queryResult = stmt1.executeQuery(query);

				ResultSetMetaData meta = queryResult.getMetaData();
				int colCount = meta.getColumnCount();
				while (queryResult.next()) {
					for (int col = 1; col <= colCount; col++) {
						Object value = queryResult.getObject(col);
						if (value != null) {
							result = value.toString();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
							Date date1 = format.parse(date);
							Date date2 = format.parse(result);
							int res = date1.compareTo(date2);
							if (result != null) {
								if (res <= 0) {
									blockCheck++;
								}
							}
						}
					}
				}
				if (rs.next()) {
					String checkBlockCnt = "SELECT block_counter FROM users WHERE username='" + username
							+ "' and password=AES_ENCRYPT('" + password + "', 'key')";
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
						out.print("Your account has been permanently blocked!");
					} else if (blockCnt < 10) {
						if (blockCheck == 1) {
							out.print("You have temporary block to: " + result.substring(0, result.lastIndexOf(".")));
							stmt1.close();
						} else {

							String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
							System.out.println(gRecaptchaResponse);
							boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);

							if (verify) {

								response.sendRedirect("http://localhost:8080/wap_project/welcome.jsp?name="
										+ rs.getString("username"));
								HttpSession session = request.getSession();
								session.setAttribute("username", username);

								out.print(username);

								String queryactualuser = "INSERT INTO actualuser (actualuser) VALUES('" + username
										+ "')";

								out.println("query: " + queryactualuser);

								if (queryactualuser.length() > 6
										&& (queryactualuser.substring(0, 6).equalsIgnoreCase("insert")
												|| queryactualuser.substring(0, 6).equalsIgnoreCase("update")
												|| queryactualuser.substring(0, 6).equalsIgnoreCase("delete"))) {
									Statement stmtuser = DBManager.getConnection().createStatement();
									stmtuser.executeUpdate(queryactualuser);

									out.println("insert or update or delete DONE");
									out.println(
											"</br>User logged successfully!</br><input type=\"submit\" value=\"Log out\" onClick=\"javascript:window.location='login.jsp';\">");
									stmtuser.close();
								}
							} else {
								RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
								PrintWriter out1 = response.getWriter();
								if (verify) {
									out1.println("<font color=red>Either user name or password is wrong.</font>");
								} else {
									out1.println("<font color=red>You missed the Captcha.</font>");
								}
								rd.include(request, response);
							}
						}
					}
				} else {
					out.println("Wrong id or password");
				}
				stmt.close();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}