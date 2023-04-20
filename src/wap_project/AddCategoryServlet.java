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

@WebServlet("/AddCategoryServlet")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddCategoryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>Category</title>"+cssTag+"</head>");
	    out.println("<body>");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			int id_category = 0;

			String query2 = "SELECT max(id_category) FROM categories";

			Statement stmt2 = DBManager.getConnection().createStatement();
			ResultSet query2Result = stmt2.executeQuery(query2);
			ResultSetMetaData meta2 = query2Result.getMetaData();
			int col2Count = meta2.getColumnCount();

			while (query2Result.next()) {

				String valueCol2 = query2Result.getObject(1).toString();

				for (int col2 = 1; col2 <= col2Count; col2++) {
					Object value2 = query2Result.getObject(col2);
					if (value2 != null) {
						id_category = Integer.parseInt(value2.toString());
					}
				}
			}
			query2Result.close();
			stmt2.close();

			String category = request.getParameter("category");
			id_category++;
			String query = "INSERT INTO categories VALUES (" + id_category + ",'" + category + "')";

			if (query.length() > 6 && (query.substring(0, 6).equalsIgnoreCase("insert")
					|| query.substring(0, 6).equalsIgnoreCase("update")
					|| query.substring(0, 6).equalsIgnoreCase("delete"))) {
				Statement stmt = DBManager.getConnection().createStatement();
				stmt.executeUpdate(query);
				out.println(
						"Category added successfully!<form action=\"AdminCategoriesServlet\" method=\"post\"><input class=\"btn3\" type=\"submit\" value=\"Back\">");
				stmt.close();
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
