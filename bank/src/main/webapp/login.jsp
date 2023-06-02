<!DOCTYPE html>
<html>
<head>
<title>Login</title>
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
<h1>Login to Online Banking</h1>
<form action="LoginServlet" method="post">
<label for="username">Username:</label>
<input type="text" id="username" name="username" required>
<br><br>
<label for="password">Password:</label>
<input type="password" id="password" name="password" required>
<br><br>
<input type="submit" value="Login">
</form>
</center>
</body>
</html>
