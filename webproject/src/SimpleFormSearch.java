import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + "<html>\r\n" + 
      		"<head><style>\r\n" + 
      		"header {\r\n" + 
      		"    background-color:black;\r\n" + 
      		"    color: white;\r\n" + 
      		"    width: 100%;\r\n" + 
      		"    height: 100px;\r\n" + 
      		"    padding: 5px;\r\n" + 
      		"}\r\n" + 
      		"nav {\r\n" + 
      		"    line-height:30px;\r\n" + 
      		"    background-color:#eeeeee;\r\n" + 
      		"    opacity: 60%;\r\n" + 
      		"    height:300px;\r\n" + 
      		"    width: 10%;\r\n" + 
      		"    float:left;\r\n" + 
      		"    padding:5px;	      \r\n" + 
      		"}\r\n" + 
      		"section {\r\n" + 
      		"    width: 85%;\r\n" + 
      		"    float:left;\r\n" + 
      		"    padding:10px;\r\n" + 
      		"    display: inline;\r\n" + 
      		"	background-color: white;\r\n" + 
      		"	opacity: 80%;\r\n" + 
      		"	margin: 1%;	 	 \r\n" + 
      		"}\r\n" + 
      		"body {\r\n" + 
      		"	background-image: url(\"/webproject/train background.jpg\");\r\n" + 
      		"    background-repeat: no-repeat;\r\n" + 
      		"    background-size: 100% 100%;\r\n" + 
      		"    overflow: hidden;\r\n" + 
      		"   	width: 100%;\r\n" + 
      		"   	margin: 0 auto 0 auto;\r\n" + 
      		"}\r\n" + 
      		".logo {\r\n" + 
      		"	width: 10%;\r\n" + 
      		"	height: 100%;\r\n" + 
      		"	text-align: left;\r\n" + 
      		"	display: inline;\r\n" + 
      		"}\r\n" + 
      		".title {\r\n" + 
      		"	text-align: center;\r\n" + 
      		"	display: inline;\r\n" + 
      		"	position: absolute;\r\n" + 
      		"	left: 40%;\r\n" + 
      		"	top: -20px;\r\n" + 
      		"	font-size: 60px;\r\n" + 
      		"}\r\n" + 
      		"</style>\r\n" + 
      		"</head>\r\n" + 
      		"\r\n" + 
      		"<body>\r\n" + 
      		"<header>\r\n" + 
      		"	<h1 class = \"title\">Search Result</h1>\r\n" + 
      		"	<img class= \"logo\" src=\"/webproject/student station logo.png\" alt=\"Student Station\">\r\n" + 
      		"</header>\r\n" + 
      		"\r\n" + 
      		"<nav>\r\n" + 
      		"<a href=\"/webproject/home.html\">Home</a> <br>\r\n" + 
      		"<a href=\"/webproject/simpleFormSearch.html\">Search Students</a> <br>\r\n" + 
      		"<a href=\"/webproject/simpleFormInsert.html\">Create A Page</a> <br>\r\n" + 
      		"</nav>\r\n" + 
      		"\r\n" + 
      		"<section>\r\n" + "<table style='width:100%'>\r\n" +
            "<tr><th align='left'>Name</th><th align='left'>Email</th><th align='left'>Major</th><th align='left'>" + 
            "Minor</th><th align='left'>Organizations</th><th align='left'>Graduation date</th></tr>" +
              "</ul>\n"
      		);

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
//         DBConnectionLynch.getDBConnection();
         connection = DBConnection.connection;

         if (keyword == null || keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM studentInfo";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM studentInfo WHERE NAME LIKE ? OR EMAIL LIKE ? OR MAJOR LIKE ? OR MINOR LIKE ? OR ORGANIZATIONS LIKE ? OR GRADUATION LIKE ?";
            String search = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);

            
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String name = rs.getString("name").trim();
            String email = rs.getString("email").trim();
            String major = rs.getString("major").trim();
            String minor = rs.getString("minor").trim();
            String org = rs.getString("organizations").trim();
            String grad = rs.getString("graduation").trim();



            if (keyword == null || keyword.isEmpty() || name.contains(keyword) || email.contains(keyword) || major.contains(keyword) || minor.contains(keyword) || org.contains(keyword) || grad.contains(keyword)) {
               out.println("<tr>\t<td>" + name + "</td>");
               out.println("<td>" + email + "</td>");
               out.println("<td>" + major + "</td>");
               out.println("<td>" + minor + "</td>");
               out.println("<td>" + org + "</td>");
               out.println("<td>" + grad + "</td></tr><br>");
            }
         }
         out.println("</table>");
         out.println("<a href=/webproject/simpleFormSearch.html>Back to Search</a> <br>");
         out.println("</section></body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
