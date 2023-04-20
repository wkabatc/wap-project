package wap_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminCategoriesServlet")
public class AdminCategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminCategoriesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Categories</title>"+cssTag+"</head>");
	    out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String query = "SELECT category FROM categories";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			out.println("<table class=\"styled-table\">");

			out.println("<tr>");
			for (int col = 1; col <= colCount; col++) {
				out.println("<th>");
				out.println(meta.getColumnLabel(col));
				out.println("</th>");
			}
			out.println("</tr>");

			while (queryResult.next()) {

				out.println("<tr>");

				String valueCol1 = queryResult.getObject(1).toString();

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);
					out.println("<td>");
					if (value != null) {
						out.println(value.toString());
					}
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");

			out.println(
					"<br><form action=\"AddCategoryServlet\" method=\"post\">New category: <input class=\"input-text\" type=\"text\" name=\"category\" required></input><button class=\"btn3\" type=\"submit\" name=\"addcategory\">ADD</button></form></td>");

			out.println("<form action=\"admin.jsp\" method=\"post\"><br><button class=\"btn2\">Back</button></form>");

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
