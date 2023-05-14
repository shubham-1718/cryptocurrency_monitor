<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Login Page</title>
    <style>
      html,
      body {
        height: 100%;
        margin: 0;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      form {
        width: 300px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
      }

      label {
        display: block;
        margin-bottom: 10px;
      }

      input[type="text"],
      input[type="password"] {
        width: 100%;
        padding: 5px;
        margin-bottom: 10px;
      }

      input[type="submit"] {
        width: 100%;
        padding: 10px;
        background-color: #4CAF50;
        color: white;
        border: none;
        cursor: pointer;
      }
    </style>
    <script>
      function storeUsername() {
        var username = document.getElementById("username").value;
        localStorage.setItem("username", username);
      }
    </script>
  </head>
  <body>
    <form action="cryptocurrencyPrice.jsp" method="post" onsubmit="storeUsername()">
      <h1>Login Page</h1>
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
      <input type="submit" value="Login">
    </form>
  </body>
</html>