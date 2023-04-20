package wap_project;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.google.gson.Gson;

import wap_project.MostCommChart;
 
@WebServlet("/MostCommChartServlet")
public class MostCommChartServlet extends HttpServlet {
 
 private static final long serialVersionUID = 1L;
 
 public MostCommChartServlet() {
  super();
 }
 
 protected void doGet(HttpServletRequest request,
   HttpServletResponse response) throws ServletException, IOException {
 
  List<MostCommChart> mostCommQues = getChartsData(request, response);
 
  Gson gson = new Gson();
 
  String jsonString = gson.toJson(mostCommQues);
 
  response.setContentType("application/json");
 
  response.getWriter().write(jsonString);
 
 }
 
 private List<MostCommChart> getChartsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  List<MostCommChart> mostCommQues = new ArrayList<MostCommChart>();
  MostCommChart s1 = new MostCommChart();
  s1.setName("Most commented chart");
  s1.setSum1(request, response);
  s1.setQuestion1(request, response);
  s1.setSum2(request, response);
  s1.setQuestion2(request, response);
  s1.setSum3(request, response);
  s1.setQuestion3(request, response);
  s1.setSum4(request, response);
  s1.setQuestion4(request, response);
  s1.setSum5(request, response);
  s1.setQuestion5(request, response);
  s1.setSum6(request, response);
  s1.setQuestion6(request, response);
  s1.setSum7(request, response);
  s1.setQuestion7(request, response);
  s1.setSum8(request, response);
  s1.setQuestion8(request, response);
  mostCommQues.add(s1);
 
  return mostCommQues;
 }
}