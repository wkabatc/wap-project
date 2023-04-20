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

import wap_project.MostPopCatChart;
 
@WebServlet("/MostPopCatChartServlet")
public class MostPopCatChartServlet extends HttpServlet {
 
 private static final long serialVersionUID = 1L;
 
 public MostPopCatChartServlet() {
  super();
 }
 
 protected void doGet(HttpServletRequest request,
   HttpServletResponse response) throws ServletException, IOException {
 
  List<MostPopCatChart> mostPopCat = getChartsData(request, response);
 
  Gson gson = new Gson();
 
  String jsonString = gson.toJson(mostPopCat);
 
  response.setContentType("application/json");
 
  response.getWriter().write(jsonString);
 
 }
 
 private List<MostPopCatChart> getChartsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
  List<MostPopCatChart> mostPopCat = new ArrayList<MostPopCatChart>();
  MostPopCatChart s1 = new MostPopCatChart();
  s1.setName("Most commented chart");
  s1.setSum1(request, response);
  s1.setCat1(request, response);
  s1.setSum2(request, response);
  s1.setCat2(request, response);
  s1.setSum3(request, response);
  s1.setCat3(request, response);
  s1.setSum4(request, response);
  s1.setCat4(request, response);
  s1.setSum5(request, response);
  s1.setCat5(request, response);
  mostPopCat.add(s1);
 
  return mostPopCat;
 }
}