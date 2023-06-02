import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String url = "jdbc:mysql://localhost:3306/bank";
    String dbUsername = "root";
    String dbPassword = "password";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        response.sendRedirect("transaction.jsp");
      } 
      else 
      {
        response.sendRedirect("login.jsp");
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
    } catch (Exception e) {
      System.out.print(e);//throw new ServletException("Error: " + e.getMessage());
    }
  }
}