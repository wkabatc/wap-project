package wap_project;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MostVisitedChart {

	private String name;

	private int views1;
	private int views2;
	private int views3;
	private int views4;
	private int views5;
	private String question1;
	private String question2;
	private String question3;	
	private String question4;
	private String question5;

	@SuppressWarnings("finally")
	public ArrayList<String> queAndViews(HttpServletRequest request, HttpServletResponse response, String returnType)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String result = "";
		ArrayList<String> arrFromQue = new ArrayList<String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String mostVisQue = "SELECT question, views from questions ORDER BY views DESC LIMIT 5";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(mostVisQue);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();

			while (queryResult.next()) {
				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(returnType);
					if (value != null) {
						result = value.toString();
						arrFromQue.add(result);
					}
				}
			}
			stmt.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return arrFromQue;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getViews1() {
		return views1;
	}

	public void setViews1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "views");
		this.views1 = Integer.parseInt(arr.get(0));
	}
	
	public int getViews2() {
		return views2;
	}

	public void setViews2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "views");
		this.views2 = Integer.parseInt(arr.get(2));
	}
	
	public int getViews3() {
		return views3;
	}

	public void setViews3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "views");
		this.views3 = Integer.parseInt(arr.get(4));
	}
	
	public int getViews4() {
		return views4;
	}

	public void setViews4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "views");
		this.views4 = Integer.parseInt(arr.get(6));
	}
	
	public int getViews5() {
		return views5;
	}

	public void setViews5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "views");
		this.views5 = Integer.parseInt(arr.get(8));
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "question");
		this.question1 = arr.get(1);
	}
	
	public String getQuestion2() {
		return question2;
	}
	
	public void setQuestion2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "question");
		this.question2 = arr.get(3);
	}
	
	public String getQuestion3() {
		return question3;
	}
	
	public void setQuestion3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "question");
		this.question3 = arr.get(5);
	}
	
	public String getQuestion4() {
		return question4;
	}
	
	public void setQuestion4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "question");
		this.question4 = arr.get(7);
	}
	
	public String getQuestion5() {
		return question5;
	}
	
	public void setQuestion5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "question");
		this.question5 = arr.get(9);
	}

}
