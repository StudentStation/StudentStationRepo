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
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

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
            String selectSQL = "SELECT * FROM studentInfo WHERE NAME LIKE ? OR EMAIL LIKE ? OR MAJOR LIKE ? OR MINOR LIKE ? OR ORGANIZATION LIKE ? OR GRADUATION LIKE ?";
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
            String org = rs.getString("organization").trim();
            String grad = rs.getString("graduation").trim();



            if (keyword == null || keyword.isEmpty() || name.contains(keyword) || email.contains(keyword) || major.contains(keyword) || minor.contains(keyword) || org.contains(keyword) || grad.contains(keyword)) {
               out.println("Name: " + name + ", ");
               out.println("Email: " + email + ", ");
               out.println("Major: " + major + ", ");
               out.println("Minor: " + minor + ", ");
               out.println("Organizations: " + org + ", ");
               out.println("Graduation Date: " + grad + "<br>");
            }
         }
         out.println("<a href=/webproject/searchLynch.html>Search Data</a> <br>");
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
