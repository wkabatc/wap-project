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

public class MostCommChart {

	private String name;

	private int sum1;
	private int sum2;
	private int sum3;
	private int sum4;
	private int sum5;
	private int sum6;
	private int sum7;
	private int sum8;
	private String question1;
	private String question2;
	private String question3;
	private String question4;
	private String question5;
	private String question6;
	private String question7;
	private String question8;

	@SuppressWarnings("finally")
	public ArrayList<String> queAndViews(HttpServletRequest request, HttpServletResponse response, String returnType)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String result = "";
		String result1 = "";
		ArrayList<String> arrFromQue = new ArrayList<String>();
		ArrayList<String> arrOfQues = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String ansQtyQue = "SELECT questions.question, count(answers.id_question) as answers FROM questions LEFT JOIN answers ON questions.id_question = answers.id_question GROUP BY questions.id_question ORDER BY questions.id_question LIMIT 8";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(ansQtyQue);
			ResultSetMetaData meta = queryResult.getMetaData();

			while (queryResult.next()) {
				Object value = queryResult.getObject("answers");
				Object value1 = queryResult.getObject("question");
				if (value != null && value1 != null) {
					result = value.toString();
					arrFromQue.add(value.toString());
					result1 = value.toString();
					arrOfQues.add(value1.toString());
				}
			}
			stmt.close();

			String commQtyQue = "SELECT count(comments.id_question) as comments FROM questions LEFT JOIN comments ON questions.id_question = comments.id_question GROUP BY questions.id_question ORDER BY questions.id_question LIMIT 8";

			Statement stmt1 = DBManager.getConnection().createStatement();
			ResultSet query1Result = stmt1.executeQuery(commQtyQue);
			ResultSetMetaData meta1 = query1Result.getMetaData();
			int col1Count = meta1.getColumnCount();
			int i = 0;

			while (query1Result.next()) {
				for (int col = 1; col <= col1Count; col++) {
					Object value1 = query1Result.getObject(1);
					if (value1 != null) {
						result1 = value1.toString();
						Integer sumOfQueAns = Integer.parseInt(arrFromQue.get(i)) + Integer.parseInt(result1);
						String sum = sumOfQueAns.toString();
						arrFromQue.set(i, sum);
					}
					i++;
				}
			}
			stmt1.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (returnType == "questions") {
				return arrOfQues;
			} else {
				return arrFromQue;
			}
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

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question1 = arr.get(0);
	}

	public int getSum2() {
		return sum2;
	}

	public void setSum2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum2 = Integer.parseInt(arr.get(1));
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question2 = arr.get(1);
	}

	public int getSum3() {
		return sum3;
	}

	public void setSum3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum3 = Integer.parseInt(arr.get(2));
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question3 = arr.get(2);
	}

	public int getSum4() {
		return sum4;
	}

	public void setSum4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum4 = Integer.parseInt(arr.get(3));
	}

	public String getQuestion4() {
		return question4;
	}

	public void setQuestion4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question4 = arr.get(3);
	}

	public int getSum5() {
		return sum5;
	}

	public void setSum5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum5 = Integer.parseInt(arr.get(4));
	}

	public String getQuestion5() {
		return question5;
	}

	public void setQuestion5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question5 = arr.get(4);
	}

	public int getSum6() {
		return sum6;
	}

	public void setSum6(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum6 = Integer.parseInt(arr.get(5));
	}

	public String getQuestion6() {
		return question6;
	}

	public void setQuestion6(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question6 = arr.get(5);
	}

	public int getSum7() {
		return sum7;
	}

	public void setSum7(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum7 = Integer.parseInt(arr.get(6));
	}

	public String getQuestion7() {
		return question7;
	}

	public void setQuestion7(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question7 = arr.get(6);
	}

	public int getSum8() {
		return sum8;
	}

	public void setSum8(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "sum");
		this.sum8 = Integer.parseInt(arr.get(7));
	}

	public String getQuestion8() {
		return question8;
	}

	public void setQuestion8(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> arr = queAndViews(request, response, "questions");
		this.question8 = arr.get(7);
	}

}
