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

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Authenticate() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      search(email, password, response);
   }

   void search(String email, String password, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
              "transitional//en\">\n"; //
        out.println(docType + "<html>\n" + 
        		"<head>\n" + 
        		"<style>\n" + 
        		"header {\n" + 
        		"    background-color:black;\n" + 
        		"    color: white;\n" + 
        		"    width: 100%;\n" + 
        		"    height: 100px;\n" + 
        		"    padding: 5px;\n" + 
        		"}\n" + 
        		"nav {\n" + 
        		"    line-height:30px;\n" + 
        		"    background-color:#eeeeee;\n" + 
        		"    opacity: 60%;\n" + 
        		"    height:300px;\n" + 
        		"    width: 10%;\n" + 
        		"    float:left;\n" + 
        		"    padding:5px;	      \n" + 
        		"}\n" + 
        		"section {\n" + 
        		"    width: 85%;\n" + 
        		"    float:left;\n" + 
        		"    padding:10px;\n" + 
        		"    display: inline;\n" + 
        		"	background-color: white;\n" + 
        		"	opacity: 100%;\n" + 
        		"	margin: 1%;	 \n" + 
        		"}\n" + 
        		"body {\n" + 
        		"	background-image: url(\"/webproject/train background.jpg\");\n" + 
        		"    background-repeat: no-repeat;\n" + 
        		"    background-size: 100% 100%;\n" + 
        		"    overflow: scroll;\n" + 
        		"   	width: 100%;\n" + 
        		"   	margin: 0 auto 0 auto;\n" + 
        		"}\n" + 
        		".logo {\n" + 
        		"	width: 10%;\n" + 
        		"	height: 100%;\n" + 
        		"	text-align: left;\n" + 
        		"	display: inline;\n" + 
        		"}\n" + 
        		".picture {\n" + 
        		"	width: 80%;\n" + 
        		"	height:80%;\n" + 
        		"	text-align: left;\n" + 
        		"	display: inline;\n" + 
        		"}\n" + 
        		".title {\n" + 
        		"	text-align: center;\n" + 
        		"	display: inline;\n" + 
        		"	position: absolute;\n" + 
        		"	left: 40%;\n" + 
        		"	top: -20px;\n" + 
        		"	font-size: 60px;\n" + 
        		"}\n" + 
        		"section img .picture {\n" + 
        		"	background: white;\n" + 
        		"}\n" + 
        		"</style>\n" + 
        		"</head>\n" + 
        		"\n" + 
        		"<body>\n" + 
        		"<header>\n" + 
        		"	<h1 class = \"title\">Student Station</h1>\n" + 
        		"	<img class= \"logo\" src=\"/webproject/student station logo.png\" alt=\"Student Station\">\n" + 
        		"</header>\n"
        		);

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      //PreparedStatement preparedStatement2 = null;
      try {
         DBConnection.getDBConnection(getServletContext());
//         DBConnectionLynch.getDBConnection();
         connection = DBConnection.connection;

         if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            String selectSQL = "SELECT * FROM authenticate";
            preparedStatement = connection.prepareStatement(selectSQL);
            //preparedStatement2 = connection.prepareStatement(selectSQL);
        	out.println("Invalid Login Information!");
        	out.println("<br> <a href=/webproject/authenticate.html>Try Again</a> <br>");
         } else {                                                          // OR
        	String selectSQL = "SELECT * FROM authenticate WHERE EMAIL LIKE ? AND PASSWORD LIKE ?";
            String em = email + "%";
            String pw = password + "%"; 
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, em);
            preparedStatement.setString(2, pw);
            
         }
         ResultSet rs = preparedStatement.executeQuery();
         int q = 0;

         while (rs.next()) {
            String ema = rs.getString("email").trim();
            String passw = rs.getString("password").trim();


            if (email.contains(ema) && passw.contains(password)) {
               q = 1;
               // ADJUST AS NEEDED FOR COMPLETION!!!!! 
               out.println("\n" + 
               		"<nav>\n" + 
               		"<a href=\"/webproject/simpleFormInsert.html\">Create Page</a> <br>\n" + 
               		"<a href=\"/webproject/simpleFormSearch.html\">Search Students</a> <br>\n" + 
               		"<a href=\"/webproject/delete.html\">Delete Student Page</a> <br>\n" + 
               		"\n" + 
               		"</nav>\n" + 
               		"\n" + 
               		"<section>\n" + 
               		"	<h2><a href=\"https://www.unomaha.edu\">Check out out UNO's news page!</a></h2> <br>\n" + 
               		"		<img class= \"picture\" alt=\"Hockey Photo\" src=\"/webproject/alberts600.jpg\"> <br>\n" + 
               		"	<h2> Student Station has now launched! </h2>\n" + 
               		"		<img class= \"picture\" src=\"/webproject/student station logo.png\" alt=\"Student Station\"> <br>\n" + 
               		"</section>\n" 
               		);
            }
         }
         if(q == 0) {
        	out.println("Invalid Login Information!");
         	out.println("<br> <a href=/webproject/authenticate.html>Try Again</a> <br>");
         }
         //out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
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