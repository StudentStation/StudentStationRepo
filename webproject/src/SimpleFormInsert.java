
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
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Name</b>: " + name + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Major</b>: " + major + "\n" + //
            "  <li><b>Minor</b>: " + minor + "\n" + //
            "  <li><b>Organizations</b>: " + org + "\n" + //
            "  <li><b>Graduation</b>: " + grad + "\n" + //
            "  <li><b>Bio</b>: " + bio + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
