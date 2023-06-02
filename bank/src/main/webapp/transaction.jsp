<%@page import="java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
 <title>Transaction</title>
 <style>
   body {
     font-family: Arial, sans-serif;
     background-color: #f0f8ff;
   }
   form {
     display: inline-block;
     background-color: #fff;
     padding: 20px;
     border-radius: 5px;
   }
 </style>
</head>
<body>
 <center>
   <h1>Welcome, <%= session.getAttribute("username") %></h1>
   <p>Current balance: <span id="balance"></span></p>
   <form action="TransactionServlet" method="post">
    <label for="amount">Amount:</label>
    <input type="number" id="amount" name="amount" min="0" step="0.01" required>
    <br><br>
    <label for="transaction_type">Transaction Type:</label>
    <select id="transaction_type" name="transaction_type">
     <option value="deposit">Deposit</option>
     <option value="withdraw">Withdraw</option>
    </select>
    <br><br>
    <input type="submit" value="Submit Transaction">
   </form>
   <br>
   <form action="LogoutServlet" method="post">
    <input type="submit" value="Logout">
   </form>
 </center>
 <% 
  String username = (String) session.getAttribute("username");
  String url = "jdbc:mysql://localhost:3306/bank";
  String dbUsername = "root";
  String dbPassword = "password";
  Connection connection = null;
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  try {
   Class.forName("com.mysql.cj.jdbc.Driver");
   connection = DriverManager.getConnection(url, dbUsername, dbPassword);
   preparedStatement = connection.prepareStatement("SELECT balance FROM users WHERE username=?");
   preparedStatement.setString(1, username);
   resultSet = preparedStatement.executeQuery();

   if (resultSet.next()) {
    double balance = resultSet.getDouble("balance");
 %>
    <script>
     document.getElementById("balance").innerHTML = "<%= balance %>";
    </script>
 <% 
   }

  } catch (Exception e) {
   out.println("Error: " + e.getMessage());
  } finally {
   if (resultSet != null) resultSet.close();
   if (preparedStatement != null) preparedStatement.close();
   if (connection != null) connection.close();
  }
 %>
</body>
</html>

