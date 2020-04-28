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

//@WebServlet("/SimpleFormSearch")
@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Authenticate() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      search(username, password, response);
   }

   void search(String username, String password, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Login Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      //PreparedStatement preparedStatement2 = null;
      try {
         DBConnection.getDBConnection(getServletContext());
//         DBConnectionLynch.getDBConnection();
         connection = DBConnection.connection;

         if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            String selectSQL = "SELECT * FROM authenticate";
            preparedStatement = connection.prepareStatement(selectSQL);
            //preparedStatement2 = connection.prepareStatement(selectSQL);
        	out.println("Invalid Login Information!");
        	out.println("<br> <a href=/webproject/authenticate.html>Search Data</a> <br>");
         } else {                                                          // OR
        	String selectSQL = "SELECT * FROM authenticate WHERE EMAIL LIKE ? AND PASSWORD LIKE ?";
            String un = username + "%";
            String pw = password + "%"; 
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, un);
            preparedStatement.setString(2, pw);
            
         }
         ResultSet rs = preparedStatement.executeQuery();
         int q = 0;

         while (rs.next()) {
            String name = rs.getString("email").trim();
            String passw = rs.getString("password").trim();


            if (name.contains(username) && passw.contains(password)) {
               q = 1;
               // ADJUST AS NEEDED FOR COMPLETION!!!!! 
               out.println("Name: " + name + ", ");
               out.println("Password: " + passw + "<br>");
               out.println("<br> <a href=/webproject/authenticate.html>Search Data</a> <br>");
            }
         }
         if(q == 0) {
        	out.println("Invalid Login Information!");
         	out.println("<br> <a href=/webproject/authenticate.html>Search Data</a> <br>");
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
