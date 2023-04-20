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

public class MostPopCatChart {

	private String name;

	private int sum1;
	private int sum2;
	private int sum3;
	private int sum4;
	private int sum5;
	private String cat1;
	private String cat2;
	private String cat3;
	private String cat4;
	private String cat5;

	@SuppressWarnings("finally")
	public ArrayList<String> queAndViews(HttpServletRequest request, HttpServletResponse response, String returnType)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String result = "";
		
		ArrayList<String> arrFromQue = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String popCatQue = "SELECT categories.category, count(questions.id_category) as sum FROM categories INNER JOIN questions ON categories.id_category = questions.id_category GROUP BY categories.id_category";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(popCatQue);
			ResultSetMetaData meta = queryResult.getMetaData();

			while (queryResult.next()) {
					Object value = queryResult.getObject(returnType);
					if (value != null) {
						result = value.toString();
						arrFromQue.add(result);
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

	public int getSum1() {
		return sum1;
	}

	public void setSum1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum1 = Integer.parseInt(arr.get(0));
	}

	public String getCat1() {
		return cat1;
	}

	public void setCat1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "category");
		this.cat1 = arr.get(0);
	}

	public int getSum2() {
		return sum2;
	}

	public void setSum2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum2 = Integer.parseInt(arr.get(1));
	}

	public String getCat2() {
		return cat2;
	}

	public void setCat2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "category");
		this.cat2 = arr.get(1);
	}
	
	public int getSum3() {
		return sum3;
	}

	public void setSum3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum3 = Integer.parseInt(arr.get(2));
	}

	public String getCat3() {
		return cat3;
	}

	public void setCat3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "category");
		this.cat3 = arr.get(2);
	}
	
	public int getSum4() {
		return sum4;
	}

	public void setSum4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum4 = Integer.parseInt(arr.get(3));
	}

	public String getCat4() {
		return cat4;
	}

	public void setCat4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "category");
		this.cat4 = arr.get(3);
	}
	
	public int getSum5() {
		return sum5;
	}

	public void setSum5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum5 = Integer.parseInt(arr.get(4));
	}

	public String getCat5() {
		return cat5;
	}

	public void setCat5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "category");
		this.cat5 = arr.get(4);
	}

}