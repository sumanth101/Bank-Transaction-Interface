import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String username = (String) session.getAttribute("username");
    double amount = Double.parseDouble(request.getParameter("amount"));
    String transaction_type = request.getParameter("transaction_type");
    String url = "jdbc:mysql://localhost:3306/bank";
    String dbUsername = "root";
    String dbPassword = "password";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
      if (transaction_type.equals("deposit")) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET balance = balance + ? WHERE username=?");
        preparedStatement.setDouble(1, amount);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
      } else if (transaction_type.equals("withdraw")) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET balance = balance - ? WHERE username=?");
        preparedStatement.setDouble(1, amount);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
      }
      response.sendRedirect("transaction.jsp");
      connection.close();
    } catch (Exception e) {
      throw new ServletException("Error: " + e.getMessage());
    }
  }
}
