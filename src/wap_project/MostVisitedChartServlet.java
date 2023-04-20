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

import wap_project.MostVisitedChart;
 
@WebServlet("/MostVisitedChartServlet")
public class MostVisitedChartServlet extends HttpServlet {
 
 private static final long serialVersionUID = 1L;
 
 public MostVisitedChartServlet() {
  super();
 }
 
 protected void doGet(HttpServletRequest request,
   HttpServletResponse response) throws ServletException, IOException {
 
  List<MostVisitedChart> mostVisQues = getChartsData(request, response);
 
  Gson gson = new Gson();
 
  String jsonString = gson.toJson(mostVisQues);
 
  response.setContentType("application/json");
 
  response.getWriter().write(jsonString);
 
 }
 
 private List<MostVisitedChart> getChartsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  List<MostVisitedChart> mostVisQues = new ArrayList<MostVisitedChart>();
  MostVisitedChart s1 = new MostVisitedChart();
  s1.setName("Most visited chart");
  s1.setViews1(request, response);
  s1.setQuestion1(request, response);
  s1.setViews2(request, response);
  s1.setQuestion2(request, response);
  s1.setViews3(request, response);
  s1.setQuestion3(request, response);
  s1.setViews4(request, response);
  s1.setQuestion4(request, response);
  s1.setViews5(request, response);
  s1.setQuestion5(request, response);
  mostVisQues.add(s1);
 
  return mostVisQues;
 }
}