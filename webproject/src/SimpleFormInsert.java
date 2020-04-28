
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String email = request.getParameter("email");
      String major = request.getParameter("major");
      String minor = request.getParameter("minor");
      String org = request.getParameter("organization");
      String grad = request.getParameter("graduation");
      String bio = request.getParameter("bio");


      Connection connection = null;
      String insertSql = " INSERT INTO studentInfo (id, NAME, EMAIL, MAJOR, MINOR, ORGANIZATIONS, GRADUATION, BIO) values (default, ?, ?, ?, ?, ?, ?, ?)";

      try {
    	 DBConnection.getDBConnection(getServletContext());
    	 DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, email);
         preparedStmt.setString(3, major);
         preparedStmt.setString(4, minor);
         preparedStmt.setString(5, org);
         preparedStmt.setString(6, grad);
         preparedStmt.setString(7, bio);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
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
          		"	<h1 class = \"title\">Create A Page</h1>\r\n" + 
          		"	<img class= \"logo\" src=\"/webproject/student station logo.png\" alt=\"Student Station\">\r\n" + 
          		"</header>\r\n" + 
          		"\r\n" + 
          		"<nav>\r\n" + 
          		"<a href=\"/webproject/home.html\">Home</a> <br>\r\n" + 
          		"<a href=\"/webproject/simpleFormSearch.html\">Search Students</a> <br>\r\n" + 
          		"<a href=\"/webproject/simpleFormInsert.html\">Create A Page</a> <br>\r\n" +
          		"<a href=\"/webproject/delete.html\">Delete Student Page</a> <br>\r\n" + 
          		"</nav>\r\n" + 
          		"\r\n" + 
          		"<section>\r\n" +

            "  <li><b>Name</b>: " + name + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Major</b>: " + major + "\n" + //
            "  <li><b>Minor</b>: " + minor + "\n" + //
            "  <li><b>Organizations</b>: " + org + "\n" + //
            "  <li><b>Graduation</b>: " + grad + "\n" + //
            "  <li><b>Bio</b>: " + bio + "\n" +"<br>"
            );

      out.println("<a href=/webproject/home.html>Go Home</a> <br>");
      out.println("</section></body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
